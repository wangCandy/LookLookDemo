package com.wly.looklookdemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wly.looklookdemo.R;

/**
 * Created by Candy on 2016/9/29.
 */
public class LoadingDialog extends Dialog {

    private LayoutInflater inflater;
    private TextView loadtext;
    private ProgressBar loadIcon;

    /**
     * Instantiates a new Loading dialog.
     *
     * @param context the context
     */
    public LoadingDialog(Context context) {
        super(context, R.style.myDialog_loading);
        inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_loading, null);
        loadtext = (TextView) layout.findViewById(R.id.loading_text);
        loadIcon = (ProgressBar) layout.findViewById(R.id.dialog_loading);
        this.setContentView(layout);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

    }

    /**
     * Sets load text.
     *
     * @param content the content
     */
    public void setLoadText(String content) {
        loadtext.setText(content);
    }

}
