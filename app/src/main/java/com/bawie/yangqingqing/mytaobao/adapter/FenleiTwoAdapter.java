package com.bawie.yangqingqing.mytaobao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.FenleiBean;
import com.bawie.yangqingqing.mytaobao.bean.UrlBean;
import com.bawie.yangqingqing.mytaobao.utils.UnicodeStreamUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

public class FenleiTwoAdapter extends BaseAdapter{


    private Context context;
    private List<FenleiBean.DatasBean.ClassListBean> list;

    public FenleiTwoAdapter(Context context, List<FenleiBean.DatasBean.ClassListBean> list) {
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
        final ViewHolder vh;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.fenleiadapter2,null);

            vh=new ViewHolder();

            vh.tv= (TextView) convertView.findViewById(R.id.fl_item2_tv);
            vh.gv= (GridView) convertView.findViewById(R.id.fl_item2_gv);

            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        vh.tv.setText(list.get(position).getGc_name());

        OkHttpUtils
                .get()
                .url(UrlBean.ER_ONE + "&gc_id=" + list.get(position).getGc_id())
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(com.squareup.okhttp.Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {

                        String str = UnicodeStreamUtils.getStr(response);
                        Gson gson = new Gson();
                        FenleiBean bean = gson.fromJson(str, FenleiBean.class);

                        vh.gv.setAdapter(new FenleiGridViewAdapter(context,bean.getDatas().getClass_list()));

                    }
                });
        return convertView;
    }
    class ViewHolder{
        TextView tv;
        GridView gv;
    }
}
