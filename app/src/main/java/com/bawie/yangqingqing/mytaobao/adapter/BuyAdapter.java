package com.bawie.yangqingqing.mytaobao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.activity.PayActivity;
import com.bawie.yangqingqing.mytaobao.app.MyApplication;
import com.bawie.yangqingqing.mytaobao.bean.ShopCarBean;
import com.bwie.imageloaderlibrary.UtilImage;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyAdapter extends BaseAdapter{

    private Context context;
    private List<ShopCarBean.DatasBean.CartListBean.GoodsBean> list;

    public BuyAdapter(Context context, List<ShopCarBean.DatasBean.CartListBean.GoodsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //判断从converview不为空
        if(convertView == null){
            //装换布局
            convertView = View.inflate(context, R.layout.buylistviewitem,null);
            viewHolder = new ViewHolder();
            //找到控件
            viewHolder.order_goods_img = (ImageView) convertView.findViewById(R.id.item_imageview);
            viewHolder.order_goods_name = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.order_goods_price = (TextView) convertView.findViewById(R.id.item_price);
            viewHolder.order_goods_num = (TextView) convertView.findViewById(R.id.item_num);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //为控件赋值
        ImageLoader.getInstance().displayImage(list.get(position).getGoods_image_url(),viewHolder.order_goods_img,UtilImage.getOptions());
        viewHolder.order_goods_name.setText(list.get(position).getGoods_name());
        viewHolder.order_goods_price.setText(list.get(position).getGoods_price());
        viewHolder.order_goods_num.setText(list.get(position).getGoods_num());
        return convertView;
    }
    //viewholder类
    class ViewHolder{
        private ImageView order_goods_img;
        private TextView order_goods_name;
        private TextView order_goods_price;
        private TextView order_goods_num;
    }
}
