package persistence;

import model.JobApplication;
import model.JobApplicationStatus;
import model.JobApplicationTracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static model.JobApplicationStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Based on JsonSerializationDemo-master project provided by the CPSC 210 teaching team
public class JsonReaderTest extends JsonTest {
    @Test
    public void testReaderMissingFile() {
        JsonReader reader = new JsonReader("./data/missingFile.json");

        try {
            JobApplicationTracker tracker = reader.read();
            fail("IOException should have been thrown.");
        } catch (IOException ioe) {
            // Expected outcome
        }
    }

    @Test
    void testReaderEmptyJobApplicationTracker() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyJobApplicationTracker.json");

        try {
            JobApplicationTracker tracker = reader.read();
            assertEquals("Sophia's Job Application Tracker", tracker.getName());
            assertEquals(0, tracker.getJobApplications().size());
        } catch (IOException ioe) {
            fail("IOException should not have been thrown.");
        }
    }

    @Test
    void testReaderTypicalJobApplicationTracker() {
        JsonReader reader = new JsonReader("./data/testReaderTypicalJobApplicationTracker.json");

        try {
            JobApplicationTracker tracker = reader.read();
            assertEquals("Sophia's Job Application Tracker", tracker.getName());

            ArrayList<JobApplication> jobApplicationList = tracker.getJobApplications();
            assertEquals(4, jobApplicationList.size());

            checkJobApplication("2023-02-05", "Microsoft", "Product Manager",
                    SUBMITTED, jobApplicationList.get(0));
            checkJobApplication("2023-06-20", "Google", "Software Engineer",
                    INTERVIEWED, jobApplicationList.get(1));
            checkJobApplication("2022-12-25", "Apple", "iOS Engineer",
                    REJECTED, jobApplicationList.get(2));
            checkJobApplication("2022-11-28", "Meta", "Tech Lead", ACCEPTED,
                    jobApplicationList.get(3));
        } catch (IOException ioe) {
            fail("IOException should not have been thrown.");
        }
    }
}
