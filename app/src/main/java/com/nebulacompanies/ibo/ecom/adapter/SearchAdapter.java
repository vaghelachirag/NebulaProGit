package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.SearchModelDetails;
import com.nebulacompanies.ibo.ecom.utils.CustomItemClickListener;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Palak Mehta on 9/29/2016.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private ArrayList<SearchModelDetails> filteredUserList;
    private Context context;
    private CustomItemClickListener customItemClickListener;
    private boolean showMRP;

    public SearchAdapter(Context context,boolean showMRP, ArrayList<SearchModelDetails> userArrayList, CustomItemClickListener customItemClickListener) {
        this.context = context;
        this.filteredUserList = userArrayList;
        this.customItemClickListener = customItemClickListener;
        this.showMRP = showMRP;
        Log.d("Data::", "SearchAdapter " + showMRP);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        Glide.with(context)
                .load(filteredUserList.get(position).getSearchMainImage()).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(getRandomDrawbleColor())
                .into(viewHolder.thumbnail);
        viewHolder.name.setText(filteredUserList.get(position).getSearchName());
        String mrp = String.valueOf(filteredUserList.get(position).getSearchMRP());
        String sale = String.valueOf(filteredUserList.get(position).getSearchSalePrice());
        Log.d("Data::", "onBindViewHolder " + showMRP+" : "+mrp+" : "+sale);
        viewHolder.tvOfferPriceValue.setText("" + (showMRP?mrp:sale));
      //  viewHolder.tvOriginalPriceValue.setText("" + mrp);
    }

    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final MyTextViewEcom name;
        private final MyTextViewEcom tvOfferPriceValue;
      //  private MyTextViewEcom tvOriginalPriceValue;
        private final ImageView thumbnail;
        CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            name = (MyTextViewEcom) view.findViewById(R.id.name);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            tvOfferPriceValue = (MyTextViewEcom) view.findViewById(R.id.tv_offer_price_value);
          //  tvOriginalPriceValue = (MyTextViewEcom) view.findViewById(R.id.tv_original_price_value);
            cardView = (CardView) view.findViewById(R.id.card_view);

            cardView.setOnClickListener(v -> customItemClickListener.onItemClick(filteredUserList.get(getAdapterPosition()), getAdapterPosition()));

        }
    }


    private final ColorDrawable[] vibrantLightColorList =
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
}
