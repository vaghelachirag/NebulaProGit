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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.daasuu.bl.BubbleLayout;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.CustomerSupportList;
import com.nebulacompanies.ibo.ecom.ShowFullScreenAttachment;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportDetailTicket;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportTicket;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class CustomerSupportDetailsAdapter extends
        RecyclerView.Adapter<CustomerSupportDetailsAdapter.ViewHolder> {

    private ArrayList<CustomerSupportDetailTicket.Detail> mListTickets;
    Typeface typeface;
    Utils utils;
    private Context context;
    Session session;

    public CustomerSupportDetailsAdapter(Context context, ArrayList<CustomerSupportDetailTicket.Detail> mListTickets) {
        this.mListTickets = mListTickets;
        this.context = context;
        session = new Session(context);
        utils = new Utils();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        BubbleLayout lysender, lyReceiver;
        MyTextViewEcom txtDateCreate, txtDateCreateSender, txtReceiveCromment, txtSenderComment;
        MyTextViewEcom txtSenderName, txtmyname;
        MyTextViewEcom attchedFile, attachfilesndr;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            lyReceiver = view.findViewById(R.id.ly_receiver);
            lysender = view.findViewById(R.id.ly_sender);
            txtReceiveCromment = view.findViewById(R.id.receiver_comment);
            txtSenderComment = view.findViewById(R.id.sender_comment);
            txtSenderName = view.findViewById(R.id.sender_name);
            txtDateCreate = view.findViewById(R.id.created_date);
            txtDateCreateSender = view.findViewById(R.id.created_date_sender);
            attchedFile = view.findViewById(R.id.attched_file);
            attachfilesndr = view.findViewById(R.id.attched_file_sender);
            txtmyname = view.findViewById(R.id.my_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_customer_support_details, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        CustomerSupportDetailTicket.Detail data = mListTickets.get(position);
        viewHolder.setIsRecyclable(false);
        viewHolder.txtReceiveCromment.setText(HtmlCompat.fromHtml(data.getComment().trim(),0));
        String createDate = utils.convertDateMonth(data.getCreatedOnLong());
        viewHolder.txtDateCreate.setText(createDate);
        viewHolder.txtmyname.setText(session.getUserName());
        boolean isSentByUs = data.getIsSentByUs();
        String attachment = data.getAttachmentFile();
        Log.d("attachment", attachment);
        if (!isSentByUs) {
            viewHolder.lyReceiver.setVisibility(View.GONE);
            viewHolder.lysender.setVisibility(View.VISIBLE);

            viewHolder.attchedFile.setVisibility(TextUtils.isEmpty(attachment) ? View.GONE : View.VISIBLE);
            viewHolder.attchedFile.setOnClickListener(v -> {
                if (Utils.isNetworkAvailable(context)) {
                    Intent i = new Intent(context, ShowFullScreenAttachment.class);
                    i.putExtra("data", attachment);
                    context.startActivity(i);
                } else
                    // noInternetConnection();
                    utils.dialogueNoInternet((Activity) context);
            });

        } else {
            viewHolder.lysender.setVisibility(View.GONE);
            viewHolder.lyReceiver.setVisibility(View.VISIBLE);
            viewHolder.txtSenderName.setText("Support");//data.getCreatedByName()
            viewHolder.txtSenderComment.setText(HtmlCompat.fromHtml(data.getComment().trim(),0));
            viewHolder.txtDateCreateSender.setText(createDate);
            viewHolder.attachfilesndr.setVisibility(TextUtils.isEmpty(attachment) ? View.GONE : View.VISIBLE);
            viewHolder.attachfilesndr.setOnClickListener(v -> {
                if (Utils.isNetworkAvailable(context)) {
                    Intent i = new Intent(context, ShowFullScreenAttachment.class);
                    i.putExtra("data", attachment);
                    context.startActivity(i);
                } else
                    // noInternetConnection();
                    utils.dialogueNoInternet((Activity) context);
            });
        }
    }


    private void noInternetConnection() {
        AppUtils.displayNetworkErrorMessage(context);
        // Toast.makeText(this, "No Internet Available.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return mListTickets.size();
    }
}
