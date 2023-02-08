package model;

import java.util.ArrayList;

// Represents a list of job applications
public class JobApplicationList {
    private ArrayList<JobApplication> jobApplicationList; // the list of job applications

    /*
     * EFFECTS: creates a new job application list with an empty list of job applications
     */
    public JobApplicationList() {
        jobApplicationList = new ArrayList<>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns true if j is successfully added to the job application list, false otherwise
     */
    public boolean add(JobApplication j) {
        if (jobApplicationList.contains(j)) {
            return false;
        } else {
            jobApplicationList.add(j);
            return true;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns true if j is successfully removed from the job application list, false otherwise
     */
    public boolean remove(JobApplication j) {
        if (jobApplicationList.contains(j)) {
            jobApplicationList.remove(j);
            return true;
        } else {
            return false;
        }
    }

    /*
     * EFFECTS: returns a list of all job applications
     */
    public ArrayList<JobApplication> getJobApplications() {
        return jobApplicationList;
    }

    /*
     * EFFECTS: returns a list of job applications with status set to given status
     */
    private ArrayList<JobApplication> getJobApplications(JobApplicationStatus status) {
        ArrayList<JobApplication> filteredList = new ArrayList<JobApplication>();
        for (JobApplication j : jobApplicationList) {
            if (j.getStatus().equals(status)) {
                filteredList.add(j);
            }
        }
        return filteredList;
    }

    /*
     * EFFECTS: returns the number of applications with status set to given status
     */
    public int count(JobApplicationStatus status) {
        return getJobApplications(status).size();
    }

    /*
     * REQUIRES: total number of job applications > 0
     * EFFECTS: returns the percentage of job applications with status set to given status
     */
    public double calculatePercentage(JobApplicationStatus status) {
        return (double) count(status) / jobApplicationList.size();
    }
}
