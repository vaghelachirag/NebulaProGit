package com.nebulacompanies.ibo.ecom.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.daasuu.bl.BubbleLayout;
import com.google.android.material.card.MaterialCardView;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.CustomerSupportList;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportTicket;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;

//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());


public class CustomerSupportAdapter
        extends RecyclerView.Adapter<CustomerSupportAdapter.ViewHolder> {


    private ArrayList<CustomerSupportTicket.Datum> mListTickets;
    Utils utils;
    private Context context;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param mListTickets String[] containing the data to populate views to be used
     *                     by RecyclerView.
     */
    public CustomerSupportAdapter(Context context, ArrayList<CustomerSupportTicket.Datum> mListTickets) {
        this.mListTickets = mListTickets;
        this.context = context;

        utils = new Utils();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private final TextView textView;
        MyTextViewEcom txtStatus, txtSubject, txtComment, txtOrder, txtDateCreate, txtDateClose;
        MyBoldTextViewEcom txtCategory;
        LinearLayout layDates, layComment;
        RelativeLayout layDetails;
        MaterialCardView cardView;
        //ShowMoreTextView showMoreTextView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            txtStatus = view.findViewById(R.id.ticket_status);
            txtCategory = view.findViewById(R.id.category_name);
            txtSubject = view.findViewById(R.id.subject_line);
            txtComment = view.findViewById(R.id.tv_comment);
            txtOrder = view.findViewById(R.id.ticket_order);

            cardView = view.findViewById(R.id.card_view_ticket);
            txtDateCreate = view.findViewById(R.id.textViewCreated);
            txtDateClose = view.findViewById(R.id.textViewClosed);
            layDates = view.findViewById(R.id.lay_date);
            layDetails = view.findViewById(R.id.layDetails);
            layComment = view.findViewById(R.id.lay_comment);
            //showMoreTextView = view.findViewById(R.id.text_view_show_more);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_customer_support, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.getTextView().setText(localDataSet[position]);
        CustomerSupportTicket.Datum data = mListTickets.get(position);
        viewHolder.setIsRecyclable(false);
        viewHolder.txtStatus.setText(data.getStatus());
        viewHolder.txtCategory.setText(data.getTicketCategoryName());
        viewHolder.txtSubject.setText(data.getSubject());

        viewHolder.cardView.setOnClickListener(v -> {
            if(Utils.isNetworkAvailable(context)) {
               /* Type listType = new TypeToken<CustomerSupportTicket.Datum>() {
                }.getType();
                Gson gson = new Gson();*/
                Intent intent = new Intent(context, CustomerSupportList.class);
                intent.putExtra( "ticketId", data.getTicketMasterId());
               // intent.putExtra("data", gson.toJson(data, listType));
                context.startActivity(intent);
            }else
                //noInternetConnection();
            utils.dialogueNoInternet((Activity)context);
        });
        if (data.getTicketCategoryName().equalsIgnoreCase("Order")) {
            String orderDate = utils.convertDateMonth(data.getOrderDateINLong());
            viewHolder.txtOrder.setVisibility(View.VISIBLE);
            viewHolder.txtOrder.setText(data.getOrderNumber()+" ["+orderDate+"]");
        } else {
            viewHolder.txtOrder.setVisibility(View.GONE);
        }

      /*  viewHolder.showMoreTextView.setShowingLine(2);
        viewHolder.showMoreTextView.addShowMoreText("Show More ");
        viewHolder.showMoreTextView.setShowMoreColor(Color.BLUE);
        // showMoreTextView.setTextSize(14);
        viewHolder.showMoreTextView.addShowLessText("Show Less ");
        viewHolder.showMoreTextView.setShowLessTextColor(Color.BLUE);*/
        String comment = data.getDescription();

        // viewHolder.showMoreTextView.setText(comment);
        viewHolder.txtComment.setText(comment);
        viewHolder.layComment.setVisibility(TextUtils.isEmpty(comment) ? View.GONE : View.VISIBLE);
        String createDate = utils.convertDateMonth(data.getCreatedOn());
        String closedDate = utils.convertDateMonth(data.getClosedOn());

        viewHolder.txtDateClose.setText("Closed On : " + closedDate);
        viewHolder.txtDateClose.setVisibility(TextUtils.isEmpty(closedDate) ? View.GONE : View.VISIBLE);
        viewHolder.txtDateCreate.setText("Created On : " + createDate);
        viewHolder.txtStatus.setBackgroundColor(
                context.getResources().getColor(data.getStatus().equalsIgnoreCase("closed") ?
                        R.color.light_green_border : R.color.light_red));
        viewHolder.txtStatus.setTextColor( context.getResources().getColor(data.getStatus().equalsIgnoreCase("closed") ?
                R.color.dark_green : R.color.red));
        /*Transition transition = new Fade();
        transition.setDuration(600);
        transition.addTarget(R.id.lay_date);*/

        /*viewHolder.layDetails.setOnClickListener(v ->
                {
                    TransitionManager.beginDelayedTransition(viewHolder.layDetails, transition);
                    boolean show = viewHolder.layDates.getVisibility() == View.VISIBLE;
                    viewHolder.layDates.setVisibility(show ? View.GONE : View.VISIBLE);
                }
        );*/
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mListTickets.size();
    }

}
