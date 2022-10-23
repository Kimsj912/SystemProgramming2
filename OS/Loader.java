package OS;

import Elements.Interrupt;
import Elements.Process;
import Elements.Program;
import Enums.EInterrupt;
import HW.Memory;
import HW.Storage;

import java.io.IOException;

public class Loader {
    private Memory memory;
    private Storage storage;

    private Scheduler scheduler;
    private InterruptHandler interruptHandler;

    public Loader() {
    }

    public void initialize(Memory memory, Storage storage, Scheduler scheduler, InterruptHandler interruptHandler) {
        this.memory = memory;
        this.storage = storage;
        this.scheduler = scheduler;
        this.interruptHandler = interruptHandler;
    }
    
    public void initialLoad() throws IOException{
        try{
            for (Program program : storage.startPrograms()) load(program);
            interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, scheduler.deReadyQueue()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void load(Program program) {
        Process process = memory.load(program);
        scheduler.enReadyQueue(process);
        this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eIdle, process));
    }


}
