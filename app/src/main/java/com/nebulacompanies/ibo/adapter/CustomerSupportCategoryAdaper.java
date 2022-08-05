package com.nebulacompanies.ibo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.OrderListActivity;
import com.nebulacompanies.ibo.ecom.CustomerSupport;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportCategory;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;

import java.util.ArrayList;

public class CustomerSupportCategoryAdaper extends RecyclerView.Adapter<CustomerSupportCategoryAdaper.ViewHolder>
{

    private Context context;
    ArrayList<CustomerSupportCategory.SupportList> supportListArrayList;


    public CustomerSupportCategoryAdaper(Context context,  ArrayList<CustomerSupportCategory.SupportList> supportListArrayList) {

        this.context = context;
        this.supportListArrayList = supportListArrayList;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private MyTextViewEcom tvCategoryName;

        public ViewHolder(@NonNull View view) {
            super(view);

            tvCategoryName = view.findViewById(R.id.tv_cs_categoryName);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_cs_category, viewGroup, false);
        return new ViewHolder(view);
    }

private long mLastClickTime = 0;

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        CustomerSupportCategory.SupportList data = supportListArrayList.get(position);
        Log.d("categoryName",""+data.getCategoryName());
        holder.tvCategoryName.setText(data.getCategoryName());
        holder.tvCategoryName.setOnClickListener(v->{
            // mis-clicking prevention, using threshold of 1000 ms
            if (SystemClock.elapsedRealtime() - mLastClickTime < 4000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

                if (data.getCategoryName().equalsIgnoreCase("my order")) {
                    Intent intent = new Intent(context, OrderListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // intent.putExtra("cat_sel", data.getCategoryId());
                    context.startActivity(intent);

                } else {
                    Intent intent = new Intent(context, CustomerSupport.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("cat_sel", data.getCategoryId());
                    context.startActivity(intent);

                }

        });

    }

    @Override
    public int getItemCount() {
        return supportListArrayList.size();
    }
}
