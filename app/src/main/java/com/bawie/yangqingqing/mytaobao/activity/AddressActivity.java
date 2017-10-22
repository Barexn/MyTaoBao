package com.bawie.yangqingqing.mytaobao.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.adapter.AddressLvAdapter;
import com.bawie.yangqingqing.mytaobao.bean.AddressBean2;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddressActivity extends AppCompatActivity {
    @BindView(R.id.f2_header)
    TextView f2Header;
    @BindView(R.id.f5_address_listview)
    ListView f5AddressListview;
    @BindView(R.id.f5_address_add)
    Button f5AddressAdd;
    private String key;
    private List<AddressBean2.DatasBean.AddressListBean> address_list;
    private AddressBean2.DatasBean.AddressListBean addressListBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        key = config.getString("key", "");
//        ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2=new Intent(AddressActivity.this,PayActivity.class);
//                startActivity(intent2);
//            }
//        });

        f5AddressAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddressActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("key", key)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.23.144/mobile/index.php?act=member_address&op=address_list")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {

                    private AddressLvAdapter addressLvAdapter;

                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        System.out.println("============地址"+result);
                        AddressBean2 addressBean = gson.fromJson(result, AddressBean2.class);
                        address_list = addressBean.getDatas().getAddress_list();
                        for (int i = 0; i < address_list.size(); i++) {
                            if(address_list.get(i).getIs_default().equals("1")){
                                address_list.get(i).setisno(true);
                            }else {
                                address_list.get(i).setisno(false);
                            }
                        }
                        addressLvAdapter = new AddressLvAdapter(AddressActivity.this, address_list);
                        f5AddressListview.setAdapter(addressLvAdapter);

                        addressLvAdapter.getDeleteAddress(new AddressLvAdapter.DeleteAddress() {

                            @Override
                            public void getPosition(final int position,boolean isno) {

                                addressListBean = address_list.get(position);
                                String address_id = addressListBean.getAddress_id();
                                //isno是判断是修改还是删除，true是删除  false是修改
                                if(isno == true){
                                    OkHttpClient client = new OkHttpClient();
                                    RequestBody formBody = new FormBody.Builder()
                                            .add("key", key)
                                            .add("address_id", address_id)
                                            .build();
                                    Request request = new Request.Builder()
                                            .url("http://192.168.23.144/mobile/index.php?act=member_address&op=address_del")
                                            .post(formBody)
                                            .build();
                                    Call call = client.newCall(request);
                                    call.enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                        }

                                        @Override
                                        public void onResponse(Call call, final Response response) throws IOException {
                                            final String result = response.body().string();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(AddressActivity.this, "删除" + position, Toast.LENGTH_SHORT).show();
                                                    address_list.remove(position);
                                                    addressLvAdapter.notifyDataSetChanged();
                                                }
                                            });
                                        }
                                    });
                                }else {
                                    Intent intent = new Intent(AddressActivity.this, UpdataAddressActivity.class);
                                    intent.putExtra("name",addressListBean.getTrue_name());
                                    intent.putExtra("phone",addressListBean.getMob_phone());
                                    intent.putExtra("address",addressListBean.getAddress());
                                    intent.putExtra("city2",addressListBean.getArea_info());
                                    intent.putExtra("address_id",addressListBean.getAddress_id());
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
