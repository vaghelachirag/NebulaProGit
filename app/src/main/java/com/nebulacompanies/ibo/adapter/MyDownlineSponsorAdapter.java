package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.IBOListDetails;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 10/1/2016.
 */

public class MyDownlineSponsorAdapter extends BaseAdapter implements Filterable {

    Activity activity;
    ValueFilter valueFilter;
    private ArrayList<IBOListDetails> arrayListSponsorTreeList = new ArrayList<>();
    ArrayList<IBOListDetails> mStringFilterList;
    ArrayList<IBOListDetails> mStringFilterListid;

    public MyDownlineSponsorAdapter(Activity activity, ArrayList<IBOListDetails> PlacementTreeLists) {
        this.activity = activity;
        this.arrayListSponsorTreeList.clear();
        arrayListSponsorTreeList.addAll(PlacementTreeLists);
        mStringFilterList = PlacementTreeLists;
        mStringFilterListid = PlacementTreeLists;
    }

    @Override
    public int getCount() {
        return arrayListSponsorTreeList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListSponsorTreeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayListSponsorTreeList.indexOf(getItem(position));
    }

    private class ViewHolder1 {
        MyTextView legtxt, memberid, membername, rank_;
        ImageView mrTextView;
        LinearLayout lnMyDownline;
    }

    private class ViewHolder2 {
        MyTextView leveltxt, city, mob, mail, date, gbv_, bv_;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        LayoutInflater mInflater1 = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater1.inflate(R.layout.listview_row_my_downline, null);
            holder1 = new ViewHolder1();
            holder1.legtxt = (MyTextView) convertView.findViewById(R.id.leg_placement1);
            holder1.memberid = (MyTextView) convertView.findViewById(R.id.member_id_placement1);
            holder1.membername = (MyTextView) convertView.findViewById(R.id.member_name_placement1);
            holder1.rank_ = (MyTextView) convertView.findViewById(R.id.rank_placement1);
            holder1.mrTextView = (ImageView) convertView.findViewById(R.id.more_info_placement_);
            holder1.lnMyDownline = (LinearLayout) convertView.findViewById(R.id.ln_my_downline);
            convertView.setTag(holder1);
        } else {
            holder1 = (ViewHolder1) convertView.getTag();
        }

        final Typeface tf1 = Typeface.createFromAsset(activity.getAssets(), Config.FONT_STYLE);

        if (position < arrayListSponsorTreeList.size()) {
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
                holder1.lnMyDownline.setBackgroundResource(R.drawable.nebula_effect_white);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
                holder1.lnMyDownline.setBackgroundResource(R.drawable.nebula_effect);
            }
            //listAnimation(convertView.getContext(), convertView, position);
            // final SponsorTreeValues sponsorTreeValues = countrylist.get(position);
            /*holder1.legtxt.setText(sponsorTreeValues.getleg());
            holder1.memberid.setText(sponsorTreeValues.getmemberId());
            holder1.membername.setText(sponsorTreeValues.getmemberName());
            holder1.rank_.setText(sponsorTreeValues.getrank());*/

            holder1.legtxt.setText(String.valueOf(arrayListSponsorTreeList.get(position).getLeg()));
            holder1.memberid.setText(arrayListSponsorTreeList.get(position).getIBOID());
            holder1.membername.setText(arrayListSponsorTreeList.get(position).getIBOUser());
            holder1.rank_.setText(arrayListSponsorTreeList.get(position).getRank());
            holder1.lnMyDownline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder2 holder2 = null;
                    LayoutInflater mInflater2 = (LayoutInflater)
                            activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    Dialog dialog1 = new Dialog(activity);
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    View convertView1 = null;
                    if (convertView1 == null) {
                        convertView1 = mInflater2.inflate(R.layout.popup_my_downline_info, null);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog1.setContentView(convertView1);
                        dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        holder2 = new ViewHolder2();
                        holder2.leveltxt = (MyTextView) convertView1.findViewById(R.id.level_1);
                        holder2.city = (MyTextView) convertView1.findViewById(R.id.city_downline_1);
                        holder2.mob = (MyTextView) convertView1.findViewById(R.id.mobile_downline_1);
                        holder2.mail = (MyTextView) convertView1.findViewById(R.id.mail_downline_1);
                        holder2.date = (MyTextView) convertView1.findViewById(R.id.joiningdate_downline_1);
                        holder2.gbv_ = (MyTextView) convertView1.findViewById(R.id.gbv_downline_1);
                        holder2.bv_ = (MyTextView) convertView1.findViewById(R.id.bv_downnline_1);
                        convertView1.setTag(holder2);
                    } else {
                        holder2 = (ViewHolder2) convertView1.getTag();
                    }

