import Enums.EInterrupt;
import Enums.EProcessStatus;

import java.util.*;

public class OS {
    private final Storage storage;
    private InterruptHandler interruptHandler;
    private CPU cpu;
    private Memory memory;

    private Queue<Process> readyQueue;

    private Process currentProcess;

    public OS(CPU cpu, Memory memory, Storage storage) {
        // Connection
        this.cpu = cpu;
        this.memory = memory;
        this.storage = storage;

        // Initialize Self
        this.interruptHandler = new InterruptHandler();
        this.readyQueue = new LinkedList<>();
    }

    public void initialize(){
        // TODO: Loader로 옮기기
        for (Program program : storage.startProgram()) {
            Process process = this.memory.load(program);
            readyQueue.add(process);
        }
        interruptHandler.addInterrupt(new Interrupt(EInterrupt.eNone, null));
    }

    Interrupt interrupt;
    public void checkInterrupt(){
        Process oldProcess, newProcess;
        this.interrupt = this.interruptHandler.getInterrupt(); // TODO: !!!!!!!!!!!!!!!!여기부터 수정해야함. 초기에 프로젝트 셋팅하고 루핑도는 과정이 뻑이 좀 있음. interruptHandler.getInterrupt()의 위치 변화가 요구됨.
        if(interrupt == null) return;
        switch(interrupt.getType()){
            case eNone: // 초기화상태
                if(! readyQueue.isEmpty()) interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessStarted, this.readyQueue.poll()));
                break;
            case eRunning: break;
            case eTimeOut:
                // Context Switching (with readyQueue)
                // Process old Process
                oldProcess = interrupt.getProcess();
                oldProcess.setContext(cpu.getContext());
                oldProcess.getContext().setStatus(EProcessStatus.READY);
                readyQueue.add(oldProcess);

                // Process new Process
                newProcess = readyQueue.poll();
                if(newProcess == null) return;
                this.currentProcess = newProcess;
                cpu.setContext(this.currentProcess.getContext());
                cpu.getContext().setStatus(EProcessStatus.RUNNING);
                System.out.println("Context Switched by Interrupted: " + oldProcess.getContext().getName()+ " -> "+ newProcess.getContext().getName());
                break;
            case eProcessStarted:
                this.currentProcess = interrupt.getProcess();
                cpu.setContext(this.currentProcess.getContext());
                System.out.println("Process " + this.currentProcess.getContext().getName() + " is started");
                break;
            case eProcessTerminated:
                // Process old Process
                oldProcess = interrupt.getProcess();
                oldProcess.setContext(cpu.getContext());
                oldProcess.getContext().setStatus(EProcessStatus.TERMINATED);
                // TODO: 요기에 Storage까지 프로세스 종료 처리 로직 추가

                // Process new Process
                newProcess = readyQueue.poll();
                try{
                    assert newProcess != null;
                } catch (AssertionError e) {
                    System.out.println("All Process Terminated");
                    // TODO: 지금은 종료하지만, 나중엔 루핑 계속 돌도록 해야함.
                    System.exit(0);
                }
                cpu.setContext(newProcess.getContext());
                this.currentProcess = newProcess;
                cpu.getContext().setStatus(EProcessStatus.RUNNING);
                System.out.println("Context Switched by Terminated: " + oldProcess.getContext().getName()+ " -> "+ newProcess.getContext().getName());
                break;
            // TODO: 구현 필요
            case eIOStarted:
                break;
            case eIOTerminated:
                break;
        }


    }

    public void run(){
        while(true) {
            // TODO: UI로 파일 추가된게 있는지 체크하는 로직 추가하여 Process 추가로직 구성
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run(){
                            Instruction nextInstruction = currentProcess.getInstruction();
                            if(nextInstruction == null) interruptHandler.addInterrupt(new Interrupt(EInterrupt.eProcessTerminated, currentProcess));
                            else cpu.executeInstruction(nextInstruction);
                        }
                    }, 2900);
                    checkInterrupt();
                }
            }, 3000);
            interruptHandler.addInterrupt(new Interrupt(EInterrupt.eTimeOut, currentProcess));
        }
    }



    // Inner Classes
    private class Interrupt {
        private EInterrupt type;
        private Process process;

        public Interrupt(EInterrupt type, Process process){
            this.type = type;
            this.process = process;
        }

        // Getter & Setter
        public Process getProcess(){return process;}
        public void setProcess(Process process){this.process = process;}
        public EInterrupt getType(){return type;}
        public void setType(EInterrupt type){this.type = type;}
    }
    private static class InterruptHandler {
        Queue<Interrupt> interruptQueue;
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
}