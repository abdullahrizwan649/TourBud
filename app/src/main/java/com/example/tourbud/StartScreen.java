package com.example.tourbud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Objects;

public class StartScreen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_start_screen);

        ImageView logo = findViewById(R.id.start_logo);
        Animation move = AnimationUtils.loadAnimation(StartScreen.this,R.anim.start_animation);
        logo.startAnimation(move);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        }, 2000);
    }
}