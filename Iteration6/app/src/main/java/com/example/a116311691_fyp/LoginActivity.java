package com.example.a116311691_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

//https://www.youtube.com/watch?v=icjkBx1qVwQ&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=10

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvOrg, tvApplicant;
    private ProgressDialog progressDialog;
    final static String Tag = "****FYP***";

    //know from IS4447 - Documents - FourthYear - MobileDev - CA1 - OneSpinnerTest project
    private static final String TAG = "*******FYP*******";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //https://stackoverflow.com/questions/4149415/onscreen-keyboard-opens-automatically-when-activity-starts
        //START
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //END

        tvOrg = findViewById(R.id.tvOrg);
        tvApplicant = findViewById(R.id.tvApplicantName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        //https://www.youtube.com/watch?v=icjkBx1qVwQ&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=10
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        //IS4447
        tvOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //IS4447
        tvApplicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterApplicant.class);
                startActivity(intent);
                finish();
            }
        });

        //IS4447
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((etUsername.getText().toString().equals("")) || (etPassword.getText().toString().equals(""))){

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Enter your login credentials", Toast.LENGTH_SHORT).show();

                } else {
                    userLogin();
                }

            }
        });



    }

    //https://www.youtube.com/watch?v=icjkBx1qVwQ&list=PLk7v1Z2rk4hjQaV062aE_CW68xgXdYFpV&index=10
    private void userLogin(){
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(Tag,response);
                        progressDialog.dismiss();

                        try {
                            Log.d(Tag,response);
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) { //means user is successfully authenticated
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .orgLogin(
                                                obj.getInt("organisationID"),
                                                obj.getString("username"),
                                                obj.getString("password"),
                                                obj.getString("orgName"),
                                                obj.getString("location"),
                                                obj.getString("industry"),
                                                obj.getString("email"),
                                                obj.getString("phoneNumber"),
                                                obj.getString("url")

                                        );

                                Intent intent = new Intent(LoginActivity.this, OrgProfile.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                                finish();


                            }else {

                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) { //means user is successfully authenticated
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .applicantLogin(
                                                obj.getInt("applicantID"),
                                                obj.getString("username"),
                                                obj.getString("password"),
                                                obj.getString("fName"),
                                                obj.getString("lName"),
                                                obj.getString("age"),
                                                obj.getString("address"),
                                                obj.getString("email"),
                                                obj.getString("phoneNumber"),
                                                obj.getString("breakReason"),
                                                obj.getString("locationPref"),
                                                obj.getString("industryPref"),
                                                obj.getString("rolePref")
                                        );

                                Intent intent = new Intent(LoginActivity.this, ApplicantProfile.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                               finish();


                            }else {

                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }



        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    //https://stackoverflow.com/questions/17719634/how-to-exit-an-android-app-programmatically
    //START
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
    //END
}
