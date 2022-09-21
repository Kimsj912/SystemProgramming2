import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Vector;

public class Scheduler {
    private static final int MAX_NUM_PROCESS = 10;
    private boolean bPowerOn;
    private ProcessQueue processQueue;
    private Process runningProcess; // 현재 서비스받고 있는 프로세스

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
                    // timeout하면 컨텍스트 스위칭을 할고다.
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
