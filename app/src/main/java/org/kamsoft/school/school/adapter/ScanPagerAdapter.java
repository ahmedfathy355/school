package org.kamsoft.school.school.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import org.kamsoft.school.school.fragments.ScanFragment;

import java.util.ArrayList;

/**
 * Created by ahmed fathy on 9-3-2018.
 */
public class ScanPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> mFragmentItems;

    public ScanPagerAdapter(FragmentManager fm, ArrayList<String> fragmentItems) {
        super(fm);
        this.mFragmentItems = fragmentItems;
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = null;

        if(i == 0) {
            fragment = new ScanFragment();
         }
// else if(i == 1){
//            //fragment = new HistoryFragment();
//        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentItems.size();
    }

}
