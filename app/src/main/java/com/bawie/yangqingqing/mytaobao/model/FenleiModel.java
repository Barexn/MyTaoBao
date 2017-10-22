package com.bawie.yangqingqing.mytaobao.model;


import com.bawie.yangqingqing.mytaobao.utils.UnicodeStreamUtils;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class FenleiModel {
    public void getDate(final String path,final FenleiModelIs fenleiModel){
        OkHttpUtils.get()
                .url(path)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        String str=UnicodeStreamUtils.getStr(response);
                        fenleiModel.isForBean(str);
                    }
                });
    }

    public interface FenleiModelIs{
        void isForBean(String str);
    }
}

