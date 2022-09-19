// CPU는 명령을 주면 처리하는 수동적인 부분이다.
public class CPU {
    // Variables
    public enum EInterrupt{
        eNone,
        eTimeout,
        eIOStarted,
        eIOCompleted,
    }

    public boolean switchOn;
    private Process process;
    private EInterrupt interrupt;

    // Constructor
    public CPU(){
        this.interrupt = EInterrupt.eNone;
    }

    public void setSwitch(boolean switchOn){
        this.switchOn = switchOn;
    }

    public  void run(){
        while(switchOn){
            // CPU가 돌아가는 코드.
            process.execute();
            this.processInterrupt();


        }
    }

    /** process를 불러오는 함수 */
    void loadProcess(Process process){
        this.process = process;
    }

    /** interrupt를 처리하는 함수. */
    private void processInterrupt(){
        switch (interrupt){
            case eTimeout:
                // timeout
                break;
            case eIOCompleted:
                // io completed
                break;
            case eIOStarted:
                // io interrupt
                break;
            case eNone:
                // interrupt가 없다.:
                break;
            default:
                break;
        }
    }
}
