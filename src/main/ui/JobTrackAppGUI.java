package ui;

import model.JobApplicationTracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the JobTrack Application GUI
public class JobTrackAppGUI extends JFrame {
    public static final int WIDTH = 1000; // the width of this window
    public static final int HEIGHT = 800; // the height of this window
    private static final String JSON_STORE = "./data/jobApplicationTracker.json"; // the file path to user data
    private static final String LOGO_STORE = "./data/jobTrackLogo.png"; // the file path to the JobTrack logo

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
        displayLoadDataPrompt();
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
     * EFFECTS: asks the user if they want to load their data from file, displays message dialog containing message
     *          depending on user selection and whether data was loaded successfully
     */
    public void displayLoadDataPrompt() {
        int response = displayYesOrNoPrompt("load");

        if (response == 0) {
            JOptionPane.showMessageDialog(null, "Your data will not be loaded.",
                    "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } else if (response == 1) {
            displayOutcomeMessage(loadJobApplicationTracker());
        }
    }

    /*
     * EFFECTS: asks the user if they want to either save or load their data from file, depending on given action;
     *          returns 0 if user selects no and 1 if user selects yes
     */
    private int displayYesOrNoPrompt(String action) {
        String[] options = {"No", "Yes"};
        int selection = JOptionPane.showOptionDialog(null,
                "Would you like to " + action + " your data?", "Load Data",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
        return selection;
    }

    /*
     * EFFECTS: displays the given outcome in either a confirmation or error message, depending on whether given outcome
     *          contains "Successfully"
     */
    private void displayOutcomeMessage(String outcome) {
        if (outcome.contains("Successfully")) {
            JOptionPane.showMessageDialog(null, outcome, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, outcome, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads job application tracker from file, returns message indicating whether file was successfully read
     * Based on JsonSerializationDemo-master project provided by the CPSC 210 teaching team
     */
    private String loadJobApplicationTracker() {
        try {
            jobApplicationTracker = jsonReader.read();
            return "Successfully loaded '" + jobApplicationTracker.getName() + "'.";
        } catch (IOException ioe) {
            return "ERROR: '" + JSON_STORE + "' could not be read. Your data will not be loaded.";
        }
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
     * EFFECTS: creates a menu bar with "JobTrack" and "Tracker" options, as well as menu items for quitting, adding
     *          a job application, and deleting a job application
     */
    public void createMenuBar() {
        menuBar = new JMenuBar();
        hideComponent(menuBar);

        JMenu jobTrackOption = new JMenu("JobTrack");
        menuBar.add(jobTrackOption);

        JMenu trackerOption = new JMenu("Tracker");
        menuBar.add(trackerOption);

        JMenuItem quitOption = new JMenuItem("Quit JobTrack");
        quitOption.addActionListener(e -> displaySaveDataPrompt());
        jobTrackOption.add(quitOption);

        JMenuItem addOption = new JMenuItem("Add Job Application");
        JMenuItem deleteOption = new JMenuItem("Delete Job Application");
        trackerOption.add(addOption);
        trackerOption.add(deleteOption);
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
     * EFFECTS: asks the user if they want to save their data to file, displays message dialog containing message
     *          depending on user selection and whether data was saved successfully
     */
    public void displaySaveDataPrompt() {
        int response = displayYesOrNoPrompt("save");

        if (response == 0) {
            JOptionPane.showMessageDialog(null, "Your data will not be saved.",
                    "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } else if (response == 1) {
            displayOutcomeMessage(saveJobApplicationTracker());
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: makes all components visible
     */
    public void showAllComponents() {
        menuBar.setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: saves job application tracker to file, returns message indicating whether file was successfully saved
     * Based on JsonSerializationDemo-master project provided by the CPSC 210 teaching team
     */
    private String saveJobApplicationTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(jobApplicationTracker);
            jsonWriter.close();
            return "Successfully saved '" + jobApplicationTracker.getName() + "'.";
        } catch (FileNotFoundException e) {
            return "ERROR: " + JSON_STORE + " could not be written to. Your data will not be saved.";
        }
    }
}
