public class BIOS {
    public BIOS () {
        // IO들을 깨운다.
    }

    public OS start(CPU cpu, Memory memory, Storage storage) {
        // OS를 실행시킨다.
        return new OS(cpu, memory, storage);
    }

}
