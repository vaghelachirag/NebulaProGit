package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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

import static com.nebulacompanies.ibo.util.Constants.ID_HAWTHORN_DWARKA;
import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 10/20/2016.
 */

public class SalesAdapter extends BaseAdapter {

    Activity activity;
    /* ArrayList<SalesValues> arrayList;*/
    ArrayList<MySalesList> arrayListMySalesAavaasList = new ArrayList<>();
    ArrayList<SalesTelecallerList> salesTelecallerLists = new ArrayList<>();
    SalesTelecallerAdapter salesTelecallerAdapter;
    int projectId;

    public SalesAdapter(Activity activity, ArrayList<MySalesList> MySalesAavaass, int projectId) {
        this.activity = activity;
        arrayListMySalesAavaasList.clear();
        arrayListMySalesAavaasList.addAll(MySalesAavaass);
        this.projectId = projectId;
    }

    @Override
    public int getCount() {
        return arrayListMySalesAavaasList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListMySalesAavaasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayListMySalesAavaasList.indexOf(getItem(position));
    }

    private class ViewHolder1 {
        LinearLayout linearLayout;
        View view;
        MyTextView cName, apt;
        ImageView cInfo, sInfo;
    }

    private class ViewHolder2 {
        MyTextView st, ct, mb, pc;
    }

    private class ViewHolder3 {
        LinearLayout monthlyLinearLayout, nextDatemonthlyLinearLayout;
        MyTextView si, pd, inv, pPlan, receipt, tAmount, dp, disc, mi, nid;
        View monthlyView, nextView, amountView;
    }

    private class ViewHolder4 {
        ListView listView;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_my_sales, null);
            holder1 = new ViewHolder1();
            holder1.linearLayout = (LinearLayout) convertView.findViewById(R.id.tablelayout16);
            holder1.view = (View) convertView.findViewById(R.id.apartment_view1);
            holder1.cName = (MyTextView) convertView.findViewById(R.id.cust_name_1);
            holder1.apt = (MyTextView) convertView.findViewById(R.id.apartment_1);
            holder1.cInfo = (ImageView) convertView.findViewById(R.id.cust_info_1);
            holder1.sInfo = (ImageView) convertView.findViewById(R.id.sale_info_1);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        final Typeface tf1 = Typeface.createFromAsset(activity.getAssets(), Config.FONT_STYLE);

        if (position < arrayListMySalesAavaasList.size()) {
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
            }


           /* for(int j = 0; j< arrayListMySalesAavaasList.size(); j++){
                salesTelecallerLists.add(j, arrayListMySalesAavaasList.get(j).getPaymentSchedule());
            }*/


            //salesTelecallerLists.addAll(arrayListMySalesAavaasList.get(position).getPaymentSchedule());
            //salesTelecallerLists = arrayListMySalesAavaasList.get(position).getPaymentSchedule();
            // Log.i("Payment Schedule : ", salesTelecallerLists.get(position).getInstallmentStatus() );


