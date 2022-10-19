package HW;

import Constants.ConstantData;
import Elements.Program;

import java.io.*;
import java.util.HashMap;

public class Storage extends HashMap<String, Program> {

    public Storage (){
        super();

        File directory = new File(ConstantData.directoryName.getText());
        if(!directory.exists()){
            if(!directory.mkdir()) {
                System.out.println("ERROR: Failed to create directory");
            }
        }
        File[] files = directory.listFiles();
        assert files != null; // listFiles 읽어보니 null이 오진 않을 것 같음. assert만 남겨도 무리 없으리라 봄.
        for (File file : files) {
            try {
                System.out.println("File: " + file.getName());
                if(file.getName().endsWith(ConstantData.fileExtension.getText())) {
                    Program program = new Program(file.getName());
                    addProgram(program);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Program[] startPrograms() throws IOException{
        // Read Start Elements.Program list
        File startProgramList = new File(ConstantData.startPrograms.getText());
        BufferedReader br = new BufferedReader(new FileReader(startProgramList));
        String line = br.readLine();

        // Make Start Elements.Program array
        Program[] startPrograms = new Program[Integer.parseInt(line)]; // 처음에 startProgram의 개수를 알려줌.
        for(int i = 0; (line = br.readLine()) != null; i++)startPrograms[i] = this.get(line);
        return startPrograms;
    }
    public void addProgram(Program program){this.put(program.getName(), program);}
}
