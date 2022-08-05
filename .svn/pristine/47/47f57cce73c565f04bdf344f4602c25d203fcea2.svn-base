package com.nebulacompanies.ibo.ecom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.PlaceOrderModel;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;

public class PaymentActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imgBackArrow, imgSearch;
    APIInterface mAPIInterface;
    MyButtonEcom btnPlaceOrder;
    Typeface typeface;

    MyTextViewEcom tvToolbarTitle;
    String deviceId,uniqueID,paymentName, paymentEmail,paymentOrderId,paymentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        /*TelephonyManager TelephonyMgr = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();*/

        deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid,null);

        Log.d("MDEVICEID Payment", "MDEVICEID Payment uniqueID: "+ uniqueID);
        if (deviceId == null || deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = this.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid,randomID);
                editor.apply();
                deviceId =  randomID;
            } else {
                deviceId = uniqueID;
            }
        }
        Log.d("MDEVICEID Payment", "MDEVICEID Payment: "+ deviceId);

        Intent paymentData = getIntent();
        if (paymentData != null) {
            paymentName = paymentData.getStringExtra( "name" );
            paymentEmail = paymentData.getStringExtra( "email" );
            paymentOrderId = paymentData.getStringExtra( "payment_id" );
            paymentID = paymentData.getStringExtra( "order_id" );
        }

        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);

        //mAPIInterface = APIClient.getClient(PaymentActivity.this).create(APIInterface.class);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/ember.ttf");

        placeOrder(deviceId);

        //getting the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        imgSearch = toolbar.findViewById(R.id.img_search);
        imgSearch.setVisibility(View.GONE);
        tvToolbarTitle = (MyTextViewEcom) toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("Payment");
        imgBackArrow = (ImageView) findViewById(R.id.img_back);
        btnPlaceOrder = (MyButtonEcom) findViewById(R.id.btn_place_order);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        
    }


    private void placeOrder(String deviceId) {
        final ProgressDialog mProgressDialog = new ProgressDialog(PaymentActivity.this, R.style.MyTheme);
        mProgressDialog.show();

        mProgressDialog.setCancelable(false);
        mProgressDialog.setContentView(R.layout.progressdialog_ecom);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                    if(mProgressDialog.isShowing()) {
                        //your logic here for back button pressed event
                        mProgressDialog.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });

        Call<PlaceOrderModel> wsCallingRegistation = mAPIInterface.placeOrder(deviceId);
        wsCallingRegistation.enqueue(new Callback<PlaceOrderModel>() {
            @Override
            public void onResponse(Call<PlaceOrderModel> call, Response<PlaceOrderModel> response) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                        //Toast.makeText(PaymentActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        //confirmDailogue();

                        SweetAlertDialog loading = new SweetAlertDialog( PaymentActivity.this, SweetAlertDialog.SUCCESS_TYPE );
                        loading.setCancelable( true );
                        loading.setConfirmText( "OK" );
                        loading.setOnShowListener( new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                                MyTextView textTitle = (MyTextView) alertDialog.findViewById( R.id.title_text );
                                MyTextView textContent = (MyTextView) alertDialog.findViewById( R.id.content_text );
                                MyButton btnConfirm = (MyButton) alertDialog.findViewById( R.id.confirm_button );
                                textTitle.setTextSize( TypedValue.COMPLEX_UNIT_SP, 15 );
                                textContent.setTextSize( TypedValue.COMPLEX_UNIT_SP, 12 );
                                // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                textContent.setTypeface( typeface );
                                textTitle.setTypeface( typeface );
                                btnConfirm.setTypeface( typeface );
                                alertDialog.setCancelable( false );
                                //textTitle.setText(response.body().getMessage());
                                textTitle.setText( "Order Successfully Placed" );
                                textContent.setText( "Your Order Number: " + response.body().getData() );
                                btnConfirm.setText( "OK" );
                                // textContent.setText("Pin you have entered is Invalid.");
                                textContent.setGravity( Gravity.NO_GRAVITY );
                                btnConfirm.setOnClickListener( new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loading.dismiss();
                                        //finish();
                                        Intent placeOderIntent = new Intent( PaymentActivity.this, MainActivity.class );
                                        placeOderIntent.putExtra( "SELECTEDVALUE", 1 );
                                        placeOderIntent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP );
                                        startActivity( placeOderIntent );
                                        overridePendingTransition( R.anim.push_right_in, R.anim.push_right_out );
                                    }
                                } );
                            }
                        } );

                        loading.show();
                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {
                        SweetAlertDialog loading = new SweetAlertDialog( PaymentActivity.this, SweetAlertDialog.ERROR_TYPE );
                        loading.setCancelable( true );
                        loading.setConfirmText( "OK" );
                        loading.setOnShowListener( new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                                MyTextView textTitle = (MyTextView) alertDialog.findViewById( R.id.title_text );
                                MyTextView textContent = (MyTextView) alertDialog.findViewById( R.id.content_text );
                                MyButton btnConfirm = (MyButton) alertDialog.findViewById( R.id.confirm_button );
                                textTitle.setTextSize( TypedValue.COMPLEX_UNIT_SP, 15 );
                                textContent.setTextSize( TypedValue.COMPLEX_UNIT_SP, 12 );
                                // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                textContent.setTypeface( typeface );
                                textTitle.setTypeface( typeface );
                                btnConfirm.setTypeface( typeface );
                                alertDialog.setCancelable( false );
                                //textTitle.setText(response.body().getMessage());
                                textTitle.setText( "Error" );
                                textContent.setText( response.body().getMessage() );
                                btnConfirm.setText( "OK" );
                                // textContent.setText("Pin you have entered is Invalid.");
                                textContent.setGravity( Gravity.NO_GRAVITY );
                                btnConfirm.setOnClickListener( new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loading.dismiss();
                                        //finish();
                                        Intent placeOderIntent = new Intent( PaymentActivity.this, MainActivity.class );
                                        placeOderIntent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP );
                                        startActivity( placeOderIntent );
                                        overridePendingTransition( R.anim.push_right_in, R.anim.push_right_out );
                                    }
                                } );
                            }
                        } );

                        loading.show();
                    }
                }
            }
            @Override
            public void onFailure(Call<PlaceOrderModel> call, Throwable t) {
                mProgressDialog.dismiss();
                android.util.Log.e("PaymentAPIError","PaymentAPIError: " +  t);
            }
        });
    }


}