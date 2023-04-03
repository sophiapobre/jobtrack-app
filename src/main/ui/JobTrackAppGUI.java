package ui;

import model.Event;
import model.EventLog;
import model.JobApplication;
import model.JobApplicationTracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Represents the JobTrack Application GUI
public class JobTrackAppGUI extends JFrame {
    private static final int WIDTH = 1000; // the width of this window
    private static final int HEIGHT = 800; // the height of this window
    private static final String JSON_STORE = "./data/jobApplicationTracker.json"; // the file path to user data
    private static final String LOGO_STORE = "./data/jobTrackLogo.png"; // the file path to the JobTrack logo

    private JobApplicationTracker jobApplicationTracker; // the tracker containing the user's job applications
    private JsonReader jsonReader; // the JSON file reader
    private JsonWriter jsonWriter; // the JSON file writer

    private JLabel logoLabel; // the label containing the JobTrack logo
    private JMenuBar menuBar; // the menu bar
    private JPanel trackerPanel; // the panel displaying the job application tracker
    private JTable trackerTable; // the table containing the job application tracker data
    private DefaultTableModel tableModel; // the model for the table data

    private EventLog eventLog; // the event log for the job application tracker

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
     * EFFECTS: initializes the job application tracker with title "Sophia's Job Application Tracker", JSON
     *          file reader, and JSON file writer
     */
    private void initializeFields() {
        jobApplicationTracker = new JobApplicationTracker("Sophia's Job Application Tracker");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        eventLog = EventLog.getInstance();
    }

    /*
     * MODIFIES: this
     * EFFECTS: configures the JFrame window where JobTrackAppGUI will run, adds and displays the JobTrack logo for 3
     *          seconds, displays the load data prompt, and adds menu bar, tracker panel, and tracker table
     */
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addLogoLabel();
        addMenuBar();

        displayLogoForThreeSeconds();
        displayLoadDataPrompt();

