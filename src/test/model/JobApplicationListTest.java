package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.JobApplicationStatus.*;
import static org.junit.jupiter.api.Assertions.*;

public class JobApplicationListTest {
    private JobApplicationList list;
    private JobApplication j1;
    private JobApplication j2;
    private JobApplication j3;
    private JobApplication j4;

    @BeforeEach
    public void setUp() {
        list = new JobApplicationList();
        j1 = new JobApplication("2023-02-05", "Microsoft", "Product Manager");
        j2 = new JobApplication("2023-06-20", "Google", "Software Engineer");
        j3 = new JobApplication("2022-12-25", "Apple", "iOS Engineer");
        j4 = new JobApplication("2022-11-28", "Meta", "Tech Lead");
    }

    @Test
    public void testConstructor() {
        assertNotNull(list.getJobApplications());
        assertEquals(0, list.getJobApplications().size());
    }

    @Test
    public void testAddOneJobApplication() {
        assertTrue(list.add(j1));
        assertTrue(list.getJobApplications().contains(j1));
        assertEquals(1, list.getJobApplications().size());
    }

    @Test
    public void testAddMultipleJobApplications() {
        assertTrue(list.add(j1));
        assertTrue(list.add(j2));
        assertTrue(list.add(j3));
        assertTrue(list.getJobApplications().contains(j1));
        assertTrue(list.getJobApplications().contains(j2));
        assertTrue(list.getJobApplications().contains(j3));
        assertEquals(3, list.getJobApplications().size());
    }

    @Test
    public void testAddSameJobApplicationTwice() {
        assertTrue(list.add(j1));
        assertFalse(list.add(j1));
        assertTrue(list.getJobApplications().contains(j1));
        assertEquals(1, list.getJobApplications().size());
    }

    @Test
    public void testRemoveJobApplicationInList() {
        list.add(j1);
        list.add(j2);
        list.add(j3);
        assertTrue(list.remove(j2));
        assertFalse(list.getJobApplications().contains(j2));
        assertEquals(2, list.getJobApplications().size());
    }

    @Test
    public void testRemoveJobApplicationNotInList() {
        list.add(j1);
        list.add(j2);
        assertFalse(list.remove(j3));
        assertFalse(list.getJobApplications().contains(j3));
        assertEquals(2, list.getJobApplications().size());
    }

    @Test
    public void testRemoveJobApplicationsUntilEmpty() {
        list.add(j1);
        list.add(j2);
        list.add(j3);
        assertTrue(list.remove(j2));
        assertTrue(list.remove(j1));
        assertTrue(list.remove(j3));
        assertFalse(list.getJobApplications().contains(j1));
        assertFalse(list.getJobApplications().contains(j2));
        assertFalse(list.getJobApplications().contains(j3));
        assertEquals(0, list.getJobApplications().size());
    }

    @Test
    public void testRemoveJobApplicationFromEmptyList() {
        assertFalse(list.remove(j1));
        assertFalse(list.getJobApplications().contains(j1));
        assertEquals(0, list.getJobApplications().size());
    }

    @Test
    public void testCountSubmitted() {
        list.add(j1);
        list.add(j2);
        list.add(j3);
        list.add(j4);
        assertEquals(4, list.count(SUBMITTED));

        j4.setStatus(ACCEPTED);
        j2.setStatus(REJECTED);
        list.remove(j1);
        assertEquals(1, list.count(SUBMITTED));

        list.remove(j3);
        assertEquals(0, list.count(SUBMITTED));
    }

    @Test
    public void testCountInterviewed() {
        list.add(j1);
        list.add(j2);
        list.add(j3);
        list.add(j4);
        j1.setStatus(INTERVIEWED);
        j3.setStatus(INTERVIEWED);
        assertEquals(2, list.count(INTERVIEWED));

        list.remove(j1);
        assertEquals(1, list.count(INTERVIEWED));

        j3.setStatus(ACCEPTED);
        assertEquals(0, list.count(INTERVIEWED));
    }

