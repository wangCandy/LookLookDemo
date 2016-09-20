package com.wly.looklookdemo.news.model;

import android.content.Context;

/**
 * Created by Candy on 2016/9/8.
 */
public interface NewsDetailModel {

    void loadNewsDetail(Context context , String type , String id , NewsDetailModelImpl.onNewsDetailListener listener);
}
