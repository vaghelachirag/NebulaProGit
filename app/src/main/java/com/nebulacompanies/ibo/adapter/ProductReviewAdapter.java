package com.nebulacompanies.ibo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.ProductColorsAdapter;
import com.nebulacompanies.ibo.ecom.model.ReviewsModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.internal.Util;

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.ViewHolder> {

    private Context context;
    List<ReviewsModel.Reviews> reviewsListModel = new ArrayList<>();
    Utils utils;

    public ProductReviewAdapter(Context context, List<ReviewsModel.Reviews> reviewsListModel) {
        this.context = context;
        this.reviewsListModel = reviewsListModel;
        utils = new Utils();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MyTextViewEcom txtStar, txtDesc, txtDate,txtTitle;
        MyBoldTextViewEcom txtName  ;

        public ViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.user_name);
            txtDesc = view.findViewById(R.id.desc);
            txtDate = view.findViewById(R.id.date);
            txtStar = view.findViewById(R.id.user_verified);
            txtTitle = view.findViewById(R.id.title);
        }
    }

    @NonNull
    @Override
    public ProductReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_product_review, viewGroup, false);
        return new ProductReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewsModel.Reviews reviewdata = reviewsListModel.get(position);
        holder.txtName.setText(reviewdata.getUserName());
        holder.txtDesc.setText(reviewdata.getDescription());

        long unixSeconds = reviewdata.getRatingDateLong(); // convert seconds to milliseconds
        Date date = new Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy ");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-0"));
        String formattedDate = sdf.format(date);
        holder.txtDate.setText(formattedDate);
        holder.txtStar.setText(String.valueOf(reviewdata.getRating()));
        holder.txtTitle.setText(reviewdata.getTitle());
    }

    @Override
    public int getItemCount() {
        return reviewsListModel.size();
    }
}
