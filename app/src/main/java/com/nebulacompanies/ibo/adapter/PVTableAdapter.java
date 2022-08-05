package com.nebulacompanies.ibo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.BannerImages;
import com.nebulacompanies.ibo.model.PVTable;
import com.nebulacompanies.ibo.view.MyTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PVTableAdapter extends RecyclerView.Adapter<PVTableAdapter.MyViewHolder>  {


    private Context context;
    ArrayList<PVTable> mArrayList = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.families)
        MyTextView Families;

        @BindView(R.id.rank)
        MyTextView Rank;

        @BindView(R.id.PvToday)
        MyTextView PVToday;

        @BindView(R.id.weeklyPv)
        MyTextView WeeklyPV;

        @BindView(R.id.lifetimePv)
        MyTextView LifetimePV;

        @BindView(R.id.ln_pv_table)
        LinearLayout linearLayout;

        @BindView(R.id.horizontal_view)
        HorizontalScrollView horizontalView;

        @BindView(R.id.view_pv)
        View viewPV;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public PVTableAdapter(Context context, ArrayList<PVTable> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pv_table_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PVTable pvTable = mArrayList.get(position);

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
            holder.horizontalView.setVisibility(View.VISIBLE);
            holder.viewPV.setVisibility(View.VISIBLE);
         //   holder.horizontalView.setScrollbarFadingEnabled(false);
        }else {
            holder.horizontalView.setVisibility(View.GONE);
            holder.viewPV.setVisibility(View.GONE);
        }

        /*DecimalFormat formatter = new DecimalFormat("#,###,###");
        String yourFormattedString = formatter.format(""+pvTable.getPVToday());*/

        DecimalFormat formatter = new DecimalFormat("#,##,###");
        String strPVToday = formatter.format(Integer.parseInt(pvTable.getPVToday()));
        String strWeelyPV = formatter.format(Integer.parseInt(pvTable.getWeeklyPV()));
        String strLifeTimePV = formatter.format(Integer.parseInt(pvTable.getLifetimePV()));
            holder.Families.setText(pvTable.getFamilies());
            holder.Rank.setText(pvTable.getRank());
            holder.PVToday.setText(strPVToday);
            holder.WeeklyPV.setText(strWeelyPV);
            holder.LifetimePV.setText(strLifeTimePV);


    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }



}
