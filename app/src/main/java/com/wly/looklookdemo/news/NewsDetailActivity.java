package com.wly.looklookdemo.news;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wly.looklookdemo.R;
import com.wly.looklookdemo.bean.NewsDetailBean;
import com.wly.looklookdemo.news.presenter.NewsDetailPresenter;
import com.wly.looklookdemo.news.presenter.NewsDetailPresenterImpl;
import com.wly.looklookdemo.news.view.NewsDetailView;
import com.wly.looklookdemo.utils.ImageUtils;
import com.wly.looklookdemo.utils.WebUtil;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailView{

    public static final String TAG = NewsDetailActivity.class.getSimpleName();

    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    @Bind(R.id.news_detail_photo_iv)
    ImageView titleImage;

    @Bind(R.id.progress_bar)
    ProgressBar progress;

    @Bind(R.id.news_detail_toobar)
    Toolbar toolbar;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Bind(R.id.wv_detail)
    WebView wvDetail;

    public NewsDetailPresenter presenter;

    public String url;
    public String body;
    public boolean isEmpty;
    public String[] css;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initData();
    }
    public void initData(){

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("News_ID");
        String image = bundle.getString("News_Image");
        String title = bundle.getString("News_Title");
        String newsType = bundle.getString("News_Type");

        toolbarLayout.setTitle(title);
        ImageUtils.loadImage(this , image , titleImage);

        presenter = new NewsDetailPresenterImpl(this , NewsDetailActivity.this);
        presenter.loadNewsDetail(id , newsType);

        mFab.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.RollIn).playOn(mFab);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void showProgress() {

        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        progress.setVisibility(View.GONE);
    }

    @Override
    public void showFailMsg() {

    }

    @Override
    public void addNewsDetail(NewsDetailBean bean) {

        url = bean.getShare_url();
        css = bean.getCss();
        body = bean.getBody();
        isEmpty = TextUtils.isEmpty(bean.getBody());
        if(isEmpty){
            wvDetail.loadUrl(url);
        }else{
            if(css == null){

                StringBuilder sb = new StringBuilder();
                sb.append("<div class=\"img-wrap\">")
                        .append("<h1 class=\"headline-title\">")
                        .append(bean.getTitle()).append("</h1>")
                        .append("<span class=\"img-source\">")
                        .append(bean.getImage_source()).append("</span>")
                        .append("\" alt=\"\">")
                        .append("<div class=\"img-mask\"></div>");
                String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
                        + "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
                        + bean.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
                wvDetail.loadDataWithBaseURL(WebUtil.BASE_URL , mNewsContent , WebUtil.MIME_TYPE , WebUtil.ENCODING , WebUtil.FAIL_URL);

            }else{
                String data = WebUtil.buildHtmlWithCss(body , css , false);
                wvDetail.loadDataWithBaseURL(WebUtil.BASE_URL , data , WebUtil.MIME_TYPE , WebUtil.ENCODING , WebUtil.FAIL_URL);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_inter:
                //使用浏览器打开
                break;
            case R.id.action_webView:
                //使用webView打开
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.news_detail_menu , menu);

        return super.onCreateOptionsMenu(menu);
    }
}
