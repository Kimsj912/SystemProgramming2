import java.util.Vector;

public class Scheduler extends Thread{
    // Attributes
    private boolean bPowerOn;
    // Components
    private InterruptHandler interruptHandler;

    // Shared Variables
    private Queue<Process> readyQueue;
    private Queue<Process> waitQueue;
    private Queue<Interrupt> interruptQueue;
    private Queue<FileIOCommand> fileIOCommandQueue;
    private Vector<Integer> fileSystemBuffer;

    // Working Variables
    public Process runningProcess;

    // Constructor
    public Scheduler(Queue<Process> readyQueue, Queue<Process> waitingQueue, Queue<Interrupt> interruptQueue, Queue<FileIOCommand> fileIOCommandQueue){
        this.readyQueue = readyQueue;
        this.waitQueue = waitingQueue;
        this.interruptQueue = interruptQueue;
        this.fileIOCommandQueue = fileIOCommandQueue;
        this.fileSystemBuffer = fileSystemBuffer;

        this.interruptHandler = new InterruptHandler();
        this.runningProcess = null; // TODO: eTimeout으로 셋팅해야한대.

        this.bPowerOn = true;
    }

    // Getter & Setter
    // 외부에 노출시켜주고 이를 직접 조작하여 사용하겠다고 함.
    public Queue<Process> getReadyQueue(){return readyQueue;}
    public Queue<Process> getWaitQueue(){return waitQueue;}


    public void run(){
        while(this.bPowerOn){
            this.runningProcess = this.readyQueue.dequeue(); // semaphore때문에 들어올때까지 블락킹된다.
            boolean result = this.runningProcess.executeInstruction();
            this.interruptHandler.handle();
        }
    }


}
