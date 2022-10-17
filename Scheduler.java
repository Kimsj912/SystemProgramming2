public class Scheduler extends Thread{
    // Variables
    private boolean bPowerOn;


    public Process runningProcess; // ���� ���񽺹ް� �ִ� ���μ���

    private InterruptHandler interruptHandler;

    // Critical Section ---------------------------------------
    private Queue<Process> readyQueue;
    private Queue<Process> waitQueue;
    // Getter & Setter
    // �ܺο� ��������ְ� �̸� ���� �����Ͽ� ����ϰڴٰ� ��.
    public Queue<Process> getReadyQueue(){return readyQueue;}
    public Queue<Process> getWaitQueue(){return waitQueue;}
    // --------------------------------------------------------

    // Constructor
    public Scheduler(){
        this.bPowerOn = true;
        this.readyQueue = new Queue<Process>();
        this.waitQueue = new Queue<Process>();
        this.interruptHandler = new InterruptHandler(this);
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
                // Execute Process (������ ���ͷ�Ʈ���� �ؾ��ϴ� ���̴�)
                boolean result = this.runningProcess.executeInstruction();
                if(!result) this.runningProcess = this.deReadyQueue();
            } else {
                this.runningProcess = this.deReadyQueue(); // critical section�̶� �ش� �Լ��θ� �����;��Ѵ�.
            }
            // Check Interrupt
//            this.interruptHandler.handle();
        }
    }


}
