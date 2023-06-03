package com.example.tourbud;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ProfileScreen extends AppCompatActivity {


    private int selectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final LinearLayout homeLayout = findViewById(R.id.homeLayout);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final LinearLayout activityLayout = findViewById(R.id.activityLayout);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final LinearLayout profileLayout = findViewById(R.id.profileLayout);
        final Button sign_out = findViewById(R.id.sign_out_btn);

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = 1;
                Intent intent_home;
                intent_home = new Intent(ProfileScreen.this, MainScreen.class);
                startActivity(intent_home);
            }
        });


        activityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = 2;
                Intent intent_activity;
                intent_activity = new Intent(ProfileScreen.this, ActivityScreen.class);
                startActivity(intent_activity);
            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = 3;
                Intent intent_profile;
                intent_profile = new Intent(ProfileScreen.this, ProfileScreen.class);
                startActivity(intent_profile);
            }
        });

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_out;
                intent_out = new Intent(ProfileScreen.this, LoginScreen.class);
                startActivity(intent_out);
            }
        });


    }
}