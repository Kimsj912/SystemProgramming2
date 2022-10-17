import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

public class Storage extends ArrayList<Program> {

    public Program[] startProgram(){
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

    public void addProgram(Program program){
        this.add(program);
    }

    public Program load(String programName){
        for(Program program : this){
            if(program.getName().equals(programName)){
                return program;
            }
        }
        return null;
    }
}
