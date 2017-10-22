package com.bawie.yangqingqing.mytaobao.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawie.yangqingqing.mytaobao.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AddActivity extends AppCompatActivity {
    @BindView(R.id.shopshow_back)
    ImageView shopshowBack;
    @BindView(R.id.tv_baocun)
    TextView tvBaocun;
    @BindView(R.id.shopshow_re)
    RelativeLayout shopshowRe;
    @BindView(R.id.address_name)
    EditText addressName;
    @BindView(R.id.address_phone)
    EditText addressPhone;
    @BindView(R.id.tv_city)
    EditText tvCity;
    @BindView(R.id.city)
    RelativeLayout city;
    @BindView(R.id.tv_address)
    EditText tvAddress;
    @BindView(R.id.f5_address_add_moren)
    CheckBox f5AddressAddMoren;
    private String address;
    private String city2;
    private String phone;
    private String name;
    private String key;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        key = config.getString("key", "");

        tvBaocun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = addressName.getText().toString().trim();
                phone = addressPhone.getText().toString().trim();
                city2 = tvCity.getText().toString().trim();
                address = tvAddress.getText().toString().trim();
                if(TextUtils.isEmpty(name)){

                }else if(TextUtils.isEmpty(name)){

                }
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                        .add("key", key)
                        .add("true_name", name)
                        .add("city_id", 36+"")
                        .add("area_id", 37+"")
                        .add("mob_phone", phone)
                        .add("address", address)
                        .add("area_info", city2)
                        .add("is_default", 1+"")
                        .build();
                Request request = new Request.Builder()
                        .url("http://192.168.23.144/mobile/index.php?act=member_address&op=address_add")
                        .post(formBody)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddActivity.this, "添加成功...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddActivity.this,AddressActivity.class));
                                finish();
                            }
                        });
                    }
                });
            }
        });
    }
}
