public class Main {
    public static void main(String[] args){
        CPU cpu = new CPU();
        cpu.setSwitch(true);

        Process process = new Process(); // ���� Process�� �ҷ����� ������ �ʿ��ѵ� �ϴ� PRocess�� ó���ϵ��� �غ���.
        cpu.loadProcess(process);
        cpu.run();
    }


}
