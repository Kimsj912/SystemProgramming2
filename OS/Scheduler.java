package OS;

import Constants.ConstantData;
import Elements.Instruction;
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

    // Association
    private OS os;
    private UI ui;
    private InterruptHandler interruptHandler;
    private CPU cpu;

    // Attributes
    private Process currentProcess;
    private final Queue<Process> readyQueue;
    private final Queue<Process> waitQueue;

    public void enReadyQueue(Process process) {readyQueue.add(process);}
    public Process deReadyQueue() {return readyQueue.poll();}
    public void enWaitQueue(Process process) {waitQueue.add(process);}
    public Process deWaitQueue() {return waitQueue.poll();}


    // For UI
    public synchronized Queue<Process> getReadyQueue(){return readyQueue;}
    public synchronized Queue<Process> getWaitingQueue(){return waitQueue;}
    public synchronized Process getCurrentProcess(){return currentProcess;}


    // Constructor
    public Scheduler() {
        this.readyQueue = new LinkedList<>();
        this.waitQueue = new LinkedList<>();
    }

    // Initialize
    public void initialize(OS os, InterruptHandler interruptHandler, UI ui, CPU cpu){
        this.os = os;
        this.interruptHandler = interruptHandler;
        this.ui = ui;
        this.cpu = cpu;
    }

    // Methods
    public void run(){
        while(true){
            try {
                interruptHandler.handleInterrupts(this.currentProcess);
                if(this.currentProcess != null) {
                    this.cpu.setProcess(this.currentProcess);
                }else if(!this.readyQueue.isEmpty()) this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, this.deReadyQueue()));
                else {
                    // 현재 프로세스도 없고, 준비 큐도 비어있으면 Idle 상태로 전환
                    this.cpu.setProcess(null);
                    interruptHandler.addInterrupt(new Interrupt(EInterrupt.eIdle, null));
                }
                sleep(Integer.parseInt(ConstantData.cpuTimeSlice.getText()));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void setCurrentProcess(Process newProcess){
        this.currentProcess = newProcess;
    }
}
