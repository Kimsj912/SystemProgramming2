package OS;

import Elements.Instruction;
import Elements.Interrupt;
import Elements.Process;
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
        this.ui.initialize(loader,scheduler,interruptHandler);
        this.interruptHandler.initialize(scheduler, ui);
        this.loader.initialize(memory, scheduler);
        this.scheduler.initialize(this, interruptHandler, ui);
    }

    public void run() throws IOException{
        // Load Start Programs
        loader.initialLoad(storage.startPrograms());

        // Start Thread
        ui.start();
        scheduler.start();
    }

    public void executeInstruction(Process process){
        Instruction nextInstruction = process.getInstruction();
        if(nextInstruction == null) interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessTerminated, process));
        else{
            cpu.setInstruction(nextInstruction);
            this.ui.addInstructionLog(process, nextInstruction.toString());
        }
    }
}