package com.bawie.yangqingqing.mytaobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.adapter.SousuoXiangqingAdapter;
import com.bawie.yangqingqing.mytaobao.bean.SousuoBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SousuoXiangqingActivity extends AppCompatActivity{

    private ImageView back;
    private EditText et;
    private ListView lv;
    private List<SousuoBean.DatasBean.GoodsListBean> list;
    private SousuoXiangqingAdapter adapter;
    private SousuoBean bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sousuoxiangqing);
        //找到控件
        back = (ImageView) findViewById(R.id.xiangqing_back);
        et = (EditText) findViewById(R.id.xaingqing_et);
        et.setInputType(InputType.TYPE_NULL);
        lv = (ListView) findViewById(R.id.xiangqing_lv);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SousuoBean.DatasBean.GoodsListBean goodsListBean=list.get(position);
                        Intent intent2=new Intent(SousuoXiangqingActivity.this,xiangqing2.class);
                        intent2.putExtra("goodslistbean",(Serializable)goodsListBean);
                        intent2.putExtra("goods_id",list.get(position).getGoods_id());
                        startActivity(intent2);
                    }
                });
        Intent intent=getIntent();
        String title=intent.getStringExtra("sousuo");
        et.setText(title);
        //设置点击事件,点击返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SousuoXiangqingActivity.this,SouSuoActivity.class);

                startActivity(intent);
            }
        });

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url("http://192.168.23.144/mobile/index.php?act=goods&op=goods_list&page=100")
                .build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result=response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        bean = gson.fromJson(result,SousuoBean.class);
                        list = bean.getDatas().getGoods_list();
                        //添加适配器
                        adapter = new SousuoXiangqingAdapter(SousuoXiangqingActivity.this,list);
                        lv.setAdapter(adapter);
                        System.out.println("ppppppppppppppppppppppppppppppp"+list.size());
                    }
                });
            }
        });
    }
}
