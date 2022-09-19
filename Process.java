import java.util.Vector;

public class Process {
    private int PC;

    private Vector<Instruction> instructions;
    public int getLineLength(){return this.instructions.size();}

    public Process(){
        this.instructions = new Vector<Instruction>();
        this.instructions.add(new L1());
        this.instructions.add(new L2());
        this.instructions.add(new L3());
        this.instructions.add(new L4());
        this.instructions.add(new L5());
    }
    public void execute (int lineNum){
        instructions.get(PC).execute();
        PC++;
    }

    private abstract class Instruction {
        abstract int execute();
    }

    private class L1 extends Instruction {
        int execute(){
            System.out.println(this.getClass().getName());
            return 0;
        }
    }
    private class L2 extends Instruction {
        int execute(){
            System.out.println(this.getClass().getName());
            return 0;
        }
    }
    private class L3 extends Instruction {
        int execute(){
            System.out.println(this.getClass().getName());
            return 0;
        }
    }
    private class L4 extends Instruction {
        int execute(){
            System.out.println(this.getClass().getName());
            return 0;
        }
    }
    private class L5 extends Instruction {
        int execute(){
            System.out.println(this.getClass().getName());
            return 0;
        }
    }
}
