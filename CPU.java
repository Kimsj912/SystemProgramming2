// CPU�� ����� �ָ� ó���ϴ� �������� �κ��̴�.
public class CPU {

    //
    private class Context { // Context�� ���� register �����̶�� ���� ��.
        private int PC;
        public int getPC(){return PC;}
        public void setPC(int PC){ this.PC = PC;}

    }

    private Process process;
    private Context context;
    public void setContext(Process process){
        this.process = process;
        this.process = this.process.getContext();
    }
    public Process getContext(){return this.process;}

    // Constructor
    public CPU(){this.context = new Context();
    }

    public  void executeInstruction(){ // �Ѷ����� �����ϴ� ��. (register���� ���¸� CPU�� �����ִٰ� �����ϰ� ������ �� �ٽ� ������)
        // ��������� �����غ��� ����pc�� ������� �� �� �ִ�.
        int nextPC = this.process.run(this.context.getPC());
        this.getContext().setPC(nextPC);
    }
}
