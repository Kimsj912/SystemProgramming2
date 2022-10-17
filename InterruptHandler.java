public class InterruptHandler {
    // Interrupt는 종류가 다양해질수있기 때문에 객체로 만들어야 확장성이 좋다.

    public enum EInterrupt { // OS Interrupt
        eTimeout,
        eIOStarted,
        eIOTerminated,
        eProcessTerminated,
        eProcessStarted,
    }

    private final Scheduler scheduler;

    class Interrupt {
        private EInterrupt eInterrupt;
        private Process process;

        public Interrupt(EInterrupt eInterrupt, Process process){
            this.eInterrupt = eInterrupt;
            this.process = process;
        }

        public EInterrupt getEInterrupt(){
            return eInterrupt;
        }

        public Process getProcess(){
            return process;
        }
    }

    // Critical Section ---------------------------------------
    private Queue<Interrupt> interruptQ;
    public Interrupt get() {
        return this.interruptQ.dequeue();
    }
    public void set(Interrupt interrupt){
        this.interruptQ.enqueue(interrupt);
    }

    // --------------------------------------------------------

    // Constructor
    public InterruptHandler(Scheduler scheduler){
        this.interruptQ = new Queue<Interrupt>();
        this.scheduler = scheduler;
    }



    public void handle(){
        Interrupt interrupt = this.get();
        if(interrupt == null) return;
        switch (interrupt.getEInterrupt()) {
            case eProcessStarted ->
                // TODO: 어떤 프로세스가 시작되었는지 파라미터로 줘야함.
                    handleProcessStart(interrupt.getProcess());
            case eProcessTerminated -> {
                // TODO: 어떤 프로세스가 종료되었는지 파라미터로 줘야함.
                handleProcessTerminated();
                scheduler.runningProcess = scheduler.getReadyQueue().dequeue();
            }
            case eIOStarted -> {
                scheduler.getWaitQueue().enqueue(scheduler.runningProcess);
                scheduler.runningProcess = scheduler.getReadyQueue().dequeue();
            }
            case eIOTerminated -> handleIOTerminate(interrupt.getProcess());
            case eTimeout -> {
                scheduler.getReadyQueue().enqueue(scheduler.runningProcess);
                scheduler.runningProcess = scheduler.getReadyQueue().dequeue();
            }
            default -> {
            }
        }
    }

    // Queue가 비어있을 때 아무짓도 안할 때의 초기 프로세스를 가져오는 역할
    private void handleProcessStart(Process process){
        scheduler.getReadyQueue().enqueue(process);
    }

    private void handleProcessTerminated(){
    }

    private void handleIOTerminate(Process process){
        scheduler.getReadyQueue().enqueue(process);
    }

}
