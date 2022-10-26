import java.util.Scanner;

public class UI  extends Thread{
    private final Scheduler scheduler;

    public UI (Scheduler scheduler){
        this.scheduler = scheduler;
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
                scheduler.enReadyQueue(process); // Critical Section�� UI�� ����� �Ѵ�.
            }
            command = sc.next();
        }

        sc.close();
    }
}
