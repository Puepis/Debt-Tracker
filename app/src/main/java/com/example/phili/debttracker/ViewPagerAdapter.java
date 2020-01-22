package com.example.phili.debttracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

/**
 * This class represents the adapter that holds the Fragment tabs.
 *
 * @see FragmentPagerAdapter
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    // Fragments and titles
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();


    /**
     * This method is the parameterized constructor for the adapter.
     *
     * @param fm
     */
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * This accessor method returns the Fragment at the indicated position.
     *
     * @param position
     * @return Fragment
     */
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    /**
     * Returns the number of fragments.
     *
     * @return int
     */
    @Override
    public int getCount() {
        return titleList.size();
    }

    /**
     * Returns title of Fragment at specific postiion
     *
     * @return CharSequence
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }


    /**
     * Add new fragment to list.
     *
     * @param fragment
     * @param title
     */
    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }
}
