import java.util.Scanner;

public class UI  extends Thread{
    private final Scheduler scheduler;

    public UI (Scheduler scheduler){
        this.scheduler = scheduler;
    }
    public void run() { // Thread는 run이라는 함수를 무조건 실행시킨다.
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
                scheduler.enReadyQueue(process); // Critical Section을 UI에 만들게 한다.
            }
            command = sc.next();
        }

        sc.close();
    }
}
