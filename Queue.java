import java.util.Vector;

public class Queue<T> extends Vector<T> {
    private static final int MAX_NUM_PROCESS = 10;
    private int head, tail, maxSize, currentSize;
    public Queue(){
        this.maxSize = MAX_NUM_PROCESS;
        this.currentSize = 0;
        this.head = 0;
        this.tail = 0;

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
}