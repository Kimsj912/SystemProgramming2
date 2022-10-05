public class InterruptHandler{
    // Interrupt는 종류가 다양해질수있기 때문에 객체로 만들어야 확장성이 좋다.

    public enum EInterrupt{ // OS Interrupt
        eTimeout,
        eIOStarted,
        eIOTerminated,
        eProcessTerminated,
        eProcessStarted,
    }

    public void handle(){
        // loader, timer, io, process가 인터럽트를 발생시킨다.
        EInterrupt eInterrupt = null;
        switch (eInterrupt){
            case eProcessStarted:
                // TODO: 어떤 프로세스가 시작되었는지 파라미터로 줘야함.
                handleProcessStart();
                break;
            case eProcessTerminated:
                // TODO: 어떤 프로세스가 종료되었는지 파라미터로 줘야함.
                handleProcessTerminated();
                break;
            case eIOStarted:
                handleIOStart();
                break;
            case eIOTerminated:
                handleIOTerminate();
                break;
            case eTimeout:
                handleTimeout();
                break;
            default:
                break;
        }
    }
    // Queue가 비어있을 때 아무짓도 안할 때의 초기 프로세스를 가져오는 역할
    private void handleProcessStart(){
        if(!readyQueue.isEmpty()){
            // get next process
            runningProcess = readyQueue.dequeue();
            // switching context
            cpu.setContext(runningProcess);
        }
    }

    private void handleProcessTerminated(){
    }

    private void handleIOStart(){
    }

    private void handleIOTerminate(){
    }

    private void handleTimeout(){

    }


}