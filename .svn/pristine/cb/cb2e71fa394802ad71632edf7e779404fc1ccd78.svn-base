package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.EnlargeItem;
import com.nebulacompanies.ibo.ecom.customBanner.InfinitePagerAdapter;
import com.nebulacompanies.ibo.ecom.model.BanersList;
import com.nebulacompanies.ibo.ecom.model.BanersListEcom;
import com.nebulacompanies.ibo.ecom.utils.AspectRatioImageView;

import java.util.ArrayList;

public class MockPagerBannerEcomCategoryAdapter extends InfinitePagerAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;

    private ArrayList<BanersList> mList = new ArrayList<>();

    public MockPagerBannerEcomCategoryAdapter(Context context, ArrayList<BanersList> mList) {
        super();
        this.mContext = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.card_layout_ecom, container, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        BanersList item = mList.get(position);
        holder.position = position;

       /* Glide.with(mContext).load(item.getuRL())
                .transition( new DrawableTransitionOptions().crossFade() )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .into(holder.image);*/


        Glide.with(mContext)
                .load(item.getuRL())
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy( DiskCacheStrategy.ALL )
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
                .into(holder.image);

        Log.d( "getImageUrl ","getImageUrl "+ item.getuRL() );



        return view;
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private static class ViewHolder {
        public int position;
        private ImageView image;
        private TextView appName;
        private FrameLayout rlBannerItem;
        private LinearLayout lnSetting;

        public ViewHolder(View v) {
            image = (ImageView) v.findViewById(R.id.image_banner);

            rlBannerItem = (FrameLayout) v.findViewById(R.id.rl_banner_item);

        }
    }
}