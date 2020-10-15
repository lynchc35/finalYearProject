package com.example.a116311691_fyp;

import java.io.Serializable;


//'implements Serializable' https://stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
public class OrgJobListingsModel implements Serializable {
        private String roleTitle;
    private String location;
    private String roleRequirements;
    private String roleDuties;
    private String salary;
    private String listingStatus;
    private int jobListingID;
    private int organisationID;

    public OrgJobListingsModel(int jobListingID, String roleTitle, int organisationID, String location, String roleRequirements, String roleDuties, String salary, String listingStatus) {
        this.roleTitle = roleTitle;
        this.location = location;
        this.roleRequirements = roleRequirements;
        this.roleDuties = roleDuties;
        this.salary = salary;
        this.listingStatus = listingStatus;
        this.jobListingID = jobListingID;
        this.organisationID = organisationID;
    }

    public String getRoleTitle() {
        return roleTitle;
    }

    public void setRoleTitle(String roleTitle) {
        this.roleTitle = roleTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoleRequirements() {
        return roleRequirements;
    }

    public void setRoleRequirements(String roleRequirements) {
        this.roleRequirements = roleRequirements;
    }

    public String getRoleDuties() {
        return roleDuties;
    }

    public void setRoleDuties(String roleDuties) {
        this.roleDuties = roleDuties;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus(String listingStatus) {
        this.listingStatus = listingStatus;
    }

    public int getJobListingID() {
        return jobListingID;
    }

    public void setJobListingID(int jobListingID) {
        this.jobListingID = jobListingID;
    }

    public int getOrganisationID() {
        return organisationID;
    }

    public void setOrganisationID(int organisationID) {
        this.organisationID = organisationID;
    }

}
