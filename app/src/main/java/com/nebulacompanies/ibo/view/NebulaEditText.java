package com.nebulacompanies.ibo.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.nebulacompanies.ibo.util.Config;

/**
 * Created by Palak Mehta on 30-Jan-18.
 */

public class NebulaEditText extends androidx.appcompat.widget.AppCompatEditText {

    public NebulaEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public NebulaEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NebulaEditText(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Config.FONT_STYLE);
        setTypeface(tf);

    }



}
