package Elements;

import Constants.ConstantData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Program {

    // Variables
    private String name;
    private String codes;

    // Getter & Setter
    public String getCodes(){return this.codes;}
    public String getName(){return this.name;}

    BufferedReader br;
    // Constructor
    // TODO: 파일에서 pname, codes 등을 가져와야 함.
    public Program(String name) throws IOException{
        this.name = name;
        br = new BufferedReader(new FileReader(ConstantData.directoryName.getText()+name));
        String readline = br.readLine();
        this.name = readline;
        this.codes = "";
        while((readline = br.readLine()) != null){
            codes = codes.concat(readline+"\n");
        }
        codes = codes.stripTrailing();
    }

    @Override
    public String toString(){
        return "Elements.Program{\n" +
                "    name='" + name + "',\n" +
                "    codes='" + codes + ",\n" +
                '}';
    }
}

// Elements.Program File Style
/**
 * process1
 * set x
 * set y
 * add x
 * print x
 * */