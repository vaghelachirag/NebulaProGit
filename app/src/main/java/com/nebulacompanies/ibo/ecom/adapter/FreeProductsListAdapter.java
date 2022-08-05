package com.nebulacompanies.ibo.ecom.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.FreeProductListModel;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FreeProductsListAdapter extends RecyclerView.Adapter<FreeProductsListAdapter.MyViewHolder> {

    private final Context context;
    ArrayList<FreeProductListModel.Datum> myCartsModel;

    int alreadysel = 0;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_mycart_name)
        MyTextViewEcom tvMyCartName;

        @BindView(R.id.tv_mycart_tablet)
        MyTextViewEcom tvQuntyDesc;

        MyTextViewEcom tvMyCartItemPrice;

        @BindView(R.id.product_count)
        MyTextViewEcom tvProductCount;

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.tv_pv_value)
        MyTextViewEcom tvCartPV;

        @BindView(R.id.tv_nv_value)
        MyTextViewEcom tvCartNV;

        @BindView(R.id.tv_bv_value)
        MyTextViewEcom tvCartBV;

        @BindView(R.id.tv_original_price_value)
        MyTextViewEcom tvMRP;

        @BindView(R.id.tv_mycart_item_price)
        MyTextViewEcom tvDiscountedPrice;

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.rd_selected)
        RadioButton rdSelect;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public FreeProductsListAdapter(Context context, ArrayList<FreeProductListModel.Datum> myCartsModel) {
        this.context = context;
        this.myCartsModel = myCartsModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_free_products_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FreeProductListModel.Datum myCart = myCartsModel.get(position);

        holder.tvMyCartName.setText(myCart.getProductName());
        holder.tvQuntyDesc.setText(myCart.getVolwt());
        holder.tvCartPV.setText(myCart.getPV().toString());
        holder.tvCartBV.setText(myCart.getBV().toString());
        holder.tvCartNV.setText(myCart.getNV().toString());
        holder.tvMRP.setText(myCart.getMRPPrice().toString());
        holder.tvMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvProductCount.setText(myCart.getCartQuantity().toString());
        holder.tvDiscountedPrice.setText(myCart.getDistributorPrice().toString());

        holder.rdSelect.setOnCheckedChangeListener(null);
        boolean checkFree = myCart.getSelected() != null && myCart.getSelected();

        if (checkFree) {
            alreadysel = position;
        }
        holder.rdSelect.setChecked(checkFree);
        Glide.with(context)
                .load(myCart.getMainImage()).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_shopping_cart)
                .into(holder.thumbnail);

        holder.cardView.setOnClickListener
                (v -> {
            if (alreadysel != position) {
                myCartsModel.get(alreadysel).setSelected(false);
                myCart.setSelected(true);
                notifyDataSetChanged();
                // alreadysel = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCartsModel.size();
    }
}