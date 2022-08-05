package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.DownloadRateCharts;
import com.nebulacompanies.ibo.model.RateChartList;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.walk.FontUtil;
import com.nebulacompanies.ibo.walk.SpotlightView;

import java.util.ArrayList;


/**
 * Created by Palak Mehta on 12/29/2016.
 */
public class DownloadsAdapter extends RecyclerView.Adapter<DownloadsAdapter.DownloadsListHolder> {
    Context context;
    ArrayList<RateChartList> arrayListRateChartList = new ArrayList<>();
    int lastPosition = -1;
    String PREFS_WALKTHROUGH_DOWNLOAD = "reDownload";
    SpotlightView spotLightDownloads;

    public DownloadsAdapter(Context context, ArrayList<RateChartList> RateChartLists) {
        this.context = context;
        arrayListRateChartList.clear();
        arrayListRateChartList.addAll(RateChartLists);
        spotLightDownloads = new SpotlightView((Activity) context);
    }

    public class DownloadsListHolder extends RecyclerView.ViewHolder {

        // CardView cardViewPackage;
        ImageView thumbImageView;
        MyTextView txtName;

        public DownloadsListHolder(View convertView) {
            super(convertView);
            context = convertView.getContext();
            //  cardViewPackage = convertView.findViewById(R.id.card_view);
            thumbImageView = (ImageView) convertView.findViewById(R.id.list_item_image);
            txtName = (MyTextView) convertView.findViewById(R.id.list_item_name);
        }
    }

    @Override
    public DownloadsListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_image_name, parent, false);

        return new DownloadsListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DownloadsListHolder holder, final int position) {

        final RateChartList rateChartList = arrayListRateChartList.get(position);
        holder.txtName.setText(rateChartList.getProjectName());
        /*Glide.with(context).load(rateChartList.getProjectThumbnail())
                .transition( new DrawableTransitionOptions().crossFade() )
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.thumbImageView);*/


        Glide.with(context)
                .load(rateChartList.getProjectThumbnail())
                .placeholder(R.drawable.nebula_placeholder_land)
                // .error(R.drawable.imagenotfound)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        // log exception

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        return false;
                    }
                })
                .into(holder.thumbImageView);


       /* if (position == 1) {
            SharedPreferences walkthroughProduct = context.getSharedPreferences(PREFS_WALKTHROUGH_DOWNLOAD, 0);
            if (walkthroughProduct.getBoolean("walkthrough_first_time_download", true)) {


                 spotLightDownloads = new SpotlightView.Builder((Activity) context)
                        .introAnimationDuration(400)
                        .enableRevealAnimation(true)
                        .performClick(true)
                        .fadeinTextDuration(400)
                        .setTypeface(FontUtil.get(context, "fonts/Montserrat-Regular.ttf"))
                        .headingTvColor(Color.parseColor("#eb273f"))
                        .headingTvSize(18)
                        .headingTvText("Downloads")
                        .subHeadingTvColor(Color.parseColor("#ffffff"))
                        .subHeadingTvSize(16)
                        .subHeadingTvText("Click on the project to view related documents")
                        .maskColor(Color.parseColor("#dc000000"))
                        .target(holder.thumbImageView)
                        .lineAnimDuration(400)
                        .lineAndArcColor(Color.parseColor("#eb273f"))
                        .dismissOnTouch(false)
                        .dismissOnBackPress(false)
                        .enableDismissAfterShown(false)
                        .show();
                holder.thumbImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DownloadRateCharts.class);
                        intent.putExtra("ProductName", arrayListRateChartList.get(position).getProjectName());
                        intent.putExtra("ProductID", arrayListRateChartList.get(position).getRateChartId());
                        context.startActivity(intent);
                    }
                });
               *//* new GuideView.Builder(context)
                        .setTitle("Rate Chart")
                        .setContentText("You can see Rate Chart over here")
                        .setGravity(Gravity.center)
                        .setDismissType(DismissType.targetView)
                        .setTargetView(holder.itemView)
                        .setGuideListener(new GuideListener() {
                            @Override
                            public void onDismiss(View view) {
                                Intent intent = new Intent(context, DownloadRateCharts.class);
                                intent.putExtra("ProductName", arrayListRateChartList.get(position).getProjectName());
                                intent.putExtra("ProductID", arrayListRateChartList.get(position).getRateChartId());
                                context.startActivity(intent);
                            }
                        })
                        .build().show();*//*
            }
            walkthroughProduct.edit().putBoolean("walkthrough_first_time_download", false).apply();
        }*/

        if (position > lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.item_animation_fall_down);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return arrayListRateChartList.size();
    }

    public void clearData() {
        // clear the data
        arrayListRateChartList.clear();
    }


    public void hideSpotLight() {
        spotLightDownloads.setVisibility(View.GONE);
    }
}