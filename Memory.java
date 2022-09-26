public class Memory {
    private static int OWNER_ID = 1; // 일단 단일 유저(owner)로 보자.

    private Process[] processList;
    private int size;

    public Memory () {
        this.processList = new Process[100];
    }
    public Process load(Program program){
        this.processList[size] = new Process(program.getName(), size, OWNER_ID, program.getCodes());
        return processList[size++];
    }
}
