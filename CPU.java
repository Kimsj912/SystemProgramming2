// CPU는 명령을 주면 처리하는 수동적인 부분이다.
public class CPU {

    //
    private class Context { // Context는 그저 register 집합이라고 보면 됨.
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

    public  void executeInstruction(){ // 한라인을 실행하는 것. (register들의 상태를 CPU가 갖고있다가 저장하고 복구할 땐 다시 가져옴)
        // 현재라인을 실행해보면 다음pc가 어디인지 알 수 있다.
        int nextPC = this.process.run(this.context.getPC());
        this.getContext().setPC(nextPC);
    }
}
