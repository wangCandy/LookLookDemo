package com.wly.looklookdemo.base;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;

import com.wly.looklookdemo.R;
import com.wly.looklookdemo.utils.NetWorkCheckUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Candy on 2016/9/2.
 */
public abstract class BaseFragment extends Fragment{

    public static final String TAG = BaseFragment.class.getSimpleName();

    public View convertView;
    public Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        convertView = inflater.inflate(getLayoutId() ,null);
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(this , convertView);
        findView();
        setListener();
        initialize();
    }

    protected abstract int getLayoutId();

    protected abstract void findView();

    protected abstract void setListener();

    protected abstract void initialize();

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: BaseFragment");
        ButterKnife.unbind(this);
    }
}
