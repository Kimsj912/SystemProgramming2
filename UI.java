import java.util.Scanner;

public class UI {
    public void run() {
        Loader loader = new Loader();
        Scheduler scheduler = new Scheduler();
        // console command
        // "r fileName -> execute fileName"
        // "q -> quit program"
        Scanner sc = new Scanner(System.in);
        String command = sc.next();
        while(command.compareTo("q") != 0){
            if(command.compareTo("r") == 0){
                String fileName = sc.next();
                Process process =  loader.load(fileName);
                scheduler.getReadyQueue().enqueue(process);
            }
            command = sc.next();
        }

        sc.close();
    }
}
