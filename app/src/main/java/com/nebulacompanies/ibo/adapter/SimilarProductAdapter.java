package com.nebulacompanies.ibo.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.ProductDescription;
import com.nebulacompanies.ibo.ecom.model.SubCategoryProductList;
import com.nebulacompanies.ibo.ecom.utils.AspectRatioImageView;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Const;
import com.nebulacompanies.ibo.util.Session;

import java.util.List;
import java.util.Random;

public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.ViewHolder> {

    private Context context;

    List<SubCategoryProductList> subCategoryProductListList;
    Session session;
    SharedPreferences spGetIsAssociate, spGetIsFirstPurchase;
    boolean mrpisAssociate, mrpisJoined30Days;
    int pvResult;

    public SimilarProductAdapter(Context context, List<SubCategoryProductList> subCategoryProductListList, int pvResult) {
        this.context = context;
        this.subCategoryProductListList = subCategoryProductListList;
        this.pvResult = pvResult;
        spGetIsAssociate = context.getSharedPreferences(PrefUtils.prefMrp, 0);
        mrpisAssociate = spGetIsAssociate.getBoolean(PrefUtils.prefMrp, false);

        spGetIsFirstPurchase = context.getSharedPreferences(PrefUtils.prefJoindays, 0);
        mrpisJoined30Days = spGetIsFirstPurchase.getBoolean(PrefUtils.prefJoindays, false);
        session = new Session(context);
        //  Log.d("categorydata:: ", mrpisAssociate+" -- " + pvResult+" -- "+ Const.PVValue+" -- "+mrpisJoined30Days);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private MyTextViewEcom productName, shortDesc, weight, offerPrice, OriginalPrice, origPricesign, priceDiscount;
        AspectRatioImageView imageView;
        LinearLayout laycontent;
        RatingBar ratingBar;

        public ViewHolder(View view) {
            super(view);

            productName = view.findViewById(R.id.name);
            shortDesc = view.findViewById(R.id.name_short_decs);
            weight = view.findViewById(R.id.product_weight);
            offerPrice = view.findViewById(R.id.tv_offer_price_value);
            OriginalPrice = view.findViewById(R.id.tv_original_price_value);
            imageView = view.findViewById(R.id.thumbnail);
            origPricesign = view.findViewById(R.id.tv_original_price);
            priceDiscount = view.findViewById(R.id.tv_price_discount);
            laycontent = view.findViewById(R.id.laycontent);
            ratingBar = view.findViewById(R.id.ratingBar);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.similar_product_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubCategoryProductList product = subCategoryProductListList.get(position);
       /*
        holder.productName.setText(product.getCategoryName());
        holder.shortDesc.setText(product.getCategoryShortDescription());
        holder.weight.setText(product.getCategoryWeight());
        holder.OriginalPrice.setText(""+product.getCategoryMRP());
        holder.offerPrice.setText(""+product.getCategoryMRP());

        Glide
                .with(context)
                .load(product.getCategoryThumbnailImage())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //.skipMemoryCache(true)
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(Bitmap arg0, GlideAnimation<? super Bitmap> arg1) {
                        // TODO Auto-generated method stub
                        holder.imageView.setImageBitmap(arg0);
                    }
                });*/
        if (!session.getLogin()) {
            holder.offerPrice.setText(String.valueOf(product.getCategoryMRP()));
            holder.OriginalPrice.setVisibility(View.GONE);
            holder.priceDiscount.setVisibility(View.GONE);
            holder.origPricesign.setVisibility(View.GONE);
        } else if (!mrpisAssociate) {
            holder.origPricesign.setVisibility(View.VISIBLE);
            holder.OriginalPrice.setVisibility(View.VISIBLE);
            holder.OriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.offerPrice.setText(String.valueOf(product.getCategorySalePrice()));
            holder.OriginalPrice.setText(String.valueOf(product.getCategoryMRP()));
            holder.priceDiscount.setText(product.getCategorySaving());
        } else if (pvResult >= Const.PVValue && !mrpisJoined30Days) {
            holder.origPricesign.setVisibility(View.VISIBLE);
            holder.OriginalPrice.setVisibility(View.VISIBLE);
            holder.OriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.offerPrice.setText(String.valueOf(product.getCategorySalePrice()));
            holder.OriginalPrice.setText(String.valueOf(product.getCategoryMRP()));
            holder.priceDiscount.setText(product.getCategorySaving());

        } else {
            //Log.d("Data:: ", " -- " + pvResult + " : " + product.getCategoryMRP());

            holder.offerPrice.setText(String.valueOf(product.getCategoryMRP()));
            holder.OriginalPrice.setVisibility(View.GONE);
            holder.priceDiscount.setVisibility(View.GONE);
            holder.origPricesign.setVisibility(View.GONE);
        }
                  /*  Log.d("mrpisAssociate"," :: "+mrpisAssociate);
                    holder.imgShare.setVisibility(mrpisAssociate?View.GONE:View.VISIBLE);*/
        //  holder.name.setText(product.getCategoryName());
        Double avgSimilarRat = product.getAverageRating();
        String rate = String.valueOf(avgSimilarRat);
        Double defRate = 4.5;
        String defRating = String.valueOf(defRate);
       /* if (Utils.showReviews) {
            if (rate.equals("0.0")) {
                holder.ratingBar.setRating(Float.parseFloat(defRating));
            } else {
                holder.ratingBar.setRating(Float.parseFloat(rate));
            }
        } else {
            holder.ratingBar.setRating(Float.parseFloat(defRating));
        }*/
        holder.ratingBar.setRating(Float.parseFloat(defRating));

        holder.shortDesc.setText(product.getCategoryShortDescription());
        holder.productName.setText(product.getCategoryName());
        holder.weight.setText(product.getCategoryWeight());
        //holder.name.setText( product.getCategoryCategoryName() );
   /*     holder.tvPVValue.setText("" + product.getCategoryPV());
        holder.tvBVValue.setText("" + product.getCategoryBV());
        holder.tvNVValue.setText("" + product.getCategoryNV());*/
        holder.imageView.setImageDrawable(getRandomDrawbleColor());


        DrawableRequestBuilder<String> thumbnailRequest = Glide
                .with(context)
                .load(product.getCategoryThumbnailImage());

        // pass the request as a a parameter to the thumbnail request
        Glide.with(context)
                .load(product.getCategoryMainImage())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(getRandomDrawbleColor())
                .into(holder.imageView);





      /*  Glide
                .with(context)
                .load(product.getCategoryThumbnailImage())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                //.skipMemoryCache(true)
                .dontAnimate()
                .placeholder(getRandomDrawbleColor())
                .into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(Bitmap arg0, GlideAnimation<? super Bitmap> arg1) {
                        // TODO Auto-generated method stub
                        holder.imageView.setImageBitmap(arg0);
                    }
                });*/
        holder.laycontent.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProductDescription.class);
           /* if (clicked) {
                intent.putExtra("product_clicked", "1");
            } else {*/
            intent.putExtra("product_clicked", "0");
            //}
            intent.putExtra("catid", product.getCategoryCategoryId());
            intent.putExtra("ebc_id", product.getCategoryId());
            intent.putExtra("quantity_count", product.getCategoryQuantity());
            intent.putExtra("product_id", product.getCategoryProductId());
            intent.putExtra("product_name", product.getCategoryName());
            intent.putExtra("product_offer_price", String.valueOf(product.getCategorySalePrice()));
            intent.putExtra("product_mrp_price", String.valueOf(product.getCategoryMRP()));
            intent.putExtra("product_desc", product.getCategoryDescription());
            intent.putExtra("product_saving", product.getCategorySaving());
            intent.putExtra("product_return", product.getCategoryReturnPolicy());
            intent.putExtra("product_warranty", product.getCategoryWarranty());
            intent.putExtra("product_quantity", product.getCategoryQuantity());
            intent.putExtra("product_MaxSaleQuantity", product.getCategoryMaxSaleQuantity());
            intent.putExtra("product_short_dec", product.getCategoryShortDescription());
            intent.putExtra("product_SKU", product.getCategorySKU());
            context.startActivity(intent);

        });
    }

    public ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#9ACCCD")), new ColorDrawable(Color.parseColor("#8FD8A0")),
                    new ColorDrawable(Color.parseColor("#CBD890")), new ColorDrawable(Color.parseColor("#DACC8F")),
                    new ColorDrawable(Color.parseColor("#D9A790")), new ColorDrawable(Color.parseColor("#D18FD9")),
                    new ColorDrawable(Color.parseColor("#FF6772")), new ColorDrawable(Color.parseColor("#DDFB5C"))
            };

    public ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    @Override
    public int getItemCount() {
        return subCategoryProductListList.size();
    }
}
