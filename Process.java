import java.lang.instrument.Instrumentation;
import java.util.Scanner;
import java.util.Vector;

public class Process {
//.program
//            .data
//    codeSize 256
//    dataSize 256
//    stackSize 256
//    heapSize 256
//            .end
//            .code
//    move @0, 0e
//    interrupt read
//    move @4, r0
//    move @8, 0
//            .label loop
//    compare @0, @4
//    jumpGraterThan loopEnd
//    add @8, 1
//    jump label
//.label loopEnd
//    move r0, @8
//    interrupt write
//    halt
//            .end


    private class Instruction{
        String command;

        public String getCommand(){return command;}
        public void setCommand(String command){this.command = command;}
        public String getOperand1(){return operand1;}
        public void setOperand1(String operand1){this.operand1 = operand1;}
        public String getOperand2(){return operand2;}
        public void setOperand2(String operand2){this.operand2 = operand2;}

        String operand1;
        String operand2;
        public Instruction(String command, String operand1, String operand2){
            this.command = command;
            this.operand1 = operand1;
            this.operand2 = operand2;
        }

        public Instruction(String line){
            String[] tokens = line.split(" ");
            this.command = tokens[0];
            if(tokens.length > 1)this.operand1 = tokens[1];
            if(tokens.length > 2)this.operand2 = tokens[2];
        }

        public String toString(){
            return command + " " + operand1 + " " + operand2;
        }
    }


    // Variables
    private int PC;
    private int stackSize;
    private int codeSize;
    private int heapSize;
    private int dataSize;
    private Vector<Instruction> codeList;


    // Getter & Setter

    public int getPC(){return PC;}
    public void setPC(int PC){this.PC = PC;}
    public int getStackSize(){return stackSize;}
    public void setStackSize(int stackSize){this.stackSize = stackSize;}
    public int getCodeSize(){return codeSize;}
    public void setCodeSize(int codeSize){this.codeSize = codeSize;}
    public int getHeapSize(){return heapSize;}
    public void setHeapSize(int heapSize){this.heapSize = heapSize;}
    public int getDataSize(){return dataSize;}
    public void setDataSize(int dataSize){this.dataSize = dataSize;}
    public Vector<Instruction> getCodeList(){return codeList;}
    public void setCodeList(Vector<Instruction> codeList){this.codeList = codeList;}



    // Constructor
    public Process () {
        this.codeList = new Vector<>();

    }


    public void parseCode(Scanner sc){
        String line = sc.nextLine();
        while(line.compareTo("halt") != 0){
//            parse(sc);
            Instruction instruction = new Instruction(line);
            this.codeList.add(instruction);
            line = sc.nextLine();
            System.out.println(line);
        }

    }

    public void parse(Scanner scanner){
        while (scanner.hasNext()){
            String token = scanner.next();
            if(token.compareTo(".program") == 0){
            } else if(token.compareTo(".code") == 0){
                this.parseCode(scanner);
            } else if(token.compareTo(".data") == 0){
                this.parseData(scanner);
            } else if(token.compareTo(".end") == 0){
                System.out.println(codeSize+" "+dataSize+" "+stackSize+" "+heapSize);
            }
        }
    }
//    private void loadCodeSegment(Scanner sc){
//        String token;
//        while((token = sc.nextLine()).compareTo(".code") != 0) {
//            String[] tokens = token.split(" ");
//            if (tokens[0].compareTo("codeSize") == 0) {
//                this.codeSize = Integer.parseInt(tokens[1]);
//            } else if (tokens[0].compareTo(".dataSize") == 0) {
//                this.dataSize = Integer.parseInt(tokens[1]);
//            } else if (tokens[0].compareTo(".heapSize") == 0) {
//                this.heapSize = Integer.parseInt(tokens[1]);
//            } else if (tokens[0].compareTo(".stackSize") == 0) {
//                this.stackSize = Integer.parseInt(tokens[1]);
//            }
//        }
//
//    }

    private void parseData(Scanner sc){ // 실제론 PCB에 들어가야하는 함수임.
        String command;
        while((command = sc.next()).compareTo(".end") != 0){
            String operand = sc.next();
            int size = Integer.parseInt(operand);
            if(command.compareTo("codeSize") == 0){
                this.codeSize = size;
            } else if(command.compareTo("dataSize") == 0){
                this.dataSize = size;
            } else if(command.compareTo("heapSize") == 0){
                this.heapSize = size;
            } else if(command.compareTo("stackSize") == 0){
                this.stackSize = size;
            }
        }
    }

    public boolean executeInstruction(){
        Instruction instruction = this.codeList.get(this.getPC());
        System.out.println(instruction);
        this.setPC(this.getPC()+1);
        return instruction.getCommand().compareTo("halt") == 0;
    }
}
