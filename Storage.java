import java.io.IOException;

public class Storage {
    public Program[] startProgram(){
        // TODO: File에서 읽어오도록 변경할 것.
        try{
            Program[] programs = new Program[3];
            programs[0] = new Program("process1.txt");
            programs[1] = new Program("process2.txt");
            programs[2] = new Program("process3.txt");
            return programs;
        } catch (IOException e){
            System.out.println("404 File Not Found");
            e.printStackTrace();
            return null;
        }
    }
}
