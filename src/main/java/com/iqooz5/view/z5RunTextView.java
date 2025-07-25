package com.iqooz5.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class z5RunTextView extends TextView {
    public z5RunTextView(Context context) {
        super(context);
    }

    public z5RunTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public z5RunTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
