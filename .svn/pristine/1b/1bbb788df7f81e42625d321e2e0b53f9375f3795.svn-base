package com.nebulacompanies.ibo.ecom.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.BanersListEcom;
import com.nebulacompanies.ibo.ecom.model.ECBBannerDetailsModel;
import com.nebulacompanies.ibo.ecom.utils.AspectRatioImageView;

import java.util.ArrayList;

public class EBCAdapter extends RecyclerView.Adapter<EBCAdapter.SiteProductsHolder> {
    private Context context;
    ArrayList<ECBBannerDetailsModel> arrayListSiteProductList = new ArrayList<>();
    int lastPosition = -1;

    String keyPro;

   /* RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.placeholder)
            .diskCacheStrategy( DiskCacheStrategy.ALL);*/

    public EBCAdapter(Activity context, ArrayList<ECBBannerDetailsModel> arrayListSiteProductList) {
        this.context = context;
        this.arrayListSiteProductList = arrayListSiteProductList;

    }

    public class SiteProductsHolder extends RecyclerView.ViewHolder {

        // CardView cardViewPackage;
        //ImageView imgEBC;
        AspectRatioImageView imgEBC;

        public SiteProductsHolder(View convertView) {
            super(convertView);
           // context = convertView.getContext();
            //  cardViewPackage = convertView.findViewById(R.id.card_view);
            // imgEBC = (ImageView) convertView.findViewById(R.id.img_ebc);
            imgEBC = (AspectRatioImageView) convertView.findViewById(R.id.img_ebc);

        }
    }

    @Override
    public SiteProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ebc, parent, false);


        return new SiteProductsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SiteProductsHolder holder, final int position) {

        final ECBBannerDetailsModel siteProductList = arrayListSiteProductList.get(position);

       /* Glide.with(context).load(siteProductList.getEbcImageFile())
                .transition( new DrawableTransitionOptions().crossFade() )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .into(holder.imgEBC);*/


        // Glide.with( context ).load( imageUrls.get( i ).getImageUrl() ).into( viewHolder.img );
       /* Glide.with(context).load(siteProductList.getEbcImageFile())
                .apply(options)
                .into(holder.imgEBC);*/

       // Log.d("image==", siteProductList.getEbcImageFile());

        Glide.with(context)
                .load(siteProductList.getEbcImageFile())
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
                //.centerCrop()
                .into(holder.imgEBC);

    }

    @Override
    public int getItemCount() {
        return arrayListSiteProductList.size();
    }


}