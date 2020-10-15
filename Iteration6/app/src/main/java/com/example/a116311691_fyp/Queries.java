package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

//https://www.android-examples.com/pie-chart-graph-android-app-using-mpandroidchart/
//START
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Queries extends AppCompatActivity {

    PieChart pieChart;
    ArrayList<Entry> entries;
    ArrayList<String> PieEntryLabels;
    PieDataSet pieDataSet;
    PieData pieData;
    int under30;
    int under40;
    int under50;
    int over50;
    int other;
    int carer;
    int illness;
    int children;
    String organisationID;
    boolean flag, first, second, third;
    BarChart chart;
    ArrayList Reason;
    ArrayList label;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.reports);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent3 = new Intent(Queries.this, OrgProfile.class);
                        startActivity(intent3);
                        return true;
                    case R.id.jobs:
                        Intent intent = new Intent(Queries.this, JobListingsPerOrg.class);
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
                                        bottomNavigationView.setSelectedItemId(R.id.reports);
                                    } else {
                                        Intent intent = new Intent(Queries.this, ApplicationsPerOrg.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Queries.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        bottomNavigationView.setSelectedItemId(R.id.reports);
                                    } else{
                                        Intent intent = new Intent(Queries.this, OrgInterviewSchedule.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Queries.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        return true;
                }
                return false;
            }
        });
        //END


        under40 = 0;
        under30 = 0;
        under50 = 0;
        over50 = 0;
        illness = 0;
        children = 0;
        carer = 0;
        other = 0;

        flag = false;
        first = false;
        second = false;
        third = false;

        pieChart = (PieChart) findViewById(R.id.chart1);
        pieChart.setDescription(null);

        entries = new ArrayList<>();

        PieEntryLabels = new ArrayList<String>();

        //https://javapapers.com/android/android-chart-example-app-using-mpandroidchart/
        //START
        chart = findViewById(R.id.barchart);
        chart.setDescription(null);
        Reason = new ArrayList();
        label = new ArrayList();
        //END

        Organisation organisation = new Organisation();
        organisation = SharedPrefManager.getInstance(this).getOrgDetails();
        organisationID = String.valueOf(organisation.getOrganisationID());


        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, Constants.URL_UNDER_30,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("TAG", response);
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                obj = jsonArray.getJSONObject(i);
                                int count = obj.getInt("count");
                                under30 = under30 + count;
                                Log.d("under30DB", String.valueOf(under30));
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UNDER_40,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                try {
                                                    Log.d("TAG", response);
                                                    JSONArray jsonArray = new JSONArray(response);
                                                    JSONObject obj = null;
                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                        obj = jsonArray.getJSONObject(i);
                                                        int count = obj.getInt("count");
                                                        under40 = under40 + count;
                                                        Log.d("under40DB", String.valueOf(under40));
                                                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Constants.URL_UNDER_50,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {

                                                                        try {
                                                                            Log.d("TAG", response);
                                                                            JSONArray jsonArray = new JSONArray(response);
                                                                            JSONObject obj = null;
                                                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                                                obj = jsonArray.getJSONObject(i);
                                                                                int count = obj.getInt("count");
                                                                                under50 = under50 + count;
                                                                                Log.d("under50DB", String.valueOf(under50));
                                                                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_OVER_50,
                                                                                        new Response.Listener<String>() {
                                                                                            @Override
                                                                                            public void onResponse(String response) {

                                                                                                try {
                                                                                                    Log.d("TAG", response);
                                                                                                    JSONArray jsonArray = new JSONArray(response);
                                                                                                    JSONObject obj = null;
                                                                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                                                                        obj = jsonArray.getJSONObject(i);
                                                                                                        int count = obj.getInt("count");
                                                                                                        over50 = over50 + count;
                                                                                                        Log.d("over50DB", String.valueOf(over50));

                                                                                                        AddValuesToPIEENTRY();
                                                                                                        AddValuesToPieEntryLabels();

                                                                                                        pieDataSet = new PieDataSet(entries, "");

                                                                                                        pieData = new PieData(PieEntryLabels, pieDataSet);

                                                                                                        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                                                                                                        pieChart.setData(pieData);

                                                                                                        pieChart.animateY(3000);
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

                                                                                        Log.d("orgO50", organisationID);
                                                                                        params.put("organisationID", organisationID);

                                                                                        return params;

                                                                                    }
                                                                                };

                                                                                RequestHandler.getInstance(Queries.this).addToRequestQueue(stringRequest2);

                                                                                //END
                                                                                flag = true;
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

                                                                Log.d("org50", organisationID);
                                                                params.put("organisationID", organisationID);

                                                                return params;

                                                            }
                                                        };

                                                        RequestHandler.getInstance(Queries.this).addToRequestQueue(stringRequest1);

                                                        //END
                                                        third = true;

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

                                        Log.d("org40", organisationID);
                                        params.put("organisationID", organisationID);

                                        return params;

                                    }
                                };

                                RequestHandler.getInstance(Queries.this).addToRequestQueue(stringRequest);

                                second = true;
                                //END

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

                Log.d("org30", organisationID);
                params.put("organisationID", organisationID);

                return params;

            }
        };

        RequestHandler.getInstance(Queries.this).addToRequestQueue(stringRequest3);
        first = true;

        /*
        Log.d("runningVALUES", "30");
        get30();

        Log.d("runningVALUES", "40");
        if (first == true) {
            get40();
        }
        Log.d("runningVALUES", "50");
        if (second == true) {
            get50();
        }
        Log.d("runningVALUES", "O50");
        if (third == true){
            getO50();
        }

        if (flag == true){
            Log.d("runningPIEENTRY", "addvalues");
            AddValuesToPIEENTRY();
        }

*/

        StringRequest stringRequest4 = new StringRequest(Request.Method.POST, Constants.URL_OTHER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("TAG", response);
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                obj = jsonArray.getJSONObject(i);
                                int count = obj.getInt("count");
                                other = other + count;
                                Log.d("other", String.valueOf(other));
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_CHILDREN,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                try {
                                                    Log.d("TAG", response);
                                                    JSONArray jsonArray = new JSONArray(response);
                                                    JSONObject obj = null;
                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                        obj = jsonArray.getJSONObject(i);
                                                        int count = obj.getInt("count");
                                                        children = children + count;
                                                        Log.d("children", String.valueOf(children));
                                                        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Constants.URL_CARER,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {

                                                                        try {
                                                                            Log.d("TAG", response);
                                                                            JSONArray jsonArray = new JSONArray(response);
                                                                            JSONObject obj = null;
                                                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                                                obj = jsonArray.getJSONObject(i);
                                                                                int count = obj.getInt("count");
                                                                                carer = carer + count;
                                                                                Log.d("carer", String.valueOf(carer));
                                                                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_ILLNESS,
                                                                                        new Response.Listener<String>() {
                                                                                            @Override
                                                                                            public void onResponse(String response) {

                                                                                                try {
                                                                                                    Log.d("TAG", response);
                                                                                                    JSONArray jsonArray = new JSONArray(response);
                                                                                                    JSONObject obj = null;
                                                                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                                                                        obj = jsonArray.getJSONObject(i);
                                                                                                        int count = obj.getInt("count");
                                                                                                        illness = illness + count;

                                                                                                        //https://javapapers.com/android/android-chart-example-app-using-mpandroidchart/
                                                                                                        //STARTz
                                                                                                        Reason.add(new BarEntry(children, 0));
                                                                                                        Reason.add(new BarEntry(carer, 1));
                                                                                                        Reason.add(new BarEntry(illness, 2));
                                                                                                        Reason.add(new BarEntry(other, 3));

                                                                                                        label.add("Children");
                                                                                                        label.add("Carer");
                                                                                                        label.add("Illness");
                                                                                                        label.add("Other");
                                                                                                        BarDataSet bardataset = new BarDataSet(Reason, "Reason for Career Break");
                                                                                                        chart.animateY(5000);
                                                                                                        BarData data = new BarData(label, bardataset);
                                                                                                        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
                                                                                                        chart.setData(data);
                                                                                                        //END
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
                                                                                        params.put("organisationID", organisationID);

                                                                                        return params;

                                                                                    }
                                                                                };

                                                                                RequestHandler.getInstance(Queries.this).addToRequestQueue(stringRequest2);
                                                                                //END
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

                                                                params.put("organisationID", organisationID);

                                                                return params;

                                                            }
                                                        };

                                                        RequestHandler.getInstance(Queries.this).addToRequestQueue(stringRequest1);
                                                        //END

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

                                        params.put("organisationID", organisationID);

                                        return params;
                                    }
                                };

                                RequestHandler.getInstance(Queries.this).addToRequestQueue(stringRequest);
                                //END

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

                params.put("organisationID", organisationID);

                return params;

            }
        };

        RequestHandler.getInstance(Queries.this).addToRequestQueue(stringRequest4);


    }

    public void AddValuesToPIEENTRY() {

        Log.d("under30PIE", String.valueOf(under30));
        Log.d("under40PIE", String.valueOf(under40));
        Log.d("under50PIE", String.valueOf(under50));
        Log.d("over50PIE", String.valueOf(over50));

        if (under30 > 0){
            entries.add(new BarEntry(under30, 0));
        } else
        {}

        if (under40 > 0){
            entries.add(new BarEntry(under40, 1));
        } else
        {}

        if (under50 > 0){
            entries.add(new BarEntry(under50, 2));
        } else
        {}

        if (over50 > 0){
            entries.add(new BarEntry(over50, 3));
        } else
        {}

        /*
        entries.add(new BarEntry(under30, 0));
        entries.add(new BarEntry(under40, 1));
        entries.add(new BarEntry(under50, 2));
        entries.add(new BarEntry(over50, 3));

         */


    }

    public void AddValuesToPieEntryLabels() {

        if (under30 > 0){
            PieEntryLabels.add("Under 30");
        } else
        {}

        if (under40 > 0){
            PieEntryLabels.add("Under 40");
        } else
        {}

        if (under50 > 0){
            PieEntryLabels.add("Under 50");
        } else
        {}

        if (over50 > 0){
            PieEntryLabels.add("Over 50");
        } else
        {}

        /*
        PieEntryLabels.add("Under 30");
        PieEntryLabels.add("Under 40");
        PieEntryLabels.add("Under 50");
        PieEntryLabels.add("Over 50");

         */

    }

    /*

    public void get40() {


    }

    public void get30() {

    }
        //END


    public void get50() {


    }

    public void getO50(){



    }
    //END

     */

    /*
    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.reports);

    }

     */
}

