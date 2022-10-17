import Enums.EInterrupt;

public class Interrupt {
    private EInterrupt type;
    private Process process;

    public Interrupt(EInterrupt type, Process process){
        this.type = type;
        this.process = process;
    }

    // Getter & Setter
    public Process getProcess(){return process;}
    public void setProcess(Process process){this.process = process;}
    public EInterrupt getType(){return type;}
    public void setType(EInterrupt type){this.type = type;}
}
