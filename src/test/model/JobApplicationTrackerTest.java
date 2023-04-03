package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.JobApplicationStatus.*;
import static org.junit.jupiter.api.Assertions.*;

// Represents the tests for the JobApplicationTracker class
public class JobApplicationTrackerTest {
    private JobApplicationTracker tracker;
    private JobApplication j1;
    private JobApplication j2;
    private JobApplication j3;
    private JobApplication j4;

    @BeforeEach
    public void setUp() {
        tracker = new JobApplicationTracker("Sophia's Job Application Tracker");
        j1 = new JobApplication("2023-02-05", "Microsoft", "Product Manager");
        j2 = new JobApplication("2023-06-20", "Google", "Software Engineer");
        j3 = new JobApplication("2022-12-25", "Apple", "iOS Engineer");
        j4 = new JobApplication("2022-11-28", "Meta", "Tech Lead");
    }

    @Test
    public void testConstructor() {
        assertEquals("Sophia's Job Application Tracker", tracker.getName());
        assertNotNull(tracker.getJobApplications());
        assertEquals(0, tracker.getJobApplications().size());
    }

    @Test
    public void testAddOneJobApplication() {
        assertTrue(tracker.add(j1));
        assertTrue(tracker.getJobApplications().contains(j1));
        assertEquals(1, tracker.getJobApplications().size());
    }

    @Test
    public void testAddMultipleJobApplications() {
        assertTrue(tracker.add(j1));
        assertTrue(tracker.add(j2));
        assertTrue(tracker.add(j3));
        assertTrue(tracker.getJobApplications().contains(j1));
        assertTrue(tracker.getJobApplications().contains(j2));
        assertTrue(tracker.getJobApplications().contains(j3));
        assertEquals(3, tracker.getJobApplications().size());
    }

    @Test
    public void testAddSameJobApplicationTwice() {
        assertTrue(tracker.add(j1));
        assertFalse(tracker.add(j1));
        assertTrue(tracker.getJobApplications().contains(j1));
        assertEquals(1, tracker.getJobApplications().size());
    }

    @Test
    public void testAddUsingDataAsParameters() {
        tracker.add("2020-01-01", "Coinbase", "Developer");
        JobApplication j = new JobApplication("2020-01-01", "Coinbase", "Developer");
        assertTrue(tracker.getJobApplications().contains(j));
        assertEquals(1, tracker.getJobApplications().size());
    }

    @Test
    public void testRemoveJobApplicationInTracker() {
        addAllJobApplicationsToTracker();
        assertTrue(tracker.remove(j2));
        assertFalse(tracker.getJobApplications().contains(j2));
        assertEquals(3, tracker.getJobApplications().size());
    }

    @Test
    public void testRemoveJobApplicationNotInTracker() {
        tracker.add(j1);
        tracker.add(j2);
        assertFalse(tracker.remove(j3));
        assertFalse(tracker.getJobApplications().contains(j3));
        assertEquals(2, tracker.getJobApplications().size());
    }

    @Test
    public void testRemoveJobApplicationsUntilEmpty() {
        addAllJobApplicationsToTracker();
        assertTrue(tracker.remove(j2));
        assertTrue(tracker.remove(j1));
        assertTrue(tracker.remove(j4));
        assertTrue(tracker.remove(j3));
        assertFalse(tracker.getJobApplications().contains(j1));
        assertFalse(tracker.getJobApplications().contains(j2));
        assertFalse(tracker.getJobApplications().contains(j3));
        assertFalse(tracker.getJobApplications().contains(j4));
        assertEquals(0, tracker.getJobApplications().size());
    }

    @Test
    public void testRemoveJobApplicationFromEmptyTracker() {
        assertFalse(tracker.remove(j1));
        assertFalse(tracker.getJobApplications().contains(j1));
        assertEquals(0, tracker.getJobApplications().size());
    }

    @Test
    public void testRemoveAllJobApplications() {
        addAllJobApplicationsToTracker();
        tracker.removeAllJobApplications();
        assertEquals(0, tracker.getJobApplications().size());
    }

    @Test
    public void testCountSubmitted() {
        addAllJobApplicationsToTracker();
        assertEquals(4, tracker.count(SUBMITTED));

        j4.setStatus(ACCEPTED);
        j2.setStatus(REJECTED);
        assertEquals(2, tracker.count(SUBMITTED));

        tracker.remove(j1);
        assertEquals(1, tracker.count(SUBMITTED));

        tracker.remove(j3);
        assertEquals(0, tracker.count(SUBMITTED));
    }

