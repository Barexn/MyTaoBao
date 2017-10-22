package com.bawie.yangqingqing.mytaobao.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.SousuoBean;
import com.bwie.imageloaderlibrary.UtilImage;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import org.xutils.common.Callback;

import java.io.Serializable;

public class xiangqing2 extends AppCompatActivity{

    private ImageView back;
    private ImageView imageView;
    private TextView title;
    private ImageView fenxiang;
    private TextView price;
    private RadioButton dianpu;
    private RadioButton kefu;
    private RadioButton shoucang;
    private Button gouwuche;
    private Button goumai;
    private int i;
    private PopupWindow p;
    //private SharedPreferences preferences;
    private SharedPreferences user;
    private SousuoBean.DatasBean.GoodsListBean goodsListBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiangqing2);

        x.view().inject(this);
        //找到控件
        back = (ImageView) findViewById(R.id.xiangqing2_back);
        imageView = (ImageView) findViewById(R.id.xiangqing2_pic);
        title = (TextView) findViewById(R.id.xiangqing2_title);
        fenxiang = (ImageView) findViewById(R.id.xiangqing2_fenxiang);
        price = (TextView) findViewById(R.id.xiangqing2_price);
        dianpu = (RadioButton) findViewById(R.id.xiangqing2_dianpu);
        kefu = (RadioButton) findViewById(R.id.xiangqing2_kefu);
        shoucang = (RadioButton) findViewById(R.id.xiangqing2_shoucang);
        gouwuche = (Button) findViewById(R.id.xiangqing2_gouwuche);
        //立即购买
        goumai = (Button) findViewById(R.id.xiangqing2_goumai);
        goumai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(xiangqing2.this, GoumaiActivity.class);
                intent1.putExtra("goodsListBean", goodsListBean);
                startActivity(intent1);
            }
        });

        user=getSharedPreferences("config",MODE_PRIVATE);
        ImageLoader.getInstance().displayImage(getIntent().getStringExtra("url"),imageView,UtilImage.getOptions());
        gouwuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=View.inflate(xiangqing2.this,R.layout.gouwuhcepoupwindow,null);
                ImageView goods=(ImageView) view.findViewById(R.id.goods);
                Button cut=(Button)view.findViewById(R.id.cut);
                Button add=(Button)view.findViewById(R.id.add);
                Button yes=(Button)view.findViewById(R.id.yes);
                final TextView count=(TextView)view.findViewById(R.id.count);
                i = 1;
                p = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,getWindowManager().getDefaultDisplay().getHeight()/3*2);
                p.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                p.setFocusable(true);
                if(!p.isShowing()){
                    p.showAtLocation(View.inflate(xiangqing2.this,R.layout.xiangqing2,null), Gravity.BOTTOM,0,0);
                    ImageLoader.getInstance().displayImage(getIntent().getStringExtra("url"),goods,UtilImage.getOptions());
                    cut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(i!=1){
                                i--;
                                count.setText(i +"");
                            }
                        }
                    });
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            i++;
                            count.setText(i+"");
                        }
                    });
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("IIII",i+"");
                            SharedPreferences.Editor edit = user.edit();
                            edit.putInt("count",i);
                            edit.commit();
                            AddGoodsCar(i);
                        }
                    });
                }
            }
        });
       // preferences = getSharedPreferences("config", MODE_PRIVATE);
        //添加点击事件,进行加入购物车或者立即购买
        //接收传过来的消息
        Intent intent=getIntent();

        goodsListBean = (SousuoBean.DatasBean.GoodsListBean) intent.getSerializableExtra("goodslistbean");



        ImageLoader.getInstance().displayImage(goodsListBean.getGoods_image_url(),imageView, UtilImage.getOptions());
        title.setText(goodsListBean.getGoods_name());
        price.setText("￥"+ goodsListBean.getGoods_price());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(xiangqing2.this,SousuoXiangqingActivity.class);
                startActivity(intent);
            }
        });
    }


    private void AddGoodsCar(int i){
        String key=user.getString("key",null);
        System.out.println("key--------------"+key);
        if(TextUtils.isEmpty(key)){
            Toast.makeText(xiangqing2.this,"您还没有登录..",Toast.LENGTH_SHORT).show();
            //跳转到登录界面
            Intent intent = new Intent(xiangqing2.this, LogActivity.class);
            System.out.println("aaaaaaaaaaaaaaaaa/d9////////////////////////"+key);
            startActivityForResult(intent,400);
            //startActivity(intent);
        }else{
            System.out.println("bbbbbbbbbbbbbb///////////////////////////");
            String goods_id=getIntent().getStringExtra("goods_id");
            String quantity=i+"";
            String path="http://192.168.23.144/mobile/index.php?act=member_cart&op=cart_add";
            RequestParams params=new RequestParams(path);
            params.addParameter("key",key);
            params.addParameter("goods_id",goods_id);
            params.addParameter("quantity",quantity);
            x.http().post(params,new Callback.CommonCallback<String>(){

                @Override
                public void onSuccess(String result) {
                    Toast.makeText(xiangqing2.this,"请求成功",Toast.LENGTH_SHORT).show();
                    try {
                        JSONObject obj=new JSONObject(result);
                        String code = obj.getString("code");
                        System.out.println("---------"+code);
                        if ("200".equals(code)){
                            Toast.makeText(xiangqing2.this,"添加购物车成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(xiangqing2.this,"添加购物车失败",Toast.LENGTH_SHORT).show();
                        }
                        if (p.isShowing()){
                            p.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(xiangqing2.this,"请求失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==400&&resultCode==300){
            AddGoodsCar(user.getInt("count",1));
        }
    }
}
