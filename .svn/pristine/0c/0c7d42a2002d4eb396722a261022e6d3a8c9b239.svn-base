package com.nebulacompanies.ibo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouponcodeAdapter extends RecyclerView.Adapter<CouponcodeAdapter.ViewHolder> {

    private ArrayList<String> myPromocode=new ArrayList<>();
    private int lastCheckedPosition = -1;

    public CouponcodeAdapter(ArrayList<String> myPromocode) {
        this.myPromocode = myPromocode;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.row_couponcode, null);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.radioButton.setChecked(position == lastCheckedPosition);
        holder.radioButton.setText(myPromocode.get(position));
    }

    @Override
    public int getItemCount() {
        return myPromocode.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.radio_sel)
        RadioButton radioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();
                    Utils. selPromocode = myPromocode.get(lastCheckedPosition);
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(lastCheckedPosition);

                }
            });
        }
    }
}