package com.nebulacompanies.ibo.ecom.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.AddAddressDetail;
import com.nebulacompanies.ibo.ecom.model.GenerateCouponModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextView;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;

public class GenerateCouponAdapter extends RecyclerView.Adapter<GenerateCouponAdapter.ViewHolder> {

    Utils utils;
    private Context context;
    APIInterface mAPIInterface;
    Session session;
    String message;
    ArrayList<GenerateCouponModel.Datum> mCouponList;
    Typeface typeface;
    ProgressDialog mLoadProgressDialog;

    public GenerateCouponAdapter(Context context, ArrayList<GenerateCouponModel.Datum> mCouponList) {
        this.mCouponList = mCouponList;
        this.context = context;
        session = new Session(context);
        mAPIInterface = APIClient.getClient(context).create(APIInterface.class);
        utils = new Utils();
        initProgress();
    }

    private void initProgress() {
        mLoadProgressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        mLoadProgressDialog.setCancelable(false);
        mLoadProgressDialog.setMessage("Loading...");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Content View Elements
       // MaterialProgressBar materialProgressBar;
        ProgressDialog mLoadProgressDialog;
        private MyBoldTextViewEcom mTextoffertitle;
        private ImageView mImage_banner;
        private Button mButtonGenerate;
        private MyTextView mTextshortdescription;
        private MyTextView mTextlongdescription;
        private MyTextView mTextofferusagecount;
        private MyTextView mTextofferdiscountamount;
        private MyTextView mTextofferdiscountperc;
        private MyTextView mTextoffermaxdiscountamt;
        private MyTextView mTextofferstartdate;

        // End Of Content View Elements

        public ViewHolder(View view) {
            super(view);
           // materialProgressBar = view.findViewById(R.id.progresbar);
            mTextoffertitle = view.findViewById(R.id.textoffertitle);
            mImage_banner = view.findViewById(R.id.image_banner);
            mButtonGenerate = view.findViewById(R.id.buttonGenerate);
            mTextshortdescription = view.findViewById(R.id.textshortdescription);
            mTextlongdescription = view.findViewById(R.id.textlongdescription);
            mTextofferusagecount = view.findViewById(R.id.textofferusagecount);
            mTextofferdiscountamount = view.findViewById(R.id.textofferdiscountamount);
            mTextofferdiscountperc = view.findViewById(R.id.textofferdiscountperc);
            mTextoffermaxdiscountamt = view.findViewById(R.id.textoffermaxdiscountamt);
            mTextofferstartdate = view.findViewById(R.id.textofferstartdate);
        }

    }


