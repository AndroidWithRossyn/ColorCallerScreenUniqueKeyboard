package com.uprisingscallscreen.theme.flashscreen.callertheme.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;


public class BoldText extends AppCompatTextView {
    public BoldText(Context context) {
        super(context);
        init();
    }

    public BoldText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public BoldText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/sf-ui-display-semibold-58646eddcae92.otf"));
    }
}
