package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

public class applicantApplications extends AppCompatActivity {

    //OrgApplicationsPerJob.java
    //START
    List<ApplicantApplicationsModel> applicationsList;

    RecyclerView recyclerView;
    ApplicantApplicationsAdapter adapter;
    final String Tag = "******FYP******";
    String additionalCV;
    final Context context = this;
    BottomNavigationView bottomNavigationView;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_applications);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        applicationsList = new ArrayList<>();

        loadApplications();
        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.applications);

        //END

        //jobListingsPerOrg.java
        recyclerView.addOnItemTouchListener(new MyTouchListener(getApplicationContext(), recyclerView, new MyTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {

                editCL(position);

            }

            @Override
            public void onRightSwipe(View view, int position) {
                //code as per your need
                //Toast.makeText(getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onClick(View view, int position) {
                ApplicantApplicationsModel applicationsModel = applicationsList.get(position);
                //Toast.makeText(getApplicationContext(), applicationsModel.getRoleTitle() + " is selected", Toast.LENGTH_SHORT).show();
                String jobListingID = String.valueOf(applicationsModel.getJobListingID());
                Log.d(Tag, jobListingID);
                Intent intent = new Intent(applicantApplications.this, SingleJobListing.class);
                intent.putExtra("jobListingID", jobListingID);
                startActivity(intent);
                //END
            }

            public void onLongClick(View view, int position){
                //code as per your need
               // Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_SHORT).show();
            }

        }));
        //END

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent3 = new Intent(applicantApplications.this, ApplicantProfile.class);
                        startActivity(intent3);
                        return true;
                    case R.id.jobs:
                        Intent intent = new Intent(applicantApplications.this, JobListings.class);
                        startActivity(intent);
                        return true;
                    case R.id.applications:
                        return true;
                    case R.id.interviews:
                        final String applicantID;
                        Applicant applicant = new Applicant();
                        applicant = SharedPrefManager.getInstance(getApplicationContext()).getApplicantDetails();
                        applicantID = String.valueOf(applicant.getApplicantID());

                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_APPLICANT_INTERVIEWS, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray apps = new JSONArray(response);

                                    if (apps.isNull(0)) {
                                        Toast.makeText(getApplicationContext(), "You have no interviews scheduled!", Toast.LENGTH_LONG).show();
                                        bottomNavigationView.setSelectedItemId(R.id.applications);
                                    } else{
                                        Intent intent = new Intent(applicantApplications.this, ApplicantInterviews.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(applicantApplications.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Intent intent5 = new Intent(applicantApplications.this, Advice.class);
                        startActivity(intent5);
                        return true;
                }
                return false;
            }
        });

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

        applicationsList.clear();
        //ApplicantProfile.java
        //START
        final String applicantID;
        Applicant applicant = new Applicant();
        applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
        applicantID = String.valueOf(applicant.getApplicantID());
        //END

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_APP_PER_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jobListings = new JSONArray(response);

                    for(int i = 0; i<jobListings.length(); i++){
                        JSONObject jobObject = jobListings.getJSONObject(i);
                        int applicationID = jobObject.getInt("applicationID");
                        int jobListingID = jobObject.getInt("jobListingID");
                        int applicantID = jobObject.getInt("applicantID");
                        String orgName = jobObject.getString("orgName");
                        String roleTitle = jobObject.getString("roleTitle");
                        String status = jobObject.getString("listingStatus");
                        String additional = jobObject.getString("additional");

                        ApplicantApplicationsModel application = new ApplicantApplicationsModel(applicationID, jobListingID, applicantID, orgName, roleTitle, status, additional);
                        applicationsList.add(application);
                    }


                    adapter = new ApplicantApplicationsAdapter(applicantApplications.this,applicationsList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(applicantApplications.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void editCL(int position){


        Log.d(Tag, String.valueOf(position));
        ApplicantApplicationsModel applicationModel = applicationsList.get(position);
        final String applicationID = String.valueOf(applicationModel.getApplicationID());
        final String additional = String.valueOf(applicationModel.getAdditional());

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompt_application, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.etAdditional);

        userInput.setText(additional);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                additionalCV = userInput.getText().toString().trim();
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_EDIT_CL,
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
                                                    if (message.equals("Cover Letter updated successfully")) {
                                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                                        applicationsList.clear();
                                                        adapter.notifyDataSetChanged();
                                                        loadApplications();
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
                                        params.put("additionalCV", additionalCV);
                                        Log.d(Tag, additionalCV);
                                        Log.d(Tag, applicationID);
                                        params.put("applicationID", applicationID);
                                        return params;


                                    }
                                };

                                RequestHandler.getInstance(applicantApplications.this).addToRequestQueue(stringRequest);

                                /*
                                applicationsList.clear();
                                loadApplications();

                                 */
                            }
                        }
                        )
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
    }
    /*

    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.applications);

    }

     */

    //END
}
