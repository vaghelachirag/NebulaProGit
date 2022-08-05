package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.ProductDescription;
import com.nebulacompanies.ibo.ecom.model.CartModel;
import com.nebulacompanies.ibo.ecom.model.Product;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWishListProductsAdapter extends RecyclerView.Adapter<MyWishListProductsAdapter.MyViewHolder> {

    private Context context;
   // private RealmResults<Product> products;
    private ProductsAdapterListener listener;
   // private RealmResults<CartItem> cartItems;
    ArrayList<CartModel> mArrayList = new ArrayList<>();
    private static int countItem = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        MyTextViewEcom name;

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.btn_add_to_cart)
        MyButtonEcom btnAddToCart;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public MyWishListProductsAdapter(Context context, ArrayList<CartModel> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_wish_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //getting the product of the specified position
        CartModel product = mArrayList.get(position);
        //binding the data with the viewholder views

        holder.name.setText(product.name);

        Glide.with(context)
                .load(product.imageUrl)
                .placeholder(R.drawable.ic_shopping_cart)
                .into(holder.thumbnail);


       holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDescription.class);
                context.startActivity(intent);
            }
        });

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Added to cart", Toast.LENGTH_SHORT).show();
            }
        });

    }



  /*  @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       // Product product = products.get(position);
        holder.name.setText(mArrayList.name());
      //  holder.price.setText(context.getString(R.string.price_with_currency, product.price));

       Glide.with(context)
                .load(mArrayList.imageUrl)
                .placeholder(R.drawable.ic_maps_and_location)
                .into(holder.thumbnail);

       *//* holder.icAdd.setOnClickListener(view -> {
            listener.onProductAddedCart(position, product);
        });

        holder.icRemove.setOnClickListener(view -> {
            listener.onProductRemovedFromCart(position, product);
        });

        if (cartItems != null) {
            CartItem cartItem = cartItems.where().equalTo("product.id", product.id).findFirst();
            if (cartItem != null) {
                holder.lblQuantity.setText(String.valueOf(cartItem.quantity));
                holder.icRemove.setVisibility(View.VISIBLE);
                holder.lblQuantity.setVisibility(View.VISIBLE);
            } else {
                holder.lblQuantity.setText(String.valueOf(0));
                holder.icRemove.setVisibility(View.GONE);
                holder.lblQuantity.setVisibility(View.GONE);
            }
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryActivity.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });*//*

    }*/

    /*public void setCartItems(RealmResults<CartItem> cartItems) {
      //  this.cartItems = cartItems;
        notifyDataSetChanged();
    }*/

    @Override
    public int getItemCount() {
        //return products.size();
        return mArrayList.size();
    }


    public interface ProductsAdapterListener {
        void onProductAddedCart(int index, Product product);

        void onProductRemovedFromCart(int index, Product product);
    }


}
