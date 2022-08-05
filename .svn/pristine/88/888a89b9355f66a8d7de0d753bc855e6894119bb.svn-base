package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.ChangodarBanner;
import com.nebulacompanies.ibo.customBanner.InfinitePagerAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.ProjectBanersList;

import java.util.ArrayList;

public class MockPagerBannerAdapter extends InfinitePagerAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;
    Utils utils = new Utils();

    private final ArrayList<ProjectBanersList> mList;

    public MockPagerBannerAdapter(Context context, ArrayList<ProjectBanersList> mList) {
        super();
        this.mContext = context;
        this.mList = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.card_layout, container, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        ProjectBanersList item = mList.get(position);
        holder.position = position;

        Glide.with(mContext).load(item.getImageUrl())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

        //SharedPreferences walkthroughBanner = mContext.getSharedPreferences(PREFS_WALKTHROUGH_BANNER, 0);


        // if (walkthroughBanner.getBoolean("walkthrough_first_time_banner", true)) {
/*SharedPreferences sp = mContext.getSharedPreferences("checkbox", 0);
boolean cb1 = sp.getBoolean("isLogin", false);
SharedPreferences skipMainGet = mContext.getSharedPreferences(PrefUtils.prefSkipmain, 0);
boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
if (!isSkipMain && !cb1) {
    holder.rlBannerItem.setClickable(false);
    holder.rlBannerItem.setEnabled(false);
} else {
    holder.rlBannerItem.setClickable(true);
    holder.rlBannerItem.setEnabled(true);
    *//*Intent cp = new Intent(mContext, ChangodarBanner.class);
    cp.putExtra("projectId",item.getProjectId());
    cp.putExtra("webUrl",item.getWebUrl());
    cp.putExtra("ProjectName",item.getProjectName());
    cp.putExtra("Product_type", false);
    cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    mContext.startActivity(cp);*//*
*//*if (position == 0) {
           Intent cp = new Intent(mContext, ChangodarBanner.class);
           cp.putExtra("projectId",item.getProjectId());
           cp.putExtra("webUrl",item.getWebUrl());
           cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
           mContext.startActivity(cp);
           ((Activity) mContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
       } else if (position == 1) {
           Intent cp = new Intent(mContext, ChennaiBanner.class);
           cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
           mContext.startActivity(cp);
           ((Activity) mContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

       } else if (position == 2) {
           Intent cp = new Intent(mContext, DwarkaBanner.class);
           cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
           mContext.startActivity(cp);
           ((Activity) mContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


       } else if (position == 3) {
           Intent cp = new Intent(mContext, HydarabadBanner.class);
           cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
           mContext.startActivity(cp);
           ((Activity) mContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
       }*//*
   }
   walkthroughBanner.edit().putBoolean("walkthrough_first_time_banner", false).apply();
}*/
        holder.rlBannerItem.setOnClickListener(view1 -> {
            if (Utils.isNetworkAvailable(mContext)) {
                Intent cp = new Intent(mContext, ChangodarBanner.class);
                cp.putExtra("projectId", item.getProjectId());
                cp.putExtra("webUrl", item.getWebUrl());
                cp.putExtra("ProjectName", item.getProjectName());
                cp.putExtra("Product_type", false);
                cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(cp);
            } else {
                // dialoguenoInternet();
                utils.dialogueNoInternet((Activity) mContext);
            }
        });
        return view;
    }

    /*public void dialoguenoInternet() {
        SweetAlertDialog loading = new SweetAlertDialog(mContext, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        loading.setCancelable(false);
        loading.setConfirmText("OK");
        loading.setCustomImage(R.drawable.ic_cloud_off_black_24dp);

        loading.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                com.nebulacompanies.ibo.view.MyTextView textTitle = (com.nebulacompanies.ibo.view.MyTextView) alertDialog.findViewById(R.id.title_text);
                com.nebulacompanies.ibo.view.MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                //btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                textTitle.setText(R.string.error_title);
                btnConfirm.setText("Ok");
                textContent.setText(R.string.error_content);

                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loading.dismiss();
                    }
                });
            }
        });
        loading.show();
    }*/

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private static class ViewHolder {
        public int position;
        private final ImageView image;
        private final FrameLayout rlBannerItem;
        public ViewHolder(View v) {
            image = v.findViewById(R.id.image_banner);
            rlBannerItem = v.findViewById(R.id.rl_banner_item);
        }
    }
}