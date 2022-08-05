package com.nebulacompanies.ibo.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import com.nebulacompanies.ibo.R;

/**
 * Created by Palak Mehta on 3/21/2017.
 */

public class InsufficientStorage {

    public static void InsufficientStorageInDevice(final Context context, String option){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.Update_Dialog));
        builder.setTitle(R.string.insufficient_storage);
        if(option.equals("Newsletters")) {
            builder.setMessage(R.string.view_device_message_newsletter);
        }
        else if(option.equals("Videos")){
            builder.setMessage(R.string.view_device_message_video);
        }
        else if(option.equals("EBrochures")){
            builder.setMessage(R.string.view_device_message_brochure);
        }
        else if(option.equals("BookingForms")){
            builder.setMessage(R.string.view_device_message_bookingform);
        }
        else if(option.equals("Leaflets")){
            builder.setMessage(R.string.view_device_message_leaflet);
        }
        else if(option.equals("RateCharts")){
            builder.setMessage(R.string.view_device_message_ratechart);
        }
        else if(option.equals("KYC")){
            builder.setMessage(R.string.view_device_message_kyc);
        }
        builder.setPositiveButton(R.string.view_storage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.cancel_storage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

}
