package Main;

import HW.CPU;
import HW.Memory;
import HW.Storage;
import HW.UI;
import OS.OS;

public class BIOS {
    public BIOS () {
        // IO들을 깨운다.
    }

    public OS start(CPU cpu, Memory memory, Storage storage, UI ui) {
        // OS를 실행시킨다.
        return new OS(cpu, memory, storage, ui);
    }

}
