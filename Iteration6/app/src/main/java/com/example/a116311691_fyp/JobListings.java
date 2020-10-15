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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.ErrorListener;

public class JobListings extends AppCompatActivity {


    Spinner spLocation, spIndustry, spBreakReason;
    Button btnSearch;
    BottomNavigationView bottomNavigationView;
    SwipeRefreshLayout swipeRefreshLayout;


    //START
    //https://www.youtube.com/watch?v=a4o9zFfyIM4
    RecyclerView recyclerView;
    JobListingAdapter jobAdapter;
    String jobListingID;
    String location;
    String industry;

    final String Tag = "*****FYP*****";

    List<JobListingsModel> jobListingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_listings);

        btnSearch = findViewById(R.id.btnSearch);

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.jobs);

        //END

        jobListingsList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        loadJobs();

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:

                        Intent intent = new Intent(JobListings.this, ApplicantProfile.class);
                        startActivity(intent);
                        return true;
                    case R.id.jobs:
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

                                        bottomNavigationView.setSelectedItemId(R.id.jobs);
                                    } else{
                                        Intent intent = new Intent(JobListings.this, applicantApplications.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(JobListings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        bottomNavigationView.setSelectedItemId(R.id.jobs);
                                    } else{
                                        Intent intent = new Intent(JobListings.this, ApplicantInterviews.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(JobListings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Intent intent5 = new Intent(JobListings.this, Advice.class);
                        startActivity(intent5);
                        return true;
                }
                return false;
            }
        });

        //END


        spBreakReason = findViewById(R.id.spBreakReason);
        spLocation = findViewById(R.id.spLocation);
        spIndustry = findViewById(R.id.spIndustry);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_industry, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIndustry.setAdapter(adapter);
        spIndustry.setSelection(0);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                R.array.filter_location, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(adapter2);
        spLocation.setSelection(0);

        //END

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = spLocation.getSelectedItem().toString();
                industry = spIndustry.getSelectedItem().toString();

                if (location.equals("(All)") && industry.equals("(All)")){
                    jobListingsList.clear();
                    loadJobs();
                    jobAdapter.notifyDataSetChanged();
                } else if (location.equals("(All)") && !industry.equals("(All)")){
                    jobListingsList.clear();
                    loadIndustry();
                    jobAdapter.notifyDataSetChanged();
                } else if (!location.equals("(All)") && industry.equals("(All)")){
                    jobListingsList.clear();
                    loadLocation();
                    jobAdapter.notifyDataSetChanged();
                } else if (!location.equals("(All)") && !industry.equals("(All)")){
                    jobListingsList.clear();
                    loadBoth();
                    jobAdapter.notifyDataSetChanged();
                }
            }
        });

        //START
        //Gotten from Documents -> MobileDev -> Tutorial4 -> MyRecyclerViewApp -> MainActivity.java
        recyclerView.addOnItemTouchListener(new MyTouchListener(getApplicationContext(), recyclerView, new MyTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {
                //code as per your need
               // Toast.makeText(getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightSwipe(View view, int position) {
                //code as per your need
               // Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick(View view, int position) {
                JobListingsModel jobListingsModel = jobListingsList.get(position);
                //Toast.makeText(getApplicationContext(), jobListingsModel.getRoleTitle() + " is selected", Toast.LENGTH_SHORT).show();
                //START
                //my own code

                jobListingID = String.valueOf(jobListingsModel.getJobListingID());
                /*
                String roleTitle = jobListingsModel.getRoleTitle();
                int organisationID = jobListingsModel.getOrganisationID();
                String location = jobListingsModel.getLocation();
                String roleRequirements = jobListingsModel.getRoleRequirements();
                String roleDuties = jobListingsModel.getRoleDuties();
                String salary = jobListingsModel.getSalary();
                String listingStatus = jobListingsModel.getListingStatus();
                JobListingsModel job = new JobListingsModel(jobListingID,roleTitle,organisationID,location,roleRequirements,roleDuties,salary,listingStatus);
                 */
                Intent intent = new Intent(JobListings.this, SingleJobListing.class);
                intent.putExtra("jobListingID", jobListingID);
                Log.d(Tag, jobListingID);
                startActivity(intent);
                //END
            }

            public void onLongClick(View view, int position){
                //code as per your need
               // Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_SHORT).show();
            }

        }));
        //END

        //https://www.youtube.com/watch?v=Ffa0Mtd21_M
        //START
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                jobAdapter.notifyDataSetChanged();
                jobListingsList.clear();
                loadJobs();
                swipeRefreshLayout.setRefreshing(false);
                spLocation.setSelection(0);
                spIndustry.setSelection(0);

            }
        });
        //END


    }

    private void loadLocation(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_FILTER_LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.i("tagconvertstr", "["+response+"]");
                    JSONArray jobListings = new JSONArray(response);

                    for(int i = 0; i<jobListings.length(); i++){JSONObject jobObject = jobListings.getJSONObject(i);
                        int jobListingID = jobObject.getInt("jobListingID");
                        String roleTitle = jobObject.getString("roleTitle");
                        int organisationID = jobObject.getInt("organisationID");
                        String location = jobObject.getString("location");
                        String roleRequirements = jobObject.getString("roleRequirements");
                        String roleDuties = jobObject.getString("roleDuties");
                        String salary = jobObject.getString("salary");
                        String listingStatus = jobObject.getString("listingStatus");
                        String orgName = jobObject.getString("orgName");

                        JobListingsModel job = new JobListingsModel(jobListingID, roleTitle, organisationID,location, roleRequirements, roleDuties, salary, listingStatus, orgName);
                        if(job.getListingStatus().equals("Open")){
                            jobListingsList.add(job);
                        }
                    }

                    jobAdapter = new JobListingAdapter(JobListings.this,jobListingsList);
                    recyclerView.setAdapter(jobAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobListings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("location", location);

                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void loadIndustry(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_FILTER_INDUSTRY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.i("tagconvertstr", "["+response+"]");
                    JSONArray jobListings = new JSONArray(response);

                    for(int i = 0; i<jobListings.length(); i++){JSONObject jobObject = jobListings.getJSONObject(i);
                        int jobListingID = jobObject.getInt("jobListingID");
                        String roleTitle = jobObject.getString("roleTitle");
                        int organisationID = jobObject.getInt("organisationID");
                        String location = jobObject.getString("location");
                        String roleRequirements = jobObject.getString("roleRequirements");
                        String roleDuties = jobObject.getString("roleDuties");
                        String salary = jobObject.getString("salary");
                        String listingStatus = jobObject.getString("listingStatus");
                        String orgName = jobObject.getString("orgName");

                        JobListingsModel job = new JobListingsModel(jobListingID, roleTitle, organisationID,location, roleRequirements, roleDuties, salary, listingStatus, orgName);
                        if(job.getListingStatus().equals("Open")){
                            jobListingsList.add(job);
                        }
                    }

                    jobAdapter = new JobListingAdapter(JobListings.this,jobListingsList);
                    recyclerView.setAdapter(jobAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobListings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("industry", industry);

                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void loadBoth(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_FILTER_BOTH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    Log.i("tagconvertstr", "["+response+"]");
                    JSONArray jobListings = new JSONArray(response);

                    for(int i = 0; i<jobListings.length(); i++){JSONObject jobObject = jobListings.getJSONObject(i);
                        int jobListingID = jobObject.getInt("jobListingID");
                        String roleTitle = jobObject.getString("roleTitle");
                        int organisationID = jobObject.getInt("organisationID");
                        String location = jobObject.getString("location");
                        String roleRequirements = jobObject.getString("roleRequirements");
                        String roleDuties = jobObject.getString("roleDuties");
                        String salary = jobObject.getString("salary");
                        String listingStatus = jobObject.getString("listingStatus");
                        String orgName = jobObject.getString("orgName");

                        JobListingsModel job = new JobListingsModel(jobListingID, roleTitle, organisationID,location, roleRequirements, roleDuties, salary, listingStatus, orgName);
                        if(job.getListingStatus().equals("Open")){
                            jobListingsList.add(job);
                        }
                    }

                    jobAdapter = new JobListingAdapter(JobListings.this,jobListingsList);
                    recyclerView.setAdapter(jobAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobListings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("location", location);
                params.put("industry", industry);

                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }



    //START
    //https://www.youtube.com/watch?v=Yw7Lx9wqyGs
    private void loadJobs(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL_JOB_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jobListings = new JSONArray(response);

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
                        String orgName = jobObject.getString("orgName");

                        JobListingsModel job = new JobListingsModel(jobListingID, roleTitle, organisationID,location, roleRequirements, roleDuties, salary, listingStatus, orgName);
                        if(job.getListingStatus().equals("Open")){
                            jobListingsList.add(job);
                        }
                    }


                    jobAdapter = new JobListingAdapter(JobListings.this,jobListingsList);
                    recyclerView.setAdapter(jobAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(JobListings.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    //END

    /*
    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.jobs);

    }

     */

}
