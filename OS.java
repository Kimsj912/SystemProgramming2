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
        // IO���� �ʱ�ȭ�Ѵ�. (association IO Devices)
        this.scheduler.initialize(memory, cpu, loader, this, interruptHandler);
        this.loader.initialize(memory, storage);
    }

    public void run(){
        // TODO: UI�� ���� �߰��Ȱ� �ִ��� üũ�ϴ� ���� �߰��Ͽ� Process �߰����� ����
        for (Program program : storage.startProgram()) { // �ᱹ storage�� �ִ� �͵帪�� �� program�̴ϱ�, �������α׷��� ���������� Program���·� �����;��� string ���·� �������� �ȵ�.
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