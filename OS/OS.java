package OS;

import Elements.Instruction;
import Elements.Interrupt;
import Elements.Process;
import Elements.Program;
import Enums.EInterrupt;
import HW.CPU;
import HW.Memory;
import HW.Storage;
import HW.UI;

import java.io.IOException;

public class OS {
    // Association
    private Storage storage;
    private Memory memory;
    private CPU cpu;

    // Attributes
    private final InterruptHandler interruptHandler;
    private final Loader loader;
    private final Scheduler scheduler;
    private final UI ui;

    public OS(CPU cpu, Memory memory, Storage storage, UI ui) {
        // Connection
        this.cpu = cpu;
        this.memory = memory;
        this.storage = storage;
        this.ui = ui;

        // Attributes
        this.interruptHandler = new InterruptHandler();
        this.loader = new Loader();
        this.scheduler = new Scheduler();
    }

    public void initialize(){
        // IO �ʱ�ȭ (association IO Devices)
        this.ui.initialize(loader,scheduler,interruptHandler);
        this.scheduler.initialize(memory, cpu, loader, this, interruptHandler, ui);
        this.loader.initialize(memory, storage, scheduler, interruptHandler);
    }

    public void run() throws IOException{
        // OS�� ���۰� �Բ� ���� ���α׷��� �����Ų��.
        ui.start();
        loader.initialLoad();
        // Timer
        // TODO: UI�� ���� �߰��Ȱ� �ִ��� üũ�ϴ� ���� �߰��Ͽ� Process �߰����� ����
        scheduler.start();

    }

    public void executeInstruction(Process process){
        Instruction nextInstruction = process.getInstruction();
        if(nextInstruction == null) interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessTerminated, process));
        else{
            cpu.setInstruction(nextInstruction);
            this.ui.addInstructionLog(nextInstruction.toString());
        }
    }
}