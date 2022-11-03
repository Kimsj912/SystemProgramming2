import java.util.Vector;

public class FileSystem extends Thread {

    private Queue<Interrupt> interruptQueue;
    private Vector<Integer> fileSystemBuffer;
    public FileSystem(Queue<Interrupt> interruptQueue, Vector<Integer> fileSystemBuffer){
        this.interruptQueue = interruptQueue;
        this.fileSystemBuffer = fileSystemBuffer;
    }

    public void initialize(){
    }
    public void finish(){
    }

    public void run(){
        while (this.bPowerOn) {

        }
    }
}
