public class Scheduler {
    public enum EInterrupt{
        eTimeout,
        eIOStarted,
        eIOCompleted,

    }
    class InterruptHandelr{
        public void processInterrupt(EInterrupt eInterrupt){
            switch (eInterrupt){
                case eTimeout:
                    // timeout�ϸ� ���ؽ�Ʈ ����Ī�� �Ұ��.
                    break;
                case eIOStarted:

                    break;
                case eIOCompleted:

                    break;
            }
        }
    }

    public void exectue(Process process){
        for(int i=0;i<process.getLineLength();i++){
            process.execute(i);
            // interrupt�� üũ�ؾ���.
            checkInterrupt();
        }
    }

    private void checkInterrupt(){
        // timeout�̳� io interrupt�� �߻��ߴ��� Ȯ��.
    }
}
