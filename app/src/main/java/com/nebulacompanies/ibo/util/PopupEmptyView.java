package com.nebulacompanies.ibo.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

/**
 * Created by Sagar Virvani on 30-01-2018.
 */

public class PopupEmptyView {


    public static void  DisplayEmptyDialog(final Context mContext,int StatusCode)
    {
        LayoutInflater mInflater1 = (LayoutInflater)mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View convertView = mInflater1.inflate(R.layout.layout_empty_view, null);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.setContentView(convertView);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        LinearLayout llEmpty = (LinearLayout)convertView.findViewById(R.id.llEmpty);
        ImageView imgError = (ImageView)convertView. findViewById(R.id.imgError);
        MyTextView txtErrorTitle = (MyTextView)convertView. findViewById(R.id.txtErrorTitle);

        if (StatusCode==0){
            txtErrorTitle.setText(R.string.error_no_records);
        }
        else if (StatusCode==-1)
        {
            txtErrorTitle.setText(R.string.error_service_unavailable);
        }else if (StatusCode==-3){
            txtErrorTitle.setText(R.string.error_msg_network);
        }
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        //dialog.show();
        if(mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            if (!activity.isFinishing() && dialog != null) {
                dialog.show();
            }
        }
    }
}
