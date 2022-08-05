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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductSizeAdapter  extends RecyclerView.Adapter<ProductSizeAdapter.ViewHolder>
{

    private int checkedPosition = -1;
    private Context context;
    String[] mySize = {"6","7","8","9","10"};

    public ProductSizeAdapter(Context context) {
        this.context = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private MyTextViewEcom sizeName;
        private LinearLayout linearLayout;
        private CardView cardView;
        public ViewHolder(View view) {
            super(view);
            sizeName = view.findViewById(R.id.product_size);
            cardView = view.findViewById(R.id.card_size);
            linearLayout = view.findViewById(R.id.laydata);
        }
    }

    @NonNull
    @Override
    public ProductSizeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_size, viewGroup, false);
        return new ProductSizeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSizeAdapter.ViewHolder holder, int position) {

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