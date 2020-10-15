package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ApplicationCV extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    //CODE FROM ApplicantProfileCV.java
    //START

    final Context context = this;

    TextView tvName, tvEmail, tvPhone;
    ImageButton btnEmail, btnInterview;
    EditText etPosition1, etOrg1, etNoYears1, etDuties1, etPosition2, etOrg2, etNoYears2, etDuties2, etInstitution1,
            etCertification1, etInstitution2, etCertification2, etSkills, etAwards, etPublications, etInterests, etAdditional;
    String position1;
    String position2;
    String org1;
    String org2;
    String noYears1;
    String noYears2;
    String duties1;
    String duties2;
    String institution1;
    String institution2;
    String certification1;
    String certification2;
    String skills;
    String awards;
    String publications;
    String interests;
    String additional;
    String applicantID;
    String applicantName;
    String email;
    String phone;

    String eFName;
    String aEmail;
    String eOrgName;
    String eRoleTitle;
    String oEmail;


    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    String location;
    String applicationID;
    String time;
    String additionalInterview;

    BottomNavigationView bottomNavigationView;

    static final String Tag = "*********FYP*********";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_cv);

        //https://stackoverflow.com/questions/4149415/onscreen-keyboard-opens-automatically-when-activity-starts
        //START
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //END

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.applications);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent3 = new Intent(ApplicationCV.this, OrgProfile.class);
                        startActivity(intent3);
                        return true;
                    case R.id.jobs:
                        Intent intent = new Intent(ApplicationCV.this, JobListingsPerOrg.class);
                        startActivity(intent);
                        return true;
                    case R.id.applications:
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
                                        //bottomNavigationView.setSelectedItemId(R.id.reports);
                                    } else {
                                        Intent intent = new Intent(ApplicationCV.this, ApplicationsPerOrg.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ApplicationCV.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        return true;
                    case R.id.interviews:
                        //ApplicantProfile btnViewApplications click listener
                        //START
                        organisation = SharedPrefManager.getInstance(getApplicationContext()).getOrgDetails();
                        organisationID = String.valueOf(organisation.getOrganisationID());

                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_ORG_INTERVIEW_LIST, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray apps = new JSONArray(response);

                                    if (apps.isNull(0)) {
                                        Toast.makeText(getApplicationContext(), "You have no scheduled interviews!", Toast.LENGTH_LONG).show();
                                        //bottomNavigationView.setSelectedItemId(R.id.reports);
                                    } else{
                                        Intent intent = new Intent(ApplicationCV.this, OrgInterviewSchedule.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ApplicationCV.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("organisationID", organisationID);

                                return params;

                            }
                        };

                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);
                        return true;
                    case R.id.reports:
                        Intent intent5 = new Intent(ApplicationCV.this, Queries.class);
                        startActivity(intent5);
                        return true;
                }
                return false;
            }
        });
        //END


        tvName = findViewById(R.id.tvApplicantName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        etPosition1 = findViewById(R.id.etPosition1);
        etPosition2 = findViewById(R.id.etPosition2);
        etOrg1 = findViewById(R.id.etOrg1);
        etOrg2 = findViewById(R.id.etOrg2);
        etNoYears1 = findViewById(R.id.etNoYears1);
        etNoYears2 = findViewById(R.id.etNoYears2);
        etDuties1 = findViewById(R.id.etDuties1);
        etDuties2 = findViewById(R.id.etDuties2);
        etInstitution1 = findViewById(R.id.etInstitution1);
        etInstitution2 = findViewById(R.id.etInstitution2);
        etCertification1 = findViewById(R.id.etCertification1);
        etCertification2 = findViewById(R.id.etCertification2);
        etSkills = findViewById(R.id.etSkills);
        etAwards = findViewById(R.id.etAwards);
        etPublications = findViewById(R.id.etPublications);
        etInterests = findViewById(R.id.etInterests);
        etAdditional = findViewById(R.id.etAdditional);
        btnInterview = findViewById(R.id.btnInterview);

        btnEmail = findViewById(R.id.imageButton);
        //END


        Intent intent = getIntent();
        applicationID = intent.getStringExtra("applicationID");

        Intent intent2 = getIntent();
        applicantID = intent2.getStringExtra("applicantID");

        loadCV();

        btnInterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkInterview();

                }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //https://medium.com/@cketti/android-sending-email-using-intents-3da63662c58f
                //START
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                String recipient = "mailto:"+email;
                emailIntent.setData(Uri.parse(recipient));
                startActivity(emailIntent);
                //END

            }
        });


    }

    private void loadCV() {
        //CODE FROM ApplicantProfileCV.java
        //START

        Intent intent = getIntent();
        applicantID = intent.getStringExtra("applicantID");
        Log.d(Tag, applicantID);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_RETRIEVE_CV,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d(Tag,response);
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                obj = jsonArray.getJSONObject(i);
                                String message = obj.getString("message");
                                if (message.equals("CV loaded successfully")) {

                                    applicantName = (obj.getString("fName") + " " + obj.getString("lName"));
                                    email = obj.getString("email");
                                    phone = obj.getString("phoneNumber");
                                    position1 = obj.getString("workHistory1Position");
                                    Log.d(Tag, position1);
                                    org1 = obj.getString("workHistory1Organisation");
                                    noYears1 = obj.getString("workHistory1NumberOfYears");
                                    duties1 = obj.getString("workHistory1Duties");
                                    position2 = obj.getString("workHistory2Position");
                                    org2 = obj.getString("workHistory2Organisation");
                                    noYears2 = obj.getString("workHistory2NumberOfYears");
                                    duties2 = obj.getString("workHistory2Duties");
                                    institution1 = obj.getString("education1Institution");
                                    certification1 = obj.getString("education1Certification");
                                    institution2 = obj.getString("education2Institution");
                                    certification2 = obj.getString("education2Certification");
                                    skills = obj.getString("skills");
                                    awards = obj.getString("awards");
                                    publications = obj.getString("publications");
                                    interests = obj.getString("interests");
                                    additional = obj.getString("additional");

                                    etPosition1.setText(position1);
                                    if(position2.equals("")){
                                        etPosition2.setText("n/a");
                                        etPosition2.setTextColor(Color.GRAY);
                                    } else {
                                        etPosition2.setText(position2);
                                        etPosition2.setTextColor(Color.DKGRAY);
                                    }
                                    etOrg1.setText(org1);
                                    if(org2.equals("")){
                                        etOrg2.setText("n/a");
                                        etOrg2.setTextColor(Color.GRAY);
                                    } else {
                                        etOrg2.setText(org2);
                                        etOrg2.setTextColor(Color.DKGRAY);
                                    }
                                    etNoYears1.setText(noYears1);
                                    Log.d(Tag,noYears2);
                                    if(noYears2.equals("")){
                                        etNoYears2.setText("n/a");
                                        etNoYears2.setTextColor(Color.GRAY);
                                    } else {
                                        etNoYears2.setText(noYears2);
                                        etNoYears2.setTextColor(Color.DKGRAY);
                                    }
                                    etDuties1.setText(duties1);
                                    if(duties2.equals("")){
                                        etDuties2.setText("n/a");
                                        etDuties2.setTextColor(Color.GRAY);
                                    } else {
                                        etDuties2.setText(duties2);
                                        etDuties2.setTextColor(Color.DKGRAY);
                                    }
                                    etInstitution1.setText(institution1);
                                    if(institution2.equals("")){
                                        etInstitution2.setText("n/a");
                                        etInstitution2.setTextColor(Color.GRAY);
                                    } else {
                                        etInstitution2.setText(institution2);
                                        etInstitution2.setTextColor(Color.DKGRAY);
                                    }
                                    etCertification1.setText(certification1);
                                    if(certification2.equals("")){
                                        etCertification2.setText("n/a");
                                        etCertification2.setTextColor(Color.GRAY);
                                    } else {
                                        etCertification2.setText(certification2);
                                        etCertification2.setTextColor(Color.DKGRAY);
                                    }
                                    if(skills.equals("")){
                                        etSkills.setText("n/a");
                                        etSkills.setTextColor(Color.GRAY);
                                    } else {
                                        etSkills.setText(skills);
                                        etSkills.setTextColor(Color.DKGRAY);
                                    }
                                    if(awards.equals("")){
                                        etAwards.setText("n/a");
                                        etAwards.setTextColor(Color.GRAY);
                                    } else {
                                        etAwards.setText(awards);
                                        etAwards.setTextColor(Color.DKGRAY);
                                    }
                                    if(publications.equals("")){
                                        etPublications.setText("n/a");
                                        etPublications.setTextColor(Color.GRAY);
                                    } else {
                                        etPublications.setText(publications);
                                        etPublications.setTextColor(Color.DKGRAY);
                                    }
                                    if(interests.equals("")){
                                        etInterests.setText("n/a");
                                        etInterests.setTextColor(Color.GRAY);
                                    } else {
                                        etInterests.setText(interests);
                                        etInterests.setTextColor(Color.DKGRAY);
                                    }
                                    if(additional.equals("")){
                                        etAdditional.setText("n/a");
                                        etAdditional.setTextColor(Color.GRAY);
                                    } else {
                                        etAdditional.setText(additional);
                                        etAdditional.setTextColor(Color.DKGRAY);
                                    }
                                    tvName.setText(applicantName);
                                    tvEmail.setText(email);
                                    tvPhone.setText(phone);


                                } else {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                }
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

    }
    //END

    //https://www.youtube.com/watch?v=a_Ap6T4RlYU
    //START
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(ApplicationCV.this, ApplicationCV.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();


    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;

            //https://mkyong.com/android/android-prompt-user-input-dialog-example/
            //START
            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.prompts, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                    location = userInput.getText().toString().trim();
                                    LayoutInflater li = LayoutInflater.from(context);
                                    View promptsView2 = li.inflate(R.layout.prompt_additional, null);

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            context);

                                    // set prompts.xml to alertdialog builder
                                    alertDialogBuilder.setView(promptsView2);

                                    final EditText userInput = (EditText) promptsView2
                                            .findViewById(R.id.etAdditional);

                                    // set dialog message
                                    alertDialogBuilder
                                            .setCancelable(false)
                                            .setPositiveButton("OK",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            // get user input and set it to result
                                                            // edit text
                                                            additionalInterview = userInput.getText().toString().trim();
                                                            createInterview();
                                                        }
                                                    })
                                            .setNegativeButton("Cancel",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.cancel();
                                                        }
                                                    });

                                    // create alert dialog
                                    AlertDialog alertDialog = alertDialogBuilder.create();

                                    // show it
                                    alertDialog.show();
                                    //END
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            //END
    }
    //END

    public void createInterview() {

        final String organisationID;
        Organisation organisation = new Organisation();
        organisation = SharedPrefManager.getInstance(getApplicationContext()).getOrgDetails();
        organisationID = String.valueOf(organisation.getOrganisationID());

        monthFinal = monthFinal + 1;
        time = yearFinal + "-" + monthFinal + "-" + dayFinal + " " + hourFinal + ":" + minuteFinal + ":00.000000";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_CREATE_INTERVIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //https://stackoverflow.com/questions/10376645/org-json-jsonexception-value-br-of-type-java-lang-string-cannot-be-converted-t
                            //START
                            Log.i("tagconvertstr", "["+response+"]");
                            //END
                            JSONObject jsonObject = new JSONObject(response);
                            //jsonObject.getString logic comes from the userLogin() method in LoginActivity.java
                            String message = jsonObject.getString("message");
                            //'Applicant registered successfully' comes from the PHP code
                            if (message.equals("Interview scheduled successfully")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();


                                //https://stackoverflow.com/questions/4149415/onscreen-keyboard-opens-automatically-when-activity-starts
                                //START
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                //END

                                //Inner string request for when an interview is successfully scheduled, an email containing the details of that
                                //interview is sent to the applicant
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_INTERVIEW_EMAIL,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    Log.i("tagconvertstr", "["+response+"]");
                                                    JSONArray jsonArray = new JSONArray(response);
                                                    JSONObject obj = null;
                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                        obj = jsonArray.getJSONObject(i);
                                                        eFName = obj.getString("fName");
                                                        aEmail = obj.getString("aEmail");
                                                        eOrgName = obj.getString("orgName");
                                                        eRoleTitle = obj.getString("roleTitle");
                                                        oEmail = obj.getString("oEmail");

                                                        //https://pepipost.com/tutorials/send-email-in-android-using-javamail-api/
                                                        //START
                                                        new Thread(new Runnable() {

                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    GMailSender sender = new GMailSender("noreply.rerecruit@gmail.com",
                                                                            "116311691FYP");
                                                                    sender.sendMail("Interview for " + eRoleTitle + " " + dayFinal + "-" + monthFinal + '-' + yearFinal ,
                                                                            "Hey " + eFName + ", \n\nWe are delighted to inform you that " + eOrgName + " " +
                                                                                    "has invited you to interview for the position of " + eRoleTitle + "!\n\nDate: " + dayFinal + "-" + monthFinal + "-" +
                                                                                    yearFinal + ".\nTime: " + hourFinal + ":" + minuteFinal + ".\nLocation: " + location + ". \n\n" +
                                                                                    additionalInterview + "\n" + "Any queries please contact " + oEmail + " directly.",
                                                                            "noreply.rerecruit@gmail.comm", " " + aEmail);
                                                                } catch (Exception e) {
                                                                    Log.e("SendMail", e.getMessage(), e);
                                                                }
                                                            }

                                                        }).start();
                                                         /*
                                                        Intent i = new Intent(Intent.ACTION_SEND);
                                                        i.setType("message/rfc822");
                                                        i.putExtra(Intent.EXTRA_EMAIL , new String[]{"recipient@example.com"});
                                                        i.putExtra(Intent.EXTRA_SUBJECT, "Subject of Your Email");
                                                        i.putExtra(Intent.EXTRA_TEXT , "The HTML/TEXT body content of the email");
                                                        try {
                                                            startActivity(Intent.createChooser(i, "Send mail..."));
                                                        } catch (android.content.ActivityNotFoundException ex) {
                                                            Toast.makeText(LoginActivity.this, "No email client configured. Please check.", Toast.LENGTH_SHORT).show();
                                                        }
                                                        */
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

                                        Log.d("TAG****", applicationID);
                                        Log.d("TAG****", applicantID);
                                        Log.d("TAG****", organisationID);
                                        params.put("applicationID", applicationID);
                                        params.put("applicantID", applicantID);
                                        params.put("organisationID", organisationID);

                                        return params;

                                    }
                                };

                                RequestHandler.getInstance(ApplicationCV.this).addToRequestQueue(stringRequest);


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
                Log.d(Tag, applicationID);
                Log.d(Tag, location);
                Log.d(Tag, additionalInterview);
                Log.d(Tag, time);
                params.put("applicationID", applicationID);
                params.put("location", location);
                params.put("additional", additionalInterview);
                params.put("time", time);
                params.put("outcome", "Pending");
                return params;


            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    public void checkInterview(){
            //ApplicantProfile.java checkCVView()

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_RETRIEVE_INTERVIEW,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                Log.d(Tag, response);
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject obj = null;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    obj = jsonArray.getJSONObject(i);
                                    //END
                                    String message = obj.getString("message");
                                    //idea for this came from Login.java obj.getString()s
                                    if (message.equals("Interview loaded successfully")) {

                                        if (!obj.getString("interviewID").equals("")){

                                            Toast.makeText(getApplicationContext(), "You already scheduled an interview for this applicant! Please return to the schedule to make changes.", Toast.LENGTH_LONG).show();

                                             } else {

                                            //https://www.youtube.com/watch?v=a_Ap6T4RlYU
                                            //START
                                            Calendar c = Calendar.getInstance();
                                            year = c.get(Calendar.YEAR);
                                            month = c.get(Calendar.MONTH);
                                            day = c.get(Calendar.DAY_OF_MONTH);

                                            DatePickerDialog datePickerDialog = new DatePickerDialog(ApplicationCV.this, ApplicationCV.this, year, month, day);
                                            datePickerDialog.show();
                                            //END
                                             }} else {
                                        Calendar c = Calendar.getInstance();
                                        year = c.get(Calendar.YEAR);
                                        month = c.get(Calendar.MONTH);
                                        day = c.get(Calendar.DAY_OF_MONTH);

                                        DatePickerDialog datePickerDialog = new DatePickerDialog(ApplicationCV.this, ApplicationCV.this, year, month, day);
                                        datePickerDialog.show();
                                    }
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

                    Log.d(Tag, applicationID);
                    params.put("applicationID", applicationID);

                    return params;

                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

            //END
        }
        /*
    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.applications);

    }

         */
    }

