import java.lang.instrument.Instrumentation;
import java.util.HashMap;
import java.util.Map;
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

        private String command;
        String operand1;
        String operand2;


        public String getCommand(){return command;}
        public void setCommand(String command){this.command = command;}
        public String getOperand1(){return operand1;}
        public void setOperand1(String operand1){this.operand1 = operand1;}
        public String getOperand2(){return operand2;}
        public void setOperand2(String operand2){this.operand2 = operand2;}

        public Instruction(String command, String operand1, String operand2){
            this.command = command;
            this.operand1 = operand1;
            this.operand2 = operand2;
        }

        public Instruction(String line){
            // 컴파일러에선 단어 하나를 토큰이라고 한다.
            String[] tokens = line.split(" ");
            this.command = tokens[0];
            this.operand1 = ""; // 초기화 해야함.
            this.operand2 = ""; // 초기화 해야함.
            if(tokens.length > 1)this.operand1 = tokens[1];
            if(tokens.length > 2)this.operand2 = tokens[2];
        }

        public String toString(){
            return command + " " + operand1 + " " + operand2;
        }
    }


    // Variables
    // about CPU
    private static final int MAX_REGISTERS = 10;
    private Vector<Integer> registers;
    private int PC;
    private int stackSize;
    private int codeSize;
    private int heapSize;
    private int dataSize;

    // about Memory
    private Vector<Instruction> codeList;
    private Vector<Integer> dataSegment;
    private Vector<Integer> heapSegment;
    private Vector<Integer> stackSegment;

    // aboutParser
    private Map<String, String> labelMap;

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
        for(int i = 0; i<MAX_REGISTERS; i++){
            this.registers.add(i);
        }
        this.codeList = new Vector<>();
        this.dataSegment = new Vector<>();
        this.heapSegment = new Vector<>();
        this.stackSegment = new Vector<>();

        this.labelMap = new HashMap<>();

    }


    public void parseCode(Scanner sc){
        // 컴파일러가 파일을 읽을 땐 심볼읽는거 한번, 전체로 한번읽음.
        this.parsePhaseI(sc);
        this.parsePhaseII(sc);
    }

    private void parsePhaseII(Scanner sc){
        for(Instruction instruction : this.codeList){
            if(instruction.getCommand().compareTo("jump")==0 || instruction.getCommand().compareTo("greaterThanEqual")==0){
                instruction.setOperand1(this.labelMap.get(instruction.getOperand1()));
            }
        }
    }

    private void parsePhaseI(Scanner sc){
        String line = sc.nextLine();
        while(sc.hasNext()){
            Instruction instruction = new Instruction(line);
            if(instruction.getCommand().compareTo("label") == 0){
                this.labelMap.put(instruction.getOperand1(), String.valueOf(this.codeList.size()));
            }else if(instruction.getCommand().compareTo("//") == 0){
            }else if(instruction.getCommand().compareTo("") == 0) {
            } else{
                this.codeList.add(instruction);
            }
            line = sc.nextLine();
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

    private void parseData(Scanner sc){ // 실제론 PCB에 들어가야하는 함수임.
        String command;
        while((command = sc.next()).compareTo(".end") != 0){
            String operand = sc.next();
            int size = Integer.parseInt(operand);
            if(command.compareTo("codeSize") == 0){
                this.codeSize = size;
            } else if(command.compareTo("dataSize") == 0){
                this.dataSize = size;
                for(int i=0;i<dataSize;i++){
                    this.dataSegment.add(i);
                }
            } else if(command.compareTo("heapSize") == 0){
                this.heapSize = size;
                for(int i=0;i<heapSize;i++){
                    this.heapSegment.add(i);
                }
            } else if(command.compareTo("stackSize") == 0){
                this.stackSize = size;
                for(int i=0;i<stackSize;i++){
                    this.stackSegment.add(i);
                }
            }
        }
    }

    public boolean executeInstruction(InterruptHandler interruptHandler){
        Instruction instruction = this.codeList.get(this.getPC());
        System.out.println(instruction);
        this.setPC(this.getPC()+1);
        if(instruction.getCommand().compareTo("halt") == 0){
            Interrupt interrupt = new Interrupt(Interrupt.EInterrupt.eProcessTerminated, this);
            this.getInterruptQueue.enqueue(interrupt);

            return false;
        }return true;
    }
}
