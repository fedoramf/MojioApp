package com.example.fedora.mojioapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.moj.java.sdk.MojioClient;
import io.moj.java.sdk.MojioEnvironment;

import java.lang.reflect.Type;
import java.util.List;

public class App extends Application {

    private static final String APP_ID = "36679cae-d8cf-42c5-bf7b-eaabe49ce24a";
    private static final String APP_SECRET = "07e0b3c6-c823-49d3-8946-926c681272a4";
    private static final String SHAREDPREFS_TAG = "SHARED_PREFS";
    private static final String TRIPDATA_TAG = "TRIP_DATA";
    private MojioClient mojioClient;
    private Gson gson;
    private boolean loggedIn;


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

    public void saveTripListData(List<TripListData> data) {
        Gson gson = new Gson();
        String json = gson.toJson(data);

        SharedPreferences sharedPreferences =  this.getSharedPreferences(SHAREDPREFS_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TRIPDATA_TAG, json);
        editor.commit();
    }

    public List<TripListData> getTripListData(){
        Gson gson = new Gson();
        List<TripListData> productFromShared;

        SharedPreferences sharedPref = this.getSharedPreferences(SHAREDPREFS_TAG, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(TRIPDATA_TAG, "");

        Type type = new TypeToken<List<TripListData>>() {}.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);

        return productFromShared;
    }
}
