package com.wly.looklookdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
public class NewsFragment extends BaseFragment implements XRecyclerView.LoadingListener{

    public XRecyclerView mTopNewsLayout;

    public ProgressBar progress;

    public TopNewsListAdapter topAdapter;

    public ViewStub viewStub;

    public LinearLayoutManager mLinearLayoutManager;

    public List<TopNewsBean> topNewsBeen = new ArrayList<TopNewsBean>();

    public int num = 0;

    public Handler mHandler  = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<TopNewsBean> items = (ArrayList<TopNewsBean>) msg.obj;

            switch (msg.arg1){
                case 0:
                    topNewsBeen.clear();
                    topNewsBeen.addAll(items);
                    break;
                case 1:
                    topNewsBeen.addAll(0 , items);
                    mTopNewsLayout.reset();
                    break;
                case 2:
                    topNewsBeen.addAll(items);
                    mTopNewsLayout.reset();
                    break;
            }
            topAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wangyi;
    }

    @Override
    protected void findView() {
        viewStub = (ViewStub) convertView.findViewById(R.id.viewsub);
        mTopNewsLayout = (XRecyclerView) convertView.findViewById(R.id.recycler_top_news_item);
        progress = (ProgressBar) convertView.findViewById(R.id.progress);
    }

    public void initRecycler(){
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mTopNewsLayout.setLayoutManager(mLinearLayoutManager);
        mTopNewsLayout.setLoadingMoreProgressStyle(ProgressStyle.LineScale);
        mTopNewsLayout.setRefreshProgressStyle(ProgressStyle.LineScale);
        mTopNewsLayout.setItemAnimator(new DefaultItemAnimator());
        topAdapter = new TopNewsListAdapter(getActivity() , topNewsBeen);
        mTopNewsLayout.setAdapter(topAdapter);
        mTopNewsLayout.setRefreshing(true);
        mTopNewsLayout.setLoadingListener(this);
        topAdapter.setItemListener(new TopNewsListAdapter.onTopNewsClickListener() {
            @Override
            public void onItemClicked(View view, int position, boolean isPhoto) {
                TopNewsBean bean = topNewsBeen.get(position - 1);
                if(isPhoto){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news_detail" , bean);
                    bundle.putInt("News_type" , 0);
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
            mTopNewsLayout.setVisibility(View.VISIBLE);
            initRecycler();
            onRefresh();

        }else {
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

    public void initData(int id , final int upOrDown){

        String url = Urls.NEWS_DETAIL + Urls.ENTERTAINMENT_ID + "/" + id + Urls.END_URL;
        LookAppApiClient.sendRequest(false, mContext,url , new ApiHandler() {
            @Override
            public void onSuccess(String jsonResult) {
                Log.d(TAG, "onCompleted: result=" + jsonResult.toString());
                List<TopNewsBean> items = new ArrayList<TopNewsBean>();
                try {
                    JSONObject object = new JSONObject(jsonResult);
                    JSONArray newsArray = object.getJSONArray(Urls.ENTERTAINMENT_ID);
                    for (int i = 0; i < newsArray.length(); i++) {

                        TopNewsBean bean = (TopNewsBean) JsonUtils.fromJson(newsArray.get(i).toString() , TopNewsBean.class);
                        items.add(bean);
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.arg1 = upOrDown;
                    msg.obj = items;
                    mHandler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    @Override
    public void onRefresh() {
        num = 0;
        initData(num , 0);
    }

    @Override
    public void onLoadMore() {
        num += 20;
        initData(num , 2);
    }
}
