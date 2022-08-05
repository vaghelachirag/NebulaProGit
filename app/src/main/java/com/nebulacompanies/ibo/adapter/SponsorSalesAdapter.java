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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.AchieverIBOList;
import com.nebulacompanies.ibo.model.AchieverIncomeList;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Sagar Virvani on 08-03-2018.
 */

public class SponsorSalesAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<AchieverIBOList> arrayListachieverIBOLists = new ArrayList<>();
    ArrayList<AchieverIncomeList> arrayListachieverIncomeLists = new ArrayList<>();

    /* private static ArrayList sr;*/
    public SponsorSalesAdapter(Activity activity, ArrayList<AchieverIBOList> achieverIBOLists, ArrayList<AchieverIncomeList> achieverIncomeLists) {
        super();
        this.activity = activity;
        arrayListachieverIBOLists.clear();
        arrayListachieverIncomeLists.clear();
        arrayListachieverIBOLists.addAll(achieverIBOLists);
        arrayListachieverIncomeLists.addAll(achieverIncomeLists);
        /*this.sr=sr;*/

           /* for(int i=0; i< arrayListachieverIncomeLists.size();i++){
                if(arrayListachieverIncomeLists.get(i).getCustomerType().equals("IBO")){
                    achieverclosingIncomeIBODetails.addAll(arrayListachieverIncomeLists);
                }
            }*/
    }

    /*private view holder class*/
    private class ViewHolder1 {
        TextView dt, iboid, iboname;
        ImageView mi;
        LinearLayout listIncomeDetailsLayout;
    }

    private class ViewHolder2 {
        MyTextView phoneMyTextView, emailMyTextView, cityMyTextView, phoneTitleMyTextView, emailTitleMyTextView, cityTitleMyTextView;
        View view;
        LinearLayout pincodeLinearLayout;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        //ViewHolder holder = null;
        final ViewHolder1 holder1;
        LayoutInflater mInflater = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_row_starclub_income, null);
            holder1 = new ViewHolder1();
            holder1.dt = (TextView) convertView.findViewById(R.id.date_starclub1);
            holder1.iboid = (TextView) convertView.findViewById(R.id.rank_starpool1);
            holder1.iboname = (TextView) convertView.findViewById(R.id.gross_amt_starpool1);
            holder1.mi = (ImageView) convertView.findViewById(R.id.more_info_starpool1);
            holder1.listIncomeDetailsLayout = (LinearLayout) convertView.findViewById(R.id.list_income_details_layout);
            //holder.txtDate = (TextView) convertView.findViewById(R.id.list_item_date);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }
        if (position % 2 == 1) {
            // Set a background color for ListView regular row/item
            convertView.setBackgroundResource(R.color.table_bg_odd);
            holder1.listIncomeDetailsLayout.setBackgroundResource(R.drawable.nebula_effect_white);

        } else {
            // Set the background color for alternate row/item
            convertView.setBackgroundResource(R.color.table_bg_even);
            holder1.listIncomeDetailsLayout.setBackgroundResource(R.drawable.nebula_effect);

        }

        if (position < arrayListachieverIBOLists.size()) {
            holder1.dt.setText(SetGmtTime(arrayListachieverIBOLists.get(position).getDate()));
            holder1.iboid.setText(arrayListachieverIBOLists.get(position).getIBOID());
            holder1.iboname.setText(arrayListachieverIBOLists.get(position).getIBOName());

            holder1.listIncomeDetailsLayout.setOnClickListener(new View.OnClickListener() {
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
                        dialog1.setContentView(convertView1);
                        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder2 = new ViewHolder2();
                        holder2.phoneTitleMyTextView = (MyTextView) convertView1.findViewById(R.id.state_title);
                        holder2.phoneMyTextView = (MyTextView) convertView1.findViewById(R.id.state_1);
                        holder2.emailTitleMyTextView = (MyTextView) convertView1.findViewById(R.id.city_title);
                        holder2.emailMyTextView = (MyTextView) convertView1.findViewById(R.id.city_1);
                        holder2.cityTitleMyTextView = (MyTextView) convertView1.findViewById(R.id.mobile_title);
                        holder2.cityMyTextView = (MyTextView) convertView1.findViewById(R.id.mobile_1);
                        holder2.pincodeLinearLayout = (LinearLayout) convertView1.findViewById(R.id.pincode_layout);
                        holder2.view = (View) convertView1.findViewById(R.id.pincode_view);
                        holder2.view.setVisibility(View.GONE);
                        holder2.pincodeLinearLayout.setVisibility(View.GONE);
                        holder2.phoneTitleMyTextView.setText(R.string.moblie);
                        holder2.emailTitleMyTextView.setText(R.string.email);
                        holder2.cityTitleMyTextView.setText(R.string.city_);
                    } else {
                        holder2 = (ViewHolder2) convertView1.getTag();
                    }

                    holder2.phoneMyTextView.setText(arrayListachieverIBOLists.get(position).getPhone());
                    holder2.emailMyTextView.setText(arrayListachieverIBOLists.get(position).getEmail());
                    holder2.cityMyTextView.setText(arrayListachieverIBOLists.get(position).getCity());

                    dialog1.show();
                }
            });
            return convertView;
        }
        return convertView;
    }

    @Override
    public int getCount() {
        //return arrayListUpdates.size();
        if (arrayListachieverIncomeLists.size() == 0) {
            return 4;
        } else {
            return 5;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearData() {
        // clear the data
        arrayListachieverIBOLists.clear();
    }


}
