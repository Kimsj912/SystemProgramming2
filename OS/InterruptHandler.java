package OS;

import Elements.Interrupt;
import Elements.Process;
import Enums.EInterrupt;
import Enums.EProcessStatus;
import HW.UI;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

// Inner Classes
public class InterruptHandler {
    // Assoication
    private Scheduler scheduler;
    private UI ui;

    // Attributes
    private Interrupt currentInterrupt;
    private final Queue<Interrupt> interruptQueue;

    // Getter & Setter
    public void addInterrupt(Interrupt interrupt){
        this.interruptQueue.add(interrupt);
    }
    public Interrupt getInterrupt(){return this.interruptQueue.poll();}

    // Constructor
    public InterruptHandler(){this.interruptQueue = new LinkedList<>();}

    // Methods
    public void initialize(Scheduler scheduler, UI ui){
        this.scheduler = scheduler;
        this.ui = ui;
    }

    public void handleInterrupts(Process currentProcess){
        Process oldProcess, newProcess;
        Interrupt interrupt = this.getInterrupt();
        if(interrupt == null || interrupt.getType() == null) return;

        currentInterrupt = interrupt;
        this.invokeMethod(interrupt.getType().name());

        switch(interrupt.getType()){
            case eTimeOut: // TimeOut
                oldProcess = interrupt.getProcess();
                this.ui.addLog("TimeOut: " +oldProcess.getProcessName()+"("+oldProcess.getPid()+")");
                newProcess = scheduler.deReadyQueue();
                if(newProcess == null) return;
                this.ui.addLog("");oldProcess.setStatus(EProcessStatus.READY);
                newProcess.setStatus(EProcessStatus.RUNNING);
                scheduler.setCurrentProcess(newProcess);
                this.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, newProcess));
                break;
            case eProcessStarted:
                oldProcess = currentProcess;
                newProcess = interrupt.getProcess();
                if(oldProcess != null) oldProcess.setStatus(EProcessStatus.READY);
                newProcess.setStatus(EProcessStatus.RUNNING);
                this.scheduler.setCurrentProcess(newProcess);
                this.ui.addLog("ProcessStarted: " +newProcess.getProcessName()+"("+newProcess.getPid()+")");
                new Timer().schedule(new TimerTask() {@Override public void run() {addInterrupt(new Interrupt(EInterrupt.eTimeOut, newProcess));}
                        }, 2011);
                break;
            case eProcessTerminated:
                oldProcess = interrupt.getProcess();
                oldProcess.setStatus(EProcessStatus.TERMINATED);

                if(this.scheduler.getReadyQueue().isEmpty()) this.addInterrupt(new Interrupt(EInterrupt.eIdle, null));
                else this.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, this.scheduler.deReadyQueue()));
                this.ui.addLog("ProcessTerminated: " +oldProcess.getProcessName()+"("+oldProcess.getPid()+")");
                break;
            // TODO: 구현 필요
            case eIOStarted:
                break;
            case eIOTerminated:
                break;
        }

    }

    public void eIdle(){
        Process process = scheduler.deReadyQueue();
        if(process == null) return;
        addInterrupt(new Interrupt(EInterrupt.eProcessStarted, process));
    }

    public void eRunning(){

    }

    public void eTimeOut(){
        Process oldProcess = currentInterrupt.getProcess();
        this.ui.addLog("TimeOut: " +oldProcess.getProcessName()+"("+oldProcess.getPid()+")");
        Process newProcess = scheduler.deReadyQueue();
        if(newProcess == null) return;
        this.ui.addLog("");oldProcess.setStatus(EProcessStatus.READY);
        newProcess.setStatus(EProcessStatus.RUNNING);
        scheduler.setCurrentProcess(newProcess);
        this.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, newProcess));
    }

    public void eProcessStarted(){
        Process oldProcess = currentInterrupt.getProcess();
        Process newProcess = currentInterrupt.getProcess();
        if(oldProcess != null) oldProcess.setStatus(EProcessStatus.READY);
        newProcess.setStatus(EProcessStatus.RUNNING);
        this.scheduler.setCurrentProcess(newProcess);
        this.ui.addLog("ProcessStarted: " +newProcess.getProcessName()+"("+newProcess.getPid()+")");
        new Timer().schedule(new TimerTask() {@Override public void run() {addInterrupt(new Interrupt(EInterrupt.eTimeOut, newProcess));}
                }, 2011);
    }

    public void eProcessTerminated(){
        Process oldProcess = currentInterrupt.getProcess();
        oldProcess.setStatus(EProcessStatus.TERMINATED);

        if(this.scheduler.getReadyQueue().isEmpty()) this.addInterrupt(new Interrupt(EInterrupt.eIdle, null));
        else this.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, this.scheduler.deReadyQueue()));
        this.ui.addLog("ProcessTerminated: " +oldProcess.getProcessName()+"("+oldProcess.getPid()+")");
    }

    public void eIOStarted(){
        Process oldProcess = currentInterrupt.getProcess();
        Process newProcess = scheduler.deReadyQueue();
        if(newProcess == null) return;
        oldProcess.setStatus(EProcessStatus.WAITING);
        newProcess.setStatus(EProcessStatus.RUNNING);
        scheduler.setCurrentProcess(newProcess);
        this.ui.addLog("IOStarted: " +oldProcess.getProcessName()+"("+oldProcess.getPid()+")");
        this.ui.addLog("ProcessStarted: " +newProcess.getProcessName()+"("+newProcess.getPid()+")");
        new Timer().schedule(new TimerTask() {@Override public void run() {addInterrupt(new Interrupt(EInterrupt.eIOTerminated, oldProcess));}
                }, 5000);
    }

    public void eIOTerminated(){
        Process oldProcess = currentInterrupt.getProcess();
        oldProcess.setStatus(EProcessStatus.READY);
        this.ui.addLog("IOTerminated: " +oldProcess.getProcessName()+"("+oldProcess.getPid()+")");
        this.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, oldProcess));
    }

    private void invokeMethod(String name) {
        try {
            this.getClass().getMethod(name).invoke(this);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException |
                 SecurityException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
