package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;

public class ProductWeightAdapter extends RecyclerView.Adapter<ProductWeightAdapter.ViewHolder>
{

    private int checkedPosition = -1;
    private Context context;
    String[] mySize = {"250 gm","350 gm","500 gm","750 gm","1000 gm"};

    public ProductWeightAdapter(Context context) {
        this.context = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private MyTextViewEcom sizeName;
        private LinearLayout linearLayout;
        private CardView cardView;
        public ViewHolder(View view) {
            super(view);
            linearLayout = view.findViewById(R.id.laydata);
            sizeName = view.findViewById(R.id.product_size);
            cardView = view.findViewById(R.id.card_weight);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_weight, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (checkedPosition == position) {
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.nebula_new_dark));
            //Toast.makeText(context, "Selected", Toast.LENGTH_SHORT).show();

        } else {
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
            // do your stuff here like
            //Change  unselected item background color and Hide sub item views
        }

        if(position == 0)
            holder.sizeName.setText(mySize[0]);
        if(position == 1)
            holder.sizeName.setText(mySize[1]);
        if(position == 2)
            holder.sizeName.setText(mySize[2]);
        if(position == 3)
            holder.sizeName.setText(mySize[3]);
        if(position == 4)
            holder.sizeName.setText(mySize[4]);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedPosition == position) {
                    checkedPosition = -1;
                    notifyDataSetChanged();
                    return;
                }
                checkedPosition = position;
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
