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

public class TripListData {
    private String startLocation, endLocation, tripDate, maxSpeed, maxRPM, fuelEfficiency;

    public TripListData(Trip trip){

        setTripDate(formatDate(trip.getEndTimestamp()));
        setLocation(trip.getStartLocation().getAddress().getFormattedAddress(), trip.getEndLocation().getAddress().getFormattedAddress());
        setMaxSpeed("Max Speed : " + trip.getMaxSpeed().getValue().intValue() + " KMPH");
        setMaxRPM("Max RPM : " + trip.getMaxRPM().getValue().intValue() + " " + trip.getMaxRPM().getRpmUnit().toString());
        setFuelEfficiency("Fuel Efficiency: " + trip.getFuelEfficiency().getValue().intValue() + " " + trip.getFuelEfficiency().getBaseFuelEfficiencyUnit().toString());
    }
    
    private void setLocation(String sLocation, String eLocation) {
        this.startLocation = sLocation;
        this.endLocation = eLocation;
    }

    private void setTripDate(String date) {
        tripDate = date;
    }

    private void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    private void setMaxRPM(String maxRPM) {
        this.maxRPM = maxRPM;
    }

    private void setFuelEfficiency(String fuelEfficiency) {
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

    public String getMaxSpeed(){
        return maxSpeed;
    }

    public String getMaxRPM(){
        return maxRPM;
    }

    public String getFuelEfficiency(){
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
