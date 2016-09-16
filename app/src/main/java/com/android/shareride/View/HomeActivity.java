package com.android.shareride.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.android.shareride.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle extra = getIntent().getExtras();
        String fullName = extra.getString("Fullname");
        TextView txtFullName = (TextView) this.findViewById(R.id.fullnameTextView);
        txtFullName.setText(fullName);

        Toolbar toolbar = (Toolbar) findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.drawable.action_bar_logo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }
}
