package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Objects;

public class SingleJobListing extends AppCompatActivity {

    TextView tvRoleTitle, tvLocation, tvRequirements, tvRoleDuties, tvSalary, tvOrgName, tvURL;
    String jobListingID;
    Button btnApply;
    String applicantID;
    final Context context = this;
    BottomNavigationView bottomNavigationView;

    String organisationID;

    String additionalCV;

    String eFName, eLName, eOrgName, eRoleTitle, oEmail, eAdditional;

    private static final String Tag = "*****FYP****";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_job_listing);

        btnApply = findViewById(R.id.btnApply);
        tvRoleTitle = findViewById(R.id.tvRoleTitle);
        tvLocation = findViewById(R.id.tvLocation);
        tvRequirements = findViewById(R.id.tvRequirements);
        tvRoleDuties = findViewById(R.id.tvRoleDuties);
        tvSalary = findViewById(R.id.tvSalary);
        tvOrgName = findViewById(R.id.tvOrgName);
        tvURL = findViewById(R.id.etURL);

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.jobs);
        //END

        //https://stackoverflow.com/questions/2736389/how-to-pass-an-object-from-one-activity-to-another-on-android
        Intent intent = getIntent();
        jobListingID = intent.getStringExtra("jobListingID");

        loadJob();

        /*
        tvRoleTitle.setText(job.getRoleTitle());
        tvLocation.setText(job.getLocation());
        tvRequirements.setText(job.getRoleRequirements());
        tvRoleDuties.setText(job.getRoleDuties());
        tvSalary.setText(job.getSalary());
*/




        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkApp();

            }
        });


        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent = new Intent(SingleJobListing.this, ApplicantProfile.class);
                        startActivity(intent);
                        return true;
                    case R.id.jobs:
                        Intent intent2 = new Intent(SingleJobListing.this, JobListings.class);
                        startActivity(intent2);
                        return true;
                    case R.id.applications:
                        final String applicantID;
                        Applicant applicant = new Applicant();
                        applicant = SharedPrefManager.getInstance(getApplicationContext()).getApplicantDetails();
                        applicantID = String.valueOf(applicant.getApplicantID());

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_APP_PER_USER, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray apps = new JSONArray(response);

                                    if (apps.isNull(0)) {
                                        Toast.makeText(getApplicationContext(), "You have not submitted any applications!", Toast.LENGTH_LONG).show();
                                        //bottomNavigationView.setSelectedItemId(R.id.jobs);
                                        //bottomNavigationView.setSelectedItemId(R.id.advice);
                                    } else{
                                        Intent intent = new Intent(SingleJobListing.this, applicantApplications.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SingleJobListing.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        /*
                        Intent intent4 = new Intent(Advice.this, applicantApplications.class);
                        startActivity(intent4);
                        */
                        return true;
                    case R.id.interviews:
                        applicant = SharedPrefManager.getInstance(getApplicationContext()).getApplicantDetails();
                        applicantID = String.valueOf(applicant.getApplicantID());

                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_APPLICANT_INTERVIEWS, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray apps = new JSONArray(response);

                                    if (apps.isNull(0)) {
                                        Toast.makeText(getApplicationContext(), "You have no interviews scheduled!", Toast.LENGTH_LONG).show();
                                        //bottomNavigationView.setSelectedItemId(R.id.advice);
                                    } else{
                                        Intent intent = new Intent(SingleJobListing.this, ApplicantInterviews.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SingleJobListing.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("applicantID", applicantID);

                                return params;

                            }
                        };

                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);
                        /*
                        Intent intent2 = new Intent(Advice.this, ApplicantInterviews.class);
                        startActivity(intent2);
                         */
                        return true;
                    case R.id.advice:
                        Intent intent5 = new Intent(SingleJobListing.this, Advice.class);
                        startActivity(intent5);
                        return true;
                }
                return false;
            }
        });

        //END

    }

        private void loadJob(){
            final String applicantID;
            Applicant applicant = new Applicant();
            applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
            applicantID = String.valueOf(applicant.getApplicantID());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DISPLAY_JOB,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                Log.i("tagconvertstr", "["+response+"]");
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject obj = null;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    obj = jsonArray.getJSONObject(i);
                                    int jobListingID = obj.getInt("jobListingID");
                                    organisationID = String.valueOf(obj.getInt("organisationID"));
                                    String roleTitle = obj.getString("roleTitle");
                                    String location = obj.getString("location");
                                    String roleRequirements = obj.getString("roleRequirements");
                                    String roleDuties = obj.getString("roleDuties");
                                    String salary = obj.getString("salary");
                                    String listingStatus = obj.getString("listingStatus");
                                    String orgName = obj.getString("orgName");
                                    String url = obj.getString("url");
                                    tvRoleTitle.setText(roleTitle);
                                    tvLocation.setText(location);
                                    tvRequirements.setText(roleRequirements);
                                    tvRoleDuties.setText(roleDuties);
                                    String salaryTxt = "€" + salary;
                                    tvSalary.setText(salaryTxt);
                                    tvOrgName.setText(orgName);

                                    //Advice.java
                                    //START
                                    tvURL.setClickable(true);
                                    tvURL.setMovementMethod(LinkMovementMethod.getInstance());
                                    String urlText = "<a href='" + url + "'> " + url + " </a>";
                                    tvURL.setText(Html.fromHtml(urlText));
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

                    Log.d("TAG****", jobListingID);
                    params.put("jobListingID", jobListingID);

                    return params;

                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }

    private void checkApp() {
            //START
            //ApplicantProfileCV.java checkCVUpload()

        final String applicantID;
        Applicant applicant = new Applicant();
        applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
        applicantID = String.valueOf(applicant.getApplicantID());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_RETRIEVE_APP,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                Log.i("tagconvertstr", "["+response+"]");
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject obj = null;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    obj = jsonArray.getJSONObject(i);
                                    //END
                                    String message = obj.getString("message");
                                    //idea for this came from Login.java obj.getString()s
                                    if (message.equals("Application loaded successfully")) {

                                        Toast.makeText(getApplicationContext(), "You have already applied for this job!", Toast.LENGTH_LONG).show();

                                    } else {
                                            checkCV();
                                        }}

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
                    params.put("jobListingID", jobListingID);

                    return params;

                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

            //END
        }


        public void createApplication() {

            final String applicantID;
            Applicant applicant = new Applicant();
            applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
            applicantID = String.valueOf(applicant.getApplicantID());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_CREATE_APPLICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.i("tagconvertstr", "["+response+"]");
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d(Tag, response);
                            //jsonObject.getString logic comes from the userLogin() method in LoginActivity.java
                            String message = jsonObject.getString("message");
                            //'Applicant registered successfully' comes from the PHP code
                            if (message.equals("Application submitted successfully")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                //ApplicationCV.java
                                //START
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_APPLICATION_EMAIL,
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
                                                        eLName = obj.getString("lName");
                                                        eOrgName = obj.getString("orgName");
                                                        eRoleTitle = obj.getString("roleTitle");
                                                        oEmail = obj.getString("email");
                                                        eAdditional = obj.getString("additional");
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    GMailSender sender = new GMailSender("noreply.rerecruit@gmail.com",
                                                                            "116311691FYP");
                                                                    sender.sendMail("Application for " + eRoleTitle + " submitted",
                                                                            "Hey " + eOrgName + ", \n" + eFName + " " + eLName + " has submitted an application for the position of "
                                                                                    + eRoleTitle + ".\n'" + eAdditional + "'\n\nTo view their CV and/or schedule an interview please visit your app!",
                                                                            "noreply.rerecruit@gmail.com", " " + oEmail);
                                                                } catch (Exception e) {
                                                                    Log.e("SendMail", e.getMessage(), e);
                                                                }
                                                            }

                                                        }).start();
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
                                        Log.d("TAG****", jobListingID);
                                        Log.d("TAG****", applicantID);
                                        Log.d("TAG****", organisationID);
                                        params.put("organisationID", organisationID);
                                        params.put("applicantID", applicantID);
                                        params.put("jobListingID", jobListingID);
                                        return params;
                                    }
                                };
                                RequestHandler.getInstance(SingleJobListing.this).addToRequestQueue(stringRequest);
                                //END
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
                Log.d("TAG", jobListingID);
                params.put("jobListingID", jobListingID);
                Log.d("TAG", applicantID);
                params.put("applicantID", applicantID);
                if (additionalCV.equals("")){
                    additionalCV = "n/a";
                    Log.d("TAG", additionalCV);
                    params.put("additionalCV", additionalCV);
                } else {
                    Log.d("TAG", additionalCV);
                    params.put("additionalCV", additionalCV);
                }

                return params;


            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void checkCV() {

        final String applicantID;
        Applicant applicant = new Applicant();
        applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
        applicantID = String.valueOf(applicant.getApplicantID());

            //from ApplicantProfile.java checkCVView()
            //START
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

                                            LayoutInflater li = LayoutInflater.from(context);
                                            View promptsView = li.inflate(R.layout.prompt_application, null);

                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                    context);

                                            // set prompts.xml to alertdialog builder
                                            alertDialogBuilder.setView(promptsView);

                                            final EditText userInput = (EditText) promptsView
                                                    .findViewById(R.id.etAdditional);

                                            // set dialog message
                                            alertDialogBuilder
                                                    .setCancelable(false)
                                                    .setPositiveButton("OK",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    // get user input and set it to result
                                                                    // edit text
                                                                    additionalCV = userInput.getText().toString().trim();
                                                                    createApplication();
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



                                        } else {
                                            Toast.makeText(getApplicationContext(), "You must upload a CV to submit an application!", Toast.LENGTH_LONG).show();
                                        }}

                            }}catch (JSONException e) {
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

        /*
    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.jobs);

    }

         */
    }
