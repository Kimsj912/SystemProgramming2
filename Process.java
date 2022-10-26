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




    // Variables
    private int PC;
    private int stackSize;
    private int codeSize;
    private int heapSize;
    private int dataSize;
    private Vector<String > codeList;


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
    public Vector<String> getCodeList(){return codeList;}
    public void setCodeList(Vector<String> codeList){this.codeList = codeList;}



    // Constructor
    public Process () {
        this.codeList = new Vector<String>();

    }


    public void parseCode(Scanner sc){
        String line;
//        while(sc.hasNextLine()){
            while(sc.hasNext() && (line = sc.nextLine()).compareTo(".end") != 0){
                parse(sc);
            }
//        }

    }

    private void parse(Scanner scanner){
        while (scanner.hasNext()){
            String token = scanner.next();
            if(token.compareTo(".program") == 0){
            } else if(token.compareTo(".code") == 0){
                this.parseCode(scanner);
            } else if(token.compareTo(".data") == 0){
                this.parseData(scanner);
            } else if(token.compareTo(".end") == 0){
            }
        }
    }
    private void loadCodeSegment(Scanner sc){
        String token;
        while((token = sc.nextLine()).compareTo(".code") != 0) {
            String[] tokens = token.split(" ");
            if (tokens[0].compareTo("codeSize") == 0) {
                this.codeSize = Integer.parseInt(tokens[1]);
            } else if (tokens[0].compareTo(".dataSize") == 0) {
                this.dataSize = Integer.parseInt(tokens[1]);
            } else if (tokens[0].compareTo(".heapSize") == 0) {
                this.heapSize = Integer.parseInt(tokens[1]);
            } else if (tokens[0].compareTo(".stackSize") == 0) {
                this.stackSize = Integer.parseInt(tokens[1]);
            }
        }

    }

    private void parseData(Scanner sc){ // 실제론 PCB에 들어가야하는 함수임.
        String command;
        while((command = sc.next()).compareTo(".end") != 0){
            String operand = sc.next();
            int size = Integer.parseInt(operand);
            if(operand.compareTo("codeSize") == 0){
                this.codeSize = size;
            } else if(operand.compareTo(".dataSize") == 0){
                this.dataSize = size;
            } else if(operand.compareTo(".heapSize") == 0){
                this.heapSize = size;
            } else if(operand.compareTo(".stackSize") == 0){
                this.stackSize = size;
            }
        }
    }

    public void executeInstruction(){
        String instruction = this.codeList.get(this.getPC());
        System.out.println(instruction);
        this.setPC(this.getPC()+1);
    }
}
