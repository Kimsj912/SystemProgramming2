import Enums.EInterrupt;
import Enums.EProcessStatus;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private InterruptHandler interruptHandler;
    // Association
    private Memory memory;
    private CPU cpu;
    private Loader loader;
    private OS os;

    // Attributes
    private Queue<Process> readyQueue;
    private Process currentProcess;


    public Scheduler() {
        this.readyQueue = new LinkedList<>();
    }

    public void initialize(Memory memory, CPU cpu, Loader loader, OS os, InterruptHandler interruptHandler){
        this.memory = memory;
        this.cpu = cpu;
        this.loader = loader;
        this.os = os;
        this.interruptHandler = interruptHandler;

    }

    public void run() throws InterruptedException{
        if(this.currentProcess == null) {
            this.currentProcess = deReadyQueue();
            if(this.currentProcess == null) {
                this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eIdle, null));
                return;
            }
            this.currentProcess.getContext().setStatus(EProcessStatus.RUNNING);
            this.cpu.setContext(this.currentProcess.getContext());
        } else {
            os.executeInstruction(this.currentProcess);
        }
        checkInterrupt();
    }

    public synchronized void enReadyQueue(Process process) {
        // ���μ����� �غ� ť�� �ִ´�.
        readyQueue.add(process);
    }
    
    public synchronized Process deReadyQueue() {
        // �غ� ť���� ���μ����� ������.
        return readyQueue.poll();
    }

    public void checkInterrupt(){
        Process oldProcess, newProcess;
        Interrupt interrupt = this.interruptHandler.getInterrupt(); // TODO: !!!!!!!!!!!!!!!!������� �����ؾ���. �ʱ⿡ ������Ʈ �����ϰ� ���ε��� ������ ���� �� ����. interruptHandler.getInterrupt()�� ��ġ ��ȭ�� �䱸��.
        if(interrupt == null || interrupt.getType() == null) return;
        switch(interrupt.getType()){
            case eIdle: // ���̻� ������ ���� ����
                break;
            case eRunning:
                break;
            case eTimeOut: // TimeOut
                System.out.println("...time out");
                // Context Switching (with readyQueue)
                // Process old Process
                oldProcess = interrupt.getProcess();
                oldProcess.setContext(cpu.getContext());
                oldProcess.getContext().setStatus(EProcessStatus.READY);
                readyQueue.add(oldProcess);

                // Process new Process
                newProcess = readyQueue.poll();
                if(newProcess == null) {
                    this.currentProcess= null;
                    return;
                }
                this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, newProcess));
                System.out.println("Context Switched by Interrupted: " + oldProcess.getContext().getName()+ " -> "+ newProcess.getContext().getName());
                break;
            case eProcessStarted:
                this.currentProcess = interrupt.getProcess();
                cpu.setContext(this.currentProcess.getContext());
                cpu.getContext().setStatus(EProcessStatus.RUNNING);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        interruptHandler.addInterrupt(new Interrupt(EInterrupt.eTimeOut, currentProcess));
                    }
                }, 2010);
                this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eRunning, this.currentProcess));
                System.out.println("Process Started: " + this.currentProcess.getContext().getName());
                break;
            case eProcessTerminated:
                // Process old Process
                oldProcess = interrupt.getProcess();
                oldProcess.setContext(cpu.getContext());
                oldProcess.getContext().setStatus(EProcessStatus.TERMINATED);
                // TODO: ��⿡ Storage���� ���μ��� ���� ó�� ���� �߰�

                // Process new Process
                newProcess = readyQueue.poll();
                if(newProcess == null){
                    System.out.println("All Process Terminated");
                    this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eIdle,null));
                    currentProcess = null;
                    return;
                }
                cpu.setContext(newProcess.getContext());
                this.currentProcess = newProcess;
                cpu.getContext().setStatus(EProcessStatus.RUNNING);
                System.out.println("Context Switched by Terminated: " + oldProcess.getContext().getName()+ " -> "+ newProcess.getContext().getName());
                break;
            // TODO: ���� �ʿ�
            case eIOStarted:
                break;
            case eIOTerminated:
                break;
        }


    }
}
