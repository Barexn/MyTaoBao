package com.bawie.yangqingqing.mytaobao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.AddressBean2;
import com.lidroid.xutils.db.annotation.Id;

import java.util.List;

public class AddressLvAdapter extends BaseAdapter{

    private Context context;
    private List<AddressBean2.DatasBean.AddressListBean> list;
    private AddressBean2.DatasBean.AddressListBean addressListBean;

    public AddressLvAdapter(Context context, List<AddressBean2.DatasBean.AddressListBean> list) {
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
        if (convertView==null){
            convertView=View.inflate(context, R.layout.activity_addressadapter,null);
            viewHolder=new ViewHolder();
            viewHolder.address_item_address= (TextView) convertView.findViewById(R.id.adapter_address);
            viewHolder.address_item_bianji= (TextView) convertView.findViewById(R.id.adapter_bianji);
            viewHolder.address_item_checkBox= (CheckBox) convertView.findViewById(R.id.checkbox);
            viewHolder.address_item_del= (TextView) convertView.findViewById(R.id.adapter_delete);
            viewHolder.address_item_name= (TextView) convertView.findViewById(R.id.adapter_name);
            viewHolder.address_item_phone= (TextView) convertView.findViewById(R.id.adapter_phone);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //删除地址
        viewHolder.address_item_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delteAddress.getPosition(position,true);
            }
        });

        viewHolder.address_item_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delteAddress.getPosition(position,false);
            }
        });

        viewHolder.address_item_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    for (int i = 0; i < list.size(); i++) {
                        addressListBean = list.get(i);
                        if(i == position){
                            addressListBean.setisno(true);
                        }else {
                            addressListBean.setisno(false);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });


        viewHolder.address_item_name.setText(list.get(position).getTrue_name());
        viewHolder.address_item_phone.setText(list.get(position).getMob_phone());
        viewHolder.address_item_address.setText(list.get(position).getAddress());
        viewHolder.address_item_checkBox.setChecked(list.get(position).getisno());
        return convertView;
    }
    class ViewHolder{
        private TextView address_item_name;
        private TextView address_item_phone;
        private TextView address_item_address;
        private TextView address_item_del;
        private TextView address_item_bianji;
        private CheckBox address_item_checkBox;
    }

    public DeleteAddress delteAddress;
    public interface DeleteAddress{
        //isno 判断删除还是修改
        void getPosition(int position,boolean isno);
    }
    public void getDeleteAddress (DeleteAddress deleteAddress){
        this.delteAddress = deleteAddress;
    }
}
