package Elements;

import Enums.EProcessStatus;

public class ProcessContext {
    // Elements.Process Status
    private int oid; // owner Id (Account)
    private int pid;

    private String name;
    private Instruction instruction;

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

    public int getOid(){
        return oid;
    }

    public void setOid(int oid){
        this.oid = oid;
    }

    public int getCs(){
        return cs;
    }

    public void setCs(int cs){
        this.cs = cs;
    }

    public int getDs(){
        return ds;
    }

    public void setDs(int ds){
        this.ds = ds;
    }

    public int getSs(){
        return ss;
    }

    public void setSs(int ss){
        this.ss = ss;
    }

    public int getHs(){
        return hs;
    }

    public void setHs(int hs){
        this.hs = hs;
    }

    public int getPC(){
        return PC;
    }

    public void setPC(int PC){
        this.PC = PC;
    }

    public int getAC(){
        return AC;
    }

    public void setAC(int AC){
        this.AC = AC;
    }

    public int getMAR(){
        return MAR;
    }

    public void setMAR(int MAR){
        this.MAR = MAR;
    }

    public int getMBR(){
        return MBR;
    }

    public void setMBR(int MBR){
        this.MBR = MBR;
    }

    // cu
    private int PC;

    // alu
    private int AC;

    // memory interface
    private int MAR;
    private int MBR;

    public ProcessContext(String name, int pid, int oid){
        // Elements.Process Status
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

    public int getPid(){
        return pid;
    }

}
