package com.bawie.yangqingqing.mytaobao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bawie.yangqingqing.mytaobao.R;

import com.bawie.yangqingqing.mytaobao.activity.SaoMiaoActivity;
import com.bawie.yangqingqing.mytaobao.activity.SouSuoActivity;
import com.bawie.yangqingqing.mytaobao.adapter.ShouyeAdapter;
import com.bawie.yangqingqing.mytaobao.bean.ShouyeBean;
import com.google.gson.Gson;
import com.google.zxing.oned.rss.FinderPattern;
import com.yalantis.phoenix.PullToRefreshView;


import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShouyeFragment extends Fragment{

    private PullToRefreshView rf;
    private RecyclerView rc;
    private static final long REFRESH_DELAY =3000 ;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                if (msg.what==0){
                    String s= (String) msg.obj;
                    ShouyeBean bean=new Gson().fromJson(s,ShouyeBean.class);
                    ShouyeAdapter adapter=new ShouyeAdapter(bean,getContext());
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                    rc.setLayoutManager(linearLayoutManager);
                    rc.setAdapter(adapter);
                }
        }
    };
    private ImageView saoyisao;
    private LinearLayout ll;
    private EditText et;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_shouye,null);
        //找到控件
        rf = (PullToRefreshView) view.findViewById(R.id.shouye_rf);
        rc = (RecyclerView) view.findViewById(R.id.shouye_rc);
        saoyisao = (ImageView) view.findViewById(R.id.shouye_saoyisao);
        ll = (LinearLayout) view.findViewById(R.id.shouye_ll);
        et = (EditText) view.findViewById(R.id.shouye_et);
        et.setInputType(InputType.TYPE_NULL);
        //设置点击事件,点击跳转到搜索的详情页面
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SouSuoActivity.class);
                startActivity(intent);
            }
        });
        //二维码扫描,添加点击事件
        saoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), SaoMiaoActivity.class),100);
            }
        });

        getData();
        refresh();
        return view;
    }

    //下拉刷新
    public void refresh(){
        rf.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rf.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rf.setRefreshing(true              );
                    }
                },REFRESH_DELAY);
            }
        });
    }


    //获取数据
    private void getData(){
        OkHttpClient client=new OkHttpClient();
        final Request request=new Request.Builder()
                .url("http://m.yunifang.com/yunifang/mobile/home")
                .build();
        client.newCall(request).enqueue(new Callback() {

            private ShouyeAdapter adapter;

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str=response.body().string();
                Gson gson=new Gson();
                ShouyeBean bean = gson.fromJson(str, ShouyeBean.class);
                List<ShouyeBean.DataBean.Ad1Bean> lists = bean.getData().getAd1();
                //添加适配器
                Message message=new Message();
                message.what=0;
                message.obj=str;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==200){
            String result = data.getStringExtra("data");
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
        }
    }
}
