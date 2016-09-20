package com.wly.looklookdemo.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ProgressBar;

import com.wly.looklookdemo.R;
import com.wly.looklookdemo.base.BaseFragment;
import com.wly.looklookdemo.bean.NewsBean;
import com.wly.looklookdemo.news.NewsListAdapter;
import com.wly.looklookdemo.news.view.NewsView;
import com.wly.looklookdemo.news.presenter.NewsPresenter;
import com.wly.looklookdemo.news.presenter.NewsPresenterImpl;
import com.wly.looklookdemo.utils.NetWorkCheckUtil;

import java.util.List;

/**
 * Created by Candy on 2016/9/2.
 */
public class ZhihuFragment extends BaseFragment implements NewsView , SwipeRefreshLayout.OnRefreshListener{

    public static final String TAG = ZhihuFragment.class.getSimpleName();

    public NewsPresenter presenter;

    public NewsListAdapter adapter;

    public Context mContext;

    public RecyclerView newsRecycler;

    public ProgressBar progress;

    public SwipeRefreshLayout swipeLayout;

    public ViewStub viewStub;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu;
    }

    @Override
    protected void findView() {

        mContext = getActivity();
        newsRecycler = (RecyclerView) convertView.findViewById(R.id.recycler_news_item);
        progress = (ProgressBar) convertView.findViewById(R.id.progress);
        swipeLayout = (SwipeRefreshLayout) convertView.findViewById(R.id.swipe);
        viewStub = (ViewStub) convertView.findViewById(R.id.viewsub);
        initRecycler();
        initSwipe();
    }

    public void initRecycler(){

        newsRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        newsRecycler.setItemAnimator(new DefaultItemAnimator());
    }

    public void initSwipe(){

        swipeLayout.setColorSchemeResources(R.color.colorAccent ,
                R.color.app_red ,
                R.color.app_red_dark ,
                R.color.app_red_light);
        swipeLayout.setOnRefreshListener(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initialize() {

        if(viewStub == null){
            viewStub.inflate();
        }
        if(NetWorkCheckUtil.isNetWorkConnected(getContext())){

            viewStub.setVisibility(View.GONE);
            presenter = new NewsPresenterImpl(getActivity() , this);
            presenter.loadNewsList();

        }else{
            viewStub.setVisibility(View.VISIBLE);
            Button reload = (Button) convertView.findViewById(R.id.reload);
            reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initialize();
                }
            });
        }
    }

    @Override
    public void addData(List<NewsBean> beans) {

        adapter = new NewsListAdapter(getActivity() , beans);
        newsRecycler.setAdapter(adapter);
    }

    @Override
    public void showProgress() {

        if (swipeLayout.isRefreshing()){

            progress.setVisibility(View.GONE);
        }else{
            progress.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideProgress() {

        swipeLayout.setRefreshing(false);
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onShowFailMsg() {

        Log.d(TAG, "onShowFailMsg");
    }

    @Override
    public void onRefresh() {
        initialize();
    }
}
