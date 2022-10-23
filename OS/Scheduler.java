package OS;

import Elements.Interrupt;
import Enums.EInterrupt;
import Enums.EProcessStatus;
import HW.CPU;
import HW.Memory;
import Elements.Process;
import HW.UI;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler extends Thread {

    private InterruptHandler interruptHandler;
    // Association
    private Memory memory;
    private CPU cpu;
    private Loader loader;
    private OS os;
    private UI ui;

    // Attributes
    private Queue<Process> readyQueue;
    private Queue<Process> waitQueue;
    private Process currentProcess;


    public Scheduler() {
        this.readyQueue = new LinkedList<>();
        this.waitQueue = new LinkedList<>();
    }

    public void initialize(Memory memory, CPU cpu, Loader loader, OS os, InterruptHandler interruptHandler, UI ui){
        this.memory = memory;
        this.cpu = cpu;
        this.loader = loader;
        this.os = os;
        this.interruptHandler = interruptHandler;
        this.ui = ui;

    }

    public void run(){
        while(true){
            try {
                checkInterrupt();
                if(this.currentProcess == null) {
                    this.currentProcess = deReadyQueue();
                    if(this.currentProcess == null) {
                        this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eIdle, null));
                        continue;
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
    public Process deReadyQueue() {return readyQueue.poll();}


    public void checkInterrupt(){
        Process oldProcess, newProcess;
        Interrupt interrupt = this.interruptHandler.getInterrupt(); // TODO: !!!!!!!!!!!!!!!!여기부터 수정해야함. 초기에 프로젝트 셋팅하고 루핑도는 과정이 뻑이 좀 있음. interruptHandler.getInterrupt()의 위치 변화가 요구됨.
        if(interrupt == null || interrupt.getType() == null) return;
        switch(interrupt.getType()){
            case eIdle: // 더이상 읽을게 없는 상태
                break;
            case eRunning:
                this.cpu.getContext().setStatus(EProcessStatus.RUNNING);
                this.ui.addLog("Process " + interrupt.getProcess().getProcessName() + " is running.");
                break;
            case eTimeOut: // TimeOut
                this.ui.addLog("...time out");
                // Context Switching (with readyQueue)
                // oldProcess
                oldProcess = interrupt.getProcess();
                oldProcess.setContext(cpu.getContext());
                oldProcess.getContext().setStatus(EProcessStatus.READY);
                enReadyQueue(oldProcess);

                // new Process
                newProcess = deReadyQueue();
                if(newProcess == null) {
                    this.currentProcess= null;
                    this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eIdle, null));
                    return;
                }
                this.ui.addLog("Context Switched by Interrupted: " + oldProcess.getContext().getName()+ " -> "+ newProcess.getContext().getName());
                this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, newProcess));
                break;
            case eProcessStarted:
                this.currentProcess = interrupt.getProcess();
                cpu.setContext(this.currentProcess.getContext());
                cpu.getContext().setStatus(EProcessStatus.RUNNING);
                this.ui.addLog(this.currentProcess.getProcessName()+" is Started.");
//                new Timer().schedule(new TimerTask() {@Override public void run() {
//                    interruptHandler.addInterrupt(new Interrupt(EInterrupt.eTimeOut, currentProcess));}
//                }, 1500);
//                this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eRunning, this.currentProcess));
                break;
            case eProcessTerminated:
                // Elements.Process old Elements.Process
                oldProcess = interrupt.getProcess();
                oldProcess.setContext(cpu.getContext());
                oldProcess.getContext().setStatus(EProcessStatus.TERMINATED);
                this.ui.addLog(oldProcess.getProcessName() + " is terminated");
                // TODO: 요기에 Storage까지 프로세스 종료 처리 로직 추가

                // new Process
                newProcess = deReadyQueue();
                if(newProcess == null) {
                    currentProcess = null;
                    this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eIdle, null));
                    break;
                }
                this.ui.addLog("Context Switched by Terminated: " + oldProcess.getContext().getName()+ " -> "+ newProcess.getContext().getName());
                this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, newProcess));
                break;
            // TODO: 구현 필요
            case eIOStarted:
                break;
            case eIOTerminated:
                break;
        }


    }

    // For UI
    public Queue<Process> getReadyQueue(){
        return readyQueue;
    }

    public Queue<Process> getWaitingQueue(){
        return waitQueue;
    }

    public Process getCurrentProcess(){
        return currentProcess;
    }
}
