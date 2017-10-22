package com.bawie.yangqingqing.mytaobao.banner;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

public class ImageLoaderBanner extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
//Glide设置图片的简单用法
        Glide.with(context).load(path).into(imageView);
    }
}
