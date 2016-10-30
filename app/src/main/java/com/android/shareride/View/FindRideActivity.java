package com.android.shareride.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.android.shareride.R;

import java.util.List;

public class FindRideActivity extends AppCompatActivity implements PostedFragment.OnListFragmentInteractionListener {

    PostedFragment.OnListFragmentInteractionListener mListener;

    private int userID;
    private int driverID;
    private String from;
    private String to;
    private String date;
    private String time;
    private float cost;
    private String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extra = getIntent().getExtras();
        userID = extra.getInt("UserID");

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list);
        Context context = recyclerView.getContext();
        mListener = (PostedFragment.OnListFragmentInteractionListener) context;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        List<Trip> tripList = helper.getTrips();
        recyclerView.setAdapter(new MyTripsRecyclerViewAdapter(tripList, mListener));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Trip trip) {
        //Toast.makeText(getApplicationContext(), trip.toString(), Toast.LENGTH_SHORT).show();
        driverID = trip.userID;
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        User driver = helper.getUserName(driverID);

        Intent intent = new Intent(FindRideActivity.this, TripDetailActivity.class);
        intent.putExtra("UserID",userID);
        intent.putExtra("From", trip.from);
        intent.putExtra("To", trip.to);
        intent.putExtra("Departure", trip.date + " " + trip.time);
        intent.putExtra("Cost", trip.individualFare);
        intent.putExtra("DriverName", driver.fullName);
        intent.putExtra("Contact", driver.contactNum);
        intent.putExtra("TripID", trip.tripID);
        startActivity(intent);
    }
}
