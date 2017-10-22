package com.bawie.yangqingqing.mytaobao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.FenleiBean;
import com.bumptech.glide.Glide;

import java.util.List;

public class FenleiOneAdapter extends BaseAdapter{

    private Context context;
    private List<FenleiBean.DatasBean.ClassListBean> list;

    public FenleiOneAdapter(Context context, List<FenleiBean.DatasBean.ClassListBean> list) {
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
        ViewHolder vh;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.fenleiadapter1,null);

            vh=new ViewHolder();

            vh.iv= (ImageView) convertView.findViewById(R.id.fl_item1_iv);
            vh.tv= (TextView) convertView.findViewById(R.id.fl_item1_tv);

            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        String image = list.get(position).getImage();

        if(image.length()<=0){
            vh.iv.setImageResource(R.mipmap.ic_launcher);
        }else {
            System.out.println(image);
            Glide.with(context).load(image).into(vh.iv);
        }

        vh.tv.setText(list.get(position).getGc_name());
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv;
    }
}
