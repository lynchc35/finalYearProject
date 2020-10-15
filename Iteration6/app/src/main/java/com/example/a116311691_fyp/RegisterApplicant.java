package com.example.a116311691_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterApplicant extends AppCompatActivity {

    //COPY & PASTED CODE FROM MAIN ACTIVITY AND ALTERED TO SUIT THIS ACTIVITY/APPLICANT REGISTRATION

    private EditText etUsername, etPassword, etFName, etLName, etAge, etAddress, etEmail, etPhoneNumber,
            etRolePref;
    private Spinner spLocation, spIndustry, spBreakReason;
    private Button btnRegister;
    private TextView tvLogin;
    final String Tag = "******FYP*****";
    //https://www.youtube.com/watch?v=8_ilbujBwsk&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=6
    private ProgressDialog progressDialog;
    final Context context = this;


    String username;
    String password;
    String fName;
    String lName;
    String age;
    String address;
    String email;
    String phoneNo;
    String breakReason;
    String industryPref;
    String locationPref;
    String rolePref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_applicant);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etFName = findViewById(R.id.etFName);
        etLName = findViewById(R.id.etLName);
        etAge = findViewById(R.id.etAge);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNo);
        spBreakReason = findViewById(R.id.spBreakReason);
        spLocation = findViewById(R.id.spLocation);
        spIndustry = findViewById(R.id.spIndustry);
        etRolePref = findViewById(R.id.etRolePref);

        // Code below comes from my IS4447 CA practice example - Documents - MobileDev - CA1 - OneSpinner_Test
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.industry_values, R.layout.app_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIndustry.setAdapter(adapter);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                R.array.location_values, R.layout.app_spinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(adapter2);

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this,
                R.array.career_values, R.layout.app_spinner);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBreakReason.setAdapter(adapter3);
        //END

        btnRegister = findViewById(R.id.btnRegister);

        tvLogin = findViewById(R.id.tvLogin);

        //https://www.youtube.com/watch?v=8_ilbujBwsk&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=6
        progressDialog = new ProgressDialog(this);

        //know this code from IS4447
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterApplicant.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ApplicationCV.java
                //START

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompt_disclosure, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                alertDialogBuilder.setView(promptsView);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Accept",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        username = etUsername.getText().toString().trim();
                                        password = etPassword.getText().toString().trim();
                                        fName = etFName.getText().toString().trim();
                                        lName = etLName.getText().toString().trim();
                                        age = etAge.getText().toString().trim();
                                        address = etAddress.getText().toString().trim();
                                        email = etEmail.getText().toString();
                                        phoneNo = etPhoneNumber.getText().toString();
                                        breakReason = spBreakReason.getSelectedItem().toString();
                                        industryPref = spIndustry.getSelectedItem().toString().trim();
                                        locationPref = spLocation.getSelectedItem().toString().trim();
                                        rolePref = etRolePref.getText().toString();
                                        registerUser();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
                //END



            }
        });
    }

    //https://stackoverflow.com/questions/22505336/email-and-phone-number-validation-in-android
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    //https://stackoverflow.com/questions/22505336/email-and-phone-number-validation-in-android
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    private void registerUser() {

        //wrote this if statement myself
        if (username.equals("") || password.equals("") || fName.equals("") || lName.equals("") || age.equals("") || address.equals("") || email.equals("") || phoneNo.equals("")
                || breakReason.equals("") || industryPref.equals("") || locationPref.equals("") || rolePref.equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show();
        } else if (isValidMail(email) == false){
            Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        } else if (isValidMobile(phoneNo) == false){
            Toast.makeText(getApplicationContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
        } else {
            //https://www.youtube.com/watch?v=8_ilbujBwsk&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=6

            progressDialog.setMessage("Registering applicant...");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_APPLICANT_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {
                                //https://stackoverflow.com/questions/10376645/org-json-jsonexception-value-br-of-type-java-lang-string-cannot-be-converted-t
                                //START
                                Log.i("tagconvertstr", "["+response+"]");
                                //END
                                JSONObject jsonObject = new JSONObject(response);
                                //jsonObject.getString logic comes from the userLogin() method in LoginActivity.java
                                String message = jsonObject.getString("message");
                                //'Applicant registered successfully' comes from the PHP code
                                if (message.equals("Applicant registered successfully")) {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(RegisterApplicant.this, LoginActivity.class);
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
                            progressDialog.hide();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    params.put("fName", fName);
                    params.put("lName", lName);
                    params.put("age", age);
                    params.put("address", address);
                    params.put("email", email);
                    params.put("phoneNumber", phoneNo);
                    params.put("breakReason", breakReason);
                    params.put("locationPref", locationPref);
                    params.put("industryPref", industryPref);
                    params.put("rolePref", rolePref);
                    return params;


                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(RegisterApplicant.this, LoginActivity.class);
        startActivity(intent);
    }
}
