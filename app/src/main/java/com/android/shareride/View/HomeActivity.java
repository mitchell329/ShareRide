package com.android.shareride.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.shareride.R;

public class HomeActivity extends AppCompatActivity {

    Bundle extra;
    String fullName;
    String username;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        extra = getIntent().getExtras();
        fullName = extra.getString("Fullname");
        username = extra.getString("Username");
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        if (helper.getUserID(username) != -1)
            userID = helper.getUserID(username);

        TextView txtFullName = (TextView) this.findViewById(R.id.fullnameTextView);
        txtFullName.setText(fullName);

        Toolbar toolbar = (Toolbar) findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.action_bar_logo);

        Button btnPostRide = (Button) this.findViewById(R.id.postRideButton);
        Button btnFindRide = (Button) this.findViewById(R.id.findRideButton);

        btnPostRide.setOnClickListener(clickListener);
        btnFindRide.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.postRideButton:
                    Intent intentPost = new Intent(HomeActivity.this, PostRideActivity.class);
                    intentPost.putExtra("UserID", userID);
                    intentPost.putExtra("Fullname", fullName);
                    startActivity(intentPost);
                    break;
                case  R.id.findRideButton:
                    Intent intentFind = new Intent(HomeActivity.this, FindRideActivity.class);
                    intentFind.putExtra("UserID", userID);
                    intentFind.putExtra("Fullname", fullName);
                    startActivity(intentFind);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile)
        {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("UserID", userID);
            intent.putExtra("OpenTab", 0);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
