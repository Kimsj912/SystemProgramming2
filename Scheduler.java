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
        this.runningProcess = null; // TODO: eTimeout���� �����ؾ��Ѵ�.

        this.bPowerOn = true;
    }

    // Getter & Setter
    // �ܺο� ��������ְ� �̸� ���� �����Ͽ� ����ϰڴٰ� ��.
    public Queue<Process> getReadyQueue(){return readyQueue;}
    public Queue<Process> getWaitQueue(){return waitQueue;}


    public void run(){
        while(this.bPowerOn){
            this.runningProcess = this.readyQueue.dequeue(); // semaphore������ ���ö����� ���ŷ�ȴ�.
            boolean result = this.runningProcess.executeInstruction();
            this.interruptHandler.handle();
        }
    }


}
