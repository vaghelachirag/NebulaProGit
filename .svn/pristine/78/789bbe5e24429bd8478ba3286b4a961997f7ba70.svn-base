package com.nebulacompanies.ibo.ecom.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class MyBoldTextViewEcom extends androidx.appcompat.widget.AppCompatTextView {

    public MyBoldTextViewEcom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyBoldTextViewEcom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyBoldTextViewEcom(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Config.FONT_STYLE_BOLD);
        setTypeface(tf);

    }
}
