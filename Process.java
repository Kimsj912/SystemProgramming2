import java.util.Scanner;
import java.util.Vector;

public class Process {
//    .program
//    .data
//    codeSize 40
//    dataSize 4096
//    stackSize 4096
//    heapSize 4096
//    .code
//    move @0, 0
//    interrupt read
//    move @4, r0
//    move @8, 0
//    .label loop
//    compare @0, @4
//    jumpGraterThan loopEnd
//    add @8, 1
//    jump label
//    .label loopEnd
//    move r0, @8
//    interrupt write
//    halt
//            .end


    // Variables
    private int stackSize;
    private int codeSize;
    private int heapSize;
    private int dataSize;
    private Vector<String > codeList;


    // Getter & Setter
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


    public void load(Scanner sc){
        String line;
        while(sc.hasNext()){
            while((line = sc.nextLine()).compareTo(".end") != 0){
                if(line.compareTo(".data") == 0){ // parsing data segment
                    this.loadDataSegment(sc);
                }
                if(line.compareTo(".code")==0){ // parsing code segment
                    this.loadCodeSegment(sc);
                }
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

    private void loadDataSegment(Scanner sc){ // 실제론 PCB에 들어가야하는 함수임.
        String token;
        while((token = sc.nextLine()).compareTo(".end") != 0){
            String[] tokens = token.split(" ");
            if(tokens[0].compareTo("codeSize") == 0){
                this.codeSize = Integer.parseInt(tokens[1]);
            } else if(tokens[0].compareTo(".dataSize") == 0){
                this.dataSize = Integer.parseInt(tokens[1]);
            } else if(tokens[0].compareTo(".heapSize") == 0){
                this.heapSize = Integer.parseInt(tokens[1]);
            } else if(tokens[0].compareTo(".stackSize") == 0){
                this.stackSize = Integer.parseInt(tokens[1]);
            }
        }
    }
}
