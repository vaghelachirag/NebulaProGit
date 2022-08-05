package com.nebulacompanies.ibo.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.GenerationIncomeList;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 9/22/2016.
 */
public class GenerationIncomeListViewAdapter extends BaseAdapter {

    //private static ArrayList closing_date, my_bv_, my_bv, bv_from_sales, fl_bv, dbv, mybvcommission, dbvcommission, totalbvcommission;
    Activity activity;
    private ArrayList<GenerationIncomeList> arrayListGenerationIncomeList = new ArrayList<>();

    public GenerationIncomeListViewAdapter(Activity activity, ArrayList<GenerationIncomeList> GenerationIncomeLists) {
        super();
        this.activity = activity;
        arrayListGenerationIncomeList.clear();
        arrayListGenerationIncomeList.addAll(GenerationIncomeLists);
    }

    @Override
    public int getCount() {
        return arrayListGenerationIncomeList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder1 {
        TextView closingDate, mybvper, total;
        ImageView info;
        LinearLayout lnRowGeneration;
    }

    private class ViewHolder2 {
        TextView bfs, flbv, mybv, mybvcomm, dbv_, dbvcomm;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_generation_income, null);
            holder1 = new ViewHolder1();
            holder1.closingDate = (TextView) convertView.findViewById(R.id.closingdate_geninc1);
            holder1.mybvper = (TextView) convertView.findViewById(R.id.mybv_per_geninc1);
            holder1.total = (TextView) convertView.findViewById(R.id.total_bv_comm_geninc1);
            holder1.info = (ImageView) convertView.findViewById(R.id.more_info1);
            holder1.lnRowGeneration = (LinearLayout) convertView.findViewById(R.id.ln_row_generation);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }


        if (position < arrayListGenerationIncomeList.size()) {
           /* if(position %2 == 1)
            {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
            }
            else
            {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
            }*/
            if (position % 2 == 1) {
                // Set a background color for linearlayout regular row/item

                convertView.setBackgroundResource(R.color.table_bg_odd);
                holder1.lnRowGeneration.setBackgroundResource(R.drawable.nebula_effect_white);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
                holder1.lnRowGeneration.setBackgroundResource(R.drawable.nebula_effect);
            }
            //listAnimation(convertView.getContext(), convertView, position);
            /*holder1.closingDate.setText(SetDateFormat(closing_date.get(position).toString()));
            holder1.mybvper.setText(my_bv_.get(position).toString());
            holder1.total.setText((activity.getResources().getString(R.string.Rs))+" "+totalbvcommission.get(position).toString());*/

            holder1.closingDate.setText(SetGmtTime(arrayListGenerationIncomeList.get(position).getDate()));
            holder1.mybvper.setText(String.valueOf(arrayListGenerationIncomeList.get(position).getMyBvPercentage()));

            //holder1.total.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListGenerationIncomeList.get(position).getTotalBVCommission());
            holder1.total.setText("" + Config.formatter.format(arrayListGenerationIncomeList.get(position).getTotalBVCommission()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

            holder1.lnRowGeneration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder2 holder2 = null;
                    LayoutInflater mInflater2 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog1 = new Dialog(activity);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView1 = null;
                    if (convertView1 == null) {

                        convertView1 = mInflater2.inflate(R.layout.popup_generation_income, null);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.setContentView(convertView1);
                        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder2 = new ViewHolder2();
                        holder2.bfs = (TextView) convertView1.findViewById(R.id.bv_from_sales1);
                        holder2.flbv = (TextView) convertView1.findViewById(R.id.fl_bv1);
                        holder2.mybv = (TextView) convertView1.findViewById(R.id.my_bv1);
                        holder2.mybvcomm = (TextView) convertView1.findViewById(R.id.my_bv_commission1);
                        holder2.dbv_ = (TextView) convertView1.findViewById(R.id.dbv1);
                        holder2.dbvcomm = (TextView) convertView1.findViewById(R.id.dbv_commission1);
                        convertView1.setTag(holder2);
                    } else {
                        holder2 = (ViewHolder2) convertView1.getTag();
                    }

                   /* holder2.bfs.setText(bv_from_sales.get(position).toString());
                    holder2.flbv.setText(fl_bv.get(position).toString());
                    holder2.mybv.setText(my_bv.get(position).toString());
                    holder2.mybvcomm.setText((activity.getResources().getString(R.string.Rs))+" "+mybvcommission.get(position).toString());
                    holder2.dbv_.setText(dbv.get(position).toString());
                    holder2.dbvcomm.setText((activity.getResources().getString(R.string.Rs))+" "+dbvcommission.get(position).toString());*/

                    holder2.bfs.setText(arrayListGenerationIncomeList.get(position).getBVFromSales().toString());
                    holder2.flbv.setText(arrayListGenerationIncomeList.get(position).getFLBV().toString());
                    holder2.mybv.setText(arrayListGenerationIncomeList.get(position).getPBV().toString());

                    //holder2.mybvcomm.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListGenerationIncomeList.get(position).getMyBVCommission());
                    holder2.mybvcomm.setText("" + Config.formatter.format(arrayListGenerationIncomeList.get(position).getMyBVCommission()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    holder2.dbv_.setText(String.valueOf(arrayListGenerationIncomeList.get(position).getDBV()));

                    //holder2.dbvcomm.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListGenerationIncomeList.get(position).getDBVCommission());
                    holder2.dbvcomm.setText("" + Config.formatter.format(arrayListGenerationIncomeList.get(position).getDBVCommission()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    dialog1.show();
                }
            });
        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListGenerationIncomeList.clear();
    }
}

