package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SingleInterview extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    String interviewId;
    TextView tvApplicantName, tvRoleTitle;
    EditText etTime, etLocation, etAdditional, etFeedback, etDate;
    ImageView btnApplication;
    Spinner spOutcome;
    ImageButton btnEdit, btnSubmit, btnDelete;
    String roleTitle, dbOutcome, dbLocation, dbTime, dbAdditional, dbFeedback;
    ImageView calendar, clock;
    int year, month, day, yearFinal, monthFinal, dayFinal, hourFinal, minuteFinal, hour, minute;
    String hourSep, minuteSep;
    String newLocation, newAdditional, newFeedback, newTime, newOutcome, newDate;

    String eFName;
    String aEmail;
    String eOrgName;
    String eRoleTitle;
    String oEmail;
    String applicantID;
    String applicationID;

    String emailRole, emailOutcome, emailLocation, emailTime, emailAdditional, emailFeedback;

    Integer emailYear, emailMonth, emailDay, emailHour, emailMin;
    String emailHourSep, emailMinSep;
    String timeDisplayed;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_interview);

        //https://stackoverflow.com/questions/4149415/onscreen-keyboard-opens-automatically-when-activity-starts
        //START
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //END

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.interviews);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent3 = new Intent(SingleInterview.this, OrgProfile.class);
                        startActivity(intent3);
                        return true;
                    case R.id.jobs:
                        Intent intent = new Intent(SingleInterview.this, JobListingsPerOrg.class);
                        startActivity(intent);
                        return true;
                    case R.id.applications:
                        Intent intent4 = new Intent(SingleInterview.this, ApplicationsPerOrg.class);
                        startActivity(intent4);
                        return true;
                    case R.id.interviews:
                        Intent intent2 = new Intent(SingleInterview.this, OrgInterviewSchedule.class);
                        startActivity(intent2);
                        return true;
                    case R.id.reports:
                        Intent intent5 = new Intent(SingleInterview.this, Queries.class);
                        startActivity(intent5);
                        return true;
                }
                return false;
            }
        });
        //END


        tvApplicantName = findViewById(R.id.tvApplicantName);
        tvRoleTitle = findViewById(R.id.tvRoleTitle);
        etTime = findViewById(R.id.etTime);
        etLocation = findViewById(R.id.etLocation);
        etAdditional = findViewById(R.id.etAdditional);
        etFeedback = findViewById(R.id.etFeedback);
        spOutcome = findViewById(R.id.spOutcome);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.outcome, R.layout.org_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOutcome.setAdapter(adapter);
        calendar = findViewById(R.id.calendar);
        clock = findViewById(R.id.clock);
        etDate = findViewById(R.id.etDate);
        btnApplication = findViewById(R.id.application);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        calendar.setVisibility(View.INVISIBLE);
        btnSubmit.setVisibility(View.INVISIBLE);
        clock.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        interviewId = intent.getStringExtra("ID");
        applicantID = intent.getStringExtra("applicantID");
        applicationID = intent.getStringExtra("applicationID");

        loadInterview();

        btnApplication.setClickable(true);
        btnApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SingleInterview.this, ApplicationCV.class);
                intent1.putExtra("applicantID", applicantID);
                startActivity(intent1);
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //etTime.setEnabled(true);
                etDate.setFocusable(true);
                etLocation.setEnabled(true);
                etAdditional.setEnabled(true);
                etFeedback.setEnabled(true);
                spOutcome.setEnabled(true);
                /*
                ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                        R.array.outcome, R.layout.org_spinner);
                adapter.setDropDownViewResource(R.layout.org_spinner_dropdown);

                 */

                btnEdit.setVisibility(View.INVISIBLE);
                if (dbAdditional.equals("")){
                    etAdditional.setText("");
                }
                if (dbFeedback.equals("")){
                    etFeedback.setText("");
                }
                if (dbAdditional.equals("")){
                    etAdditional.setText("");
                }
                btnSubmit.setVisibility(View.VISIBLE);
                calendar.setVisibility(View.VISIBLE);
                clock.setVisibility(View.VISIBLE);

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String organisationID;
                Organisation organisation = new Organisation();
                organisation = SharedPrefManager.getInstance(getApplicationContext()).getOrgDetails();
                organisationID = String.valueOf(organisation.getOrganisationID());

                //etTime.setEnabled(false);
                etLocation.setEnabled(false);
                spOutcome.setEnabled(false);

                etAdditional.setEnabled(false);
                etFeedback.setEnabled(false);
                btnEdit.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.INVISIBLE);
                clock.setVisibility(View.INVISIBLE);
                calendar.setVisibility(View.INVISIBLE);
                updateInterview();

                Log.d("DB", dbTime);
                Log.d("DB", dbAdditional);
                Log.d("DB", dbLocation);
                Log.d("DB", dbFeedback);
                Log.d("DB", dbOutcome);

                loadInterview();

                Log.d("DB", dbTime);
                Log.d("DB", dbAdditional);
                Log.d("DB", dbLocation);
                Log.d("DB", dbFeedback);
                Log.d("DB", dbOutcome);


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        calendar.setClickable(true);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               int month2Show = month -1;

                DatePickerDialog datePickerDialog = new DatePickerDialog(SingleInterview.this, SingleInterview.this, year, month2Show, day);
                datePickerDialog.show();



            }
        });

        clock.setClickable(true);
        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //variables from onClick() method - must convert them to integer before can pass into the TimePickerDialog call below
                 hour = Integer.parseInt(hourSep);
                 minute = Integer.parseInt(minuteSep);

                Calendar c = Calendar.getInstance();
                // hour = c.get(Calendar.HOUR_OF_DAY);
                //minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(SingleInterview.this, SingleInterview.this, hour, minute, DateFormat.is24HourFormat(getApplicationContext()));
                timePickerDialog.show();


            }
        });
    }

    private void loadInterview(){
        //etTime.setEnabled(false);
        etLocation.setEnabled(false);
        etAdditional.setEnabled(false);
        etFeedback.setEnabled(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_SINGLE_INTERVIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.i("tagconvertstr", "["+response+"]");
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject obj = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                obj = jsonArray.getJSONObject(i);
                                roleTitle = obj.getString("roleTitle");
                                dbOutcome = obj.getString("outcome");
                                dbLocation = obj.getString("location");
                                dbTime = obj.getString("time");
                                dbAdditional = obj.getString("additional");
                                dbFeedback = obj.getString("feedback");
                                String applicantName = obj.getString("fName") + " " + obj.getString("lName");
                                Log.d("DB***", roleTitle);
                                tvRoleTitle.setText(roleTitle);
                                etLocation.setText(dbLocation);
                                //etTime.setText(time);
                                etFeedback.setText(dbFeedback);
                                tvApplicantName.setText(applicantName);

                                //old JobInterviews.java code
                                //START
                                String[] seperated = dbTime.split(" ");
                                String date = seperated[0];
                                etDate.setText(date);
                                String timeHour = seperated[1];

                                String[] dateSep = date.split("-");
                                String yearSep = dateSep[0];
                                 year = Integer.parseInt(yearSep);
                                String monthSep = dateSep[1];
                                month = Integer.parseInt(monthSep);
                                //because January is stored as 00 therefore january dates were showing as February on the calendar
                                //month = month-1;
                                String daySep = dateSep[2];
                                day = Integer.parseInt(daySep);
                                String[] timeSep = timeHour.split(":");
                                hourSep = timeSep[0];
                                hour = Integer.parseInt(hourSep);
                                minuteSep = timeSep[1];
                                minute = Integer.parseInt(minuteSep);
                                String timeDisplayed = hourSep + ":" + minuteSep;
                                etTime.setText(timeDisplayed);

                                String secondSep = timeSep[2];
                                //END

                                //JobInterviewListAdapter.java
                                //START
                                String pending = "Pending";
                                String successful = "Successful";
                                String unsuccessful = "Unsuccessful";

                                /*
                                ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                                        R.array.outcome, R.layout.org_spinner);
                                adapter.setDropDownViewResource(R.layout.org_spinner_dropdown);


                                spOutcome.setAdapter(adapter);
                                */


                                if (dbOutcome.equals(pending)) {
                                    spOutcome.setSelection(0);
                                } else if (dbOutcome.equals(successful)){
                                    spOutcome.setSelection(1);
                                } else if (dbOutcome.equals(unsuccessful)){
                                    spOutcome.setSelection(2);
                                }

                                spOutcome.setEnabled(false);

                                if (dbAdditional.equals("")){
                                    etAdditional.setText("n/a");
                                } else {
                                    etAdditional.setText(dbAdditional);
                                }

                                if (dbFeedback.equals("")){
                                    etFeedback.setText("n/a");
                                } else {
                                    etFeedback.setText(dbFeedback);
                                }

                                //END
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

                params.put("interviewID", interviewId);

                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void updateInterview(){

        final String organisationID;
        Organisation organisation = new Organisation();
        organisation = SharedPrefManager.getInstance(getApplicationContext()).getOrgDetails();
        organisationID = String.valueOf(organisation.getOrganisationID());

        newFeedback = etFeedback.getText().toString();
        newLocation = etLocation.getText().toString();
        newAdditional = etAdditional.getText().toString();
        newOutcome = spOutcome.getSelectedItem().toString();
       // newOutcome =

        //if date & time pickers not selected goes to this
        if ((dayFinal == 1) || (dayFinal == 3) || (dayFinal == 4) || (dayFinal == 5) || (dayFinal == 6) || (dayFinal == 7) || (dayFinal == 8) || (dayFinal == 9)){
            newTime = yearFinal + "-0" + monthFinal + "-0" + dayFinal + " " + hourFinal + ":" + minuteFinal + ":00";
        } else{
            newTime = yearFinal + "-0" + monthFinal + "-" + dayFinal + " " + hourFinal + ":" + minuteFinal + ":00";
        }

        Log.d("TIME1", newTime);
        System.out.println(newTime);
        if (newTime.equals("0-00-0 0:0:00")){
            newTime = dbTime;
            yearFinal = year;
            System.out.println(monthFinal);
            monthFinal = month;
            dayFinal = day;
            hourFinal = hour;
            minuteFinal = minute;
            if ((dayFinal == 1) || (dayFinal == 3) || (dayFinal == 4) || (dayFinal == 5) || (dayFinal == 6) || (dayFinal == 7) || (dayFinal == 8) || (dayFinal == 9)) {
                newTime = yearFinal + "-0" + monthFinal + "-0" + dayFinal + " " + hourFinal +":" + minuteFinal + ":00";
            } else{
                newTime = yearFinal + "-0" + monthFinal + "-" + dayFinal + " " + hourFinal +":" + minuteFinal + ":00";
            }
            Log.d("TIME2", newTime);
        } else if (newTime.equals("0-00-0 " + hourFinal +":" + minuteFinal + ":00" )){
            yearFinal = year;
            System.out.println(monthFinal);
            monthFinal = month;
            dayFinal = day;
            if ((dayFinal == 1) || (dayFinal == 3) || (dayFinal == 4) || (dayFinal == 5) || (dayFinal == 6) || (dayFinal == 7) || (dayFinal == 8) || (dayFinal == 9)) {
                newTime = yearFinal + "-0" + monthFinal + "-0" + dayFinal + " " + hourFinal +":" + minuteFinal + ":00";
            } else{
                newTime = yearFinal + "-0" + monthFinal + "-" + dayFinal + " " + hourFinal +":" + minuteFinal + ":00";
            }
            Log.d("TIME3", newTime);
        } else if (newTime.equals(yearFinal + "-0" + monthFinal + "-" + dayFinal+ " 0:0:00")) {
            hourFinal = hour;
            System.out.println(monthFinal);
            minuteFinal = minute;
           // monthFinal = monthFinal + 1;
            if ((dayFinal == 1) || (dayFinal == 3) || (dayFinal == 4) || (dayFinal == 5) || (dayFinal == 6) || (dayFinal == 7) || (dayFinal == 8) || (dayFinal == 9)) {
                newTime = yearFinal + "-0" + monthFinal + "-0" + dayFinal + " " + hourFinal +":" + minuteFinal + ":00";
            } else{
                newTime = yearFinal + "-0" + monthFinal + "-" + dayFinal + " " + hourFinal +":" + minuteFinal + ":00";
            }
            Log.d("TIME4", newTime);
        } else {
            System.out.println(monthFinal);
            //monthFinal = monthFinal + 1;
            if ((dayFinal == 1) || (dayFinal == 3) || (dayFinal == 4) || (dayFinal == 5) || (dayFinal == 6) || (dayFinal == 7) || (dayFinal == 8) || (dayFinal == 9)) {
                newTime = yearFinal + "-0" + monthFinal + "-0" + dayFinal + " " + hourFinal +":" + minuteFinal + ":00";
            } else{
                newTime = yearFinal + "-0" + monthFinal + "-" + dayFinal + " " + hourFinal +":" + minuteFinal + ":00";
            }
            Log.d("TIME5", newTime);

            }



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_INTERVIEW,
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
                            if (message.equals("Interview updated successfully")) {
                                //Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                                etLocation.setText(newLocation);
                                etAdditional.setText(newAdditional);
                                String[] seperate = newTime.split(" ");
                                String dateN = seperate[0];
                                etDate.setText(dateN);
                                String timeHourN = seperate[1];

                                //from above^^
                                //START
                                String[] dateSepN = dateN.split("-");
                                String yearSepN = dateSepN[0];
                                int yearN = Integer.parseInt(yearSepN);
                                String monthSepN = dateSepN[1];
                                int monthN = Integer.parseInt(monthSepN);
                                //because January is stored as 00 therefore january dates were showing as February on the calendar
                                //monthN = monthN-1;
                                String daySepN = dateSepN[2];
                                int dayN = Integer.parseInt(daySepN);
                                String[] timeSepN = timeHourN.split(":");
                                String hourSepN = timeSepN[0];
                                String minuteSepN = timeSepN[1];
                                String timeDisplayedN = hourSepN + ":" + minuteSepN;
                                etTime.setText(timeDisplayedN);
                                //END

                                //JobInterviewListAdapter.java
                                //START
                                String pending = "Pending";
                                String successful = "Successful";
                                String unsuccessful = "Unsuccessful";
                                ArrayAdapter adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                                        R.array.outcome, R.layout.org_spinner);
                                adapter.setDropDownViewResource(R.layout.org_spinner_dropdown);
                                spOutcome.setAdapter(adapter);
                                if (newOutcome.equals(pending)) {
                                    spOutcome.setSelection(0);
                                } else if (newOutcome.equals(successful)){
                                    spOutcome.setSelection(1);
                                } else if (newOutcome.equals(unsuccessful)){
                                    spOutcome.setSelection(2);
                                }


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

                                                        etFeedback.setText(newFeedback);
                                                        //EMAILS
                                                        //if only time, date, location & additional

                                                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_SINGLE_INTERVIEW,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {

                                                                        try {
                                                                            Log.i("tagconvertstr", "["+response+"]");
                                                                            JSONArray jsonArray = new JSONArray(response);
                                                                            JSONObject obj = null;
                                                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                                                obj = jsonArray.getJSONObject(i);
                                                                                emailRole = obj.getString("roleTitle");
                                                                                emailOutcome = obj.getString("outcome");
                                                                                emailLocation = obj.getString("location");
                                                                                emailTime = obj.getString("time");
                                                                                emailAdditional = obj.getString("additional");
                                                                                emailFeedback = obj.getString("feedback");
                                                                                String applicantName = obj.getString("fName") + " " + obj.getString("lName");
                                                                                //Log.d("DB***", roleTitle);

                                                                                //old JobInterviews.java code
                                                                                //START
                                                                                String[] seperated = emailTime.split(" ");
                                                                                String date = seperated[0];
                                                                                String timeHour = seperated[1];

                                                                                String[] dateSep = date.split("-");
                                                                                String yearSep = dateSep[0];
                                                                                emailYear = Integer.parseInt(yearSep);
                                                                                String monthSep = dateSep[1];
                                                                                emailMonth = Integer.parseInt(monthSep);
                                                                                //because January is stored as 00 therefore january dates were showing as February on the calendar
                                                                                //month = month-1;
                                                                                String daySep = dateSep[2];
                                                                                emailDay = Integer.parseInt(daySep);
                                                                                String[] timeSep = timeHour.split(":");
                                                                                emailHourSep = timeSep[0];
                                                                                emailHour = Integer.parseInt(hourSep);
                                                                                emailMinSep = timeSep[1];
                                                                                emailMin = Integer.parseInt(minuteSep);
                                                                                String timeDisplayed = hourSep + ":" + minuteSep;

                                                                                Log.d("PRINT", emailTime);
                                                                                Log.d("PRINT", newTime);
                                                                                Log.d("PRINT", emailAdditional);
                                                                                Log.d("PRINT", newAdditional);
                                                                                Log.d("PRINT", emailLocation);
                                                                                Log.d("PRINT", newLocation);
                                                                                Log.d("PRINT", emailFeedback);
                                                                                Log.d("PRINT", newFeedback);
                                                                                Log.d("PRINT", emailOutcome);
                                                                                Log.d("PRINT", newOutcome);

                                                                                if ((!newTime.equals(dbTime) || !newAdditional.equals(dbAdditional) || !newLocation.equals(dbLocation))){

                                                                                    //ApplicationCV.java
                                                                                    //START
                                                                                    StringRequest stringRequest5 = new StringRequest(Request.Method.POST, Constants.URL_INTERVIEW_EMAIL,
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

                                                                                                            Log.d("PRINT", dbAdditional);
                                                                                                            Log.d("PRINT", newAdditional);
                                                                                                            Log.d("PRINT", newLocation);
                                                                                                            Log.d("PRINT", dbLocation);
                                                                                                            Log.d("PRINT", newTime);
                                                                                                            Log.d("PRINT", dbTime);
                                                                                                            if (newAdditional.equals(dbAdditional) && newLocation.equals(dbLocation) && newTime.equals(dbTime)) {

                                                                                                            } else {
                                                                                                                new Thread(new Runnable() {
                                                                                                                    @Override
                                                                                                                    public void run() {
                                                                                                                        try {
                                                                                                                            GMailSender sender = new GMailSender("noreply.rerecruit@gmail.com",
                                                                                                                                    "116311691FYP");
                                                                                                                            sender.sendMail("Interview for " + eRoleTitle + " changed",
                                                                                                                                    "Hey " + eFName + ", \n\nThe details of your interview for the position of " + eRoleTitle + " have changed! \nPlease read the new " +
                                                                                                                                            "details below:\n\nDate: " + dayFinal + "-" + monthFinal + "-" +
                                                                                                                                            yearFinal + "\nTime: " + hourFinal + ":" + minuteFinal + "\nLocation: " + newLocation + "\n" +
                                                                                                                                            newAdditional + "\n\n" + "Any queries please contact " + oEmail + " directly.",
                                                                                                                                    "noreply.rerecruit@gmail.com", " " + aEmail);
                                                                                                                        } catch (Exception e) {
                                                                                                                            Log.e("SendMail", e.getMessage(), e);
                                                                                                                        }
                                                                                                                    }

                                                                                                                }).start();
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
                                                                                            params.put("applicationID", applicationID);
                                                                                            params.put("applicantID", applicantID);
                                                                                            params.put("organisationID", organisationID);
                                                                                            return params;
                                                                                        }
                                                                                    };
                                                                                    RequestHandler.getInstance(SingleInterview.this).addToRequestQueue(stringRequest5);
                                                                                    //END

                                                                                    //if only feedback
                                                                                }
                                                                                if (!newFeedback.equals(dbFeedback)){
                                                                                    //ApplicationCV.java
                                                                                    StringRequest stringRequest4 = new StringRequest(Request.Method.POST, Constants.URL_INTERVIEW_EMAIL,
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

                                                                                                            if (newFeedback.equals(dbFeedback)){

                                                                                                            } else if (newFeedback.equals("")) {

                                                                                                            } else {
                                                                                                                new Thread(new Runnable() {

                                                                                                                    @Override
                                                                                                                    public void run() {
                                                                                                                        try {
                                                                                                                            GMailSender sender = new GMailSender("noreply.rerecruit@gmail.com",
                                                                                                                                    "116311691FYP");
                                                                                                                            sender.sendMail("Feedback added",
                                                                                                                                    "Hey " + eFName + ", \n\nFeedback has been added by " + eOrgName + " " +
                                                                                                                                            "regarding your interview for " + eRoleTitle + "!\n\n'" + newFeedback + "'",
                                                                                                                                    "noreply.rerecruit@gmail.com", " " + aEmail);
                                                                                                                        } catch (Exception e) {
                                                                                                                            Log.e("SendMail", e.getMessage(), e);
                                                                                                                        }
                                                                                                                    }

                                                                                                                }).start();
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

                                                                                            params.put("applicationID", applicationID);
                                                                                            params.put("applicantID", applicantID);
                                                                                            params.put("organisationID", organisationID);

                                                                                            return params;

                                                                                        }
                                                                                    };

                                                                                    RequestHandler.getInstance(SingleInterview.this).addToRequestQueue(stringRequest4);
                                                                                    //if only outcome
                                                                                }

                                                                                if (!newOutcome.equals(dbOutcome)) {
                                                                                    //ApplicationCV.java
                                                                                    StringRequest stringRequest3 = new StringRequest(Request.Method.POST, Constants.URL_INTERVIEW_EMAIL,
                                                                                            new Response.Listener<String>() {
                                                                                                @Override
                                                                                                public void onResponse(String response) {
                                                                                                    try {
                                                                                                        Log.i("tagconvertstr", "[" + response + "]");
                                                                                                        JSONArray jsonArray = new JSONArray(response);
                                                                                                        JSONObject obj = null;
                                                                                                        for (int i = 0; i < jsonArray.length(); i++) {
                                                                                                            obj = jsonArray.getJSONObject(i);
                                                                                                            eFName = obj.getString("fName");
                                                                                                            aEmail = obj.getString("aEmail");
                                                                                                            eOrgName = obj.getString("orgName");
                                                                                                            eRoleTitle = obj.getString("roleTitle");
                                                                                                            oEmail = obj.getString("oEmail");

                                                                                                            if (newOutcome.equals("Successful")) {
                                                                                                                new Thread(new Runnable() {

                                                                                                                    @Override
                                                                                                                    public void run() {
                                                                                                                        try {
                                                                                                                            GMailSender sender = new GMailSender("noreply.rerecruit@gmail.com",
                                                                                                                                    "116311691FYP");
                                                                                                                            sender.sendMail("A decision has been made on your application",
                                                                                                                                    "Hey " + eFName + ", \n\nA decision has been made by " + eOrgName + " " +
                                                                                                                                            "regarding your application for " + eRoleTitle + "!\n\nWe are delighted to inform you that you have " +
                                                                                                                                            "been successful in your application and they would like to offer you the position of " + eRoleTitle +
                                                                                                                                            "! \n\nTo accept this offer please " +
                                                                                                                                            "email " + oEmail + "\n\nCongratulations!",
                                                                                                                                    "noreply.rerecruit@gmail.com", " " + aEmail);
                                                                                                                        } catch (Exception e) {
                                                                                                                            Log.e("SendMail", e.getMessage(), e);
                                                                                                                        }
                                                                                                                    }

                                                                                                                }).start();
                                                                                                            } else if (newOutcome.equals("Unsuccessful")) {
                                                                                                                new Thread(new Runnable() {

                                                                                                                    @Override
                                                                                                                    public void run() {
                                                                                                                        try {
                                                                                                                            GMailSender sender = new GMailSender("noreply.rerecruit@gmail.com",
                                                                                                                                    "116311691FYP");
                                                                                                                            sender.sendMail("A decision has been made on your application",
                                                                                                                                    "Hey " + eFName + ", \n\nA decision has been made by " + eOrgName + " regarding your application for " + eRoleTitle + "!\n\nWe regret to inform you that you have" +
                                                                                                                                            " been unsuccessful this time." +
                                                                                                                                            "\n\nTo apply to more roles please go to your app to view open job listings!",
                                                                                                                                    "noreply.rerecruit@gmail.comm", " " + aEmail);
                                                                                                                        } catch (Exception e) {
                                                                                                                            Log.e("SendMail", e.getMessage(), e);
                                                                                                                        }
                                                                                                                    }

                                                                                                                }).start();


                                                                                                            }

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

                                                                                            params.put("applicationID", applicationID);
                                                                                            params.put("applicantID", applicantID);
                                                                                            params.put("organisationID", organisationID);

                                                                                            return params;

                                                                                        }
                                                                                    };

                                                                                    RequestHandler.getInstance(SingleInterview.this).addToRequestQueue(stringRequest3);
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

                                                                params.put("interviewID", interviewId);

                                                                return params;

                                                            }
                                                        };

                                                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);


                                                    }else {
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
                                        params.put("feedback", newFeedback);
                                        params.put("interviewID", interviewId);
                                        return params;


                                    }
                                };

                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

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
                params.put("location", newLocation);
                params.put("additional", newAdditional);
                params.put("time", newTime);
                params.put("interviewID", interviewId);
                params.put("outcome", newOutcome);
                return params;


            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);



         /*else if ((!newOutcome.equals(dbOutcome) && !newFeedback.equals(dbFeedback)) && (newTime.equals(dbTime) || newAdditional.equals(dbAdditional) || newLocation.equals(dbLocation))) {

            //ApplicationCV.java
            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_INTERVIEW_EMAIL,
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

                                    if (newFeedback.equals(dbFeedback)){

                                    } else if (newFeedback.equals("")) {

                                    } else {
                                        new Thread(new Runnable() {

                                            @Override
                                            public void run() {
                                                try {
                                                    GMailSender sender = new GMailSender("clodaghFYP@gmail.com",
                                                            "116311691FYP");
                                                    sender.sendMail("Application Decision",
                                                            "Hey " + eFName + ", \n\nA decision has been on your application for " + eRoleTitle + " at " + eOrgName + " and feedback has been added. \n\nPlease check your app to view.",
                                                            "clodaghFYP@gmail.com", " " + aEmail);
                                                } catch (Exception e) {
                                                    Log.e("SendMail", e.getMessage(), e);
                                                }
                                            }

                                        }).start();
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

                    Log.d("TAG****", applicationID);
                    Log.d("TAG****", applicantID);
                    Log.d("TAG****", organisationID);
                    params.put("applicationID", applicationID);
                    params.put("applicantID", applicantID);
                    params.put("organisationID", organisationID);

                    return params;

                }
            };

            RequestHandler.getInstance(SingleInterview.this).addToRequestQueue(stringRequest2);


            //generic
        }
        else {


            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        GMailSender sender = new GMailSender("clodaghFYP@gmail.com",
                                "116311691FYP");
                        sender.sendMail("Interview Changes",
                                "Hey " + eFName + ", \n\nChanges have been made to your interview/application status for " + eRoleTitle + " at " + eOrgName + ". \n\nPlease check your app to view.",
                                "clodaghFYP@gmail.com", " " + aEmail);
                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }
                }

            }).start();

        }
        */


        //loadInterview();


    }

    private void delete(){
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView2 = li.inflate(R.layout.prompt_confirm, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

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
                                                        Intent intent = new Intent(SingleInterview.this, OrgInterviewSchedule.class);
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
                                        params.put("interviewID", interviewId);
                                        return params;


                                    }
                                };

                                RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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

    }

    @Override
    public void onDateSet(DatePicker view, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1;
        dayFinal = i2;

        monthFinal += 1;

        String display = yearFinal + "-" + monthFinal + "- " + dayFinal;
        etDate.setText(display);


    }

    @Override
    public void onTimeSet(TimePicker view, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;
        String display = hourFinal + ": " + minuteFinal;
        Log.d("DISPLAY", display);
        etTime.setText(display);
    }

    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        calendarView.setDate(0);

    }

     */

    /*
    @Override
    protected void onResume() {
        super.onResume();

        bottomNavigationView.setSelectedItemId(R.id.interviews);

    }

     */

}

