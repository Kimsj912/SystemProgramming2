import java.util.Vector;

public class Scheduler {
    private static final int MAX_NUM_PROCESS = 10;
    private boolean bPowerOn;
    private ProcessQueue readyQueue;
    private ProcessQueue waitQueue;
    private Process runningProcess; // 현재 서비스받고 있는 프로세스

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
        this.runningProcess = null; // TODO: eTimeout으로 셋팅해야한대.
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
        // Interrupt는 종류가 다양해질수있기 때문에 객체로 만들어야 확장성이 좋다.
        public void handle(){
            // loader, timer, io, process가 인터럽트를 발생시킨다.
            EInterrupt eInterrupt = cpu.checkInterrupt();
            switch (eInterrupt){
                case eProcessStarted:
                    // TODO: 어떤 프로세스가 시작되었는지 파라미터로 줘야함.
                    handleProcessStart();
                    break;
                case eProcessTerminated:
                    // TODO: 어떤 프로세스가 종료되었는지 파라미터로 줘야함.
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
        // Queue가 비어있을 때 아무짓도 안할 때의 초기 프로세스를 가져오는 역할
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
            // TODO: handlerProcessStart()와 동일한 내용이 있음. 이걸 합쳐도 좋을 것 같다.
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
