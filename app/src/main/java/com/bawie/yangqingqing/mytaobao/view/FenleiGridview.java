package com.bawie.yangqingqing.mytaobao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class FenleiGridview extends GridView{
    public FenleiGridview(Context context) {
        super(context);
    }

    public FenleiGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FenleiGridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
