package com.wly.looklookdemo.utils;

/**
 * Created by Candy on 2016/9/2.
 */
public class Urls {

    public static final String METHOD = "GET";

    public static final String ZHIHU_BASE = "http://news-at.zhihu.com/api/4/news/";

    public static final String LATEST = ZHIHU_BASE + "latest";

    public static final String NETEAST_HOST = "http://c.m.163.com/nc/article/";
    public static final String END_URL = "-20.html";
    public static final String ENDDETAIL_URL = "/full.html";

    // 新闻详情
    public static final String NEWS_DETAIL = NETEAST_HOST + "headline/";

    // 头条TYPE
    public static final String HEADLINE_TYPE = "headline";
    // 房产TYPE
    public static final String HOUSE_TYPE = "house";
    // 其他TYPE
    public static final String OTHER_TYPE = "list";

    // 娱乐
    public static final String ENTERTAINMENT_ID = "T1348648517839";

    public static final int TIME_OUT = 10 * 1000;
}
