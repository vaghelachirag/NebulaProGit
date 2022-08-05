package com.nebulacompanies.ibo.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * Created by Palak Mehta on 12/27/2016.
 */

public class AvailableSpace {

    public static float kilobytesAvailable() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable;
        if(Build.VERSION.SDK_INT >= 18){
            bytesAvailable = getAvailableBytes(stat);
        }
        else{
            //noinspection deprecation
            bytesAvailable = stat.getBlockSize() * stat.getAvailableBlocks();
        }

        Log.i("INFO", "getAvailableSpaceOnDevice in KB :" + bytesAvailable / (1024.f));
        return bytesAvailable / (1024.f);
    }

    public static float megabytesAvailable() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable;
        if(Build.VERSION.SDK_INT >= 18){
            bytesAvailable = getAvailableBytes(stat);
        }
        else{
            //noinspection deprecation
            bytesAvailable = stat.getBlockSize() * stat.getAvailableBlocks();
        }

        Log.i("INFO", "getAvailableSpaceOnDevice in MB :" + bytesAvailable / (1024.f * 1024.f));
        return bytesAvailable / (1024.f * 1024.f);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static long getAvailableBytes(StatFs stat) {
        return stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
    }

}
