package com.subayu.comarudin.lbb_gis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by black4v on 06/05/2017.
 */

class ControlerPager extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ControlerPager(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MenuUtama mn = new MenuUtama();
                return mn;
            case 1:
                LokUser lok = new LokUser();
                return lok;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
