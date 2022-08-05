package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.BoosterIncomeList;

import java.util.ArrayList;

import static android.graphics.Paint.UNDERLINE_TEXT_FLAG;

/**
 * Created by Palak Mehta on 10/7/2016.
 */
public class BoosterIncomeAdapter extends BaseAdapter {

   // private static ArrayList flat_no, block_no, customer_id, customer_name, payment_plan, amount_received, pending_amount, installment_amount, product_name, type, payment_percent;
    Activity activity;
    private ArrayList<BoosterIncomeList> arrayListBoosterIncomeList = new ArrayList<>();
    public BoosterIncomeAdapter(Activity activity, ArrayList<BoosterIncomeList> BoosterIncomeLists ) {
        super();
        this.activity = activity;
        arrayListBoosterIncomeList.clear();
        arrayListBoosterIncomeList.addAll(BoosterIncomeLists);
    }

    @Override
    public int getCount() {
        return arrayListBoosterIncomeList.size();
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
        TextView indexTextView, unitTextView, perTextView;
        ProgressBar progressBar;
        LinearLayout lnBoosterIncome;
    }

    private class ViewHolder2 {
        TextView cid, cname, pPlan, amtRec, pendingamt, insamt, proname, tp;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.list_booster_income, null);
            holder1 = new ViewHolder1();
            holder1.indexTextView = (TextView) convertView.findViewById(R.id.booster_index);
            holder1.unitTextView = (TextView) convertView.findViewById(R.id.unit_no);
            holder1.perTextView = (TextView) convertView.findViewById(R.id.ahd_payment_percent);
            holder1.progressBar = (ProgressBar) convertView.findViewById(R.id.booster_bar);
            holder1.lnBoosterIncome = (LinearLayout) convertView.findViewById(R.id.ln_booster_income);
            convertView.setTag(holder1);
        }
        else {
            holder1 = (ViewHolder1) convertView.getTag();
        }


        if(position < arrayListBoosterIncomeList.size()) {
          /*  if(position %2 == 1)
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
                holder1.lnBoosterIncome.setBackgroundResource(R.color.table_bg_odd);

            } else {
                // Set the background color for alternate row/item
                holder1.lnBoosterIncome.setBackgroundResource(R.color.table_bg_even);
            }
            //listAnimation(convertView.getContext(), convertView, position);
            /*holder1.indexTextView.setText(String.valueOf(position+1));
            holder1.unitTextView.setText(block_no.get(position).toString() + " - " + flat_no.get(position).toString());
            holder1.unitTextView.setPaintFlags(holder1.unitTextView.getPaintFlags() | UNDERLINE_TEXT_FLAG);
            if (type.get(position).toString().equals("Buy")) {
                holder1.unitTextView.setTextColor(activity.getResources().getColor(R.color.green));
            }*/

            holder1.indexTextView.setText(String.valueOf(position+1));
            holder1.unitTextView.setText(arrayListBoosterIncomeList.get(position).getUnit());
            holder1.unitTextView.setPaintFlags(holder1.unitTextView.getPaintFlags() | UNDERLINE_TEXT_FLAG);
            if (arrayListBoosterIncomeList.get(position).getSaleType().equals("Buy")) {
                holder1.unitTextView.setTextColor(activity.getResources().getColor(R.color.green));
            }
            holder1.unitTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder2 holder2 = null;
                    LayoutInflater mInflater2 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog1 = new Dialog(activity);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView1 = null;
                    if (convertView1 == null) {
                        convertView1 = mInflater2.inflate(R.layout.popup_booster_income, null);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.setContentView(convertView1);
                        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder2 = new ViewHolder2();
                        holder2.cid = (TextView) convertView1.findViewById(R.id.customer_id1);
                        holder2.cname = (TextView) convertView1.findViewById(R.id.customer_name1);
                        holder2.pPlan = (TextView) convertView1.findViewById(R.id.payment_plan1);
                        holder2.amtRec = (TextView) convertView1.findViewById(R.id.amount_received1);
                        holder2.pendingamt = (TextView) convertView1.findViewById(R.id.pending_amount1);
                        holder2.insamt = (TextView) convertView1.findViewById(R.id.installment_amount1);
                        holder2.proname = (TextView) convertView1.findViewById(R.id.product_Name1);
                        holder2.tp = (TextView) convertView1.findViewById(R.id.typeof1);
                        convertView1.setTag(holder2);
                    }
                    else {
                        holder2 = (ViewHolder2) convertView1.getTag();
                    }

                   /* holder2.cid.setText(customer_id.get(position).toString());
                    holder2.cname.setText(customer_name.get(position).toString());
                    holder2.pPlan.setText(payment_plan.get(position).toString());
                    holder2.amtRec.setText((activity.getResources().getString(R.string.Rs))+" " +amount_received.get(position).toString());
                    holder2.pendingamt.setText((activity.getResources().getString(R.string.Rs))+" " +pending_amount.get(position).toString());
                    holder2.insamt.setText((activity.getResources().getString(R.string.Rs))+" " +installment_amount.get(position).toString());
                    holder2.proname.setText(product_name.get(position).toString());
                    holder2.tp.setText(type.get(position).toString());*/

                    holder2.cid.setText(arrayListBoosterIncomeList.get(position).getID());
                    holder2.cname.setText(arrayListBoosterIncomeList.get(position).getName());
                    holder2.pPlan.setText(arrayListBoosterIncomeList.get(position).getPaymentPlan());

                    //holder2.amtRec.setText((activity.getResources().getString(R.string.Rs))+" " +arrayListBoosterIncomeList.get(position).getAmountRecieved());
                    holder2.amtRec.setText(""+ Config.formatter.format(arrayListBoosterIncomeList.get(position).getAmountRecieved()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    //holder2.pendingamt.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListBoosterIncomeList.get(position).getPendingAmount());
                    holder2.pendingamt.setText(""+ Config.formatter.format(arrayListBoosterIncomeList.get(position).getPendingAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    //holder2.insamt.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListBoosterIncomeList.get(position).getInstallmentAmount());
                    holder2.insamt.setText(""+ Config.formatter.format(arrayListBoosterIncomeList.get(position).getInstallmentAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    holder2.proname.setText(arrayListBoosterIncomeList.get(position).getProductName());
                    holder2.tp.setText(arrayListBoosterIncomeList.get(position).getSaleType());
                    dialog1.show();
                }
            });

           /* holder1.progressBar.setProgress(Integer.parseInt(payment_percent.get(position).toString()));
            holder1.perTextView.setText(payment_percent.get(position).toString() + "%");*/

            holder1.progressBar.setProgress(arrayListBoosterIncomeList.get(position).getPercent());
            holder1.perTextView.setText(arrayListBoosterIncomeList.get(position).getPercent() + "%");
        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListBoosterIncomeList.clear();
    }
}
