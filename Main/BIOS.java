package Main;

import HW.CPU;
import HW.Memory;
import HW.Storage;
import HW.UI;
import OS.OS;

public class BIOS {
    public BIOS () {
        // IO���� �����.
    }

    public OS start(CPU cpu, Memory memory, Storage storage, UI ui) {
        // OS�� �����Ų��.
        return new OS(cpu, memory, storage, ui);
    }

}
