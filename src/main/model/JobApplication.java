package model;

import java.time.LocalDate;

import static model.JobApplicationStatus.SUBMITTED;

// Represents a job application that has a submission date, company name, role name, and status
public class JobApplication {
    private LocalDate submissionDate; // the date when this job application was submitted
    private String companyName; // the name of the company that received this application
    private String roleName; // the name of the role applied to
    private JobApplicationStatus status; // the current status of this job application

    /*
     * REQUIRES: date in format YYYY-MM-DD, company and role have a non-zero length
     * EFFECTS: creates a new job application wherein submission date is set to given date, company name is set to
     *          given company, and role name is set to given role, and status is set to SUBMITTED
     */
    public JobApplication(String date, String company, String role) {
        submissionDate = LocalDate.parse(date);
        companyName = company;
        roleName = role;
        status = SUBMITTED;
    }

    /*
     * EFFECTS: returns the submission date
     */
    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    /*
     * EFFECTS: returns the name of the company that received this application
     */
    public String getCompanyName() {
        return companyName;
    }

    /*
     * EFFECTS: returns the name of the role applied to
     */
    public String getRoleName() {
        return roleName;
    }

    /*
     * EFFECTS: returns the current status
     */
    public JobApplicationStatus getStatus() {
        return status;
    }

    /*
     * REQUIRES: date in format YYYY-MM-DD
     * MODIFIES: this
     * EFFECTS: sets the submission date to given date
     */
    public void setSubmissionDate(String date) {
        submissionDate = LocalDate.parse(date);
    }

    /*
     * REQUIRES: company has a non-zero length
     * MODIFIES: this
     * EFFECTS: sets the company name to given company
     */
    public void setCompanyName(String company) {
        companyName = company;
    }

    /*
     * REQUIRES: role has a non-zero length
     * MODIFIES: this
     * EFFECTS: sets the role name to given role
     */
    public void setRoleName(String role) {
        roleName = role;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the status to given status
     */
    public void setStatus(JobApplicationStatus status) {
        this.status = status;
    }

    /*
     * EFFECTS: returns true if given object is an instance of JobApplication and its submission date, company name,
     *          role name, and status are equal to the corresponding fields of this, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof JobApplication) {
            JobApplication j = (JobApplication) o;
            return j.getSubmissionDate().equals(submissionDate) && j.getCompanyName().equals(companyName)
                    && j.getRoleName().equals(roleName) && j.getStatus().equals(status);
        } else {
            return false;
        }
    }
}
