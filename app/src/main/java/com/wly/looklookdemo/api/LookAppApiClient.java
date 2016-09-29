package com.wly.looklookdemo.api;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wly.looklookdemo.utils.NetWorkCheckUtil;
import com.wly.looklookdemo.utils.Urls;
import com.wly.looklookdemo.widget.LoadingDialog;

/**
 * Created by Candy on 2016/9/21.
 */
public class LookAppApiClient {

    public static final String CODE_500 = "500";//服务器不通

    public static final String CODE_1000 = "1000"; //无网络

    public static final String TAG = LookAppApiClient.class.getSimpleName();

    public static void sendRequest(boolean isShowProgress , Context context , final String url , final ApiHandler handler){

        if (context != null) {

            if(!NetWorkCheckUtil.isNetWorkConnected(context)){
                handler.onFailure("暂无网络" , CODE_1000);
            }
            LoadingDialog dialog = new LoadingDialog(context);
            try {
                if (isShowProgress) {
                    dialog.dismiss();
                    dialog.show();
                }
            } catch (Exception e) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
            }

            createIon(isShowProgress , context , url , handler , dialog);
        }
    }

    public static void createIon(final boolean isProgress , final Context context , final String url , final ApiHandler handler ,final LoadingDialog dialog){

        Ion.with(context).load(url)
                .setTimeout(Urls.TIME_OUT)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if (dialog != null){
                            dialog.dismiss();
                        }
                        if(result == null){
                            catchException(isProgress , context , handler , CODE_500 , "接口异常");
                            return;
                        }else{
                            handler.onSuccess(result.toString());
                        }
                    }
                });
    }

    /**
     * 异常处理
     */
    private static void catchException(boolean isProgress, Context context, ApiHandler handler, String errCode, String errMessage) {
        Log.e(TAG, context.getClass().getSimpleName()+"接口异常--->>>" + errMessage + "(" + errCode + ")");
        handler.onFailure(errMessage , errCode);
    }
}
