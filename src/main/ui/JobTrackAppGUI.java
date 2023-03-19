package ui;

import model.JobApplicationTracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;

// Represents the JobTrack Application GUI
public class JobTrackAppGUI extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    private static final String JSON_STORE = "./data/jobApplicationTracker.json";
    private static final String LOGO_STORE = "./data/jobTrackLogo.png";

    private JobApplicationTracker jobApplicationTracker; // the tracker containing the user's job applications
    private JsonReader jsonReader; // the JSON file reader
    private JsonWriter jsonWriter; // the JSON file writer

    private JLabel logoLabel; // the label containing the JobTrack logo
    private JMenuBar menuBar; // the menu bar

    /*
     * MODIFIES: this
     * EFFECTS: creates a JobTrackAppGUI with title "JobTrack", initializes fields and graphics
     */
    public JobTrackAppGUI() {
        super("JobTrack");
        initializeFields();
        initializeGraphics();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the job application list, scanner, and JSON file reader
     */
    public void initializeFields() {
        jobApplicationTracker = new JobApplicationTracker("Sophia's Job Application Tracker");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up the JFrame window where JobTrackAppGUI will run, adds components, displays logo for 3 seconds
     */
    public void initializeGraphics() {
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addLogo();
        addMenuBar();

        displayLogoForThreeSeconds();

        showAllComponents();
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates and adds a logo to the center of the window
     */
    public void addLogo() {
        createLogo();
        add(logoLabel, BorderLayout.CENTER);
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates the JLabel component containing the JobTrack logo
     */
    public void createLogo() {
        ImageIcon logo = new ImageIcon(LOGO_STORE);
        logoLabel = new JLabel();
        logoLabel.setIcon(logo);
    }

    /*
     * MODIFIES: this
     * EFFECTS: displays the JobTrack logo for 3 seconds
     */
    public void displayLogoForThreeSeconds() {
        setVisible(true);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hideComponent(logoLabel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates and adds a menu bar to the north of the window
     */
    public void addMenuBar() {
        createMenuBar();
        add(menuBar, BorderLayout.NORTH);
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates a menu bar with "Add", "Delete", and "Quit" options
     */
    public void createMenuBar() {
        menuBar = new JMenuBar();
        hideComponent(menuBar);

        JMenu addOption = new JMenu("Add");
        JMenu deleteOption = new JMenu("Delete");
        JMenu quitOption = new JMenu("Quit");

        menuBar.add(addOption);
        menuBar.add(deleteOption);
        menuBar.add(quitOption);
    }

    /*
     * MODIFIES: component
     * EFFECTS: hides the given component
     */
    public void hideComponent(JComponent component) {
        component.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS: makes all components visible
     */
    public void showAllComponents() {
        menuBar.setVisible(true);
    }
}