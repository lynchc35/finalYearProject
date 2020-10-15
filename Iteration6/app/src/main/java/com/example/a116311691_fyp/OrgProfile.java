package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OrgProfile extends AppCompatActivity {


    TextView tvTitle, tvLogout;
    EditText etUsername, etPassword, etOrgName, etEmail, etPhoneNumber, etURL;
    Spinner spLocation, spIndustry;
    Button btnUploadJob, btnViewJobs, btnApplications, btnInterviews, btnQueries;
    ImageButton btnEdit, btnDelete, btnSubmit;
    final Context context = this;
    String usernameSET, emailSET, phoneSET;
    BottomNavigationView bottomNavigationView;

    String title, username, password, orgName, url, phoneNumber, email, location, industry;
    String organisationID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_profile);

        //https://stackoverflow.com/questions/4149415/onscreen-keyboard-opens-automatically-when-activity-starts
        //START
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //END

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        //Log.d("NAVIGATION", "PROFILE");
                        return true;
                    case R.id.jobs:
                        jobs();
                        return true;
                    case R.id.applications:
                        applications();
                        return true;
                    case R.id.interviews:
                        interviews();
                        return true;
                    case R.id.reports:
                        queries();
                        return true;
                }
                return false;
            }});

        //END

        tvTitle = findViewById(R.id.tvOrgNameTitle);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etOrgName = findViewById(R.id.etOrgName);
        etEmail = findViewById(R.id.etEmail);
        etURL = findViewById(R.id.etURL);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        spLocation = findViewById(R.id.spLocation);
        spIndustry = findViewById(R.id.spIndustry);
        btnUploadJob = findViewById(R.id.btnUploadJob);
        btnEdit = findViewById(R.id.btnEdit);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setVisibility(View.INVISIBLE);
        btnViewJobs = findViewById(R.id.btnViewJobs);
        btnDelete = findViewById(R.id.btnDelete);
        btnApplications = findViewById(R.id.btnViewApplications);
        btnInterviews = findViewById(R.id.btnInterviews);
        btnQueries = findViewById(R.id.btnQueries);
        tvLogout = findViewById(R.id.logout);
        tvLogout.setClickable(true);
        etURL.setEnabled(false);

        //MainActivity.java
        //START
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.industry_values, R.layout.org_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIndustry.setAdapter(adapter);
        spIndustry.setEnabled(false);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                R.array.location_values, R.layout.org_spinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(adapter2);
        spLocation.setEnabled(false);
        //END

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrgProfile.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        Organisation organisation = new Organisation();
        organisation = SharedPrefManager.getInstance(this).getOrgDetails();
        organisationID = String.valueOf(organisation.getOrganisationID());

        loadProfile();


