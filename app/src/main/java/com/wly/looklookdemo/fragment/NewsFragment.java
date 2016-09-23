package com.wly.looklookdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wly.looklookdemo.R;
import com.wly.looklookdemo.adapter.TabFragmentAdapter;
import com.wly.looklookdemo.adapter.TopNewsListAdapter;
import com.wly.looklookdemo.activities.TopPhotoDetailActivity;
import com.wly.looklookdemo.api.ApiHandler;
import com.wly.looklookdemo.api.LookAppApiClient;
import com.wly.looklookdemo.base.BaseFragment;
import com.wly.looklookdemo.bean.TopNewsBean;
import com.wly.looklookdemo.news.NewsDetailActivity;
import com.wly.looklookdemo.utils.JsonUtils;
import com.wly.looklookdemo.utils.NetWorkCheckUtil;
import com.wly.looklookdemo.utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Candy on 2016/9/2.
 */
public class NewsFragment extends BaseFragment{

    public TabLayout mTab;

    public ViewPager mPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wangyi;
    }

    @Override
    protected void findView() {

        mTab = (TabLayout) convertView.findViewById(R.id.tabs);
        mPager = (ViewPager) convertView.findViewById(R.id.viewpager);
    }
    @Override
    protected void setListener() {
    }

    @Override
    protected void initialize() {

        TabFragmentAdapter adapter = new TabFragmentAdapter(getFragmentManager());
        mPager.setOffscreenPageLimit(2);
        mPager.setAdapter(adapter);
        mTab.setupWithViewPager(mPager);
    }
}
