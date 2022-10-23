package HW;

import Elements.Instruction;
import Elements.ProcessContext;
import Enums.EProcessStatus;

// CPU는 명령을 주면 처리하는 수동적인 부분이다.
public class CPU extends Thread{

    private Instruction instruction;

    // Getter & Setter
    public ProcessContext getContext(){return context;}
    public void setContext(ProcessContext context){this.context = context; }

    // Variables
    private ProcessContext context; // process context (register)

    private Process process;

    // Constructor
    public CPU(){
        this.context = null;
        this.process = null;
    }


    public void setInstruction(Instruction instruction){
        this.instruction = instruction;
    }

    public void run(){
        while(true){
            if(instruction != null){
                instruction.run();
            }
        }
    }

    private void executeInstruction(Instruction nextInstruction){
        nextInstruction.run();
    }


}
