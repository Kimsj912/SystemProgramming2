package HW;

import Elements.Instruction;
import Elements.ProcessContext;

// CPU는 명령을 주면 처리하는 수동적인 부분이다.
public class CPU {

    // Getter & Setter
    public ProcessContext getContext(){return context;}
    public void setContext(ProcessContext context){this.context = context; }

    // Variables
    private ProcessContext context; // process context (register)

    // Constructor
    public CPU(){
        this.context = null;
    }


    public  void executeInstruction(Instruction instruction){
        instruction.run();
    }


}
