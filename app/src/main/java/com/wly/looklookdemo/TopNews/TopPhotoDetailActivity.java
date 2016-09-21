package com.wly.looklookdemo.TopNews;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.wly.looklookdemo.R;
import com.wly.looklookdemo.bean.TopNewsBean;
import com.wly.looklookdemo.utils.ImageUtils;
import com.wly.looklookdemo.widget.PhotoViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TopPhotoDetailActivity extends AppCompatActivity {

    public static final String TAG = TopPhotoDetailActivity.class.getSimpleName();

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.photo_detail_title_tv)
    TextView photoTitle;

    @Bind(R.id.photo_view_pager)
    PhotoViewPager photoViewPager;

    public TopNewsBean photoBean;

    public List<ImageView> imageData;

    public TopPhotoDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_photo_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        init();
    }

    public void init(){
        if(photoBean == null){
            photoBean = (TopNewsBean) getIntent().getExtras().getSerializable("news_detail");
        }
        initData();
        setPhotoTitle(0);
    }

    public void initData(){

        imageData = new ArrayList<ImageView>();

        if(photoBean.getAds() != null){

            for(TopNewsBean.AdsBean bean : photoBean.getAds()){
                ImageView image = new ImageView(this);
                ImageUtils.loadImage(this , bean.getImgsrc() , image);
                imageData.add(image);
            }

        }else if(photoBean.getImgextra() != null){
            for(TopNewsBean.ImgextraBean bean : photoBean.getImgextra()){
                ImageView image = new ImageView(this);
                ImageUtils.loadImage(this , bean.getImgsrc() , image);
                imageData.add(image);
            }
        } else{
            ImageView image = new ImageView(this);
            ImageUtils.loadImage(this , photoBean.getImgsrc() , image);
            imageData.add(image);
        }
        initViewPager();
    }

    public void initViewPager(){
        adapter = new TopPhotoDetailAdapter(imageData);
        photoViewPager.setAdapter(adapter);
        photoViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPhotoTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setPhotoTitle(int position){

        String title = getTitle(position);
        photoTitle.setText(getString(R.string.photo_detail_title , position + 1 , imageData.size() , title));
    }

    public String getTitle(int position){
        if(photoBean.getAds() != null){
            String title = photoBean.getAds().get(position).getTitle();
            if(title == null){
                title = photoBean.getTitle();
            }
            return title;
        }else{
            return photoBean.getTitle();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
