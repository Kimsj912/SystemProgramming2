import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Loader {
    // HDD exe를 읽어서 exe파일의 메모리 주소를 Scheduler에게 주는 역할을 수행.
//    .program
//            .data
//    codeSize 40
//    dataSize 4096
//    stackSize 4096
//    heapSize 4096
//            .code
//    move @0, 0
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

    public void load(String exeName){
        try {
            File file = new File("Data"+"/"+exeName);
            Scanner sc = new Scanner(file);
            this.parse(sc);
            sc.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void parse(Scanner sc){
        String command = sc.next();
        command = sc.next();
        parseData(sc);
        parseCode(sc);
    }
    private void parseData(Scanner sc){
        String command = sc.next();
        while (command.compareTo(".end") != 0) {
//            parseData(sc);
        }
    }

    private void parseCode(Scanner sc) {
        String command = sc.next();
        while (command.compareTo(".end") != 0) {
//            parseCode(sc);
        }
    }
}
