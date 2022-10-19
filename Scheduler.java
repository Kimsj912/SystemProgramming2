import Enums.EInterrupt;
import Enums.EProcessStatus;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Scheduler extends Thread {

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

    public void run(){
        while(true){
            try {
                checkInterrupt();
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
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void enReadyQueue(Process process) {readyQueue.add(process);}
    public synchronized Process deReadyQueue() {return readyQueue.poll();}


    public void checkInterrupt(){
        Process oldProcess, newProcess;
        Interrupt interrupt = this.interruptHandler.getInterrupt(); // TODO: !!!!!!!!!!!!!!!!여기부터 수정해야함. 초기에 프로젝트 셋팅하고 루핑도는 과정이 뻑이 좀 있음. interruptHandler.getInterrupt()의 위치 변화가 요구됨.
        if(interrupt == null || interrupt.getType() == null) return;
        switch(interrupt.getType()){
            case eIdle: // 더이상 읽을게 없는 상태
                // TODO: UI로 ReadyQueue에 enqueue되는게 있는지 관찰자 패턴으로 체크하기
                System.out.println("waiting new process...");
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
                enReadyQueue(oldProcess);

                // Process new Process
                newProcess = deReadyQueue();
                if(newProcess == null) {
                    this.currentProcess= null;
                    this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eIdle, null));
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
                // TODO: 요기에 Storage까지 프로세스 종료 처리 로직 추가

                // Process new Process
                newProcess = deReadyQueue();
                if(newProcess == null){
                    currentProcess = null;
                    this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eIdle,null));
                    System.out.println("All Process Terminated");
                } else{
                    this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, newProcess));
                    System.out.println("Context Switched by Terminated: " + oldProcess.getContext().getName()+ " -> "+ newProcess.getContext().getName());
                }
                break;
            // TODO: 구현 필요
            case eIOStarted:
                break;
            case eIOTerminated:
                break;
        }


    }
}
