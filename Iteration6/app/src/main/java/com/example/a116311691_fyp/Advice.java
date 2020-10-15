package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class Advice extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView cv, interview, linkedIn, reboot, springboard, opsyc, medicine, law, accountancy, cs, teaching, engineering, media;
    TextView cork, dublin, kerry, limerick, galway, accenture, skillnet, hse, teach, randox, hubspot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        cv = findViewById(R.id.CV);

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Advice.this, CVTips.class);
                startActivity(intent);
            }
        });

        interview = findViewById(R.id.Interview);
        interview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Advice.this, InterviewTips.class);
                startActivity(intent);
            }
        });

        /*
        linkedIn = findViewById(R.id.LinkedIn);
        linkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Advice.this, LinkedIn.class);
                startActivity(intent);
            }
        });

         */

        //https://stackoverflow.com/questions/9290651/make-a-hyperlink-textview-in-android
        //START
        reboot = findViewById(R.id.Reboot);
        reboot.setClickable(true);
        reboot.setMovementMethod(LinkMovementMethod.getInstance());
        String reText = "<a href='https://www.softwareskillnet.ie/women-reboot/'> Women ReBOOT- Returnships in ICT </a>";
        reboot.setText(Html.fromHtml(reText));

        accenture = findViewById(R.id.Accenture);
        accenture.setClickable(true);
        accenture.setMovementMethod(LinkMovementMethod.getInstance());
        String acctext = "<a href='https://www.accenture.com/ie-en/careers/resume-accenture-returners-programme'> Accenture ReSUME </a>";
        accenture.setText(Html.fromHtml(acctext));

        skillnet = findViewById(R.id.Skillnet);
        skillnet.setClickable(true);
        skillnet.setMovementMethod(LinkMovementMethod.getInstance());
        String skillText = "<a href='https://www.skillnetireland.ie/about/developing-your-skills/developing-irelands-future-workforce/'> Skillnet Ireland </a>";
        skillnet.setText(Html.fromHtml(skillText));


        hse = findViewById(R.id.HSE);
        hse.setClickable(true);
        hse.setMovementMethod(LinkMovementMethod.getInstance());
        String hseText = "<a href='https://healthservice.hse.ie/about-us/onmsd/careers-in-nursing-and-midwifery/return-to-nursing-and-midwifery.html'> Return to Nursing and Midwifery </a>";
        hse.setText(Html.fromHtml(hseText));

        teach = findViewById(R.id.Teaching);
        teach.setClickable(true);
        teach.setMovementMethod(LinkMovementMethod.getInstance());
        String teachText = "<a href='https://getintoteaching.education.gov.uk/explore-my-options/return-to-teaching'> Return to Teaching </a>";
        teach.setText(Html.fromHtml(teachText));


        randox = findViewById(R.id.Randox);
        randox.setClickable(true);
        randox.setMovementMethod(LinkMovementMethod.getInstance());
        String randoxText = "<a href='https://www.randox.com/randox-returners/'> Randox </a>";
        randox.setText(Html.fromHtml(randoxText));


        hubspot = findViewById(R.id.Hubspot);
        hubspot.setClickable(true);
        hubspot.setMovementMethod(LinkMovementMethod.getInstance());
        String hubText = "<a href='https://www.hubspot.com/returners-program'> Hubspot </a>";
        hubspot.setText(Html.fromHtml(hubText));


        springboard = findViewById(R.id.springboard);
        springboard.setClickable(true);
        springboard.setMovementMethod(LinkMovementMethod.getInstance());
        String sText = "<a href='https://springboardcourses.ie/search'> All Springboard Courses </a>";
        springboard.setText(Html.fromHtml(sText));


        cork = findViewById(R.id.cork);
        cork.setClickable(true);
        cork.setMovementMethod(LinkMovementMethod.getInstance());
        String sCork = "<a href='https://springboardcourses.ie/results?keywords=&providers%5B%5D=39'> Cork </a>";
        cork.setText(Html.fromHtml(sCork));

        dublin = findViewById(R.id.dublin);
        dublin.setClickable(true);
        dublin.setMovementMethod(LinkMovementMethod.getInstance());
        String sDub = "<a href='https://springboardcourses.ie/results?keywords=&providers%5B%5D=24'> Dublin </a>";
        dublin.setText(Html.fromHtml(sDub));

        kerry = findViewById(R.id.kerry);
        kerry.setClickable(true);
        kerry.setMovementMethod(LinkMovementMethod.getInstance());
        String sKerry = "<a href='https://springboardcourses.ie/results?keywords=&providers%5B%5D=5'> Kerry </a>";
        kerry.setText(Html.fromHtml(sKerry));

        limerick = findViewById(R.id.limerick);
        limerick.setClickable(true);
        limerick.setMovementMethod(LinkMovementMethod.getInstance());
        String sLim = "<a href='https://springboardcourses.ie/results?keywords=&providers%5B%5D=22'> Limerick </a>";
        limerick.setText(Html.fromHtml(sLim));

        galway = findViewById(R.id.galway);
        galway.setClickable(true);
        galway.setMovementMethod(LinkMovementMethod.getInstance());
        String sGal = "<a href='https://springboardcourses.ie/results?keywords=&providers%5B%5D=23'> Galway </a>";
        galway.setText(Html.fromHtml(sGal));


        opsyc = findViewById(R.id.opsyc);
        opsyc.setClickable(true);
        opsyc.setMovementMethod(LinkMovementMethod.getInstance());
        String psycText = "<a href='http://wrpn.womenreturners.com/fionas-story/'> Occupational Psychology </a>";
        opsyc.setText(Html.fromHtml(psycText));

        medicine = findViewById(R.id.medicine);
        medicine.setClickable(true);
        medicine.setMovementMethod(LinkMovementMethod.getInstance());
        String medicineText = "<a href='http://wrpn.womenreturners.com/rachel-r-story/'> Medicine </a>";
        medicine.setText(Html.fromHtml(medicineText));

        law = findViewById(R.id.law);
        law.setClickable(true);
        law.setMovementMethod(LinkMovementMethod.getInstance());
        String lawText = "<a href='http://wrpn.womenreturners.com/virginias-story/'> Law </a>";
        law.setText(Html.fromHtml(lawText));


        accountancy = findViewById(R.id.accountancy);
        accountancy.setClickable(true);
        accountancy.setMovementMethod(LinkMovementMethod.getInstance());
        String accountText = "<a href='http://wrpn.womenreturners.com/jackie-s-story/'> Accountancy </a>";
        accountancy.setText(Html.fromHtml(accountText));


        cs = findViewById(R.id.computerScience);
        cs.setClickable(true);
        cs.setMovementMethod(LinkMovementMethod.getInstance());
        String csText = "<a href='http://wrpn.womenreturners.com/abirs-story/'> Computer Science </a>";
        cs.setText(Html.fromHtml(csText));

        teaching = findViewById(R.id.teaching);
        teaching.setClickable(true);
        teaching.setMovementMethod(LinkMovementMethod.getInstance());
        String teachingText = "<a href='http://wrpn.womenreturners.com/lowri-story/'> Teaching </a>";
        teaching.setText(Html.fromHtml(teachingText));

        engineering = findViewById(R.id.engineering);
        engineering.setClickable(true);
        engineering.setMovementMethod(LinkMovementMethod.getInstance());
        String engText = "<a href='http://wrpn.womenreturners.com/sarah-b-story/'> Engineering </a>";
        engineering.setText(Html.fromHtml(engText));

        media = findViewById(R.id.media);
        media.setClickable(true);
        media.setMovementMethod(LinkMovementMethod.getInstance());
        String mediaText = "<a href='http://pro.womenreturners.com/success-stories/olgas-story/'> Media </a>";
        media.setText(Html.fromHtml(mediaText));

        //END

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.advice);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent3 = new Intent(Advice.this, ApplicantProfile.class);
                        startActivity(intent3);
                        return true;
                    case R.id.jobs:
                        Intent intent = new Intent(Advice.this, JobListings.class);
                        startActivity(intent);
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

                                        bottomNavigationView.setSelectedItemId(R.id.advice);
                                    } else{
                                        Intent intent = new Intent(Advice.this, applicantApplications.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Advice.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                        bottomNavigationView.setSelectedItemId(R.id.advice);
                                    } else{
                                        Intent intent = new Intent(Advice.this, ApplicantInterviews.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Advice.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        return true;
                }
                return false;
            }
        });
        //END

    }
    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Advice.this, ApplicantProfile.class);
        startActivity(intent);

    }
     */
}
