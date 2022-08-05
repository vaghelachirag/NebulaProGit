package com.nebulacompanies.ibo.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.nebulacompanies.ibo.ecom.utils.Config;


public class MyTextViewDesignBold extends androidx.appcompat.widget.AppCompatTextView {

    public MyTextViewDesignBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextViewDesignBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewDesignBold(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Config.FONT_STYLE);
        setTypeface(tf,Typeface.BOLD);

    }
}
