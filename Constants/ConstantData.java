package Constants;

public enum ConstantData {
    startPrograms("System/startPrograms.txt"),


    // Storage
    directoryName("Data/"),
    fileExtension(".txt");




    // Getter & Setter
    private String text;
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    // Constructor
    ConstantData(String text){
        this.text = text;
    }

}
