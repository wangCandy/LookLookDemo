package com.wly.looklookdemo.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wly.looklookdemo.adapter.LookImageAdapter;
import com.wly.looklookdemo.R;
import com.wly.looklookdemo.api.ApiHandler;
import com.wly.looklookdemo.api.LookAppApiClient;
import com.wly.looklookdemo.base.BaseFragment;
import com.wly.looklookdemo.bean.ImageBean;
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
public class LookFragment extends BaseFragment implements XRecyclerView.LoadingListener{

    public XRecyclerView mLookNewsLayout;

    public LookImageAdapter mAdapter;

    public List<ImageBean> imageBeen = new ArrayList<ImageBean>();

    public ViewStub viewStub;

    public Button reload;

    public int id = 1;

    public Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            List<ImageBean> list = (ArrayList<ImageBean>) msg.obj;
            switch (msg.arg1) {

                case 0://刷新
                    imageBeen.clear();
                    imageBeen.addAll(list);
                    break;
                case 1:
                    imageBeen.addAll(0, list);
                    mLookNewsLayout.reset();
                    break;
                case 2://加载更多
                    imageBeen.addAll(list);
                    mLookNewsLayout.reset();
                    break;
            }
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected void findView() {

        viewStub = (ViewStub) convertView.findViewById(R.id.viewsub);
        reload = (Button) convertView.findViewById(R.id.reload);
        mLookNewsLayout = (XRecyclerView) convertView.findViewById(R.id.look_item);
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
            mLookNewsLayout.setVisibility(View.VISIBLE);
            initRecycler();

        }else{
            mLookNewsLayout.setVisibility(View.GONE);
            viewStub.setVisibility(View.VISIBLE);
            reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initialize();
                }
            });
        }
    }

    public void loadImageList(int id , final int upOrDown){

        final List<ImageBean> items = new ArrayList<ImageBean>();
        LookAppApiClient.sendRequest(false, mContext, Urls.IMAGE_BASE + id, new ApiHandler() {
            @Override
            public void onSuccess(String jsonResult) {
                try {
                    JSONObject object = new JSONObject(jsonResult);
                    JSONArray dataArray = object.getJSONArray("results");
                    for (int i = 0; i < dataArray.length(); i++) {

                        ImageBean item = (ImageBean) JsonUtils.fromJson(dataArray.get(i).toString() , ImageBean.class);
                        items.add(item);
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

    public void initRecycler(){

        mLookNewsLayout.setLayoutManager(new LinearLayoutManager(mContext));
        mLookNewsLayout.setRefreshProgressStyle(ProgressStyle.BallScale);
        mLookNewsLayout.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        mLookNewsLayout.setArrowImageView(R.mipmap.iconfont_downgrey);
        mAdapter = new LookImageAdapter(imageBeen , mContext);
        mLookNewsLayout.setAdapter(mAdapter);
        mLookNewsLayout.setLoadingListener(this);
        mLookNewsLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        id = 1;
        loadImageList(1 , 0);
        mLookNewsLayout.refreshComplete();
    }

    @Override
    public void onLoadMore() {

        id++;
        loadImageList(id , 2);
    }
}
