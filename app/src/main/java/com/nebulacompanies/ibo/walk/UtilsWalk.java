package com.nebulacompanies.ibo.walk;

import android.content.res.Resources;

/**
 * Created by jitender on 10/06/16.
 */

public class UtilsWalk {

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
