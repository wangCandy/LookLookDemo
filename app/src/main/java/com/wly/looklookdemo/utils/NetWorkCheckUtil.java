package com.wly.looklookdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Candy on 2016/9/2.
 */
public class NetWorkCheckUtil {

    public static boolean isNetWorkConnected(Context context){

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();
        return info!=null && info.isConnectedOrConnecting();
    }
}
