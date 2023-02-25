package persistence;

import model.JobApplication;
import model.JobApplicationTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static model.JobApplicationStatus.*;
import static model.JobApplicationStatus.ACCEPTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Based on JsonSerializationDemo-master project provided by the CPSC 210 teaching team
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
            assertEquals("Sophia's Job Application Tracker", tracker.getName());
            assertEquals(0, tracker.getJobApplications().size());
        } catch (IOException ioe) {
            fail("IOException should not have been thrown.");
        }
    }

    @Test
    public void testWriterTypicalJobApplicationTracker() {
        try {
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

            JsonWriter writer = new JsonWriter("./data/testWriterTypicalJobApplicationTracker.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTypicalJobApplicationTracker.json");
            tracker = reader.read();
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
