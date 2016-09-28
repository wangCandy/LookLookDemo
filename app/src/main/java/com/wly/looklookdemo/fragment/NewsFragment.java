package com.wly.looklookdemo.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wly.looklookdemo.R;
import com.wly.looklookdemo.adapter.TabFragmentAdapter;
import com.wly.looklookdemo.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Candy on 2016/9/2.
 */
public class NewsFragment extends BaseFragment {

    public static final String TAG = NewsFragment.class.getSimpleName();

    @Bind(R.id.tabs)
    TabLayout mTab;
    @Bind(R.id.viewpager)
    ViewPager mPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wangyi;
    }

    @Override
    protected void findView() {
        ButterKnife.bind(this , convertView);
//        mTab = (TabLayout) convertView.findViewById(R.id.tabs);
//        mPager = (ViewPager) convertView.findViewById(R.id.viewpager);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initialize() {

        TabFragmentAdapter adapter = new TabFragmentAdapter(getFragmentManager());
        mPager.setAdapter(adapter);
        mTab.setupWithViewPager(mPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: BaseFragment");
        ButterKnife.unbind(this);
    }
}
