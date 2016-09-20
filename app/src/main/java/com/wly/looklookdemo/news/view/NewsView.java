package com.wly.looklookdemo.news.view;

import com.wly.looklookdemo.bean.NewsBean;

import java.util.List;

/**
 * Created by Candy on 2016/9/8.
 */
public interface NewsView {

    void addData(List<NewsBean> beans);

    void showProgress();

    void hideProgress();

    void onShowFailMsg();
}
