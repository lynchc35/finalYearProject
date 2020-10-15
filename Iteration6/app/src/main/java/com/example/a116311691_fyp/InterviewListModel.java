package com.example.a116311691_fyp;

public class InterviewListModel {

    private String applicantName;
    private String roleTitle;
    private String time;
    private String location;
    private String additional;
    private int interviewID;
    private int applicantID;
    private String feedback;
    private int jobListingID;
    private int applicationID;
    private String outcome;

    public InterviewListModel(int interviewID, String applicantName, int applicantID, String roleTitle, String time, String location, String additional, String feedback, int jobListingID, int applicationID, String outcome) {
        this.applicantName = applicantName;
        this.applicantID = applicantID;
        this.interviewID = interviewID;
        this.roleTitle = roleTitle;
        this.time = time;
        this.location = location;
        this.additional = additional;
        this.feedback = feedback;
        this.jobListingID = jobListingID;
        this.applicationID = applicationID;
        this.outcome = outcome;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public int getJobListingID() {
        return jobListingID;
    }

    public void setJobListingID(int jobListingID) {
        this.jobListingID = jobListingID;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setApplicantID(int applicantID) {
        this.applicantID = applicantID;
    }

    public int getApplicantID() {
        return applicantID;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public int getInterviewID() {
        return interviewID;
    }

    public void setInterviewID(int interviewID) {
        this.interviewID = interviewID;
    }


    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

}
