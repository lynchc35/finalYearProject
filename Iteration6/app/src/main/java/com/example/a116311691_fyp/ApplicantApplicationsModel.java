package com.example.a116311691_fyp;

import java.io.Serializable;

//serializable taken from JobListingsModel.java
    //'implements Serializable' https://stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
    public class ApplicantApplicationsModel implements Serializable {
        private int applicationID;
        private int jobListingID;
        private int applicantID;
        private String orgName;
    private String roleTitle;
    private String status;

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    private String additional;

        public ApplicantApplicationsModel(int applicationID, int jobListingID, int applicantID, String orgName, String roleTitle, String status, String additional) {
            this.applicantID = applicantID;
            this.applicationID = applicationID;
            this.jobListingID = jobListingID;
            this.orgName = orgName;
            this.roleTitle = roleTitle;
            this.status = status;
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

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getRoleTitle() {
            return roleTitle;
        }

        public void setRoleTitle(String roleTitle) {
            this.roleTitle = roleTitle;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

