package com.wly.looklookdemo.api;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wly.looklookdemo.utils.NetWorkCheckUtil;
import com.wly.looklookdemo.utils.Urls;

/**
 * Created by Candy on 2016/9/21.
 */
public class LookAppApiClient {

    public static final String TAG = LookAppApiClient.class.getSimpleName();

    public static void sendRequest(boolean isShowProgress , Context context , final String url , final ApiHandler handler){

        if(isShowProgress){

        }
        if(!NetWorkCheckUtil.isNetWorkConnected(context)){
            handler.onFailure("暂无网络");
        }else{
            Ion.with(context).load(url)
                    .setTimeout(Urls.TIME_OUT)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            Log.d(TAG, "onCompleted: url=" + url);
                            Log.d(TAG, "onCompleted: result=" + result.toString());
                            handler.onSuccess(result.toString());
                        }

                    });
        }
    }
}
