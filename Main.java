public class Main {
    public static void main(String[] args){
//        CPU cpu = new CPU();
//        cpu.setSwitch(true);

        // 실제론 메모리에 올려야하는데 일단은 process 3개를 만들었다고 가정하고 코딩
        Loader loader = new Loader();
        Process process1 = loader.load("process1");
        Process process2 = loader.load("process2");
        Process process3 = loader.load("process3");

        Scheduler scheduler = new Scheduler();
        scheduler.enqueueReadyQueue(process1);
        scheduler.enqueueReadyQueue(process2);
        scheduler.enqueueReadyQueue(process3);
        scheduler.run();

//        Process process = new Process(); // 원래 Process를 불러오는 과정이 필요한데 일단 PRocess를 처리하도록 해보자.
//        cpu.loadProcess(process);
//        cpu.run();
    }


}
