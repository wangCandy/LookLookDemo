package com.wly.looklookdemo.TopNews.model;

import android.content.Context;

/**
 * Created by Candy on 2016/9/9.
 */
public interface TopNewsModel {

    void loadTopNewsList(Context context , String id , TopNewsModelImpl.onTopNewsListListener listener);
}
