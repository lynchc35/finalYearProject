package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class OrgSingleJobListing extends AppCompatActivity {

    TextView etTitle, etRequirements, etRoleDuties, etSalary;
    String jobListingID;
    ImageButton btnEdit, btnSubmit, btnApplications, btnInterviews;
    Spinner spStatus,spLocation;
    final static String Tag = "***FYP***";
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_single_job_listing);

        //https://stackoverflow.com/questions/4149415/onscreen-keyboard-opens-automatically-when-activity-starts
        //START
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //END

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.jobs);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent3 = new Intent(OrgSingleJobListing.this, OrgProfile.class);
                        startActivity(intent3);
                        return true;
                    case R.id.jobs:
                        Intent intent = new Intent(OrgSingleJobListing.this, JobListingsPerOrg.class);
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
                                        Intent intent = new Intent(OrgSingleJobListing.this, ApplicationsPerOrg.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(OrgSingleJobListing.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        Intent intent = new Intent(OrgSingleJobListing.this, OrgInterviewSchedule.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(OrgSingleJobListing.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Intent intent5 = new Intent(OrgSingleJobListing.this, Queries.class);
                        startActivity(intent5);
                        return true;
                }
                return false;
            }
        });
        //END

        //SingleJobListing.java
        //START
        etTitle = findViewById(R.id.etRoleTitle);
        spLocation = findViewById(R.id.spLocation);
        etRequirements = findViewById(R.id.etRequirements);
        etRoleDuties = findViewById(R.id.etRoleDuties);
        etSalary = findViewById(R.id.etSalary);
        btnEdit = findViewById(R.id.btnEdit);
        spStatus = findViewById(R.id.spStatus);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnApplications = findViewById(R.id.btnViewApplications);
        btnInterviews = findViewById(R.id.btnViewInterviews);

        //RegisterApplicant.java
        //START
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.listing_status_values, R.layout.org_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(adapter);
        //END

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                R.array.location_values, R.layout.org_spinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(adapter2);

        Intent intent = getIntent();
        jobListingID = (intent.getStringExtra("jobListingID"));
        loadJob();

        /*
        etTitle.setText(job.getRoleTitle());
        etLocation.setText(job.getLocation());
        etRequirements.setText(job.getRoleRequirements());
        etRoleDuties.setText(job.getRoleDuties());
        etSalary.setText(job.getSalary());
        if (job.getListingStatus().equals("Open")) {
            spStatus.setSelection(0);
        } else if (job.getListingStatus().equals("Closed")) {
            spStatus.setSelection(1);
        } else {
            spStatus.setSelection(0);
        }

         */

        //jobListingID = String.valueOf(job.getJobListingID());

        etTitle.setEnabled(false);
        spLocation.setEnabled(false);
        etRoleDuties.setEnabled(false);
        etRequirements.setEnabled(false);
        etSalary.setEnabled(false);
        spStatus.setEnabled(false);
        btnSubmit.setVisibility(View.INVISIBLE);


        //END

        btnInterviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_JOB_INTERVIEW_LIST, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jobListings = new JSONArray(response);
                            Log.d(Tag, response);

                            if (jobListings.isNull(0)){
                                Toast.makeText(getApplicationContext(), "No interviews have been scheduled for this job!", Toast.LENGTH_LONG).show();

                            } else {
                                Intent intent1 = new Intent(OrgSingleJobListing.this, JobInterviews.class);
                                intent1.putExtra("jobListingID", jobListingID);
                                startActivity(intent1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrgSingleJobListing.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("jobListingID", jobListingID);

                        return params;

                    }
                };

                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


            }
        });

        btnApplications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //taken from OrgApplicationsPerJob.java loadApplications() method and altered
                //START
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_APP_PER_JOB, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jobListings = new JSONArray(response);
                            Log.d(Tag, response);

                            if (jobListings.isNull(0)){
                                Toast.makeText(getApplicationContext(), "No applications have been submitted to this job!", Toast.LENGTH_LONG).show();

                            } else {
                                        Intent intent1 = new Intent(OrgSingleJobListing.this, OrgApplicationsPerJob.class);
                                        intent1.putExtra("jobListingID", jobListingID);
                                        startActivity(intent1);
                                    }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrgSingleJobListing.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("jobListingID", jobListingID);

                        return params;

                    }
                };

                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


            }
        });



        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTitle.setEnabled(true);
                spLocation.setEnabled(true);
                etRoleDuties.setEnabled(true);
                etRequirements.setEnabled(true);
                etSalary.setEnabled(true);
                spStatus.setEnabled(true);
                btnSubmit.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.INVISIBLE);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateJob();
                btnEdit.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.INVISIBLE);
                etTitle.setEnabled(false);
                spLocation.setEnabled(false);
                etRoleDuties.setEnabled(false);
                etRequirements.setEnabled(false);
                etSalary.setEnabled(false);
                spStatus.setEnabled(false);

            }
        });
    }


    private void loadJob(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DISPLAY_JOB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                obj = jsonArray.getJSONObject(i);
                                String jobListingID = String.valueOf(obj.getInt("jobListingID"));
                                int organisationID = obj.getInt("organisationID");
                                String roleTitle = obj.getString("roleTitle");
                                String location = obj.getString("location");
                                String roleRequirements = obj.getString("roleRequirements");
                                String roleDuties = obj.getString("roleDuties");
                                String salary = obj.getString("salary");
                                String listingStatus = obj.getString("listingStatus");

                                etTitle.setText(roleTitle);
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
                                etRequirements.setText(roleRequirements);
                                etRoleDuties.setText(roleDuties);
                                etSalary.setText(salary);
                                if (listingStatus.equals("Open")) {
                                    spStatus.setSelection(0);
                                } else if (listingStatus.equals("Closed")) {
                                    spStatus.setSelection(1);
                                } else {
                                    spStatus.setSelection(0);
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

                params.put("jobListingID", jobListingID);

                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


    private void updateJob() {
        //ApplicantProfileCV.java updateCV()
        //START
        final String title = etTitle.getText().toString().trim();
        final String location = spLocation.getSelectedItem().toString().trim();
        final String requirements = etRequirements.getText().toString().trim();
        final String duties = etRoleDuties.getText().toString().trim();
        final String salary = etSalary.getText().toString().trim();
        final String status = spStatus.getSelectedItem().toString().trim();

        if (title.equals("") || location.equals("") || requirements.equals("") || duties.equals("") || salary.equals("")){
            Toast.makeText(getApplicationContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show();
        } else{
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_JOB,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(Tag,response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                if (message.equals("Job Listing updated successfully")) {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    //https://stackoverflow.com/questions/4149415/onscreen-keyboard-opens-automatically-when-activity-starts
                                    //START
                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
                            Log.d(Tag,error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("roleTitle", title);
                    params.put("location", location);
                    params.put("roleRequirements", requirements);
                    params.put("roleDuties", duties);
                    params.put("salary", salary);
                    params.put("listingStatus", status);
                    params.put("jobListingID", jobListingID);
                    return params;
                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
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
