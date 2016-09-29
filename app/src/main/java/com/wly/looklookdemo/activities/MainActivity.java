package com.wly.looklookdemo.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.wly.looklookdemo.R;
import com.wly.looklookdemo.fragment.LookFragment;
import com.wly.looklookdemo.fragment.NewsFragment;
import com.wly.looklookdemo.fragment.ZhihuFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.navigation)
    NavigationView mNavigation;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.fab)
    public FloatingActionButton fab;

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , mDrawerLayout , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigation.setNavigationItemSelectedListener(this);
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.main_contain , new NewsFragment()).commitAllowingStateLoss();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                mDrawerLayout.scrollTo(0 , 0);
            }
        });
    }

    private boolean isInitializeFAB = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isInitializeFAB) {
            isInitializeFAB = true;
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_zhihu:
                manager.beginTransaction().replace(R.id.main_contain , new ZhihuFragment()).commitAllowingStateLoss();
                break;
            case R.id.nav_wangyi:
                manager.beginTransaction().replace(R.id.main_contain , new NewsFragment()).commitAllowingStateLoss();
                break;
            case R.id.nav_Look:
                manager.beginTransaction().replace(R.id.main_contain , new LookFragment()).commitAllowingStateLoss();
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
