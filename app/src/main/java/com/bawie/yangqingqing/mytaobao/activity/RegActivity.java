package com.bawie.yangqingqing.mytaobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.RegisterBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Callback;
import okhttp3.Response;

public class RegActivity extends AppCompatActivity{

    private ImageView imageView;
    private EditText name;
    private EditText pwd;
    private EditText pwd1;
    private EditText email;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        //找到控件
        imageView = (ImageView) findViewById(R.id.reg_back);
        name = (EditText) findViewById(R.id.reg_name);
        pwd = (EditText) findViewById(R.id.reg_pwd);
        pwd1 = (EditText) findViewById(R.id.reg_pwd1);
        email = (EditText) findViewById(R.id.reg_email);
        button = (Button) findViewById(R.id.reg_button);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegActivity.this, MyActivity.class);
                startActivity(intent);
            }
        });

        //注册
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("http://192.168.23.144/mobile/index.php?act=login&op=register");
            }
        });
    }
    public void getData(String url){
        OkHttpClient client=new OkHttpClient();
        RequestBody formbody=new FormBody.Builder()
                .add("username",name.getText().toString())
                .add("password",pwd.getText().toString())
                .add("password_confirm",pwd1.getText().toString())
                .add("email",email.getText().toString())
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
                Gson gson = new Gson();
                final RegisterBean registerBean = gson.fromJson(string, RegisterBean.class);
                final int code = registerBean.getCode();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(name.getText().toString())||!TextUtils.isEmpty(pwd.getText().toString())||!TextUtils.isEmpty(pwd1.getText().toString())||!TextUtils.isEmpty(email.getText().toString())){
                            if (code==200){
                                Toast.makeText(RegActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegActivity.this,LogActivity.class);
                                intent.putExtra("username",registerBean.getDatas().getUsername());
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(RegActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(RegActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
