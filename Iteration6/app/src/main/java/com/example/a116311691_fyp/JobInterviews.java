package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobInterviews extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    //OrgInterviewSchedule.java - using same adapter & model
    //START

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    JobInterviewListAdapter adapter;
    String interviewID;
    List<InterviewListModel> interviewListModelList;
    String jobListingID, applicantID;

    String dbLocation, dbAdditional, dbFeedback, dbOutcome, dbTime;
    String additionalInterview;
    String time;
    String location;
    String feedback;
    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    final Context context = this;
    String hourSep;
    String minuteSep;
    String secondSep;
    String outcome;

    String eFName;
    String aEmail;
    String eOrgName;
    String eRoleTitle;
    String oEmail;
    String applicationID;

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_interviews);

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.jobs);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent3 = new Intent(JobInterviews.this, OrgProfile.class);
                        startActivity(intent3);
                        return true;
                    case R.id.jobs:
                        Intent intent = new Intent(JobInterviews.this, JobListingsPerOrg.class);
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
                                        Intent intent = new Intent(JobInterviews.this, ApplicationsPerOrg.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(JobInterviews.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        Intent intent = new Intent(JobInterviews.this, OrgInterviewSchedule.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(JobInterviews.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Intent intent5 = new Intent(JobInterviews.this, Queries.class);
                        startActivity(intent5);
                        return true;
                }
                return false;
            }
        });
        //END


        interviewListModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        Intent intent = getIntent();
        jobListingID = (intent.getStringExtra("jobListingID"));

        loadInterviews();

        recyclerView.addOnItemTouchListener(new MyTouchListener(getApplicationContext(), recyclerView, new MyTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {
                //DELETE INTERVIEW

                InterviewListModel interviewListModel = interviewListModelList.get(position);
                interviewID = String.valueOf(interviewListModel.getInterviewID());

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView2 = li.inflate(R.layout.prompt_confirm, null);

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
                                        deleteInterview();
                                    }
                                })
                        .setNegativeButton("CANCEL",
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

                interviewListModelList.clear();
                loadInterviews();
            }

            @Override
            public void onRightSwipe(View view, int position) {
                /*
                InterviewListModel interviewListModel = interviewListModelList.get(position);
                dbFeedback = String.valueOf(interviewListModel.getFeedback());
                interviewID = String.valueOf(interviewListModel.getInterviewID());
                applicationID = String.valueOf(interviewListModel.getApplicationID());
                applicantID = String.valueOf(interviewListModel.getApplicantID());
                dbOutcome = String.valueOf(interviewListModel.getOutcome());

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView2 = li.inflate(R.layout.prompt_feedback, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView2);

                final EditText userInput = (EditText) promptsView2
                        .findViewById(R.id.etFeedback);

                if (dbFeedback.equals("")){
                    userInput.setText("");
                } else {
                    userInput.setText(dbFeedback);
                }
                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text

                                        feedback = userInput.getText().toString().trim();
                                        addFeedback();
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

                interviewListModelList.clear();
                loadInterviews();

                 */

            }

            @Override
            public void onClick(View view, final int position) {

                InterviewListModel interviewListModel = interviewListModelList.get(position);
                interviewID = String.valueOf(interviewListModel.getInterviewID());

                Intent intent = new Intent(JobInterviews.this, SingleInterview.class);
                intent.putExtra("ID", interviewID);
                Log.d("TAG!!!", interviewID);
                applicantID = String.valueOf(interviewListModel.getApplicantID());
                applicationID = String.valueOf(interviewListModel.getApplicationID());
                intent.putExtra("position", position);
                intent.putExtra("applicantID", applicantID);
                intent.putExtra("applicationID", applicationID);
                startActivity(intent);

                /*

                InterviewListModel interviewListModel = interviewListModelList.get(position);
                interviewID = String.valueOf(interviewListModel.getInterviewID());
                dbOutcome = String.valueOf(interviewListModel.getOutcome());
                dbFeedback = String.valueOf(interviewListModel.getFeedback());
                dbLocation = String.valueOf(interviewListModel.getLocation());
                dbTime = String.valueOf(interviewListModel.getTime());
                //Toast.makeText(getApplicationContext(), interviewListModel.getApplicantName() + " is selected", Toast.LENGTH_SHORT).show();


                //START
                //https://stackoverflow.com/questions/3732790/android-split-string
                dbTime = interviewListModel.getTime();
                String[] seperated = dbTime.split(" ");
                String date = seperated[0];
                String time = seperated[1];

                String[] dateSep = date.split("-");
                String yearSep = dateSep[0];
                int year = Integer.parseInt(yearSep);
                String monthSep = dateSep[1];
                int month = Integer.parseInt(monthSep);
                //because January is stored as 00 therefore january dates were showing as February on the calendar
                month = month -1;
                String daySep = dateSep[2];
                int day = Integer.parseInt(daySep);

                String[] timeSep = time.split(":");
                hourSep = timeSep[0];
                minuteSep = timeSep[1];
                secondSep = timeSep[2];

                dbAdditional = String.valueOf(interviewListModel.getAdditional());
                dbFeedback = String.valueOf(interviewListModel.getFeedback());
                dbLocation = String.valueOf(interviewListModel.getLocation());

                Calendar c = Calendar.getInstance();
                //year = c.get(Calendar.YEAR);
                //month = c.get(Calendar.MONTH);
                //day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(JobInterviews.this, JobInterviews.this, year, month, day);
                datePickerDialog.show();

                 */

            }

            public void onLongClick(View view, final  int position){
                /*InterviewListModel interviewListModel = interviewListModelList.get(position);
                interviewID = String.valueOf(interviewListModel.getInterviewID());


                 */

            }

        }));

        //https://www.youtube.com/watch?v=Ffa0Mtd21_M
        //START
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                interviewListModelList.clear();
                loadInterviews();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //END

    }

    public void loadInterviews(){

        //different StringRequest - calls a different PHP file, code adjusted according to what's required for that
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_JOB_INTERVIEW_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jobListings = new JSONArray(response);
                    Log.d("TAG", response);

                    for(int i = 0; i<jobListings.length(); i++){
                        JSONObject jobObject = jobListings.getJSONObject(i);
                        int interviewID = jobObject.getInt("interviewID");
                        String applicantName = (jobObject.getString("fName") + " " + jobObject.getString("lName"));
                        int applicantID = jobObject.getInt("applicantID");
                        int applicationID = jobObject.getInt("applicationID");
                        int jobListingID = jobObject.getInt("jobListingID");
                        String roleTitle = jobObject.getString("roleTitle");
                        String time = jobObject.getString("time");
                        String location = jobObject.getString("location");
                        String additional = jobObject.getString("additional");
                        String feedback = jobObject.getString("feedback");
                        String outcome = jobObject.getString("outcome");

                        InterviewListModel interview = new InterviewListModel(interviewID, applicantName, applicantID, roleTitle,time, location, additional, feedback, jobListingID, applicationID, outcome);
                        interviewListModelList.add(interview);
                    }


                    adapter = new JobInterviewListAdapter(JobInterviews.this, interviewListModelList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobInterviews.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("jobListingID", jobListingID);

                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }



    //END

    //ApplicationCV.java
    //START
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1;
        dayFinal = i2;

        int hour = Integer.parseInt(hourSep);
        int minute = Integer.parseInt(minuteSep);

        Calendar c = Calendar.getInstance();
        //hour = c.get(Calendar.HOUR_OF_DAY);
        //minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(JobInterviews.this, JobInterviews.this, hour, minute, DateFormat.is24HourFormat(this));
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

        userInput.setText(dbLocation);

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

                                userInput.setText(dbAdditional);

                                // set dialog message
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        // get user input and set it to result
                                                        // edit text
                                                        additionalInterview = userInput.getText().toString().trim();
                                                        updateInterview();
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

    //OrgInterviewSchedule.java updateInterview()
    public void updateInterview() {




    }

    public void addFeedback(){

        final String organisationID;
        Organisation organisation = new Organisation();
        organisation = SharedPrefManager.getInstance(getApplicationContext()).getOrgDetails();
        organisationID = String.valueOf(organisation.getOrganisationID());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_FEEDBACK,
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
                            if (message.equals("Feedback inputted successfully")) {
                                //Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                interviewListModelList.clear();
                                loadInterviews();
                                //ApplicationCV.java
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

                                                        if (feedback.equals(dbFeedback)){

                                                        } else if (feedback.equals("")) {

                                                        } else {

                                                        new Thread(new Runnable() {

                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    GMailSender sender = new GMailSender("clodaghFYP@gmail.com",
                                                                            "116311691FYP");
                                                                    sender.sendMail("Feedback added",
                                                                            "Hey " + eFName + ", \n\nFeedback has been added by " + eOrgName + " " +
                                                                                    "regarding your interview for " + eRoleTitle + "!\n\n'" + feedback + "'",
                                                                            "clodaghFYP@gmail.com", " " + aEmail);
                                                                } catch (Exception e) {
                                                                    Log.e("SendMail", e.getMessage(), e);
                                                                }
                                                            }

                                                        }).start();

                                                        editOutcome();

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

                                        Log.d("TAG****", applicationID);
                                        Log.d("TAG****", applicantID);
                                        Log.d("TAG****", organisationID);
                                        params.put("applicationID", applicationID);
                                        params.put("applicantID", applicantID);
                                        params.put("organisationID", organisationID);

                                        return params;

                                    }
                                };

                                RequestHandler.getInstance(JobInterviews.this).addToRequestQueue(stringRequest);
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
                params.put("feedback", feedback);
                params.put("interviewID", interviewID);
                return params;


            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void deleteInterview(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DELETE_INTERVIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            //'Applicant registered successfully' comes from the PHP code
                            if (message.equals("Interview deleted successfully")) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                interviewListModelList.clear();
                                loadInterviews();
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
                params.put("interviewID", interviewID);
                return params;


            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }


    public void editOutcome(){
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.outcome, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final Spinner spOutcome = (Spinner) promptsView
                .findViewById(R.id.spOutcome);

        //JobInterviewListAdapter.java
        //START
        String pending = "Pending";
        String successful = "Successful";
        String unsuccessful = "Unsuccessful";
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.outcome, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOutcome.setAdapter(adapter);
        if (dbOutcome.equals(pending)) {
            spOutcome.setSelection(0);
        } else if (dbOutcome.equals(successful)){
            spOutcome.setSelection(1);
        } else if (dbOutcome.equals(unsuccessful)){
            spOutcome.setSelection(2);
        }

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                outcome = spOutcome.getSelectedItem().toString().trim();

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SUBMIT_OUTCOME,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    String message = jsonObject.getString("message");
                                                    //'Applicant registered successfully' comes from the PHP code
                                                    if (message.equals("Outcome submitted successfully")) {
                                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                                        interviewListModelList.clear();
                                                        loadInterviews();
                                                        if (outcome.equals(dbOutcome)){

                                                        } else {
                                                            if (outcome.equals("Successful")) {
                                                                new Thread(new Runnable() {

                                                                    @Override
                                                                    public void run() {
                                                                        try {
                                                                            GMailSender sender = new GMailSender("clodaghFYP@gmail.com",
                                                                                    "116311691FYP");
                                                                            sender.sendMail("A decision has been made on your application",
                                                                                    "Hey " + eFName + ", \n\nA decision has been made by " + eOrgName + " " +
                                                                                            "regarding your application for " + eRoleTitle + "!\n\nWe are delighted to inform you that you have " +
                                                                                            "been successful in your application and they would like to offer you the position of " + eRoleTitle +
                                                                                            "! \n\nTo accept this offer please " +
                                                                                            "email " + oEmail + "\n\nCongratulations!",
                                                                                    "clodaghFYP@gmail.com", " " + aEmail);
                                                                        } catch (Exception e) {
                                                                            Log.e("SendMail", e.getMessage(), e);
                                                                        }
                                                                    }

                                                                }).start();
                                                            } else if (outcome.equals("Unsuccessful")) {
                                                                new Thread(new Runnable() {

                                                                    @Override
                                                                    public void run() {
                                                                        try {
                                                                            GMailSender sender = new GMailSender("clodaghFYP@gmail.com",
                                                                                    "116311691FYP");
                                                                            sender.sendMail("A decision has been made on your application",
                                                                                    "Hey " + eFName + ", \n\nA decision has been made by " + eOrgName + " regarding your application for " + eRoleTitle + "!\n\nWe regret to inform you that you have" +
                                                                                            " been unsuccessful this time." +
                                                                                            "\n\nTo apply to more roles please go to your app to view open job listings!",
                                                                                    "clodaghFYP@gmail.com", " " + aEmail);
                                                                        } catch (Exception e) {
                                                                            Log.e("SendMail", e.getMessage(), e);
                                                                        }
                                                                    }

                                                                }).start();

                                                            }
                                                        }


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
                                        params.put("interviewID", interviewID);
                                        params.put("outcome", outcome);
                                        return params;


                                    }
                                };

                                RequestHandler.getInstance(JobInterviews.this).addToRequestQueue(stringRequest);



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
    /*

    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.jobs);

    }

     */
}