/*        btnUploadJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrgProfile.this, JobListingUpload.class);
                startActivity(intent);
            }
        });

 */


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.INVISIBLE);
                etOrgName.setEnabled(true);
                etPhoneNumber.setEnabled(true);
                spLocation.setEnabled(true);
                spIndustry.setEnabled(true);
                etUsername.setEnabled(true);
                etEmail.setEnabled(true);
                etPassword.setEnabled(true);
                etURL.setEnabled(true);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrg();
                btnEdit.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.INVISIBLE);
                etOrgName.setEnabled(false);
                etPhoneNumber.setEnabled(false);
                spLocation.setEnabled(false);
                spIndustry.setEnabled(false);
                etUsername.setEnabled(false);
                etEmail.setEnabled(false);
                etPassword.setEnabled(false);
                etURL.setEnabled(false);
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OrgInterviewSchedule.java
                //START
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView2 = li.inflate(R.layout.deleteaccount, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView2);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        deleteUser();
                                    }
                                })
                        .setNegativeButton("CANCEL",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
                //END
            }
        });

    }

    //from ApplicantProfile.java
    //START
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    //END

    //https://stackoverflow.com/questions/22505336/email-and-phone-number-validation-in-android
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void updateOrg() {
        //copied registerUser() from MainActivity & altered
        //START
        final String orgNameNew = etOrgName.getText().toString().trim();
        final String usernameNew = etUsername.getText().toString().trim();
        final String passwordNew = etPassword.getText().toString().trim();
        final String locationNew = spLocation.getSelectedItem().toString().trim();
        final String industryNew = spIndustry.getSelectedItem().toString().trim();
        final String phoneNoNew = etPhoneNumber.getText().toString();
        final String emailNew = etEmail.getText().toString().trim();
        final String urlNew = etURL.getText().toString().trim();

        //taken from ApplicantProfile.java and altered
        //START
        final String organisationID;
        Organisation organisation = new Organisation();
        organisation = SharedPrefManager.getInstance(this).getOrgDetails();
        organisationID = String.valueOf(organisation.getOrganisationID());
        //END


        if (usernameNew.equals("") || passwordNew.equals("") ||orgNameNew.equals("") || locationNew.equals("") || industryNew.equals("") || usernameNew.equals("") || passwordNew.equals("") || emailNew.equals("") || phoneNoNew.equals("") || urlNew.equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show();
        } else if (isValidMobile(phoneNoNew) == false) {
            Toast.makeText(getApplicationContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
        } else if (isValidMail(emailNew) == false) {
            Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_ORG,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //did this code in RegisterApplicant.java, copied it across and altered it
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                //'Applicant registered successfully' comes from the PHP code
                                if (message.equals("Profile details updated successfully")) {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    //https://stackoverflow.com/questions/4149415/onscreen-keyboard-opens-automatically-when-activity-starts
                                    //START
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                    //END
                                    /*
                                    tvTitle.setText("");
                                    tvTitle.setText(orgName);
                                    loadProfile();

                                     */
                                } else {
                                    loadProfile();
                                    /*
                                    etUsername.setText("");
                                    etUsername.setText(usernameSET);
                                    etPhoneNumber.setText("");
                                    etPhoneNumber.setText(phoneSET);
                                    etEmail.setText("");
                                    etEmail.setText(emailSET);

                                     */
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("orgName", orgNameNew);
                    params.put("username", usernameNew);
                    params.put("password", passwordNew);
                    params.put("location", locationNew);
                    params.put("industry", industryNew);
                    params.put("email", emailNew);
                    params.put("phoneNumber", phoneNoNew);
                    params.put("organisationID", organisationID);
                    params.put("url", urlNew);
                    return params;
                }
            };


            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
            //END
        }

        //ApplicantProfile.java
        //START
    }
    private void deleteUser() {
        //from method above
        final String organisationID;
        Organisation organisation = new Organisation();
        organisation = SharedPrefManager.getInstance(this).getOrgDetails();
        organisationID = String.valueOf(organisation.getOrganisationID());


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DELETE_ORG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //jsonObject.getString logic comes from the userLogin() method in LoginActivity.java
                            String message = jsonObject.getString("message");
                            //'Applicant registered successfully' comes from the PHP code
                            if (message.equals("Organisation deleted successfully")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(OrgProfile.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("organisationID", organisationID);
                return params;


            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void queries(){
        //taken from ApplicationsPerOrg.java loadApplications()
        //START
        final String organisationID;
        Organisation organisation = new Organisation();
        organisation = SharedPrefManager.getInstance(getApplicationContext()).getOrgDetails();
        organisationID = String.valueOf(organisation.getOrganisationID());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_APP_PER_ORG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jobListings = new JSONArray(response);

                    if (jobListings.isNull(0)) {
                        Toast.makeText(getApplicationContext(), "No stats currently exist because no applications have yet been made!", Toast.LENGTH_LONG).show();
                        bottomNavigationView.setSelectedItemId(R.id.profile);
                    } else {
                        Intent intent = new Intent(OrgProfile.this, Queries.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrgProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("organisationID", organisationID);

                return params;

            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void applications(){

        //taken from ApplicationsPerOrg.java loadApplications()
        //START
        final String organisationID;
        Organisation organisation = new Organisation();
        organisation = SharedPrefManager.getInstance(getApplicationContext()).getOrgDetails();
        organisationID = String.valueOf(organisation.getOrganisationID());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_APP_PER_ORG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jobListings = new JSONArray(response);

                    if (jobListings.isNull(0)) {
                        Toast.makeText(getApplicationContext(), "No applicants have submitted applications!", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(OrgProfile.this, ApplicationsPerOrg.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrgProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("organisationID", organisationID);

                return params;

            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

//END
    }

    public void interviews(){
        //ApplicantProfile btnViewApplications click listener
        //START
        final String organisationID;
        Organisation organisation = new Organisation();
        organisation = SharedPrefManager.getInstance(getApplicationContext()).getOrgDetails();
        organisationID = String.valueOf(organisation.getOrganisationID());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ORG_INTERVIEW_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray apps = new JSONArray(response);

                    if (apps.isNull(0)) {
                        Toast.makeText(getApplicationContext(), "You have no scheduled interviews!", Toast.LENGTH_LONG).show();
                        bottomNavigationView.setSelectedItemId(R.id.profile);

                    } else{
                        Intent intent = new Intent(OrgProfile.this, OrgInterviewSchedule.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrgProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("organisationID", organisationID);

                return params;

            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        //END
    }

    public void jobs(){

        //taken from JobListingsPerOrg.java load jobs()
        //START
        final String organisationID;
        Organisation organisation = new Organisation();
        organisation = SharedPrefManager.getInstance(getApplicationContext()).getOrgDetails();
        organisationID = String.valueOf(organisation.getOrganisationID());
        //END

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_JOB_PER_ORG_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jobListings = new JSONArray(response);

                    if (jobListings.isNull(0)){
                        Toast.makeText(getApplicationContext(), "You have not uploaded any job listings!", Toast.LENGTH_LONG).show();
                        bottomNavigationView.setSelectedItemId(R.id.profile);

                    } else {
                        Intent intent = new Intent(OrgProfile.this, JobListingsPerOrg.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrgProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("organisationID", organisationID);

                return params;

            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    /*
    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.profile);

    }

     */

    //https://stackoverflow.com/questions/17719634/how-to-exit-an-android-app-programmatically
    //START
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
    //END


    public void loadProfile() {
//https://www.youtube.com/watch?v=rfhX1aE7zX0&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=13 TUTORIAL 13


        if (SharedPrefManager.getInstance(this).isLoggedIn()) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_RETRIEVE_ORG,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                Log.i("tagconvertstr", "["+response+"]");
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject obj = null;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    obj = jsonArray.getJSONObject(i);

                                    username = obj.getString("username");
                                    password = obj.getString("password");
                                    orgName = obj.getString("orgName");
                                    url = obj.getString("url");
                                    email = obj.getString("email");
                                    phoneNumber = obj.getString("phoneNumber");
                                    location = obj.getString("location");
                                    industry = obj.getString("industry");

                                    Log.d("FIRST", orgName);
                                    //from ApplicantProfile.java code
                                    tvTitle.setText(orgName);
                                    etUsername.setText(username);
                                    etPassword.setText(password);
                                    Log.d("SECOND", orgName);
                                    etOrgName.setText(orgName);
                                    etURL.setText(url);
                                    usernameSET = username;
                                    phoneSET = phoneNumber;
                                    emailSET = email;

                                    //OrgSingleJobListing.java
                                    //START
                                    if (location.equals("Cork")) {
                                        spLocation.setSelection(0);
                                    } else if (location.equals("Dublin")){
                                        spLocation.setSelection(1);
                                    } else if (location.equals("Galway")){
                                        spLocation.setSelection(2);
                                    } else if (location.equals("Limerick")){
                                        spLocation.setSelection(3);
                                    } else if (location.equals("Kerry")){
                                        spLocation.setSelection(4);
                                    } else if (location.equals("Other")){
                                        spLocation.setSelection(4);
                                    }
                                    //END

                                    //OrgSingleJobListing.java
                                    //START
                                    if (industry.equals("Nursing")) {
                                        spIndustry.setSelection(0);
                                    } else if (industry.equals("Clerical")){
                                        spIndustry.setSelection(1);
                                    } else if (industry.equals("Technology")){
                                        spIndustry.setSelection(2);
                                    } else if (industry.equals("Accounting")){
                                        spIndustry.setSelection(3);
                                    } else if (industry.equals("Consulting")){
                                        spIndustry.setSelection(4);
                                    } else if (industry.equals("Retail")){
                                        spIndustry.setSelection(5);
                                    } else if (industry.equals("Education")){
                                        spIndustry.setSelection(6);
                                    } else if (industry.equals("Medical")){
                                        spIndustry.setSelection(7);
                                    } else if (industry.equals("Hospitality")){
                                        spIndustry.setSelection(8);
                                    } else if (industry.equals("Other")){
                                        spIndustry.setSelection(9);
                                    }
                                    //END
                                    etEmail.setText(email);
                                    etPhoneNumber.setText(phoneNumber);


                                    //END
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("organisationID", organisationID);

                    return params;

                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        } else {
            finish();
            Intent intent = new Intent(OrgProfile.this, LoginActivity.class);
            startActivity(intent);
        }


    }
}