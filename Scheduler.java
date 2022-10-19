import java.util.concurrent.Semaphore;

public class Scheduler extends Thread{
    // Constants
    private final static int MAX_READY_PERMITS = 4;

    // Variables
    private boolean bPowerOn;
    private final Semaphore emptySemaphoreReady;
    private final Semaphore fullSemaphoreReady;


    public Process runningProcess; // ���� ���񽺹ް� �ִ� ���μ���

    private InterruptHandler interruptHandler;

    // Critical Section ---------------------------------------
    private Queue<Process> readyQueue;
    private Queue<Process> waitQueue;
    // Getter & Setter
    // �ܺο� ��������ְ� �̸� ���� �����Ͽ� ����ϰڴٰ� ��.
    public Queue<Process> getReadyQueue(){return readyQueue;}
    public Queue<Process> getWaitQueue(){return waitQueue;}
    // --------------------------------------------------------

    // Constructor
    public Scheduler(){
        this.bPowerOn = true;
        this.readyQueue = new Queue<Process>();
        this.waitQueue = new Queue<Process>();
        this.interruptHandler = new InterruptHandler(this);
        this.runningProcess = null; // TODO: eTimeout���� �����ؾ��Ѵ�.

        try {
            emptySemaphoreReady = new Semaphore(MAX_READY_PERMITS, true);
            emptySemaphoreReady.acquire(MAX_READY_PERMITS);
            fullSemaphoreReady = new Semaphore(MAX_READY_PERMITS, true);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void enReadyQueue(Process process){ // synchronized : Critical Section (�� �����常 �� �� �ִ�)
        // critical Section ����
        try { // euqueue�� �Ϸ��ߴµ� fullsemaphore�� �����ð� ������ ����. �׷��� emptySemaphore�� ��ť�� �Ǹ� Ǯ��.
            this.fullSemaphoreReady.acquire();
            this.getReadyQueue().enqueue(process);
            this.emptySemaphoreReady.release();
        } catch (InterruptedException e) { // acquire�Ѱ� ������ block�Ǿ����. empty semaphore�� �� �����ִµ� �ϳ��� Ǯ���ְ� �ǵ�°�.
            throw new RuntimeException(e);
        }
    }

    public synchronized Process deReadyQueue(){
        Process process = null;
        try{ // dequeue�� �Ϸ��ߴµ� emptySemaphore�� �����ð� ������ ����. �׷��� fullSemaphore�� dequeue�� �Ǹ� Ǯ��.
            this.emptySemaphoreReady.acquire(); // ���°� �ִ��� ���.
            process = this.getReadyQueue().dequeue();
            this.fullSemaphoreReady.release(); // ���°� ������ �ϳ��� Ǯ����.
        } catch(InterruptedException e){
            throw new RuntimeException(e);
        }
        return process;
    }

    public void run(){

        while(this.bPowerOn){
            this.runningProcess = this.deReadyQueue(); // dequeue�� ó���� �ϰ� runningProcess�� ��������.
            boolean result = this.runningProcess.executeInstruction();
            this.interruptHandler.handle();
        }
    }


}
