package HW;

import Constants.Assembly;
import Constants.ConstantData;
import Elements.Instruction;
import Elements.Interrupt;
import Elements.Process;
import Elements.ProcessContext;
import Enums.EInterrupt;
import Enums.EProcessStatus;
import OS.InterruptHandler;
import OS.Scheduler;

import java.util.Timer;
import java.util.TimerTask;

// CPU는 명령을 주면 처리하는 수동적인 부분이다.
public class CPU extends Thread{

    private InterruptHandler interruptHandler;
    private Scheduler scheduler;
    private UI ui;

    // Getter & Setter
    public ProcessContext getContext(){return context;}
    public void setContext(ProcessContext context){this.context = context; }

    // Variables
    private ProcessContext context; // process context (register)

    public Process getProcess(){return process;}
    public void setProcess(Process process){
        this.process = process;
        processChanged = true;
    }

    private Process process; // process

    private boolean processChanged;

    // Constructor
    public CPU(){
        this.context = null;
        this.process = null;
        this.processChanged = true;
    }

    // Methods
    public void initialize(InterruptHandler interruptHandler, Scheduler scheduler, UI ui){
        this.interruptHandler = interruptHandler;
        this.scheduler = scheduler;
        this.ui = ui;

    }

    public void run(){
        while(true){
            try {
                sleep(Long.parseLong(ConstantData.cpuTimeSlice.getText()));
                if(process == null) continue;
                if(this.process.getStatus().equals(EProcessStatus.WAITING) && !processChanged) return;
                Instruction instruction = process.getInstruction();
                if(instruction == null){
                    this.process.setStatus(EProcessStatus.TERMINATED);
                    this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessTerminated, process));
                }else{
                    this.ui.addInstructionLog(process, instruction.toString());
//                    if(instruction.getOpcode().equals(Assembly.itr.name())){
//                        this.process.setStatus(EProcessStatus.WAITING);
//                        this.interruptHandler.addInterrupt(new Interrupt(EInterrupt.eIOStarted, process));
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run(){
//                                processChanged = true;
//                            }
//                        }, 1000);
//                    }
//                    processChanged = false;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
