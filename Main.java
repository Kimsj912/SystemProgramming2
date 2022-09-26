public class Main {
    public static void main(String[] args){ // main�� ����Ǵ� �� poweron�̶�� �����սô�.
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
