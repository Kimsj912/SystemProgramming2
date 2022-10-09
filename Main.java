public class Main {
    public static void main(String[] args){
        Loader loader = new Loader();
        Process process = loader.load("process1");
        Scheduler scheduler = new Scheduler();
        scheduler.exectue(process);
    }


}
