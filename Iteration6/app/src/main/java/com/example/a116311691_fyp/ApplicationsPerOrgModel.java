package com.example.a116311691_fyp;

import java.io.Serializable;

//serializable taken from JobListingsModel.java
    //'implements Serializable' https://stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
    public class ApplicationsPerOrgModel implements Serializable {
        private int applicationID;
        private int jobListingID;
        private int applicantID;
        private String applicantName, roleTitle;

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    private String additional;

        public ApplicationsPerOrgModel(int applicationID, int jobListingID, int applicantID, String applicantName, String roleTitle, String additional) {
            this.applicantID = applicantID;
            this.applicationID = applicationID;
            this.jobListingID = jobListingID;
            this.applicantName = applicantName;
            this.roleTitle = roleTitle;
            this.additional = additional;
        }

        public int getApplicationID() {
            return applicationID;
        }

        public void setApplicationID(int applicationID) {
            this.applicationID = applicationID;
        }

        public int getJobListingID() {
            return jobListingID;
        }

        public void setJobListingID(int jobListingID) {
            this.jobListingID = jobListingID;
        }

        public int getApplicantID() {
            return applicantID;
        }

        public void setApplicantID(int applicantID) {
            this.applicantID = applicantID;
        }

        public String getApplicantName() {
            return applicantName;
        }

        public void setApplicantName(String applicantName) {
            this.applicantName = applicantName;
        }

        public String getRoleTitle() {
            return roleTitle;
        }

        public void setRoleTitle(String roleTitle) {
            this.roleTitle = roleTitle;
        }
    }

