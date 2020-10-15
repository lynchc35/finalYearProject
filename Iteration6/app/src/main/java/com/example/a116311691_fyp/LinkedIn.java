package com.example.a116311691_fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LinkedIn extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_in);

        //https://www.youtube.com/watch?v=JjfSjMs0ImQ
        //START
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.advice);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        Intent intent3 = new Intent(LinkedIn.this, ApplicantProfile.class);
                        startActivity(intent3);
                        return true;
                    case R.id.jobs:
                        Intent intent = new Intent(LinkedIn.this, JobListings.class);
                        startActivity(intent);
                        return true;
                    case R.id.applications:
                        Intent intent4 = new Intent(LinkedIn.this, applicantApplications.class);
                        startActivity(intent4);
                        return true;
                    case R.id.interviews:
                        Intent intent2 = new Intent(LinkedIn.this, ApplicantInterviews.class);
                        startActivity(intent2);
                        return true;
                    case R.id.advice:
                        Intent intent5 = new Intent(LinkedIn.this, Advice.class);
                        startActivity(intent5);
                        return true;
                }
                return false;
            }
        });

        //END
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(LinkedIn.this, Advice.class);
        startActivity(intent);

    }
}
