package com.example.tourbud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterScreen extends AppCompatActivity {
Button registered_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        registered_btn = findViewById(R.id.registered_button);

        registered_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_registered;
                intent_registered = new Intent(RegisterScreen.this,MainScreen.class);
                startActivity(intent_registered);
            }
        });


    }
}