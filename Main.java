public class Main {
    public static void main(String[] args){
//        CPU cpu = new CPU();
//        cpu.setSwitch(true);

        // ������ �޸𸮿� �÷����ϴµ� �ϴ��� process 3���� ������ٰ� �����ϰ� �ڵ�
        Loader loader = new Loader();
        Process process1 = loader.load("process1");
        Process process2 = loader.load("process2");
        Process process3 = loader.load("process3");

        Scheduler scheduler = new Scheduler();
        scheduler.enqueueReadyQueue(process1);
        scheduler.enqueueReadyQueue(process2);
        scheduler.enqueueReadyQueue(process3);
        scheduler.run();

//        Process process = new Process(); // ���� Process�� �ҷ����� ������ �ʿ��ѵ� �ϴ� PRocess�� ó���ϵ��� �غ���.
//        cpu.loadProcess(process);
//        cpu.run();
    }


}
