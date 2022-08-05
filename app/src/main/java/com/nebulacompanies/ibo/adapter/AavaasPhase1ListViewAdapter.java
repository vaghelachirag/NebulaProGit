package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.model.ProductMasterList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import me.toptas.fancyshowcase.DismissListener;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;

/**
 * Created by Palak Mehta on 6/15/2016.
 */
public class AavaasPhase1ListViewAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<ProductMasterList> arrayListProductMaster = new ArrayList<>();
   // float notShow = 0;
    double notShow = 0.0;
    // double notShowDouble = 0.0;
    Dialog dialog1;

    public AavaasPhase1ListViewAdapter(Activity activity, ArrayList<ProductMasterList> productMasters) {
        super();
        this.activity = activity;
        arrayListProductMaster.clear();
        arrayListProductMaster.addAll(productMasters);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return arrayListProductMaster.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arrayListProductMaster.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private class ViewHolder1 {
        MyTextView name;
        //ImageView mrTextView;
        LinearLayout lnProduct;
    }

    private class ViewHolder2 {
        MyTextView bvtxt, dvtxt, rttxt, nvtokentxt, nvdptxt, nvbankloantxt,
                starpool, starpoolamount, threepoolPercent, threepoolamount, retails_advance_amount, star_pool_advance_amount,
                three_star_pool_advance_amount, sponsor_amount, sponsor_income, platinum_amount, platinum_rate, star_platinum_amount,
                star_platinum_rate, holiday_rate, holiday_amount, holiday_mini_amount,
                holiday_bonaza_rate, holiday_bonaza_amount, holiday_mini_rate;

        LinearLayout star_pool_layout, star_pool_amount_layout, three_pool_percent_layout,
                three_pool_amount_layout, retails_advance_amount_layout, star_pool_advance_layout,
                three_star_pool_advance_layout, sponsor_amount_layout,
                sponsor_income_layout, platinum_amount_layout, platinum_rate_layout, star_platinum_amount_layout,
                star_platinum_rate_layout, holiday_rate_layout, holiday_amount_layout,
                holiday_mini_amount_layout, holiday_bonaza_rate_layout, holiday_bonaza_amount_layout,
                nv_token_layout, nv_dp_layout, nv_bank_layout, holiday_mini_rate_layout,retail_profit_layout,bv_layout;

        View star_pool_view, star_pool_amount, three_pool_percent_view, three_pool_view,
                retails_advance_amount_view, star_pool_advance_view,
                three_star_pool_advance_view, sponsor_amount_view, sponsor_income_view,
                platinum_amount_view, platinum_rate_view, star_platinum_amount_view,
                star_platinum_rate_view, holiday_rate_view, holiday_amount_view,
                holiday_mini_amount_view, holiday_bonaza_rate_view, holiday_bonaza_amount_view,
                nv_token_view, nv_dp_view, nv_bank_view, holiday_mini_rate_view,retail_profit_view,bv_view;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_products, null);
            holder1 = new ViewHolder1();
            holder1.name = (MyTextView) convertView.findViewById(R.id.product_name);
            holder1.lnProduct = (LinearLayout) convertView.findViewById(R.id.ln_product);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        if (position < arrayListProductMaster.size()) {
           /* if(position %2 == 1)
            {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
                holder1.name.setBackgroundResource(R.drawable.nebula_effect_white);
            }
            else
            {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
                holder1.name.setBackgroundResource(R.drawable.nebula_effect);
            }*/

            if (position % 2 == 1) {
                // Set a background color for linearlayout regular row/item
                // holder1.lnProduct.setBackgroundResource(R.color.table_bg_odd);
                convertView.setBackgroundResource(R.color.table_bg_odd);
                holder1.name.setBackgroundResource(R.drawable.nebula_effect_white);
            } else {
                // Set the background color for alternate row/item
                //holder1.lnProduct.setBackgroundResource(R.color.table_bg_even);
                convertView.setBackgroundResource(R.color.table_bg_even);
                holder1.name.setBackgroundResource(R.drawable.nebula_effect);
            }
            //listAnimation(convertView.getContext(), convertView, position);

            if(arrayListProductMaster.get(position).getProductName().equalsIgnoreCase("Thailand Holiday (New)")){
                holder1.name.setText("Thailand Holiday");
            }
            else
            {
                holder1.name.setText(arrayListProductMaster.get(position).getProductName());
            }
            if (position == 1) {
                SharedPreferences walkthroughProduct = activity.getSharedPreferences(PrefUtils.preProductlist, 0);
                if (walkthroughProduct.getBoolean("walkthrough_first_time_product_detail", true)) {
                    new FancyShowCaseView.Builder(activity)
                            .focusOn(holder1.name)
                            //.closeOnTouch(true)
                            .title("Click to view details.")
                            //  .enableTouchOnFocusedView(true)
                            .focusBorderColor(R.color.nebula_new_light)
                            //.titleSize(20,20)
                            .titleStyle(R.style.TitleBarTextAppearance, Gravity.CENTER)
                            .focusShape(FocusShape.ROUNDED_RECTANGLE)
                            .dismissListener(new DismissListener() {
                                @Override
                                public void onDismiss(String id) {
                                    callData(position);
                                }

                                @Override
                                public void onSkipped(String id) {

                                }
                            })
                            .build()
                            .show();
                }
                walkthroughProduct.edit().putBoolean("walkthrough_first_time_product_detail", false).apply();
            }

            ViewHolder1 finalHolder = holder1;
            holder1.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callData(position);
                }
            });
        }

        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListProductMaster.clear();
    }

    private void callData(int position) {
        ViewHolder2 holder2 = null;
        LayoutInflater mInflater2 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        dialog1 = new Dialog(activity);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View convertView1 = null;
        if (convertView1 == null) {
            convertView1 = mInflater2.inflate(R.layout.popup_aavaas_phases_info, null);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog1.setContentView(convertView1);
            dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog1.setCancelable(true);
            holder2 = new ViewHolder2();
            holder2.bvtxt = (MyTextView) convertView1.findViewById(R.id.bv_per_value);
            holder2.nvtokentxt = (MyTextView) convertView1.findViewById(R.id.nv_token);
            holder2.nvdptxt = (MyTextView) convertView1.findViewById(R.id.nv_dp);
            holder2.nvbankloantxt = (MyTextView) convertView1.findViewById(R.id.nv_bank_loan);
            //modify Sagar virvani
            //add view,layout,textview
            //modify Sagar virvani
            //add view,layout,textview



            convertView1.setTag(holder2);
        } else {
            holder2 = (ViewHolder2) convertView1.getTag();
        }


        //1.bv
        holder2.bv_view = (View) convertView1.findViewById(R.id.bv_view);
        holder2.bv_layout = (LinearLayout) convertView1.findViewById(R.id.bv_layout);

        //2.RetailProfit
        holder2.rttxt = (MyTextView) convertView1.findViewById(R.id.ret_pro_value);
        holder2.retail_profit_view = (View) convertView1.findViewById(R.id.retail_profit_view);
        holder2.retail_profit_layout = (LinearLayout) convertView1.findViewById(R.id.retail_profit_layout);

        //3.star pool
        holder2.star_pool_view = (View) convertView1.findViewById(R.id.star_pool_view);
        holder2.star_pool_layout = (LinearLayout) convertView1.findViewById(R.id.star_pool_layout);
        holder2.starpool = (MyTextView) convertView1.findViewById(R.id.starpool);

        //4.star_pool_amount_layout
        //star pool amount
        holder2.star_pool_amount = (View) convertView1.findViewById(R.id.star_pool_amount);
        holder2.star_pool_amount_layout = (LinearLayout) convertView1.findViewById(R.id.star_pool_amount_layout);
        holder2.starpoolamount = (MyTextView) convertView1.findViewById(R.id.starpoolamount);

        //5.three pool percent
        holder2.three_pool_percent_view = (View) convertView1.findViewById(R.id.three_pool_percent_view);
        holder2.three_pool_percent_layout = (LinearLayout) convertView1.findViewById(R.id.three_pool_percent_layout);
        holder2.threepoolPercent = (MyTextView) convertView1.findViewById(R.id.threepoolPercent);

        //6.three pool amount
        holder2.three_pool_view = (View) convertView1.findViewById(R.id.three_pool_view);
        holder2.three_pool_amount_layout = (LinearLayout) convertView1.findViewById(R.id.three_pool_amount_layout);
        holder2.threepoolamount = (MyTextView) convertView1.findViewById(R.id.threepoolamount);

        //7.retails advance amount
        holder2.retails_advance_amount_view = (View) convertView1.findViewById(R.id.retails_advance_amount_view);
        holder2.retails_advance_amount_layout = (LinearLayout) convertView1.findViewById(R.id.retails_advance_amount_layout);
        holder2.retails_advance_amount = (MyTextView) convertView1.findViewById(R.id.retails_advance_amount);

        //8.star pool advance
        holder2.star_pool_advance_view = (View) convertView1.findViewById(R.id.star_pool_advance_view);
        holder2.star_pool_advance_layout = (LinearLayout) convertView1.findViewById(R.id.star_pool_advance_layout);
        holder2.star_pool_advance_amount = (MyTextView) convertView1.findViewById(R.id.star_pool_advance_amount);

        //9.three star pool advance
        holder2.three_star_pool_advance_view = (View) convertView1.findViewById(R.id.three_star_pool_advance_view);
        holder2.three_star_pool_advance_layout = (LinearLayout) convertView1.findViewById(R.id.three_star_pool_advance_layout);
        holder2.three_star_pool_advance_amount = (MyTextView) convertView1.findViewById(R.id.three_star_pool_advance_amount);

        //10.sponsor amount
        holder2.sponsor_amount_view = (View) convertView1.findViewById(R.id.sponsor_amount_view);
        holder2.sponsor_amount_layout = (LinearLayout) convertView1.findViewById(R.id.sponsor_amount_layout);
        holder2.sponsor_amount = (MyTextView) convertView1.findViewById(R.id.sponsor_amount);

        //11.sponsor income
        holder2.sponsor_income_view = (View) convertView1.findViewById(R.id.sponsor_income_view);
        holder2.sponsor_income_layout = (LinearLayout) convertView1.findViewById(R.id.sponsor_income_layout);
        holder2.sponsor_income = (MyTextView) convertView1.findViewById(R.id.sponsor_income);

        //12.platinum amount
        holder2.platinum_amount_view = (View) convertView1.findViewById(R.id.platinum_amount_view);
        holder2.platinum_amount_layout = (LinearLayout) convertView1.findViewById(R.id.platinum_amount_layout);
        holder2.platinum_amount = (MyTextView) convertView1.findViewById(R.id.platinum_amount);

        //13.platinum rate
        holder2.platinum_rate_view = (View) convertView1.findViewById(R.id.platinum_rate_view);
        holder2.platinum_rate_layout = (LinearLayout) convertView1.findViewById(R.id.platinum_rate_layout);
        holder2.platinum_rate = (MyTextView) convertView1.findViewById(R.id.platinum_rate);

        //14.star platinum amount
        holder2.star_platinum_amount_view = (View) convertView1.findViewById(R.id.star_platinum_amount_view);
        holder2.star_platinum_amount_layout = (LinearLayout) convertView1.findViewById(R.id.star_platinum_amount_layout);
        holder2.star_platinum_amount = (MyTextView) convertView1.findViewById(R.id.star_platinum_amount);

        //15.star platinum rate
        holder2.star_platinum_rate_view = (View) convertView1.findViewById(R.id.star_platinum_rate_view);
        holder2.star_platinum_rate_layout = (LinearLayout) convertView1.findViewById(R.id.star_platinum_rate_layout);
        holder2.star_platinum_rate = (MyTextView) convertView1.findViewById(R.id.star_platinum_rate);

        //16.holiday rate
        holder2.holiday_rate_view = (View) convertView1.findViewById(R.id.holiday_rate_view);
        holder2.holiday_rate_layout = (LinearLayout) convertView1.findViewById(R.id.holiday_rate_layout);
        holder2.holiday_rate = (MyTextView) convertView1.findViewById(R.id.holiday_rate);

        //17.holiday_amount_layout
        //holiday amount
        holder2.holiday_amount_view = (View) convertView1.findViewById(R.id.holiday_amount_view);
        holder2.holiday_amount_layout = (LinearLayout) convertView1.findViewById(R.id.holiday_amount_layout);
        holder2.holiday_amount = (MyTextView) convertView1.findViewById(R.id.holiday_amount);

        //18.holiday_mini_amount_layout
        //holiday mini amount
        holder2.holiday_mini_amount_view = (View) convertView1.findViewById(R.id.holiday_mini_amount_view);
        holder2.holiday_mini_amount_layout = (LinearLayout) convertView1.findViewById(R.id.holiday_mini_amount_layout);
        holder2.holiday_mini_amount = (MyTextView) convertView1.findViewById(R.id.holiday_mini_amount);

        //19.holiday mini rate
        holder2.holiday_mini_rate_view = (View) convertView1.findViewById(R.id.holiday_mini_rate_view);
        holder2.holiday_mini_rate_layout = (LinearLayout) convertView1.findViewById(R.id.holiday_mini_rate_layout);
        holder2.holiday_mini_rate = (MyTextView) convertView1.findViewById(R.id.holiday_mini_rate);

        //20.holiday bonaza rate
        holder2.holiday_bonaza_rate_view = (View) convertView1.findViewById(R.id.holiday_bonaza_rate_view);
        holder2.holiday_bonaza_rate_layout = (LinearLayout) convertView1.findViewById(R.id.holiday_bonaza_rate_layout);
        holder2.holiday_bonaza_rate = (MyTextView) convertView1.findViewById(R.id.holiday_bonaza_rate);

        //21.holiday bonaza amount
        holder2.holiday_bonaza_amount_view = (View) convertView1.findViewById(R.id.holiday_bonaza_amount_view);
        holder2.holiday_bonaza_amount_layout = (LinearLayout) convertView1.findViewById(R.id.holiday_bonaza_amount_layout);
        holder2.holiday_bonaza_amount = (MyTextView) convertView1.findViewById(R.id.holiday_bonaza_amount);

        //22.nv token
        holder2.nv_token_view = (View) convertView1.findViewById(R.id.nv_token_view);
        holder2.nv_token_layout = (LinearLayout) convertView1.findViewById(R.id.nv_token_layout);

        //23.nv dp
        holder2.nv_dp_view = (View) convertView1.findViewById(R.id.nv_dp_view);
        holder2.nv_dp_layout = (LinearLayout) convertView1.findViewById(R.id.nv_dp_layout);

        //24.nv_bank_layout
        holder2.nv_bank_layout = (LinearLayout) convertView1.findViewById(R.id.nv_bank_layout);
        //1.bv
        if (arrayListProductMaster.get(position).getbVPer()!=notShow)
        {
            holder2.bv_view.setVisibility(View.VISIBLE);
            holder2.bv_layout.setVisibility(View.VISIBLE);
        }

        //2.RetailProfit
        if (arrayListProductMaster.get(position).getRetailProfit()!= notShow)
        {
            holder2.retail_profit_view.setVisibility(View.VISIBLE);
            holder2.retail_profit_layout.setVisibility(View.VISIBLE);
        }

        //3.star_pool_layout
        //star pool
        if (arrayListProductMaster.get(position).getStarPoolPer()!=notShow)
        {
            holder2.star_pool_view.setVisibility(View.VISIBLE);
            holder2.star_pool_layout.setVisibility(View.VISIBLE);
        }

        //4.star_pool_amount_layout
        if (arrayListProductMaster.get(position).getStarPoolAmount()!=notShow)
        {
            holder2.star_pool_amount.setVisibility(View.VISIBLE);
            holder2.star_pool_amount_layout.setVisibility(View.VISIBLE);

        }
        //5.three_pool_percent_view
        if (arrayListProductMaster.get(position).getThreeStarPoolPercent()!=notShow)
        {
            holder2.three_pool_percent_view.setVisibility(View.VISIBLE);
            holder2.three_pool_percent_layout.setVisibility(View.VISIBLE);

        }

        //6.three_pool_amount_layout
        //three pool amount
        if (arrayListProductMaster.get(position).getThreeStarPoolAmount()!=notShow)
        {

            holder2.three_pool_view.setVisibility(View.VISIBLE);
            holder2.three_pool_amount_layout.setVisibility(View.VISIBLE);

        }
        //7.retails_advance_amount_layout
        if (arrayListProductMaster.get(position).getRetailsAdvanceAmount()!=notShow)
        {

            holder2.retails_advance_amount_view.setVisibility(View.VISIBLE);
            holder2.retails_advance_amount_layout.setVisibility(View.VISIBLE);
        }

        //8.star_pool_advance_layout
        if (arrayListProductMaster.get(position).getThreeStarPoolAdvanceAmount()!=notShow)
        {
            holder2.star_pool_advance_view.setVisibility(View.VISIBLE);
            holder2.star_pool_advance_layout.setVisibility(View.VISIBLE);

        }

        //9.three_star_pool_advance_layout
        if (arrayListProductMaster.get(position).getThreeStarPoolAdvanceAmount()!=notShow)
        {
            holder2.three_star_pool_advance_view.setVisibility(View.VISIBLE);
            holder2.three_star_pool_advance_layout.setVisibility(View.VISIBLE);

        }
        //10.sponsor_amount_layout
        //sponsor amount
       // if (arrayListProductMaster.get(position).getSponsorIncomeAmount()!=notShow)
        if (arrayListProductMaster.get(position).getSponsorIncomeAmount()!=notShow)
        {
            holder2.sponsor_amount_view.setVisibility(View.VISIBLE);
            holder2.sponsor_amount_layout.setVisibility(View.VISIBLE);

        }

        //11.sponsor_income_layout
        //sponsor income
        if (arrayListProductMaster.get(position).getSponsorIncomeRate()!=notShow)
        {
            holder2.sponsor_income_view.setVisibility(View.VISIBLE);
            holder2.sponsor_income_layout.setVisibility(View.VISIBLE);
        }

        //12.platinum_amount_layout
        //platinum amount
        if (arrayListProductMaster.get(position).getPlatinumRankBonusAmount()!=notShow)
        {
            holder2.platinum_amount_view.setVisibility(View.VISIBLE);
            holder2.platinum_amount_layout.setVisibility(View.VISIBLE);
        }

        //13.platinum_rate_layout
        //platinum rate
        if (arrayListProductMaster.get(position).getPlatinumRankBonusRate()!=notShow)
        {
            holder2.platinum_rate_view.setVisibility(View.VISIBLE);
            holder2.platinum_rate_layout.setVisibility(View.VISIBLE);

        }

        //14.star_platinum_amount_layout
        //star platinum amount
        if (arrayListProductMaster.get(position).getStarPlatinumRankBonusIncomeAmount()!=notShow)
        {
            holder2.star_platinum_amount_view.setVisibility(View.VISIBLE);
            holder2.star_platinum_amount_layout.setVisibility(View.VISIBLE);
        }
        //15.star_platinum_rate_layout
        //star platinum rate
        if (arrayListProductMaster.get(position).getStarPlatinumRankBonusIncomeRate()!=notShow)
        {
            holder2.star_platinum_rate_view.setVisibility(View.VISIBLE);
            holder2.star_platinum_rate_layout.setVisibility(View.VISIBLE);
        }
        //16.holiday_rate_layout
        //holiday rate
        if (arrayListProductMaster.get(position).getHolidayAchieverRate()!=notShow)
        {

            holder2.holiday_rate_view.setVisibility(View.VISIBLE);
            holder2.holiday_rate_layout.setVisibility(View.VISIBLE);

        }

        //17.holiday amount
        if (arrayListProductMaster.get(position).getHolidayAchieverAmount()!=notShow)
        {
            holder2.holiday_amount_view.setVisibility(View.VISIBLE);
            holder2.holiday_amount_layout.setVisibility(View.VISIBLE);
        }

        //18.holiday mini amount
        if (arrayListProductMaster.get(position).getHolidayMiniBonanzaAmount()!=notShow)
        {
            holder2.holiday_mini_amount_view.setVisibility(View.VISIBLE);
            holder2.holiday_mini_amount_layout.setVisibility(View.VISIBLE);

        }

        //19.holiday_mini_rate_layout
        //holiday mini rate
        if (arrayListProductMaster.get(position).getHolidayMiniBonanzaRate()!=notShow)
        {
            holder2.holiday_mini_rate_view.setVisibility(View.VISIBLE);
            holder2.holiday_mini_rate_layout.setVisibility(View.VISIBLE);

        }
        //20.holiday_bonaza_rate_layout
        //holiday bonaza rate
        if (arrayListProductMaster.get(position).getHolidayBonanzaRate()!=notShow)
        {
            holder2.holiday_bonaza_rate_view.setVisibility(View.VISIBLE);
            holder2.holiday_bonaza_rate_layout.setVisibility(View.VISIBLE);
        }

        //21.holiday_bonaza_amount_layout
        //holiday bonaza amount
        if (arrayListProductMaster.get(position).getHolidayBonanzaAmount()!=notShow)
        {
            holder2.holiday_bonaza_amount_view.setVisibility(View.VISIBLE);
            holder2.holiday_bonaza_amount_layout.setVisibility(View.VISIBLE);
        }

        //22.nv_token_layout
        //nv token
        if (arrayListProductMaster.get(position).getNVToken()!=notShow) {
            holder2.nv_token_view.setVisibility(View.VISIBLE);
            holder2.nv_token_layout.setVisibility(View.VISIBLE);
        }
        //23.nv_dp_layout
        //nv dp
        if (arrayListProductMaster.get(position).getNVDP()!=notShow) {
            holder2.nv_dp_view.setVisibility(View.GONE);
            holder2.nv_dp_layout.setVisibility(View.VISIBLE);
        }
        //24.nv_bank_layout
        //nv bank
        if (arrayListProductMaster.get(position).getNVBankLoan()!=notShow) {
            holder2.nv_dp_view.setVisibility(View.VISIBLE);
            holder2.nv_bank_layout.setVisibility(View.VISIBLE);
        }


        holder2.bvtxt.setText(String.valueOf(arrayListProductMaster.get(position).getbVPer())+ " %");
        holder2.rttxt.setText(String.valueOf(arrayListProductMaster.get(position).getRetailProfit())+ " %");
        holder2.nvtokentxt.setText(String.valueOf(arrayListProductMaster.get(position).getNVToken())+ "  ");
        holder2.nvdptxt.setText(String.valueOf(arrayListProductMaster.get(position).getNVDP())+ "  ");
        holder2.nvbankloantxt.setText(String.valueOf(arrayListProductMaster.get(position).getNVBankLoan())+ "  ");

        //add for Sagar Virvani
        holder2.starpool.setText(String.valueOf(arrayListProductMaster.get(position).getStarPoolPer())+ " %");
        holder2.starpoolamount.setText(String.valueOf(arrayListProductMaster.get(position).getStarPoolAmount())+ "  ");
        holder2.threepoolPercent.setText(String.valueOf(arrayListProductMaster.get(position).getThreeStarPoolPercent())+ " %");
        holder2.threepoolamount.setText(String.valueOf(arrayListProductMaster.get(position).getThreeStarPoolAmount())+ "  ");
        holder2.retails_advance_amount.setText(String.valueOf(arrayListProductMaster.get(position).getRetailsAdvanceAmount())+ "  ");
        holder2.star_pool_advance_amount.setText(String.valueOf(arrayListProductMaster.get(position).getStarPoolAdvanceAmount())+ "  ");
        holder2.three_star_pool_advance_amount.setText(String.valueOf(arrayListProductMaster.get(position).getThreeStarPoolAdvanceAmount())+ "  ");
        holder2.sponsor_amount.setText(String.valueOf(arrayListProductMaster.get(position).getSponsorIncomeAmount())+ "  ");
        holder2.sponsor_income.setText(String.valueOf(arrayListProductMaster.get(position).getSponsorIncomeRate())+ " %");
        holder2.platinum_amount.setText(String.valueOf(arrayListProductMaster.get(position).getPlatinumRankBonusAmount())+ "  ");
        holder2.platinum_rate.setText(String.valueOf(arrayListProductMaster.get(position).getPlatinumRankBonusRate())+ " %");
        holder2.star_platinum_amount.setText(String.valueOf(arrayListProductMaster.get(position).getStarPlatinumRankBonusIncomeAmount())+ "  ");
        holder2.star_platinum_rate.setText(String.valueOf(arrayListProductMaster.get(position).getStarPlatinumRankBonusIncomeRate())+ " %");
        holder2.holiday_rate.setText(String.valueOf(arrayListProductMaster.get(position).getHolidayAchieverRate())+ " %");
        holder2.holiday_amount.setText(String.valueOf(arrayListProductMaster.get(position).getHolidayAchieverAmount())+ "  ");

        holder2.holiday_mini_amount.setText(String.valueOf(arrayListProductMaster.get(position).getHolidayMiniBonanzaAmount())+ "  ");
        holder2.holiday_mini_rate.setText(String.valueOf(arrayListProductMaster.get(position).getHolidayMiniBonanzaRate())+ " %");
        holder2.holiday_bonaza_rate.setText(String.valueOf(arrayListProductMaster.get(position).getHolidayBonanzaRate())+ " %");
        holder2.holiday_bonaza_amount.setText(String.valueOf(arrayListProductMaster.get(position).getHolidayBonanzaAmount())+ "  ");
        dialog1.show();

    }
}