            //listAnimation(convertView.getContext(), convertView, position);
            // final SalesValues salesValues = arrayList.get(position);
            holder1.cName.setText(arrayListMySalesAavaasList.get(position).getCustomerName());
            holder1.cName.setPaintFlags(holder1.cName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            /*
            if (arrayListMySalesAavaasList.get(position).getCategory().equals("Aavaas")) {
                //holder1.apt.setText(salesValues.getapartment());
                holder1.apt.setText(arrayListMySalesAavaasList.get(position).getApartment());
            } else if (arrayListMySalesAavaasList.get(position).getCategory().equals("AVS Hyderabad")) {
                //holder1.apt.setText(salesValues.gethyderabad_product());
                holder1.apt.setText(arrayListMySalesAavaasList.get(position).getProductName());
            } else if (arrayListMySalesAavaasList.get(position).getCategory().equals("Dwarka")) {
                holder1.linearLayout.setWeightSum(10);
                holder1.apt.setVisibility(View.GONE);
                holder1.view.setVisibility(View.GONE);
            }*/
            if (arrayListMySalesAavaasList.get(position).getProductName().contains("Hawthorn Dwarka")) {
                holder1.linearLayout.setWeightSum(10);
                holder1.apt.setVisibility(View.GONE);
                holder1.view.setVisibility(View.GONE);
            }
            holder1.apt.setText(arrayListMySalesAavaasList.get(position).getUnit());
            holder1.cInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder2 holder2 = null;
                    LayoutInflater mInflater2 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog1 = new Dialog(activity);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView1 = null;
                    if (convertView1 == null) {
                        convertView1 = mInflater2.inflate(R.layout.popup_customer_info, null);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.setContentView(convertView1);
                        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder2 = new ViewHolder2();
                        holder2.st = (MyTextView) convertView1.findViewById(R.id.state_1);
                        holder2.ct = (MyTextView) convertView1.findViewById(R.id.city_1);
                        holder2.mb = (MyTextView) convertView1.findViewById(R.id.mobile_1);
                        holder2.pc = (MyTextView) convertView1.findViewById(R.id.pincode_1);
                        convertView1.setTag(holder2);
                    } else {
                        holder2 = (ViewHolder2) convertView1.getTag();
                    }

                    holder2.st.setText(arrayListMySalesAavaasList.get(position).getState());
                    holder2.ct.setText(arrayListMySalesAavaasList.get(position).getCity());
                    holder2.mb.setText(arrayListMySalesAavaasList.get(position).getMobile());
                    holder2.pc.setText(arrayListMySalesAavaasList.get(position).getPincode());

                    dialog1.show();
                }
            });

            holder1.cName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder3 holder3 = null;
                    LayoutInflater mInflater3 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog2 = new Dialog(activity);
                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView2 = null;
                    if (convertView2 == null) {
                        convertView2 = mInflater3.inflate(R.layout.popup_sale_info, null);
                        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog2.setContentView(convertView2);
                        dialog2.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog2.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder3 = new ViewHolder3();
                        holder3.si = (MyTextView) convertView2.findViewById(R.id.saleid_1);
                        holder3.pd = (MyTextView) convertView2.findViewById(R.id.purchasedate_1);
                        holder3.inv = (MyTextView) convertView2.findViewById(R.id.investment_1);
                        holder3.pPlan = (MyTextView) convertView2.findViewById(R.id.paymentplan_1);
                        holder3.tAmount = (MyTextView) convertView2.findViewById(R.id.tokenamount_1);
                        holder3.dp = (MyTextView) convertView2.findViewById(R.id.dp20_1);
                        holder3.disc = (MyTextView) convertView2.findViewById(R.id.discount_1);
                        holder3.mi = (MyTextView) convertView2.findViewById(R.id.monthlyinstallment_1);
                        holder3.nid = (MyTextView) convertView2.findViewById(R.id.nextinstallmentdate_1);
                        holder3.receipt = (MyTextView) convertView2.findViewById(R.id.receipt_1);
                        holder3.monthlyLinearLayout = (LinearLayout) convertView2.findViewById(R.id.monthly_inst_layout);
                        holder3.nextDatemonthlyLinearLayout = (LinearLayout) convertView2.findViewById(R.id.next_install_date_layout);
                        holder3.monthlyView = (View) convertView2.findViewById(R.id.monthly_view);
                        holder3.nextView = (View) convertView2.findViewById(R.id.next_view);
                        holder3.amountView = (View) convertView2.findViewById(R.id.amount_view);
                        convertView2.setTag(holder3);
                    } else {
                        holder3 = (ViewHolder3) convertView2.getTag();
                    }

                    /*holder3.si.setText(arrayListMySalesAavaasList.get(position).getSaleID().toString());
                    holder3.pd.setText(SetDateFormat1(arrayListMySalesAavaasList.get(position).getPurchaseDate()));
                    holder3.inv.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMySalesAavaasList.get(position).getInvestment());
                    if(arrayListMySalesAavaasList.get(position).getPaymentOptions().contains("plan")) {
                        String plan = arrayListMySalesAavaasList.get(position).getPaymentOptions().replace(" plan", "");
                        holder3.pPlan.setText(plan);
                    }
                    else{
                        holder3.pPlan.setText(arrayListMySalesAavaasList.get(position).getPaymentOptions());
                    }
                    holder3.tAmount.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMySalesAavaasList.get(position).getTokenAmount());
                    holder3.dp.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMySalesAavaasList.get(position).getC20Amount());
                    holder3.disc.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMySalesAavaasList.get(position).getDiscount());
                    holder3.mi.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMySalesAavaasList.get(position).getInstallment());

                    holder3.nid.setText(SetDateFormat2(arrayListMySalesAavaasList.get(position).getInstallmentDate()));
                    //holder3.nid.setText(salesValues.getnext_installment_date());

                    holder3.receipt.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMySalesAavaasList.get(position).getReceipt());*/

                    holder3.si.setText(arrayListMySalesAavaasList.get(position).getSaleId().toString());
                    holder3.pd.setText(SetGmtTime(arrayListMySalesAavaasList.get(position).getBookingDate()));

                    //holder3.inv.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMySalesAavaasList.get(position).getInvestmentAmount());
                    holder3.inv.setText(" " + Config.formatter.format(arrayListMySalesAavaasList.get(position).getInvestmentAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    holder3.pPlan.setText(arrayListMySalesAavaasList.get(position).getPaymentPlan());

                    //holder3.tAmount.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMySalesAavaasList.get(position).getTokenAmount());
                    holder3.tAmount.setText(" " + Config.formatter.format(arrayListMySalesAavaasList.get(position).getTokenAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    if (arrayListMySalesAavaasList.get(position).getDownPaymentAmount() == null) {
                        holder3.dp.setText(" " + Config.formatter.format(0).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));
                    } else {
                        holder3.dp.setText(" " + Config.formatter.format(arrayListMySalesAavaasList.get(position).getDownPaymentAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));
                    }
                    //holder3.dp.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMySalesAavaasList.get(position).getDownPaymentAmount());
                    //holder3.dp.setText(" "+ Config.formatter.format(arrayListMySalesAavaasList.get(position).getDownPaymentAmount()).replace(".00", ""));

                    //holder3.disc.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMySalesAavaasList.get(position).getDiscountAmount());
                    holder3.disc.setText(" " + Config.formatter.format(arrayListMySalesAavaasList.get(position).getDiscountAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    //holder3.mi.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMySalesAavaasList.get(position).getInstallmentAmount());
                    if (projectId == ID_HAWTHORN_DWARKA) {
                        holder3.monthlyLinearLayout.setVisibility(View.GONE);
                        holder3.nextDatemonthlyLinearLayout.setVisibility(View.GONE);
                        holder3.monthlyView.setVisibility(View.GONE);
                        holder3.amountView.setVisibility(View.GONE);
                    }

                    holder3.mi.setText(" " + Config.formatter.format(arrayListMySalesAavaasList.get(position).getInstallmentAmount()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    holder3.nid.setText(SetGmtTime(arrayListMySalesAavaasList.get(position).getInstallmentDate()));

                    //holder3.nid.setText(salesValues.getnext_installment_date());

                    //holder3.receipt.setText((activity.getResources().getString(R.string.Rs))+" "+arrayListMySalesAavaasList.get(position).getReceipt());
                    holder3.receipt.setText(" " + Config.formatter.format(arrayListMySalesAavaasList.get(position).getReceipt()).replace(".00", "").replace("Rs.", activity.getResources().getString(R.string.Rs)));

                    dialog2.show();
                }
            });

            holder1.sInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder4 holder4 = null;
                    LayoutInflater mInflater4 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog3 = new Dialog(activity);
                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView3 = null;
                    if (convertView3 == null) {
                        convertView3 = mInflater4.inflate(R.layout.popup_telecaller_list, null);
                        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog3.setContentView(convertView3);
                        dialog3.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog3.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder4 = new ViewHolder4();
                        holder4.listView = (ListView) convertView3.findViewById(R.id.listview_sales_telecallerlist);

                        convertView3.setTag(holder4);
                    } else {
                        holder4 = (ViewHolder4) convertView3.getTag();
                    }

                    salesTelecallerLists.clear();
                    for (int i = 0; i < arrayListMySalesAavaasList.get(position).getPaymentSchedule().size(); i++) {
                        salesTelecallerLists.add(arrayListMySalesAavaasList.get(position).getPaymentSchedule().get(i));
                    }

                    salesTelecallerAdapter = new SalesTelecallerAdapter(activity, salesTelecallerLists);
                    holder4.listView.setAdapter(salesTelecallerAdapter);
                    salesTelecallerAdapter.notifyDataSetChanged();

                    dialog3.show();
                }
            });

        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListMySalesAavaasList.clear();
    }

}

