package Main;

import HW.CPU;
import HW.Memory;
import HW.Storage;
import OS.OS;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{ // main이 실행되는 걸 poweron이라고 가정합시다.
        CPU cpu = new CPU();
        Memory memory = new Memory();
        Storage storage = new Storage();

        BIOS bios = new BIOS();
        OS os = bios.start(cpu, memory, storage);

        // IO Connections
        os.initialize();
        os.run();
    }


}
