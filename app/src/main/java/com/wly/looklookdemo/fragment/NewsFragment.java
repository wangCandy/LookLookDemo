package com.wly.looklookdemo.fragment;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
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
import com.wly.looklookdemo.TopNews.TopNewsListAdapter;
import com.wly.looklookdemo.TopNews.TopPhotoDetailActivity;
import com.wly.looklookdemo.TopNews.presenter.TopNewsPresenter;
import com.wly.looklookdemo.TopNews.presenter.TopNewsPresenterImpl;
import com.wly.looklookdemo.TopNews.view.TopNewsView;
import com.wly.looklookdemo.base.BaseFragment;
import com.wly.looklookdemo.bean.TopNewsBean;
import com.wly.looklookdemo.news.NewsDetailActivity;
import com.wly.looklookdemo.utils.NetWorkCheckUtil;
import com.wly.looklookdemo.utils.Urls;

import java.util.List;

/**
 * Created by Candy on 2016/9/2.
 */
public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener , TopNewsView{

    public SwipeRefreshLayout swipeLayout;

    public RecyclerView mTopNewsLayout;

    public TopNewsPresenter presenter;

    public ProgressBar progress;

    public TopNewsListAdapter topAdapter;

    public ViewStub viewStub;

    public LinearLayoutManager mLinearLayoutManager;

    public RecyclerView.OnScrollListener loadingMoreListener;

    public boolean loading;

    public int num = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wangyi;
    }

    @Override
    protected void findView() {
        viewStub = (ViewStub) convertView.findViewById(R.id.viewsub);
        swipeLayout = (SwipeRefreshLayout) convertView.findViewById(R.id.swipe);
        mTopNewsLayout = (RecyclerView) convertView.findViewById(R.id.recycler_top_news_item);
        progress = (ProgressBar) convertView.findViewById(R.id.progress);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        initRecycler();
        initSwipe();
    }

    public void initRecycler(){

        initialListener();
        mTopNewsLayout.setLayoutManager(mLinearLayoutManager);
        mTopNewsLayout.setItemAnimator(new DefaultItemAnimator());
//        mTopNewsLayout.setOnScrollListener(loadingMoreListener);
    }

    public void initSwipe(){

        swipeLayout.setColorSchemeResources(R.color.colorAccent ,
                R.color.app_red ,
                R.color.app_red_dark ,
                R.color.app_red_light);
    }

    @Override
    protected void setListener() {

        swipeLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initialize() {

        if(viewStub == null){
            viewStub.inflate();
        }

        if(NetWorkCheckUtil.isNetWorkConnected(getContext())){

            viewStub.setVisibility(View.GONE);
            presenter = new TopNewsPresenterImpl(getActivity() , this);
            presenter.loadTopNewsList(Urls.ENTERTAINMENT_ID);

        }else {
            viewStub.setVisibility(View.VISIBLE);
            Button reload = (Button) convertView.findViewById(R.id.reload);
            reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }

    private void initialListener() {

        loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //向下滚动
                {
                    int visibleItemCount = mLinearLayoutManager.getChildCount();
                    int totalItemCount = mLinearLayoutManager.getItemCount();
                    int pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        loadMore();
                    }
                }
            }
        };

    }

    public void loadMore(){
        topAdapter.loadingStart();
        num += 20;
        presenter.loadMoreNewList(Urls.ENTERTAINMENT_ID , num);
    }

    @Override
    public void onRefresh() {
        initialize();
    }

    @Override
    public void showProgress() {
        if(swipeLayout.isRefreshing()){
            progress.setVisibility(View.GONE);
        }else{
            progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {

        progress.setVisibility(View.GONE);
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void showMsg() {

    }

    @Override
    public void addTopNewsList(final List<TopNewsBean> been) {

        topAdapter = new TopNewsListAdapter(getActivity());
        topAdapter.addItems(been);
        mTopNewsLayout.setAdapter(topAdapter);
        topAdapter.setItemListener(new TopNewsListAdapter.onTopNewsClickListener() {
            @Override
            public void onItemClicked(View view, int position, boolean isPhoto) {
                TopNewsBean bean = been.get(position);
                if(isPhoto){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news_detail" , bean);
                    Intent intent = new Intent(mContext , TopPhotoDetailActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("News_ID" , String.valueOf(bean.getPostid()));
                    bundle.putString("News_Image" , bean.getImgsrc());
                    bundle.putString("News_Title" , bean.getTitle());
                    bundle.putString("News_Type" , "2");
                    Intent intent = new Intent(mContext , NewsDetailActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
