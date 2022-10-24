package Elements;

public class Instruction {

    private String opcode;
    private String[] operand;
    public void setOpcode(String opcode){this.opcode = opcode;}
    public void setOperand(String[] operand){this.operand = operand;}
    public String getOpcode() {return opcode;}
    public String getOperand() {return String.join(" ", operand);}

    public Instruction(String opcode, String ...operand) {
        this.opcode = opcode;
        this.operand = operand;
    }
    public String toString(){
        return getOpcode() + " " + getOperand();
    }
}
