# JobTrack

## About the Project
**What is *JobTrack*, and who is it for?**  
*JobTrack* is a Java desktop application that serves as a job application management system. It is designed for jobseekers who want to keep track of the job applications that they've submitted and gain insight into how many of their applications have progressed to further stages in the recruitment process. *JobTrack* features a tracker view, wherein users can add, delete, and see a list of their submitted job applications with various details, including date applied, company name, role, and status (e.g., submitted, interviewed, accepted, and rejected). Users can update the status of each job application and view the number and percentage of job applications under each status category. *JobTrack* also allows users to save the changes that they've made to their tracker and reaccess their data upon application relaunch.

**What inspired me to create *JobTrack?***  
I wanted to create *JobTrack* to monitor the countless job applications I've submitted in pursuit of my first internship as a Computer Science student. Previously, I had always applied for jobs without keeping track of my job applications, but I realized that being aware of my own job application statistics could help me pinpoint which job hunt strategies are most effective in increasing my chances of progressing to further stages in the recruitment process. As a result, I decided to build an application with a user-friendly interface that could not only help me in my own job search journey but also help others in theirs.

## User Stories
- As a user, I want to be able to add a job application to my tracker
- As a user, I want to be able to view a list of job applications in my tracker
- As a user, I want to be able to delete a job application from my tracker
- As a user, I want to be able to all remove job applications from my tracker
- As a user, I want to be able to update a job application's status (e.g., submitted, interviewed, accepted, and rejected)
- As a user, I want to be able to view the number and percentage of job applications under each status category
- As a user, I want to be given the option to save (or not save) my tracker to file when I decide to quit the application
- As a user, I want to be given the option to load (or not load) my tracker from file upon launching the application

# Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by clicking "Tracker" and then "Add Job Application" on the menu bar. You must then enter a valid submission date (i.e., a non-empty string in YYYY-MM-DD format) and press OK, enter a valid company name (i.e., non-empty string) and press OK, and enter a valid role name (i.e., non-empty string) and press OK. The job application with your inputs will then be displayed as a new row in the tracker. Note that a job application with your given submission date, company name, and role name must not already exist in the tracker prior to this action.
- You can generate the second required action related to adding Xs to a Y by clicking "Tracker" and then "Delete All Job Applications" on the menu bar. You must then press the "Yes" button when a prompt asks if you would like to delete all your data, and press the "OK" button when a confirmation prompt appears. The tracker will then appear empty.
- You can locate my visual component (i.e., the JobTrack logo) by simply launching the application. The image will be displayed for 3 seconds and then automatically disappear when you are asked whether you want to load your data.
- You can save the state of my application by clicking "JobTrack" and then "Quit JobTrack" on the menu bar, and then pressing the "Yes" button when a prompt asks if you would like to save your data.
- You can reload the state of my application by launching the application and pressing the "Yes" button when a prompt asks if you would like to load your data.

# Phase 4: Task 2

Sun Apr 02 20:27:49 PDT 2023
Added job application to the tracker: 2023-04-02 | Coinbase | Web Developer | SUBMITTED
Sun Apr 02 20:27:54 PDT 2023
Removed all job applications from the tracker.
Sun Apr 02 20:28:04 PDT 2023
Added job application to the tracker: 2023-04-01 | Twitter | CEO | SUBMITTED