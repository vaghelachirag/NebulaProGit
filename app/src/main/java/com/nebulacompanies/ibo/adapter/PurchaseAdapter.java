package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.MySalesList;
import com.nebulacompanies.ibo.model.SalesTelecallerList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 10/20/2016.
 */

public class PurchaseAdapter extends BaseAdapter {

    Activity activity;
    //ArrayList<PurchaseValues> arrayList;
    ArrayList<MySalesList> arrayListMyPurchasesAavaasList = new ArrayList<>();
    ArrayList<SalesTelecallerList> salesTelecallerLists = new ArrayList<>();
    SalesTelecallerAdapter salesTelecallerAdapter;

    public PurchaseAdapter(Activity activity, ArrayList<MySalesList> MyPurchasesAavaasLists) {
        this.activity = activity;
        arrayListMyPurchasesAavaasList.clear();
        arrayListMyPurchasesAavaasList.addAll(MyPurchasesAavaasLists);

    }

    @Override
    public int getCount() {
        Log.i("INFO", "getCount :" + arrayListMyPurchasesAavaasList.size());
        return arrayListMyPurchasesAavaasList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListMyPurchasesAavaasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayListMyPurchasesAavaasList.indexOf(getItem(position));
    }

    private class ViewHolder1 {
        MyTextView pDate, apt;
        ImageView sInfo;
        LinearLayout lnMyPurchase;
    }

    private class ViewHolder2 {
        MyTextView sId, rec, inv, pPlan, tAmount, dp, disc, mi, nid;
    }

    private class ViewHolder3 {
        ListView listView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_my_purchases, null);
            holder1 = new ViewHolder1();
            holder1.pDate = (MyTextView) convertView.findViewById(R.id.purchase_date1);
            holder1.apt = (MyTextView) convertView.findViewById(R.id.apartment1);
            holder1.sInfo = (ImageView) convertView.findViewById(R.id.sale_info1);
            holder1.lnMyPurchase = (LinearLayout) convertView.findViewById(R.id.ln_my_purchase);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        final Typeface tf1 = Typeface.createFromAsset(activity.getAssets(), Config.FONT_STYLE);

        if (position < arrayListMyPurchasesAavaasList.size()) {
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
                holder1.lnMyPurchase.setBackgroundResource(R.drawable.nebula_effect_white);

            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
                holder1.lnMyPurchase.setBackgroundResource(R.drawable.nebula_effect);
            }
            //listAnimation(convertView.getContext(), convertView, position);
            // final PurchaseValues purchaseValues = arrayList.get(position);

            //old use
           /* holder1.pDate.setText(SetDateFormat3(arrayListMyPurchasesAavaasList.get(position).getPurchaseDate()));

            if (arrayListMyPurchasesAavaasList.get(position).getCategory().equals("Aavaas")) {
               // holder1.apt.setText(purchaseValues.getapartment());
                holder1.apt.setText(arrayListMyPurchasesAavaasList.get(position).getApartment());
            } else if (arrayListMyPurchasesAavaasList.get(position).getCategory().equals("AVS Hyderabad")) {
               // holder1.apt.setText(purchaseValues.gethyderabad_product());
                holder1.apt.setText(arrayListMyPurchasesAavaasList.get(position).getProductName());
            }*/

