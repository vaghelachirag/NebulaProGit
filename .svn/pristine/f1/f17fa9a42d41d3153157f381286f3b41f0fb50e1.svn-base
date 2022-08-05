package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.ShowFullScreenEvents;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.model.SubEventList;
import com.nebulacompanies.ibo.walk.FontUtil;
import com.nebulacompanies.ibo.walk.SpotlightViewNew;

import java.util.ArrayList;
import java.util.List;

public class EventItemAdapter extends RecyclerView.Adapter<EventItemAdapter.ItemViewHolder> {

    private List<SubEventList> Items = new ArrayList<>();
    OnItemClickListener mItemClickListener;
    Context mContext;
    String event;
    ArrayList<String> imagepic = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<Integer> count = new ArrayList<Integer>();
    private boolean isLoadingAdded = false;
    SharedPreferences walkthrough;
    boolean first_time;


    public EventItemAdapter(Context context, String event, ArrayList<String> imagepic, ArrayList<String> date, ArrayList<Integer> count, boolean first_time) {
        Items = new ArrayList<>();
        mContext = context;
        this.event = event;
        this.imagepic = imagepic;
        this.date = date;
        this.count = count;
        this.first_time=first_time;

    }

    /*public EventItemAdapter(List<SubEventList> items) {
        this.Items = items;
    }*/

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgUserAvatar;

        public ItemViewHolder(View view) {
            super(view);
            imgUserAvatar = (ImageView) view.findViewById(R.id.picture1);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        SubEventList model = Items.get(position);

        if (position == Items.size() - 1) {
            holder.imgUserAvatar.setVisibility(View.GONE);
        } else {
            holder.imgUserAvatar.setVisibility(View.VISIBLE);
        }
       // Picasso.with(mContext).load(model.getEventThumbnail()).placeholder(R.drawable.nebula_effect).into(holder.imgUserAvatar);

        Glide.with(mContext)
                .load(model.getEventThumbnail())
                .placeholder(R.drawable.nebula_effect)
                .into(holder.imgUserAvatar);
        RecyclerView.ViewHolder finalHolder1 = holder;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                walkthrough = mContext.getSharedPreferences(PrefUtils.prefEventwalk, 0);
                SharedPreferences skipMainGet = mContext.getSharedPreferences(PrefUtils.prefSkipmain, 0);
                boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
                SharedPreferences skipModuleGet = mContext.getSharedPreferences(PrefUtils.prefGuideskip, 0);
                boolean isSkipModule = skipModuleGet.getBoolean("guideviewskipEvents", false);
                if (!isSkipMain && !isSkipModule) {
                    if (position == 4 && first_time) {
                        SpotlightViewNew spotLight = new SpotlightViewNew.Builder((Activity) mContext)
                                .introAnimationDuration(400)
                                .enableRevealAnimation(true)
                                .performClick(true)
                                .fadeinTextDuration(400)
                                .setTypeface(FontUtil.get(mContext, "fonts/Montserrat-Regular.ttf"))
                                .headingTvColor(Color.parseColor("#eb273f"))
                                .headingTvSize(18)
                                .headingTvText("Company Event")
                                .subHeadingTvColor(Color.parseColor("#ffffff"))
                                .subHeadingTvSize(14)
                                .targetPadding(10)
                                .subHeadingTvText("More about this section")
                                .maskColor(Color.parseColor("#dc000000"))
                                .target(finalHolder1.itemView)
                                .lineAnimDuration(400)
                                .targetPadding(12)
                                .lineAndArcColor(Color.parseColor("#eb273f"))
                                .dismissOnTouch(false)
                                .dismissOnBackPress(false)
                                .enableDismissAfterShown(false)
                                .show();

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(mContext, ShowFullScreenEvents.class);
                                holder.imgUserAvatar.setClickable(false);
                                holder.imgUserAvatar.setEnabled(true);
                                intent.putExtra("id", position);
                                intent.putExtra("date", date);
                                intent.putExtra("imagepic", imagepic);
                                intent.putExtra("count", count);
                                intent.putExtra("first_time_event_sup", true);
                                intent.putExtra("eventName", event);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                mContext.startActivity(intent);
                            }
                        });
                       // walkthrough.edit().putBoolean("walkthrough_first_time_event_sup", false).apply();
                    }
                }
            }
        }, 3000);
        holder.imgUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShowFullScreenEvents.class);
              //  holder.imgUserAvatar.setClickable(false);
               // holder.imgUserAvatar.setEnabled(true);
                intent.putExtra("id", position);
                intent.putExtra("date", date);
                intent.putExtra("imagepic", imagepic);
                intent.putExtra("count", count);
                intent.putExtra("first_time_event_sup", false);
                intent.putExtra("eventName", event);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClicklListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setItems(List<SubEventList> items) {
        Items = items;
        notifyDataSetChanged();
    }

    public void clearData() {
        // clear the data
        Items.clear();
    }

    public void add(SubEventList r) {
        Items.add(r);
        notifyItemInserted(Items.size() - 1);
        // notifyItemInserted(Items.size());
    }

    public void addAll(List<SubEventList> moveResults) {
        for (SubEventList result : moveResults) {
            if (!moveResults.contains(result.getEventPic())) {
                add(result);
            }
            // add(result);
        }
    }

    public void remove(SubEventList r) {
        int position = Items.indexOf(r);
        if (position > -1) {
            Items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        Items.clear();
        /*while (getItemCount() > 0) {
            remove(getItem(0));
        }*/
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new SubEventList());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = Items.size() - 1;
        SubEventList result = getItem(position);

        if (result != null) {
            Items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public SubEventList getItem(int position) {
        return Items.get(position);
    }
}
