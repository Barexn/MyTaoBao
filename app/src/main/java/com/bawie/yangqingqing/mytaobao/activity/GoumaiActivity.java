package com.bawie.yangqingqing.mytaobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.ShopCarBean;
import com.bawie.yangqingqing.mytaobao.bean.SousuoBean;
import com.bwie.imageloaderlibrary.UtilImage;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GoumaiActivity extends AppCompatActivity {
    @BindView(R.id.shopshow_back233333)
    ImageView shopshowBack2;
    @BindView(R.id.shopshow_re)
    RelativeLayout shopshowRe;
    @BindView(R.id.order_address)
    LinearLayout orderAddress;
    @BindView(R.id.order_store_name)
    TextView orderStoreName;
    @BindView(R.id.order_goods_img)
    ImageView orderGoodsImg;
    @BindView(R.id.order_goods_name)
    TextView orderGoodsName;
    @BindView(R.id.order_goods_price)
    TextView orderGoodsPrice;
    @BindView(R.id.order_goods_num)
    TextView orderGoodsNum;
    @BindView(R.id.order_1)
    RelativeLayout order1;
    @BindView(R.id.textView15)
    TextView textView15;
    @BindView(R.id.order_jian)
    Button orderJian;
    @BindView(R.id.order_jj_num)
    TextView orderJjNum;
    @BindView(R.id.order_jia)
    Button orderJia;
    @BindView(R.id.order_2)
    RelativeLayout order2;
    @BindView(R.id.textView16)
    TextView textView16;
    @BindView(R.id.order_3)
    RelativeLayout order3;
    @BindView(R.id.order_4)
    RelativeLayout order4;
    @BindView(R.id.textView19)
    TextView textView19;
    @BindView(R.id.order_5)
    RelativeLayout order5;
    @BindView(R.id.order_sum_num)
    TextView orderSumNum;
    @BindView(R.id.textView21)
    TextView textView21;
    @BindView(R.id.order_goods_xiao_price)
    TextView orderGoodsXiaoPrice;
    @BindView(R.id.order_6)
    RelativeLayout order6;
    @BindView(R.id.order_order)
    Button orderOrder;
    @BindView(R.id.textView22)
    TextView textView22;
    @BindView(R.id.order_goods_heji_price)
    TextView orderGoodsHejiPrice;
    @BindView(R.id.order_7)
    RelativeLayout order7;
    @BindView(R.id.goods_ll)
    LinearLayout goodsLl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goumai);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        String num = intent.getStringExtra("num");
        String mTotlaPrice = intent.getStringExtra("mTotlaPrice");
        orderGoodsHejiPrice.setText("￥"+mTotlaPrice);

        shopshowBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(GoumaiActivity.this,xiangqing2.class);
                startActivity(intent1);
            }
        });

        SousuoBean.DatasBean.GoodsListBean goodsListBean = (SousuoBean.DatasBean.GoodsListBean) intent.getSerializableExtra("goodsListBean");
        //为控件赋值
        ImageLoader.getInstance().displayImage(goodsListBean.getGoods_image_url(),orderGoodsImg, UtilImage.getOptions());
        orderGoodsName.setText(goodsListBean.getGoods_name());
        orderGoodsPrice.setText(goodsListBean.getGoods_price());
        orderGoodsNum.setText(num+"");
    }
}
