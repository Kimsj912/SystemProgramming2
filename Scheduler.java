import java.util.Vector;

public class Scheduler {
    private static final int MAX_NUM_PROCESS = 10;
    private boolean bPowerOn;
    private ProcessQueue readyQueue;
    private ProcessQueue waitQueue;
    private Process runningProcess; // ���� ���񽺹ް� �ִ� ���μ���

    private InterruptHandler interruptHandler;


    public enum EInterrupt{ // OS Interrupt
        eTimeout,
        eIOStarted,
        eIOTerminated,
        eProcessTerminated,
        eProcessStarted,
    }

    public Scheduler(){
        this.bPowerOn = true;
        this.readyQueue = new ProcessQueue();
        this.waitQueue = new ProcessQueue();
        this.interruptHandler = new InterruptHandler();
        this.runningProcess = null; // TODO: eTimeout���� �����ؾ��Ѵ�.
    }


    public void enqueueReadyQueue(Process process){
        this.readyQueue.enqueue(process);
    }
    public void enqueueWaitQueue(Process process){
        this.waitQueue.enqueue(process);
    }
    public Process enqueueReadyDequeue(Process process){
        return this.readyQueue.dequeue();
    }
    public Process enqueueWaitDequeue(Process process){
        return this.waitQueue.dequeue();
    }

    public void run(){

        while(this.bPowerOn){
            // Check Interrupt
            this.interruptHandler.handle();
            // running
            if(this.runningProcess != null) continue;
            // Execute Process
            this.cpu.executeInstruction(this.runningProcess);
        }
    }

    private class InterruptHandler{
        // Interrupt�� ������ �پ��������ֱ� ������ ��ü�� ������ Ȯ�强�� ����.
        public void handle(){
            // loader, timer, io, process�� ���ͷ�Ʈ�� �߻���Ų��.
            EInterrupt eInterrupt = cpu.checkInterrupt();
            switch (eInterrupt){
                case eProcessStarted:
                    // TODO: � ���μ����� ���۵Ǿ����� �Ķ���ͷ� �����.
                    handleProcessStart();
                    break;
                case eProcessTerminated:
                    // TODO: � ���μ����� ����Ǿ����� �Ķ���ͷ� �����.
                    handleProcessTerminated();
                    break;
                case eIOStarted:
                    handleIOStart();
                    break;
                case eIOTerminated:
                    handleIOTerminate();
                    break;
                case eTimeout:
                    handleTimeout();
                    break;
                default:
                    break;
            }
        }
        // Queue�� ������� �� �ƹ����� ���� ���� �ʱ� ���μ����� �������� ����
        private void handleProcessStart(){
            if(!readyQueue.isEmpty()){
                // get next process
                runningProcess = readyQueue.dequeue();
                // switching context
                cpu.setContext(runningProcess);
            }
        }

        private void handleProcessTerminated(){
        }

        private void handleIOStart(){
        }

        private void handleIOTerminate(){
        }

        private void handleTimeout(){
            // TODO: handlerProcessStart()�� ������ ������ ����. �̰� ���ĵ� ���� �� ����.
            if(runningProcess != null){
                // 1) save current cpu context to runningProcess
                runningProcess.setContext(cpu.getContext());
                // 2) enqueue to the end
                readyQueue.enqueue(runningProcess);
            }
            // 3) get next process
            runningProcess = readyQueue.dequeue();
            // 4) switching context
            cpu.setContext(runningProcess);
        }


    }

    private static class ProcessQueue extends Vector<Process> {
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
