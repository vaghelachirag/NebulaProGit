package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
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
import com.nebulacompanies.ibo.activities.ShowFullScreenSiteProgress;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.model.SiteProgressImageList;
import com.nebulacompanies.ibo.walk.FontUtil;
import com.nebulacompanies.ibo.walk.SpotlightListener;
import com.nebulacompanies.ibo.walk.SpotlightViewNew;

import java.util.ArrayList;

import cn.jzvd.AVLoadingIndicatorView;

public class NewSiteProgressImagesAdapter extends BaseAdapter {

    Activity context;
    ArrayList<SiteProgressImageList> arrayListSiteProgressImageList = new ArrayList<>();

    private int mItemHeight = 0;
    private RelativeLayout.LayoutParams mImageViewLayoutParams;

    boolean product_type_sub;
    String name,monthIntext,year;
    int productid,month;

    // FancyShowCaseView mFancyShowCaseView;
    public NewSiteProgressImagesAdapter(Activity context, ArrayList<SiteProgressImageList> siteProgressImageLists, boolean product_type_sub, String name, int productid, int month, String monthIntext, String year) {
        this.context = context;
        this.product_type_sub=product_type_sub;
        this.name=name;
        this.monthIntext=monthIntext;
        this.month=month;
        this.productid=productid;
        this.year=year;
        arrayListSiteProgressImageList.clear();
        arrayListSiteProgressImageList.addAll(siteProgressImageLists);

        mImageViewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public int getCount() {
        // TODO Auto-generated method stub
        return arrayListSiteProgressImageList.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arrayListSiteProgressImageList.get(position);
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
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

        if (i < arrayListSiteProgressImageList.size()) {
            //System.out.println("thumbnail : " + arrayListSiteProgressImageList.get(i).getThumbnail());
            //System.out.println("image : " + arrayListSiteProgressImageList.get(i).getSubImages());

            holder.siteprogessAvLoadingIndicatorView.setVisibility(View.VISIBLE);

            final ViewHolder finalHolder = holder;

            Glide.with(context)
                    .load(arrayListSiteProgressImageList.get(i).getThumbnail().replaceAll(" ", "%20"))
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
                    if (i == 1 && product_type_sub) {
                        SharedPreferences skipMainGet = context.getSharedPreferences(PrefUtils.prefSkipmain, 0);
                        boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
                        if (!isSkipMain) {

                            SpotlightViewNew spotLight = new SpotlightViewNew.Builder((Activity) context)
                                    .introAnimationDuration(400)
                                    .enableRevealAnimation(true)
                                    .performClick(true)
                                    .fadeinTextDuration(400)
                                    .setTypeface(FontUtil.get(context, "fonts/Montserrat-Regular.ttf"))
                                    .headingTvColor(Color.parseColor("#eb273f"))
                                    .headingTvSize(18)
                                    .headingTvText("Company Profile")
                                    .subHeadingTvColor(Color.parseColor("#ffffff"))
                                    .subHeadingTvSize(14)
                                    .targetPadding(10)
                                    .subHeadingTvText("Company Profile")
                                    .maskColor(Color.parseColor("#dc000000"))
                                    .target(finalHolder1.cover)
                                    .lineAnimDuration(400)
                                    .targetPadding(12)
                                    .setListener(new SpotlightListener() {
                                        @Override
                                        public void onUserClicked(String spotlightViewId) {
                                            Intent intent = new Intent(context, ShowFullScreenSiteProgress.class);
                                            intent.putExtra("id", i);
                                            intent.putExtra("image_list", arrayListSiteProgressImageList);




                                            context.startActivity(intent);
                                            ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                        }
                                    })
                                    .lineAndArcColor(Color.parseColor("#eb273f"))
                                    .dismissOnTouch(false)
                                    .dismissOnBackPress(false)
                                    .enableDismissAfterShown(false)
                                    .show();

                        }
                    }
                }
            }, 3000);




        }
        return view;
    }

    public void clearData() {
        // clear the data
        arrayListSiteProgressImageList.clear();
    }
}
