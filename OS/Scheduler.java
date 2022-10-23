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

    // Association
    private OS os;
    private UI ui;
    private InterruptHandler interruptHandler;

    // Attributes
    private Process currentProcess;
    private final Queue<Process> readyQueue;
    private final Queue<Process> waitQueue;

    public void enReadyQueue(Process process) {readyQueue.add(process);}
    public Process deReadyQueue() {return readyQueue.poll();}


    // For UI
    public Queue<Process> getReadyQueue(){return readyQueue;}
    public Queue<Process> getWaitingQueue(){return waitQueue;}
    public Process getCurrentProcess(){return currentProcess;}


    // Constructor
    public Scheduler() {
        this.readyQueue = new LinkedList<>();
        this.waitQueue = new LinkedList<>();
    }

    // Initialize
    public void initialize(OS os, InterruptHandler interruptHandler, UI ui){
        this.os = os;
        this.interruptHandler = interruptHandler;
        this.ui = ui;

    }

    // Methods
    public void run(){
        while(true){
            try {
                interruptHandler.handleInterrupts(this.currentProcess);
                if(this.currentProcess != null) os.executeInstruction(this.currentProcess);
                else if(!this.readyQueue.isEmpty()) this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, this.deReadyQueue()));
                sleep(900);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void setCurrentProcess(Process newProcess){
        this.currentProcess = newProcess;
    }
}
