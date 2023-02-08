package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.JobApplicationStatus.*;
import static org.junit.jupiter.api.Assertions.*;

public class JobApplicationListTest {
    private JobApplicationList l;
    private JobApplication j1;
    private JobApplication j2;
    private JobApplication j3;
    private JobApplication j4;

    @BeforeEach
    public void setUp() {
        l = new JobApplicationList();
        j1 = new JobApplication("2023-02-05", "Microsoft", "Product Manager");
        j2 = new JobApplication("2023-06-20", "Google", "Software Engineer");
        j3 = new JobApplication("2022-12-25", "Apple", "iOS Engineer");
        j4 = new JobApplication("2022-11-28", "Meta", "Tech Lead");
    }

    @Test
    public void testConstructor() {
        assertNotNull(l.getJobApplications());
        assertEquals(0, l.getJobApplications().size());
    }

    @Test
    public void testAddOneJobApplication() {
        assertTrue(l.add(j1));
        assertTrue(l.getJobApplications().contains(j1));
        assertEquals(1, l.getJobApplications().size());
    }

    @Test
    public void testAddMultipleJobApplications() {
        assertTrue(l.add(j1));
        assertTrue(l.add(j2));
        assertTrue(l.add(j3));
        assertTrue(l.getJobApplications().contains(j1));
        assertTrue(l.getJobApplications().contains(j2));
        assertTrue(l.getJobApplications().contains(j3));
        assertEquals(3, l.getJobApplications().size());
    }

    @Test
    public void testAddSameJobApplicationTwice() {
        assertTrue(l.add(j1));
        assertFalse(l.add(j1));
        assertTrue(l.getJobApplications().contains(j1));
        assertEquals(1, l.getJobApplications().size());
    }

    @Test
    public void testRemoveJobApplicationInList() {
        l.add(j1);
        l.add(j2);
        l.add(j3);
        assertTrue(l.remove(j2));
        assertFalse(l.getJobApplications().contains(j2));
        assertEquals(2, l.getJobApplications().size());
    }

    @Test
    public void testRemoveJobApplicationNotInList() {
        l.add(j1);
        l.add(j2);
        assertFalse(l.remove(j3));
        assertFalse(l.getJobApplications().contains(j3));
        assertEquals(2, l.getJobApplications().size());
    }

    @Test
    public void testRemoveJobApplicationsUntilEmpty() {
        l.add(j1);
        l.add(j2);
        l.add(j3);
        assertTrue(l.remove(j2));
        assertTrue(l.remove(j1));
        assertTrue(l.remove(j3));
        assertFalse(l.getJobApplications().contains(j1));
        assertFalse(l.getJobApplications().contains(j2));
        assertFalse(l.getJobApplications().contains(j3));
        assertEquals(0, l.getJobApplications().size());
    }

    @Test
    public void testRemoveJobApplicationFromEmptyList() {
        assertFalse(l.remove(j1));
        assertFalse(l.getJobApplications().contains(j1));
        assertEquals(0, l.getJobApplications().size());
    }

    @Test
    public void testCountSubmitted() {
        l.add(j1);
        l.add(j2);
        l.add(j3);
        l.add(j4);
        assertEquals(4, l.count(SUBMITTED));

        j4.setStatus(ACCEPTED);
        j2.setStatus(REJECTED);
        l.remove(j1);
        assertEquals(1, l.count(SUBMITTED));

        l.remove(j3);
        assertEquals(0, l.count(SUBMITTED));
    }

    @Test
    public void testCountInterviewed() {
        l.add(j1);
        l.add(j2);
        l.add(j3);
        l.add(j4);
        j1.setStatus(INTERVIEWED);
        j3.setStatus(INTERVIEWED);
        assertEquals(2, l.count(INTERVIEWED));

        l.remove(j1);
        assertEquals(1, l.count(INTERVIEWED));

        j3.setStatus(ACCEPTED);
        assertEquals(0, l.count(INTERVIEWED));
    }

