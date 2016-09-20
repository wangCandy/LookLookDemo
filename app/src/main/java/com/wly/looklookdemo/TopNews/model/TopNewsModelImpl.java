package com.wly.looklookdemo.TopNews.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wly.looklookdemo.bean.TopNewsBean;
import com.wly.looklookdemo.utils.JsonUtils;
import com.wly.looklookdemo.utils.NetWorkCheckUtil;
import com.wly.looklookdemo.utils.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Candy on 2016/9/9.
 */
public class TopNewsModelImpl implements TopNewsModel {

    public static final String TAG = TopNewsModelImpl.class.getSimpleName();

    @Override
    public void loadTopNewsList(Context context , final String id, final onTopNewsListListener listener) {

        if(!NetWorkCheckUtil.isNetWorkConnected(context)){
            listener.onFailure("暂无网络" , null);
        }else{

            String url = Urls.NEWS_DETAIL + id + "/" + "0" + Urls.END_URL;

            final List<TopNewsBean> topNewsBeans = new ArrayList<TopNewsBean>();
            Ion.with(context).load(Urls.METHOD , url)
                    .setTimeout(Urls.TIME_OUT)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {

                            Log.d(TAG, "onCompleted: result=" + result.toString());
                            JsonArray newsArray = result.getAsJsonArray(id);
                            for (int i = 0; i < newsArray.size(); i++) {

                                TopNewsBean bean = (TopNewsBean) JsonUtils.fromJson(newsArray.get(i).toString() , TopNewsBean.class);
                                topNewsBeans.add(bean);
                            }
                            listener.onSuccess(topNewsBeans);
                        }
                    });

        }
    }

    public interface onTopNewsListListener{

        void onSuccess(List<TopNewsBean> beans);

        void onFailure(String msg , Exception e);
    }
}
