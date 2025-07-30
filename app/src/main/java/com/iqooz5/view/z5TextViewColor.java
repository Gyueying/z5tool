package com.iqooz5.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class z5TextViewColor extends TextView {
    private LinearGradient mLinearGradient;
    private Matrix mLinearMatrix;
    private TextPaint mPaint;
    private int mTranslate;
    private int mViewWidth;

    public z5TextViewColor(Context context) {
        super(context);
        this.mTranslate = 0;
    }

    public z5TextViewColor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTranslate = 0;
    }

    public z5TextViewColor(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTranslate = 0;
    }

    private void init(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(), "ToolboxTQfont.ttf"));
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (this.mLinearMatrix != null) {
                this.mTranslate += this.mViewWidth / 10;
                if (this.mTranslate > this.mViewWidth * 2) {
                    this.mTranslate = -this.mViewWidth;
                }
                this.mLinearMatrix.setTranslate(this.mTranslate, 0.0f);
                this.mLinearGradient.setLocalMatrix(this.mLinearMatrix);
                postInvalidateDelayed(120L);
                Thread.sleep(30L);
            }
        } catch (InterruptedException e) {
        }
    }

    @Override
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.mViewWidth == 0) {
            this.mViewWidth = getMeasuredWidth();
            this.mLinearMatrix = new Matrix();
            this.mLinearGradient = new LinearGradient(0.0f, 0.0f, this.mViewWidth, 0.0f, new int[]{Color.parseColor("#0067E7"), -1, Color.parseColor("#0000FF")}, (float[]) null, Shader.TileMode.REPEAT);
            this.mPaint = getPaint();
            this.mPaint.setShader(this.mLinearGradient);
        }
    }
}
