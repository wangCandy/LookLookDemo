package com.wly.looklookdemo.TopNews.view;

import com.wly.looklookdemo.bean.TopNewsBean;

import java.util.List;

/**
 * Created by Candy on 2016/9/9.
 */
public interface TopNewsView {

    void showProgress();

    void hideProgress();

    void showMsg();

    void addTopNewsList(List<TopNewsBean> been);
}
