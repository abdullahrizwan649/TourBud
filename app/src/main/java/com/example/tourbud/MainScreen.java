package com.example.tourbud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;

import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.List;



public class MainScreen extends AppCompatActivity implements LocationListener
{
    int selectedTab = 1;
    EditText retSelectDate;
    EditText depSelectDate;
    Button search_btn;

    LocationManager locationManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout activityLayout = findViewById(R.id.activityLayout);
        final LinearLayout profileLayout = findViewById(R.id.profileLayout);
        final TextView invalidDate = findViewById(R.id.invalid_date);


        grantPermission();

        checkLocationIsEnabledOrNot();
        getLocation();

        //departure spinner
        Spinner departure_dd = findViewById(R.id.departure_dropdown);


        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource
                (this, R.array.departures, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        departure_dd.setAdapter(adapter);


        //arrival spinner
        Spinner arrival_dd = findViewById(R.id.destination_dropdown);
        ArrayAdapter<CharSequence>adapter1=ArrayAdapter.createFromResource
                (this, R.array.arrivals_array, android.R.layout.simple_spinner_item);
        arrival_dd.setAdapter(adapter1);


        depSelectDate = findViewById(R.id.dep_select_date);
        retSelectDate = findViewById(R.id.ret_select_date);

        final Calendar d_calendar = Calendar.getInstance();
        final int d_year = d_calendar.get(Calendar.YEAR);
        final int d_month = d_calendar.get(Calendar.MONTH);
        final int d_day = d_calendar.get(Calendar.DAY_OF_MONTH);

        depSelectDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                DatePickerDialog dialog = new DatePickerDialog(MainScreen.this,
                        new DatePickerDialog.OnDateSetListener()
                        {
                    @Override
                    public void onDateSet(DatePicker dep_view, int dep_year, int dep_month, int dep_day)
                    {

                        dep_month = d_month+1;
                        String departure_date = dep_day+"/"+dep_month+"/"+dep_year;
                        depSelectDate.setText(departure_date);

                    }
                },d_year, d_month,d_day);
                dialog.show();

            }
        });

        final Calendar r_calendar = Calendar.getInstance();
        final int r_year = r_calendar.get(Calendar.YEAR);
        final int r_month = r_calendar.get(Calendar.MONTH);
        final int r_day = r_calendar.get(Calendar.DAY_OF_MONTH);

        retSelectDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                DatePickerDialog dialog = new DatePickerDialog(MainScreen.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker ret_view, int ret_year, int ret_month, int ret_dayOfMonth) {

                        ret_month = ret_month+1;
                        String return_date = ret_dayOfMonth+"/"+ret_month+"/"+ret_year;
                        retSelectDate.setText(return_date);

                    }
                },r_year, r_month,r_day);
                dialog.show();

            }
        });


        activityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                selectedTab = 2;
                Intent intent_activity;
                intent_activity = new Intent(MainScreen.this, ActivityScreen.class);
                startActivity(intent_activity);
            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = 3;
                Intent intent_profile;
                intent_profile = new Intent(MainScreen.this, ProfileScreen.class);
                startActivity(intent_profile);
            }
        });



        search_btn = findViewById(R.id.search_button);


        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(check_date() == 0)
                {
                    invalidDate.setVisibility(View.GONE);
                }
                else
                {
                    invalidDate.setVisibility(View.GONE);
                    Intent intent_search;
                    intent_search = new Intent(MainScreen.this, SearchScreen.class);
                    startActivity(intent_search);
                }


            }
        });


    }




    private int check_date() {
        String departureDateStr = depSelectDate.getText().toString();
        String returnDateStr = retSelectDate.getText().toString();

        // Parse the departure and return dates into Calendar objects
        Calendar departureDate = parseDate(departureDateStr);
        Calendar returnDate = parseDate(returnDateStr);

        if (departureDate != null && returnDate != null) {
            if (departureDate.after(returnDate) || departureDate.equals(returnDate)) {
                // Departure date is later than or equal to the return date
                showToast("Please select a valid date range.");
                return 0;
            } else
            {
                // Valid date range, proceed with the search
                Intent intent_search = new Intent(MainScreen.this, SearchScreen.class);
                startActivity(intent_search);
                return 1;
            }
        } else {
            // Invalid dates, show an error message
            showToast("Please select valid departure and return dates.") ;
            return 0;
        }
    }

    // Helper method to parse a date string into a Calendar object
    private Calendar parseDate(String dateStr)
    {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = dateFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void showToast(String message) {
        Toast toast = Toast.makeText(MainScreen.this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void grantPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
    }


    private void checkLocationIsEnabledOrNot()
    {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(MainScreen.this)
                    .setTitle("Enable GPS Service")
                    .setMessage("GIVE ME GPS NOWWWWW")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }
    private void getLocation()
    {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500,5,(LocationListener) this);
        }catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location)
    {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            String currentLocation = addresses.get(0).getLocality();

            String[] departures = getResources().getStringArray(R.array.departures);
            departures[0] = currentLocation;

            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departures);
            Spinner departure_dd = findViewById(R.id.departure_dropdown);
            departure_dd.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}




