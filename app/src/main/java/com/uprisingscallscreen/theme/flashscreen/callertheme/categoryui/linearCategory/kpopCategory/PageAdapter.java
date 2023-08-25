package com.uprisingscallscreen.theme.flashscreen.callertheme.categoryui.linearCategory.kpopCategory;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by TomazWang on 2016/10/31.
 */
public class PageAdapter extends FragmentPagerAdapter {

    String[] data;

    public PageAdapter(FragmentManager fm, String[] data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        return PagerFragmet.newInstance(data[position], position);
    }

    @Override
    public int getCount() {
        return data.length;
    }
}
