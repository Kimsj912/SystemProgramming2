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
}