package com.wly.looklookdemo.news.presenter;

import android.content.Context;

import com.wly.looklookdemo.bean.NewsBean;
import com.wly.looklookdemo.news.model.NewsModel;
import com.wly.looklookdemo.news.model.NewsModelImpl;
import com.wly.looklookdemo.news.view.NewsView;

import java.util.List;

/**
 * Created by Candy on 2016/9/8.
 */
public class NewsPresenterImpl implements NewsPresenter , NewsModelImpl.onNewsListLoadListener{

    public NewsView myView;
    public NewsModel newsModel;
    public Context mContext;

    public NewsPresenterImpl(Context context , NewsView view){

        mContext = context;
        this.newsModel = new NewsModelImpl();
        myView = view;
    }

    @Override
    public void loadNewsList() {

        myView.showProgress();
        newsModel.loadNewsList(mContext , this);
    }

    @Override
    public void onSuccess(List<NewsBean> bean) {

        myView.addData(bean);
        myView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {

        myView.hideProgress();
        myView.onShowFailMsg();
    }
}
