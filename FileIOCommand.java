public class FileIOCommand {
    enum EFileIOCommand {
        eRead,
        eWrite,
        eTerminate,
    }

    private EFileIOCommand eFileIOCommand;
    private Process process;
    private int address;
    private int size;

    public FileIOCommand(EFileIOCommand eFileIOCommand, Process process, int address, int size){
        this.eFileIOCommand = eFileIOCommand;
        this.process = process;
        this.address = address;
        this.size = size;
    }

    public EFileIOCommand getEFileIOCommand(){
        return eFileIOCommand;
    }

    public Process getProcess(){
        return process;
    }

    public int getAddress(){
        return address;
    }

    public int getSize(){
        return size;
    }
}
