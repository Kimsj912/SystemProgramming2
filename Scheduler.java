import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Vector;

public class Scheduler {
    private static final int MAX_NUM_PROCESS = 10;
    private boolean bPowerOn;
    private ProcessQueue processQueue;
    private Process runningProcess; // ���� ���񽺹ް� �ִ� ���μ���

    public enum EInterrupt{ // OS Interrupt
        eTimeout,
        eIOStarted,
        eIOCompleted,
    }

    public Scheduler(){
        this.bPowerOn = true;
        this.processQueue = new ProcessQueue();
        this.runningProcess = null;
    }

    static class InterruptHandelr{
        public void processInterrupt(EInterrupt eInterrupt){
            switch (eInterrupt){
                case eTimeout:
                    // timeout�ϸ� ���ؽ�Ʈ ����Ī�� �Ұ��.
                    break;
                case eIOStarted:

                    break;
                case eIOCompleted:

                    break;
            }
        }
    }

    public void run(){
        while(this.bPowerOn){
            // Context Switching
            this.runningProcess.setContext(this.cpu.getContext());
            this.runningProcess = this.processQueue.dequeue();
            this.cpu.setContext(this.runningProcess.getContext());
            // Check Interrupt
//            while(this.interrupt.getStatus()){
//                this.cpu.executeInstruction(this.runningProcess);
//            }

        }
    }


    private class ProcessQueue extends Vector<Process> {
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
