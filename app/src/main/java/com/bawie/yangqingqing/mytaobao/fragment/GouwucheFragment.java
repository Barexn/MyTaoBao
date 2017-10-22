package com.bawie.yangqingqing.mytaobao.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.activity.PayActivity;
import com.bawie.yangqingqing.mytaobao.adapter.GoodsAdapter;
import com.bawie.yangqingqing.mytaobao.bean.DeleteBean;
import com.bawie.yangqingqing.mytaobao.bean.ShopCarBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.bawie.yangqingqing.mytaobao.bean.ShopCarBean.*;


public class GouwucheFragment extends Fragment{

    private RecyclerView recyclerView;
    private TextView allselect;
    private TextView submit;
    private GoodsAdapter adapter;
    private ShopCarBean shopCarBean;
    private SharedPreferences sp;
    private Double price,num;
    private List<ShopCarBean.DatasBean.CartListBean.GoodsBean> mAllOrderList;
    private TextView price2;
    private TextView num2;
    private List<ShopCarBean> list;
    private List<String> flist;
    private View view;
    private ShopCarBean shopBean1;
    private int mTotalNum;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_gouwuche, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //找到控件
        recyclerView = (RecyclerView) view.findViewById(R.id.third_recyclerview);
        allselect = (TextView) view.findViewById(R.id.third_allselect);
        price2 = (TextView) view.findViewById(R.id.third_totalprice);
        num2 = (TextView) view.findViewById(R.id.third_totalnum);
        submit = (TextView) view.findViewById(R.id.third_submit);
        mAllOrderList = new ArrayList<>();
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        String key=sp.getString("key","");
        //结算
        submit.setOnClickListener(new View.OnClickListener() {

            private List<DatasBean.CartListBean.GoodsBean> newlist;

            @Override
            public void onClick(View v) {
                newlist = new ArrayList<DatasBean.CartListBean.GoodsBean>();
                for (int i = 0; i <shopBean1.getDatas().getCart_list().size() ; i++) {
                    for (int j = 0; j <shopBean1.getDatas().getCart_list().get(i).getGoods().size() ; j++) {
                        if (shopBean1.getDatas().getCart_list().get(i).getGoods().get(j).isSelect()){
                            newlist.add(shopBean1.getDatas().getCart_list().get(i).getGoods().get(j));
                        }
                    }
                }
                if (mTotalNum==0){
                    Toast.makeText(getActivity(), "您还没有选择商品", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity(), PayActivity.class);
                    intent.putExtra("buyList",(Serializable) newlist);
                    getActivity().startActivity(intent);
                }
            }
        });
        allselect.setTag(1);
        showData();
    }

    //从服务器请求购物车数据
    private void showData() {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("key", "b360266f34090c97dac9360c6fe7c188")
                    //c60d5d406d7e9d8dbfb7566c6a725e91
                    //.add("goods_id", "100008")
                    //.add("quantity", "1")
                    .build();
            Request request = new Request.Builder()
                    .url("http://192.168.23.144/mobile/index.php?act=member_cart&op=cart_list")
                    .post(formBody)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println("失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String result = response.body().string();
                    System.out.println("asdasd"+result);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shopBean1 = new Gson().fromJson(result, ShopCarBean.class);
                            for (int i = 0; i < shopBean1.getDatas().getCart_list().size(); i++) {
                                int length = shopBean1.getDatas().getCart_list().get(i).getGoods().size();
                                for (int j = 0; j < length; j++) {
                                    mAllOrderList.add(shopBean1.getDatas().getCart_list().get(i).getGoods().get(j));
                                }
                            }
                            setFirstState(mAllOrderList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            adapter = new GoodsAdapter(getActivity());
                            adapter.setData(mAllOrderList);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(adapter);

                            adapter.setOnDeleteClickListener(new GoodsAdapter.OnDeleteClickListener(){

                                @Override
                                public void onDeleteClick(View view, int position, String cartid) {
                                    deleteShop(position);
                                    adapter.notifyDataSetChanged();
                                }
                            });

                            adapter.setOnRefershListener(new GoodsAdapter.OnRefershListener() {

                                @Override
                                public void onRefersh(boolean isSelect, List<ShopCarBean.DatasBean.CartListBean.GoodsBean> list) {

                                    //标记底部 全选按钮
                                    if (isSelect) {
                                        Drawable left = getResources().getDrawable(R.mipmap.shopcart_selected);
                                        allselect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                                    } else {
                                        Drawable left = getResources().getDrawable(R.mipmap.shopcart_unselected);
                                        allselect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                                    }
                                    //总价
                                    float mTotlaPrice = 0f;
                                    mTotalNum = 0;
                                    for (int i = 0; i < list.size(); i++) {
                                        if (list.get(i).isSelect()) {
                                            price = Double.parseDouble(list.get(i).getGoods_price());
                                            num = Double.parseDouble(list.get(i).getGoods_num());
                                            mTotlaPrice += price * num;
                                            mTotalNum += num;
                                        }
                                    }
                                    System.out.println("mTotlaPrice = " + mTotlaPrice);

                                    price2.setText("总价 : " + mTotlaPrice);

                                    num2.setText("共" + mTotalNum + "件商品");
                                }
                            });
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //全选
        allselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state= (int) allselect.getTag();
                adapter.setUnSelected(state);
                if (state==1){
                    allselect.setTag(2);
                }else{
                    allselect.setTag(1);
                }
            }
        });

    }

    public static void setFirstState(List<ShopCarBean.DatasBean.CartListBean.GoodsBean> list) {

        if (list.size() > 0) {
            list.get(0).setIsFirst(1);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getStore_id() == list.get(i - 1).getStore_id()) {
                    list.get(i).setIsFirst(2);
                } else {
                    list.get(i).setIsFirst(1);
                }
            }
        }
    }

    //删除商品的方法
    public void deleteShop(int i){
        OkHttpClient client = new OkHttpClient();

        FormBody body = new FormBody.Builder()
                .add("key", "382da161f6bfc0db2bbf4259987cf38c")
                .add("cart_id",shopBean1.getDatas().getCart_list().get(0).getGoods().get(i).getCart_id())
                .build();
        final Request request = new Request.Builder()
                .url("http://192.168.23.144/mobile/index.php?act=member_cart&op=cart_del")
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final DeleteBean deleteBean = gson.fromJson(string, DeleteBean.class);
                System.out.println("=======delete"+string);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (deleteBean.getCode()==200){
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}
