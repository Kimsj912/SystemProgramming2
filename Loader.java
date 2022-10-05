import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Loader {
    public void load(String exeName){
        try {
            File file = new File("Data"+"/"+exeName);
            Scanner sc = new Scanner(file);
            Process process = new Process(); // process�� ���� ������ �е��� �Ͽ� modularity�� ����.
            process.load(sc);
            sc.close();

            Scheduler scheduler = new Scheduler();
            scheduler.getReadyQueue().enqueue(process);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
