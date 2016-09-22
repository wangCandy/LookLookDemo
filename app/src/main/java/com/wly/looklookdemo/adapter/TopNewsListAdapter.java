package com.wly.looklookdemo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.wly.looklookdemo.R;
import com.wly.looklookdemo.bean.TopNewsBean;
import com.wly.looklookdemo.utils.ImageUtils;

import java.util.List;

/**
 * Created by Candy on 2016/9/9.
 */
public class TopNewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int TYPE_ITEM = 0;

    public static final int TYPE_PHOTO = 1;

    private static final String TAG = TopNewsListAdapter.class.getSimpleName();

    public List<TopNewsBean> beanList;

    public Context mContext;

    public onTopNewsClickListener itemListener;

    public TopNewsListAdapter(Context context , List<TopNewsBean> list){

        this.mContext = context;
        this.beanList = list;
    }

    public void setItemListener(onTopNewsClickListener listener){

        this.itemListener = listener;
    }


    public interface onTopNewsClickListener{

        void onItemClicked(View view , int position , boolean isPhoto);
    }

    public void setOnItemClickListener(final RecyclerView.ViewHolder itemView , final boolean isPhoto){

        if(itemListener != null){

            itemView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    itemListener.onItemClicked(itemView.itemView , itemView.getLayoutPosition() , isPhoto);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case TYPE_ITEM:
                View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_news_layout , parent , false);
                MyViewHolder holder = new MyViewHolder(itemView);
                setOnItemClickListener(holder , false);
                return holder;
            case TYPE_PHOTO:
                View photoItem = LayoutInflater.from(mContext).inflate(R.layout.item_news_photo , parent , false);
                PhotoViewHolder photoHolder = new PhotoViewHolder(photoItem);
                setOnItemClickListener(photoHolder , true);
                return photoHolder;
            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int type = getItemViewType(position);
        switch (type){
            case TYPE_ITEM:
                setValues((MyViewHolder) holder , position);
                break;
            case TYPE_PHOTO:
                setValues((PhotoViewHolder) holder , position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }
    private void setValues(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            setItemValues((MyViewHolder) holder, position);
        } else if (holder instanceof PhotoViewHolder)
            setPhotoItemValues((PhotoViewHolder) holder, position);
    }
    public void setItemValues(MyViewHolder holder , int position){

        final TopNewsBean bean = beanList.get(position);
        if(bean!= null){
            holder.title.setText(bean.getTitle());
            ImageUtils.loadImage(mContext , bean.getImgsrc() , holder.titleImg);
            holder.pTime.setText(bean.getPtime());
            holder.digest.setText(bean.getDigest());
        }
    }

    public void setPhotoItemValues(PhotoViewHolder holder , int position){

        TopNewsBean bean = beanList.get(position);
        setPhotoText(bean , holder);
        setPhotoImage(bean , holder);

    }

    public void setPhotoText(TopNewsBean bean , PhotoViewHolder holder){

        holder.photoTitle.setText(bean.getTitle());
        holder.pTime.setText(bean.getPtime());
    }

    public void setPhotoImage(TopNewsBean bean , PhotoViewHolder holder){

        String leftImg = null;
        String middleImg = null;
        String rightImg = null;

        if(bean.getAds() != null){

            List<TopNewsBean.AdsBean> adsBeen = bean.getAds();
            int count = adsBeen.size();
            if(count >= 3){
                leftImg = adsBeen.get(0).getImgsrc();
                middleImg = adsBeen.get(1).getImgsrc();
                rightImg = adsBeen.get(2).getImgsrc();
            }else if(count >= 2){

                leftImg = adsBeen.get(0).getImgsrc();
                rightImg = adsBeen.get(1).getImgsrc();
            }else if(count >= 1){

                leftImg = adsBeen.get(0).getImgsrc();
            }
        }else if(bean.getImgextra() != null){

            List<TopNewsBean.ImgextraBean> adsBeen = bean.getImgextra();
            int count = adsBeen.size();
            if(count >= 3){
                leftImg = adsBeen.get(0).getImgsrc();
                middleImg = adsBeen.get(1).getImgsrc();
                rightImg = adsBeen.get(2).getImgsrc();
            }else if(count >= 2){

                leftImg = adsBeen.get(0).getImgsrc();
                rightImg = adsBeen.get(1).getImgsrc();
            }else if(count >= 1){

                leftImg = adsBeen.get(0).getImgsrc();
            }
        }else{

            leftImg = bean.getImgsrc();
        }
        setPhotoImageView(holder , leftImg , middleImg , rightImg);
    }

    public void setPhotoImageView(PhotoViewHolder holder , String left , String middle , String right){

        if(left != null){
            showImage(holder.imgLeft , left);
        }else{
            hideImage(holder.imgLeft);
        }

        if(middle != null){
            showImage(holder.imgMiddle , middle);
        }else{
            hideImage(holder.imgMiddle);
        }

        if(right != null){
            showImage(holder.imgRight , right);
        }else{
            hideImage(holder.imgRight);
        }
    }

    public void showImage(ImageView imageView , String url){
        imageView.setVisibility(View.VISIBLE);
        ImageUtils.loadImage(mContext , url , imageView);
    }

    public void hideImage(ImageView imageView){
        imageView.setVisibility(View.GONE);
    }

    @Override
    public int getItemViewType(int position) {

        if (!TextUtils.isEmpty(beanList.get(position).getDigest())) {
            return TYPE_ITEM;
        } else {
            return TYPE_PHOTO;
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        ImageView titleImg;

        CardView container;

        TextView digest;

        TextView pTime;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_title);

            titleImg = (ImageView) itemView.findViewById(R.id.iv_title);

            container = (CardView) itemView.findViewById(R.id.rl_news_item);

            digest = (TextView) itemView.findViewById(R.id.tv_digest);

            pTime = (TextView) itemView.findViewById(R.id.tv_pTime);
        }
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder{
        CardView photoContainer;
        TextView photoTitle;
        ImageView imgLeft;
        ImageView imgMiddle;
        ImageView imgRight;
        TextView pTime;

        public PhotoViewHolder(View itemView) {
            super(itemView);

            photoContainer = (CardView) itemView.findViewById(R.id.card_news_photo);

            photoTitle = (TextView) itemView.findViewById(R.id.news_summary_title_tv);

            imgLeft = (ImageView) itemView.findViewById(R.id.news_summary_photo_iv_left);
            imgMiddle = (ImageView) itemView.findViewById(R.id.news_summary_photo_iv_middle);
            imgRight = (ImageView) itemView.findViewById(R.id.news_summary_photo_iv_right);

            pTime = (TextView) itemView.findViewById(R.id.news_summary_ptime_tv);
        }
    }
}
