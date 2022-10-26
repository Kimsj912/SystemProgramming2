public class Scheduler extends Thread{
    // Variables
    private boolean bPowerOn;


    private Process runningProcess; // ���� ���񽺹ް� �ִ� ���μ���

    private InterruptHandler interruptHandler;

    // Critical Section ---------------------------------------
    private ProcessQueue readyQueue;
    private ProcessQueue waitQueue;
    // Getter & Setter
    // �ܺο� ��������ְ� �̸� ���� �����Ͽ� ����ϰڴٰ� ��.
    public ProcessQueue getReadyQueue(){return readyQueue;}
    public ProcessQueue getWaitQueue(){return waitQueue;}
    // --------------------------------------------------------

    // Constructor
    public Scheduler(){
        this.bPowerOn = true;
        this.readyQueue = new ProcessQueue();
        this.waitQueue = new ProcessQueue();
        this.interruptHandler = new InterruptHandler();
        this.runningProcess = null; // TODO: eTimeout���� �����ؾ��Ѵ�.
    }

    public synchronized void enReadyQueue(Process process){ // synchronized : Critical Section (�� �����常 �� �� �ִ�)
        // critical Section ����
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
        // Interrupt�� ������ �پ��������ֱ� ������ ��ü�� ������ Ȯ�强�� ����.

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
            // loader, timer, io, process�� ���ͷ�Ʈ�� �߻���Ų��.
            switch (this.interrupt.getEInterrupt()) {
                case eProcessStarted -> // enqueue��
                    // TODO: � ���μ����� ���۵Ǿ����� �Ķ���ͷ� �����.
                        handleProcessStart(this.interrupt.getProcess());
                case eProcessTerminated -> {
                    // TODO: � ���μ����� ����Ǿ����� �Ķ���ͷ� �����.
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
        // Queue�� ������� �� �ƹ����� ���� ���� �ʱ� ���μ����� �������� ����
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
