import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Queue<T> extends Vector<T> {
    // 큐는 항상 공유영역이다.
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
            this.add(null); // vector의 element를 10개 잡아두기만 한것. 이제 이후엔 set만으로 queue안의 값을 통제
        }
    }

    // TODO: Exception handling 추가
    public void enqueue(T t){ // process를 넣지만 실제론 주소를 넣는다.
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
//            this.set(this.head, null); // 없어도 될듯
            this.head = (this.head + 1) % this.maxSize;
            this.currentSize--;
        }
        return t;
    }

    // TODO: Syncronized Queue를 후에 따로 만들것이다. (Queue를 상속받아 되도록 할 것)
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
}