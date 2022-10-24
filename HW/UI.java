package HW;

import Constants.ConstantData;
import Elements.Process;
import OS.InterruptHandler;
import OS.Loader;
import OS.Scheduler;
import View.UIView;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;

public class UI extends Thread {
    // Association
    private CPU cpu;
    private Storage storage;
    private InterruptHandler interruptHandler;
    private Scheduler scheduler;
    private Loader loader;

    // Attributes
    private UIView osSimulator;
    private HashMap<Process, Color> colors;

    public UI(){
        osSimulator = new UIView();
        colors = new HashMap<>();
    }

    public void initialize(CPU cpu, Storage storage){
        this.cpu = cpu;
        this.storage = storage;
    }

    public void initialize(Loader loader, Scheduler scheduler, InterruptHandler interruptHandler){
        this.loader = loader;
        this.scheduler = scheduler;
        this.interruptHandler = interruptHandler;

        this.osSimulator.initialize(loader, scheduler, interruptHandler, storage);
    }

    public void run(){
        osSimulator.setVisible(true);
        while (true) {
            try {
                updateUI();
                Thread.sleep(Integer.parseInt(ConstantData.moniter.getText()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUI(){
        LinkedList<Process> copyReadyQueue = new LinkedList<>();
        copyReadyQueue.addAll(scheduler.getReadyQueue());
        LinkedList<Process> copyWaitQueue = new LinkedList<>();
        copyWaitQueue.addAll(scheduler.getWaitingQueue());
        osSimulator.setReadyQueuePanel(copyReadyQueue);
        osSimulator.setWaitingQueuePanel(copyWaitQueue);
        osSimulator.setCurrentProcess((scheduler.getCurrentProcess() == null)? "" : scheduler.getCurrentProcess().getProcessName());
        osSimulator.updateProgramList();
    }

    public void addLog(Process process, String log){
        if(!colors.containsKey(process)) colors.put(process, new Color((int)(Math.random()*0x1000000)));
        osSimulator.addLog(log, colors.get(process));
    }

    public void addInstructionLog(Process process, String log){
        if(!colors.containsKey(process)) colors.put(process, new Color((int)(Math.random() * 0x1000000)));
        osSimulator.addInstructionLog(log, colors.get(process));
    }
}