package com.wly.looklookdemo.bean;

import java.util.List;

/**
 * Created by Candy on 2016/9/8.
 */
public class NewsBean {


    /**
     * images : ["http://pic1.zhimg.com/33a8483bef0fb4f410be12c300004c64.jpg"]
     * type : 0
     * id : 8767385
     * ga_prefix : 090809
     * title : 「业务能力」一流的电信诈骗集团，就是用的这几招
     */

    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
