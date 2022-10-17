import Enums.EInterrupt;
import Enums.EProcessStatus;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class OS {
    // Association
    private Storage storage;
    private Memory memory;
    private CPU cpu;

    // Attributes
    private final InterruptHandler interruptHandler;
    private final Loader loader;
    private final Scheduler scheduler;

    public OS(CPU cpu, Memory memory, Storage storage) {
        // Connection
        this.cpu = cpu;
        this.memory = memory;
        this.storage = storage;

        // Attrubutes
        this.interruptHandler = new InterruptHandler();
        this.loader = new Loader();
        this.scheduler = new Scheduler();

    }

    public void initialize(){
        // IO들을 초기화한다. (association IO Devices)
        this.scheduler.initialize(memory, cpu, loader, this, interruptHandler);
        this.loader.initialize(memory, storage);
    }

    public void run(){
        // TODO: UI로 파일 추가된게 있는지 체크하는 로직 추가하여 Process 추가로직 구성
        for (Program program : storage.startProgram()) { // 결국 storage에 있는 것드릉ㄴ 다 program이니까, 시작프로그램을 가져오더라도 Program형태로 가져와야지 string 형태로 가져오면 안됨.
            Process process = this.loader.load(program);
            scheduler.enReadyQueue(process);
        }
        interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, scheduler.deReadyQueue()));
        // Timer
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    scheduler.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }

    public void executeInstruction(Process process){
        Instruction nextInstruction = process.getInstruction();
        if(nextInstruction == null) interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessTerminated, process));
        else cpu.executeInstruction(nextInstruction);
    }
}