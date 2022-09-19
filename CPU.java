// CPU�� ����� �ָ� ó���ϴ� �������� �κ��̴�.
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
            // CPU�� ���ư��� �ڵ�.
            process.execute();
            this.processInterrupt();


        }
    }

    /** process�� �ҷ����� �Լ� */
    void loadProcess(Process process){
        this.process = process;
    }

    /** interrupt�� ó���ϴ� �Լ�. */
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
                // interrupt�� ����.:
                break;
            default:
                break;
        }
    }
}
