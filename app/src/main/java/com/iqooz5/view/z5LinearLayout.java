package com.iqooz5.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;


public class z5LinearLayout extends LinearLayout {
    private static int[] s = {52547349};
    private int pivot = 0;
    private boolean superb = true;

    public z5LinearLayout(Context context) {
        super(context);
        setClickable(true);
    }

    public z5LinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setClickable(true);
    }

    public z5LinearLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setClickable(true);
    }

    public void closeSuperb() {
        this.superb = false;
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
                // 手指按下
            case MotionEvent.ACTION_DOWN:
                pivot = z5Anim.startAnimDown(this, superb, event.getX(), event.getY());
                break;

                // 触摸动作取消
            case MotionEvent.ACTION_CANCEL:
                // 手指抬起
            case MotionEvent.ACTION_UP:
                z5Anim.startAnimUp(this, pivot);
                break;

            default:
                break;
        }

        return super.onTouchEvent(event);
    }
    public void openSuperb() {
        this.superb = true;
    }
}
