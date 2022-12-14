import java.util.Scanner;
import java.util.Vector;

public class Main {
    private boolean bPowerOn;
    public Main(){
        this.bPowerOn = true;

    }

    public void initialize(){

    }
    public void run() throws InterruptedException{
        // scheduler에 들어갈 애들임.
        Queue<Process> readyQueue = new Queue<>();
        Queue<Process> waitingQueue = new Queue<>();
        Queue<Interrupt> interruptQueue = new Queue<>();
        Queue<FileIOCommand> fileIOCommandQueue = new Queue<>(); // File System의 buffer와 같음
        Vector<Integer> fileSystemBuffer = new Vector<>();

        Scheduler scheduler = new Scheduler(readyQueue, waitingQueue, interruptQueue, fileIOCommandQueue);
        scheduler.start();

        UI ui = new UI(readyQueue);
        ui.start(); // ui를 Thread로 만들어서 돌린다.

        FileSystem fileSystem = new FileSystem(interruptQueue, fileSystemBuffer);
        fileSystem.start();
    }

    public void finish(){

    }
    public static void main(String[] args){
        Main main = new Main();
        main.initialize();
        main.run();
        main.finish();
    }


}
