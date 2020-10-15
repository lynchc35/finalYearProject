package com.example.a116311691_fyp;

//INSTRUCTIONS FOR THIS https://www.youtube.com/watch?v=dJFer9piVc0&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=5
// MySQL php connection tutorial
public class Constants {

    //START
    //https://www.youtube.com/watch?v=dJFer9piVc0&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=5
    //link to php script file that sets contains the PHP script files for CRUD
    //IP address came from terminal - ifconfig
    //192.168.0.6
    //10.241.230.233

    //10.241.166.80
    //192.168.0.11
    private static final String ROOT_URL = "http://10.241.166.80/Android/v1/";

    //link to php script file that sets up the CREATE of CRUD for the MySQL DB on phpMyAdmin for Organisation registration
    public static final String URL_ORG_REGISTER = ROOT_URL+"registerOrg.php";
    public static final String URL_UPDATE_ORG = ROOT_URL+"updateOrgProfile.php";
    public static final String URL_DELETE_ORG = ROOT_URL+"deleteOrg.php";
    public static final String URL_RETRIEVE_ORG = ROOT_URL+"retrieveOrg.php";

    //link to php script file that sets up the CREATE of CRUD for the MySQL DB on phpMyAdmin for Applicant registration
    public static final String URL_APPLICANT_REGISTER = ROOT_URL+"registerApplicant.php";
    public static final String URL_UPDATE_APPLICANT = ROOT_URL+"updateApplicantProfile.php";
    public static final String URL_DELETE_APPLICANT = ROOT_URL+"deleteApplicant.php";
    public static final String URL_RETRIEVE_APPLICANT = ROOT_URL+"retrieveApplicant.php";
    //END

    //C of CRUD on job listing php file
    public static final String URL_JOB_UPLOAD = ROOT_URL+"createJob.php";
    //R of CRUD
    public static final String URL_JOB_LIST = ROOT_URL+"displayJobListings.php";
    public static final String URL_JOB_PER_ORG_LIST = ROOT_URL+"displayJobListingsPerOrg.php";
    public static final String URL_UPDATE_JOB = ROOT_URL+"updateJobListing.php";
    public static final String URL_DISPLAY_JOB = ROOT_URL+"displaySingleJobListing.php";


    public static final String URL_FILTER_LOCATION = ROOT_URL+"filterLocation.php";
    public static final String URL_FILTER_INDUSTRY = ROOT_URL+"filterIndustry.php";
    public static final String URL_FILTER_BOTH = ROOT_URL+"filterBoth.php";

    public static final String URL_UPLOAD_CV = ROOT_URL+"createCV.php";

    public static final String URL_RETRIEVE_CV = ROOT_URL+"retrieveCV.php";
    public static final String URL_UPDATE_CV = ROOT_URL+"updateCV.php";

    public static final String URL_APP_PER_JOB = ROOT_URL+"displayApplicationsPerJob.php";
    public static final String URL_APP_PER_ORG = ROOT_URL+"displayApplicationsPerOrg.php";
    public static final String URL_CREATE_APPLICATION = ROOT_URL+"createApplication.php";
    public static final String URL_APP_PER_USER = ROOT_URL+"displayApplicantApplications.php";
    public static final String URL_RETRIEVE_APP = ROOT_URL+"retrieveApp.php";
    public static final String URL_EDIT_CL = ROOT_URL+"editCoverLetter.php";

    public static final String URL_CREATE_INTERVIEW = ROOT_URL+"createInterview.php";
    public static final String URL_ORG_INTERVIEW_LIST = ROOT_URL+"orgInterviews.php";
    public static final String URL_JOB_INTERVIEW_LIST = ROOT_URL+"jobInterviews.php";
    public static final String URL_APPLICANT_INTERVIEWS = ROOT_URL+"applicantInterviews.php";
    public static final String URL_UPDATE_INTERVIEW = ROOT_URL+"updateInterview.php";
    public static final String URL_RETRIEVE_INTERVIEW = ROOT_URL+"retrieveInterview.php";
    public static final String URL_ADD_FEEDBACK = ROOT_URL+"addFeedback.php";
    public static final String URL_DELETE_INTERVIEW = ROOT_URL+"deleteInterview.php";
    public static final String URL_SINGLE_INTERVIEW = ROOT_URL+"singleInterview.php";

    public static final String URL_INTERVIEW_EMAIL = ROOT_URL+"interviewEmail.php";
    public static final String URL_APPLICATION_EMAIL = ROOT_URL+"applicationEmail.php";

    public static final String URL_SUBMIT_OUTCOME = ROOT_URL+"submitOutcome.php";

    public static final String URL_UNDER_30 = ROOT_URL+"under30.php";
    public static final String URL_UNDER_40 = ROOT_URL+"under40.php";
    public static final String URL_UNDER_50 = ROOT_URL+"under50.php";
    public static final String URL_OVER_50 = ROOT_URL+"over50.php";


    public static final String URL_CHILDREN = ROOT_URL+"children.php";
    public static final String URL_CARER = ROOT_URL+"carer.php";
    public static final String URL_OTHER = ROOT_URL+"other.php";
    public static final String URL_ILLNESS = ROOT_URL+"Illness.php";


    //START
    //https://www.youtube.com/watch?v=qKtWV5htt_M&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=12
    //implementing the user login function
    public static final String URL_LOGIN = ROOT_URL+"userLogin.php";
    //END
}
