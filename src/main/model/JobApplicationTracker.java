package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a collection of job applications
public class JobApplicationTracker implements Writable {
    private String name; // the name of the job application tracker
    private ArrayList<JobApplication> jobApplicationList; // the list of job applications

    /*
     * EFFECTS: creates a new job application tracker with the given name and an empty list of job applications
     */
    public JobApplicationTracker(String name) {
        this.name = name;
        jobApplicationList = new ArrayList<>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns true if j is successfully added to the job application tracker, false otherwise
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
     * EFFECTS: returns true if j is successfully removed from the job application tracker, false otherwise
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
     * EFFECTS: returns the name of the job application tracker
     */
    public String getName() {
        return name;
    }

    /*
     * EFFECTS: returns a list of all job applications in the tracker
     */
    public ArrayList<JobApplication> getJobApplications() {
        return jobApplicationList;
    }

    /*
     * EFFECTS: returns a list of job applications with status set to given status in the tracker
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

    /*
     * EFFECTS: returns this as a JSON object
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("jobApplicationList", jobApplicationListToJson());
        return json;
    }

    /*
     * EFFECTS: returns the list of job applications in the tracker as a JSON array
     */
    private JSONArray jobApplicationListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (JobApplication j : jobApplicationList) {
            jsonArray.put(j.toJson());
        }

        return jsonArray;
    }
}