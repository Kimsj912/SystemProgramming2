public class BIOS {
    public BIOS () {
        // IO���� �����.
    }

    public OS start(CPU cpu, Memory memory, Storage storage) {
        // OS�� �����Ų��.
        return new OS(cpu, memory, storage);
    }

}
