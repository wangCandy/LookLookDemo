package com.wly.looklookdemo.news.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wly.looklookdemo.bean.NewsBean;
import com.wly.looklookdemo.news.NewsJsonUtils;
import com.wly.looklookdemo.utils.NetWorkCheckUtil;
import com.wly.looklookdemo.utils.Urls;

import java.util.List;

/**
 * Created by Candy on 2016/9/7.
 */
public class NewsModelImpl implements NewsModel {

    public static final String TAG = NewsModelImpl.class.getSimpleName();

    @Override
    public void loadNewsList(Context context, final onNewsListLoadListener listener) {

        if(!NetWorkCheckUtil.isNetWorkConnected(context)){

            listener.onFailure("暂无网络" , null);
        }else{

            Ion.with(context).load(Urls.METHOD , Urls.LATEST)
                    .setTimeout(Urls.TIME_OUT)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            Log.d(TAG, "onCompleted: result=" + result.toString());

                            List<NewsBean> newsList = NewsJsonUtils.readNewsListBean(result.toString());
                            listener.onSuccess(newsList);
                        }
                    });
        }
    }


    public interface onNewsListLoadListener{

        void onSuccess(List<NewsBean> bean);
        void onFailure(String msg , Exception e);
    }
}
