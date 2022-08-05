package com.nebulacompanies.ibo.ecom.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.MyCategoryActivity;
import com.nebulacompanies.ibo.ecom.model.Category;
import com.nebulacompanies.ibo.ecom.utils.AdapterCallback;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.TELEPHONY_SERVICE;

public class CategoryDecsAdapter extends RecyclerView.Adapter<CategoryDecsAdapter.MyViewHolder> {

    private Context context;

    ArrayList<Category> categoryArrayList = new ArrayList<>();

    private AdapterCallback mAdapterCallback;

    private APIInterface mAPIInterface;
    //String m_deviceId;
    String m_deviceId;
    String uniqueID;
    Integer positionGet;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        MyBoldTextViewEcom name;

       /* @BindView(R.id.tv_category_decs)
        MyTextViewEcom tvCategoryDecs;*/

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.img_category)
        ImageView thumbnail;

        @BindView(R.id.rl)
        RelativeLayout rl;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public CategoryDecsAdapter(Context context, ArrayList<Category> mArrayList/*, AdapterCallback callback*/) {
        //public ProductsAdapter(Context context, ArrayList<Product> mArrayList) {
        this.context = context;
        this.categoryArrayList = mArrayList;
        // this.positionGet = position;

        // this.mAdapterCallback = callback;
        mAPIInterface = APIClient.getClient(context).create(APIInterface.class);

        /*TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        m_deviceId = TelephonyMgr.getDeviceId();*/


        m_deviceId = android.provider.Settings.Secure.getString(
                context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        SharedPreferences deviceSharedPreferences = context.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        Log.d("MDEVICEID product", "MDEVICEID product uniqueID: " + uniqueID);
        if (m_deviceId == null || m_deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = context.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid, randomID);
                editor.apply();
                m_deviceId = randomID;
            } else {
                m_deviceId = uniqueID;
            }
        }
        Log.d("MDEVICEID product", "MDEVICEID product: " + m_deviceId);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);


        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Category category = categoryArrayList.get(position);

        holder.name.setText(category.getCategoryName());
       // holder.tvCategoryDecs.setText(category.getCategoryDescription());

        Glide.with(context)
                .load(category.getCategoryImage()).fitCenter()
                .placeholder(R.drawable.ic_shopping_cart)
                .into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < categoryArrayList.size(); i++) {
                    categoryArrayList.get(i).setSetSelected(false);
                }
                category.setSetSelected(true);
                Intent intent = new Intent(context, MyCategoryActivity.class);
                Type collectionType = new TypeToken<ArrayList<Category>>() {
                }.getType();
                Gson gson = new Gson();
                String jsonData = gson.toJson(categoryArrayList, collectionType);

                intent.putExtra("data", jsonData);
                intent.putExtra("selid", category.getProductID());
                intent.putExtra("selpos", position);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.fade_in_new, R.anim.fade_out_new);
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                holder.thumbnail.performClick();

            }
        });
        Log.d("categoryPostionA", "categoryPostionA:" + positionGet);

        if (positionGet == category.getProductID()) {
            holder.rl.setBackgroundColor(Color.parseColor("#CDFFFF"));
        } else {
            holder.rl.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }


    @Override
    public int getItemCount() {

        return categoryArrayList.size();
    }


}
