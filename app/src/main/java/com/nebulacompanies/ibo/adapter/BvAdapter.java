package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.BvList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BvAdapter extends BaseAdapter {
    Activity activity;
    /* ArrayList<SalesValues> arrayList;*/
    ArrayList<BvList> arrayListBvList = new ArrayList<>();

    public BvAdapter(Activity activity, ArrayList<BvList> bvLists) {
        this.activity = activity;
        arrayListBvList.clear();
        arrayListBvList.addAll(bvLists);
    }

    @Override
    public int getCount() {
        return arrayListBvList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListBvList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayListBvList.indexOf(getItem(position));
    }

    private class ViewHolder1 {
        LinearLayout linearLayout;
        View view;
        MyTextView date, unit, pFor, rankBonus;
        LinearLayout lnRankBonus;
        // WaveLoadingView mWaveLoadingView;

    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_bv, null);
            holder1 = new ViewHolder1();
            holder1.linearLayout = (LinearLayout) convertView.findViewById(R.id.tablelayout16);
            holder1.view = (View) convertView.findViewById(R.id.apartment_view1);
            holder1.date = (MyTextView) convertView.findViewById(R.id.date);
            holder1.unit = (MyTextView) convertView.findViewById(R.id.unit);
            holder1.pFor = (MyTextView) convertView.findViewById(R.id.For_);
            holder1.rankBonus = (MyTextView) convertView.findViewById(R.id.Rankbonus);
            // holder1.mWaveLoadingView = (WaveLoadingView) convertView.findViewById(R.id.waveLoadingView);
            holder1.lnRankBonus = (LinearLayout) convertView.findViewById(R.id.ln_rank_bonus);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }


        if (position < arrayListBvList.size()) {
            if (position % 2 == 1) {
                // Set a background color for linearlayout regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
                holder1.lnRankBonus.setBackgroundResource(R.color.table_bg_odd);
                holder1.lnRankBonus.setBackgroundResource(R.drawable.nebula_effect_white);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
                holder1.lnRankBonus.setBackgroundResource(R.drawable.nebula_effect);
            }
            DecimalFormat formatter = new DecimalFormat("#,##,###");
            String strUnit= formatter.format(Integer.parseInt(arrayListBvList.get(position).getBvmin().toString()));
            String strBxMax= formatter.format(Integer.parseInt(arrayListBvList.get(position).getBvmax().toString()));
            holder1.date.setText("" + arrayListBvList.get(position).getId());
            holder1.unit.setText(strUnit);
            holder1.pFor.setText(strBxMax);
            holder1.rankBonus.setText(arrayListBvList.get(position).getCommission()+ "%");
            /*holder1.mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
            holder1.mWaveLoadingView.setCenterTitleColor(Color.RED);
            holder1.mWaveLoadingView.setBottomTitleSize(10);
            holder1. mWaveLoadingView.setBorderWidth(10);
            holder1.mWaveLoadingView.setAmplitudeRatio(60);
            holder1.mWaveLoadingView.setWaveColor(Color.RED);
            holder1.mWaveLoadingView.setBorderColor(Color.RED);
            holder1.mWaveLoadingView.setTopTitleStrokeColor(Color.WHITE);
            holder1. mWaveLoadingView.setTopTitleStrokeWidth(10);
            holder1.mWaveLoadingView.setAnimDuration(3000);
            holder1.mWaveLoadingView.pauseAnimation();
            holder1.mWaveLoadingView.resumeAnimation();
            holder1.mWaveLoadingView.cancelAnimation();
            holder1.mWaveLoadingView.startAnimation();
            holder1.mWaveLoadingView.setTopTitle(arrayListBvList.get(position).getCommission() + "%");
            holder1.mWaveLoadingView.setProgressValue((int) arrayListBvList.get(position).getDifferentials());*/
        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListBvList.clear();
    }
}
