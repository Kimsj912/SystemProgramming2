import java.util.Vector;

public class Scheduler {
    // Variables
    private static final int MAX_NUM_PROCESS = 10;
    private boolean bPowerOn;


    private ProcessQueue readyQueue;
    private ProcessQueue waitQueue;
    private Process runningProcess; // ���� ���񽺹ް� �ִ� ���μ���

    private InterruptHandler interruptHandler;

    // Getter & Setter
    // �ܺο� ��������ְ� �̸� ���� �����Ͽ� ����ϰڴٰ� ��.
    public ProcessQueue getReadyQueue(){return readyQueue;}
    public ProcessQueue getWaitQueue(){return waitQueue;}

    // Constructor
    public Scheduler(){
        this.bPowerOn = true;
        this.readyQueue = new ProcessQueue();
        this.waitQueue = new ProcessQueue();
        this.interruptHandler = new InterruptHandler();
        this.runningProcess = null; // TODO: eTimeout���� �����ؾ��Ѵ�.
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
                this.add(null); // vector�� element�� 10�� ��Ƶα⸸ �Ѱ�. ���� ���Ŀ� set������ queue���� ���� ����
            }
        }

        // TODO: Exception handling �߰�
        public void enqueue(Process process){ // process�� ������ ������ �ּҸ� �ִ´�.
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
//            this.set(this.head, null); // ��� �ɵ�
                this.head = (this.head + 1) % this.maxSize;
                this.currentSize--;
            }
            return process;
        }
    }
}
q