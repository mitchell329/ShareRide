package com.android.shareride.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.shareride.R;

public class PostRideActivity extends AppCompatActivity {

    private static final String  INCOMPLETE_INFOR = "Please enter all the fields.";
    private static final String ALERT_TITLE = "Success";
    private static final String ALERT_MESSAGE = "Your trip has been posted!";
    private static final String ALERT_OK_BUTTON = "OK";
    private static final String POST_FAIL = "Post trip failed.";

    private Bundle extra;
    private int userID;
    private String fullName;
    private long tripID;

    private EditText txtFrom;
    private EditText txtTo;
    private EditText txtDate;
    private EditText txtTime;
    private EditText txtCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ride);

        extra = getIntent().getExtras();
        userID = extra.getInt("UserID");
        fullName = extra.getString("Fullname");

        txtFrom = (EditText)this.findViewById(R.id.fromEditText);
        txtTo = (EditText)this.findViewById(R.id.toEditText);
        txtDate = (EditText)this.findViewById(R.id.dateEditText);
        txtTime = (EditText)this.findViewById(R.id.timeEditText);
        txtCost = (EditText)this.findViewById(R.id.costEditText);

        Button btnPost = (Button) this.findViewById(R.id.postButton);
        btnPost.setOnClickListener(postHandler);
    }

    View.OnClickListener postHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String from = txtFrom.getText().toString();
            String to = txtTo.getText().toString();
            String date = txtDate.getText().toString();
            String time = txtTime.getText().toString();
            String cost = txtCost.getText().toString();

            if (from.equals("") || to.equals("") || date.equals("") || time.equals("") || cost.equals(""))
            {
                Toast.makeText(getApplicationContext(), INCOMPLETE_INFOR, Toast.LENGTH_SHORT).show();
            }
            else
            {
                DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                tripID = helper.addNewTrip(userID, from, to, date, time, Float.parseFloat(cost));
                if (tripID != -1)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostRideActivity.this);
                    builder.setTitle(ALERT_TITLE);
                    builder.setMessage(ALERT_MESSAGE);
                    builder.setPositiveButton(ALERT_OK_BUTTON, alertOKListener);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), POST_FAIL, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    DialogInterface.OnClickListener alertOKListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(PostRideActivity.this, ProfileActivity.class);
            intent.putExtra("Fullname", fullName);
            intent.putExtra("UserID", userID);
            intent.putExtra("OpenTab", 0);
            //intent.putExtra("TripID", tripID);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), ALERT_OK_BUTTON, Toast.LENGTH_SHORT).show();
        }
    };
}
