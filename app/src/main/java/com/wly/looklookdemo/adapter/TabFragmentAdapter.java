package com.wly.looklookdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wly.looklookdemo.fragment.NewsPagerFragment;
import com.wly.looklookdemo.utils.Urls;

/**
 * Created by Candy on 2016/9/23.
 */
public class TabFragmentAdapter extends FragmentPagerAdapter {

    public String[] titleData = new String[]{
            "科技",
            "财经",
            "军事",
            "体育",
            "娱乐"
    };

    public String[] titleId = new String[]{

            Urls.TECH_ID,
            Urls.FINANCE_ID,
            Urls.MILITARY_ID,
            Urls.SPORTS_ID,
            Urls.ENTERTAINMENT_ID
    };

    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        NewsPagerFragment fragment = NewsPagerFragment.newInstance(titleId[position]);
        return fragment;
    }

    @Override
    public int getCount() {
        return titleData.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titleData[position];
    }
}
