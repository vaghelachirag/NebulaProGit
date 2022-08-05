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
import com.nebulacompanies.ibo.model.WeeklyMatching;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeeklyMatchingAdapter extends RecyclerView.Adapter<WeeklyMatchingAdapter.MyViewHolder>
{



    private Context context;
    ArrayList<WeeklyMatching> mArrayList = new ArrayList<>();




    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date)
        MyTextView date;

        @BindView(R.id.PVF1)
        MyTextView PVF1;

        @BindView(R.id.PVF2)
        MyTextView PVF2;

        @BindView(R.id.matching)
        MyTextView matching;

        @BindView(R.id.weekly_cf)
        MyTextView tvCf;

        @BindView(R.id.weekly_cfpv2)
        MyTextView tvCfPv2;

        @BindView(R.id.ln_weekly_matching)
        LinearLayout linearLayout;

        @BindView(R.id.weekly_PVcf)
        LinearLayout linearLayoutCF;

        @BindView(R.id.weekly_cf2)
        LinearLayout linearLayoutCF2;

        @BindView(R.id.horizontal_weekly_matching)
        HorizontalScrollView horizontalScrollView;

        @BindView(R.id.view_weekly_matching)
        View viewWeeklymatching;

        @BindView(R.id.PVF1Name)
        MyTextView tvName;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public WeeklyMatchingAdapter(Context context, ArrayList<WeeklyMatching> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weekly_matching_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        WeeklyMatching weeklyMatching = mArrayList.get(position);

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
           holder.viewWeeklymatching.setVisibility(View.VISIBLE);
            //   holder.horizontalView.setScrollbarFadingEnabled(false);
        }else {
            holder.horizontalScrollView.setVisibility(View.GONE);
           holder.viewWeeklymatching.setVisibility(View.GONE);
        }

        DecimalFormat formatter = new DecimalFormat("#,##,###");
        String strPVF1= formatter.format(Integer.parseInt(weeklyMatching.getPVF1()));
        String strCarryForwardF1= formatter.format(Integer.parseInt(weeklyMatching.getCarryForward()));
        String strCarryForwardF2= formatter.format(Integer.parseInt(weeklyMatching.getCarryForwardPV2()));
        String strPVF2= formatter.format(Integer.parseInt(weeklyMatching.getPVF2()));
        String strMatching= formatter.format(Integer.parseInt(weeklyMatching.getMatching()));


        holder.date.setText(weeklyMatching.getFromDateString() + " to " + weeklyMatching.getToDateString());
        holder.tvName.setText("ID "+weeklyMatching.getIDName()+": ");
        holder.PVF1.setText(strPVF1);
       holder.tvCf.setText(strCarryForwardF1+")");
       holder.tvCfPv2.setText(strCarryForwardF2+")");
        holder.PVF2.setText(strPVF2);
        holder.matching.setText(strMatching);
        //holder.LifetimePV.setText(pvTable.getLifetimePV());

        int PvCf1 = Integer.parseInt(weeklyMatching.getCarryForward());
        int Pvcf2 = Integer.parseInt(weeklyMatching.getCarryForwardPV2());
        if( PvCf1 == 0) {
            holder.linearLayoutCF.setVisibility(View.GONE);
        }else
        {
            holder.linearLayoutCF.setVisibility(View.VISIBLE);
        }

        if(Pvcf2 == 0){
            holder.linearLayoutCF2.setVisibility(View.GONE);
        }
        else
        {
            holder.linearLayoutCF2.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }



}



