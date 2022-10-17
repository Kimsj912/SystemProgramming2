public class InterruptHandler {
    // Interrupt�� ������ �پ��������ֱ� ������ ��ü�� ������ Ȯ�强�� ����.

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
                // TODO: � ���μ����� ���۵Ǿ����� �Ķ���ͷ� �����.
                    handleProcessStart(interrupt.getProcess());
            case eProcessTerminated -> {
                // TODO: � ���μ����� ����Ǿ����� �Ķ���ͷ� �����.
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

    // Queue�� ������� �� �ƹ����� ���� ���� �ʱ� ���μ����� �������� ����
    private void handleProcessStart(Process process){
        scheduler.getReadyQueue().enqueue(process);
    }

    private void handleProcessTerminated(){
    }

    private void handleIOTerminate(Process process){
        scheduler.getReadyQueue().enqueue(process);
    }

}
