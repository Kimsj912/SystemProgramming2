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
                    // timeout하면 컨텍스트 스위칭을 할고다.
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
            // interrupt를 체크해야함.
            checkInterrupt();
        }
    }

    private void checkInterrupt(){
        // timeout이나 io interrupt가 발생했는지 확인.
    }
}
