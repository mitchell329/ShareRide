package com.android.shareride.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.android.shareride.R;

public class MainActivity extends AppCompatActivity {

    private WelcomePagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mSectionsPagerAdapter = new WelcomePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.vpgWelcome);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        for (Fragment frag : fm.getFragments()) {
            if (frag.isVisible()) {
                FragmentManager childFm = frag.getChildFragmentManager();
                if (childFm.getBackStackEntryCount() > 0) {
                    childFm.popBackStack();
                    return;
                }
            }
        }
        super.onBackPressed();
    }
}
