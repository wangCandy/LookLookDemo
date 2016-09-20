package com.wly.looklookdemo.news.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wly.looklookdemo.bean.NewsDetailBean;
import com.wly.looklookdemo.utils.JsonUtils;
import com.wly.looklookdemo.utils.NetWorkCheckUtil;
import com.wly.looklookdemo.utils.Urls;

/**
 * Created by Candy on 2016/9/8.
 */
public class NewsDetailModelImpl implements NewsDetailModel{

    public static final String TAG = NewsDetailModelImpl.class.getSimpleName();

    @Override
    public void loadNewsDetail(Context context , final String newsType ,final String id, final onNewsDetailListener listener) {

        if(!NetWorkCheckUtil.isNetWorkConnected(context)){
            listener.onFailure("暂无网络" , null);
        }else{

            String url = getUrl(newsType , id);
            Log.d(TAG, "loadNewsDetail: url=" + url);
            Ion.with(context).load(Urls.METHOD , url)
                    .setTimeout(Urls.TIME_OUT)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            switch (newsType){
                                case "1":
                                    Log.d(TAG, "onCompleted: result=" + result.toString());
                                    NewsDetailBean bean = (NewsDetailBean) JsonUtils.fromJson(result.toString() , NewsDetailBean.class);
                                    listener.onSuccess(bean);
                                    break;
                                case "2":
                                    Log.d(TAG, "onCompleted: result=" + result.toString());
                                    NewsDetailBean bean2 = (NewsDetailBean) JsonUtils.fromJson(result.get(id).toString() , NewsDetailBean.class);
                                    listener.onSuccess(bean2);
                                    break;

                            }
                        }
                    });
        }
    }
    public String getUrl(String type , String id){

        String url = null;
        switch (type){

            case "1":
                //知乎详情
                url = Urls.ZHIHU_BASE + id;
                break;
            case "2":
                url = Urls.NETEAST_HOST + id + Urls.ENDDETAIL_URL;
                break;
            default:
                url = null;
                break;
        }

        return url;
    }

    public interface onNewsDetailListener{

        void onSuccess(NewsDetailBean bean);

        void onFailure(String errorMsg , Exception e);
    }
}
