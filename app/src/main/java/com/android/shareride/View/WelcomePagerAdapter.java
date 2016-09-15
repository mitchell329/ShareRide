package com.android.shareride.View;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Marshal on 2016/9/15.
 */
public class WelcomePagerAdapter extends FragmentPagerAdapter {

    public WelcomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new WelcomeFrameFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
