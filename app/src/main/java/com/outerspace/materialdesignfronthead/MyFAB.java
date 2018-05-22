package com.outerspace.materialdesignfronthead;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

public class MyFAB extends FloatingActionButton {

    private MyFABCallback fabCallback;

    public MyFAB(Context context) {
        super(context);
    }

    public MyFAB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFabCallback(MyFABCallback fabCallback) {
        this.fabCallback = fabCallback;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(fabCallback != null) {
            fabCallback.onFabSizeChanged(w, h);
        }
    }
}
