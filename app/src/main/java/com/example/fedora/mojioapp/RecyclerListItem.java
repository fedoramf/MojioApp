package com.example.fedora.mojioapp;

import android.annotation.TargetApi;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import io.moj.java.sdk.model.Trip;

/**
 * Created by Fedora on 2016-10-19.
 */
public class RecyclerListItem { //RENAME TO TRIPLISTDATA
    private String startLocation, endLocation, tripDate;
    private int maxSpeed, maxRPM, fuelEfficiency;

    public RecyclerListItem(Trip trip){

        setTripDate(formatDate(trip.getEndTimestamp()));
        setLocation(trip.getStartLocation().getAddress().getFormattedAddress(), trip.getEndLocation().getAddress().getFormattedAddress());
        setMaxSpeed(trip.getMaxSpeed().getValue().intValue());
        setMaxRPM(trip.getMaxRPM().getValue().intValue());
        setFuelEfficiency(trip.getFuelEfficiency().getValue().intValue());
    }
    
    public void setLocation(String sLocation, String eLocation) {
        this.startLocation = sLocation;
        this.endLocation = eLocation;
    }

    public void setTripDate(String date) {
        tripDate = date;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setMaxRPM(int maxRPM) {
        this.maxRPM = maxRPM;
    }

    public void setFuelEfficiency(int fuelEfficiency) {
        this.fuelEfficiency = fuelEfficiency;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public String getTripDate() {
        return tripDate;
    }

    public int getMaxSpeed(){
        return maxSpeed;
    }

    public int getMaxRPM(){
        return maxRPM;
    }

    public int getFuelEfficiency(){
        return fuelEfficiency;
    }


    @TargetApi(24)
    private String formatDate(long timeStamp) {
        DateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy' 'H:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        TimeZone tz = TimeZone.getDefault();
        sdf.setTimeZone(tz);
        return sdf.format(calendar.getTime());
    }



}
