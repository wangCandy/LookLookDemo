package com.wly.looklookdemo.news.model;

import android.content.Context;

/**
 * Created by Candy on 2016/9/7.
 */
public interface NewsModel {

    void loadNewsList(Context context , NewsModelImpl.onNewsListLoadListener listener);
}
