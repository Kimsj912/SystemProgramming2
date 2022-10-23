package Main;

import HW.CPU;
import HW.Memory;
import HW.Storage;
import HW.UI;
import OS.OS;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{ // main�� ����Ǵ� �� poweron�̶�� �����սô�.
        CPU cpu = new CPU();
        Memory memory = new Memory();
        Storage storage = new Storage();
        UI ui = new UI();

        BIOS bios = new BIOS();
        OS os = bios.start(cpu, memory, storage, ui);

        ui.initialize(cpu, storage);
        storage.initialize(ui);
        os.initialize();
        os.run();
    }


}
