package com.bawie.yangqingqing.mytaobao.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.ShouyeBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SecondAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ShouyeBean.DataBean.DefaultGoodsListBean> list;
    private Context context;
    private List<Integer> height;

    public SecondAdapter(List<ShouyeBean.DataBean.DefaultGoodsListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.secondadapter,parent,false);
        final MyViewHolder myViewHolder=new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (onChilk!=null){
                    onChilk.RecyOnClick(myViewHolder.getAdapterPosition());
                }
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder holder1= (MyViewHolder) holder;
        ((MyViewHolder) holder).second_title.setText(list.get(position).getEfficacy());
        ((MyViewHolder) holder).second_dec.setText(list.get(position).getGoods_name());
        ((MyViewHolder) holder).second_price.setText("¥"+list.get(position).getShop_price());
        ViewGroup.LayoutParams params=((MyViewHolder) holder).second_iv.getLayoutParams();
        getItemHeight(list);
        params.height=height.get(position);
        ((MyViewHolder) holder).second_iv.setLayoutParams(params);
        ImageLoader.getInstance().displayImage(list.get(position).getGoods_img(),holder1.second_iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView second_iv;
        private final TextView second_title;
        private final TextView second_price;
        private final TextView second_dec;

        public MyViewHolder(View itemView) {
            super(itemView);
            second_iv = (ImageView) itemView.findViewById(R.id.second_iv);
            second_title = (TextView) itemView.findViewById(R.id.second_title);
            second_price = (TextView) itemView.findViewById(R.id.second_price);
            second_dec = (TextView) itemView.findViewById(R.id.second_dec);

        }
    }
    public void getItemHeight(List<ShouyeBean.DataBean.DefaultGoodsListBean> list){
        height = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            height.add((int) (200+Math.random()*200));
        }
    }
    private  OnChilk onChilk;

    public interface OnChilk{
        public void RecyOnClick(int position);
    }
    public void setRecyOnClick(OnChilk onChilk){
        this.onChilk=onChilk;
    }
}
