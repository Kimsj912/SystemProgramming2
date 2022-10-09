public class InterruptHandler{
    // Interrupt�� ������ �پ��������ֱ� ������ ��ü�� ������ Ȯ�强�� ����.

    public enum EInterrupt{ // OS Interrupt
        eTimeout,
        eIOStarted,
        eIOTerminated,
        eProcessTerminated,
        eProcessStarted,
    }

    public void handle(){
        // loader, timer, io, process�� ���ͷ�Ʈ�� �߻���Ų��.
        EInterrupt eInterrupt = null;
        switch (eInterrupt){
            case eProcessStarted:
                // TODO: � ���μ����� ���۵Ǿ����� �Ķ���ͷ� �����.
                handleProcessStart();
                break;
            case eProcessTerminated:
                // TODO: � ���μ����� ����Ǿ����� �Ķ���ͷ� �����.
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
    // Queue�� ������� �� �ƹ����� ���� ���� �ʱ� ���μ����� �������� ����
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