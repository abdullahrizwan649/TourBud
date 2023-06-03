package com.example.tourbud.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService
{

        @Headers("Content-type: application/json")
        @POST("/users/login")
        Call<JsonObject> login(@Body String user);

        @GET("/products")
        Call<JsonArray> fck();



}
