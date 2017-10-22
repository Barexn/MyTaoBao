package com.bawie.yangqingqing.mytaobao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.SousuoBean;
import com.bwie.imageloaderlibrary.UtilImage;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class SousuoXiangqingAdapter extends BaseAdapter{

    private Context context;
    private List<SousuoBean.DatasBean.GoodsListBean> list;

    public SousuoXiangqingAdapter(Context context, List<SousuoBean.DatasBean.GoodsListBean> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.sousuoxiangqingadapter,null);
            viewHolder=new ViewHolder();
            ImageView image =viewHolder.pic= (ImageView) convertView.findViewById(R.id.souxiangqing_iv);
            TextView address =viewHolder.address= (TextView) convertView.findViewById(R.id.souxiangqing__address);
            TextView price =viewHolder.price= (TextView) convertView.findViewById(R.id.souxiangqing__price);
            TextView title =viewHolder.title= (TextView) convertView.findViewById(R.id.souxiangqing_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getGoods_image_url(),viewHolder.pic, UtilImage.getOptions());
        viewHolder.price.setText(list.get(position).getGoods_price());
        viewHolder.address.setText(list.get(position).getStore_name());
        viewHolder.title.setText(list.get(position).getGoods_name());
        return convertView;
    }
    class ViewHolder{
        private ImageView pic;
        private TextView title;
        private TextView price;
        private TextView address;
    }

}
