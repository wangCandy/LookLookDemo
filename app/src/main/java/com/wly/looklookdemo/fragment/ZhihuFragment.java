package com.wly.looklookdemo.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wly.looklookdemo.R;
import com.wly.looklookdemo.api.ApiHandler;
import com.wly.looklookdemo.api.LookAppApiClient;
import com.wly.looklookdemo.base.BaseFragment;
import com.wly.looklookdemo.bean.NewsBean;
import com.wly.looklookdemo.news.NewsJsonUtils;
import com.wly.looklookdemo.news.NewsListAdapter;
import com.wly.looklookdemo.utils.NetWorkCheckUtil;
import com.wly.looklookdemo.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Candy on 2016/9/2.
 */
public class ZhihuFragment extends BaseFragment implements XRecyclerView.LoadingListener{

    public static final String TAG = ZhihuFragment.class.getSimpleName();

    public NewsListAdapter adapter;

    public Context mContext;

    public XRecyclerView newsRecycler;

    public String url;

    public ViewStub viewStub;

    public int date;

    public List<NewsBean> newsItems = new ArrayList<NewsBean>();

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            List<NewsBean> items = (ArrayList<NewsBean>) msg.obj;
            switch (msg.arg1){
                case 0:
                    newsItems.clear();
                    newsItems.addAll(items);
                    break;
                case 1:
                    newsItems.addAll(0 , items);
                    newsRecycler.reset();
                    break;
                case 2:
                    newsItems.addAll(items);
                    newsRecycler.reset();
                    break;
            }
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhihu;
    }

    @Override
    protected void findView() {
        mContext = getActivity();
        newsRecycler = (XRecyclerView) convertView.findViewById(R.id.recycler_news_item);
        viewStub = (ViewStub) convertView.findViewById(R.id.viewsub);
    }

    public void initRecycler(){

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        newsRecycler.setLayoutManager(manager);
        newsRecycler.setRefreshProgressStyle(ProgressStyle.BallBeat);
        newsRecycler.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        newsRecycler.setArrowImageView(R.mipmap.iconfont_downgrey);
        newsRecycler.setItemAnimator(new DefaultItemAnimator());
        adapter = new NewsListAdapter(mContext , newsItems);
        newsRecycler.setAdapter(adapter);
        newsRecycler.setLoadingListener(this);
        newsRecycler.setRefreshing(true);
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
            newsRecycler.setVisibility(View.VISIBLE);
            initRecycler();
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

    public void initData(String url , final int upOrDown){
        LookAppApiClient.sendRequest(false, mContext, url, new ApiHandler() {
            @Override
            public void onSuccess(String jsonResult) {

                try {
                    JSONObject object = new JSONObject(jsonResult);
                    date = Integer.parseInt(object.getString("date"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<NewsBean> newsList = NewsJsonUtils.readNewsListBean(jsonResult);
                Message msg = mHandler.obtainMessage();
                msg.arg1 = upOrDown;
                msg.obj = newsList;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    @Override
    public void onRefresh() {
        url = Urls.LATEST;
        initData(url , 0);
        newsRecycler.refreshComplete();
    }

    @Override
    public void onLoadMore() {
        url = Urls.BEFORE + date;
        initData(url , 2);
        newsRecycler.loadMoreComplete();
    }
}
