package com.example.a116311691_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etOrgName, etEmail, etPhoneNumber, etURL;
    private Spinner spLocation, spIndustry;
    private Button btnRegister;
    private TextView tvLogin;
    String username;
    String password;
    String orgName;
    String location;
    String industry;
    String email;
    String phoneNo;
    String url;
    final String Tag = "****FYP****";

    //https://www.youtube.com/watch?v=8_ilbujBwsk&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=6
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etOrgName = findViewById(R.id.etOrgName);
        spLocation = findViewById(R.id.spLocation);
        spIndustry = findViewById(R.id.spIndustry);
        etEmail = findViewById(R.id.etEmailAddress);
        etURL = findViewById(R.id.etURL);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        // Code below comes from my IS4447 CA practice example - Documents - MobileDev - CA1 - OneSpinner_Test
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.industry_values, R.layout.org_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIndustry.setAdapter(adapter);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                R.array.location_values, R.layout.org_spinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(adapter2);
        //END

        //https://www.youtube.com/watch?v=icjkBx1qVwQ&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=10
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister = findViewById(R.id.btnRegister);

        //https://www.youtube.com/watch?v=8_ilbujBwsk&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=6
        progressDialog = new ProgressDialog(this);


        //know this code from IS4447
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = etUsername.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                orgName = etOrgName.getText().toString().trim();
                location = spLocation.getSelectedItem().toString().trim();
                industry = spIndustry.getSelectedItem().toString().trim();
                email = etEmail.getText().toString().trim();
                phoneNo = etPhoneNumber.getText().toString();
                url = etURL.getText().toString();
                registerUser();
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
        if (username.equals("") || password.equals("") || orgName.equals("") || location.equals("") || industry.equals("") || email.equals("") || phoneNo.equals("") || url.equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill out all required fields", Toast.LENGTH_SHORT).show();
        } else if (isValidMail(email) == false){
            Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        } else if (isValidMobile(phoneNo) == false){
            Toast.makeText(getApplicationContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
        } else {

            //START
            //https://www.youtube.com/watch?v=8_ilbujBwsk&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=6

            progressDialog.setMessage("Registering organisation...");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ORG_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                Log.i("tagconvertstr", "["+response+"]");
                                //did this code in RegisterApplicant.java, copied it across and altered it
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                //'Applicant registered successfully' comes from the PHP code
                                if (message.equals("Organisation registered successfully")) {
                                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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
                    Log.d(Tag, password);
                    Log.d(Tag, username);
                    Log.d(Tag, orgName);
                    Log.d(Tag, location);
                    Log.d(Tag, industry);
                    Log.d(Tag, email);
                    Log.d(Tag, phoneNo);
                    params.put("username", username);
                    params.put("password", password);
                    params.put("orgName", orgName);
                    params.put("location", location);
                    params.put("industry", industry);
                    params.put("email", email);
                    params.put("phoneNumber", phoneNo);
                    params.put("url", url);
                    return params;
                }
            };


            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }
        //END
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
