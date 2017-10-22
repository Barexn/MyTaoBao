package com.bawie.yangqingqing.mytaobao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;

import java.util.List;

public class SousuoLsLv extends BaseAdapter{

    private Context context;
    private List<String> list;

    public SousuoLsLv(Context context, List<String> list) {
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
            convertView=View.inflate(context,R.layout.sousuolishilistview,null);
            viewHolder=new ViewHolder();
            TextView tv =viewHolder.textView= (TextView) convertView.findViewById(R.id.lishi_tv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position));
        return convertView;
    }
    class ViewHolder{
        TextView textView;
    }
}