    @Test
    public void testCountAccepted() {
        l.add(j1);
        l.add(j2);
        l.add(j3);
        l.add(j4);
        j1.setStatus(ACCEPTED);
        j4.setStatus(ACCEPTED);
        j3.setStatus(ACCEPTED);
        assertEquals(3, l.count(ACCEPTED));

        j1.setStatus(REJECTED);
        l.remove(j4);
        assertEquals(1, l.count(ACCEPTED));

        l.remove(j3);
        assertEquals(0, l.count(ACCEPTED));
    }

    @Test
    public void testCountRejected() {
        l.add(j1);
        l.add(j2);
        l.add(j3);
        l.add(j4);
        j1.setStatus(REJECTED);
        j4.setStatus(REJECTED);
        j3.setStatus(REJECTED);
        assertEquals(3, l.count(REJECTED));

        j1.setStatus(ACCEPTED);
        l.remove(j4);
        assertEquals(1, l.count(REJECTED));

        l.remove(j3);
        assertEquals(0, l.count(REJECTED));
    }

    @Test
    public void testCalculatePercentageSubmitted() {
        l.add(j1);
        l.add(j2);
        l.add(j3);
        l.add(j4);
        assertEquals(1.0, l.calculatePercentage(SUBMITTED), 0.01);

        j4.setStatus(ACCEPTED);
        j2.setStatus(REJECTED);
        l.remove(j1);
        assertEquals(0.33, l.calculatePercentage(SUBMITTED), 0.01);

        l.remove(j3);
        l.remove(j2);
        assertEquals(0, l.calculatePercentage(SUBMITTED), 0.01);
    }

    @Test
    public void testCalculatePercentageInterviewed() {
        l.add(j1);
        l.add(j2);
        l.add(j3);
        l.add(j4);
        j1.setStatus(INTERVIEWED);
        j2.setStatus(INTERVIEWED);
        j3.setStatus(INTERVIEWED);
        j4.setStatus(INTERVIEWED);
        assertEquals(1.0, l.calculatePercentage(INTERVIEWED), 0.01);

        j2.setStatus(ACCEPTED);
        assertEquals(0.75, l.calculatePercentage(INTERVIEWED), 0.01);

        l.remove(j3);
        l.remove(j4);
        l.remove(j1);
        assertEquals(0.0, l.calculatePercentage(INTERVIEWED), 0.01);
    }

    @Test
    public void testCalculatePercentageAccepted() {
        l.add(j1);
        l.add(j2);
        l.add(j3);
        l.add(j4);
        j1.setStatus(ACCEPTED);
        j2.setStatus(ACCEPTED);
        j3.setStatus(ACCEPTED);
        j4.setStatus(ACCEPTED);
        assertEquals(1.0, l.calculatePercentage(ACCEPTED), 0.01);

        j2.setStatus(REJECTED);
        l.remove(j1);
        assertEquals(0.66, l.calculatePercentage(ACCEPTED), 0.01);

        l.remove(j3);
        l.remove(j4);
        assertEquals(0.0, l.calculatePercentage(ACCEPTED), 0.01);
    }

    @Test
    public void testCalculatePercentageRejected() {
        l.add(j1);
        l.add(j2);
        l.add(j3);
        l.add(j4);
        j1.setStatus(REJECTED);
        j2.setStatus(REJECTED);
        j3.setStatus(REJECTED);
        j4.setStatus(REJECTED);
        assertEquals(1.0, l.calculatePercentage(REJECTED), 0.01);

        j2.setStatus(ACCEPTED);
        j1.setStatus(ACCEPTED);
        assertEquals(0.5, l.calculatePercentage(REJECTED), 0.01);

        l.remove(j1);
        l.remove(j3);
        l.remove(j4);
        assertEquals(0.0, l.calculatePercentage(REJECTED), 0.01);
    }
}
