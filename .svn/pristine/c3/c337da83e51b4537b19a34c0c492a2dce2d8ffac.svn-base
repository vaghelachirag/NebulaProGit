package com.nebulacompanies.ibo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.WalletHistory;
import com.nebulacompanies.ibo.ecom.utils.Utils;

import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {


    private ArrayList<WalletHistory.Datum> mListTickets;
    Utils utils;
    private Context context;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param mListTickets String[] containing the data to populate views to be used
     *                     by RecyclerView.
     */
    public WalletAdapter(Context context, ArrayList<WalletHistory.Datum> mListTickets) {
        this.mListTickets = mListTickets;
        this.context = context;

        utils = new Utils();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Content View Elements
        protected TextView txtRank;
        protected TextView txtMovieName;
        protected TextView txtYear;
        protected TextView txtCost;
       /* private com.google.android.material.card.MaterialCardView mCard_view_ticket;
        private com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom mTicket_status;
        private com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom mCategory_name;
        private com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom mSubject_line;
        private com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom mTicket_order;
        private LinearLayout mLay_comment;
        private com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom mTv_comment;
        private RelativeLayout mLayDetails;
        private ImageView mImage_more;
        private LinearLayout mLay_date;
        private com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom mTextViewCreated;
        private com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom mTextViewClosed;*/

        // End Of Content View Elements

        public ViewHolder(View view) {
            super(view);
            txtRank = itemView.findViewById(R.id.txtRank);
            txtMovieName = itemView.findViewById(R.id.txtMovieName);
            txtYear = itemView.findViewById(R.id.txtYear);
            txtCost = itemView.findViewById(R.id.txtCost);
      /*      mCard_view_ticket = (com.google.android.material.card.MaterialCardView) view.findViewById(R.id.card_view_ticket);
            mTicket_status = (com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom) view.findViewById(R.id.ticket_status);
            mCategory_name = (com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom) view.findViewById(R.id.category_name);
            mSubject_line = (com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom) view.findViewById(R.id.subject_line);
            mTicket_order = (com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom) view.findViewById(R.id.ticket_order);
            mLay_comment = (LinearLayout) view.findViewById(R.id.lay_comment);
            mTv_comment = (com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom) view.findViewById(R.id.tv_comment);
            mLayDetails = (RelativeLayout) view.findViewById(R.id.layDetails);
            mImage_more = (ImageView) view.findViewById(R.id.image_more);
            mLay_date = (LinearLayout) view.findViewById(R.id.lay_date);
            mTextViewCreated = (com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom) view.findViewById(R.id.textViewCreated);
            mTextViewClosed = (com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom) view.findViewById(R.id.textViewClosed);*/
        }

    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.table_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder rowViewHolder, final int pos) {


        // WalletHistory.Datum data = mListTickets.get(position);
        int rowPos = rowViewHolder.getAdapterPosition();

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
          /*  rowViewHolder.txtRank.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtMovieName.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtYear.setBackgroundResource(R.drawable.table_header_cell_bg);
            rowViewHolder.txtCost.setBackgroundResource(R.drawable.table_header_cell_bg);*/

            rowViewHolder.txtRank.setBackgroundResource(R.color.nebula);
            rowViewHolder.txtMovieName.setBackgroundResource(R.color.nebula);
            rowViewHolder.txtYear.setBackgroundResource(R.color.nebula);
            rowViewHolder.txtCost.setBackgroundResource(R.color.nebula);

            rowViewHolder.txtRank.setTextColor(Color.WHITE);
            rowViewHolder.txtMovieName.setTextColor(Color.WHITE);
            rowViewHolder.txtYear.setTextColor(Color.WHITE);
            rowViewHolder.txtCost.setTextColor(Color.WHITE);

            rowViewHolder.txtRank.setText("Amount");
            rowViewHolder.txtMovieName.setText("Transaction");
            rowViewHolder.txtYear.setText("Remarks");
            rowViewHolder.txtCost.setText("Date");
        } else {
            WalletHistory.Datum modal = mListTickets.get(rowPos);
            String createDate = utils.convertDateMonth(modal.getLongcreatedOn());
            String type= modal.getTransactiontype();
            // Content Cells. Content appear here
           /* rowViewHolder.txtRank.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtMovieName.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtYear.setBackgroundResource(R.drawable.table_content_cell_bg);
            rowViewHolder.txtCost.setBackgroundResource(R.drawable.table_content_cell_bg);*/
            String remark = modal.getRemark();
            rowViewHolder.txtRank.setText("₹ " + modal.getAmount());
            rowViewHolder.txtMovieName.setText(type);
            rowViewHolder.txtMovieName.setTextColor(context.getResources().getColor(type.equalsIgnoreCase("cr")?R.color.green:R.color.red));
            rowViewHolder.txtYear.setText(TextUtils.isEmpty(remark) ? "-" : remark);
            rowViewHolder.txtCost.setText(createDate);

            if (rowPos % 2 == 1) {
                // Set a background color for linearlayout regular row/item
                rowViewHolder.txtRank.setBackgroundResource(R.drawable.nebula_effect_white);
                rowViewHolder.txtMovieName.setBackgroundResource(R.drawable.nebula_effect_white);
                rowViewHolder.txtYear.setBackgroundResource(R.drawable.nebula_effect_white);
                rowViewHolder.txtCost.setBackgroundResource(R.drawable.nebula_effect_white);

                rowViewHolder.txtRank.setBackgroundResource(R.color.table_bg_odd);
                rowViewHolder.txtMovieName.setBackgroundResource(R.color.table_bg_odd);
                rowViewHolder.txtYear.setBackgroundResource(R.color.table_bg_odd);
                rowViewHolder.txtCost.setBackgroundResource(R.color.table_bg_odd);
                // convertView.setBackgroundResource(R.color.table_bg_odd);
              /*  holder1.lnRankBonus.setBackgroundResource(R.color.table_bg_odd);
                holder1.lnRankBonus.setBackgroundResource(R.drawable.nebula_effect_white);*/
            } else {
                // Set the background color for alternate row/item
                rowViewHolder.txtRank.setBackgroundResource(R.drawable.nebula_effect);
                rowViewHolder.txtMovieName.setBackgroundResource(R.drawable.nebula_effect);
                rowViewHolder.txtYear.setBackgroundResource(R.drawable.nebula_effect);
                rowViewHolder.txtCost.setBackgroundResource(R.drawable.nebula_effect);

                rowViewHolder.txtRank.setBackgroundResource(R.color.table_bg_even);
                rowViewHolder.txtMovieName.setBackgroundResource(R.color.table_bg_even);
                rowViewHolder.txtYear.setBackgroundResource(R.color.table_bg_even);
                rowViewHolder.txtCost.setBackgroundResource(R.color.table_bg_even);

                // convertView.setBackgroundResource(R.color.table_bg_even);
                // holder1.lnRankBonus.setBackgroundResource(R.drawable.nebula_effect);
            }
        }
     /*
        viewHolder.setIsRecyclable(false);
        String tr=data.getTransactiontype();
        viewHolder.mTicket_status.setText(tr);
        viewHolder.mSubject_line.setText("Remarks: "+data.getRemark());
        viewHolder.mCategory_name.setText("Amount: ₹"+data.getAmount());
        viewHolder.mTicket_status.setTextColor( context.getResources().getColor(tr.equalsIgnoreCase("cr") ?
                R.color.dark_green : R.color.red));
        viewHolder.mTicket_status.setBackgroundColor(
                context.getResources().getColor(tr.equalsIgnoreCase("cr") ?
                        R.color.light_green_border : R.color.light_red));
           viewHolder.mTextViewCreated.setText(createDate);*/
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mListTickets.size();
    }

}
