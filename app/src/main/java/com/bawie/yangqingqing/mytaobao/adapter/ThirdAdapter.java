package com.bawie.yangqingqing.mytaobao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.ShouyeBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ThirdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<ShouyeBean.DataBean.SubjectsBean.GoodsListBeanX> list;

    public ThirdAdapter(Context context, List<ShouyeBean.DataBean.SubjectsBean.GoodsListBeanX> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.thirdadapter,parent,false);
        MyViewHolder myViewHolder= new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder holder1= (MyViewHolder) holder;
        ((MyViewHolder) holder).third_title.setText(list.get(position).getGoodsName());
        ((MyViewHolder) holder).third_price.setText("¥"+list.get(position).getShop_price());
        ImageLoader.getInstance().displayImage(list.get(position).getGoodsImage(),holder1.third_iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView third_iv;
        private final TextView third_title;
        private final TextView third_price;

        public MyViewHolder(View itemView) {
            super(itemView);
            third_iv = (ImageView) itemView.findViewById(R.id.third_iv);
            third_title = (TextView) itemView.findViewById(R.id.third_title);
            third_price = (TextView) itemView.findViewById(R.id.third_price);
        }
    }
}
