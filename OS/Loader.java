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
    private Scheduler scheduler;

    public void initialize(Memory memory, Scheduler scheduler) {
        this.memory = memory;
        this.scheduler = scheduler;
    }
    
    public void load(Program program) {
        Process process = memory.load(program);
        scheduler.enReadyQueue(process);
    }

    public void initialLoad(Program[] startPrograms) throws IOException {
        for (Program program : startPrograms) {
            load(program);
        }
    }


}
