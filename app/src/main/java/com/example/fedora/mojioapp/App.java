package com.example.fedora.mojioapp;

import android.app.Application;

import com.google.gson.Gson;

import io.moj.java.sdk.MojioClient;
import io.moj.java.sdk.MojioEnvironment;
import io.moj.java.sdk.model.Trip;

import java.util.List;

public class App extends Application {

    private static final String APP_ID = "36679cae-d8cf-42c5-bf7b-eaabe49ce24a";
    private static final String APP_SECRET = "07e0b3c6-c823-49d3-8946-926c681272a4";

    private MojioClient mojioClient;
    private Gson gson;
    private boolean loggedIn;
    private List<Trip> trips;

    @Override
    public void onCreate() {
        super.onCreate();

        gson = new Gson();
        mojioClient = new MojioClient.Builder(APP_ID, APP_SECRET)
                .environment(MojioEnvironment.STAGING)
                .logging(BuildConfig.DEBUG)
                .gson(gson)
                .build();
        loggedIn = false;
    }

    public MojioClient getMojioClient() {
        return mojioClient;
    }

    public Gson getGson() {
        return gson;
    }

    public boolean getLoginStatus(){
        return loggedIn;
    }

    public void setLoginStatus(boolean status){
        loggedIn = status;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }


}
