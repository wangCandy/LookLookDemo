package com.wly.looklookdemo.TopNews.presenter;

import android.content.Context;

import com.wly.looklookdemo.TopNews.model.TopNewsModel;
import com.wly.looklookdemo.TopNews.model.TopNewsModelImpl;
import com.wly.looklookdemo.TopNews.view.TopNewsView;
import com.wly.looklookdemo.bean.TopNewsBean;

import java.util.List;

/**
 * Created by Candy on 2016/9/9.
 */
public class TopNewsPresenterImpl implements TopNewsPresenter , TopNewsModelImpl.onTopNewsListListener{

    public TopNewsModel model;

    public Context mContext;

    public TopNewsView myView;

    public TopNewsPresenterImpl(Context context , TopNewsView myView){

        this.mContext = context;
        this.myView = myView;
        this.model = new TopNewsModelImpl();
    }

    @Override
    public void loadTopNewsList(String id) {

        myView.showProgress();
        model.loadTopNewsList(mContext , id , 0 , this);
    }

    @Override
    public void loadMoreNewList(String id , int num) {

        model.loadTopNewsList(mContext , id  , num , this);
    }

    @Override
    public void onSuccess(List<TopNewsBean> beans) {

        myView.addTopNewsList(beans);
        myView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {

        myView.hideProgress();
        myView.showMsg();
    }
}
