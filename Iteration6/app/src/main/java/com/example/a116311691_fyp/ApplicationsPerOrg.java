package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationsPerOrg extends AppCompatActivity {

    //applicantApplications.java
    //START
    List<ApplicationsPerOrgModel> applicationsList;
    RecyclerView recyclerView;
    ApplicationsPerOrgAdapter adapter;
    final String Tag = "******FYP******";

    BottomNavigationView bottomNavigationView;
    SwipeRefreshLayout swipeRefreshLayout;
    //END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications_per_org);

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.applications);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent3 = new Intent(ApplicationsPerOrg.this, OrgProfile.class);
                        startActivity(intent3);
                        return true;
                    case R.id.jobs:
                        Intent intent = new Intent(ApplicationsPerOrg.this, JobListingsPerOrg.class);
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
                                        bottomNavigationView.setSelectedItemId(R.id.applications);
                                    } else {
                                        Intent intent = new Intent(ApplicationsPerOrg.this, ApplicationsPerOrg.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ApplicationsPerOrg.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        bottomNavigationView.setSelectedItemId(R.id.applications);
                                    } else{
                                        Intent intent = new Intent(ApplicationsPerOrg.this, OrgInterviewSchedule.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ApplicationsPerOrg.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Intent intent5 = new Intent(ApplicationsPerOrg.this, Queries.class);
                        startActivity(intent5);
                        return true;
                }
                return false;
            }
        });
        //END

        //applicantApplications.java
        //START
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        applicationsList = new ArrayList<>();

        loadApplications();

        //OrgApplicationsPerJob.java
        //START
        recyclerView.addOnItemTouchListener(new MyTouchListener(getApplicationContext(), recyclerView, new MyTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {
                //code as per your need
                //Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightSwipe(View view, int position) {
                //code as per your need
                //Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(View view, int position) {
                ApplicationsPerOrgModel applicationModel = applicationsList.get(position);
               // Toast.makeText(getApplicationContext(), applicationModel.getApplicantName() + " is selected", Toast.LENGTH_SHORT).show();

                String applicantID = String.valueOf(applicationModel.getApplicantID());
                String jobListingID = String.valueOf(applicationModel.getJobListingID());
                String applicationID = String.valueOf(applicationModel.getApplicationID());
                Intent intent = new Intent(ApplicationsPerOrg.this, ApplicationCV.class);
                intent.putExtra("applicantID", applicantID);
                intent.putExtra("applicationID", applicationID);
                intent.putExtra("jobListingID", jobListingID);
                Log.d(Tag, applicantID);
                startActivity(intent);
                //END
            }

            public void onLongClick(View view, int position){
                //code as per your need
                //Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_SHORT).show();
            }

        }));
        //END

        //https://www.youtube.com/watch?v=Ffa0Mtd21_M
        //START
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                applicationsList.clear();
                loadApplications();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //END


    }
        private void loadApplications() {

            //taken from JobListingsPerOrg.java
            //START
            final String organisationID;
            Organisation organisation = new Organisation();
            organisation = SharedPrefManager.getInstance(this).getOrgDetails();
            organisationID = String.valueOf(organisation.getOrganisationID());
            //END



            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_APP_PER_ORG, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jobListings = new JSONArray(response);

                        for(int i = 0; i<jobListings.length(); i++){
                            JSONObject jobObject = jobListings.getJSONObject(i);
                            int applicationID = jobObject.getInt("applicationID");
                            int jobListingID = jobObject.getInt("jobListingID");
                            int applicantID = jobObject.getInt("applicantID");
                            String applicantName = jobObject.getString("fName") + " " + jobObject.getString("lName");
                            String roleTitle = jobObject.getString("roleTitle");
                            String additional = jobObject.getString("additional");
                            Log.d(Tag, roleTitle);

                            ApplicationsPerOrgModel application = new ApplicationsPerOrgModel(applicationID, jobListingID, applicantID, applicantName, roleTitle, additional);
                            applicationsList.add(application);
                        }


                        adapter = new ApplicationsPerOrgAdapter(ApplicationsPerOrg.this,applicationsList);
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ApplicationsPerOrg.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        //END

    /*
    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.applications);

    }

     */
    }
