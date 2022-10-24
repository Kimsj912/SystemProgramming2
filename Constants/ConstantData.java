package Constants;

public enum ConstantData {
    startPrograms("System/startPrograms.txt"),


    // HW.Storage
    directoryName("Data/"),
    fileExtension(".txt"),

    // Timeslice
    cpuTimeSlice("300"),
    timeSlice("2000"),
    ioInterrupt("4000"),
    moniter("100");



    // Getter & Setter
    private String text;
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    // Constructor
    ConstantData(String text){
        this.text = text;
    }

}
