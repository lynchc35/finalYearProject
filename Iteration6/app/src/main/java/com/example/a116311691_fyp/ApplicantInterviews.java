package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ApplicantInterviews extends AppCompatActivity {


    //JobInterviews.java
    //START
    RecyclerView recyclerView;
    ApplicantInterviewListAdapter adapter;
    String interviewID;
    List<InterviewListModel> interviewListModelList;
    String jobListingID;
    String roleTitle;
    String time;
    String location;
    String additional;
    BottomNavigationView bottomNavigationView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_interviews);

        interviewListModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.interviews);

        //END

        loadInterviews();

        recyclerView.addOnItemTouchListener(new MyTouchListener(getApplicationContext(), recyclerView, new MyTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {
                //code as per your need
                //Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
                InterviewListModel interviewListModel = interviewListModelList.get(position);
                jobListingID = String.valueOf(interviewListModel.getJobListingID());
                Intent intent = new Intent(ApplicantInterviews.this, SingleJobListing.class);
                intent.putExtra("jobListingID", jobListingID);
                startActivity(intent);
            }

            @Override
            public void onRightSwipe(View view, int position) {

                //Toast.makeText(getApplicationContext(), interviewListModel.getRoleTitle() + " is selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onClick(View view, int position) {

                InterviewListModel interviewListModel = interviewListModelList.get(position);
                //interviewID = String.valueOf(interviewListModel.getInterviewID());
                roleTitle = String.valueOf(interviewListModel.getRoleTitle());
                time = String.valueOf(interviewListModel.getTime());
                //have to break down time to fit it in the calendar
                //JobInterviews.java
                //START
                time = interviewListModel.getTime();
                String[] seperated = time.split(" ");
                String date = seperated[0];
                String time = seperated[1];

                String[] dateSep = date.split("-");
                String yearSep = dateSep[0];
                int year = Integer.parseInt(yearSep);
                String monthSep = dateSep[1];
                int month = Integer.parseInt(monthSep);
                String daySep = dateSep[2];
                int day = Integer.parseInt(daySep);

                String[] timeSep = time.split(":");
                String hourSep = timeSep[0];
                int hour = Integer.parseInt(hourSep);
                String minuteSep = timeSep[1];
                int minute = Integer.parseInt(minuteSep);
                String secondSep = timeSep[2];
                int second = Integer.parseInt(secondSep);
                //END

                location = String.valueOf(interviewListModel.getLocation());
                additional = String.valueOf(interviewListModel.getAdditional());


                //https://www.vogella.com/tutorials/AndroidCalendar/article.html
                //START
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, "Interview for " + roleTitle);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location + " ");
                intent.putExtra(CalendarContract.Events.DESCRIPTION, additional + " ");
                GregorianCalendar calDate = new GregorianCalendar(year, month, day, hour, minute);
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,calDate.getTimeInMillis());
                startActivity(intent);
                //END

            }

            public void onLongClick(View view, int position){
                //code as per your need
                // Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_SHORT).show();
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

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent = new Intent(ApplicantInterviews.this, ApplicantProfile.class);
                        startActivity(intent);
                        return true;
                    case R.id.jobs:
                        Intent intent2 = new Intent(ApplicantInterviews.this, JobListings.class);
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

                                        bottomNavigationView.setSelectedItemId(R.id.interviews);
                                    } else{
                                        Intent intent = new Intent(ApplicantInterviews.this, applicantApplications.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ApplicantInterviews.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        return true;
                    case R.id.interviews:

                        return true;
                    case R.id.advice:
                        Intent intent5 = new Intent(ApplicantInterviews.this, Advice.class);
                        startActivity(intent5);
                        return true;
                }
                return false;
            }
        });

        //END

    }


    public void loadInterviews(){

        //ApplicantProfileCV.java
        //START
        final String applicantID;
        Applicant applicant = new Applicant();
        applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
        applicantID = String.valueOf(applicant.getApplicantID());
        //END

        //different StringRequest - calls a different PHP file, code adjusted according to what's required for that
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_APPLICANT_INTERVIEWS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONArray jobListings = new JSONArray(response);

                    for(int i = 0; i<jobListings.length(); i++){
                        JSONObject jobObject = jobListings.getJSONObject(i);
                        int interviewID = jobObject.getInt("interviewID");
                        String applicantName = (jobObject.getString("fName") + " " + jobObject.getString("lName"));
                        int applicantID = jobObject.getInt("applicantID");
                        int applicationID = jobObject.getInt("applicationID");
                        String roleTitle = jobObject.getString("roleTitle");
                        int jobListingID = jobObject.getInt("jobListingID");
                        String time = jobObject.getString("time");
                        String location = jobObject.getString("location");
                        String additional = jobObject.getString("additional");
                        String feedback = jobObject.getString("feedback");
                        String outcome = jobObject.getString("outcome");

                        InterviewListModel interview = new InterviewListModel(interviewID, applicantName, applicantID, roleTitle, time, location, additional, feedback, jobListingID, applicationID, outcome);
                        interviewListModelList.add(interview);
                    }
                    adapter = new ApplicantInterviewListAdapter(ApplicantInterviews.this, interviewListModelList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ApplicantInterviews.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    /*
    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.interviews);

    }

     */
}
