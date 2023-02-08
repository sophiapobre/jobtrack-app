package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static model.JobApplicationStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class JobApplicationTest {
    private JobApplication j1;

    @BeforeEach
    public void setUp() {
        j1 = new JobApplication("2023-02-01", "Workday", "Software Developer Co-op");
    }

    @Test
    public void testConstructor() {
        assertEquals(LocalDate.parse("2023-02-01"), j1.getSubmissionDate());
        assertEquals("Workday", j1.getCompanyName());
        assertEquals("Software Developer Co-op", j1.getRoleName());
        assertEquals(SUBMITTED, j1.getStatus());
    }

    @Test
    public void testSetSubmissionDate() {
        j1.setSubmissionDate("2022-02-01");
        assertEquals(LocalDate.parse("2022-02-01"), j1.getSubmissionDate());
    }

    @Test
    public void testSetCompanyName() {
        j1.setCompanyName("Amazon");
        assertEquals("Amazon", j1.getCompanyName());
    }

    @Test
    public void testSetRoleName() {
        j1.setRoleName("Data Analyst");
        assertEquals("Data Analyst", j1.getRoleName());
    }

    @Test
    public void testSetStatus() {
        j1.setStatus(INTERVIEWED);
        assertEquals(INTERVIEWED, j1.getStatus());

        j1.setStatus(ACCEPTED);
        assertEquals(ACCEPTED, j1.getStatus());

        j1.setStatus(REJECTED);
        assertEquals(REJECTED, j1.getStatus());

        j1.setStatus(SUBMITTED);
        assertEquals(SUBMITTED, j1.getStatus());
    }

    @Test
    public void testEquals() {
        JobApplication j2 = new JobApplication("2023-02-07", "Microsoft", "Software Engineer Intern");
        assertFalse(j1.equals(j2));
        assertFalse(j2.equals("2023-02-07"));
        assertTrue(j1.equals(j1));
    }
}