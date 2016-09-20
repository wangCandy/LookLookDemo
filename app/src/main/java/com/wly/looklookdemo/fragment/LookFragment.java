package com.wly.looklookdemo.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.wly.looklookdemo.R;
import com.wly.looklookdemo.base.BaseFragment;
import com.wly.looklookdemo.utils.NetWorkCheckUtil;

/**
 * Created by Candy on 2016/9/2.
 */
public class LookFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    protected void findView() {

        if(NetWorkCheckUtil.isNetWorkConnected(getContext())){



        }else{
            ViewStub view = (ViewStub) convertView.findViewById(R.id.viewsub);
            view.inflate();
            Button reload = (Button) convertView.findViewById(R.id.reload);
            reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initialize() {

    }
}
