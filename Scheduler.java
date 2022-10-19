import java.util.concurrent.Semaphore;

public class Scheduler extends Thread{
    // Constants
    private final static int MAX_READY_PERMITS = 4;

    // Variables
    private boolean bPowerOn;
    private final Semaphore emptySemaphoreReady;
    private final Semaphore fullSemaphoreReady;


    public Process runningProcess; // 현재 서비스받고 있는 프로세스

    private InterruptHandler interruptHandler;

    // Critical Section ---------------------------------------
    private Queue<Process> readyQueue;
    private Queue<Process> waitQueue;
    // Getter & Setter
    // 외부에 노출시켜주고 이를 직접 조작하여 사용하겠다고 함.
    public Queue<Process> getReadyQueue(){return readyQueue;}
    public Queue<Process> getWaitQueue(){return waitQueue;}
    // --------------------------------------------------------

    // Constructor
    public Scheduler(){
        this.bPowerOn = true;
        this.readyQueue = new Queue<Process>();
        this.waitQueue = new Queue<Process>();
        this.interruptHandler = new InterruptHandler(this);
        this.runningProcess = null; // TODO: eTimeout으로 셋팅해야한대.

        try {
            emptySemaphoreReady = new Semaphore(MAX_READY_PERMITS, true);
            emptySemaphoreReady.acquire(MAX_READY_PERMITS);
            fullSemaphoreReady = new Semaphore(MAX_READY_PERMITS, true);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void enReadyQueue(Process process){ // synchronized : Critical Section (한 스레드만 들어갈 수 있다)
        // critical Section 시작
        try { // euqueue를 하려했는데 fullsemaphore가 가져올게 없으면 블럭됨. 그러다 emptySemaphore에 인큐가 되면 풀림.
            this.fullSemaphoreReady.acquire();
            this.getReadyQueue().enqueue(process);
            this.emptySemaphoreReady.release();
        } catch (InterruptedException e) { // acquire한게 없으면 block되어버림. empty semaphore가 다 막혀있는데 하나를 풀어주게 ㅗ디는것.
            throw new RuntimeException(e);
        }
    }

    public synchronized Process deReadyQueue(){
        Process process = null;
        try{ // dequeue를 하려했는데 emptySemaphore가 가져올게 없으면 블럭됨. 그러다 fullSemaphore에 dequeue가 되면 풀림.
            this.emptySemaphoreReady.acquire(); // 들어온게 있는지 물어봄.
            process = this.getReadyQueue().dequeue();
            this.fullSemaphoreReady.release(); // 들어온게 있으면 하나를 풀어줌.
        } catch(InterruptedException e){
            throw new RuntimeException(e);
        }
        return process;
    }

    public void run(){

        while(this.bPowerOn){
            this.runningProcess = this.deReadyQueue(); // dequeue의 처리를 믿고 runningProcess를 돌려보자.
            boolean result = this.runningProcess.executeInstruction();
            this.interruptHandler.handle();
        }
    }


}
