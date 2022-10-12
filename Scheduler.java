public class Scheduler extends Thread{
    // Variables
    private boolean bPowerOn;


    private Process runningProcess; // 현재 서비스받고 있는 프로세스

    private InterruptHandler interruptHandler;

    // Critical Section ---------------------------------------
    private ProcessQueue readyQueue;
    private ProcessQueue waitQueue;
    // Getter & Setter
    // 외부에 노출시켜주고 이를 직접 조작하여 사용하겠다고 함.
    public ProcessQueue getReadyQueue(){return readyQueue;}
    public ProcessQueue getWaitQueue(){return waitQueue;}
    // --------------------------------------------------------

    // Constructor
    public Scheduler(){
        this.bPowerOn = true;
        this.readyQueue = new ProcessQueue();
        this.waitQueue = new ProcessQueue();
        this.interruptHandler = new InterruptHandler();
        this.runningProcess = null; // TODO: eTimeout으로 셋팅해야한대.
    }

    public synchronized void enReadyQueue(Process process){ // synchronized : Critical Section (한 스레드만 들어갈 수 있다)
        // critical Section 시작
        this.getReadyQueue().enqueue(process);
    }

    public synchronized Process deReadyQueue(){
        return this.getReadyQueue().dequeue();
    }

    public void run(){

        while(this.bPowerOn){
            // running
            if(this.runningProcess != null) {
                // Execute Process
//              this.cpu.executeInstruction(this.runningProcess);
                this.runningProcess.executeInstruction();
            };
            // Check Interrupt
//            this.interruptHandler.handle();
        }
    }


    public class InterruptHandler{
        // Interrupt는 종류가 다양해질수있기 때문에 객체로 만들어야 확장성이 좋다.

        public enum EInterrupt{ // OS Interrupt
            eTimeout,
            eIOStarted,
            eIOTerminated,
            eProcessTerminated,
            eProcessStarted,
        }
        class Interrupt {
            private EInterrupt eInterrupt;
            private Process process;

            public Interrupt(EInterrupt eInterrupt, Process process){
                this.eInterrupt = eInterrupt;
                this.process = process;
            }

            public EInterrupt getEInterrupt() {return eInterrupt;}
            public Process getProcess() {return process;}
        }

        // Critical Section ---------------------------------------
        private Interrupt interrupt;
        public EInterrupt geteInterrupt(){return interrupt.getEInterrupt();}
        public void seteInterrupt(Interrupt eInterrupt){this.interrupt = eInterrupt;}
        // --------------------------------------------------------


        public void handle(){
            // loader, timer, io, process가 인터럽트를 발생시킨다.
            switch (this.interrupt.getEInterrupt()) {
                case eProcessStarted -> // enqueue를
                    // TODO: 어떤 프로세스가 시작되었는지 파라미터로 줘야함.
                        handleProcessStart(this.interrupt.getProcess());
                case eProcessTerminated -> {
                    // TODO: 어떤 프로세스가 종료되었는지 파라미터로 줘야함.
                    handleProcessTerminated();
                    runningProcess = getReadyQueue().dequeue();
                }
                case eIOStarted -> {
                    getWaitQueue().enqueue(runningProcess);
                    runningProcess = getReadyQueue().dequeue();
                }
                case eIOTerminated -> handleIOTerminate(this.interrupt.getProcess());
                case eTimeout -> {
                    getReadyQueue().enqueue(runningProcess);
                    runningProcess = getReadyQueue().dequeue();
                }
                default -> {
                }
            }
        }
        // Queue가 비어있을 때 아무짓도 안할 때의 초기 프로세스를 가져오는 역할
        private void handleProcessStart(Process process){
            getReadyQueue().enqueue(process);
        }

        private void handleProcessTerminated(){
        }

        private void handleIOTerminate(Process process){
            getReadyQueue().enqueue(process);
        }

    }


}
