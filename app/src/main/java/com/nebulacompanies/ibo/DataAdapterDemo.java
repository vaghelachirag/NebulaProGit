package com.nebulacompanies.ibo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Random;

import androidx.recyclerview.widget.RecyclerView;

public class DataAdapterDemo extends RecyclerView.Adapter<DataAdapterDemo.ViewHolder> {
    private ArrayList<ImageUrlDemo> imageUrls;
    private Context context;

    public DataAdapterDemo(Context context, ArrayList<ImageUrlDemo> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;

    }

    @Override
    public DataAdapterDemo.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.image_layout_demo, viewGroup, false );
        return new ViewHolder( view );
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

      /*  RequestOptions options = new RequestOptions()
                .placeholder(getRandomDrawableColor())
                .diskCacheStrategy( DiskCacheStrategy.ALL);
       // Glide.with( context ).load( imageUrls.get( i ).getImageUrl() ).into( viewHolder.img );
        Glide.with(context).load(imageUrls.get( i ).getImageUrl())
                .apply(options)
                .into(viewHolder.img );*/


        Glide.with(context)
                .load(imageUrls.get( i ).getImageUrl())
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
                .into(viewHolder.img);




    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public ViewHolder(View view) {
            super( view );
            img = view.findViewById( R.id.imageView );
        }
    }



    public ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable( Color.parseColor( "#9ACCCD" ) ), new ColorDrawable( Color.parseColor( "#8FD8A0" ) ),
                    new ColorDrawable( Color.parseColor( "#CBD890" ) ), new ColorDrawable( Color.parseColor( "#DACC8F" ) ),
                    new ColorDrawable( Color.parseColor( "#D9A790" ) ), new ColorDrawable( Color.parseColor( "#D18FD9" ) ),
                    new ColorDrawable( Color.parseColor( "#FF6772" ) ), new ColorDrawable( Color.parseColor( "#DDFB5C" ) )
            };


    public ColorDrawable getRandomDrawableColor() {
        int idx = new Random().nextInt( vibrantLightColorList.length );
        return vibrantLightColorList[idx];
    }
}