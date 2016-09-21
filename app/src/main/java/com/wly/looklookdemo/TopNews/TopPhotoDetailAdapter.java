package com.wly.looklookdemo.TopNews;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Candy on 2016/9/21.
 */
public class TopPhotoDetailAdapter extends PagerAdapter {

    List<ImageView> mImageData;

    public TopPhotoDetailAdapter(List<ImageView> data){

        this.mImageData = data;
    }

    @Override
    public int getCount() {
        return mImageData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageData.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(mImageData.get(position));
        return mImageData.get(position);
    }
}
