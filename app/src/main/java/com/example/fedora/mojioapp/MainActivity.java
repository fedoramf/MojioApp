package com.example.fedora.mojioapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import io.moj.java.sdk.MojioClient;
import io.moj.java.sdk.model.Trip;
import io.moj.java.sdk.model.User;
import io.moj.java.sdk.model.response.ListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickCallback{

    private static final String TAG = "myApp";
    private static final String EXTRA_COUNT = "EXTRA_QUOTE";
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String LOSS_OF_NETWORK_CONNECTIVITY_TRY_AGAIN = "Loss of network connectivity - try again";

    private Context context;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recylerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.recent_trips);

        recyclerView = (RecyclerView) findViewById(R.id.rec_list);
        recylerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recylerViewLayoutManager);

        String loginID = "fedora01";
        String loginPassword = "Test123";

        if(((App) context).getLoginStatus()){
        //logged in
            initRecyclerAdapter();
        }else{
            loginClient(loginID, loginPassword);
        }
    }

    private void loginClient(String loginID, String loginPassword) {

            final MojioClient mClient = ((App) context).getMojioClient();

        Call<User> loginCall = mClient.login(loginID, loginPassword);
            loginCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if (response.isSuccessful()) {

                        mClient.rest().getTrips().enqueue(new Callback<ListResponse<Trip>>() {
                            @Override
                            public void onResponse(Call<ListResponse<Trip>> call, Response<ListResponse<Trip>> response) {
                                if (response.isSuccessful()) {
                                    //save trips to Application activity's list of Trips
                                    ((App) context).setLoginStatus(true);
                                    extractData(response.body().getData());
                                    initRecyclerAdapter();

                                } else {
                                    // Handle the error - this means we got a response without a success code. Are you logged in?
                                    Log.e(TAG, "Error response without a success code - are you logged in?");
                                }
                            }

                            @Override
                            public void onFailure(Call<ListResponse<Trip>> call, Throwable t) {
                                // Handle the error - this is caused by a request failure such as loss of network connectivity
                                Snackbar.make(recyclerView, LOSS_OF_NETWORK_CONNECTIVITY_TRY_AGAIN, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                delayedFinish();
                            }
                        });
                    } else {
                        // Handle the error - this means we got a response without a success code. The user probably
                        // entered the wrong username or password
                        Snackbar.make(recyclerView, "Wrong username/password", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // Handle the error - this is caused by a request failure such as loss of network connectivity
                    Snackbar.make(recyclerView, LOSS_OF_NETWORK_CONNECTIVITY_TRY_AGAIN, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    delayedFinish();

                }
            });
    }

    private void delayedFinish() {
        this.findViewById(android.R.id.content).postDelayed(new Runnable() {

            @Override
            public void run() {
                MainActivity.this.finish();
            }

        }, 3000);
    }

    private void extractData(List<Trip> trips) {

        List<TripListData> data = new ArrayList<>();

        for(int i = 0; i< trips.size(); i++){
            TripListData item = new TripListData(trips.get(i));
            data.add(item);
        }

        ((App) context).saveTripListData(data);
    }

    private void initRecyclerAdapter() {
        //send trips to adapter
        recyclerViewAdapter = new RecyclerAdapter(MainActivity.this, ((App) context).getTripListData());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setItemClickCallback(MainActivity.this);
    }


    @Override
    public void onItemClick(int p) {
        Intent i = new Intent(this, TripDetailActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(EXTRA_COUNT, p);
        i.putExtra(BUNDLE_EXTRAS, extras);

        startActivity(i);
    }

}
