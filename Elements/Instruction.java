package Elements;

public class Instruction {
    private String opcode;
    private String[] operand;

    public Instruction(String opcode, String ...operand) {
        this.opcode = opcode;
        this.operand = operand;
    }

    public String getOpcode() {
        return opcode;
    }

    public String getOperand() {
        return String.join(" ", operand);
    }

    public void run(){
//        System.out.println("Instruction: " + opcode + " " + operand);
    }

    public String toString(){
        return getOpcode() + " " + getOperand();
    }
}
