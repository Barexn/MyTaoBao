package com.bawie.yangqingqing.mytaobao.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.FenleiBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class FenleiGridViewAdapter extends BaseAdapter{

    private Context context;
    private List<FenleiBean.DatasBean.ClassListBean> list;

    public FenleiGridViewAdapter(Context context, List<FenleiBean.DatasBean.ClassListBean> list) {
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
            convertView=View.inflate(context, R.layout.fenleigridviewadapter3,null);

            vh=new ViewHolder();

            vh.tv= (TextView) convertView.findViewById(R.id.fl_item3_tv);

            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        vh.tv.setText(list.get(position).getGc_name());
        return convertView;
    }
    class ViewHolder{
        TextView tv;
    }
}
