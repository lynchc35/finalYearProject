package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class ApplicantProfile extends AppCompatActivity {

    TextView tvApplicantName, tvLogout;
    EditText etUsername, etPassword, etFName, etLName, etAge, etAddress, etEmail, etPhone, etRolePref;


    Spinner spLocation, spIndustry, spBreakReason;

    String usernameSET, emailSET, phoneSET;

    ImageButton btnEdit, btnSubmit, btnDelete;
    Button btnViewJobs, btnUploadCV, btnViewCV, btnViewApplications, btnViewInterviews;
    final Context context = this;
    BottomNavigationView bottomNavigationView;
    String applicantID;
    String username, password, fName, lName, age, email, phoneNumber, breakReason, locationPref, industryPref, rolePref, address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_profile);

        //https://stackoverflow.com/questions/4149415/onscreen-keyboard-opens-automatically-when-activity-starts
        //START
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //END

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        //END

        tvApplicantName = findViewById(R.id.tvApplicantName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etFName = findViewById(R.id.etFName);
        etLName = findViewById(R.id.etLName);
        etAge = findViewById(R.id.etAge);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        spBreakReason = findViewById(R.id.spBreakReason);
        spBreakReason.setEnabled(false);
        spLocation = findViewById(R.id.spLocation);
        spLocation.setEnabled(false);
        spIndustry = findViewById(R.id.spIndustry);
        spIndustry.setEnabled(false);
        etRolePref = findViewById(R.id.etRolePref);
        btnViewJobs = findViewById(R.id.btnViewJobs);
        btnUploadCV = findViewById(R.id.btnUploadCV);
        btnViewCV = findViewById(R.id.btnViewCV);
        btnEdit = findViewById(R.id.btnEdit);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setVisibility(View.INVISIBLE);
        btnDelete = findViewById(R.id.btnDelete);
        btnViewApplications = findViewById(R.id.btnViewApplications);
        btnViewInterviews = findViewById(R.id.btnViewInterviews);
        tvLogout = findViewById(R.id.logout);
        tvLogout.setClickable(true);

        loadProfile();



            /*
            tvApplicantName.setText((String.valueOf(applicant.getfName()) + " " + String.valueOf(applicant.getlName())));
            etUsername.setText(applicant.getUsername());
            etPassword.setText(applicant.getPassword());
            etFName.setText(applicant.getfName());
            etLName.setText(applicant.getlName());
            etAge.setText(applicant.getAge());
            etAddress.setText(applicant.getAddress());
            etEmail.setText(applicant.getEmail());
            etPhone.setText(applicant.getPhoneNumber());
            usernameSET = applicant.getUsername();
            emailSET = applicant.getEmail();
            phoneSET = applicant.getPhoneNumber();
            //https://stackoverflow.com/questions/2390102/how-to-set-selected-item-of-spinner-by-value-not-by-position
            //START
            String nursing = "Nursing";
            String clerical = "Clerical";
            String technology = "Technology";
            String accounting = "Accounting";
            String consulting = "Consulting";
            String retail = "Retail";
            String education = "Education";
            String medical = "Medical";
            String hospitality = "Hospitality";
            //RegisterApplicant.java
            //START
            ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                    R.array.industry_values, R.layout.app_spinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spIndustry.setAdapter(adapter);
            //END
            Log.d("FYP*****",applicant.getIndustryPref());
            if (applicant.getIndustryPref().equals(nursing)) {
                spIndustry.setSelection(0);
            } else if (applicant.getIndustryPref().equals(clerical)){
                spIndustry.setSelection(1);
            } else if (applicant.getIndustryPref().equals(accounting)){
                spIndustry.setSelection(2);
            } else if (applicant.getIndustryPref().equals(technology)){
                spIndustry.setSelection(3);
            } else if (applicant.getIndustryPref().equals(consulting)){
                spIndustry.setSelection(4);
            } else if (applicant.getIndustryPref().equals(retail)){
                spIndustry.setSelection(5);
            } else if (applicant.getIndustryPref().equals(education)){
                spIndustry.setSelection(6);
            } else if (applicant.getIndustryPref().equals(medical)){
                spIndustry.setSelection(7);
            } else if (applicant.getIndustryPref().equals(hospitality)){
                spIndustry.setSelection(8);
            }


            String cork = "Cork";
            String dublin = "Dublin";
            String galway = "Galway";
            String limerick = "Limerick";
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.location_values, R.layout.app_spinner);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spLocation.setAdapter(adapter2);
            if (applicant.getLocationPref().equals(cork)) {
                spLocation.setSelection(0);
            } else if (applicant.getLocationPref().equals(dublin)){
                spLocation.setSelection(1);
            } else if (applicant.getLocationPref().equals(galway)){
                spLocation.setSelection(2);
            } else if (applicant.getLocationPref().equals(limerick)){
                spLocation.setSelection(3);
            }

            String children = "Children";
            String carer = "Carer";
            String illness = "Illness";
            String other = "Other";
            ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this,
                    R.array.career_values, R.layout.app_spinner);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spBreakReason.setAdapter(adapter3);
            if (applicant.getBreakReason().equals(children)) {
                spBreakReason.setSelection(0);
            } else if (applicant.getBreakReason().equals(carer)){
                spBreakReason.setSelection(1);
            } else if (applicant.getBreakReason().equals(illness)){
                spBreakReason.setSelection(2);
            } else if (applicant.getBreakReason().equals(other)){
                spBreakReason.setSelection(3);
            }
            //END
            etRolePref.setText(applicant.getRolePref());
            */


        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        //Log.d("NAVIGATION", "PROFILE");
                        return true;
                    case R.id.jobs:
                        Intent intent = new Intent(ApplicantProfile.this, JobListings.class);
                        startActivity(intent);
                        return true;
                    case R.id.applications:
                        viewApplications();
                        return true;
                    case R.id.interviews:
                        viewInterviews();
                        return true;
                    case R.id.advice:
                        Intent intent5 = new Intent(ApplicantProfile.this, Advice.class);
                        startActivity(intent5);
                        return true;

                }
                return false;
            }
        });
        //END

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplicantProfile.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnUploadCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //only allow the user onto the uploadCV activity if they have not already uploaded a CV
                checkCVUpload();
            }
        });

        btnViewCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //only allow a user to view their CV if they have uploaded one
                checkCVView();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  etPassword.setEnabled(true);
                //https://stackoverflow.com/questions/8991522/how-can-i-set-the-focus-and-display-the-keyboard-on-my-edittext-programmatical
                //START
                etUsername.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etUsername, InputMethodManager.SHOW_IMPLICIT);
                //END

                etFName.setEnabled(true);
                etLName.setEnabled(true);
                etAge.setEnabled(true);
                etAddress.setEnabled(true);
                etPhone.setEnabled(true);
                etEmail.setEnabled(true);
                spBreakReason.setEnabled(true);
                spLocation.setEnabled(true);
                spIndustry.setEnabled(true);
                etRolePref.setEnabled(true);
                etUsername.setEnabled(true);
                etPassword.setEnabled(true);
                btnSubmit.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.INVISIBLE);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
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

    public void viewApplications(){
        final String applicantID;
        Applicant applicant = new Applicant();
        applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
        applicantID = String.valueOf(applicant.getApplicantID());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_APP_PER_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray apps = new JSONArray(response);

                    if (apps.isNull(0)) {
                        Toast.makeText(getApplicationContext(), "You have not submitted any applications!", Toast.LENGTH_LONG).show();
                        bottomNavigationView.setSelectedItemId(R.id.profile);

                    } else{
                        Intent intent = new Intent(ApplicantProfile.this, applicantApplications.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApplicantProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("applicantID", applicantID);

                return params;

            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        //END
    }
    private void checkCVUpload() {
        //START
        //ApplicantProfileCV.java loadCV()
        final String applicantID;
        Applicant applicant = new Applicant();
        applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
        applicantID = String.valueOf(applicant.getApplicantID());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_RETRIEVE_CV,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                obj = jsonArray.getJSONObject(i);
                                //END
                                String message = obj.getString("message");
                                //idea for this came from Login.java obj.getString()s
                                if (message.equals("CV loaded successfully")) {

                                    if (!obj.getString("workHistory1Position").equals("")){
                                    Toast.makeText(getApplicationContext(), "You have already uploaded a CV, please press 'View CV' to make changes to it", Toast.LENGTH_LONG).show();

                                } else {
                                    Intent intent = new Intent(ApplicantProfile.this, UploadCV.class);
                                    startActivity(intent);
                                }}
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

                params.put("applicantID", applicantID);

                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        //END
        }

    private void checkCVView() {
        //SAME AS checkCVUpload() above
        //except the actions of the if statement
        //START
        final String applicantID;
        Applicant applicant = new Applicant();
        applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
        applicantID = String.valueOf(applicant.getApplicantID());
        //END

        //START
        //ApplicantProfileCV.java loadCV()
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_RETRIEVE_CV,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                obj = jsonArray.getJSONObject(i);
                                //END
                                String message = obj.getString("message");
                                //idea for this came from Login.java obj.getString()s
                                if (message.equals("CV loaded successfully")) {

                                    if (!obj.getString("workHistory1Position").equals("")){
                                        Intent intent = new Intent(ApplicantProfile.this, ApplicantProfileCV.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "You haven't yet uploaded a CV, please click 'Upload CV' to do so", Toast.LENGTH_LONG).show();
                                    }}
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

                params.put("applicantID", applicantID);

                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        //END
    }

    //https://stackoverflow.com/questions/22505336/email-and-phone-number-validation-in-android
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    //https://stackoverflow.com/questions/22505336/email-and-phone-number-validation-in-android
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void updateProfile() {
        //COPIED registerUser() from RegisterApplicant.java and altered accordingly
        //START
       // final String password = etPassword.getText().toString().trim();
        final String fNameNew = etFName.getText().toString().trim();
        final String usernameNew = etUsername.getText().toString().trim();
        final String passwordNew = etPassword.getText().toString().trim();
        final String lNameNew = etLName.getText().toString().trim();
        final String ageNew = etAge.getText().toString().trim();
        final String addressNew = etAddress.getText().toString().trim();
        final String phoneNoNew = etPhone.getText().toString();
        final String breakReasonNew = spBreakReason.getSelectedItem().toString();
        final String industryPrefNew = spIndustry.getSelectedItem().toString();
        final String locationPrefNew = spLocation.getSelectedItem().toString();
        final String rolePrefNew = etRolePref.getText().toString();
        final String emailNew = etEmail.getText().toString();

        //taken from JobListingUpload.java and altered
        //START
        final String applicantID;
        Applicant applicant = new Applicant();
        applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
        applicantID = String.valueOf(applicant.getApplicantID());
        //END

        if (usernameNew.equals("") || passwordNew.equals("") || fNameNew.equals("") || lNameNew.equals("") || ageNew.equals("") || addressNew.equals("") || emailNew.equals("") || phoneNoNew.equals("")
                || breakReasonNew.equals("") || industryPrefNew.equals("") || locationPrefNew.equals("") || rolePrefNew.equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show();
        } else if (isValidMobile(phoneNoNew) == false){
            Toast.makeText(getApplicationContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
        } else if (isValidMail(emailNew) == false) {
            Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_APPLICANT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                //jsonObject.getString logic comes from the userLogin() method in LoginActivity.java
                                String message = jsonObject.getString("message");
                                //'Applicant registered successfully' comes from the PHP code
                                if (message.equals("Profile details updated successfully")) {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    //https://stackoverflow.com/questions/4149415/onscreen-keyboard-opens-automatically-when-activity-starts
                                    //START
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                    //END
                                    /*
                                    tvApplicantName.setText("");
                                    String applicantNameDisplay = fName + " " + lName;
                                    tvApplicantName.setText(applicantNameDisplay);
                                    etUsername.setText(usernameNew);
                                    //etPassword.setText(password);
                                    //etFName.setText(fName);
                                    //etLName.setText(lName);
                                    etEmail.setText(emailNew);
                                    etPhone.setText(phoneNoNew);
                                    //etAddress.setText(address);
                                    //etAge.setText(age);
                                    //etRolePref.setText(rolePref);

                                    Log.d("UPDATE", usernameNew);
                                    Log.d("UPDATE", emailNew);
                                    Log.d("UPDATE", phoneNoNew);

                                     */

                                } else {
                                    loadProfile();
                                    /*
                                    etUsername.setText("");
                                    etUsername.setText(usernameSET);
                                    etPhone.setText("");
                                    etPhone.setText(phoneSET);
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
                   // params.put("password", password);
                    params.put("fName", fNameNew);
                    params.put("username", usernameNew);
                    params.put("password", passwordNew);
                    params.put("lName", lNameNew);
                    params.put("age", ageNew);
                    params.put("address", addressNew);
                    params.put("email", emailNew);
                    params.put("phoneNumber", phoneNoNew);
                    params.put("breakReason", breakReasonNew);
                    params.put("locationPref", locationPrefNew);
                    Log.d("TAG*****FYP", industryPrefNew);
                    Log.d("TAG*****FYP", locationPrefNew);
                    Log.d("TAG*****FYP", breakReasonNew);
                    params.put("industryPref", industryPrefNew);
                    params.put("rolePref", rolePrefNew);
                    params.put("applicantID", applicantID);
                    return params;


                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

            etFName.setEnabled(false);
            etPassword.setEnabled(false);
            etUsername.setEnabled(false);
            etEmail.setEnabled(false);
            etLName.setEnabled(false);
            etAge.setEnabled(false);
            etAddress.setEnabled(false);
            etPhone.setEnabled(false);
            spBreakReason.setEnabled(false);
            spLocation.setEnabled(false);
            spIndustry.setEnabled(false);
            etRolePref.setEnabled(false);
            btnEdit.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.INVISIBLE);
        }
        //END
    }

    private void deleteUser() {
        //from method above
        final String applicantID;
        Applicant applicant = new Applicant();
        applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
        applicantID = String.valueOf(applicant.getApplicantID());


            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DELETE_APPLICANT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                //jsonObject.getString logic comes from the userLogin() method in LoginActivity.java
                                String message = jsonObject.getString("message");
                                //'Applicant registered successfully' comes from the PHP code
                                if (message.equals("Applicant deleted successfully")) {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ApplicantProfile.this, LoginActivity.class);
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
                    params.put("applicantID", applicantID);
                    return params;


                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
        //END

    public void viewInterviews(){
        final String applicantID;
        Applicant applicant = new Applicant();
        applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
        applicantID = String.valueOf(applicant.getApplicantID());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_APPLICANT_INTERVIEWS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray apps = new JSONArray(response);

                    if (apps.isNull(0)) {
                        Toast.makeText(getApplicationContext(), "You have no interviews scheduled!", Toast.LENGTH_LONG).show();
                        bottomNavigationView.setSelectedItemId(R.id.profile);
                    } else{
                        Intent intent = new Intent(ApplicantProfile.this, ApplicantInterviews.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApplicantProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("applicantID", applicantID);

                return params;

            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        //END
    }

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
    @Override
    protected void onResume() {
        super.onResume();

    }

    public void loadProfile() {
//https://www.youtube.com/watch?v=rfhX1aE7zX0&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=13 TUTORIAL 13

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            //figured this out from my prior knowledge of objects in Java
            final Applicant applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
            applicantID = String.valueOf(applicant.getApplicantID());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_RETRIEVE_APPLICANT,
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
                                    fName = obj.getString("fName");
                                    lName = obj.getString("lName");
                                    age = obj.getString("age");
                                    address = obj.getString("address");
                                    email = obj.getString("email");
                                    phoneNumber = obj.getString("phoneNumber");
                                    breakReason = obj.getString("breakReason");
                                    locationPref = obj.getString("locationPref");
                                    industryPref = obj.getString("industryPref");
                                    rolePref = obj.getString("rolePref");

                                    etUsername.setText(username);
                                    etPassword.setText(password);
                                    etFName.setText(fName);
                                    etLName.setText(lName );
                                    etAge.setText(age);
                                    etAddress.setText(address);
                                    etEmail.setText(email);
                                    etPhone.setText(phoneNumber);
                                    usernameSET = username;
                                    emailSET = email;
                                    phoneSET = phoneNumber;
                                    String applicantNameDisplay = fName + " " + lName;
                                    tvApplicantName.setText(applicantNameDisplay);

                                    //https://stackoverflow.com/questions/2390102/how-to-set-selected-item-of-spinner-by-value-not-by-position
                                    //START
                                    String nursing = "Nursing";
                                    String clerical = "Clerical";
                                    String technology = "Technology";
                                    String accounting = "Accounting";
                                    String consulting = "Consulting";
                                    String retail = "Retail";
                                    String education = "Education";
                                    String medical = "Medical";
                                    String hospitality = "Hospitality";
                                    //RegisterApplicant.java
                                    //START
                                    ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                                            R.array.industry_values, R.layout.app_spinner);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spIndustry.setAdapter(adapter);
                                    //END
                                    if (industryPref.equals(nursing)) {
                                        spIndustry.setSelection(0);
                                    } else if (industryPref.equals(clerical)){
                                        spIndustry.setSelection(1);
                                    } else if (industryPref.equals(accounting)){
                                        spIndustry.setSelection(2);
                                    } else if (industryPref.equals(technology)){
                                        spIndustry.setSelection(3);
                                    } else if (industryPref.equals(consulting)){
                                        spIndustry.setSelection(4);
                                    } else if (industryPref.equals(retail)){
                                        spIndustry.setSelection(5);
                                    } else if (industryPref.equals(education)){
                                        spIndustry.setSelection(6);
                                    } else if (industryPref.equals(medical)){
                                        spIndustry.setSelection(7);
                                    } else if (industryPref.equals(hospitality)){
                                        spIndustry.setSelection(8);
                                    }


                                    String cork = "Cork";
                                    String dublin = "Dublin";
                                    String galway = "Galway";
                                    String limerick = "Limerick";
                                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),
                                            R.array.location_values, R.layout.app_spinner);
                                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spLocation.setAdapter(adapter2);
                                    if (locationPref.equals(cork)) {
                                        spLocation.setSelection(0);
                                    } else if (locationPref.equals(dublin)){
                                        spLocation.setSelection(1);
                                    } else if (locationPref.equals(galway)){
                                        spLocation.setSelection(2);
                                    } else if (locationPref.equals(limerick)){
                                        spLocation.setSelection(3);
                                    }

                                    String children = "Children";
                                    String carer = "Carer";
                                    String illness = "Illness";
                                    String other = "Other";
                                    ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getApplicationContext(),
                                            R.array.career_values, R.layout.app_spinner);
                                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spBreakReason.setAdapter(adapter3);
                                    if (breakReason.equals(children)) {
                                        spBreakReason.setSelection(0);
                                    } else if (breakReason.equals(carer)){
                                        spBreakReason.setSelection(1);
                                    } else if (breakReason.equals(illness)){
                                        spBreakReason.setSelection(2);
                                    } else if (breakReason.equals(other)){
                                        spBreakReason.setSelection(3);
                                    }
                                    //END
                                    etRolePref.setText(rolePref);


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

                    params.put("applicantID", applicantID);

                    return params;

                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        } else {
            finish();
            Intent intent = new Intent(ApplicantProfile.this, LoginActivity.class);
            startActivity(intent);
        }

}
}

