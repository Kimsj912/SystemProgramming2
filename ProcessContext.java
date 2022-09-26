import Enums.EProcessStatus;

public class ProcessContext {
    // Process Status
    private int oid; // owner Id (Account)
    private int pid;

    private String name;
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    private EProcessStatus status;
    public EProcessStatus getStatus(){return status;}
    public void setStatus(EProcessStatus status){this.status = status;}

    // Segment Table (about segment registers)
    private int cs;
    private int ds;
    private int ss;
    private int hs;

    // cu
    private int PC;

    // alu
    private int AC;

    // memory interface
    private int MAR;
    private int MBR;

    public ProcessContext(String name, int pid, int oid){
        // Process Status
        this.oid = oid;
        this.pid = pid;
        this.name = name;
        this.status = EProcessStatus.NEW;
        // Segment Table (about segment registers)
        this.cs = 0;
        this.ds = 0;
        this.ss = 0;
        this.hs = 0;
        // registers
        this.PC = 0;
        this.AC = 0;
        this.MAR = 0;
        this.MBR = 0;
    }

}