            holder1.pDate.setText(SetGmtTime(arrayListMyPurchasesAavaasList.get(position).getBookingDate()));
            holder1.pDate.setPaintFlags(holder1.pDate.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

           /* if (arrayListMyPurchasesAavaasList.get(position).getCategory().equals("Aavaas")) {
                // holder1.apt.setText(purchaseValues.getapartment());
                holder1.apt.setText(arrayListMyPurchasesAavaasList.get(position).getApartment());
            } else if (arrayListMyPurchasesAavaasList.get(position).getCategory().equals("AVS Hyderabad")) {
                // holder1.apt.setText(purchaseValues.gethyderabad_product());
                holder1.apt.setText(arrayListMyPurchasesAavaasList.get(position).getUnit());
            }*/
            holder1.apt.setText(arrayListMyPurchasesAavaasList.get(position).getUnit());
            holder1.pDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder2 holder2 = null;
                    LayoutInflater mInflater2 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog1 = new Dialog(activity);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView1 = null;
                    if (convertView1 == null) {
                        convertView1 = mInflater2.inflate(R.layout.popup_purchase_info, null);
                        dialog1.setContentView(convertView1);
                        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder2 = new ViewHolder2();
                        holder2.sId = (MyTextView) convertView1.findViewById(R.id.saleid_11);
                        holder2.rec = (MyTextView) convertView1.findViewById(R.id.receipt_11);
                        holder2.inv = (MyTextView) convertView1.findViewById(R.id.investment_11);
                        holder2.pPlan = (MyTextView) convertView1.findViewById(R.id.paymentplan_11);
                        holder2.tAmount = (MyTextView) convertView1.findViewById(R.id.tokenamount_11);
                        holder2.dp = (MyTextView) convertView1.findViewById(R.id.dp20_11);
                        holder2.disc = (MyTextView) convertView1.findViewById(R.id.discount_11);
                        holder2.mi = (MyTextView) convertView1.findViewById(R.id.monthlyinstallment_11);
                        holder2.nid = (MyTextView) convertView1.findViewById(R.id.nextinstallmentdate_11);
                        convertView1.setTag(holder2);
                    } else {
                        holder2 = (ViewHolder2) convertView1.getTag();
                    }

                   /* holder2.sId.setText(purchaseValues.getsale_id());
                    holder2.rec.setText((activity.getResources().getString(R.string.Rs))+" "+purchaseValues.getreceipt());
                    holder2.inv.setText((activity.getResources().getString(R.string.Rs))+" "+purchaseValues.getinvestment());*/


                   /* holder2.sId.setText(arrayListMyPurchasesAavaasList.get(position).getSaleID().toString());
                    holder2.rec.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMyPurchasesAavaasList.get(position).getReceipt());
                    holder2.inv.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMyPurchasesAavaasList.get(position).getInvestment());*/

                    holder2.sId.setText(arrayListMyPurchasesAavaasList.get(position).getSaleId().toString());

                    //holder2.rec.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMyPurchasesAavaasList.get(position).getReceipt());
                    holder2.rec.setText(" " + Config.formatter.format(arrayListMyPurchasesAavaasList.get(position).getReceipt()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    //holder2.inv.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMyPurchasesAavaasList.get(position).getInvestmentAmount());
                    holder2.inv.setText(" " + Config.formatter.format(arrayListMyPurchasesAavaasList.get(position).getInvestmentAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    //holder2.pPlan.setText(purchaseValues.getpayment_plan());


                    /*if(purchaseValues.getpayment_plan().contains("plan")) {
                        String plan = purchaseValues.getpayment_plan().replace(" plan", "");
                        holder2.pPlan.setText(plan);
                    }
                    else{
                        holder2.pPlan.setText(purchaseValues.getpayment_plan());
                    }
                    holder2.tAmount.setText((activity.getResources().getString(R.string.Rs))+" "+purchaseValues.gettoken_amount());
                    holder2.dp.setText((activity.getResources().getString(R.string.Rs))+" "+purchaseValues.getdp_20());
                    holder2.disc.setText(purchaseValues.getdiscount());
                    holder2.mi.setText((activity.getResources().getString(R.string.Rs))+" "+purchaseValues.getmonthly_installment());
                    holder2.nid.setText(SetDateFormat2(purchaseValues.getnext_installment_date()));*/


                    /*if(arrayListMyPurchasesAavaasList.get(position).getPaymentOptions().contains("plan")) {
                        String plan = arrayListMyPurchasesAavaasList.get(position).getPaymentOptions().replace(" plan", "");
                        holder2.pPlan.setText(plan);
                    }
                    else{
                        holder2.pPlan.setText(arrayListMyPurchasesAavaasList.get(position).getPaymentOptions());
                    }
                    holder2.tAmount.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMyPurchasesAavaasList.get(position).getTokenAmount());
                    holder2.dp.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMyPurchasesAavaasList.get(position).getC20Amount());
                    holder2.disc.setText(arrayListMyPurchasesAavaasList.get(position).getDiscount().toString());
                    holder2.mi.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMyPurchasesAavaasList.get(position).getInvestment());
                    holder2.nid.setText(SetDateFormat2(arrayListMyPurchasesAavaasList.get(position).getInstallmentDate()));*/


                    holder2.pPlan.setText(arrayListMyPurchasesAavaasList.get(position).getPaymentPlan());

                    //holder2.tAmount.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMyPurchasesAavaasList.get(position).getTokenAmount());
                    holder2.tAmount.setText(" " + Config.formatter.format(arrayListMyPurchasesAavaasList.get(position).getTokenAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    //holder2.dp.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMyPurchasesAavaasList.get(position).getDownPaymentAmount());
                    holder2.dp.setText(" " + Config.formatter.format(arrayListMyPurchasesAavaasList.get(position).getDownPaymentAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    holder2.disc.setText(arrayListMyPurchasesAavaasList.get(position).getDiscountAmount().toString());

                    //holder2.mi.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMyPurchasesAavaasList.get(position).getInvestmentAmount());
                    holder2.mi.setText(" " + Config.formatter.format(arrayListMyPurchasesAavaasList.get(position).getInvestmentAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    holder2.nid.setText(SetGmtTime(arrayListMyPurchasesAavaasList.get(position).getInstallmentDate()));
                    dialog1.show();
                }
            });

            holder1.lnMyPurchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder3 holder3 = null;
                    LayoutInflater mInflater3 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog2 = new Dialog(activity);
                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView2 = null;
                    if (convertView2 == null) {
                        convertView2 = mInflater3.inflate(R.layout.popup_telecaller_list, null);
                        dialog2.setContentView(convertView2);
                        dialog2.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog2.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder3 = new ViewHolder3();
                        holder3.listView = (ListView) convertView2.findViewById(R.id.listview_sales_telecallerlist);

                        convertView2.setTag(holder3);
                    } else {
                        holder3 = (ViewHolder3) convertView2.getTag();
                    }

                    salesTelecallerLists.clear();
                    for (int i = 0; i < arrayListMyPurchasesAavaasList.get(position).getPaymentSchedule().size(); i++) {
                        salesTelecallerLists.add(arrayListMyPurchasesAavaasList.get(position).getPaymentSchedule().get(i));
                    }

                    salesTelecallerAdapter = new SalesTelecallerAdapter(activity, salesTelecallerLists);
                    holder3.listView.setAdapter(salesTelecallerAdapter);
                    salesTelecallerAdapter.notifyDataSetChanged();

                    dialog2.show();
                }
            });

        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListMyPurchasesAavaasList.clear();
    }

}

