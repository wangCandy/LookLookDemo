package com.wly.looklookdemo.news.presenter;

import android.content.Context;

import com.wly.looklookdemo.bean.NewsDetailBean;
import com.wly.looklookdemo.news.model.NewsDetailModel;
import com.wly.looklookdemo.news.model.NewsDetailModelImpl;
import com.wly.looklookdemo.news.view.NewsDetailView;

/**
 * Created by Candy on 2016/9/8.
 */
public class NewsDetailPresenterImpl implements NewsDetailPresenter , NewsDetailModelImpl.onNewsDetailListener{

    public Context context;
    public NewsDetailModel model;
    public NewsDetailView myView;

    public NewsDetailPresenterImpl(NewsDetailView view , Context context){

        this.context = context;
        this.myView = view;
        model = new NewsDetailModelImpl();
    }

    @Override
    public void loadNewsDetail(String id , String newsType) {
        myView.showProgress();
        model.loadNewsDetail(context , newsType , id , this);
    }

    @Override
    public void onSuccess(NewsDetailBean bean) {

        myView.addNewsDetail(bean);
        myView.hideProgress();
    }

    @Override
    public void onFailure(String errorMsg, Exception e) {

        myView.hideProgress();
        myView.showFailMsg();
    }
}
