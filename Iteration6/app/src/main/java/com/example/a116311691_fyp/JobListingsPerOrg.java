package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobListingsPerOrg extends AppCompatActivity {

    //from JobListings.java
    //START
    RecyclerView recyclerView;
    OrgJobListingAdapter adapter;

    List<OrgJobListingsModel> jobListingsList;
    BottomNavigationView bottomNavigationView;

    SwipeRefreshLayout swipeRefreshLayout;

    FloatingActionButton floatingActionButton;
    final static String Tag = "****FYP*****";
    private static final String HOME_ACTIVITY_TAG = JobListingsPerOrg.class.getSimpleName();

    private void showLog(String text){

        Log.d(HOME_ACTIVITY_TAG, text);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listings_per_org);


        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.jobs);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobListingsPerOrg.this, JobListingUpload.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent3 = new Intent(JobListingsPerOrg.this, OrgProfile.class);
                        startActivity(intent3);
                        return true;
                    case R.id.jobs:
                        //Intent intent = new Intent(JobListingsPerOrg.this, JobListingsPerOrg.class);
                        //startActivity(intent);
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
                                        bottomNavigationView.setSelectedItemId(R.id.jobs);
                                    } else {
                                        Intent intent = new Intent(JobListingsPerOrg.this, ApplicationsPerOrg.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(JobListingsPerOrg.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        bottomNavigationView.setSelectedItemId(R.id.jobs);
                                    } else{
                                        Intent intent = new Intent(JobListingsPerOrg.this, OrgInterviewSchedule.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(JobListingsPerOrg.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Intent intent5 = new Intent(JobListingsPerOrg.this, Queries.class);
                        startActivity(intent5);
                        return true;
                }
                return false;
            }
        });
        //END


        jobListingsList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        loadJobs();
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
                OrgJobListingsModel jobListingsModel = jobListingsList.get(position);
                //Toast.makeText(getApplicationContext(), jobListingsModel.getRoleTitle() + " is selected", Toast.LENGTH_SHORT).show();
                //START
                //my own code
                String jobListingID = String.valueOf(jobListingsModel.getJobListingID());
                /*String roleTitle = jobListingsModel.getRoleTitle();
                int organisationID = jobListingsModel.getOrganisationID();
                String location = jobListingsModel.getLocation();
                String roleRequirements = jobListingsModel.getRoleRequirements();
                String roleDuties = jobListingsModel.getRoleDuties();
                String salary = jobListingsModel.getSalary();
                String listingStatus = jobListingsModel.getListingStatus();
                JobListingsModel job = new JobListingsModel(jobListingID,roleTitle,organisationID,location,roleRequirements,roleDuties,salary,listingStatus);
                 */
                Log.d(Tag, String.valueOf(jobListingID));
                Intent intent = new Intent(JobListingsPerOrg.this, OrgSingleJobListing.class);
                intent.putExtra("jobListingID", jobListingID);
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
                jobListingsList.clear();
                loadJobs();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //END

    }



    @Override

    protected void onRestart(){

        super.onRestart();//call to restart after onStop

        showLog("Activity restarted");
        //https://stackoverflow.com/questions/47397030/how-to-refresh-a-recyclerview-with-new-data-in-onresume
        //START
        //originally put in onResume() as per the link above however, realised it was meant for this method instead
        jobListingsList.clear();
        loadJobs();
        adapter.notifyDataSetChanged();
        //END
    }

    @Override

    protected void onStart() {

        super.onStart();//soon be visible

        showLog("Activity started");

    }


    @Override

    protected void onPause() {

        super.onPause();//invisible

        showLog("Activity paused");

    }

    @Override

    protected void onStop() {

        super.onStop();

        showLog("Activity stopped");

    }

    @Override

    protected void onDestroy() {

        super.onDestroy();

        showLog("Activity is being destroyed");

    }



    private void loadJobs() {

        //taken from JobListingUpload.java and altered
        //START
        final String organisationID;
        Organisation organisation = new Organisation();
        organisation = SharedPrefManager.getInstance(this).getOrgDetails();
        organisationID = String.valueOf(organisation.getOrganisationID());
        //END

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_JOB_PER_ORG_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jobListings = new JSONArray(response);
                    Log.d(Tag, response);

                    for(int i = 0; i<jobListings.length(); i++){
                        JSONObject jobObject = jobListings.getJSONObject(i);
                        int jobListingID = jobObject.getInt("jobListingID");
                        String roleTitle = jobObject.getString("roleTitle");
                        int organisationID = jobObject.getInt("organisationID");
                        String location = jobObject.getString("location");
                        String roleRequirements = jobObject.getString("roleRequirements");
                        String roleDuties = jobObject.getString("roleDuties");
                        String salary = jobObject.getString("salary");
                        String listingStatus = jobObject.getString("listingStatus");

                        OrgJobListingsModel job = new OrgJobListingsModel(jobListingID, roleTitle, organisationID,location, roleRequirements, roleDuties, salary, listingStatus);
                        jobListingsList.add(job);
                    }


                    adapter = new OrgJobListingAdapter(JobListingsPerOrg.this,jobListingsList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobListingsPerOrg.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.jobs);

    }
}
