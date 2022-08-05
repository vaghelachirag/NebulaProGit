package com.nebulacompanies.ibo.ecom.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


public class MyItalicTextViewEcom extends androidx.appcompat.widget.AppCompatTextView {

    public MyItalicTextViewEcom(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyItalicTextViewEcom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyItalicTextViewEcom(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Config.FONT_STYLE_ITALIC);
        setTypeface(tf);

    }
}
