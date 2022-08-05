package com.nebulacompanies.ibo.ecom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;


import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.trackorder.LineType;
import com.nebulacompanies.ibo.ecom.trackorder.TimelineView;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;

import java.util.List;

/**
 * Adapter for RecyclerView with TimelineView
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private final int orientation;
    private final List<TrackListItem> items;

    public TimelineAdapter(int orientation, List<TrackListItem> items) {
        this.orientation = orientation;
        this.items = items;
    }



    @Override
    public int getItemViewType(int position) {
        //if (orientation == LinearLayoutManager.VERTICAL) {
            return R.layout.item_vertical;
        //} else {
          //  return R.layout.item_horizontal;
        //}
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvDate.setText(items.get(position).getDate());
        holder.tvStatus.setText(items.get(position).getOrder_status());
        holder.timelineView.setLineType(getLineType(position));
        holder.timelineView.setNumber(position);

        if(position==0){

            holder.timelineView.setActive(true);
        }else {
            holder.timelineView.setActive(false);
            holder.timelineView.setDrawable(AppCompatResources
                    .getDrawable(holder.timelineView.getContext(),
                            R.drawable.ic_checked));
        }

//        if (position == 0) {
//            holder.timelineView.setDrawable(AppCompatResources
//                    .getDrawable(holder.timelineView.getContext(),
//                            R.drawable.ic_checked));
//        }

        // Make first and last markers stroked, others filled
       /* if (position == 0 || position + 1 == getItemCount()) {
            holder.timelineView.setFillMarker(false);
        } else {
            holder.timelineView.setFillMarker(true);
        }

        if (position == 4) {
            holder.timelineView.setDrawable(AppCompatResources
                                                    .getDrawable(holder.timelineView.getContext(),
                                                                 R.drawable.ic_checked));
        } else {
            holder.timelineView.setDrawable(null);
        }

        // Set every third item active
        holder.timelineView.setActive(position % 3 == 2);*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private LineType getLineType(int position) {
        if (getItemCount() == 1) {
            return LineType.ONLYONE;

        } else {
            if (position == 0) {
                return LineType.BEGIN;

            } else if (position == getItemCount() - 1) {
                return LineType.END;

            } else {
                return LineType.NORMAL;
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TimelineView timelineView;
        MyTextViewEcom tvDate;
        MyTextViewEcom tvStatus;

        ViewHolder(View view) {
            super(view);
            timelineView = (TimelineView) view.findViewById(R.id.timeline);
            tvDate = (MyTextViewEcom) view.findViewById(R.id.tv_date);
            tvStatus = (MyTextViewEcom) view.findViewById(R.id.tv_status);
        }
    }

}
