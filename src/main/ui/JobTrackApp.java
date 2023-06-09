package ui;

import model.JobApplication;
import model.JobApplicationTracker;
import model.JobApplicationStatus;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Represents the job application tracker application
public class JobTrackApp {
    private static final String YES_COMMAND = "yes";
    private static final String NO_COMMAND = "no";
    private static final String ADD_COMMAND = "add";
    private static final String DELETE_COMMAND = "delete";
    private static final String UPDATE_STATUS_COMMAND = "update";
    private static final String VIEW_TRACKER_COMMAND = "tracker";
    private static final String VIEW_STATS_COMMAND = "stats";
    private static final String EXIT_COMMAND = "exit";
    private static final String JSON_STORE = "./data/jobApplicationTracker.json";

    private Scanner input; // the scanner for user input
    private JobApplicationTracker jobApplicationTracker; // the tracker containing the user's job applications
    private JsonReader jsonReader; // the JSON file reader
    private JsonWriter jsonWriter; // the JSON file writer

    /*
     * MODIFIES: this
     * EFFECTS: runs the JobTrack application
     */
    public JobTrackApp() {
        runJobTrackApp();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the JobTrack application, presents user with the option to load data from file,
     *          displays the main menu, and responds to given user command
     */
    public void runJobTrackApp() {
        boolean continueRunning = true;
        String command = null;

        initialize();

        System.out.println();
        System.out.println("Welcome to JobTrack!");
        System.out.println();
        showOptionToLoad();

        while (continueRunning) {
            showMainMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals(EXIT_COMMAND)) {
                showOptionToSave();
                continueRunning = false;
            } else {
                executeCommand(command);
            }
        }

        System.out.println("Thank you for using JobTrack.");
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the job application list, scanner, and JSON file reader
     */
    private void initialize() {
        jobApplicationTracker = new JobApplicationTracker("Sophia's Job Application Tracker");
        input = new Scanner(System.in);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    /*
     * MODIFIES: this
     * EFFECTS: presents user with the option to load data from file, loads data from file if user says yes
     *          and prints confirmation message if user says no
     */
    private void showOptionToLoad() {
        System.out.println("Would you like to load your data from file? Type yes or no and press Enter:");
        String command = input.nextLine();
        command = command.toLowerCase();

        if (command.equals(YES_COMMAND)) {
            loadJobApplicationTracker();
        } else if (command.equals(NO_COMMAND)) {
            System.out.println("Your data will not be loaded from file.");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads job application tracker from file, prints error message in case of IOException
     * Based on JsonSerializationDemo-master project provided by the CPSC 210 teaching team
     */
    private void loadJobApplicationTracker() {
        try {
            jobApplicationTracker = jsonReader.read();
            System.out.println("Successfully loaded '" + jobApplicationTracker.getName() + "' from " + JSON_STORE
                    + ".");
        } catch (IOException ioe) {
            System.out.println("ERROR: '" + JSON_STORE + "' could not be read.");
        }
    }

    /*
     * EFFECTS: prints out the main menu containing available user options
     */
    private void showMainMenu() {
        System.out.println();
        System.out.println("Select an option by typing the keyword to the left of '->' and pressing Enter:");

        System.out.println(ADD_COMMAND + " -> add a job application to your tracker");

        if (jobApplicationTracker.getJobApplications().size() > 0) {
            System.out.println(DELETE_COMMAND + " -> delete a job application from your tracker");
            System.out.println(UPDATE_STATUS_COMMAND + " -> update the status of an existing job application");
            System.out.println(VIEW_TRACKER_COMMAND + " -> view all job applications in your tracker");
            System.out.println(VIEW_STATS_COMMAND + " -> view job application statistics");
        }

        System.out.println(EXIT_COMMAND + " -> exit program");
        System.out.println();

        if (jobApplicationTracker.getJobApplications().size() == 0) {
            System.out.println("TIP: Unlock more options by adding your first job application now!");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: presents user with the option to save data, saves data to file if user says yes and prints
     *          confirmation message if user says no
     */
    private void showOptionToSave() {
        System.out.println("Would you like to save your work before quitting? Type yes or no and press Enter:");
        String command = input.nextLine();
        command = command.toLowerCase();

        if (command.equals(YES_COMMAND)) {
            saveJobApplicationTracker();
        } else if (command.equals(NO_COMMAND)) {
            System.out.println("Your data will not be saved.");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: saves the job application tracker to file, prints error message in case of FileNotFoundException
     * Based on JsonSerializationDemo-master project provided by the CPSC 210 teaching team
     */
    private void saveJobApplicationTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(jobApplicationTracker);
            jsonWriter.close();
            System.out.println("Successfully saved '" + jobApplicationTracker.getName() + "' to " + JSON_STORE + ".");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: " + JSON_STORE + " could not be written to. Your data will not be saved.");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: executes the command given by the user in the main menu
     */
    private void executeCommand(String command) {
        if (command.equals(ADD_COMMAND)) {
            addJobApplication();
        } else if (command.equals(DELETE_COMMAND)) {
            deleteJobApplication();
        } else if (command.equals(UPDATE_STATUS_COMMAND)) {
            updateStatus();
        } else if (command.equals(VIEW_TRACKER_COMMAND)) {
            printAllJobApplications();
        } else if (command.equals(VIEW_STATS_COMMAND)) {
            printJobApplicationStatistics();
        } else {
            System.out.println("Invalid command. Please try again.");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: allows the user to add a job application to the tracker
     */
    private void addJobApplication() {
        System.out.println();
        System.out.println("Type the name of the company you applied to and press Enter:");
        String company = input.nextLine();
        System.out.println("Type the name of the role you applied for and press Enter:");
        String role = input.nextLine();
        System.out.println("Type the date when you submitted this application in format YYYY-MM-DD and press Enter:");
        String date = input.nextLine();
        System.out.println();

        JobApplication j = new JobApplication(date, company, role);

        if (jobApplicationTracker.add(j)) {
            System.out.println("Successfully added this job application to your tracker!");
        } else {
            System.out.println("Unable to add this job application because it is already in the tracker.");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: allows the user to delete a job application from the tracker
     */
    private void deleteJobApplication() {
        JobApplication selectedJobApplication = handleJobApplicationSelection("delete");

        if (jobApplicationTracker.remove(selectedJobApplication)) {
            System.out.println("Successfully removed this job application from your tracker!");
        } else {
            System.out.println("Unable to remove this job application because it is not currently in your tracker.");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: prompts the user to select a job application from the tracker and returns the job application
     *          selected by the user
     */
    private JobApplication handleJobApplicationSelection(String userAction) {
        printAllJobApplications();
        System.out.println("Type the ID of the job application you'd like to " + userAction + " and press Enter:");
        int givenId = input.nextInt();
        input.nextLine();

        JobApplication j = jobApplicationTracker.getJobApplications().get(givenId);
        System.out.println();
        System.out.println("You have chosen to " + userAction + ":");
        System.out.println(givenId + " | " + j.getSubmissionDate() + " | " + j.getCompanyName() + " | "
                + j.getRoleName() + " | " + j.getStatus());
        System.out.println();
        return j;
    }

    /*
     * MODIFIES: this
     * EFFECTS: allows the user to update the status of a job application in the tracker
     */
    private void updateStatus() {
        JobApplication selectedJobApplication = handleJobApplicationSelection("update");

        System.out.println("Select one of the following as this application's new status and press Enter:");
        for (JobApplicationStatus s : JobApplicationStatus.values()) {
            System.out.println(s);
        }
        System.out.println();

        String givenStatus = input.nextLine();
        JobApplicationStatus newStatus = JobApplicationStatus.valueOf(givenStatus.toUpperCase());
        System.out.println();

        if (!selectedJobApplication.getStatus().equals(newStatus)) {
            selectedJobApplication.setStatus(newStatus);
            System.out.println("Successfully updated the status of this job application!");
        } else {
            System.out.println("Unable to update status because the status is already set to " + newStatus + ".");
        }
    }

    /*
     * EFFECTS: prints out the list of all job applications
     */
    private void printAllJobApplications() {
        int id = 0;

        System.out.println();
        System.out.println("ID | DATE APPLIED | COMPANY | ROLE | STATUS");

        for (JobApplication j : jobApplicationTracker.getJobApplications()) {
            System.out.println(id + " | " + j.getSubmissionDate() + " | " + j.getCompanyName() + " | "
                    + j.getRoleName() + " | " + j.getStatus());
            id++;
        }
        System.out.println();
    }

    /*
     * EFFECTS: prints the job application statistics, including the number and percentage of job applications under
     *          each status category
     */
    private void printJobApplicationStatistics() {
        System.out.println();
        System.out.println("Number of Job Applications Per Status Category");
        for (JobApplicationStatus s : JobApplicationStatus.values()) {
            System.out.println(s + ": " + jobApplicationTracker.count(s));
        }

        System.out.println();
        System.out.println("Percentage of Total Job Applications Per Status Category");
        for (JobApplicationStatus s : JobApplicationStatus.values()) {
            System.out.println(s + ": " + String.format("%.2f", jobApplicationTracker.calculatePercentage(s) * 100.0)
                                + "%");
        }
    }
}