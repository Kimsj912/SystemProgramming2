public class Main {
    public static void main(String[] args){
        CPU cpu = new CPU();
        cpu.setSwitch(true);

        Process process = new Process(); // 원래 Process를 불러오는 과정이 필요한데 일단 PRocess를 처리하도록 해보자.
        cpu.loadProcess(process);
        cpu.run();
    }


}
