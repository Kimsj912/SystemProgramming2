public class Interrupt {
    enum EInterrupt { // OS Interrupt
        eTimeout,
        eIOStarted,
        eIOTerminated,
        eProcessTerminated,
        eProcessStarted,
    }


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