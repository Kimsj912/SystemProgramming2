import java.util.Vector;

public class ProcessQueue extends Vector<Process> {
    private static final int MAX_NUM_PROCESS = 10;
    private int head, tail, maxSize, currentSize;
    public ProcessQueue(){
        this.maxSize = MAX_NUM_PROCESS;
        this.currentSize = 0;
        this.head = 0;
        this.tail = 0;

        for(int i=0;i<maxSize;i++){
            this.add(null); // vector의 element를 10개 잡아두기만 한것. 이제 이후엔 set만으로 queue안의 값을 통제
        }
    }

    // TODO: Exception handling 추가
    public void enqueue(Process process){ // process를 넣지만 실제론 주소를 넣는다.
        if(this.currentSize < this.maxSize) {
            this.set(this.tail, process);
            this.tail = (this.tail + 1) % this.maxSize;
            this.currentSize++;
        }
    }

    public Process dequeue(){
        Process process = null;
        if(this.currentSize > 0) {
            process = this.get(this.head);
//            this.set(this.head, null); // 없어도 될듯
            this.head = (this.head + 1) % this.maxSize;
            this.currentSize--;
        }
        return process;
    }
}