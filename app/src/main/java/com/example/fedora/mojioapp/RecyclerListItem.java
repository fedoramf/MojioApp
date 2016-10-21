package com.example.fedora.mojioapp;

/**
 * Created by Fedora on 2016-10-19.
 */
public class RecyclerListItem {
    private String startLocation;
    private String endLocation;
    private String tripDate;

    public void setStartLocation(String location) {
        this.startLocation = location;
    }

    public void setEndLocation(String location) {
        this.endLocation = location;
    }

    public void setTripDate(String date) {
        this.tripDate = date;
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
}
