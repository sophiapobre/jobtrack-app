package ui;

import model.JobApplication;
import model.JobApplicationList;
import model.JobApplicationStatus;

import java.util.Scanner;

// Job application tracker application
public class JobTrackApp {
    private static final String ADD_COMMAND = "add";
    private static final String DELETE_COMMAND = "delete";
    private static final String UPDATE_STATUS_COMMAND = "update";
    private static final String VIEW_TRACKER_COMMAND = "tracker";
    private static final String VIEW_STATS_COMMAND = "stats";
    private static final String EXIT_COMMAND = "exit";

    private Scanner input; // the scanner for user input
    private JobApplicationList jobApplicationList; // the list of job applications in the tracker

    public JobTrackApp() {
        runJobTrackApp();
    }

    /*
     * EFFECTS: runs the JobTrack application
     */
    public void runJobTrackApp() {
        boolean continueRunning = true;
        String command = null;

        init();

        while (continueRunning) {
            showMainMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals(EXIT_COMMAND)) {
                continueRunning = false;
            } else {
                executeCommand(command);
            }
        }

        System.out.println("Thank you for using JobTrack.");
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the job application list and scanner
     */
    private void init() {
        jobApplicationList = new JobApplicationList();
        input = new Scanner(System.in);
    }

    /*
     * EFFECTS: prints out the main menu containing available user options
     */
    private void showMainMenu() {
        System.out.println();
        System.out.println("Welcome to JobTrack!");
        System.out.println();
        System.out.println("Select an option by typing the keyword to the left of '->' and pressing Enter:");
        System.out.println(ADD_COMMAND + " -> add a job application to your tracker");

        if (jobApplicationList.getJobApplications().size() > 0) {
            System.out.println(DELETE_COMMAND + " -> delete a job application from your tracker");
            System.out.println(UPDATE_STATUS_COMMAND + " -> update the status of an existing job application");
            System.out.println(VIEW_TRACKER_COMMAND + " -> view all job applications in your tracker");
            System.out.println(VIEW_STATS_COMMAND + " -> view job application statistics");
        }

        System.out.println(EXIT_COMMAND + " -> exit program");
        System.out.println();

        if (jobApplicationList.getJobApplications().size() == 0) {
            System.out.println("TIP: Unlock more options by adding your first job application now!");
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

        if (jobApplicationList.add(j)) {
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

        if (jobApplicationList.remove(selectedJobApplication)) {
            System.out.println("Successfully removed this job application from your tracker!");
        } else {
            System.out.println("Unable to remove this job application because it is not currently in your tracker.");
        }
    }

    /*
     * EFFECTS: prompts the user to select a job application from the tracker and returns the job application
     *          selected by the user
     */
    private JobApplication handleJobApplicationSelection(String userAction) {
        printAllJobApplications();
        System.out.println("Type the ID of the job application you'd like to " + userAction + " and press Enter:");
        int givenId = input.nextInt();
        input.nextLine();

        JobApplication j = jobApplicationList.getJobApplications().get(givenId);
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

        for (JobApplication j : jobApplicationList.getJobApplications()) {
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
            System.out.println(s + ": " + jobApplicationList.count(s));
        }

        System.out.println();
        System.out.println("Percentage of Total Job Applications Per Status Category");
        for (JobApplicationStatus s : JobApplicationStatus.values()) {
            System.out.println(s + ": " + String.format("%.2f", jobApplicationList.calculatePercentage(s) * 100.0)
                                + "%");
        }
    }
}