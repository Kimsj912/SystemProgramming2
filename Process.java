import java.util.Vector;

public class Process {
    private class ProcessControlBlock {
        private int pid; // 프로세스 id

        // Account
        private int oid; // owner Id

        // Statistics
        // io Status Information (IO가 끝났는지 아닌지, 어떤 IO가 끝났는지, 어떤 IO가 시작되었는지 등)
        private int ioDeviceId;
        private int ioDeviceStatus;
        public enum Estatus{
            eReady,
            eRunning,
            eWaiting,
            eSuspended, // 일시중단됨.
        }
        private Estatus status;

        private class CPUContext { // TODO: CPU와 Process가 공유해서 쓸 거임.
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
        }
        private CPUContext cpuContext;



    }
    private int PC;

    private Vector<Instruction> instructions;
    public int getLineLength(){return this.instructions.size();}

    public Process(){
        this.instructions = new Vector<Instruction>();
    }

    public void execute (){
        instructions.get(PC).execute("");
        PC++;
    }

    private class Instruction {
        public void execute(String instruction){
            System.out.println("Instruction: " + instruction);
        }
    }
}
