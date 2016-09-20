package com.wly.looklookdemo.news.view;

import com.wly.looklookdemo.bean.NewsDetailBean;

/**
 * Created by Candy on 2016/9/8.
 */
public interface NewsDetailView {

    void showProgress();

    void hideProgress();

    void showFailMsg();

    void addNewsDetail(NewsDetailBean bean);
}
