import java.util.Scanner;

public class UI  extends Thread{
    private Queue<Process> readyQueue;

    public UI (Queue<Process> readyQueue){
        this.readyQueue = readyQueue;
    }
    public void run() { // Thread�� run�̶�� �Լ��� ������ �����Ų��.
        Loader loader = new Loader();
        // console command
        // "r fileName -> execute fileName"
        // "q -> quit program"
        Scanner sc = new Scanner(System.in);
        String command = sc.next();
        while(command.compareTo("q") != 0){
            if(command.compareTo("r") == 0){
                String fileName = sc.next();
                Process process =  loader.load(fileName);
                readyQueue.enReadyQueue(process); // Critical Section�� UI�� ����� �Ѵ�.
            }
            command = sc.next();
        }

        sc.close();
    }
}
