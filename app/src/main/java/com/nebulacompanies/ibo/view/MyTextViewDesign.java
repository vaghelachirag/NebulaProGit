package com.nebulacompanies.ibo.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.nebulacompanies.ibo.ecom.utils.Config;


/**
 * Created by Palak Mehta on 8/25/2017.
 */

public class MyTextViewDesign extends androidx.appcompat.widget.AppCompatTextView {

    public MyTextViewDesign(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextViewDesign(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewDesign(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Config.FONT_STYLE);
        setTypeface(tf);

    }
}
