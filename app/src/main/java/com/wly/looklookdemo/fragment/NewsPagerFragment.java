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
import com.jcodecraeer.xrecyclerview.adapter.SlideInRightAnimatorAdapter;
import com.jcodecraeer.xrecyclerview.adapter.SwingBottomInAnimationAdapter;
import com.wly.looklookdemo.R;
import com.wly.looklookdemo.activities.TopPhotoDetailActivity;
import com.wly.looklookdemo.adapter.TopNewsListAdapter;
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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Candy on 2016/9/23.
 */
public class NewsPagerFragment extends BaseFragment implements XRecyclerView.LoadingListener{

    @Bind(R.id.recycler_top_news_item)
    public XRecyclerView mTopNewsLayout;

    public TopNewsListAdapter topAdapter;

    @Bind(R.id.viewsub)
    public ViewStub viewStub;

    public LinearLayoutManager mLinearLayoutManager;

    public List<TopNewsBean> topNewsBeen = new ArrayList<TopNewsBean>();

    public String id;

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

    public static NewsPagerFragment newInstance(String id){
        NewsPagerFragment fragment = new NewsPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Tabs_id" , id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_pager;
    }

    @Override
    protected void findView() {
        ButterKnife.bind(this , convertView);
    }

    public void initRecycler(){
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mTopNewsLayout.setLayoutManager(mLinearLayoutManager);
        mTopNewsLayout.setLoadingMoreProgressStyle(ProgressStyle.LineScale);
        mTopNewsLayout.setRefreshProgressStyle(ProgressStyle.LineScale);
        mTopNewsLayout.setItemAnimator(new DefaultItemAnimator());
        topAdapter = new TopNewsListAdapter(getActivity() , topNewsBeen);
        SlideInRightAnimatorAdapter animationAdapter = new SlideInRightAnimatorAdapter(topAdapter , mTopNewsLayout);
        mTopNewsLayout.setAdapter(animationAdapter);
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

        id = getArguments().getString("Tabs_id");
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

    public void initData(final String urlId , int id , final int upOrDown){

        final String url = Urls.NEWS_DETAIL + urlId + "/" + id + Urls.END_URL;
        LookAppApiClient.sendRequest(false, mContext,url , new ApiHandler() {
            @Override
            public void onSuccess(String jsonResult) {
                Log.d(TAG, "onCompleted: result=" + jsonResult.toString());
                List<TopNewsBean> items = new ArrayList<TopNewsBean>();
                try {
                    JSONObject object = new JSONObject(jsonResult);
                    JSONArray newsArray = object.getJSONArray(urlId);
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
        initData(id , num , 0);
        mTopNewsLayout.reset();
    }

    @Override
    public void onLoadMore() {
        num += 20;
        initData(id , num , 2);
        mTopNewsLayout.reset();
    }
}
