import java.util.Vector;

public class Scheduler {
    // Variables
    private static final int MAX_NUM_PROCESS = 10;
    private boolean bPowerOn;


    private ProcessQueue readyQueue;
    private ProcessQueue waitQueue;
    private Process runningProcess; // 현재 서비스받고 있는 프로세스

    private InterruptHandler interruptHandler;

    // Getter & Setter
    // 외부에 노출시켜주고 이를 직접 조작하여 사용하겠다고 함.
    public ProcessQueue getReadyQueue(){return readyQueue;}
    public ProcessQueue getWaitQueue(){return waitQueue;}

    // Constructor
    public Scheduler(){
        this.bPowerOn = true;
        this.readyQueue = new ProcessQueue();
        this.waitQueue = new ProcessQueue();
        this.interruptHandler = new InterruptHandler();
        this.runningProcess = null; // TODO: eTimeout으로 셋팅해야한대.
    }



    public void run(){

        while(this.bPowerOn){
            // Check Interrupt
            this.interruptHandler.handle();
            // running
            if(this.runningProcess != null) continue;
            // Execute Process
//            this.cpu.executeInstruction(this.runningProcess);
        }
    }



    public static class ProcessQueue extends Vector<Process> {
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
}
q