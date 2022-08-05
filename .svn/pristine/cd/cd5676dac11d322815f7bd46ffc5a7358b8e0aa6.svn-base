package com.nebulacompanies.ibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.CommunityGrowthIncome;

import com.nebulacompanies.ibo.view.MyTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityGrowthIncomeAdapter extends RecyclerView.Adapter<CommunityGrowthIncomeAdapter.MyViewHolder>
{

    private Context context;
    ArrayList<CommunityGrowthIncome> mArrayList = new ArrayList<>();




    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.community_growth_date)
        MyTextView date;

        @BindView(R.id.community_growth_PVF1)
        MyTextView PVF1;

        @BindView(R.id.community_growth_PVF2)
        MyTextView PVF2;

        @BindView(R.id.community_growth_matching)
        MyTextView matching;

        @BindView(R.id.community_growth)
        MyTextView community_growth;

        @BindView(R.id.ln_community_growth)
        LinearLayout linearLayout;

        @BindView(R.id.horizontal_community_growth)
        HorizontalScrollView horizontalScrollView;

        @BindView(R.id.view_community)
        View viewCommunity;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public CommunityGrowthIncomeAdapter(Context context, ArrayList<CommunityGrowthIncome> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.community_growth_income_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CommunityGrowthIncome communityGrowthIncome = mArrayList.get(position);

//
//        if (position < mArrayList.size()) {
//            if (position % 2 == 1) {
//                // Set a background color for linearlayout regular row/item
//                //linearLayout.setBackgroundResource(R.color.table_bg_odd);
//                holder.linearLayout.setBackgroundResource(R.color.table_bg_odd);
//                holder.linearLayout.setBackgroundResource(R.drawable.nebula_effect_white);
//            } else {
//                // Set the background color for alternate row/item
//                //convertView.setBackgroundResource(R.color.table_bg_even);
//                holder.linearLayout.setBackgroundResource(R.drawable.nebula_effect);
//            }
        if (position==0){
            holder.horizontalScrollView.setVisibility(View.VISIBLE);
            holder.viewCommunity.setVisibility(View.VISIBLE);
            //   holder.horizontalView.setScrollbarFadingEnabled(false);
        }else {
            holder.horizontalScrollView.setVisibility(View.GONE);
           holder.viewCommunity.setVisibility(View.GONE);
        }

        DecimalFormat formatter = new DecimalFormat("#,##,###");
        String strPVF1= formatter.format(Integer.parseInt(communityGrowthIncome.getPVF1()));
        String strPVF2= formatter.format(Integer.parseInt(communityGrowthIncome.getPVF2()));
        String strMatching= formatter.format(Integer.parseInt(communityGrowthIncome.getMatching()));
        String strAmount= formatter.format(Integer.parseInt(communityGrowthIncome.getTotalAmount()));


        holder.date.setText(communityGrowthIncome.getFromDateString() + " to " + communityGrowthIncome.getToDateString());
        holder.PVF1.setText(strPVF1);
        holder.PVF2.setText(strPVF2);
        holder.matching.setText(strMatching);
        holder.community_growth.setText(strAmount);
        //holder.LifetimePV.setText(pvTable.getLifetimePV());

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }



}
