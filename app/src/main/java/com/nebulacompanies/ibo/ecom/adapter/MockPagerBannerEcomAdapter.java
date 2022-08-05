package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.EnlargeItem;
import com.nebulacompanies.ibo.ecom.customBanner.InfinitePagerAdapter;
import com.nebulacompanies.ibo.ecom.model.BanersListEcom;

import java.util.ArrayList;

public class MockPagerBannerEcomAdapter extends InfinitePagerAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final ArrayList<BanersListEcom> mList ;

    public MockPagerBannerEcomAdapter(Context context, ArrayList<BanersListEcom> mList) {
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
            view = mInflater.inflate(R.layout.card_layout, container, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        BanersListEcom item = mList.get(position);
        holder.position = position;

       /* Glide.with(mContext).load(item.getImageFile())
                .transition( new DrawableTransitionOptions().crossFade() )
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.placeholder)
                .into(holder.image);*/

        Log.d("img==", item.getImageFile());
        Glide.with(mContext)
                .load(item.getImageFile())
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.error(R.drawable.imagenotfound)
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


        holder.rlBannerItem.setOnClickListener(view1 -> {
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).setSelected(i == position);
            }
            Intent cp = new Intent(mContext, EnlargeItem.class);
            cp.putExtra("data", mList);
            Log.d("projectImage", "projectImage " + mList.size());
            Log.d("projectImage", "projectImage " + mList.toString());
            cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(cp);
        });
        return view;
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private static class ViewHolder {
        public int position;
        //private AspectRatioImageView image;
        private final ImageView image;

       // private TextView appName;
        private final FrameLayout rlBannerItem;
       // private LinearLayout lnSetting;

        public ViewHolder(View v) {
            // image = (AspectRatioImageView) v.findViewById(R.id.image_banner);
            image =  v.findViewById(R.id.image_banner);
            rlBannerItem =  v.findViewById(R.id.rl_banner_item);
        }
    }
}