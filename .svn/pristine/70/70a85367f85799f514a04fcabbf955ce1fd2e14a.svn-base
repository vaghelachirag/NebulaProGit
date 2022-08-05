package com.nebulacompanies.ibo.util;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.nebulacompanies.ibo.R;

/**
 * Created by Palak Mehta on 3/6/2017.
 */

public class Animation {
    public static void listAnimation(Context context, View view, int position){
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, (position > -1) ? R.anim.up_from_bottom : R.anim.down_from_top);
        view.startAnimation(animation);
       /* android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.list);
        animation.setStartOffset(position * 100);
        view.startAnimation(animation);*/
    }

}
