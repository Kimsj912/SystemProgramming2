import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scheduler scheduler = new Scheduler();
        UI ui = new UI(scheduler);
//        ui.run();

        scheduler.start();
        ui.start(); // ui�� Thread�� ���� ������.

    }


}
