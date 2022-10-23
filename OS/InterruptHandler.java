package OS;

import Elements.Interrupt;

import java.util.LinkedList;
import java.util.Queue;

// Inner Classes
public class InterruptHandler {
    private final Queue<Interrupt> interruptQueue;

    public InterruptHandler(){
        this.interruptQueue = new LinkedList<>();
    }

    public void addInterrupt(Interrupt interrupt){
        this.interruptQueue.add(interrupt);
    }

    public Interrupt getInterrupt(){
        return this.interruptQueue.poll();
    }
}
