import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Program {

    // Variables
    private String path;
    private String name;
    private String codes;

    // Getter & Setter
    public String getCodes(){return this.codes;}
    public String getName(){return this.name;}

    // Constructor
    // TODO: 파일에서 pname, codes 등을 가져와야 함.
    public Program(String path) throws IOException{
        this.path = path;

        BufferedReader br = new BufferedReader(new FileReader(path));
        String readline = br.readLine();
        this.name = readline;
        this.codes = "";
        while((readline = br.readLine()) != null){
            codes = codes.concat(readline+"\n");
        }
        codes = codes.stripTrailing();
    }

}

// Program File Style
/**
 * process1
 * set x
 * set y
 * add x
 * print x
 * */