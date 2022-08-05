package com.nebulacompanies.ibo.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.DashboardActivity;
import com.nebulacompanies.ibo.model.GetOfferImageResponse.GetOfferImageData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyViewPagerOfferAdapter  extends PagerAdapter {

    private LayoutInflater layoutInflater;

    Context context;
    ArrayList<GetOfferImageData>data ;
    public MyViewPagerOfferAdapter(Context context, ArrayList<GetOfferImageData>imageData) {
        this.context = context;
        data = imageData;
    }

    @Override
    public Object instantiateItem(@NotNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = layoutInflater.inflate(R.layout.item_offer_image, container, false);
        ImageView imageView = view.findViewById(R.id.iv_Offer);



        Glide.with(context)
                .load(data.get(position).getImagePath()) // image url
                .placeholder(R.drawable.placeholder) // any placeholder to load at start
                .error(R.drawable.placeholder)  // any image in case of error
                .into(imageView);  // imageview object



        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NotNull View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
