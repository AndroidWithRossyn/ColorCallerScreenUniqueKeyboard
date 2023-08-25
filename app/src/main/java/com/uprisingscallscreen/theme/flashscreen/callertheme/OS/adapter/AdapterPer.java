package com.uprisingscallscreen.theme.flashscreen.callertheme.OS.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.uprisingscallscreen.theme.flashscreen.callertheme.OS.fragment.FragmentPer;


public class AdapterPer extends FragmentPagerAdapter {
    @Override
    public int getCount() {
        return 4;
    }

    public AdapterPer(FragmentManager fragmentManager) {
        super(fragmentManager, 1);
    }

    @Override
    public Fragment getItem(int i) {
        return FragmentPer.newInstance(i);
    }
}
