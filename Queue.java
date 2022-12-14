import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Queue<T> extends Vector<T> {
    // ť�� �׻� ���������̴�.
    private final Semaphore emptySemaphoreReady;
    private final Semaphore fullSemaphoreReady;

    private static final int MAX_NUM_PROCESS = 10;
    private int head, tail, maxSize, currentSize;
    public Queue() throws InterruptedException{
        this.maxSize = MAX_NUM_PROCESS;
        this.currentSize = 0;
        this.head = 0;
        this.tail = 0;

        this.emptySemaphoreReady = new Semaphore(MAX_NUM_PROCESS,true);
        this.fullSemaphoreReady = new Semaphore(MAX_NUM_PROCESS, true);
        this.emptySemaphoreReady.acquire(MAX_NUM_PROCESS);

        for(int i=0;i<maxSize;i++){
            this.add(null); // vector�� element�� 10�� ��Ƶα⸸ �Ѱ�. ���� ���Ŀ� set������ queue���� ���� ����
        }
    }

    // TODO: Exception handling �߰�
    public void enqueue(T t){ // process�� ������ ������ �ּҸ� �ִ´�.
        if(this.currentSize < this.maxSize) {
            this.set(this.tail, t);
            this.tail = (this.tail + 1) % this.maxSize;
            this.currentSize++;
        }
    }

    public T dequeue(){
        T t = null;
        if(this.currentSize > 0) {
            t = this.get(this.head);
//            this.set(this.head, null); // ��� �ɵ�
            this.head = (this.head + 1) % this.maxSize;
            this.currentSize--;
        }
        return t;
    }

    // TODO: Syncronized Queue�� �Ŀ� ���� ������̴�. (Queue�� ��ӹ޾� �ǵ��� �� ��)
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
}