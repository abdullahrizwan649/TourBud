package com.example.tourbud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class ActivityScreen extends AppCompatActivity {
    private int selectedTab = 1;
    LinearLayout cardContainer;

    ImageButton delete_add_btn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        List<TourSearchDetail> tourSearchDetail = getTourSearchDetails();
        cardContainer = findViewById(R.id.cardContainers);


        final LinearLayout homeLayout = findViewById(R.id.homeLayout);
        final LinearLayout activityLayout = findViewById(R.id.activityLayout);
        final LinearLayout profileLayout = findViewById(R.id.profileLayout);


        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = 1;
                Intent intent_home;
                intent_home = new Intent(ActivityScreen.this, MainScreen.class);
                startActivity(intent_home);
            }
        });




        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = 3;
                Intent intent_profile;
                intent_profile = new Intent(ActivityScreen.this, ProfileScreen.class);
                startActivity(intent_profile);
            }
        });

        for (TourSearchDetail tourSearchDetails : tourSearchDetail) {
            CardView cardView = createCardView(tourSearchDetails);
            cardContainer.addView(cardView);
        }

        delete_add_btn = findViewById(R.id.delete_add_btn);
        delete_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //add
            }
        });

    }

    private List<TourSearchDetail> getTourSearchDetails() {
        // Retrieve your tour search details from your data source
        // For this example, we'll use dummy data

        List<TourSearchDetail> tourSearchDetails = new ArrayList<>();

        tourSearchDetails.add(new TourSearchDetail("Tour 1", "Destination 1", "Price 1"));
        tourSearchDetails.add(new TourSearchDetail("Tour 2", "Destination 2", "Price 2"));
        tourSearchDetails.add(new TourSearchDetail("Tour 3", "Destination 3", "Price 3"));
        tourSearchDetails.add(new TourSearchDetail("Tour 4", "Destination 4", "Price 4"));
        tourSearchDetails.add(new TourSearchDetail("Tour 5", "Destination 5", "Price 5"));

        return tourSearchDetails;
    }

    private CardView createCardView(TourSearchDetail tourSearchDetail) {
        // Inflate the card view layout
        CardView cardView = (CardView) getLayoutInflater().inflate(R.layout.card_view_layout, null);

        // Find and set the tour search details in the card view's views
        TextView tourTitleTextView = cardView.findViewById(R.id.tourTitleTextView);
        TextView destinationTextView = cardView.findViewById(R.id.destinationTextView);
        TextView priceTextView = cardView.findViewById(R.id.priceTextView);

        tourTitleTextView.setText(tourSearchDetail.getTourTitle());
        destinationTextView.setText(tourSearchDetail.getDestination());
        priceTextView.setText(tourSearchDetail.getPrice());

        return cardView;
    }
}