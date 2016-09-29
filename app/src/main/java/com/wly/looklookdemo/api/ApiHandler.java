package com.wly.looklookdemo.api;

/**
 * Created by Candy on 2016/9/21.
 */
public interface ApiHandler {

    void onSuccess(String jsonResult);

    void onFailure(String errorMsg , String errorCode);
}
