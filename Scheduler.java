public class Scheduler extends Thread{
    // Variables
    private boolean bPowerOn;


    public Process runningProcess; // 현재 서비스받고 있는 프로세스

    private InterruptHandler interruptHandler;

    // Critical Section ---------------------------------------
    private Queue<Process> readyQueue;
    private Queue<Process> waitQueue;
    // Getter & Setter
    // 외부에 노출시켜주고 이를 직접 조작하여 사용하겠다고 함.
    public Queue<Process> getReadyQueue(){return readyQueue;}
    public Queue<Process> getWaitQueue(){return waitQueue;}
    // --------------------------------------------------------

    // Constructor
    public Scheduler(){
        this.bPowerOn = true;
        this.readyQueue = new Queue<Process>();
        this.waitQueue = new Queue<Process>();
        this.interruptHandler = new InterruptHandler(this);
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
                // Execute Process (원래는 인터럽트에서 해야하는 일이다)
                boolean result = this.runningProcess.executeInstruction();
                if(!result) this.runningProcess = this.deReadyQueue();
            } else {
                this.runningProcess = this.deReadyQueue(); // critical section이라 해당 함수로만 가져와야한다.
            }
            // Check Interrupt
//            this.interruptHandler.handle();
        }
    }


}
