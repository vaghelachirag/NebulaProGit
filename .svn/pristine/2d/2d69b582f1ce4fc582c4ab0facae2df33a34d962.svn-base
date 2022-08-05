package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.ViewEvents;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.model.EventList;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.view.MyBoldTextView;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;


/**
 * Created by Sagar virvani on 30/11/2018.
 */
public class CompanyEventsAdapter extends RecyclerView.Adapter<CompanyEventsAdapter.CompanyEventsHolder> {
    private Context context;
    ArrayList<EventList> arrayListEventList = new ArrayList<>();
    private int lastPosition = -1;
    final String PREFS_WALKTHROUGH_EVENT = "eventwalkthrough";

    public CompanyEventsAdapter(Context context, ArrayList<EventList> eventLists) {
        this.context = context;
        arrayListEventList.clear();
        arrayListEventList.addAll(eventLists);
    }

    public class CompanyEventsHolder extends RecyclerView.ViewHolder {

        // CardView cardViewPackage;
        MyBoldTextView txtTitle;
        MyTextView txtPlace, txtDate;

        public CompanyEventsHolder(View convertView) {
            super(convertView);
            context = convertView.getContext();
            //  cardViewPackage = convertView.findViewById(R.id.card_view);
            txtTitle = (MyBoldTextView) convertView.findViewById(R.id.list_item_name1);
            txtPlace = (MyTextView) convertView.findViewById(R.id.list_item_place1);
            txtDate = (MyTextView) convertView.findViewById(R.id.list_item_date1);
        }
    }

    @Override
    public CompanyEventsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_events, parent, false);

        return new CompanyEventsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CompanyEventsHolder holder, final int position) {

        final EventList eventListList = arrayListEventList.get(position);

        holder.txtTitle.setText(eventListList.getEventName());
        holder.txtPlace.setText(eventListList.getLocation());

        holder.txtDate.setText(SetGmtTime(eventListList.getEventDate()));
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(context, ViewEvents.class);
                i1.putExtra("Event_Name", eventListList.getEventName());
                i1.putExtra("Event_Date", SetGmtTime(eventListList.getEventDate()));
                i1.putExtra("first_time",false);
                i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP );

                holder.itemView.setEnabled(true);
                holder.itemView.setClickable(true);
                holder.itemView.setFocusable(true);

                SharedPreferences guideViewSkipEventMain = context.getSharedPreferences(PrefUtils.prefMainEvents, MODE_PRIVATE);
                SharedPreferences.Editor et = guideViewSkipEventMain.edit();
                et.putBoolean("guideViewSkipEventMain", true);
                et.apply();



                context.startActivity(i1);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return arrayListEventList.size();
    }

    public void clearData() {
        // clear the data
        arrayListEventList.clear();
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
            viewToAnimate.startAnimation(animation);
            //lastPosition = position;
           /* new AnimationItem("Fall down", R.anim.layout_animation_fall_down);
            lastPosition = position;*/
        }

    }
}
