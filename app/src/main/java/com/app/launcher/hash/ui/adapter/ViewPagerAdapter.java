package com.app.launcher.hash.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private FragmentManager fragmentManager;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        fragmentManager = manager;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
//        mFragmentTitleList.add(title);
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mFragmentTitleList.get(position);
//    }

    public void removeAllfragments()
    {
        if ( mFragmentList != null ) {
            for ( Fragment fragment : mFragmentList ) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
            mFragmentList.clear();
            notifyDataSetChanged();
        }
    }
}