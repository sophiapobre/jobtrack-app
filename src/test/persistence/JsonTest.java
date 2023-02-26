package persistence;

import model.JobApplication;
import model.JobApplicationStatus;
import model.JobApplicationTracker;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Based on JsonSerializationDemo-master project provided by the CPSC 210 teaching team
public class JsonTest {
    protected void checkJobApplicationTrackerNameAndListSize(String name, int size, JobApplicationTracker tracker) {
        assertEquals(name, tracker.getName());
        assertEquals(size, tracker.getJobApplications().size());
    }

    protected void checkJobApplicationFields(String submissionDate, String companyName, String roleName,
                                             JobApplicationStatus status, JobApplication j) {
        assertEquals(LocalDate.parse(submissionDate), j.getSubmissionDate());
        assertEquals(companyName, j.getCompanyName());
        assertEquals(roleName, j.getRoleName());
        assertEquals(status, j.getStatus());
    }
}
