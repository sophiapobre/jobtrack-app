package persistence;

import model.JobApplication;
import model.JobApplicationStatus;
import model.JobApplicationTracker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads the tracker from JSON data stored in file
// Based on JsonSerializationDemo-master project provided by the CPSC 210 teaching team
public class JsonReader {
    private String source; // the source file

    /*
     * EFFECTS: constructs a reader to read from the source file
     */
    public JsonReader(String source) {
        this.source = source;
    }

    /*
     * EFFECTS: reads the job application tracker from file and returns it, throws IOException if an error occurs while
     *          the file is being read
     */
    public JobApplicationTracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJobApplicationTracker(jsonObject);
    }

    /*
     * EFFECTS: reads and returns the given source file as a string, throws IOException if an error occurs while the
     *          file is being read
     */
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    /*
     * EFFECTS: parses the job application tracker from the given jsonObject and returns it
     */
    private JobApplicationTracker parseJobApplicationTracker(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        JobApplicationTracker tracker = new JobApplicationTracker(name);
        addAllJobApplicationsToTracker(tracker, jsonObject);
        return tracker;
    }

    /*
     * MODIFIES: tracker
     * EFFECTS: parses all job applications from the given JSON object and adds them to the job application tracker
     */
    private void addAllJobApplicationsToTracker(JobApplicationTracker tracker, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("jobApplicationList");
        for (Object json : jsonArray) {
            JSONObject jobApplication = (JSONObject) json;
            addSingleJobApplicationToTracker(tracker, jobApplication);
        }
    }

    /*
     * MODIFIES: tracker
     * EFFECTS: parses a single job application from the given JSON object and adds it to the job application tracker
     */
    private void addSingleJobApplicationToTracker(JobApplicationTracker tracker, JSONObject jsonObject) {
        String submissionDate = jsonObject.getString("submissionDate");
        String companyName = jsonObject.getString("companyName");
        String roleName = jsonObject.getString("roleName");
        JobApplicationStatus status = JobApplicationStatus.valueOf(jsonObject.getString("status"));

        JobApplication jobApplication = new JobApplication(submissionDate, companyName, roleName);
        jobApplication.setStatus(status);

        tracker.add(jobApplication);
    }
}