package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.ProductDescription;
import com.nebulacompanies.ibo.ecom.model.BannerImages;

import java.util.ArrayList;
import java.util.Random;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class TodaysDealProductsAdapter extends RecyclerView.Adapter<TodaysDealProductsAdapter.MyViewHolder> {

    private Context context;
    ArrayList<BannerImages> mArrayList = new ArrayList<>();
    String cityID;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.card_view)
        CardView cardView;




        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public TodaysDealProductsAdapter(Context context, ArrayList<BannerImages> mArrayList, String cityID) {
        this.context = context;
        this.mArrayList = mArrayList;
        this.cityID = cityID;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todays_deals_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        BannerImages product = mArrayList.get(position);
        Log.d("Today",position+" : "+product.getImage());
      /*  Glide.with(context)
                .load(product.getImage())
                .placeholder( getRandomDrawbleColor())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
              //  .thumbnail(0.1f)
                .into(holder.thumbnail);*/


       /* RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context).load(product.getImage())
                .dontAnimate()
                .transition(withCrossFade())
                .thumbnail(0.1f)
                .placeholder( getRandomDrawbleColor() )
                .apply(options)
                .into( holder.thumbnail);*/



        Glide.with(context)
                .load(product.getImage())
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy( DiskCacheStrategy.SOURCE )
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
                .into(holder.thumbnail);

        holder.cardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, ProductDescription.class );
                /*intent.putExtra( "ecom_id", product.getProductDetilsID() );
                intent.putExtra( "ecom_product_id", product.getProductId() );
                intent.putExtra( "ecom_product_quantity", product.getQuantity() );
                intent.putExtra( "ecom_pick_up_id", Integer.parseInt(cityID) );
*/
                intent.putExtra("catid", product.getCategoryID());
                intent.putExtra( "product_id",product.getProductId());
                intent.putExtra( "ebc_id",   product.getProductDetilsID()  );
                intent.putExtra( "product_quantity", product.getQuantity() );
                intent.putExtra( "ecom_pick_up_id", Integer.parseInt(cityID) );

                context.startActivity( intent );
            }
        } );


    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    private ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable( Color.parseColor("#9ACCCD")), new ColorDrawable(Color.parseColor("#8FD8A0")),
                    new ColorDrawable(Color.parseColor("#CBD890")), new ColorDrawable(Color.parseColor("#DACC8F")),
                    new ColorDrawable(Color.parseColor("#D9A790")), new ColorDrawable(Color.parseColor("#D18FD9")),
                    new ColorDrawable(Color.parseColor("#FF6772")), new ColorDrawable(Color.parseColor("#DDFB5C"))
            };


    public ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

}
