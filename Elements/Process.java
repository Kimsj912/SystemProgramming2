package Elements;

import Enums.EProcessStatus;

import java.util.Vector;

public class Process {


    // Attributes
    private ProcessContext context;
    private CodeSegment codeSegment;
    private DataSegment dataSegment;
    private StackSegment stackSegment;
    private HeapSegment heapSegment;

    // Getter & Setter
    public ProcessContext getContext(){return context;}
    public void setContext(ProcessContext context){this.context = context;}

    // Constructor
    public Process(String name, int pid, int oid, String codes) {
        this.context = new ProcessContext(name, pid, oid);

        String flag = "";
        for(String code : codes.split("\n")){
            if(code.strip().equals("")) continue;
            if(code.charAt(0) == '_'){
                flag = code.substring(1);
                continue;
            }
            if(flag.equals("data")) {
                String[] cmd = code.split(" ");
                switch(cmd[0]){
                    case "code" -> this.codeSegment = new CodeSegment(Integer.parseInt(cmd[1]));
                    case "data" -> this.dataSegment = new DataSegment(Integer.parseInt(cmd[1]));
                    case "stack" -> this.stackSegment = new StackSegment(Integer.parseInt(cmd[1]));
                    case "heap" -> this.heapSegment = new HeapSegment(Integer.parseInt(cmd[1]));
                }
            } else if(flag.equals("code")){
                this.codeSegment.addCode(code);
            }
        }
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

    public void setStatus(EProcessStatus status){
        this.context.setStatus(status);
    }

    public EProcessStatus getStatus(){
        return this.context.getStatus();
    }

    @Override
    public String toString(){
        return this.getProcessName();
    }

    // Inner Classes
    private static class CodeSegment extends Segment{
        private final Vector<Instruction> instructions;

        public CodeSegment(int maxSize){
            super(maxSize);
            this.instructions = new Vector<Instruction>();
        }

        public void addCode(String codeLine){
            String[] code = codeLine.split(" ");
            System.out.println(codeLine);
            if(code.length == 1){
                this.instructions.add(new Instruction(code[0]));
            } else if(code.length == 2){
                this.instructions.add(new Instruction(code[0], code[1]));
            } else if(code.length == 3){
                this.instructions.add(new Instruction(code[0], code[1], code[2]));
            }
            this.size++;
        }

        public Instruction getInstruction(){
            if(this.base < this.size) return this.instructions.get(this.base++);
            else return null;
        }
    }
    private static class StackSegment extends Segment{
        public StackSegment(int maxSize){
            super(maxSize);
        }
    }
    private static class HeapSegment  extends Segment{
        public HeapSegment(int maxSize){
            super(maxSize);
        }
    }
    private static class DataSegment extends Segment {
        public DataSegment(int maxSize){
            super(maxSize);
        }
    }

    private static class Segment{
        protected int maxSize;
        public int getMaxSize(){return maxSize;}
        public void setMaxSize(int maxSize){this.maxSize = maxSize;}

        protected int base = 0;
        protected int size = 0;

        public Segment(int maxSize){
            this.maxSize = maxSize;
        }
    }
}