        addTrackerPanel();
        addTrackerTable();
        menuBar.setVisible(true);
        setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates and adds the JobTrack logo to the center of the window
     */
    private void addLogoLabel() {
        createLogoLabel();
        add(logoLabel, BorderLayout.CENTER);
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates the JLabel for logoLabel and sets it to contain the JobTrack logo
     */
    private void createLogoLabel() {
        ImageIcon logo = new ImageIcon(LOGO_STORE);
        logoLabel = new JLabel();
        logoLabel.setIcon(logo);
    }

    /*
     * MODIFIES: this
     * EFFECTS: displays the JobTrack logo for 3 seconds
     */
    private void displayLogoForThreeSeconds() {
        setVisible(true);
        waitForThreeSeconds();
        hideComponent(logoLabel);
    }

    /*
     * EFFECTS: pauses program execution for 3 seconds, prints the stack trace in case an InterruptedException is thrown
     */
    private void waitForThreeSeconds() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: asks the user if they want to load their data from file, displays message dialog containing an
     *          outcome message depending on user selection and whether data was loaded successfully
     */
    private void displayLoadDataPrompt() {
        int response = displayYesOrNoPrompt("load");

        if (response == 0) {
            JOptionPane.showMessageDialog(null, "Your data will not be loaded.",
                    "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } else if (response == 1) {
            displayOutcomeMessage(loadJobApplicationTracker());
        }
    }

    /*
     * REQUIRES: action == "save" || action == "load" || action == "delete all"
     * EFFECTS: asks the user if they want to perform the given action, returns 0 if user selects No and 1 if user
     *          selects Yes
     */
    private int displayYesOrNoPrompt(String action) {
        String[] options = {"No", "Yes"};
        int selection = JOptionPane.showOptionDialog(null,
                "Would you like to " + action + " your data?", "Data",
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
    private void addMenuBar() {
        createMenuBar();
        add(menuBar, BorderLayout.NORTH);
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates a menu bar for menuBar with "JobTrack" and "Tracker" options, as well as menu items for
     *          quitting the application, adding a job application, and deleting all job applications; menu bar is
     *          initially set to be hidden
     */
    private void createMenuBar() {
        menuBar = new JMenuBar();
        hideComponent(menuBar);

        JMenu jobTrackOption = new JMenu("JobTrack");
        menuBar.add(jobTrackOption);

        JMenu trackerOption = new JMenu("Tracker");
        menuBar.add(trackerOption);

        JMenuItem quitOption = createQuitMenuItem();
        jobTrackOption.add(quitOption);

        JMenuItem addOption = createAddMenuItem();
        JMenuItem deleteOption = createDeleteAllMenuItem();
        trackerOption.add(addOption);
        trackerOption.add(deleteOption);
    }

    /*
     * MODIFIES: component
     * EFFECTS: hides the given component
     */
    private void hideComponent(JComponent component) {
        component.setVisible(false);
    }

    /*
     * EFFECTS: creates and returns a menu item for quitting the application
     */
    private JMenuItem createQuitMenuItem() {
        JMenuItem quitOption = new JMenuItem("Quit JobTrack");
        quitOption.addActionListener(e -> {
            displaySaveDataPrompt();
            printEventLog();
            System.exit(0);
        });

        return quitOption;
    }

    /*
     * EFFECTS: prints all events in the event log to the console
     */
    private void printEventLog() {
        for (Event event : eventLog) {
            System.out.println(event);
        }
    }

    /*
     * EFFECTS: creates and returns a menu item for adding a new job application
     */
    private JMenuItem createAddMenuItem() {
        JMenuItem addOption = new JMenuItem("Add Job Application");
        addOption.addActionListener(e -> displayAddJobApplicationPrompts());
        return addOption;
    }

    /*
     * EFFECTS: displays 3 prompts for the user to enter the submission date, company name, and role name and
     *          adds the job application with the given inputs to the tracker if user does not press cancel in any of
     *          the 3 prompts, otherwise returns without updating the tracker
     */
    private void displayAddJobApplicationPrompts() {
        String submissionDate = displaySubmissionDatePrompt();
        if (submissionDate == null) {
            return;
        }

        String companyName = displayCompanyNamePrompt();
        if (companyName == null) {
            return;
        }

        String roleName = displayRoleNamePrompt();
        if (roleName == null) {
            return;
        }

        addJobApplicationToTracker(submissionDate, companyName, roleName);
    }

    /*
     * REQUIRES: given parameters are not empty and not null, submissionDate is in format YYYY-MM-DD, and a job
     *           application with given parameters are not already in the tracker
     * MODIFIES: this
     * EFFECTS: creates a new job application and adds it to the tracker, adds a row containing the given parameters
     *          and a default status of "SUBMITTED" to the tracker table
     */
    private void addJobApplicationToTracker(String submissionDate, String companyName, String roleName) {
        jobApplicationTracker.add(submissionDate, companyName, roleName);
        addRowToTable(submissionDate, companyName, roleName, "SUBMITTED");
    }

    /*
     * EFFECTS: prompts user to enter a submission date; returns a null string if user pressed cancel, otherwise
     *          returns the submission date entered by user
     */
    private String displaySubmissionDatePrompt() {
        String submissionDate = JOptionPane.showInputDialog("Please enter the date you submitted this "
                                                            + "application (YYYY-MM-DD):");
        return submissionDate;
    }

    /*
     * EFFECTS: prompts user to enter a company name; returns a null string if user pressed cancel, otherwise
     *          returns the company name entered by user
     */
    private String displayCompanyNamePrompt() {
        String companyName = JOptionPane.showInputDialog("Please enter the name of the company you applied to:");
        return companyName;
    }

    /*
     * EFFECTS: prompts the user to enter a role name; returns a null string if user pressed cancel, otherwise
     *          returns the role name entered by user
     */
    private String displayRoleNamePrompt() {
        String roleName = JOptionPane.showInputDialog("Please enter the name of the role you applied for:");
        return roleName;
    }

    /*
     * EFFECTS: creates and returns a menu item for deleting all job applications
     */
    private JMenuItem createDeleteAllMenuItem() {
        JMenuItem deleteOption = new JMenuItem("Delete All Job Applications");
        deleteOption.addActionListener(e -> displayDeleteJobApplicationPrompt());
        return deleteOption;
    }

    /*
     * MODIFIES: this
     * EFFECTS: asks the user if they want to delete all their job applications, displays message dialog containing
     *          confirmation message depending on user response; deletes all job applications in the tracker and updates
     *          the tracker table if user selected yes
     */
    private void displayDeleteJobApplicationPrompt() {
        int response = displayYesOrNoPrompt("delete all");

        displayDeleteConfirmationMessage(response);

        if (response == 1) {
            deleteAllJobApplications();
        }
    }

    /*
     * REQUIRES: response == 0 || response == 1
     * EFFECTS: displays a confirmation message depending on whether user requested to delete all their data or not
     */
    private void displayDeleteConfirmationMessage(int response) {
        String not = "";

        if (response == 0) {
            not = "not ";
        }

        JOptionPane.showMessageDialog(null, "Your data will " + not + "be deleted.",
                "Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    /*
     * MODIFIES: this
     * EFFECTS: empties the job application tracker and the table displaying the tracker data
     */
    private void deleteAllJobApplications() {
        jobApplicationTracker.removeAllJobApplications();
        tableModel.setRowCount(0);
    }

    /*
     * MODIFIES: this
     * EFFECTS: asks the user if they want to save their data to file, displays message dialog containing outcome
     *          message depending on user selection and whether data was saved successfully
     */
    private void displaySaveDataPrompt() {
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

    /*
     * MODIFIES: this
     * EFFECTS: creates a panel for storing the tracker and adds it to the center of the window
     */
    private void addTrackerPanel() {
        createTrackerPanel();
        add(trackerPanel, BorderLayout.CENTER);
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates a JPanel for trackerPanel and sets the layout to BorderLayout
     */
    private void createTrackerPanel() {
        trackerPanel = new JPanel();
        trackerPanel.setLayout(new BorderLayout());
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates a table for storing tracker data and adds it to the center of trackerPanel
     */
    private void addTrackerTable() {
        createTable();
        trackerPanel.add(new JScrollPane(trackerTable), BorderLayout.CENTER);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes a table model and creates a non-editable JTable for trackerTable for storing tracker data,
     *          populates the tracker table with the data from the model
     */
    private void createTable() {
        initializeTableModel();
        trackerTable = new JTable(tableModel);
        trackerTable.setEnabled(false);
        populateTable();
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates a DefaultTableModel for tableModel and adds columns for "Submission Date", "Company", "Role",
     *          and "Status"
     */
    private void initializeTableModel() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Submission Date");
        tableModel.addColumn("Company");
        tableModel.addColumn("Role");
        tableModel.addColumn("Status");
    }

    /*
     * MODIFIES: this
     * EFFECTS: populates the tracker table with data from jobApplicationTracker
     */
    private void populateTable() {
        int numJobApplications = jobApplicationTracker.getJobApplications().size();
        for (int i = 0; i < numJobApplications; i++) {
            addJobApplicationDataToTable(i);
        }
    }

    /*
     * REQUIRES: index >= 0 && index < the number of job applications in the tracker
     * MODIFIES: this
     * EFFECTS: adds the data of the job application at the given index to the tracker table
     */
    private void addJobApplicationDataToTable(int index) {
        ArrayList<JobApplication> jobApplicationList = jobApplicationTracker.getJobApplications();

        String submissionDate = jobApplicationList.get(index).getSubmissionDate().toString();
        String company = jobApplicationList.get(index).getCompanyName();
        String role = jobApplicationList.get(index).getRoleName();
        String status = String.valueOf(jobApplicationList.get(index).getStatus());

        addRowToTable(submissionDate, company, role, status);
    }

    /*
     * REQUIRES: a job application with given submissionDate, company, role, and status are already in the tracker
     * MODIFIES: this
     * EFFECTS: adds a row to the table that represents a job application with the given parameters
     */
    private void addRowToTable(String submissionDate, String company, String role, String status) {
        tableModel.addRow(new Object[]{submissionDate, company, role, status});
    }
}
