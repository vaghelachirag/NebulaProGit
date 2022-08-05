package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.customBanner.InfinitePagerAdapter;
import com.nebulacompanies.ibo.ecom.model.ImageBannerInfoEcom;
import com.nebulacompanies.ibo.ecom.utils.AspectRatioImageView;

import java.util.ArrayList;

import androidx.fragment.app.FragmentActivity;

public class MockPagerBannerEcomDashBoardAdapter extends InfinitePagerAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;

    private ArrayList<ImageBannerInfoEcom> mList = new ArrayList<>();

    public MockPagerBannerEcomDashBoardAdapter(FragmentActivity activity, ArrayList<ImageBannerInfoEcom> banersListEcom) {
        super();
        this.mContext = activity;
        this.mList = banersListEcom;
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
        ImageBannerInfoEcom item = mList.get(position);
        holder.position = position;

        Glide.with(mContext).load(item.getuRL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .into(holder.image);


        return view;
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private static class ViewHolder {
        public int position;
       private AspectRatioImageView image;
      //  private ImageView image;
        private TextView appName;
        private FrameLayout rlBannerItem;
        private LinearLayout lnSetting;

        public ViewHolder(View v) {
            image = (AspectRatioImageView) v.findViewById(R.id.image_banner);
           // image = (ImageView) v.findViewById(R.id.image_banner);
            rlBannerItem = (FrameLayout) v.findViewById(R.id.rl_banner_item);

        }
    }
}