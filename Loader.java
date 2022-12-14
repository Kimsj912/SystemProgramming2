import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Loader {
    public Process load(String exeName){
        try {
            File file = new File("Data"+"/"+exeName);
            Scanner sc = new Scanner(file);
            Process process = new Process(); // process�� ���� ������ �е��� �Ͽ� modularity�� ����.
            process.parse(sc);
            sc.close();

            Scheduler scheduler = new Scheduler();
            return process;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
