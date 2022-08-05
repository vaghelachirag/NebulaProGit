package com.nebulacompanies.ibo.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.nebulacompanies.ibo.util.Config;

/**
 * Created by Palak Mehta on 8/25/2017.
 */

public class MyButton extends androidx.appcompat.widget.AppCompatButton {


    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        // TODO Auto-generated constructor stub
    }
    public MyButton(Context context) {
        super(context);
        init();
        // TODO Auto-generated constructor stub
    }
    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        // TODO Auto-generated constructor stub
    }
    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Config.FONT_STYLE);
        setTypeface(tf);

    }
}
