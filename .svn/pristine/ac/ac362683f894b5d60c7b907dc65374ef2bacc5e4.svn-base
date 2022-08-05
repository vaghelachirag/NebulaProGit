package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.BanersListEcom;
import com.nebulacompanies.ibo.ecom.model.CartModel;
import com.nebulacompanies.ibo.ecom.utils.AspectRatioImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListProductsAdapter extends RecyclerView.Adapter<ListProductsAdapter.MyViewHolder> {

    private Context context;
    ArrayList<BanersListEcom> mArrayList = new ArrayList<>();
    private onItemClick onItemClick;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_thumb)
        AspectRatioImageView thumbnail;

        @BindView(R.id.img_thumb_visible)
        ImageView imageViewHolder;

        @BindView(R.id.img_thumb_invisible)
        ImageView imageViewHolderInactive;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public ListProductsAdapter(Context context, ArrayList<BanersListEcom> mArrayList, onItemClick onItemClick) {
        this.context = context;
        this.mArrayList = mArrayList;
        this.onItemClick=onItemClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        BanersListEcom product = mArrayList.get(position);

        Glide.with(context)
                .load(product.getImageFile())
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);

        Log.d("Position ","Position: "+position);


        if (product.isSelected()){
            holder.imageViewHolder.setVisibility(View.VISIBLE);
            holder.imageViewHolderInactive.setVisibility(View.INVISIBLE);
        }else {
            holder.imageViewHolderInactive.setVisibility(View.VISIBLE);
            holder.imageViewHolder.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             // callBackMehod(position);
                onItemClick.onMethodCallbackMain(position);

                Log.d( "position Swe","position Swe "+ position );
            }
        });

      /* holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDescription.class);
                context.startActivity(intent);
            }
        });*/
    }

    public void callBackMehod( int position) {
        if (mArrayList.size()>0){
            for (int i = 0; i <mArrayList.size() ; i++) {
                if(i==position){
                    mArrayList.get(i).setSelected(true);
                }else {
                    mArrayList.get(i)
                            .setSelected(false);
                }
            }
        }
        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public interface onItemClick {
        void onMethodCallbackMain( int position);


    }

}
