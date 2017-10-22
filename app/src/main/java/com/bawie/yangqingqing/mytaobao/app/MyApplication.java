package com.bawie.yangqingqing.mytaobao.app;

import android.app.Application;
import android.os.Environment;
import android.text.Html;

import com.bwie.imageloaderlibrary.UtilImage;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

/**
 * Created by Administrator on 2017/10/9.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration configuration = UtilImage.getConfiguration(this);
        ImageLoader.getInstance().init(configuration);
        //对xUtils进行初始化
        x.Ext.init(this);
    }

}
