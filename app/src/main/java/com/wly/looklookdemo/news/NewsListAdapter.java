package com.wly.looklookdemo.news;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wly.looklookdemo.R;
import com.wly.looklookdemo.bean.NewsBean;
import com.wly.looklookdemo.utils.ImageUtils;

import java.util.List;

/**
 * Created by Candy on 2016/9/8.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    public static final String TAG = NewsListAdapter.class.getSimpleName();

    public List<NewsBean> newsBeans;

    public Context mContext;

    public NewsListAdapter(Context context , List<NewsBean> beans){

        this.mContext = context;
        this.newsBeans = beans;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_news_layout , null);
        NewsViewHolder holder = new NewsViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        if(newsBeans != null && newsBeans.size() > 0){

            final NewsBean bean = newsBeans.get(position);

            holder.title.setText(bean.getTitle());
            ImageUtils.loadImage(mContext , bean.getImages().get(0).toString() , holder.titleImg);

            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "onClick: id=" + bean.getId() + "images" + bean.getImages().get(0) + "title=" + bean.getTitle());

                    Bundle bundle = new Bundle();
                    bundle.putString("News_ID" , String.valueOf(bean.getId()));
                    bundle.putString("News_Image" , bean.getImages().get(0));
                    bundle.putString("News_Title" , bean.getTitle());
                    bundle.putString("News_Type" , "1");
                    Intent intent = new Intent(mContext , NewsDetailActivity.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return newsBeans.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        ImageView titleImg;

        CardView container;

        TextView digest;

        TextView pTime;

        public NewsViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_title);

            titleImg = (ImageView) itemView.findViewById(R.id.iv_title);

            container = (CardView) itemView.findViewById(R.id.rl_news_item);

            digest = (TextView) itemView.findViewById(R.id.tv_digest);

            pTime = (TextView) itemView.findViewById(R.id.tv_pTime);
        }
    }
}
