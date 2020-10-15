package com.example.a116311691_fyp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

//https://www.youtube.com/watch?v=8t3ppHJGgwc&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=11
//ENTIRE CLASS CODE IS FROM ABOVE VIDEO

//singleton class
//used to store data of the user currently logged in

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context ctx;

    //https://www.youtube.com/watch?v=8t3ppHJGgwc&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=11
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ORG_ID = "organisationID";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_ORG_NAME = "orgName";
    private static final String KEY_ORG_LOCATION = "location";
    private static final String KEY_ORG_INDUSTRY = "industry";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONENUMBER = "phoneNumber";
    private static final String KEY_APPLICANT_ID = "applicantID";
    private static final String KEY_APP_FNAME = "fName";
    private static final String KEY_APP_LNAME = "lName";
    private static final String KEY_APP_AGE = "age";
    private static final String KEY_APP_ADDRESS = "address";
    private static final String KEY_APP_BREAKREASON = "breakReason";
    private static final String KEY_APP_LOCATIONPREF = "locationPref";
    private static final String KEY_APP_INDUSTRYPREF = "industryPref";
    private static final String KEY_APP_ROLEPREF = "rolePref";
    private static final String KEY_ORG_URL = "url";
    //https://www.simplifiedcoding.net/android-mysql-tutorial-to-perform-basic-crud-operation/
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String KEY_EMPTY = "";


    //https://www.youtube.com/watch?v=8t3ppHJGgwc&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=11
   // https://www.simplifiedcoding.net/android-mysql-tutorial-to-perform-basic-crud-operation/
    public SharedPrefManager(Context context) {
        this.ctx = context;
        sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //https://www.youtube.com/watch?v=8t3ppHJGgwc&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=11
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;

    }

    //https://www.youtube.com/watch?v=8t3ppHJGgwc&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=11
    public boolean orgLogin(int organisationID, String username, String password, String orgName, String location, String industry,
                            String email, String phoneNumber, String url){

        editor.putInt(KEY_ORG_ID, organisationID);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_ORG_NAME, orgName);
        editor.putString(KEY_ORG_LOCATION, location);
        editor.putString(KEY_ORG_INDUSTRY, industry);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONENUMBER, phoneNumber);
        editor.putString(KEY_ORG_URL, url);

        editor.apply();

        return true;
    }

    //https://www.youtube.com/watch?v=8t3ppHJGgwc&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=11
    public boolean applicantLogin(int applicantID, String username, String password, String fName, String lName, String age, String address,
                                  String email, String phoneNumber, String breakReason, String locationPref, String industryPref, String rolePref){


        editor.putInt(KEY_APPLICANT_ID, applicantID);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_APP_FNAME, fName);
        editor.putString(KEY_APP_LNAME, lName);
        editor.putString(KEY_APP_AGE, age);
        editor.putString(KEY_APP_ADDRESS, address);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONENUMBER, phoneNumber);
        editor.putString(KEY_APP_BREAKREASON, breakReason);
        editor.putString(KEY_APP_LOCATIONPREF, locationPref);
        editor.putString(KEY_APP_INDUSTRYPREF, industryPref);
        editor.putString(KEY_APP_ROLEPREF, rolePref);

        editor.apply();

        return true;
    }

    //   //https://www.simplifiedcoding.net/android-mysql-tutorial-to-perform-basic-crud-operation/
    public Applicant getApplicantDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        Applicant applicant = new Applicant();
        applicant.setApplicantID(sharedPreferences.getInt(KEY_APPLICANT_ID, 0));
        applicant.setUsername(sharedPreferences.getString(KEY_USERNAME, KEY_EMPTY));
        applicant.setPassword(sharedPreferences.getString(KEY_PASSWORD, KEY_EMPTY));
        applicant.setfName(sharedPreferences.getString(KEY_APP_FNAME, KEY_EMPTY));
        applicant.setlName(sharedPreferences.getString(KEY_APP_LNAME, KEY_EMPTY));
        applicant.setAge(sharedPreferences.getString(KEY_APP_AGE, KEY_EMPTY));
        applicant.setAddress(sharedPreferences.getString(KEY_APP_ADDRESS, KEY_EMPTY));
        applicant.setEmail(sharedPreferences.getString(KEY_EMAIL, KEY_EMPTY));
        applicant.setPhoneNumber(sharedPreferences.getString(KEY_PHONENUMBER, KEY_EMPTY));
        applicant.setBreakReason(sharedPreferences.getString(KEY_APP_BREAKREASON, KEY_EMPTY));
        applicant.setLocationPref(sharedPreferences.getString(KEY_APP_LOCATIONPREF, KEY_EMPTY));
        applicant.setIndustryPref(sharedPreferences.getString(KEY_APP_INDUSTRYPREF, KEY_EMPTY));
        applicant.setRolePref(sharedPreferences.getString(KEY_APP_ROLEPREF, KEY_EMPTY));

        return applicant;
    }
    //   //https://www.simplifiedcoding.net/android-mysql-tutorial-to-perform-basic-crud-operation/
    public Organisation getOrgDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        Organisation organisation = new Organisation();
        organisation.setOrganisationID(sharedPreferences.getInt(KEY_ORG_ID, 0));
        organisation.setUsername(sharedPreferences.getString(KEY_USERNAME, KEY_EMPTY));
        organisation.setPassword(sharedPreferences.getString(KEY_PASSWORD, KEY_EMPTY));
        organisation.setOrgName(sharedPreferences.getString(KEY_ORG_NAME, KEY_EMPTY));
        organisation.setLocation(sharedPreferences.getString(KEY_ORG_LOCATION, KEY_EMPTY));
        organisation.setIndustry(sharedPreferences.getString(KEY_ORG_INDUSTRY, KEY_EMPTY));
        organisation.setEmail(sharedPreferences.getString(KEY_EMAIL, KEY_EMPTY));
        organisation.setPhoneNumber(sharedPreferences.getString(KEY_PHONENUMBER, KEY_EMPTY));
        organisation.setUrl(sharedPreferences.getString(KEY_ORG_URL, KEY_EMPTY));

        return organisation;
    }


    //https://www.youtube.com/watch?v=8t3ppHJGgwc&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=11
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //if not equal to null then it means some username is stored in shared preferences i.e. user is logged in
        //if it returns null, that means the user is not logged in
        if (sharedPreferences.getString(KEY_USERNAME, null) != null){
            return true; //user logged in
        }
        return false; //user not logged in
    }

    //https://www.youtube.com/watch?v=8t3ppHJGgwc&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=11
    public boolean logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); //remove all values from the editor - if no values saved, means user is logged out
        editor.apply(); //save
        return true;
    }

}
