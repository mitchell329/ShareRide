package com.android.shareride.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.shareride.R;

public class TripDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String ALERT_TITLE = "Success";
    private static final String ALERT_MESSAGE = "Thanks for booking.";
    private static final String ALERT_OK_BUTTON = "OK";
    private static final String BOOKING_FAIL = "Booking ride failed.";

    private TextView lblFrom;
    private TextView lblTo;
    private TextView lblDeparture;
    private TextView lblCost;
    private TextView lblDriver;
    private TextView lblContact;
    private Button btnBookRide;
    private Button btnBack;

    private Bundle extra;
    private int tripID;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lblFrom = (TextView)findViewById(R.id.fromTextView);
        lblTo = (TextView)findViewById(R.id.toTextView);
        lblDeparture = (TextView)findViewById(R.id.departureTextView);
        lblCost = (TextView)findViewById(R.id.costTextView);
        lblDriver = (TextView)findViewById(R.id.driverTextView);
        lblContact = (TextView)findViewById(R.id.contactTextView);
        btnBookRide = (Button)findViewById(R.id.bookRideButton);
        btnBack = (Button)findViewById(R.id.backButton);

        extra = getIntent().getExtras();
        lblFrom.setText(extra.getString("From"));
        lblTo.setText(extra.getString("To"));
        lblDeparture.setText(extra.getString("Departure"));
        lblCost.setText(String.valueOf(extra.getFloat("Cost")));
        lblDriver.setText(extra.getString("DriverName"));
        lblContact.setText(extra.getString("Contact"));

        tripID = extra.getInt("TripID");
        userID = extra.getInt("UserID");

        btnBookRide.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bookRideButton:
                Toast.makeText(getApplicationContext(), "booked", Toast.LENGTH_SHORT).show();
                DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                long bookingID = helper.newBooking(userID, tripID);
                if (bookingID != -1)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TripDetailActivity.this);
                    builder.setTitle(ALERT_TITLE);
                    builder.setMessage(ALERT_MESSAGE);
                    builder.setPositiveButton(ALERT_OK_BUTTON, alertOKListener);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), BOOKING_FAIL, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.backButton:
                this.finish();
        }
    }

    DialogInterface.OnClickListener alertOKListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(TripDetailActivity.this, ProfileActivity.class);
            intent.putExtra("UserID", userID);
            intent.putExtra("OpenTab", 1);
            //intent.putExtra("TripID", tripID);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), ALERT_OK_BUTTON, Toast.LENGTH_SHORT).show();
        }
    };
}
