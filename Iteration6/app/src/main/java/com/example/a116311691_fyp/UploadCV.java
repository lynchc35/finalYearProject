package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class UploadCV extends AppCompatActivity {

    EditText etPosition1, etOrg1, etNoYears1, etDuties1, etPosition2, etOrg2, etNoYears2, etDuties2, etInstitution1,
            etCertification1, etInstitution2, etCertification2, etSkills, etAwards, etPublications, etInterests, etAdditional;
    Button btnUpload;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_cv);

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        //END

        //https://stackoverflow.com/questions/6456588/how-to-get-a-red-asterisk-in-a-string-entry/35450876
        //START
        TextView tvPosition1 = findViewById(R.id.tvPosition1);
        String pos1 = "Position: ";
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(pos1);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPosition1.setText(builder);

        TextView tvOrg1 = findViewById(R.id.tvOrganisation1);
        String org1 = "Organisation: ";
        SpannableStringBuilder builderOrg = new SpannableStringBuilder();
        builderOrg.append(org1);
        int startOrg = builderOrg.length();
        builderOrg.append(colored);
        int endOrg = builderOrg.length();
        builderOrg.setSpan(new ForegroundColorSpan(Color.RED), startOrg, endOrg,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvOrg1.setText(builderOrg);

        TextView tvYears = findViewById(R.id.tvNoYears1);
        String years1 = "Number of Years: ";
        SpannableStringBuilder builderYears = new SpannableStringBuilder();
        builderYears.append(years1);
        int startYear = builderYears.length();
        builderYears.append(colored);
        int endYear = builderYears.length();
        builderYears.setSpan(new ForegroundColorSpan(Color.RED), startYear, endYear,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvYears.setText(builderYears);

        TextView tvDuties = findViewById(R.id.tvDuties);
        String duties = "Duties: ";
        SpannableStringBuilder builderDuties = new SpannableStringBuilder();
        builderDuties.append(duties);
        int startDuties = builderDuties.length();
        builderDuties.append(colored);
        int endDuties = builderDuties.length();
        builderDuties.setSpan(new ForegroundColorSpan(Color.RED), startDuties, endDuties,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvDuties.setText(builderDuties);

        TextView tvInst = findViewById(R.id.tvInstitution1);
        String inst = "Institution: ";
        SpannableStringBuilder builderInst = new SpannableStringBuilder();
        builderInst.append(inst);
        int startInst = builderInst.length();
        builderInst.append(colored);
        int endInst = builderInst.length();
        builderInst.setSpan(new ForegroundColorSpan(Color.RED), startInst, endInst,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvInst.setText(builderInst);

        TextView tvCert = findViewById(R.id.tvCertification);
        String cert = "Certification: ";
        SpannableStringBuilder builderCert = new SpannableStringBuilder();
        builderCert.append(cert);
        int startCert = builderCert.length();
        builderCert.append(colored);
        int endCert = builderCert.length();
        builderCert.setSpan(new ForegroundColorSpan(Color.RED), startCert, endCert,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvCert.setText(builderCert);
        //END



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
        btnUpload = findViewById(R.id.btnUpload);

        etPosition1.setFocusable(true);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadCV();
            }
        });

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent = new Intent(UploadCV.this, ApplicantProfile.class);
                        startActivity(intent);
                        return true;
                    case R.id.jobs:
                        Intent intent2 = new Intent(UploadCV.this, JobListings.class);
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

                                        //bottomNavigationView.setSelectedItemId(R.id.advice);
                                    } else{
                                        Intent intent = new Intent(UploadCV.this, applicantApplications.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(UploadCV.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        Intent intent = new Intent(UploadCV.this, ApplicantInterviews.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(UploadCV.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Intent intent5 = new Intent(UploadCV.this, Advice.class);
                        startActivity(intent5);
                        return true;
                }
                return false;
            }
        });

        //END

    }

    private void uploadCV() {
    //COPIED registerUser() from RegisterApplicant.java and altered accordingly
    // START
        final String workHistory1Position = etPosition1.getText().toString().trim();
        final String workHistory1Organisation = etOrg1.getText().toString().trim();
        final String workHistory1NumberOfYears = etNoYears1.getText().toString().trim();
        final String workHistory1Duties = etDuties1.getText().toString().trim();
        final String workHistory2Position = etPosition2.getText().toString().trim();
        final String workHistory2Organisation = etOrg2.getText().toString().trim();
        final String workHistory2NumberOfYears = etNoYears2.getText().toString().trim();
        final String workHistory2Duties = etDuties2.getText().toString().trim();
        final String education1Institution = etInstitution1.getText().toString().trim();
        final String education1Certification = etCertification1.getText().toString().trim();
        final String education2Institution = etInstitution2.getText().toString().trim();
        final String education2Certification = etCertification2.getText().toString().trim();
        final String skills = etSkills.getText().toString();
        final String awards = etAwards.getText().toString();
        final String publications = etPublications.getText().toString();
        final String interests = etInterests.getText().toString().trim();
        final String additional = etAdditional.getText().toString().trim();

        //taken from JobListingUpload.java and altered
        //START
        final String applicantID;
        Applicant applicant = new Applicant();
        applicant = SharedPrefManager.getInstance(this).getApplicantDetails();
        applicantID = String.valueOf(applicant.getApplicantID());
        //END


        if (workHistory1Position.equals("") || workHistory1Organisation.equals("") || workHistory1NumberOfYears.equals("") || workHistory1Duties.equals("")
                || education1Institution.equals("") || education1Certification.equals("") ) {
            Toast.makeText(getApplicationContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show();
        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPLOAD_CV,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                if (message.equals("CV uploaded successfully")) {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(UploadCV.this, ApplicantProfile.class);
                                    startActivity(intent);
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
                    params.put("workHistory1Position", workHistory1Position);
                    params.put("workHistory1Organisation", workHistory1Organisation);
                    params.put("workHistory1NumberOfYears", workHistory1NumberOfYears);
                    params.put("workHistory1Duties", workHistory1Duties);
                    params.put("workHistory2Position", workHistory2Position);
                    params.put("workHistory2Organisation", workHistory2Organisation);
                    params.put("workHistory2NumberOfYears", workHistory2NumberOfYears);
                    params.put("workHistory2Duties", workHistory2Duties);
                    params.put("education1Institution", education1Institution);
                    params.put("education1Certification", education1Certification);
                    params.put("education2Institution", education2Institution);
                    params.put("education2Certification", education2Certification);
                    params.put("skills", skills);
                    params.put("awards", awards);
                    params.put("publications", publications);
                    params.put("interests", interests);
                    params.put("additional", additional);
                    params.put("applicantID", applicantID);

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

        bottomNavigationView.setSelectedItemId(R.id.profile);

    }

     */
}