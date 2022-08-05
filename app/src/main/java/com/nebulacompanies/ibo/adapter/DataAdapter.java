package com.nebulacompanies.ibo.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.ProductListHoliday;
import com.nebulacompanies.ibo.activities.SalesHoliday;
import com.nebulacompanies.ibo.model.HolidayPackageDetail;

import java.util.ArrayList;

import cn.jzvd.AVLoadingIndicatorView;

import static com.nebulacompanies.ibo.util.Constants.ID_HOLIDAY;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<HolidayPackageDetail> holidayPackageDetails;
    private Context context;

    public DataAdapter(Context context, ArrayList<HolidayPackageDetail> holidayPackageDetails) {
        this.holidayPackageDetails = holidayPackageDetails;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.holiday_cardview_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        //viewHolder.tv_android.setText(android.get(i).getAndroid_version_name());
        //Picasso.with(context).load(holidayPackageDetails.get(i).image_url()).into(viewHolder.img_android);
        Glide.with(context)
                .load(holidayPackageDetails.get(i).image_url())
                .diskCacheStrategy( DiskCacheStrategy.ALL)
                .placeholder(R.drawable.load)
                .into(viewHolder.img_android);


      /*  Glide.with(context)
                .load(holidayPackageDetails.get(i).image_url())
                .placeholder(R.drawable.nebula_placeholder_land)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
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
                .into(viewHolder.img_android);*/






        viewHolder.img_android.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 0) {
                   /* Intent vac = new Intent(context, SalesHoliday.class);
                    context.startActivity(vac);*/
                    Intent vac = new Intent(context, SalesHoliday.class);
                    vac.putExtra("ProjectId",ID_HOLIDAY);
                    vac.putExtra("ProjectName","Holiday");
                    context.startActivity(vac);
                }

                else if (i == 1) {
                    Intent i3 = new Intent(context, ProductListHoliday.class);
                    context.startActivity(i3);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return holidayPackageDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_android;
        private ImageView img_android;
        private AVLoadingIndicatorView aviLoadingPlaceHolder;
        public ViewHolder(View view) {
            super(view);

            // tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView) view.findViewById(R.id.thumbnail);
            aviLoadingPlaceHolder = view.findViewById(R.id.aviLoadingHolidayPackageBackground);
            aviLoadingPlaceHolder.setVisibility(View.GONE);
        }
    }
}