                    holder2.leveltxt.setText(String.valueOf(arrayListSponsorTreeList.get(position).getLevelNo().toString()));
                    holder2.city.setText(arrayListSponsorTreeList.get(position).getCity());
                    holder2.mob.setText(arrayListSponsorTreeList.get(position).getMobile());
                    holder2.mail.setText(arrayListSponsorTreeList.get(position).getEmail());
                    holder2.date.setText(SetGmtTime(arrayListSponsorTreeList.get(position).getDOJ()));
                    holder2.gbv_.setText(String.valueOf(arrayListSponsorTreeList.get(position).getGBV().toString()));
                    holder2.bv_.setText(Double.toString(arrayListSponsorTreeList.get(position).getBVPercent()));
                    dialog1.show();
                }
            });
        }
        return convertView;
    }

    public void clearData() {
        // clear the data
        arrayListSponsorTreeList.clear();
        mStringFilterList.clear();
        mStringFilterListid.clear();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<IBOListDetails> filterList = new ArrayList<IBOListDetails>();

                for (IBOListDetails item : mStringFilterList) {
                    if ((item.getIBOUser() != null && item.getIBOUser().toLowerCase().contains(constraint.toString().toLowerCase()))
                            || (item.getIBOID() != null && item.getIBOID().toLowerCase().contains(constraint.toString().toLowerCase()))||
                            (item.getRank() != null && item.getRank().toLowerCase().contains(constraint.toString().toLowerCase()))) {
                        filterList.add(item);
                    }
                }

               /* for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getIBOUser().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        IBOListDetails sponsorTreeValues = new IBOListDetails(mStringFilterList.get(i).getIBOID(),
                                mStringFilterList.get(i).getIBOUser(),
                                mStringFilterList.get(i).getDOJ(),
                                mStringFilterList.get(i).getLevelNo(),
                                mStringFilterList.get(i).getLeg(),
                                mStringFilterList.get(i).getMobile(),
                                mStringFilterList.get(i).getEmail(),
                                mStringFilterList.get(i).getRank(),
                                mStringFilterList.get(i).getGBV(),
                                mStringFilterList.get(i).getBVPercent());

                        filterList.add(sponsorTreeValues);
                    }

                    if ((mStringFilterList.get(i).getIBOID().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {

                        IBOListDetails sponsorTreeValues2 = new IBOListDetails(mStringFilterListid.get(i).getIBOID(),
                                mStringFilterListid.get(i).getIBOUser(),
                                mStringFilterListid.get(i).getDOJ(),
                                mStringFilterListid.get(i).getLevelNo(),
                                mStringFilterListid.get(i).getLeg(),
                                mStringFilterListid.get(i).getMobile(),
                                mStringFilterListid.get(i).getEmail(),
                                mStringFilterListid.get(i).getRank(),
                                mStringFilterListid.get(i).getGBV(),
                                mStringFilterListid.get(i).getBVPercent());

                        filterList.add(sponsorTreeValues2);
                    }
                }*/
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
                results.count = mStringFilterListid.size();
                results.values = mStringFilterListid;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            arrayListSponsorTreeList = (ArrayList<IBOListDetails>) results.values;
            notifyDataSetChanged();
        }
    }

}
