package com.example.tourbud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


class TourSearchDetail{
    String TourTitle;
    String Destination;
    String Price;

    public TourSearchDetail(String tourTitle, String destination, String price) {
        TourTitle = tourTitle;
        Destination = destination;
        Price = price;
    }

    public String getTourTitle() {
        return TourTitle;
    }

    public void setTourTitle(String tourTitle) {
        TourTitle = tourTitle;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}


public class SearchScreen extends AppCompatActivity
{
        LinearLayout cardContainer;
        Button add_btn;


        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_screen);

            cardContainer = findViewById(R.id.cardContainer);

            // Example data for tour search details
            List<TourSearchDetail> tourSearchDetails = getTourSearchDetails();

            // Create and add dynamic card views
            for (TourSearchDetail tourSearchDetail : tourSearchDetails)
            {
                CardView cardView = createCardView(tourSearchDetail);
                cardContainer.addView(cardView);
            }

            add_btn = findViewById(R.id.add_btn);

            add_btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent_add;
                    intent_add = new Intent(SearchScreen.this, ActivityScreen.class);
                    startActivity(intent_add);
                }
            });
        }

        private List<TourSearchDetail> getTourSearchDetails() {
            // Retrieve your tour search details from your data source
            // For this example, we'll use dummy data

            List<TourSearchDetail> tourSearchDetails = new ArrayList<>();

            tourSearchDetails.add(new TourSearchDetail("Tour 1", "Destination", "Price 1"));
            tourSearchDetails.add(new TourSearchDetail("Tour 2", "Destination", "Price 2"));
            tourSearchDetails.add(new TourSearchDetail("Tour 3", "Destination", "Price 3"));
            tourSearchDetails.add(new TourSearchDetail("Tour 4", "Destination", "Price 4"));
            tourSearchDetails.add(new TourSearchDetail("Tour 5", "Destination", "Price 5"));
            tourSearchDetails.add(new TourSearchDetail("Tour 6", "Destination", "Price 6"));
            tourSearchDetails.add(new TourSearchDetail("Tour 7", "Destination", "Price 7"));

            return tourSearchDetails;
        }

        private CardView createCardView(TourSearchDetail tourSearchDetail) {
            // Inflate the card view layout
            CardView cardView = (CardView) getLayoutInflater().inflate(R.layout.card_view_add, null);

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
