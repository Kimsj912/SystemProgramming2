package OS;

import Constants.ConstantData;
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
    private Object currentProcess;

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
        this.currentProcess = currentProcess;
        Interrupt interrupt = this.getInterrupt();
        if(interrupt == null || interrupt.getType() == null) return;

        currentInterrupt = interrupt;
        this.invokeMethod(interrupt.getType().name());
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
        Process newProcess = scheduler.deReadyQueue();


        if(newProcess == null) {
            if(oldProcess.getStatus().equals(EProcessStatus.TERMINATED)){
                scheduler.setCurrentProcess(null);
//                this.addInterrupt(new Interrupt(EInterrupt.eIdle, null));
                return;
            }
            scheduler.enReadyQueue(oldProcess);

            this.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, oldProcess));
            this.ui.addLog(oldProcess, "Timeout: " +oldProcess.getProcessName()+"("+oldProcess.getPid()+") -> reRunning");
        } else {
            oldProcess.setStatus(EProcessStatus.READY);

            scheduler.enReadyQueue(oldProcess);

            this.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, newProcess));
            this.ui.addLog(oldProcess, "TimeOut: " +oldProcess.getProcessName()+"("+oldProcess.getPid()+") -> "+newProcess.getProcessName()+"("+newProcess.getPid()+")");
        }
    }

    public void eProcessStarted(){
        Process oldProcess = currentInterrupt.getProcess();
        Process newProcess = currentInterrupt.getProcess();

//        if(oldProcess != null) oldProcess.setStatus(EProcessStatus.READY);
//        newProcess.setStatus(EProcessStatus.RUNNING);
        this.scheduler.setCurrentProcess(newProcess);
        this.ui.addLog(newProcess,"ProcessStarted: " +newProcess.getProcessName()+"("+newProcess.getPid()+")");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run(){
                addInterrupt(new Interrupt(EInterrupt.eTimeOut, newProcess));
            }
        }, Integer.parseInt(ConstantData.timeSlice.getText()));
    }

    public void eProcessTerminated(){
        Process oldProcess = currentInterrupt.getProcess();
        Process newProcess = scheduler.deReadyQueue();

//        oldProcess.setStatus(EProcessStatus.TERMINATED);
        if(newProcess == null) {
            this.ui.addLog(oldProcess, "ProcessTerminated: " +oldProcess.getProcessName()+"("+oldProcess.getPid()+")");
            scheduler.setCurrentProcess(null);
//            this.addInterrupt(new Interrupt(EInterrupt.eIdle, null));
        }else {
//            newProcess.setStatus(EProcessStatus.RUNNING);

            this.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, newProcess));
            this.ui.addLog(oldProcess, "ProcessTerminated: " + oldProcess.getProcessName() + "(" + oldProcess.getPid() + ") -> " + newProcess.getProcessName() + "(" + newProcess.getPid() + ")");
        }
    }

    public void eIOStarted(){
        Process oldProcess = currentInterrupt.getProcess();
        Process newProcess = scheduler.deReadyQueue();

        scheduler.enWaitQueue(oldProcess);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                addInterrupt(new Interrupt(EInterrupt.eIOTerminated, oldProcess));}
        }, Integer.parseInt(ConstantData.ioInterrupt.getText()));

        this.scheduler.setCurrentProcess(newProcess);
        if(newProcess == null) {
//            this.addInterrupt(new Interrupt(EInterrupt.eIdle, null));
            this.ui.addLog(oldProcess, "IOStarted: " +oldProcess.getProcessName()+"("+oldProcess.getPid()+") -> reRunning");
        } else {
            this.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, newProcess));
            this.ui.addLog(oldProcess, "IOStarted: " +oldProcess.getProcessName()+"("+oldProcess.getPid()+") -> "+newProcess.getProcessName()+"("+newProcess.getPid()+")");
        }
    }

    public void eIOTerminated(){
        Process oldProcess = currentInterrupt.getProcess();

        Process newProcess;
        while((newProcess = scheduler.deWaitQueue()) != oldProcess){
            scheduler.enWaitQueue(newProcess);
        }
        oldProcess.setStatus(EProcessStatus.READY);
        scheduler.enReadyQueue(oldProcess);
        this.ui.addLog(oldProcess,"IOTerminated: " +oldProcess.getProcessName()+"("+oldProcess.getPid()+")");
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