    @Override
    public GenerateCouponAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_generate_code, viewGroup, false);

        return new GenerateCouponAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(GenerateCouponAdapter.ViewHolder viewHolder, final int position) {
        GenerateCouponModel.Datum datum = mCouponList.get(position);
        Glide.with(context)
                .load(datum.getImageFile()).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.app_logo)
                .into(viewHolder.mImage_banner);
        viewHolder.mTextoffertitle.setText(datum.getName());
        viewHolder.mTextshortdescription.setText(datum.getSortDescription());
        viewHolder.mTextlongdescription.setText(datum.getLongDescription());
        viewHolder.mTextofferusagecount.setText("• The offer can be used by " + datum.getMaxUsageCount() + " times only.");
        viewHolder.mTextofferdiscountamount.setText("• Get Flat Rs." + datum.getDiscountAmt() + " OFF on your order.");
        viewHolder.mTextofferdiscountperc.setText("• Get Flat " + datum.getDiscountPercentage() + "% OFF on your order.");
        viewHolder.mTextoffermaxdiscountamt.setText("• Get Maximum Flat Rs." + datum.getMaxDiscountAmount() + " OFF on your order.");

        String endDate = utils.convertDateFormat(datum.getEndDate());
        String startDate = utils.convertDateFormat(datum.getStartDate());

        viewHolder.mTextofferstartdate.setText("• Start from " + startDate + " to " + endDate);

        viewHolder.mButtonGenerate.setOnClickListener(v -> {
            viewHolder.mButtonGenerate.setEnabled(false);
            if (Utils.isNetworkAvailable(context)) {
                mLoadProgressDialog.show();
                Call<AddAddressDetail> wsCallingEvents = mAPIInterface.generatePromocode(session.getIboKeyId(), datum.getId());
                wsCallingEvents.enqueue(new Callback<AddAddressDetail>() {
                    @Override
                    public void onResponse(Call<AddAddressDetail> call, Response<AddAddressDetail> response) {
                        viewHolder.mButtonGenerate.setEnabled(true);
                        mLoadProgressDialog.dismiss();
                        if (response.isSuccessful()) {
                            int statuscode = response.body().getStatusCode();
                            message = response.body().getData();
                            if (statuscode == REQUEST_STATUS_CODE_NO_RECORDS) {

                                //Toast.makeText(context, response.body().getData(), Toast.LENGTH_SHORT).show();
                                dialogueNoRecords();
                                viewHolder.mButtonGenerate.setEnabled(true);
                            } else if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                // Toast.makeText(context, "Code generated! " + response.body().getData(), Toast.LENGTH_SHORT).show();
                                dialogueSuccess();
                                Intent mIntent = new Intent(Utils.actionCoupon);
                                context.sendBroadcast(mIntent);
                                viewHolder.mButtonGenerate.setEnabled(true);
                            }
                        } else {
                            Toast.makeText(context, R.string.error_normal, Toast.LENGTH_SHORT).show();
                            viewHolder.mButtonGenerate.setEnabled(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<AddAddressDetail> call, Throwable t) {
                        mLoadProgressDialog.dismiss();
                       // viewHolder.materialProgressBar.setVisibility(View.VISIBLE);
                        dialogueFailure();
                        //Toast.makeText(context, R.string.error_normal, Toast.LENGTH_SHORT).show();
                        viewHolder.mButtonGenerate.setEnabled(true);
                    }
                });
            } else {
                viewHolder.mButtonGenerate.setEnabled(true);
                utils.dialogueNoInternet((Activity) context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCouponList.size();
    }

    public void dialogueNoRecords() {
        SweetAlertDialog loading = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        loading.setCancelable(false);
        loading.setConfirmText("OK");
        //loading.setCustomImage(R.drawable.ic_cloud_off_black_24dp);

        loading.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                com.nebulacompanies.ibo.view.MyTextView textTitle = (com.nebulacompanies.ibo.view.MyTextView) alertDialog.findViewById(R.id.title_text);
                com.nebulacompanies.ibo.view.MyTextView textContent = (com.nebulacompanies.ibo.view.MyTextView) alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                //btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                // textTitle.setText(R.string.error_no_records);
                textTitle.setVisibility(View.GONE);
                btnConfirm.setText("Ok");
                textContent.setText(message);

                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(v -> loading.dismiss());
            }
        });
        loading.show();
    }



    public void dialogueSuccess() {
        SweetAlertDialog loading = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        loading.setCancelable(false);
        loading.setConfirmText("OK");
        // loading.setCustomImage(R.drawable.ic_cloud_off_black_24dp);

        loading.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                com.nebulacompanies.ibo.view.MyTextView textTitle = (com.nebulacompanies.ibo.view.MyTextView) alertDialog.findViewById(R.id.title_text);
                com.nebulacompanies.ibo.view.MyTextView textContent = (com.nebulacompanies.ibo.view.MyTextView) alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                //btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                textTitle.setText("Code generated!");
                btnConfirm.setText("Ok");
                textContent.setText(message);

                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(v -> {
                    loading.dismiss();
                    // update list
                });
            }
        });
        loading.show();
    }


    public void dialogueFailure() {
        SweetAlertDialog loading = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        loading.setCancelable(false);
        loading.setConfirmText("OK");
        // loading.setCustomImage(R.drawable.ic_cloud_off_black_24dp);

        loading.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                com.nebulacompanies.ibo.view.MyTextView textTitle = (com.nebulacompanies.ibo.view.MyTextView) alertDialog.findViewById(R.id.title_text);
                com.nebulacompanies.ibo.view.MyTextView textContent = (com.nebulacompanies.ibo.view.MyTextView) alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                //btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                textTitle.setText(R.string.error_normal);
                btnConfirm.setText("Ok");
                textContent.setText(message);

                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(v -> {
                    loading.dismiss();

                });
            }
        });
        loading.show();
    }
}
