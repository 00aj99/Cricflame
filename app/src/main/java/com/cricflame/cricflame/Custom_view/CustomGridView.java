package com.cricflame.cricflame.Custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class CustomGridView extends GridView {
    int mHeight;

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == 1073741824) {
            width = widthSize;
        } else if (widthMode == Integer.MIN_VALUE) {
            width = Math.min(100, widthSize);
        } else {
            width = 100;
        }
        setMeasuredDimension(width, this.mHeight);
    }

    public void SetHeight(int height) {
        this.mHeight = height;
    }
}
