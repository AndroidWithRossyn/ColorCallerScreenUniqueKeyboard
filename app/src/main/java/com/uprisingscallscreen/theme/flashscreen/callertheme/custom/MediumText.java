package com.uprisingscallscreen.theme.flashscreen.callertheme.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class MediumText extends TextView {
    public MediumText(Context context) {
        super(context);
        init();
    }

    public MediumText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public MediumText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/sf-ui-display-medium-58646be638f96.otf"));
    }
}
