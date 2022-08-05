package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.UpdateProfile;
import com.nebulacompanies.ibo.ecom.model.PromoCodeModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextView;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyButton;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.nebulacompanies.ibo.util.Config.shareMainUrl;


public class MyCouponAdapter extends RecyclerView.Adapter<MyCouponAdapter.ViewHolder> {

    Utils utils;
    private Context context;
    List<PromoCodeModel.Datum> promodata;
    Session session;
    AlertDialog alertDialog;


    public MyCouponAdapter(Context context, List<PromoCodeModel.Datum> promodata) {
        this.context = context;
        this.promodata = promodata;
        utils = new Utils();
        session = new Session(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Content View Elements
        private MyBoldTextViewEcom mTextoffertitle;
        private ImageView mImg_share, mImageBanner;
        private Button mButtonCouponcode;
        private MyTextView mTextactive;
        private MyTextView mTextgeneratetime;
        private MyTextView mTextpeopleused;
        private MyTextView mTextvalidtill;
        private MyTextView mTextofferusagecount;
        private MyTextView mTextofferdiscountamount;
        private MyTextView mTextofferdiscountperc;
        private MyTextView mTextoffermaxdiscountamt;
        private MyTextView mTextofferstartdate;
        private MyTextView mTextoffershort;
        private MyTextView mTextofferLong;
        private MyTextView mMore;
        // End Of Content View Elements

        public ViewHolder(View view) {
            super(view);

            mTextoffertitle = view.findViewById(R.id.textoffertitle);
            mImg_share = view.findViewById(R.id.img_share);
            mButtonCouponcode = view.findViewById(R.id.buttonCouponcode);
            mTextoffershort = view.findViewById(R.id.textshortdescription);
            mTextofferLong = view.findViewById(R.id.textlongdescription);
            mImageBanner = view.findViewById(R.id.image_banner);
            mTextactive = view.findViewById(R.id.textactive);
            mTextgeneratetime = view.findViewById(R.id.textgeneratetime);
            mTextpeopleused = view.findViewById(R.id.textpeopleused);
            mTextvalidtill = view.findViewById(R.id.textvalidtill);
            mTextofferusagecount = view.findViewById(R.id.textofferusagecount);
            mTextofferdiscountamount = view.findViewById(R.id.textofferdiscountamount);
            mTextofferdiscountperc = view.findViewById(R.id.textofferdiscountperc);
            mTextoffermaxdiscountamt = view.findViewById(R.id.textoffermaxdiscountamt);
            mTextofferstartdate = view.findViewById(R.id.textofferstartdate);
            mMore = view.findViewById(R.id.textmoredetails);
        }
    }

    @Override
    public MyCouponAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_coupon, viewGroup, false);
        return new MyCouponAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCouponAdapter.ViewHolder viewHolder, final int position) {

        PromoCodeModel.Datum datum = promodata.get(position);

        Glide.with(context)
                .load(datum.getImageFile()).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.app_logo)
                .into(viewHolder.mImageBanner);

        String timeStr = datum.getCreatedOn();//"2016-11-01T09:45:00.000+02:00";
        String timeEnd = datum.getEndDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date dateObj = null;
        Date dateObjEnd = null;
        try {
            dateObj = sdf.parse(timeStr);
            dateObjEnd = sdf.parse(timeEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String genTime = "0";
        String endTime = "0";
        try {

            PrettyTime prettyTime = new PrettyTime();
            genTime = prettyTime.format(dateObj);
            endTime = prettyTime.format(dateObjEnd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*viewHolder.mTextoffertitle.setText(datum.getName());
        viewHolder.mTextactive.setText(datum.getIsActive() ? "Active" : "Inactive");
        viewHolder.mButtonCouponcode.setText(datum.getCouponCode());
        viewHolder.mTextoffershort.setText("• " + datum.getSortDescription());
        viewHolder.mTextofferLong.setText("• " + datum.getLongDescription());
        viewHolder.mTextgeneratetime.setText("generated " + genTime);
        viewHolder.mTextpeopleused.setText(datum.getTotalUsedPromocode() + " people used");
        viewHolder.mTextofferusagecount.setText("• The offer can be used by " + datum.getMaxUsageCount() + " times only.");
        viewHolder.mTextofferdiscountamount.setText("• Get Flat Rs." + datum.getDiscountAmt() + " OFF on your order.");
        viewHolder.mTextofferdiscountperc.setText("• Get Flat " + datum.getDiscountPercentage() + "% OFF on your order.");
*/
       /* String endDate = utils.convertDateFormat(datum.getEndDate());
           String startDate = utils.convertDateFormat(datum.getStartDate());
        viewHolder.mTextvalidtill.setText(endDate);
        viewHolder.mTextofferstartdate.setText("• Start from " + startDate + " to " + endDate);*/

        viewHolder.mImg_share.setOnClickListener(v -> {
            String text = " Nebulacare brings to you a wide range of products on health, wellness and other categories. Use the promo code <" + datum.getCouponCode() + "> to avail offer on all your purchases. Discount valid for limited time. Shop now, click on the link below.\n\n";
            String url = shareMainUrl + "?ref=" + session.getRefId();
            utils.shareData(context, text, url);
        });
        viewHolder.mMore.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View customLayout = inflater.inflate(R.layout.dialog_promo_list, null);
            builder.setView(customLayout);

            alertDialog = builder.create();
            alertDialog.setCancelable(false);
            MyTextView mTextgeneratetime = customLayout.findViewById(R.id.textgeneratetime);
            MyTextView mTextpeopleused = customLayout.findViewById(R.id.textpeopleused);
            MyTextView mTextvalidtill = customLayout.findViewById(R.id.textvalidtill);
            MyTextView mTextofferusagecount = customLayout.findViewById(R.id.textofferusagecount);
            MyTextView mTextofferdiscountamount = customLayout.findViewById(R.id.textofferdiscountamount);
            MyTextView mTextofferdiscountperc = customLayout.findViewById(R.id.textofferdiscountperc);
            MyTextView mTextoffermaxdiscountamt = customLayout.findViewById(R.id.textoffermaxdiscountamt);
            MyTextView mTextofferstartdate = customLayout.findViewById(R.id.textofferstartdate);
            MyTextView mTextoffershort = customLayout.findViewById(R.id.textshortdescription);
            MyTextView mTextofferLong = customLayout.findViewById(R.id.textlongdescription);
            ImageView imgClose = customLayout.findViewById(R.id.img_close);


            mTextoffershort.setText("• " + datum.getSortDescription());
            mTextofferLong.setText("• " + datum.getLongDescription());
            mTextpeopleused.setText(datum.getTotalUsedPromocode() + " people used");
            mTextofferusagecount.setText("• The offer can be used by " + datum.getMaxUsageCount() + " times only.");
            mTextofferdiscountamount.setText("• Get Flat Rs." + datum.getDiscountAmt() + " OFF on your order.");
            mTextofferdiscountperc.setText("• Get Flat " + datum.getDiscountPercentage() + "% OFF on your order.");
            mTextoffermaxdiscountamt.setText("• Get Maximum Flat Rs." + datum.getMaxDiscountAmount() + " OFF on your order.");
            String endDate = utils.convertDateFormat(datum.getEndDate());
            String startDate = utils.convertDateFormat(datum.getStartDate());

            //viewHolder.mTextvalidtill.setText(endDate);
            mTextofferstartdate.setText("• Start from " + startDate + " to " + endDate + ".");
            imgClose.setOnClickListener(v1 -> alertDialog.dismiss());

            alertDialog.show();

        });
    }

    @Override
    public int getItemCount() {
        return promodata.size();// promodata.size();
    }


}