    @Test
    public void testCountInterviewed() {
        addAllJobApplicationsToTracker();
        setAllJobApplicationsToStatus(INTERVIEWED);
        assertEquals(4, tracker.count(INTERVIEWED));

        tracker.remove(j1);
        tracker.remove(j2);
        assertEquals(2, tracker.count(INTERVIEWED));

        tracker.remove(j4);
        assertEquals(1, tracker.count(INTERVIEWED));

        j3.setStatus(ACCEPTED);
        assertEquals(0, tracker.count(INTERVIEWED));
    }

    @Test
    public void testCountAccepted() {
        addAllJobApplicationsToTracker();
        setAllJobApplicationsToStatus(ACCEPTED);
        assertEquals(4, tracker.count(ACCEPTED));

        j1.setStatus(REJECTED);
        tracker.remove(j4);
        assertEquals(2, tracker.count(ACCEPTED));

        j2.setStatus(SUBMITTED);
        assertEquals(1, tracker.count(ACCEPTED));

        tracker.remove(j3);
        assertEquals(0, tracker.count(ACCEPTED));
    }

    @Test
    public void testCountRejected() {
        addAllJobApplicationsToTracker();
        setAllJobApplicationsToStatus(REJECTED);
        assertEquals(4, tracker.count(REJECTED));

        j2.setStatus(ACCEPTED);
        j4.setStatus(INTERVIEWED);
        assertEquals(2, tracker.count(REJECTED));

        j1.setStatus(ACCEPTED);
        tracker.remove(j4);
        assertEquals(1, tracker.count(REJECTED));

        tracker.remove(j3);
        assertEquals(0, tracker.count(REJECTED));
    }

    @Test
    public void testCalculatePercentageSubmitted() {
        addAllJobApplicationsToTracker();
        assertEquals(1.0, tracker.calculatePercentage(SUBMITTED), 0.01);

        j4.setStatus(ACCEPTED);
        j2.setStatus(REJECTED);
        tracker.remove(j1);
        assertEquals(0.33, tracker.calculatePercentage(SUBMITTED), 0.01);

        tracker.remove(j3);
        tracker.remove(j2);
        assertEquals(0, tracker.calculatePercentage(SUBMITTED), 0.01);
    }

    @Test
    public void testCalculatePercentageInterviewed() {
        addAllJobApplicationsToTracker();
        setAllJobApplicationsToStatus(INTERVIEWED);
        assertEquals(1.0, tracker.calculatePercentage(INTERVIEWED), 0.01);

        j2.setStatus(ACCEPTED);
        assertEquals(0.75, tracker.calculatePercentage(INTERVIEWED), 0.01);

        tracker.remove(j3);
        tracker.remove(j4);
        tracker.remove(j1);
        assertEquals(0.0, tracker.calculatePercentage(INTERVIEWED), 0.01);
    }

    @Test
    public void testCalculatePercentageAccepted() {
        addAllJobApplicationsToTracker();
        setAllJobApplicationsToStatus(ACCEPTED);
        assertEquals(1.0, tracker.calculatePercentage(ACCEPTED), 0.01);

        j2.setStatus(REJECTED);
        tracker.remove(j1);
        assertEquals(0.66, tracker.calculatePercentage(ACCEPTED), 0.01);

        tracker.remove(j3);
        tracker.remove(j4);
        assertEquals(0.0, tracker.calculatePercentage(ACCEPTED), 0.01);
    }

    @Test
    public void testCalculatePercentageRejected() {
        addAllJobApplicationsToTracker();
        setAllJobApplicationsToStatus(REJECTED);
        assertEquals(1.0, tracker.calculatePercentage(REJECTED), 0.01);

        j2.setStatus(ACCEPTED);
        j1.setStatus(ACCEPTED);
        assertEquals(0.5, tracker.calculatePercentage(REJECTED), 0.01);

        tracker.remove(j3);
        tracker.remove(j4);
        assertEquals(0.0, tracker.calculatePercentage(REJECTED), 0.01);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds all instantiated job applications to the tracker
     */
    private void addAllJobApplicationsToTracker() {
        tracker.add(j1);
        tracker.add(j2);
        tracker.add(j3);
        tracker.add(j4);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the status of all instantiated job applications to given status
     */
    private void setAllJobApplicationsToStatus(JobApplicationStatus status) {
        j1.setStatus(status);
        j2.setStatus(status);
        j3.setStatus(status);
        j4.setStatus(status);
    }
}
