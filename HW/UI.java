package HW;

import Elements.Process;
import Elements.Program;
import OS.InterruptHandler;
import OS.Loader;
import OS.Scheduler;
import View.OSSimulator;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class UI extends Thread {
    // Association
    private CPU cpu;
    private Storage storage;
    private InterruptHandler interruptHandler;
    private Scheduler scheduler;
    private Loader loader;

    // Attributes
    private OSSimulator osSimulator;
    private HashMap<Process, Color> colors;

    public UI(){
        osSimulator = new OSSimulator();
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
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUI(){
        osSimulator.updateQueue(scheduler.getReadyQueue(), scheduler.getWaitingQueue(), scheduler.getCurrentProcess());
        osSimulator.updateProgramList();
    }

    public void addLog(String log){
        osSimulator.addLog(log);
    }

    public void addInstructionLog(Process process, String log){
        if(!colors.containsKey(process)){
            colors.put(process, new Color((int)(Math.random() * 0x1000000)));
        }
        osSimulator.addInstructionLog(log, colors.get(process));
    }

}