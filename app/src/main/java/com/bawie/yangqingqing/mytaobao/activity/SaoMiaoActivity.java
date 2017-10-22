package com.bawie.yangqingqing.mytaobao.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bawie.yangqingqing.mytaobao.R;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;

/**
 * Created by Administrator on 2017/10/10.
 */

public class SaoMiaoActivity extends AppCompatActivity{

    private ScannerView scanner_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saomiao);
        scanner_view = (ScannerView) findViewById(R.id.scanner_view);
        scanner_view.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void OnScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
                setResult(200,new Intent().putExtra("data",rawResult.getText()));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanner_view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanner_view.onPause();
    }
}
