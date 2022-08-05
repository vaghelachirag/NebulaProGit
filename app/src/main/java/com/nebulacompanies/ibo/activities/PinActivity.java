package com.nebulacompanies.ibo.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.GetPin;
import com.nebulacompanies.ibo.model.PostPinDetails;
import com.nebulacompanies.ibo.model.ReSendPin;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());
import static com.nebulacompanies.ibo.util.SetDateFormat.SetPinGmtTime;

public class PinActivity extends AppCompatActivity {
    Pinview pin;
    Button btnVerifyPin, btnSkip, resetPinButton;
    ImageView imgBackArrow;
    boolean home;
    private APIInterface mAPIInterface;
    Typeface typeface;
    RelativeLayout rlNoPay, rlYesPay;
    MyTextView exdatetxt, pindatetxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            home = b.getBoolean("home");
        }
        mAPIInterface = APIClient.getClient(PinActivity.this).create(APIInterface.class);
        pin = (Pinview) findViewById(R.id.pinview);
        btnVerifyPin = (Button) findViewById(R.id.validate_button);
        btnSkip = (Button) findViewById(R.id.skip_button);
        resetPinButton = (Button) findViewById(R.id.reset_pin_button);
        imgBackArrow = (ImageView) findViewById(R.id.img_back_arrow);
        rlNoPay = (RelativeLayout) findViewById(R.id.rl_no_pay);
        rlYesPay = (RelativeLayout) findViewById(R.id.rl_yes_pay);
        exdatetxt = (MyTextView) findViewById(R.id.tv_pin_tc);
        pindatetxt = (MyTextView) findViewById(R.id.tv_pin_date);
        GetPinDetails();
        if (home) {
            imgBackArrow.setVisibility(View.VISIBLE);
            btnSkip.setVisibility(View.GONE);
            RelativeLayout.LayoutParams testLP = new RelativeLayout.LayoutParams(200, 75);
           /* RelativeLayout.LayoutParams testLP = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);*/
            testLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            testLP.addRule(RelativeLayout.CENTER_IN_PARENT);
            testLP.setMargins(0, 0, 0, 20);

            this.resetPinButton.setLayoutParams(testLP);
        }

        typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        btnVerifyPin.setTypeface(typeface);
        btnSkip.setTypeface(typeface);
        resetPinButton.setTypeface(typeface);
        pin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                //Make api calls here or what not
                // Toast.makeText(PinActivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
            }
        });

        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //Skip to DashBoard Dialogue
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog loading = new SweetAlertDialog(PinActivity.this, SweetAlertDialog.WARNING_TYPE);
                loading.setCancelable(true);
                loading.setConfirmText("OK");
                loading.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                        MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                        MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                        MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                        /*textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                        textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                        btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                        textContent.setTypeface(typeface);
                        textTitle.setTypeface(typeface);
                        btnConfirm.setTypeface(typeface);*/
                        textTitle.setText("Skip");
                        btnConfirm.setText("OK");
                        textContent.setText("Are you sure you want to skip? You can always enter the Payout PIN, before the deadline, from the sidebar menu section in your IBO dashboard.");
                        textContent.setGravity(Gravity.NO_GRAVITY);
                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loading.dismiss();
                                finish();
                            }
                        });
                    }
                });

                loading.show();
            }
        });


    }


    void GetPinDetails() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mAPIInterface = APIClient.getClient(PinActivity.this).create(APIInterface.class);
            Call<GetPin> wsCallinggetPin = mAPIInterface.getGetPin();
            wsCallinggetPin.enqueue(new Callback<GetPin>() {
                @Override
                public void onResponse(Call<GetPin> call, Response<GetPin> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getData().getVerifyStatus() != null) {
                            if (response.body().getData().getVerifyStatus() != true && response.body().getData().getIsKYC().equalsIgnoreCase("Yes")) {
                                int date = 1555221264;
                                if (response.body().getData().getCurrentDate() < response.body().getData().getPopUpCloseDateTime()) {
                                    //show dialog
                                    rlNoPay.setVisibility(View.GONE);
                                    rlYesPay.setVisibility(View.VISIBLE);
                                    Config.EnterPin = "";
                                    pindatetxt.setText("(For closing dated " + SetPinGmtTime(response.body().getData().getPopUpCloseDateTime()) + ")");
                                    exdatetxt.setText("This PIN will expire on " + SetPinGmtTime(response.body().getData().getPopUpCloseDateTime()) + ". Failure to enter the PIN before deadline will result in non payment of your Income.");
                                    btnVerifyPin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            //Config.EnterPin = editText.getText().toString();
                                            Config.EnterPin = pin.getValue().toString();
                                            if (!Config.EnterPin.equals(response.body().getData().getPinNumber())) {
                                                errorDailogueShow();
                                            } else {
                                                if (Utils.isNetworkAvailable(getApplicationContext())) {
                                                    PostData(response.body().getData().getID().toString());
                                                }
                                                else
                                                {
                                                   // Toast.makeText(PinActivity.this, R.string.error_msg_network, Toast.LENGTH_SHORT).show();
                                                    errorNetworkDailogue();
                                                }
                                            }
                                        }
                                    });
                                    //PIN Resent Dialogue
                                    resetPinButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            SweetAlertDialog loading = new SweetAlertDialog(PinActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                            loading.setCancelable(true);
                                            loading.setConfirmText("OK");
                                            loading.setOnShowListener(new DialogInterface.OnShowListener() {
                                                @Override
                                                public void onShow(DialogInterface dialog) {
                                                    SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                                                    MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                                                    MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                                                    MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                                                    /*textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                                    textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                                    btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                                    textContent.setTypeface(typeface);
                                                    textTitle.setTypeface(typeface);
                                                    btnConfirm.setTypeface(typeface);*/
                                                    textTitle.setText("PIN Resent");
                                                    btnConfirm.setText("OK");
                                                    textContent.setText("The Payout PIN has been sent to your Email and Mobile.");
                                                    textContent.setGravity(Gravity.NO_GRAVITY);
                                                    btnConfirm.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            GetResendPin(response.body().getData().getID().toString());
                                                            loading.dismiss();
                                                        }
                                                    });
                                                }
                                            });
                                            loading.show();
                                        }
                                    });
                                } else {
                                    rlNoPay.setVisibility(View.VISIBLE);
                                    rlYesPay.setVisibility(View.GONE);
                                }

                            } else {
                                rlNoPay.setVisibility(View.VISIBLE);
                                rlYesPay.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        rlNoPay.setVisibility(View.VISIBLE);
                        rlYesPay.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<GetPin> call, Throwable t) {

                }
            });
        }
    }


    private void PostData(String id) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(PinActivity.this, R.style.MyTheme);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mAPIInterface = APIClient.getClient(PinActivity.this).create(APIInterface.class);
            Call<PostPinDetails> wsCallingPostPinDetail = mAPIInterface.getpostpin(id);
            wsCallingPostPinDetail.enqueue(new Callback<PostPinDetails>() {
                @Override
                public void onResponse(Call<PostPinDetails> call, Response<PostPinDetails> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                //   Toast.makeText(PinActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                confirmDailogue();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<PostPinDetails> call, Throwable t) {

                }
            });

        }
    }

    private void GetResendPin(String id) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(PinActivity.this, R.style.MyTheme);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mAPIInterface = APIClient.getClient(PinActivity.this).create(APIInterface.class);
            Call<ReSendPin> wsCallinggetResendPin = mAPIInterface.getResendPin(id);
            wsCallinggetResendPin.enqueue(new Callback<ReSendPin>() {
                @Override
                public void onResponse(Call<ReSendPin> call, Response<ReSendPin> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                Toast.makeText(PinActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ReSendPin> call, Throwable t) {

                }
            });

        }

    }

    //Invalid PIN Dailogue
    private void errorDailogueShow() {
        SweetAlertDialog loading = new SweetAlertDialog(PinActivity.this, SweetAlertDialog.ERROR_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                /*textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);*/
                textTitle.setText("Invalid Pin");
                btnConfirm.setText("OK");
                textContent.setText("Pin you have entered is Invalid.");
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loading.dismiss();
                        //  finish();
                    }
                });
            }
        });

        loading.show();
    }


    //Confirm DailogueBox
    private void confirmDailogue() {
        SweetAlertDialog loading = new SweetAlertDialog(PinActivity.this, SweetAlertDialog.SUCCESS_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                /*textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);*/
                textTitle.setText("Success");
                btnConfirm.setText("OK");
                textContent.setText("Pin Verified Successfully.");
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loading.dismiss();
                        finish();
                    }
                });
            }
        });
        loading.show();
    }


    public void onBackPressed() {
        if (home) {
            super.onBackPressed();
        } else {
            moveTaskToBack(false);
        }
    }



    //No Internet Dialogue
    private void errorNetworkDailogue() {
        SweetAlertDialog loading = new SweetAlertDialog(PinActivity.this, SweetAlertDialog.ERROR_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                /*textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);*/
                textTitle.setText("Network Error");
                textContent.setText(R.string.error_msg_network);
                btnConfirm.setText("OK");
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loading.dismiss();
                        //  finish();
                    }
                });
            }
        });

        loading.show();
    }
}