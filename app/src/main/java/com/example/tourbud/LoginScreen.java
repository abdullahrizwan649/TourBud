package com.example.tourbud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


import com.example.tourbud.network.APIService;
import com.example.tourbud.network.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginScreen extends AppCompatActivity {

    Button login_btn;
    Button register_btn;

    EditText email;

    EditText password;

    TextView check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://fakestoreapi.com")
                .addConverterFactory(GsonConverterFactory.create()).build();
        APIService service = retrofit.create(APIService.class);


        login_btn = findViewById(R.id.login_button);
        register_btn = findViewById(R.id.register_btn);

        email = findViewById(R.id.username_input);
        password = findViewById(R.id.password_input);

        check = findViewById(R.id.login_check);



        //login intent
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
//                JSONObject user1 = new JSONObject();
//                try {
//                    user1.put("email" ,emailText );
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//                try {
//                    user1.put("password" ,passwordText );
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//                Call<JsonObject> hammad = service.login(user1.toString());
//                hammad.enqueue(new Callback<JsonObject>()
//                {
//                    @Override
//                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
//                    {
//                        response.body();
//                        check.setText("HAHA");
//
//                        Intent intent_main;
//                        intent_main = new Intent(LoginScreen.this, MainScreen.class);
//                        startActivity(intent_main);
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonObject> call, Throwable t)
//                    {
//                        check.setText("SAD");
//
//                        Log.e("fckme",t.toString());
//                    }
//                });

                Call<JsonArray> hammad = service.fck();
                hammad.enqueue(new Callback<JsonArray>()
                {
                    @Override
                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response)
                    {
                        response.body();
                        check.setText("HAHA");

                        Intent intent_main;
                        intent_main = new Intent(LoginScreen.this, MainScreen.class);
                        startActivity(intent_main);
                    }

                    @Override
                    public void onFailure(Call<JsonArray> call, Throwable t)
                    {
                        check.setText("SAD");

                        Log.e("fckme",t.toString());
                    }
                });

            }
        });





        //register intent
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_register;
                intent_register = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent_register);
            }
        });




    }






}