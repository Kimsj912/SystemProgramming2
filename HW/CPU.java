package HW;

import Elements.Instruction;
import Elements.ProcessContext;

// CPU�� ����� �ָ� ó���ϴ� �������� �κ��̴�.
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
