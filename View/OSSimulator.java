package View;

import Elements.Process;
import Elements.Program;
import HW.Storage;
import OS.Loader;
import OS.Scheduler;
import OS.InterruptHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Queue;

public class OSSimulator extends JFrame {

    private final DefaultListModel<String> model;
    JScrollPane scrolled;

    private JPanel queuePanel;
    private JPanel centerPanel;
    private JPanel readyQueuePanel;
    private JPanel waitingQueuePanel;
    private JPanel currentProcessPanel;
    private JPanel programPanel;
    private JList<String> programList;

    private JPanel instructionPanel;
    private JPanel logPanel;
    private JPanel logText;

    private Scheduler scheduler;
    private Loader loader;
    private InterruptHandler interruptHandler;
    private Storage storage;

    public OSSimulator(){
        super("OS Simulator");
        this.setSize(1200, 800);
        this.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        queuePanel = new JPanel();
        queuePanel.setLayout(new GridLayout(1, 3));
        queuePanel.add((readyQueuePanel = new JPanel(){
            {
                this.setBorder(BorderFactory.createTitledBorder("Ready Queue"));
            }
        }));
        queuePanel.add((waitingQueuePanel = new JPanel(){
            {
                this.setBorder(BorderFactory.createTitledBorder("Waiting Queue"));
            }
        }));
        queuePanel.add((currentProcessPanel = new JPanel(){
            {
                this.setBorder(BorderFactory.createTitledBorder("current Process"));
            }
        }));


        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 3));

        programPanel = new JPanel();
        programPanel.setLayout(new GridLayout(1, 1));
        programPanel.setBorder(BorderFactory.createTitledBorder("Program"));
        programPanel.add((scrolled = new JScrollPane(programList = new JList<String>((model = new DefaultListModel<String>())))));
        programList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        programList.setLayoutOrientation(JList.VERTICAL);
        programList.setVisibleRowCount(-1);
        programList.setFixedCellHeight(20);
        programList.setSelectionBackground(Color.LIGHT_GRAY);
        programList.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                Program program = storage.getProgram(programList.getSelectedValue());
                if(program != null){
                    loader.load(program);
                }
            }
        });
        centerPanel.add(programPanel);

        instructionPanel = new JPanel(){
            {
                this.setBorder(BorderFactory.createTitledBorder("Running Instruction"));
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            }
        };
        centerPanel.add(instructionPanel);

        logPanel = new JPanel();
        logPanel.setLayout(new GridLayout(1, 1));
        logPanel.add((logText = new JPanel(){
            {
                this.setBorder(BorderFactory.createTitledBorder("Log"));
                this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            }
        }));
        JScrollPane scrollPane = new JScrollPane(logPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 300, 50);
        centerPanel.add(scrollPane);

        this.getContentPane().add(queuePanel, BorderLayout.NORTH);
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
    }

    public void initialize(Loader loader, Scheduler scheduler, InterruptHandler interruptHandler, Storage storage){
        this.scheduler = scheduler;
        this.loader = loader;
        this.interruptHandler = interruptHandler;
        this.storage = storage;

    }

    public void addInstructionLog(String log){
        instructionPanel.add(new JLabel(log));
        instructionPanel.updateUI();
    }

    public void addLog(String log){
        logText.add(new JLabel(log));
        logText.updateUI();
    }

    public void setReadyQueuePanel(Queue<Process> readyQueue){
        readyQueuePanel.removeAll();
        readyQueuePanel.setLayout(new BoxLayout(readyQueuePanel, BoxLayout.X_AXIS));
        for (Process process : readyQueue) {
            readyQueuePanel.add(new JLabel(process.toString()){
                {
                    this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            });
        }
    }
    public void setWaitingQueuePanel(Queue<Process> waitingQueue){
        waitingQueuePanel.removeAll();
        waitingQueuePanel.setLayout(new BoxLayout(waitingQueuePanel, BoxLayout.X_AXIS));
        for (Process process : waitingQueue) {
            waitingQueuePanel.add(new JLabel(process.toString()){
                {
                    this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            });
        }
    }
    public void setCurrentProcess(String process){
        currentProcessPanel.removeAll();
        currentProcessPanel.add(new JLabel(process));
    }

    public void updateQueue(Queue<Process> readyQueue, Queue<Process> waitingQueue, Process currentProcess){
        setReadyQueuePanel(readyQueue);
        setWaitingQueuePanel(waitingQueue);
        if(currentProcess != null) setCurrentProcess(currentProcess.getProcessName());
        queuePanel.updateUI();
    }

    public void updateProgramList(){
        Program[] programs = storage.getPrograms();
        model.removeAllElements();
        for (Program program : programs) {
            model.addElement(program.getProgramName());
        }
        programPanel.updateUI();
    }


    public void addItem(String text) {
        if(text==null||text.length()==0) return;
        model.addElement(text);
        //가장 마지막으로 list 위치 이동
        scrolled.getVerticalScrollBar().setValue(scrolled.getVerticalScrollBar().getMaximum());
    }
}
