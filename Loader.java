import java.io.File;
import java.io.FileNotFoundException;

public class Loader {
    private Memory memory;
    private Storage storage;

    public Loader() {
    }

    public void initialize(Memory memory, Storage storage){
        this.memory = memory;
        this.storage = storage;
    }

    public Process load(Program program) {
        return memory.load(program);
    }

}
