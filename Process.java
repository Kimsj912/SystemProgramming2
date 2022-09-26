import java.util.Vector;

public class Process {
    private class ProcessControlBlock {
        private int pid; // ���μ��� id

        // Account
        private int oid; // owner Id

        // Statistics
        // io Status Information (IO�� �������� �ƴ���, � IO�� ��������, � IO�� ���۵Ǿ����� ��)
        private int ioDeviceId;
        private int ioDeviceStatus;
        public enum Estatus{
            eReady,
            eRunning,
            eWaiting,
            eSuspended, // �Ͻ��ߴܵ�.
        }
        private Estatus status;

        private class CPUContext { // TODO: CPU�� Process�� �����ؼ� �� ����.
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
