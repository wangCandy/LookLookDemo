package com.wly.looklookdemo.utils;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

/**
 * Created by Candy on 2016/9/8.
 */
public class ImageUtils {

    public static void loadImage(Context context , String url , ImageView view){

        Ion.with(context).load(url)
                .intoImageView(view);
    }
}
