package com.bawie.yangqingqing.mytaobao.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.adapter.SousuoLsLv;
import com.bawie.yangqingqing.mytaobao.adapter.SousuoXiangqingAdapter;
import com.bawie.yangqingqing.mytaobao.bean.SousuoBean;
import com.bawie.yangqingqing.mytaobao.dao.SousuoSqlite;

import java.util.ArrayList;
import java.util.List;


public class SouSuoActivity extends AppCompatActivity{


    private ImageView iv;
    private EditText et;
    private Button bt;
    private TextView tv;
    private ListView lv;
    private Button delete;
    private SousuoSqlite sqlite;
    private List<String> list;
    private boolean flag;
    private SousuoLsLv adapter;
    private List<SousuoBean.DatasBean.GoodsListBean> goods_list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sousuoactivity);
        sqlite = new SousuoSqlite(this);
        list = sqlite.sele();
        //找到控件
        et = (EditText) findViewById(R.id.sousuo_et);
        bt = (Button) findViewById(R.id.sousuo_bt);
        tv = (TextView) findViewById(R.id.sousuo_lishi);
        lv = (ListView) findViewById(R.id.sousuo_listview);
        delete = (Button) findViewById(R.id.sousuo_dele);
        iv = (ImageView) findViewById(R.id.sousuo_back);
        goods_list = new ArrayList<>();
        //设置点击事件,跳转
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SouSuoActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
        //设置点击事件,搜索
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=et.getText().toString().trim();
                if (!TextUtils.isEmpty(title)){
                    if (list.size()!=0){
                        for (int i = 0; i <list.size() ; i++) {
                            if (title.equals(list.get(i))){
                                flag=true;
                            }
                        }if (flag==false){
                            sqlite.add(title);
                        }else{
                            sqlite.add(title);
                        }
                    }
                    Intent intent1=new Intent(SouSuoActivity.this,SousuoXiangqingActivity.class);
                    intent1.putExtra("sousuo",title);
                    startActivity(intent1);
                }
            }
        });

        adapter = new SousuoLsLv(this,list);
        lv.setAdapter(adapter);

        //删除历史记录
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlite.dele(0,"");
                list=sqlite.sele();
                adapter =new SousuoLsLv(SouSuoActivity.this,list);
                lv.setAdapter(adapter);
            }
        });
        //设置长按点击事件
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SouSuoActivity.this);
                dialog.setMessage("确认删除该历史记录？");
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list=new ArrayList<String>();
                        sqlite.dele(1,list.get(position));
                        list = sqlite.sele();
                        adapter=new SousuoLsLv(SouSuoActivity.this,list);
                        lv.setAdapter(adapter);
                    }
                });
                dialog.create().show();
                return true;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et.setText(list.get(position));
                Intent intent=new Intent(SouSuoActivity.this,SousuoXiangqingActivity.class);
                intent.putExtra("sousuo",list.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
