package com.bawie.yangqingqing.mytaobao.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.RegisterBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogActivity extends AppCompatActivity{

    private ImageView img;
    private EditText name;
    private EditText pwd;
    private Button button;
    private TextView forget;
    private TextView reg;
    private SharedPreferences preferences;
    private SharedPreferences.Editor et;
    //private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logactivity);

        //找到控件
        name = (EditText) findViewById(R.id.log_name);
        pwd = (EditText) findViewById(R.id.log_pwd);
        button = (Button) findViewById(R.id.log_bt);
        forget = (TextView) findViewById(R.id.log_forget);
        img = (ImageView) findViewById(R.id.log_img);
        reg = (TextView) findViewById(R.id.log_reg);
        preferences = getSharedPreferences("config", MODE_PRIVATE);
        et = preferences.edit();
        //final Intent intent = getIntent();
        //String username = intent.getStringExtra("username");
        //name.setText(username);
        //设置点击事件,进行登录
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("http://192.168.23.144/mobile/index.php?act=login");
            }
        });
        //设置点击事件,跳转到注册页面
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LogActivity.this,RegActivity.class);
                startActivity(intent);
                finish();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LogActivity.this,MyActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getData(String url){
        OkHttpClient client=new OkHttpClient();
        RequestBody formbody=new FormBody.Builder()
                .add("username",name.getText().toString())
                .add("password",pwd.getText().toString())
                .add("client","android")
                .build();
        final Request request=new Request.Builder()
                .url(url)
                .post(formbody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                System.out.println("=====login："+string);
                Gson gson = new Gson();
                final RegisterBean registerBean = gson.fromJson(string, RegisterBean.class);
                final int code = registerBean.getCode();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(name.getText().toString())||TextUtils.isEmpty(pwd.getText().toString())){
                            Toast.makeText(LogActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                        }else{
                            if (code==200){
                                String key = registerBean.getDatas().getKey();
                                System.out.println(key+"---------------");
                                et.putString("key",key);
                                et.putString("username",registerBean.getDatas().getUsername());
                                et.commit();
                                System.out.println("oooooooooooooooooooooooooooo"+key);
                                Toast.makeText(LogActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LogActivity.this,SecondActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LogActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }}
                    }
                });

            }
        });
    }


}
