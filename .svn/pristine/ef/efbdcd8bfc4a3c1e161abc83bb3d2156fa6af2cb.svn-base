package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.ShowFullScreenEvents;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.model.SubEventList;
import com.nebulacompanies.ibo.walk.FontUtil;
import com.nebulacompanies.ibo.walk.SpotlightViewNew;

import java.util.ArrayList;

import cn.jzvd.AVLoadingIndicatorView;

/**
 * Created by Palak Mehta on 17-Jan-18.
 */

public class EventImagesAdapter extends BaseAdapter {

    Context context;
    ArrayList<SubEventList> arrayListEventList = new ArrayList<>();

    private int mItemHeight = 0;
    private RelativeLayout.LayoutParams mImageViewLayoutParams;

    String eventName;

    public EventImagesAdapter(Context context, ArrayList<SubEventList> eventLists, String eventName) {
        this.context = context;
        this.eventName = eventName;
        arrayListEventList.clear();
        arrayListEventList.addAll(eventLists);
        //mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageViewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return arrayListEventList.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arrayListEventList.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private class ViewHolder {
        ImageView cover;
        AVLoadingIndicatorView siteprogessAvLoadingIndicatorView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.grid_item, null);
            holder.cover = (ImageView) view.findViewById(R.id.picture1);
            holder.siteprogessAvLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.aviLoadingPackageSiteProgres);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Check the height matches our calculated column width
        if (holder.cover.getLayoutParams().height != mItemHeight) {
            holder.cover.setLayoutParams(mImageViewLayoutParams);
        }

        if (i < arrayListEventList.size()) {
            holder.siteprogessAvLoadingIndicatorView.setVisibility(View.VISIBLE);
            final ViewHolder finalHolder = holder;

          /*  Picasso.with(context).load(arrayListEventList.get(i).getEventThumbnail()).
                    placeholder(R.drawable.nebula_placeholder).into(holder.cover);*/

            Glide.with(context)
                    .load(arrayListEventList.get(i).getEventThumbnail())
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.nebula_effect)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                       boolean isFromMemoryCache, boolean isFirstResource) {
                            finalHolder.siteprogessAvLoadingIndicatorView.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.cover);


            ViewHolder finalHolder1 = holder;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences walkthrough = context.getSharedPreferences(PrefUtils.prefEventwalk, 0);
                    SharedPreferences skipMainGet = context.getSharedPreferences(PrefUtils.prefSkipmain, 0);
                    boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
                    SharedPreferences skipModuleGet = context.getSharedPreferences(PrefUtils.prefGuideskip, 0);
                    boolean isSkipModule = skipModuleGet.getBoolean("guideviewskipEvents", false);
                    if (!isSkipMain && !isSkipModule){
                        if (i == 1 && walkthrough.getBoolean("walkthrough_first_time_event_sup", true)) {
                            SpotlightViewNew spotLight = new SpotlightViewNew.Builder((Activity) context)
                                    .introAnimationDuration(400)
                                    .enableRevealAnimation(true)
                                    .performClick(true)
                                    .fadeinTextDuration(400)
                                    .setTypeface(FontUtil.get(context, "fonts/Montserrat-Regular.ttf"))
                                    .headingTvColor(Color.parseColor("#eb273f"))
                                    .headingTvSize(18)
                                    .headingTvText("Company Event")
                                    .subHeadingTvColor(Color.parseColor("#ffffff"))
                                    .subHeadingTvSize(14)
                                    .targetPadding(10)
                                    .subHeadingTvText("More about this section")
                                    .maskColor(Color.parseColor("#dc000000"))
                                    .target(finalHolder1.cover)
                                    .lineAnimDuration(400)
                                    .targetPadding(12)
                                    .lineAndArcColor(Color.parseColor("#eb273f"))
                                    .dismissOnTouch(false)
                                    .dismissOnBackPress(false)
                                    .enableDismissAfterShown(false)
                                    .show();

                            finalHolder1.cover.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i1 = new Intent(context, ShowFullScreenEvents.class);
                                    i1.putExtra("id", i);
                                    i1.putExtra("image_list", arrayListEventList);
                                    i1.putExtra("first_time_event_sup", true);
                                    i1.putExtra("eventName", eventName);
                                    i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    context.startActivity(i1);
                                    ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                                }
                            });


                            walkthrough.edit().putBoolean("walkthrough_first_time_event_sup", false).apply();
                        }
                    }


                }
            }, 3500);

        }
        return view;
    }

    public void clearData() {
        // clear the data
        arrayListEventList.clear();
    }


}