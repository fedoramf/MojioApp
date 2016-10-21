package com.example.fedora.mojioapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import io.moj.java.sdk.model.Trip;

/**
 * Created by Fedora on 2016-10-19.
 */
public class TripDetailActivity extends AppCompatActivity {

    private static final String EXTRA_COUNT = "EXTRA_QUOTE";
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";

    private Trip currentTrip;
    private TextView maxSpeedTV, maxRPMTV, durationTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(R.string.trip_detail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        maxSpeedTV = (TextView) findViewById(R.id.maxSpeed);
        maxRPMTV = (TextView) findViewById(R.id.maxRPM);
        durationTV = (TextView) findViewById(R.id.duration);

        Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);

        getTripData(extras.getInt(EXTRA_COUNT));
        setTripDataView();

    }

    private void setTripDataView() {
        maxSpeedTV.setText("Max Speed: " + currentTrip.getMaxSpeed().getValue().intValue() + " KMPH");
        maxRPMTV.setText("Max RPM: " + currentTrip.getMaxRPM().getValue().intValue() + " RPM");
        durationTV.setText("Fuel Efficiency: " + currentTrip.getFuelEfficiency().getValue().intValue() + " " + currentTrip.getFuelEfficiency().getBaseFuelEfficiencyUnit().toString());
    }

    private void getTripData(int i) {
        currentTrip = ((App) getApplicationContext()).getTrips().get(i);
    }
}
