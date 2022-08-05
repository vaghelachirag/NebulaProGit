package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.MyCategoryActivity;
import com.nebulacompanies.ibo.ecom.MyLatestCategoryActivity;
import com.nebulacompanies.ibo.ecom.model.Category;
import com.nebulacompanies.ibo.ecom.utils.AdapterCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private final Context context;
    ArrayList<Category> categoryArrayList;
    int selectedPosition = -1;
    String jsonData;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textname)
        TextView name;

        @BindView(R.id.card_view)
        LinearLayout cardView;

        @BindView(R.id.img_category)
        ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public CategoryAdapter(Context context, ArrayList<Category> mArrayList, AdapterCallback callback) {
        this.context = context;
        this.categoryArrayList = mArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        selectedPosition = position;
        Category category = categoryArrayList.get(position);
        holder.name.setText(category.getCategoryName());

        Glide.with(context)
                .load(category.getCategoryImage())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.thumbnail);

        holder.cardView.setOnClickListener(v -> {
            for (int i = 0; i < categoryArrayList.size(); i++) {
                categoryArrayList.get(i).setSetSelected(false);
            }
            category.setSetSelected(true);
           Intent intent = new Intent(context, MyCategoryActivity.class);
          // Intent intent = new Intent(context, MyLatestCategoryActivity.class);
            Type collectionType = new TypeToken<ArrayList<Category>>() {}.getType();
            Gson gson = new Gson();
            jsonData = gson.toJson(categoryArrayList, collectionType);
            intent.putExtra("data", jsonData);
            intent.putExtra("selid", category.getProductID());
            intent.putExtra("selpos", position);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    private ColorDrawable[] vibrantLightColorList =
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
