package persistence;

import model.JobApplication;
import model.JobApplicationTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static model.JobApplicationStatus.*;
import static model.JobApplicationStatus.ACCEPTED;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    private JobApplicationTracker tracker;

    @BeforeEach
    public void setUp() {
        tracker = new JobApplicationTracker("Sophia's Job Application Tracker");
    }

    @Test
    public void testWriterInvalidFileName() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException should have been thrown.");
        } catch (IOException ioe) {
            // Expected outcome
        }
    }

    @Test
    public void testWriterEmptyJobApplicationTracker() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJobApplicationTracker.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJobApplicationTracker.json");
            tracker = reader.read();
            checkJobApplicationTrackerNameAndListSize("Sophia's Job Application Tracker", 0, tracker);
        } catch (IOException ioe) {
            fail("IOException should not have been thrown.");
        }
    }

    @Test
    public void testWriterTypicalJobApplicationTracker() {
        try {
            addJobApplicationsToTracker();

            JsonWriter writer = new JsonWriter("./data/testWriterTypicalJobApplicationTracker.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTypicalJobApplicationTracker.json");
            tracker = reader.read();
            checkJobApplicationTrackerNameAndListSize("Sophia's Job Application Tracker", 4, tracker);

            ArrayList<JobApplication> jobApplicationList = tracker.getJobApplications();
            checkJobApplicationFields("2023-02-05", "Microsoft", "Product Manager",
                    SUBMITTED, jobApplicationList.get(0));
            checkJobApplicationFields("2023-06-20", "Google", "Software Engineer",
                    INTERVIEWED, jobApplicationList.get(1));
            checkJobApplicationFields("2022-12-25", "Apple", "iOS Engineer",
                    REJECTED, jobApplicationList.get(2));
            checkJobApplicationFields("2022-11-28", "Meta", "Tech Lead", ACCEPTED,
                    jobApplicationList.get(3));
        } catch (IOException ioe) {
            fail("IOException should not have been thrown.");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds 4 different job applications to the tracker
     */
    private void addJobApplicationsToTracker() {
        JobApplication j1 = new JobApplication("2023-02-05", "Microsoft", "Product Manager");
        JobApplication j2 = new JobApplication("2023-06-20", "Google", "Software Engineer");
        JobApplication j3 = new JobApplication("2022-12-25", "Apple", "iOS Engineer");
        JobApplication j4 = new JobApplication("2022-11-28", "Meta", "Tech Lead");

        j2.setStatus(INTERVIEWED);
        j3.setStatus(REJECTED);
        j4.setStatus(ACCEPTED);

        tracker.add(j1);
        tracker.add(j2);
        tracker.add(j3);
        tracker.add(j4);
    }
}
