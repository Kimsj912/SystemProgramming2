import java.util.Vector;

public class Process {
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
        instructions.get(lineNum).execute();
    }

    private abstract class Instruction {
        abstract void execute();
    }

    private class L1 extends Instruction {
        void execute(){
            System.out.println(this.getClass().getName());
        }
    }
    private class L2 extends Instruction {
        void execute(){
            System.out.println(this.getClass().getName());
        }
    }
    private class L3 extends Instruction {
        void execute(){
            System.out.println(this.getClass().getName());
        }
    }
    private class L4 extends Instruction {
        void execute(){
            System.out.println(this.getClass().getName());
        }
    }
    private class L5 extends Instruction {
        void execute(){
            System.out.println(this.getClass().getName());
        }
    }
}