    @Test
    public void testCountAccepted() {
        list.add(j1);
        list.add(j2);
        list.add(j3);
        list.add(j4);
        j1.setStatus(ACCEPTED);
        j4.setStatus(ACCEPTED);
        j3.setStatus(ACCEPTED);
        assertEquals(3, list.count(ACCEPTED));

        j1.setStatus(REJECTED);
        list.remove(j4);
        assertEquals(1, list.count(ACCEPTED));

        list.remove(j3);
        assertEquals(0, list.count(ACCEPTED));
    }

    @Test
    public void testCountRejected() {
        list.add(j1);
        list.add(j2);
        list.add(j3);
        list.add(j4);
        j1.setStatus(REJECTED);
        j4.setStatus(REJECTED);
        j3.setStatus(REJECTED);
        assertEquals(3, list.count(REJECTED));

        j1.setStatus(ACCEPTED);
        list.remove(j4);
        assertEquals(1, list.count(REJECTED));

        list.remove(j3);
        assertEquals(0, list.count(REJECTED));
    }

    @Test
    public void testCalculatePercentageSubmitted() {
        list.add(j1);
        list.add(j2);
        list.add(j3);
        list.add(j4);
        assertEquals(1.0, list.calculatePercentage(SUBMITTED), 0.01);

        j4.setStatus(ACCEPTED);
        j2.setStatus(REJECTED);
        list.remove(j1);
        assertEquals(0.33, list.calculatePercentage(SUBMITTED), 0.01);

        list.remove(j3);
        list.remove(j2);
        assertEquals(0, list.calculatePercentage(SUBMITTED), 0.01);
    }

    @Test
    public void testCalculatePercentageInterviewed() {
        list.add(j1);
        list.add(j2);
        list.add(j3);
        list.add(j4);
        j1.setStatus(INTERVIEWED);
        j2.setStatus(INTERVIEWED);
        j3.setStatus(INTERVIEWED);
        j4.setStatus(INTERVIEWED);
        assertEquals(1.0, list.calculatePercentage(INTERVIEWED), 0.01);

        j2.setStatus(ACCEPTED);
        assertEquals(0.75, list.calculatePercentage(INTERVIEWED), 0.01);

        list.remove(j3);
        list.remove(j4);
        list.remove(j1);
        assertEquals(0.0, list.calculatePercentage(INTERVIEWED), 0.01);
    }

    @Test
    public void testCalculatePercentageAccepted() {
        list.add(j1);
        list.add(j2);
        list.add(j3);
        list.add(j4);
        j1.setStatus(ACCEPTED);
        j2.setStatus(ACCEPTED);
        j3.setStatus(ACCEPTED);
        j4.setStatus(ACCEPTED);
        assertEquals(1.0, list.calculatePercentage(ACCEPTED), 0.01);

        j2.setStatus(REJECTED);
        list.remove(j1);
        assertEquals(0.66, list.calculatePercentage(ACCEPTED), 0.01);

        list.remove(j3);
        list.remove(j4);
        assertEquals(0.0, list.calculatePercentage(ACCEPTED), 0.01);
    }

    @Test
    public void testCalculatePercentageRejected() {
        list.add(j1);
        list.add(j2);
        list.add(j3);
        list.add(j4);
        j1.setStatus(REJECTED);
        j2.setStatus(REJECTED);
        j3.setStatus(REJECTED);
        j4.setStatus(REJECTED);
        assertEquals(1.0, list.calculatePercentage(REJECTED), 0.01);

        j2.setStatus(ACCEPTED);
        j1.setStatus(ACCEPTED);
        assertEquals(0.5, list.calculatePercentage(REJECTED), 0.01);

        list.remove(j1);
        list.remove(j3);
        list.remove(j4);
        assertEquals(0.0, list.calculatePercentage(REJECTED), 0.01);
    }
}
