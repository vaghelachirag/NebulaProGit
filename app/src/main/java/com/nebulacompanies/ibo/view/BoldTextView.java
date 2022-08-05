package com.nebulacompanies.ibo.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nebulacompanies.ibo.util.Config;

/**
 * Created by Palak Mehta on 1/7/2017.
 */

public class BoldTextView extends TextView {

    public BoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFontStyle();
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontStyle();
    }

    public BoldTextView(Context context) {
        super(context);
        setFontStyle();
    }
    private void setFontStyle() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Config.FONT_STYLE_BOLD);
        setTypeface(tf);
    }

}