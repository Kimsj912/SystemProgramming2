package OS;

import Elements.Instruction;
import Elements.Interrupt;
import Elements.Process;
import Elements.Program;
import Enums.EInterrupt;
import HW.CPU;
import HW.Memory;
import HW.Storage;

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

    public void run() throws IOException{
        // OS�� ���۰� �Բ� ���� ���α׷��� �����Ų��.
        for (Program program : storage.startPrograms()) { // �ᱹ storage�� �ִ� �͵帪�� �� program�̴ϱ�, �������α׷��� ���������� Program���·� �����;��� string ���·� �������� �ȵ�.
            Process process = this.loader.load(program);
            scheduler.enReadyQueue(process);
        }
        interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, scheduler.deReadyQueue()));

        // Timer
        // TODO: UI�� ���� �߰��Ȱ� �ִ��� üũ�ϴ� ���� �߰��Ͽ� Elements.Process �߰����� ����
        scheduler.start();
    }

    public void executeInstruction(Process process){
        Instruction nextInstruction = process.getInstruction();
        if(nextInstruction == null) interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessTerminated, process));
        else cpu.executeInstruction(nextInstruction);
    }
}