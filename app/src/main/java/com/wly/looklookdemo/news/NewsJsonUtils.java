package com.wly.looklookdemo.news;

import com.google.gson.JsonArray;
import com.wly.looklookdemo.bean.NewsBean;
import com.wly.looklookdemo.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Candy on 2016/9/8.
 */
public class NewsJsonUtils {

    public static List<NewsBean> readNewsListBean(String str){

        List<NewsBean> newsList = new ArrayList<NewsBean>();
        if (str == null) {
            return null;
        }else{
            try {
                JSONObject object = new JSONObject(str);
                JSONArray newsArray = object.getJSONArray("stories");
                for (int i = 0; i < newsArray.length(); i++) {

                    NewsBean bean = (NewsBean) JsonUtils.fromJson(newsArray.get(i).toString() , NewsBean.class);
                    newsList.add(bean);
                }
                return newsList;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return newsList;
        }
    }
}
