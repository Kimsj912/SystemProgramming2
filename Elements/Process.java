package Elements;

import java.util.Vector;

public class Process {


    // Attributes
    private ProcessContext context;
    private final CodeSegment codeSegment;
    private final DataSegment dataSegment;
    private final StackSegment stackSegment;
    private final HeapSegment heapSegment;

    // Getter & Setter
    public ProcessContext getContext(){return context;}
    public void setContext(ProcessContext context){this.context = context;}

    // Constructor
    public Process(String name, int pid, int oid, String codes) {

        this.context = new ProcessContext(name, pid, oid);
        // TODO: initialize (stack, heap, data) segments
        this.codeSegment = new CodeSegment(codes);
        this.dataSegment = new DataSegment(); // Empty Stack
        this.stackSegment = new StackSegment(); // Empty Stack
        this.heapSegment = new HeapSegment(); // Empty Heap
    }

    // Methods
    public Instruction getInstruction(){
        return this.codeSegment.getInstruction();
    }

    public int getPid(){
        return this.context.getPid();
    }

    public String getProcessName(){
        return this.context.getName();
    }

    @Override
    public String toString(){
        return this.getProcessName();
    }

    // Inner Classes
    private static class CodeSegment {
        private final Vector<Instruction> instructions;
        private int size;
        private int base;

        public CodeSegment(String codes){
            this.instructions = new Vector<Instruction>();
            this.size = 0;
            this.base = 0;

            String[] codeLines = codes.split("\n");

            for (String codeLine : codeLines) {
                String[] code = codeLine.split(" ");
                Instruction instruction = new Instruction(code[0], code[1]);
                this.instructions.add(instruction);
                this.size++;
            }
        }

        public Instruction getInstruction(){
            if(this.base < this.size) return this.instructions.get(this.base++);
            else return null;
        }
    }
    private static class StackSegment{ }
    private static class HeapSegment{ }
    private static class DataSegment { }
}
