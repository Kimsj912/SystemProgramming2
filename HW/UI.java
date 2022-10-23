package HW;

import Elements.Program;
import OS.InterruptHandler;
import OS.Loader;
import OS.Scheduler;
import View.OSSimulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UI extends Thread {

    private CPU cpu;
    private Storage storage;
    private InterruptHandler interruptHandler;
    private Scheduler scheduler;
    private Loader loader;

    private BufferedReader br;

    private OSSimulator osSimulator;

    public UI(){
        br = new BufferedReader(new InputStreamReader(System.in));
        osSimulator = new OSSimulator();

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
            updateUI();
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            System.out.print("Enter the program name to load.\n>> ");
//            try {
//                String programName = br.readLine();
//                Program program = storage.getProgram(programName);
//                if(program == null) throw new IOException("Program not found.");
//                loader.load(program);
//            } catch (IOException e) {
//                System.out.println("The Program does not exist.");
//                System.out.println(e.getMessage());
//            }
        }
    }

    private void updateUI(){
        osSimulator.updateQueue(scheduler.getReadyQueue(), scheduler.getWaitingQueue(), scheduler.getCurrentProcess());
        osSimulator.updateProgramList();
    }

    public void addLog(String log){
        osSimulator.addLog(log);
    }

    public void addInstructionLog(String log){
        osSimulator.addInstructionLog(log);
    }

}