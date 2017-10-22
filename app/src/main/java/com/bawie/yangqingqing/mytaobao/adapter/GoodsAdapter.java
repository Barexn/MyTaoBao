package com.bawie.yangqingqing.mytaobao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.ShopCarBean;
import com.bawie.yangqingqing.mytaobao.bean.SousuoBean;
import com.bawie.yangqingqing.mytaobao.fragment.GouwucheFragment;
import com.bwie.imageloaderlibrary.UtilImage;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.IViewHolder>{

    private Context context;
    private List<ShopCarBean.DatasBean.CartListBean.GoodsBean> list;

    public GoodsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ShopCarBean.DatasBean.CartListBean.GoodsBean> list) {
        if (this.list==null){
            this.list=new ArrayList<>();
        }
        //刷新适配器
        this.list=list;
        notifyDataSetChanged();

    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.goodsadapteritem,parent,false);
        IViewHolder viewHolder=new IViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IViewHolder holder, final int position) {
        if (position>0){
            if (list.get(position).getStore_id().equals(list.get(position-1).getStore_id())){
                holder.header.setVisibility(View.GONE);
            }else{
                holder.header.setVerticalGravity(View.VISIBLE);
            }
        }else{
            holder.header.setVerticalGravity(View.VISIBLE);
        }
        holder.shopname.setText(list.get(position).getStore_name());
        holder.name.setText(list.get(position).getGoods_name());
        holder.price.setText("¥"+list.get(position).getGoods_price());
        holder.num.setText(list.get(position).getGoods_num()+"");
        ImageLoader.getInstance().displayImage(list.get(position).getGoods_image_url(),holder.pic, UtilImage.getOptions());

        //标记商品是否被选中
        if (list.get(position).isSelect()){
            holder.clothselect.setImageDrawable(context.getResources().getDrawable(R.mipmap.shopcart_selected));
        }else{
            holder.clothselect.setImageDrawable(context.getResources().getDrawable(R.mipmap.shopcart_unselected));
        }
        //标记商店是否被选中
        if (list.get(position).isShopSelect()){
            holder.select.setImageDrawable(context.getResources().getDrawable(R.mipmap.shopcart_selected));
        }else{
            holder.select.setImageDrawable(context.getResources().getDrawable(R.mipmap.shopcart_unselected));
        }
        if (onRefershListener!=null){
            boolean isSelect=false;
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).isSelect()){
                    isSelect=false;
                    break;
                }else{
                    isSelect=true;
                }
            }
            onRefershListener.onRefersh(isSelect,list);
        }

        //删除事件
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteClickListener!=null){
                    onDeleteClickListener.onDeleteClick(v,position,list.get(position).getGoods_id());
                }
                list.remove(position);
                GouwucheFragment.setFirstState(list);
                notifyDataSetChanged();
            }
        });

        //商品-数量事件
        holder.jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = list.get(position).getGoods_num();
                int content = Integer.valueOf(num);
                if(content > 1){
                    int count = content - 1 ;
                    list.get(position).setGoods_num(count+"");
                    notifyDataSetChanged();
                    String getGc_id = list.get(position).getGc_id();
                    int id = Integer.valueOf(getGc_id);
                }
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goods_id = list.get(position).getGoods_num();
                int content = Integer.valueOf(goods_id);

                int count = content+ 1 ;
                list.get(position).setGoods_num(count+"");
                notifyDataSetChanged();

                String getGc_id = list.get(position).getGc_id();
                int id = Integer.valueOf(getGc_id);
            }
        });

        //商品未选中和选中事件点击
        holder.clothselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setSelect(!list.get(position).isSelect());
                for (int i = 0; i <list.size() ; i++) {
                    for (int j = 0; j <list.size() ; j++) {
                        if (list.get(j).getStore_id().equals(list.get(i).getStore_id()) && !list.get(j).isSelect()){
                            list.get(i).setShopSelect(false);
                            break;
                        }else{
                            list.get(i).setShopSelect(true);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });

        //店铺选中与未选中

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getIsFirst()==1){
                    list.get(position).setShopSelect(!list.get(position).isShopSelect());
                    for (int i = 0; i <list.size() ; i++) {
                        if (list.get(i).getStore_id().equals(list.get(position).getStore_id())){
                            list.get(i).setSelect(list.get(position).isShopSelect());
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }


    //全选
    public void setUnSelected(int selected){
        if(list != null && list.size() > 0){

            for (int i=0;i<list.size();i++){
                if(selected == 2){
                    list.get(i).setSelect(false);
                    list.get(i).setShopSelect(false);
                } else {
                    list.get(i).setSelect(true);
                    list.get(i).setShopSelect(true);

                }
            }
            notifyDataSetChanged();

        }
    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size() ;
    }


    // 点击事件

    public OnItemClickListener onItemClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener ;
    }

    //删除
    public OnDeleteClickListener onDeleteClickListener;
    public interface OnDeleteClickListener {
        void onDeleteClick(View view, int position, String cartid);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener deleteClickListener){
        this.onDeleteClickListener = deleteClickListener;
    }

    //减少
    public OnEditListener onEditListener;
    //添加 减少
    public interface OnEditListener {
        void onEditListener(int position, int cartid, int count);
    }

    public void setOnEditListener(OnEditListener onEditListener){
        this.onEditListener = onEditListener;
    }

    //商品选中状态发生改变
    public OnRefershListener onRefershListener;
    public interface OnRefershListener{
        //isSelect true 表示商品全部选中 false 未全部选中
        void onRefersh(boolean isSelect, List<ShopCarBean.DatasBean.CartListBean.GoodsBean> list);
    }

    public void setOnRefershListener(OnRefershListener listener){
        this.onRefershListener = listener ;
    }

    public class IViewHolder extends RecyclerView.ViewHolder {

        private final ImageView add;
        private final ImageView delete;
        private final ImageView jian;
        private final ImageView pic;
        private final TextView num;
        private final ImageView select;
        private final TextView oolor;
        private final TextView price;
        private final TextView size;
        private final TextView name;
        private final TextView shopname;
        private final ImageView clothselect;
        private final LinearLayout header;

        public IViewHolder(View itemView) {
            super(itemView);
            add = (ImageView) itemView.findViewById(R.id.iv_item_shopcart_cloth_add);
            delete = (ImageView) itemView.findViewById(R.id.iv_item_shopcart_cloth_delete);
            jian = (ImageView) itemView.findViewById(R.id.iv_item_shopcart_cloth_minus);
            pic = (ImageView) itemView.findViewById(R.id.iv_item_shopcart_cloth_pic);
            num = (TextView) itemView.findViewById(R.id.et_item_shopcart_cloth_num);
            select = (ImageView) itemView.findViewById(R.id.iv_item_shopcart_shopselect);
            oolor = (TextView) itemView.findViewById(R.id.tv_item_shopcart_cloth_color);
            price = (TextView) itemView.findViewById(R.id.tv_item_shopcart_cloth_price);
            size = (TextView) itemView.findViewById(R.id.tv_item_shopcart_cloth_size);
            name = (TextView) itemView.findViewById(R.id.tv_item_shopcart_clothname);
            shopname = (TextView) itemView.findViewById(R.id.tv_item_shopcart_shopname);
            clothselect = (ImageView) itemView.findViewById(R.id.tv_item_shopcart_clothselect);
            header = (LinearLayout) itemView.findViewById(R.id.ll_shopcart_header);
        }
    }
}
