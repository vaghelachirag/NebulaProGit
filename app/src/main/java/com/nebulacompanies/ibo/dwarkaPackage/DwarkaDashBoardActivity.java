package com.nebulacompanies.ibo.dwarkaPackage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.BuildConfig;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.DashboardActivity;
import com.nebulacompanies.ibo.dwarkaPackage.adapter.DwarkaPackageAdapter;
import com.nebulacompanies.ibo.dwarkaPackage.model.DwarkaPlanModel;
import com.nebulacompanies.ibo.dwarkaPackage.model.PackageData;
import com.nebulacompanies.ibo.dwarkaPackage.model.PackageEMIModelDwarka;
import com.nebulacompanies.ibo.dwarkaPackage.model.PackageListModelDwarka;
import com.nebulacompanies.ibo.dwarkaPackage.model.PaymentSuccess;
import com.nebulacompanies.ibo.dwarkaPackage.model.PaymentSuccessResult;
import com.nebulacompanies.ibo.ecom.model.AddAddressDetail;
import com.nebulacompanies.ibo.ecom.model.CardData;
import com.nebulacompanies.ibo.ecom.model.CardDwarkaModelEcom;
import com.nebulacompanies.ibo.ecom.utils.ActionBottomDialogFragment;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.DwarkaUpiModel;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialogCart;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.ecom.utils.Utils.actionCloseDialog;
import static com.nebulacompanies.ibo.ecom.utils.Utils.actionUservalid;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class DwarkaDashBoardActivity extends AppCompatActivity implements
        ActionBottomDialogFragment.ItemClickListener,
        PaymentResultWithDataListener {

    APIInterface mAPIInterface;
    MaterialProgressBar mProgressDialog;
    ArrayList<CardData> cardData = new ArrayList<>();
    ArrayList<PaymentSuccessResult> paymentSuccessResults = new ArrayList<>();
    ArrayList<PackageData> packageData = new ArrayList<>();
    RecyclerView rvDwarkaPackage;
    private DwarkaPackageAdapter dwarkaPackageAdapter;
    Integer productID, productPrice, position;
    CheckBox chkTerms, chkFreeLookUp;
    Toolbar toolbar;
    ImageView imgBackArrow, imgError;
    Typeface typeface;
    boolean isWaiveOff;
    String amount, currency, name, description, orderId, email, mobileNo, address, key, theme, receiptId, paymentResult, version;
    boolean isPaymentFlag;
    Session session;
    LinearLayout lnDwarka;
    String versionName;
    UtilsVersion utils = new UtilsVersion();
    SharedPreferences SharedPrefTransID;
    MyButtonEcom btnBuy;
    private MyTextViewEcom txtErrorContent, txtRetry;
    MyBoldTextViewEcom txtErrorTitle;
    private RelativeLayout llEmpty;
    Utils utils1 = new Utils();
    boolean loginDone = false;
    IntentFilter filter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_dashboard);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        session = new Session(this);
        mAPIInterface = APIClient.getClient(DwarkaDashBoardActivity.this).create(APIInterface.class);
        mProgressDialog = findViewById(R.id.progresbar);

        int versionCode = BuildConfig.VERSION_CODE;
        versionName = BuildConfig.VERSION_NAME;
        Log.d("version322343243535C", "version322343243535C: " + versionCode);
        Log.d("version322343243535N", "version322343243535N: " + versionName);

        toolbar = findViewById(R.id.toolbar_search);
        imgBackArrow = findViewById(R.id.img_back);

        chkTerms = findViewById(R.id.chk_terms);
        chkFreeLookUp = findViewById(R.id.chk_free_look_up);
        rvDwarkaPackage = findViewById(R.id.rv_dwarka_package);
        lnDwarka = findViewById(R.id.ln_dwarka);
        btnBuy = findViewById(R.id.btn_buy_package);
        rvDwarkaPackage.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DwarkaDashBoardActivity.this, LinearLayoutManager.VERTICAL, false);
        rvDwarkaPackage.setLayoutManager(mLayoutManager);
        rvDwarkaPackage.setItemAnimator(new DefaultItemAnimator());
        typeface = Typeface.createFromAsset(getAssets(), "fonts/ember.ttf");
        imgBackArrow.setOnClickListener(view -> onBackPressed());

        filter = new IntentFilter();
        filter.addAction(actionUservalid);
        registerReceiver(myReceiver, filter);

        llEmpty = findViewById(R.id.llEmpty);
        imgError = findViewById(R.id.imgError);
        txtErrorTitle = findViewById(R.id.txtErrorTitle);
        txtErrorContent = findViewById(R.id.txt_error_content);
        txtRetry = findViewById(R.id.txtRetry);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("custom-message"));

        utils.checkVersion(this);
        getPackageList();
        sendTransaction(false);

        txtRetry.setOnClickListener(v -> {
            lnDwarka.setVisibility(View.VISIBLE);
            getPackageList();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        utils1.checkExpireUser(mAPIInterface, this, session);
    }

    @Override
    public void onBackPressed() {
       /* if (loginDone) {
            Intent backIntent = new Intent();
            setResult(RESULT_OK, backIntent);
            finish();
        } else {*/
        if (!mProgressDialog.isShown()) {
            super.onBackPressed();
        }
        // }
    }

    public void showBottomSheet(View view) {
        if (!session.getLogin()) {
            utils1.openLoginDialog(this, utils1.gotoDwarka);
        } else {
            if (chkTerms.isChecked() && productPrice != null) {
                utils1.getExpireUser(mAPIInterface, this, session);
            } else {
                Toast toast = Toast.makeText(DwarkaDashBoardActivity.this, "Please agree to Terms and Conditions and select any package", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(actionUservalid)) {
                if (dataPackage.getShowPackage()) {
                    getPlanList();
                } else {
                    callRazor();
                }

            } else if (intent.getAction().equalsIgnoreCase(actionCloseDialog)) {
                Toast.makeText(DwarkaDashBoardActivity.this, "There is an error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void callRazor() {
        if (calledGateway)
            openBottomsheet();
        else
            callAPIGateway();
    }

    boolean callRazorpay = true;
    ArrayList<DwarkaUpiModel.UPIList> upiListArrayList = new ArrayList<>();
    boolean calledGateway = false;

    private void callAPIGateway() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Call<DwarkaUpiModel> wsCallingEvents = mAPIInterface.getDwarkaGateway();
            wsCallingEvents.enqueue(new Callback<DwarkaUpiModel>() {
                @Override
                public void onResponse(@NotNull Call<DwarkaUpiModel> call, @NotNull Response<DwarkaUpiModel> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            assert response.body() != null;
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                calledGateway = true;
                                callRazorpay = response.body().getData().getUPIGateWay().equals("1");
                                upiListArrayList.clear();
                                upiListArrayList.addAll(response.body().getData().getUPIList());
                                openBottomsheet();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<DwarkaUpiModel> call, @NotNull Throwable t) {
                }
            });
        } else {
            utils1.dialogueNoInternet(this);
        }
    }

    private void openBottomsheet() {
        ActionBottomDialogFragment addPhotoBottomDialogFragment =
                ActionBottomDialogFragment.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                ActionBottomDialogFragment.TAG);
    }


    @Override
    public void onItemClick(String item) {
        isWaiveOff = chkFreeLookUp.isChecked();
        setButtons(false);
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mProgressDialog.setVisibility(View.VISIBLE);
            Call<CardDwarkaModelEcom> wsCallingEvents;
            if (dataPackage.getShowPackage()) {
                wsCallingEvents = mAPIInterface.getTimeplanUPIDwarka(
                        session.getIboKeyId(), String.valueOf(productPrice),
                        String.valueOf(isWaiveOff), String.valueOf(productID),
                        String.valueOf(planID));
            } else {
                if (!callRazorpay && item.equalsIgnoreCase("UPI / Paytm / Google Pay")) {
                    wsCallingEvents = mAPIInterface.getUPIDwarka(session.getIboKeyId(), String.valueOf(productPrice), String.valueOf(isWaiveOff), String.valueOf(productID));
                } else
                    wsCallingEvents = mAPIInterface.getCardDetailsDwarka(session.getIboKeyId(), String.valueOf(productPrice), String.valueOf(isWaiveOff), String.valueOf(productID), versionName);
            }

            wsCallingEvents.enqueue(new Callback<CardDwarkaModelEcom>() {
                @Override
                public void onResponse(Call<CardDwarkaModelEcom> call, Response<CardDwarkaModelEcom> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    cardData.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CARDAPI", "CARDAPI: " + response);

                            assert response.body() != null;
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                Log.d("CARDAPI", "CARDAPI: " + response);
                                CardDwarkaModelEcom.Data mdata = response.body().getData();
                                key = mdata.getKey();
                                amount = mdata.getAmount();
                                currency = mdata.getCurrency();
                                name = mdata.getName();
                                orderId = mdata.getOrderId();
                                email = mdata.getEmail();
                                mobileNo = mdata.getMobileNo();
                                address = mdata.getAddress();
                                description = mdata.getDescription();
                                theme = mdata.getTheme();
                                receiptId = mdata.getReceiptId();
                                version = mdata.getVersion();
                                startPayment(item);
                            } else {
                                setButtons(true);
                            }
                        }
                    } else
                        setButtons(true);
                }

                @Override
                public void onFailure(Call<CardDwarkaModelEcom> call, Throwable t) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    setButtons(true);
                }
            });

    /*} else

    {
        if (!callRazorpay && item.equalsIgnoreCase("UPI / Paytm / Google Pay")) {
            Call<CardDwarkaModelEcom> wsCallingEvents = mAPIInterface.getUPIDwarka(session.getIboKeyId(), String.valueOf(productPrice), String.valueOf(isWaiveOff), String.valueOf(productID));
            wsCallingEvents.enqueue(new Callback<CardDwarkaModelEcom>() {
                @Override
                public void onResponse(@NotNull Call<CardDwarkaModelEcom> call, @NotNull Response<CardDwarkaModelEcom> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    cardData.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CARDAPI", "CARDAPI: " + response);

                            assert response.body() != null;
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                Log.d("CARDAPI", "CARDAPI: " + response);
                                CardDwarkaModelEcom.Data mdata = response.body().getData();
                                key = mdata.getKey();
                                amount = mdata.getAmount();
                                currency = mdata.getCurrency();
                                name = mdata.getName();
                                orderId = mdata.getOrderId();
                                email = mdata.getEmail();
                                mobileNo = mdata.getMobileNo();
                                address = mdata.getAddress();
                                description = mdata.getDescription();
                                theme = mdata.getTheme();
                                receiptId = mdata.getReceiptId();
                                version = mdata.getVersion();
                                startPayment(item);
                            } else {
                                setButtons(true);
                            }
                        }
                    } else
                        setButtons(true);
                }

                @Override
                public void onFailure(@NotNull Call<CardDwarkaModelEcom> call, @NotNull Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    setButtons(true);
                }
            });
        } else {

            Call<CardDetailsModelEcom> wsCallingEvents = mAPIInterface.getCardDetailsDwarka(session.getIboKeyId(), String.valueOf(productPrice), String.valueOf(isWaiveOff), String.valueOf(productID), versionName);
            wsCallingEvents.enqueue(new Callback<CardDetailsModelEcom>() {
                @Override
                public void onResponse(@NotNull Call<CardDetailsModelEcom> call, @NotNull Response<CardDetailsModelEcom> response) {
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    cardData.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CARDAPI", "CARDAPI: " + response);

                            assert response.body() != null;
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                Log.d("CARDAPI", "CARDAPI: " + response);

                                key = response.body().getData().getShippingKey();
                                amount = response.body().getData().getShippingAmount();
                                currency = response.body().getData().getShippingCurrency();
                                name = response.body().getData().getShippingName();
                                orderId = response.body().getData().getShippingOrderId();
                                email = response.body().getData().getShippingEmail();
                                mobileNo = response.body().getData().getShippingMobileNo();
                                address = response.body().getData().getShippingAddress();
                                description = response.body().getData().getShippingDescription();
                                theme = response.body().getData().getShippingTheme();
                                receiptId = response.body().getData().getReceiptId();
                                version = response.body().getData().getVersion();
                                startPayment(item);

                            } else {
                                setButtons(true);
                            }

                        } else
                            setButtons(true);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CardDetailsModelEcom> call, @NotNull Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    setButtons(true);
                    Log.d("CARDAPI", "CARDAPI: " + t);
                }
            });
        }
    }*/
        } else setButtons(true);
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Log.d("Order Success", "Order Success: " + s + " Payment ID: " + paymentData.getPaymentId() +
                " Signature: " + paymentData.getSignature() + " Name:" + paymentData.getUserContact() +
                " Email:" + paymentData.getUserEmail() + " External Wallet:" + paymentData.getExternalWallet() +
                "Order ID: " + paymentData.getOrderId() + " All Data:" + paymentData.getData());
        setSuccessDialog();

        String signature = paymentData.getSignature();
        Log.d("Signature ", "Signature: " + signature);

        paymentSuccessDetails(session.getLoginID(), paymentData.getPaymentId(), orderId, signature, String.valueOf(productID), String.valueOf(productPrice), String.valueOf(isWaiveOff));
    }

    @SuppressLint("SetTextI18n")
    private void setSuccessDialog() {

        SweetAlertDialogCart loading = new SweetAlertDialogCart(DwarkaDashBoardActivity.this, SweetAlertDialogCart.SUCCESS_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);
            textTitle.setText("Success");
            textContent.setText("Paid ₹" + productPrice);
            btnConfirm.setText("OK");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {
                loading.dismiss();
                finish();
            });
        });

        loading.show();
        imgBackArrow.setEnabled(false);
        isWaiveOff = chkFreeLookUp.isChecked();
        lnDwarka.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.d("Order Error", "Order Error: " + s + " Payment ID: " + paymentData.getPaymentId() +
                " Signature: " + paymentData.getSignature() + " Name:" + paymentData.getUserContact() +
                " Email:" + paymentData.getUserEmail() + " External Wallet:" + paymentData.getExternalWallet() +
                "Order ID: " + paymentData.getOrderId() + " All Data:" + paymentData.getData());

        Log.d("payment Code: ", "payment Code: " + i);
        if (i == 0) {
            paymentCancelled();
        } else if (i == 2) {
            paymentCancelledInternetIssue();
        } else {
            SweetAlertDialogCart loading = new SweetAlertDialogCart(DwarkaDashBoardActivity.this, SweetAlertDialogCart.ERROR_TYPE);
            loading.setCancelable(true);
            loading.setConfirmText("OK");
            loading.setOnShowListener(dialog -> {
                SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
                MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
                MyTextView textContent = alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                alertDialog.setCancelable(false);
                textContent.setText("If the amount was debited, kindly wait for 8 hours until we verify and update your payment. \n Error: " + i + "\n" + s);
                textTitle.setText("Error: " + i);
                btnConfirm.setText("OK");
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(v -> loading.dismiss());
            });
            loading.show();
        }
    }

    public void startPayment(String itemName) {

        String paymentMethod = "";
        switch (itemName) {
            case "UPI / Paytm / Google Pay":
                paymentMethod = "upi";
                break;
            case "Credit / Debit Card":
                paymentMethod = "card";
                break;
            case "Wallet":
                paymentMethod = "wallet";
                break;
            case "Net Banking":
                paymentMethod = "netbanking";
                break;
        }

        if (!callRazorpay && paymentMethod.equals("upi")) {
            startUpiDialog();
        } else {
            final Activity activity = this;

            final Checkout co = new Checkout();
            co.setKeyID(key);
            co.setImage(R.drawable.nebula_launcher);
            try {
                JSONObject options = new JSONObject();
                options.put("name", name);
                options.put("description", description);
                options.put("currency", currency);
                options.put("amount", amount);
                options.put("theme.color", theme);
                options.put("receipt", receiptId);
                options.put("order_id", orderId);
                options.put("payment_capture", false);

                // Additional Information Pass to Razor Pay
                JSONObject notes = new JSONObject();
                notes.put("notes", address);
                notes.put("receipt_id", receiptId);
                options.put("notes", notes);

                JSONObject preFill = new JSONObject();
                preFill.put("email", email);
                preFill.put("contact", mobileNo);
                // preFill.put("contact", "9726419833");

                preFill.put("method", paymentMethod);
                options.put("prefill", preFill);
                co.open(activity, options);
            } catch (Exception e) {
                Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                        .show();
                e.printStackTrace();
                setButtons(true);
            }
        }
    }

    RecyclerView rvupiApps;
    Uri uriapp;
    android.app.AlertDialog dialogApp;
    ImageView imgClose;
    MyButtonEcom btnApply;

    private void startUpiDialog() {
        uriapp = new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", getString(R.string.vpa_holiday))
                .appendQueryParameter("pn", getString(R.string.payee_holiday))
                .appendQueryParameter("tr", orderId)
                .appendQueryParameter("tn", description)
                .appendQueryParameter("am", String.valueOf(amount))
                .appendQueryParameter("cu", "INR")
                .build();


        if (upiListArrayList.size() > 0) {
            // Create an alert builder
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

            // set the custom layout
            final View customLayout = getLayoutInflater().inflate(R.layout.dialog_upi_app, null);
            builder.setView(customLayout);
            dialogApp = builder.create();

            rvupiApps = customLayout.findViewById(R.id.rvApps);
            imgClose = customLayout.findViewById(R.id.img_sort_close);
            btnApply = customLayout.findViewById(R.id.btn_apply);
            imgClose.setOnClickListener(v -> dialogApp.dismiss());
            btnApply.setOnClickListener(v -> {
                if (selmodel != null && !TextUtils.isEmpty(selmodel.getPackageName()))
                    callUPI(selmodel);
                else {
                    Toast.makeText(DwarkaDashBoardActivity.this, "Please select payment mode.", Toast.LENGTH_SHORT).show();
                }
            });
            // int numberOfColumns = 4;
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

            rvupiApps.setLayoutManager(mLayoutManager);
            MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this);
            rvupiApps.setAdapter(adapter);
            dialogApp.setCancelable(false);
            dialogApp.show();
        } else {
            setButtons(true);
            Toast.makeText(this, "This payment mode is not available on your device.", Toast.LENGTH_LONG).show();
        }
    }

    int GOOGLE_REQUEST_CODE = 111;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("result::", requestCode + " : onActivityResult : " + resultCode);
        if (requestCode == GOOGLE_REQUEST_CODE) {
            if (data != null) {
                Log.d("result::", requestCode + " : onActivityResult data: " + resultCode + " : " + data);
                Log.d("result::", " : Bundle : " + data.getExtras());
                if (resultCode == Activity.RESULT_OK) {
                    // result::: paymentResponse : txnId=&responseCode=ZD&Status=FAILURE&txnRef=1610974456636
                    // result::: Bundle[{Status=FAILURE, txnRef=1610974456636, ApprovalRefNo=, response=txnId=&responseCode=ZD&Status=FAILURE&txnRef=1610974456636, txnId=, responseCode=ZD, TrtxnRef=1610974456636}] : -1
                    //Bundle[{response=txnId=UPI2eaca6bc9fa34c49a6cdd80993ad7907&responseCode=00&ApprovalRefNo=&Status=SUCCESS&txnRef=Order_637489684277312418}]
                    String Status = data.getStringExtra("Status");
                    String paymentResponse = data.getStringExtra("response");
                    Log.d("result::", Status + " : paymentResponse : " + paymentResponse);
                    if (TextUtils.isEmpty(Status)) {
                        assert paymentResponse != null;
                        String[] arrayString = paymentResponse.split("&");
                        JSONObject jsonObject = new JSONObject();
                        for (String strData : arrayString) {
                            String[] kayvalue = strData.split("=");
                            try {
                                if (kayvalue.length > 1)
                                    jsonObject.put(kayvalue[0], kayvalue[1]);
                                else
                                    jsonObject.put(kayvalue[0], "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        try {
                            Status = jsonObject.getString("Status");
                            Log.d("result::", Status + " : Status : String ");

                            doSuccess(jsonObject.getString("txnId"),
                                    orderId,
                                    jsonObject.getString("ApprovalRefNo"),
                                    jsonObject.getString("responseCode"),
                                    Status,
                                    jsonObject.getString("txnRef"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            doError();
                        }
                    } else {
                        assert Status != null;
                        doSuccess(data.getStringExtra("txnId"), orderId, data.getStringExtra("ApprovalRefNo"), data.getStringExtra("responseCode"), Status, data.getStringExtra("txnRef"));
                    }
                } else {
                    doError();
                }
            } else {
                Log.d("result::", "cancel dialog : " + resultCode);
                doError();
            }
        } else if (requestCode == utils1.gotoDwarka) {
            if (resultCode == Activity.RESULT_OK) {
                Intent mIntent = new Intent(Utils.actionLogin);
                sendBroadcast(mIntent);
                loginDone = true;
                showBottomSheet(null);
            }
        }
        setButtons(true);
    }

    private void doError() {
        setButtons(true);
        paymentCancelled();
    }

    public void setButtons(boolean b) {
        imgBackArrow.setEnabled(b);
        btnBuy.setEnabled(b);
    }

    private void doSuccess(String transID, String orderId, String aproval, String response, String Status, String txnRef) {
        if (Status.equalsIgnoreCase("success")) {
            setSuccessDialog();
            setUPITransactionID(session.getIboKeyId(),
                    transID,
                    orderId,
                    aproval,
                    response,
                    Status,
                    txnRef);
        } else if (Status.equalsIgnoreCase("failure")) {
            setButtons(true);
            paymentFail();
        } else {
            setButtons(true);
            paymentCancelled();
        }
    }

    private void setUPITransactionID(String iboid, String transID, String orderId, String aproval, String response, String Status, String txnRef) {
        SharedPrefTransID.edit().putString("PREFtransId", transID).apply();
        SharedPrefTransID.edit().putString("PREFOrderId", orderId).apply();
        SharedPrefTransID.edit().putString("PREFAproval", aproval).apply();
        SharedPrefTransID.edit().putString("PREFResponse", response).apply();
        SharedPrefTransID.edit().putString("PREFStatus", Status).apply();
        SharedPrefTransID.edit().putString("PREFtxnRef", txnRef).apply();
        SharedPrefTransID.edit().putString("PREFiboid", iboid).apply();

        sendTransaction(true);
    }

    private void sendTransaction(boolean show) {
        // OrderID,TxnId,,ApprovalRef,ResponseCode,Status,txnRef,Remarks
        SharedPrefTransID = getSharedPreferences(PrefUtils.prefTransactionDwarka, 0);

        String transID = SharedPrefTransID.getString("PREFtransId", "0");
        String orderID = SharedPrefTransID.getString("PREFOrderId", "0");
        String approval = SharedPrefTransID.getString("PREFAproval", "0");
        String response = SharedPrefTransID.getString("PREFResponse", "0");
        String status = SharedPrefTransID.getString("PREFStatus", "0");
        String refId = SharedPrefTransID.getString("PREFtxnRef", "0");
        String iboid = SharedPrefTransID.getString("PREFiboid", "0");
        if (!transID.equals("0")) {
            // call API for submitting transaction
            paymentSuccessUPIDetails(show, iboid, orderID, transID, approval, response, status, refId);
        }
    }

    @SuppressLint("SetTextI18n")
    private void paymentFail() {

        SweetAlertDialog loading = new SweetAlertDialog(DwarkaDashBoardActivity.this, SweetAlertDialog.ERROR_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
            MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);
            textTitle.setText("Payment Failed");
            textContent.setText("Transaction not successful");
            btnConfirm.setText("OK");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> loading.dismiss());
        });

        loading.show();
    }

    private void paymentSuccessDetails(String userName, String paymentId, String orderId, String signature, String productId, String totalAmount, String isWaiveOff) {

        mProgressDialog.setVisibility(View.VISIBLE);

        Call<PaymentSuccess> wsCallingAddAddress = mAPIInterface.paymentSuccess(userName, paymentId, orderId, signature, productId, totalAmount, isWaiveOff);
        wsCallingAddAddress.enqueue(new Callback<PaymentSuccess>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<PaymentSuccess> call, @NotNull Response<PaymentSuccess> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Log.d("Response Address Start", "Response Address Start " + response.body().getMessage());
                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    paymentSuccessResults.clear();
                    if (response.code() == 200) {
                        Log.d("Response Address 200", "Response Address 200 " + response.body().getMessage());
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                            Log.d("Payment Success", "Payment Success " + response.body().getMessage());
                            Log.e("Payment Success", "Payment Success" + new Gson().toJson(response.body()));
                            paymentResult = response.body().getData().getPaymentResult();
                            isPaymentFlag = response.body().getData().isPaymentFlag();
                            // paymentSuccess();

                            SweetAlertDialogCart loading;
                            if (isPaymentFlag) {
                                loading = new SweetAlertDialogCart(DwarkaDashBoardActivity.this, SweetAlertDialogCart.SUCCESS_TYPE);
                                loading.setCancelable(true);
                                loading.setConfirmText("OK");
                                loading.setOnShowListener(dialog -> {
                                    SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
                                    MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
                                    MyTextView textContent = alertDialog.findViewById(R.id.content_text);
                                    MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
                                    textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                    textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                    textContent.setTypeface(typeface);
                                    textTitle.setTypeface(typeface);
                                    btnConfirm.setTypeface(typeface);
                                    alertDialog.setCancelable(false);
                                    textTitle.setText("Paid ₹" + productPrice);
                                    textContent.setText(paymentResult);
                                    btnConfirm.setText("OK");
                                    textContent.setGravity(Gravity.NO_GRAVITY);
                                    btnConfirm.setOnClickListener(v -> {
                                        imgBackArrow.setEnabled(true);
                                        loading.dismiss();
                                        Intent intentPaymentSuccess = new Intent(DwarkaDashBoardActivity.this, DashboardActivity.class);
                                        intentPaymentSuccess.putExtra("PAYMENTSUCCESS", 1);
                                        startActivity(intentPaymentSuccess);
                                        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                                    });
                                });

                            } else {
                                loading = new SweetAlertDialogCart(DwarkaDashBoardActivity.this, SweetAlertDialogCart.ERROR_TYPE);
                                loading.setCancelable(true);
                                loading.setConfirmText("OK");
                                loading.setOnShowListener(dialog -> {
                                    SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
                                    MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
                                    MyTextView textContent = alertDialog.findViewById(R.id.content_text);
                                    MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
                                    textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                    textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                    textContent.setTypeface(typeface);
                                    textTitle.setTypeface(typeface);
                                    btnConfirm.setTypeface(typeface);
                                    alertDialog.setCancelable(false);
                                    textTitle.setText("Transaction not processed");
                                    textContent.setText(paymentResult);
                                    btnConfirm.setText("OK");
                                    textContent.setGravity(Gravity.NO_GRAVITY);
                                    btnConfirm.setOnClickListener(v -> {
                                        imgBackArrow.setEnabled(true);
                                        loading.dismiss();
                                        finish();
                                    });
                                });
                            }
                            loading.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<PaymentSuccess> call, @NotNull Throwable t) {
                mProgressDialog.setVisibility(View.GONE);
                Log.d("Response Address Error", "Response Address Error " + t);
            }
        });
    }


    private void paymentSuccessUPIDetails(boolean show, String iboid, String OrderID, String TxnId, String ApprovalRef, String ResponseCode, String Status, String txnRef) {
        if (show)
            mProgressDialog.setVisibility(View.VISIBLE);

        Call<AddAddressDetail> wsCallingAddAddress = mAPIInterface.paymentUpiSuccess(iboid, OrderID, TxnId, ApprovalRef, ResponseCode, Status, txnRef, "GooglePay");
        wsCallingAddAddress.enqueue(new Callback<AddAddressDetail>() {
            @Override
            public void onResponse(@NotNull Call<AddAddressDetail> call, @NotNull Response<AddAddressDetail> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Log.d("Response Address Start", "Response Address Start " + response.body().getMessage());

                    if (show) {

                        if (mProgressDialog != null) {
                            mProgressDialog.setVisibility(View.GONE);
                        }
                        paymentSuccessResults.clear();
                        if (response.code() == 200) {
                            Log.d("Response Address 200", "Response Address 200 " + response.body().getMessage());
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                Log.d("Payment Success", "Payment Success " + response.body().getMessage());
                                Log.e("Payment Success", "Payment Success" + new Gson().toJson(response.body()));

                                paymentResult = "Paid";
                                //  if (isPaymentFlag) {
                               /* SweetAlertDialogCart loading = new SweetAlertDialogCart(DwarkaDashBoardActivity.this, SweetAlertDialogCart.SUCCESS_TYPE);
                                loading.setCancelable(true);
                                loading.setConfirmText("OK");
                                loading.setOnShowListener((DialogInterface.OnShowListener) dialog -> {
                                    SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
                                    MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                                    MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                                    MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                                    textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                                    textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                    textContent.setTypeface(typeface);
                                    textTitle.setTypeface(typeface);
                                    btnConfirm.setTypeface(typeface);
                                    alertDialog.setCancelable(false);
                                    //textTitle.setText(response.body().getMessage());
                                    textTitle.setText("Paid ₹" + productPrice);
                                    textContent.setText(paymentResult);
                                    btnConfirm.setText("OK");
                                    // textContent.setText("Pin you have entered is Invalid.");
                                    textContent.setGravity(Gravity.NO_GRAVITY);
                                    btnConfirm.setOnClickListener(v -> {
                                        // lnDwarka.setVisibility( View.VISIBLE );
                                        imgBackArrow.setEnabled(true);
                                        loading.dismiss();
                                        finish();
                                       *//* Intent intentPaymentSuccess = new Intent(DwarkaDashBoardActivity.this, DashboardActivity.class);
                                        intentPaymentSuccess.putExtra("PAYMENTSUCCESS", 1);
                                        startActivity(intentPaymentSuccess);
                                        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);*//*

                                    });
                                });

                                loading.show();*/
                                //  }
                                /* else {
                                    SweetAlertDialogCart loading = new SweetAlertDialogCart(DwarkaDashBoardActivity.this, SweetAlertDialogCart.ERROR_TYPE);
                                    loading.setCancelable(true);
                                    loading.setConfirmText("OK");
                                    loading.setOnShowListener((DialogInterface.OnShowListener) dialog -> {
                                        SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
                                        MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                                        MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                                        MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                                        textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                        textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                        textContent.setTypeface(typeface);
                                        textTitle.setTypeface(typeface);
                                        btnConfirm.setTypeface(typeface);
                                        alertDialog.setCancelable(false);
                                        //textTitle.setText(response.body().getMessage());
                                        textTitle.setText("Transaction not processed");
                                        textContent.setText(paymentResult);
                                        btnConfirm.setText("OK");
                                        // textContent.setText("Pin you have entered is Invalid.");
                                        textContent.setGravity(Gravity.NO_GRAVITY);
                                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                imgBackArrow.setEnabled(true);
                                                loading.dismiss();
                                                finish();
                                            }
                                        });
                                    });
                                    loading.show();
                                }*/
                            }
                        }
                    } else {
                        SharedPrefTransID.edit().clear().apply();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<AddAddressDetail> call, @NotNull Throwable t) {
                mProgressDialog.setVisibility(View.GONE);
                Log.d("Response Address Error", "Response Address Error " + t);
            }
        });
    }

    ArrayList<DwarkaPlanModel.Datum> planList = new ArrayList<>();
    ArrayList<String> stringArrayList = new ArrayList<>();

    private void getPlanList() {
        if (planList.size() == 0) {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                mProgressDialog.setVisibility(View.VISIBLE);
                Call<DwarkaPlanModel> wsCallingEvents = mAPIInterface.getDwarkaPlan();
                wsCallingEvents.enqueue(new Callback<DwarkaPlanModel>() {
                    @Override
                    public void onResponse(Call<DwarkaPlanModel> call, Response<DwarkaPlanModel> response) {

                        if (mProgressDialog != null) {
                            mProgressDialog.setVisibility(View.GONE);
                        }
                        stringArrayList.clear();
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                planList.addAll(response.body().getData());
                                if (planList.size() > 0) {
                                    stringArrayList.add("Choose Plan");
                                    for (int i = 0; i < planList.size(); i++) {
                                        stringArrayList.add(planList.get(i).getPlanName());
                                    }
                                    showPlanDialog();
                                } else
                                    showToast(getResources().getString(R.string.error_normal));
                            } else
                                showToast(getResources().getString(R.string.error_normal));
                        } else
                            showToast(getResources().getString(R.string.error_normal));

                    }

                    @Override
                    public void onFailure(Call<DwarkaPlanModel> call, Throwable t) {

                        if (mProgressDialog != null) {
                            mProgressDialog.setVisibility(View.GONE);
                        }
                        showToast(getResources().getString(R.string.error_normal));
                    }
                });
            } else {
                showToast(getResources().getString(R.string.error_content));
            }
        } else {
            showPlanDialog();
        }
    }

    int planID = 0;
    MyTextViewEcom emiAmount, packageDiscount, packageToken;
    MyButtonEcom btnSubmit;
    MyBoldTextViewEcom payableAmount;
    LinearLayout layDiscount;
    MaterialProgressBar mprogressdialog;
    int minAmount;

    private void showPlanDialog() {
        minAmount = dataPackage.getMinTokenAmount();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DwarkaDashBoardActivity.this);
        LayoutInflater inflater = LayoutInflater.from(DwarkaDashBoardActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_dwarka_details, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        AlertDialog alertDialog = dialogBuilder.create();

        MyBoldTextViewEcom tvPackageName = dialogView.findViewById(R.id.package_name);
        MyTextViewEcom tvPackageAmount = dialogView.findViewById(R.id.amount);

        packageToken = dialogView.findViewById(R.id.token);
        AppCompatSpinner spinnerPlan = dialogView.findViewById(R.id.plan_details);

        emiAmount = dialogView.findViewById(R.id.emi_amount);
        mprogressdialog = dialogView.findViewById(R.id.progresbar_dialog);
        packageDiscount = dialogView.findViewById(R.id.discount_amount);
        payableAmount = dialogView.findViewById(R.id.payable_amount);
        btnSubmit = dialogView.findViewById(R.id.btn_submit_detail);
        layDiscount = dialogView.findViewById(R.id.lay_discount);
        ImageView imageView = dialogView.findViewById(R.id.img_dialog_close);
        mprogressdialog.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringArrayList);
        spinnerPlan.setAdapter(listAdapter);
        spinnerPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("selected", "" + position);
                if (position == 0) {
                    planID = -1;
                    setdefaultamount();
                } else {
                    planID = planList.get(position - 1).getId();
                    callEmiAmountAPI();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvPackageName.setText(dataPackage.getProductName() + "");
        tvPackageAmount.setText("₹" + dataPackage.getProductPrice());


        imageView.setOnClickListener(v -> alertDialog.dismiss());
       /* packageToken.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    try {
                        int i = Integer.parseInt(s.toString());
                        if (minAmount <= i) {
                            if (i > dataPackage.getProductPrice()) {
                                packageToken.setError("Maximum amount is " + dataPackage.getProductPrice());
                                setdefaultamount();
                            } else {
                                packageToken.setError(null);
                                amountReady = true;
                                callEmiAmountAPI();
                            }
                        } else {
                            packageToken.setError("Minimum amount is " + minAmount);
                            setdefaultamount();
                        }
                    } catch (NumberFormatException ex) { // handle your exception
                        setdefaultamount();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });*/
        setdefaultamount();
        packageToken.setText("₹" + minAmount);
        btnSubmit.setOnClickListener(v -> {
            /*InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(packageToken.getWindowToken(), 0);*/
            alertDialog.dismiss();
            productPrice = minAmount;
            openBottomsheet();
        });
        alertDialog.show();
        // packageToken.requestFocus();
       /* InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);*/
    }

    private void setdefaultamount() {
        payableAmount.setText("-");
        emiAmount.setText("-");
        packageDiscount.setText("-");
        btnSubmit.setEnabled(false);
        layDiscount.setVisibility(View.GONE);

    }

    private void callEmiAmountAPI() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mprogressdialog.setVisibility(View.VISIBLE);
            Call<PackageEMIModelDwarka> wsCallingEvents = mAPIInterface.getDwarkaEMIPackage(
                    String.valueOf(dataPackage.getProductID()),
                    String.valueOf(planID),
                    String.valueOf(minAmount));
            wsCallingEvents.enqueue(new Callback<PackageEMIModelDwarka>() {
                @Override
                public void onResponse(Call<PackageEMIModelDwarka> call, Response<PackageEMIModelDwarka> response) {

                    mprogressdialog.setVisibility(View.INVISIBLE);
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            PackageEMIModelDwarka mdata = response.body();
                            assert mdata != null;
                            if (mdata.getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                double disc = mdata.getData().getDiscountAmt();
                                emiAmount.setText("₹" + (mdata.getData().getSTWithInstallment()));
                                packageDiscount.setText("₹" + disc);
                                productPrice = (int) mdata.getData().getPayableAmount();
                                payableAmount.setText("₹" + productPrice);
                                btnSubmit.setEnabled(true);
                                layDiscount.setVisibility(disc > 0 ? View.VISIBLE : View.GONE);
                            } else {
                                showToast(getResources().getString(R.string.error_normal));
                            }
                        } else
                            showToast(getResources().getString(R.string.error_normal));
                    } else
                        showToast(getResources().getString(R.string.error_normal));
                }

                @Override
                public void onFailure(Call<PackageEMIModelDwarka> call, Throwable t) {
                    mprogressdialog.setVisibility(View.INVISIBLE);
                    showToast(getResources().getString(R.string.error_normal));
                }
            });
        } else {
            showToast(getResources().getString(R.string.error_content));
        }
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void getPackageList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);
            Call<PackageListModelDwarka> wsCallingEvents = mAPIInterface.getDwarkaPackage();
            wsCallingEvents.enqueue(new Callback<PackageListModelDwarka>() {
                @Override
                public void onResponse(@NotNull Call<PackageListModelDwarka> call, @NotNull Response<PackageListModelDwarka> response) {
                    Log.d("CartResponse", "CartResponse11: " + response.body());

                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility(View.GONE);
                    }
                    packageData.clear();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            llEmpty.setVisibility(View.GONE);
                            Log.d("CartAPI", "CartAPI: dwarka" + response);
                            Log.d("CartResponse", "CartResponse200: " + response.body());

                            assert response.body() != null;
                           /* if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                //noRecordsFound();
                            } else*/
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                packageData.addAll(response.body().getData());
                                Log.d("CartAPIIn", "CartAPIIn: " + response.body().getData().size());
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DwarkaDashBoardActivity.this);
                                rvDwarkaPackage.setLayoutManager(mLayoutManager);
                                rvDwarkaPackage.setItemAnimator(new DefaultItemAnimator());
                                dwarkaPackageAdapter = new DwarkaPackageAdapter(DwarkaDashBoardActivity.this, packageData);
                                rvDwarkaPackage.setAdapter(dwarkaPackageAdapter);
                                lnDwarka.setVisibility(View.VISIBLE);

                                Log.e("CartListAPI", "CartListAPI: " + new Gson().toJson(response.body()));

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                                lnDwarka.setVisibility(View.GONE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<PackageListModelDwarka> call, @NotNull Throwable t) {
                    mProgressDialog.setVisibility(View.GONE);
                    Log.d("CartAPI", "CartAPI: dwarka" + t);
                    Log.d("CartResponse", "CartResponseFil: " + t);
                    lnDwarka.setVisibility(View.GONE);
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    PackageData dataPackage;
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String datastr = intent.getExtras().getString("data");
            dataPackage = new Gson().fromJson(datastr, new TypeToken<PackageData>() {
            }.getType());
            productPrice = dataPackage.getProductPrice();//intent.getIntExtra("price", 0);
            productID = dataPackage.getProductID();// intent.getIntExtra("id", 0);
            position = intent.getIntExtra("position", 0);
        }
    };


    @SuppressLint("SetTextI18n")
    private void paymentCancelled() {
        setButtons(true);
        SweetAlertDialogCart loading = new SweetAlertDialogCart(DwarkaDashBoardActivity.this, SweetAlertDialogCart.ERROR_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);
            textTitle.setText("Payment Cancelled.");
            textContent.setText("If the amount was debited, kindly wait for 8 hours until we verify and update your payment.");
            btnConfirm.setText("OK");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> loading.dismiss());
        });
        loading.show();
    }


    @SuppressLint("SetTextI18n")
    private void paymentCancelledInternetIssue() {

        SweetAlertDialogCart loading = new SweetAlertDialogCart(DwarkaDashBoardActivity.this, SweetAlertDialogCart.ERROR_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            MyTextView textTitle = alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);
            textTitle.setText("There was a network error.");
            textContent.setText("If the amount was debited, kindly wait for 8 hours until we verify and update your payment. \n There was a network error.");
            btnConfirm.setText("OK");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> loading.dismiss());
        });
        loading.show();
    }

    DwarkaUpiModel.UPIList selmodel = null;

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        //private String[] mData;
        private final LayoutInflater mInflater;
        int pos = -1;
        Context context;

        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
            this.context = context;
            selmodel = new DwarkaUpiModel().new UPIList();
        }

        // inflates the cell layout from xml when needed
        @Override
        @NonNull
        public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.recyclerview_item_upi, parent, false);
            return new MyRecyclerViewAdapter.ViewHolder(view);
        }

        // binds the data to the TextView in each cell
        @Override
        public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
            DwarkaUpiModel.UPIList model = upiListArrayList.get(position);
            holder.myTextView.setText(model.getPackageType());
            Glide.with(context)
                    .load(model.getImageFile())
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            // log exception
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.myImageview);
            holder.cardView.setOnClickListener(v -> {
                selmodel = model;
                if (pos != -1) {
                    View view = rvupiApps.getChildAt(pos);
                    LinearLayout card = view.findViewById(R.id.laydata);
                    card.setBackgroundColor(Color.WHITE);
                }
                holder.layout.setBackgroundColor(context.getResources().getColor(R.color.pdlg_color_yellow_light));
                pos = position;
            });
        }

        // total number of cells
        @Override
        public int getItemCount() {
            return upiListArrayList.size();
        }

        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView myTextView;
            ImageView myImageview;
            CardView cardView;
            LinearLayout layout;

            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.appname);
                myImageview = itemView.findViewById(R.id.imgapp);
                cardView = itemView.findViewById(R.id.upi_cardview);
                layout = itemView.findViewById(R.id.laydata);
            }
        }
    }

    private void callUPI(DwarkaUpiModel.UPIList model) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uriapp);
        intent.setPackage(model.getPackageName());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, GOOGLE_REQUEST_CODE);
            dialogApp.dismiss();
        } else {
            Toast.makeText(this, "This payment mode is not available on your device.", Toast.LENGTH_LONG).show();
        }
    }

    private void noInternetAvailable() {
        lnDwarka.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
    }
}
