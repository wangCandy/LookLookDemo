package com.wly.looklookdemo.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wly.looklookdemo.R;
import com.wly.looklookdemo.activities.TopPhotoDetailActivity;
import com.wly.looklookdemo.bean.ImageBean;
import com.wly.looklookdemo.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Candy on 2016/9/21.
 */
public class LookImageAdapter extends RecyclerView.Adapter<LookImageAdapter.ViewHolder> {
    public List<ImageBean> datas = new ArrayList<ImageBean>();
    public Context mContext;
    public LookImageAdapter(List<ImageBean> datas , Context context) {
        this.datas = datas;
        this.mContext = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);
        return new ViewHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ImageUtils.loadImage(mContext , datas.get(position).getUrl() , holder.mImage);

        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt("News_type" , 1);
                bundle.putString("Image_url" , datas.get(position).getUrl());
                Intent intent = new Intent(mContext , TopPhotoDetailActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;
        public ViewHolder(View view){
            super(view);
            mImage = (ImageView) view.findViewById(R.id.image);
        }
    }
}
