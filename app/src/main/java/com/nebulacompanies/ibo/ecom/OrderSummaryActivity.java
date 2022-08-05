package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.OrderSummaryAdapter;
import com.nebulacompanies.ibo.ecom.model.AddAddressDetail;
import com.nebulacompanies.ibo.ecom.model.CardData;
import com.nebulacompanies.ibo.ecom.model.CardDetailsModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListModelEcom;
import com.nebulacompanies.ibo.ecom.model.CartListTotalCountModelEcom;
import com.nebulacompanies.ibo.ecom.model.MarkCartDeleteModel;
import com.nebulacompanies.ibo.ecom.model.MyCart;
import com.nebulacompanies.ibo.ecom.model.MyCartData;
import com.nebulacompanies.ibo.ecom.model.MyTotalCountCartData;
import com.nebulacompanies.ibo.ecom.model.WalletFreezeModel;
import com.nebulacompanies.ibo.ecom.utils.ActionBottomDialogFragment;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.AppUtilsModel;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialogCart;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.payu.base.models.CustomNote;
import com.payu.base.models.ErrorResponse;
import com.payu.base.models.PayUBillingCycle;
import com.payu.base.models.PayUPaymentParams;
import com.payu.base.models.PayUSIParams;
import com.payu.base.models.PaymentType;
import com.payu.base.models.PayuBillingLimit;
import com.payu.base.models.PayuBillingRule;
import com.payu.checkoutpro.PayUCheckoutPro;
import com.payu.checkoutpro.models.PayUCheckoutProConfig;
import com.payu.checkoutpro.utils.PayUCheckoutProConstants;
import com.payu.custombrowser.Bank;
import com.payu.custombrowser.PayUWebChromeClient;
import com.payu.ui.model.listeners.PayUCheckoutProListener;
import com.payu.ui.model.listeners.PayUHashGenerationListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.ecom.utils.Utils.actionFail;
import static com.nebulacompanies.ibo.ecom.utils.Utils.actionUservalid;
//import static com.nebulacompanies.ibo.ecom.utils.Utils.showWallet;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class OrderSummaryActivity extends AppCompatActivity implements ActionBottomDialogFragment.ItemClickListener, View.OnClickListener {

    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface;
    Session session;
    Typeface typeface;

    Toolbar toolbar;
    RelativeLayout rlPaymentUPI, rlPaymentCard, rlPaymentWallet, rlPaymentNetBanking, rlOrderSummary;
    NestedScrollView nestedScrollView;
    ImageView imgBackArrow, imgSearch;
    MyTextViewEcom tvaddressOne, tvorderwallet, tvwalletsettle, tvaddressTwo, tvaddressThree, tvaddressFour, tvaddressFive, tvOrderGrandTotalValue, tvOrderSubtotalValue, tvOrderShipping, tvTotalOrder;
    MyTextViewEcom tvToolbarTitle, tvBillingAddressOne, tvBillingAddressTwo, tvBillingAddressThree, tvBillingAddressFour, tvBillingAddressFive;
    MyBoldTextViewEcom tvAmount, tvPaymentShippingTitle;
    MyButtonEcom btnConfirmOrder;
    RecyclerView rvSummary;
    OrderSummaryAdapter orderSummaryAdapter;

    ArrayList<MyCart> cartModels = new ArrayList<>();
    ArrayList<CardData> cardData = new ArrayList<>();
    ArrayList<MyTotalCountCartData> myTotalCountCartData = new ArrayList<>();
    UtilsVersion utilsVersion = new UtilsVersion();

    String billaddressOne, billaddressTwo, billaddressThree, billaddressFour, billaddressFive, addressOne, addressTwo, addressThree, addressFour, addressFive;
    String deviceId, uniqueID, addressNameSave, addressType, paymentitem;
    Integer GrandTotal, addressCityId, totalCartAmount, totalCartAmountorig;
    String amount, currency, name, description, orderId, email,firstname, mobileNo, address, key, theme, receiptId;
    int cityId;
    boolean isWaiveOff, isdeleted = true;

    SharedPreferences SharedPreferencesLocationName, citySave, SharedPreferencesCityID, SharedPreferencesAddressType, sharedPreferencesWaiveOff;
    Intent summaryOrderData, cartTotalPriceData;
    int GOOGLE_REQUEST_CODE = 111;
    SharedPreferences SharedPrefTransID, SharedPrefEwallet;
    CheckBox cbWallet;
    Utils utils = new Utils();
    LinearLayout laywalletdata;

    boolean callOrder = false;

    int subtotal = 0;
    int subtotalcheckedwallet = 0;
    int subtotalnowallet = 0;
    int shippingcharge = 0;
    int shippingcheckedwallet = 0;
    int shippingNowallet = 0;
    int grandtotalNowallet = 0;
    int grandtotalcheckedwallet = 0;
    MyCartData myCartData;
    boolean switchFreeze = false;
    double walletamount = 0;
    LinearLayout laywallet;
    double finalamount = 0;
    boolean isFreeze = false;
    IntentFilter filter;
    ProgressDialog mLoadProgressDialog;
    boolean paymentPaytm=true;


    // For PayU Money
    String txnid = "";
    String productInfo = "";
    String firstName = "";

    private String SUCCESS_URL = "https://payuresponse.firebaseapp.com/success";
    private String FAILED_URL = "https://payuresponse.firebaseapp.com/failure";
    private String phone = "";
    private String serviceProvider = "payu_paisa";
    private String hash = "";

    Handler mHandler = new Handler();


//    // These will hold all the payment parameters
//    private PaymentParams mPaymentParams;
//    private String paymentHash1;
//
//    // This sets the configuration
//    private PayuConfig payuConfig;
//
//
//    private String subventionHash;
//
//    // Used when generating hash from SDK
//    private PayUChecksum checksum;
//    private String merchantKey, userCredentials;

    boolean bl_Payment  = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        initPref();
        initSuccessDialog();
        initFullDialog();
        initUI();
        getExtra();
        setClicks();
        setData();
        sendTransaction();
        sendEwalletTransaction();
      // navigateToBaseActivity();
      // preparePayUBizParams();
    }

    public void initFullDialog() {
        mLoadProgressDialog = new ProgressDialog(OrderSummaryActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
        mLoadProgressDialog.setCancelable(false);
        mLoadProgressDialog.setMessage("Loading...");
    }

    public void showFullDialog() {
        if (mLoadProgressDialog != null && !mLoadProgressDialog.isShowing())
            mLoadProgressDialog.show();
    }

    public void hideFullDialog() {
        if (mLoadProgressDialog != null && mLoadProgressDialog.isShowing())
            mLoadProgressDialog.dismiss();
    }

    @SuppressLint("HardwareIds")
    private void initPref() {

        deviceId = android.provider.Settings.Secure.getString(this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/ember.ttf");

        SharedPreferencesLocationName = getSharedPreferences(PrefUtils.prefLocation, 0);
        addressNameSave = SharedPreferencesLocationName.getString(PrefUtils.prefLocation, null);

        SharedPreferencesCityID = getSharedPreferences(PrefUtils.prefPrefcityid, 0);
        addressCityId = SharedPreferencesCityID.getInt(PrefUtils.prefPrefcityid, 0);

        SharedPreferencesAddressType = getSharedPreferences(PrefUtils.prefAddresstypevalue, 0);
        addressType = SharedPreferencesAddressType.getString(PrefUtils.prefAddresstypevalue, null);

        Log.e("addressType", "addressType " + addressType);

        sharedPreferencesWaiveOff = getSharedPreferences(PrefUtils.prefernceIsWaiveOffEcom, 0);
        isWaiveOff = sharedPreferencesWaiveOff.getBoolean(PrefUtils.prefernceIsWaiveOffEcom, false);

        utilsVersion.checkVersion(this);

        citySave = getSharedPreferences(PrefUtils.prefCityid, 0);
        cityId = citySave.getInt(PrefUtils.prefCityid, 0); //0 is the default value

        SharedPrefEwallet = getSharedPreferences(PrefUtils.prefEwalletTransaction, 0);

        cartTotalPriceData = getIntent();
        if (cartTotalPriceData != null) {
            totalCartAmountorig = cartTotalPriceData.getIntExtra("cartTotalPrice", 0);
            totalCartAmount = totalCartAmountorig;
        }

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);
        session = new Session(this);
        if (deviceId == null || deviceId.equals("")) {
            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();
                SharedPreferences preferences = this.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid, randomID);
                editor.apply();
                deviceId = randomID;
            } else {
                deviceId = uniqueID;
            }
        }

        mAPIInterface = APIClient.getClient(OrderSummaryActivity.this).create(APIInterface.class);
    }

    private void setData() {
        if (addressType.equals("PickUp")) {
            tvPaymentShippingTitle.setText("Pickup Point");
        } else {
            tvPaymentShippingTitle.setText("Shipping Address");
        }
    }

    private void setClicks() {

        imgBackArrow.setOnClickListener(view -> onBackPressed());

        btnConfirmOrder.setOnClickListener(v -> {
            btnConfirmOrder.setClickable(false);
            btnConfirmOrder.setEnabled(false);
            if (paymentitem != null || totalCartAmount == 0) {
                setSelection(false);
                if (Utils.isNetworkAvailable(getApplicationContext())) {
                  //  mProgressDialog.setVisibility(View.VISIBLE);
                    callOrder = true;
                    utils.getExpireUser(mAPIInterface, this, session);

                } else {
                    utils.dialogueNoInternet(this);
                    setSelection(true);
                    setButtonSelection(true);
                }
            } else {
                alertSelectpayment();
                setSelection(true);
                setButtonSelection(true);
            }
        });
    }

    private void callOrderconfirm() {
        Utils.showProgressDialoug(this);
        sendTransaction();
        String userpayment = TextUtils.isEmpty(paymentitem) ? "UPI" : paymentitem.equals("UPI") ? "UPI" : "";
        Call<CardDetailsModelEcom> wsCallingEvents = null;
        if (showEWallet) {
            if (totalCartAmount == 0)
                userpayment = "UPI";
            if(paymentPaytm)
                wsCallingEvents = mAPIInterface.getCardDetailsV2Paytm(session.getIboKeyId(), String.valueOf(totalCartAmount), "", "", addressType, String.valueOf(addressCityId), "", "", "Online Payment", "true",
                        userpayment, ewalletamount);
            else
            wsCallingEvents = mAPIInterface.getCardDetailsV2(session.getIboKeyId(), String.valueOf(totalCartAmount), "", "", addressType, String.valueOf(addressCityId), "", "", "Online Payment", "true",
                    userpayment, ewalletamount);
            getWallet(wsCallingEvents);
        } else {
            if (totalCartAmount == 0) {
                if (paymentPaytm)
                    wsCallingEvents = mAPIInterface.getCardDetailsV2Paytm(session.getIboKeyId(), String.valueOf(totalCartAmount), "", "", addressType, String.valueOf(addressCityId), "", "", "Online Payment", "true",
                            "UPI", "0");
                else
                    wsCallingEvents = mAPIInterface.getCardDetailsV2(session.getIboKeyId(), String.valueOf(totalCartAmount), "", "", addressType, String.valueOf(addressCityId), "", "", "Online Payment", "true",
                            "UPI", "0");
            }
            else {
                if (paymentPaytm)
                    wsCallingEvents = mAPIInterface.getCardDetailsPaytm(session.getIboKeyId(), String.valueOf(totalCartAmount), "", "", addressType, String.valueOf(addressCityId), "", "", "Online Payment", "true",
                            userpayment);
                else
                    wsCallingEvents = mAPIInterface.getCardDetails(session.getIboKeyId(), String.valueOf(totalCartAmount), "", "", addressType, String.valueOf(addressCityId), "", "", "Online Payment", "true",
                            userpayment);
            }
            Log.e("OderData",wsCallingEvents.toString());
            callConfirmOrder(wsCallingEvents);
        }
    }

    private void initUI() {
        //getting the toolbar
        toolbar = findViewById(R.id.toolbar_search);
        mProgressDialog = findViewById(R.id.progresbar);
        imgSearch = toolbar.findViewById(R.id.img_search);
        imgSearch.setVisibility(View.GONE);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("Order Summary");
        imgBackArrow = findViewById(R.id.img_back);
        rlOrderSummary = findViewById(R.id.rl_ordersummary);
        tvPaymentShippingTitle = findViewById(R.id.tv_payment_shipping_title);
        rlPaymentUPI = findViewById(R.id.rl_payment_upi);
        rlPaymentCard = findViewById(R.id.rl_payment_card);
        rlPaymentWallet = findViewById(R.id.rl_payment_wallet);
        rlPaymentNetBanking = findViewById(R.id.rl_payment_net_banking);
        nestedScrollView = findViewById(R.id.ns_order_summary);
        cbWallet = findViewById(R.id.cbWallet);
        laywallet = findViewById(R.id.lay_wallet);
        laywalletdata = findViewById(R.id.laywalletvalue);
        tvorderwallet = findViewById(R.id.tv_order_wallet_value);
        tvwalletsettle = findViewById(R.id.id_text_settle);

        cbWallet.setTypeface(ResourcesCompat.getFont(this, R.font.ember));
        rlPaymentUPI.setOnClickListener(this);
        rlPaymentCard.setOnClickListener(this);
        rlPaymentWallet.setOnClickListener(this);
        rlPaymentNetBanking.setOnClickListener(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        rvSummary = findViewById(R.id.rv_summary);
        rvSummary.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderSummaryActivity.this, LinearLayoutManager.VERTICAL, false);
        rvSummary.setLayoutManager(mLayoutManager);
        rvSummary.setItemAnimator(new DefaultItemAnimator());

        tvaddressOne = findViewById(R.id.tv_shipping_address_one);
        tvaddressTwo = findViewById(R.id.tv_shipping_address_two);
        tvaddressThree = findViewById(R.id.tv_shipping_address_three);
        tvaddressFour = findViewById(R.id.tv_shipping_address_four);
        tvaddressFive = findViewById(R.id.tv_shipping_address_mobile);

        tvBillingAddressOne = findViewById(R.id.tv_billing_address_one);
        tvBillingAddressTwo = findViewById(R.id.tv_billing_address_two);
        tvBillingAddressThree = findViewById(R.id.tv_billing_address_three);
        tvBillingAddressFour = findViewById(R.id.tv_billing_address_four);
        tvBillingAddressFive = findViewById(R.id.tv_billing_address_mobile);

        tvOrderGrandTotalValue = findViewById(R.id.tv_order_grand_total_value);
        tvOrderSubtotalValue = findViewById(R.id.tv_order_subtotal_value);
        tvOrderShipping = findViewById(R.id.tv_order_shiping_price);
        tvTotalOrder = findViewById(R.id.tv_order_id_value);
        tvAmount = findViewById(R.id.tv_amount);
        btnConfirmOrder = findViewById(R.id.btn_confirm_order);

        if (addressNameSave == null || addressNameSave.equals("")) {
            Log.d("Ordersummary", "Location Fill " + addressNameSave);
        } else {
            Log.d("Ordersummary", "Location Empty " + addressNameSave);
            tvaddressOne.setText(addressNameSave);
        }

        filter = new IntentFilter();
        filter.addAction(actionUservalid);
        filter.addAction(actionFail);
        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(actionUservalid)) {
                if (callOrder) {
                    callOrder = false;
                    callOrderconfirm();
                    //  btnProcessEvent();
                } /*else {
                    sendTransaction();
                    sendEwalletTransaction();
                }*/
            } else if (intent.getAction().equalsIgnoreCase(actionFail)) {
                setSelection(true);
                setbtnselection(true);
                Toast.makeText(OrderSummaryActivity.this, "There is an error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    };
    String midString;
    String txnAmountString;
    String orderIdString;
    String txnTokenString;
    Integer ActivityRequestCode = 2;
    String txtToken, mid, backUrl;


    private void btnProcessEvent() {
       // showFullDialog();
        txnAmountString = amount;// txnAmount.getText().toString();
        midString = mid;// mid.getText().toString();

        orderIdString = orderId;//orderId.getText().toString();
        txnTokenString = txtToken;// txnToken.getText().toString();


        String orderDetails = "MID: " + midString + ", OrderId: " + orderIdString + ", TxnToken: " + txnTokenString + ", Amount: " + txnAmountString;


        String callBackUrl = backUrl + "/theia/paytmCallback?ORDER_ID=" + orderIdString;
        Log.d("data==", orderDetails + " : callBackUrl+ " + callBackUrl);
        PaytmOrder paytmOrder = new PaytmOrder(orderIdString, midString, txnTokenString,
                txnAmountString, callBackUrl);

        TransactionManager transactionManager = new TransactionManager(paytmOrder,
                new PaytmPaymentTransactionCallback() {

                    @Override
                    public void onTransactionResponse(Bundle bundlem) {
                        Log.d("paytm==", bundlem.toString());
                        hideFullDialog();
                        setSelection(true);
                        JSONObject json = new JSONObject();

                        try {
                            json.put("body", (bundlem.get("body")));
                            JSONObject jsondata = new JSONObject(String.valueOf(bundlem.get("body")));

                            JSONObject jsondetails = (JSONObject) jsondata.get("txnInfo");

                            String status = jsondetails.getString("STATUS");
                            Log.d("status==", " : " + status);
                            Log.d("jsondetails", jsondetails + " : ");
                            if (status.equalsIgnoreCase("TXN_SUCCESS")) {

                                callpayUwebhook(jsondetails);

                            } else if (status.equalsIgnoreCase("TXN_FAILURE")) {
                                callEwalletClear();
                                paymentFail();
                            } else {
                                paymentCancelled();
                            }
                        } catch (JSONException e) {
                            try {
                                if (bundlem.getString("STATUS").equalsIgnoreCase("TXN_SUCCESS")) {
                                    JSONObject jsondetails = new JSONObject();
                                    Set<String> keys = bundlem.keySet();
                                    for (String key : keys) {
                                        try {
                                            jsondetails.put(key, JSONObject.wrap(bundlem.get(key)));
                                        } catch (JSONException e1) {
                                            //Handle exception here
                                        }
                                    }
                                    Log.d("jsondetails", jsondetails + " : ");

                                    setSuccessPayment();
                                    callpayUwebhook(jsondetails);

                                } else if (bundlem.getString("STATUS").equalsIgnoreCase("TXN_FAILURE")) {
                                    callEwalletClear();
                                    paymentFail();
                                } else {
                                    paymentCancelled();
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void networkNotAvailable() {
                        hideFullDialog();
                        setSelection(true);
                        paymentCancelledInternetIssue();
                    }

                    @Override
                    public void onErrorProceed(String s) {
                        hideFullDialog();
                        setSelection(true);
                        paymentCancelled();
                    }

                    @Override
                    public void clientAuthenticationFailed(String s) {
                        hideFullDialog();
                        setSelection(true);
                        paymentCancelled();
                    }

                    @Override
                    public void someUIErrorOccurred(String s) {
                        hideFullDialog();
                        setSelection(true);
                        paymentCancelled();
                    }

                    @Override
                    public void onErrorLoadingWebPage(int i, String s, String s1) {
                        hideFullDialog();
                        setSelection(true);
                        paymentCancelled();
                    }

                    @Override
                    public void onBackPressedCancelTransaction() {
                        hideFullDialog();
                        setSelection(true);
                        paymentCancelled();
                    }

                    @Override
                    public void onTransactionCancel(String s, Bundle bundle) {
                        hideFullDialog();
                        setSelection(true);
                        paymentCancelled();
                    }
                });

        transactionManager.setShowPaymentUrl(backUrl + "/theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(OrderSummaryActivity.this, ActivityRequestCode);
    }

    private void callpayUwebhook(JSONObject jsonObj) {
        Log.d("callpaytmwebhook", jsonObj + " : ");

       // setSuccessPayment();
        showFullDialog();

        Log.d("callpaytmwebhook", jsonObj + " : ");
        try {
            String mihpayid = jsonObj.getString("mihpayid");
            String mode = jsonObj.getString("mode");
            String status = jsonObj.getString("status");
            String unmappedstatus = jsonObj.getString("unmappedstatus");
            String key = jsonObj.getString("key");
            String txnid = jsonObj.getString("txnid");

            Call<AddAddressDetail> wsCallingEvents = mAPIInterface.successPayUUPI(mihpayid,
                    mode,status,unmappedstatus,key,txnid);
            wsCallingEvents.enqueue(new Callback<AddAddressDetail>() {
                @Override
                public void onResponse(Call<AddAddressDetail> call, Response<AddAddressDetail> response) {
                    if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                        setSuccessPayment();
                    } else {
                        paymentFail();
                    }
                    hideFullDialog();
                }

                @Override
                public void onFailure(Call<AddAddressDetail> call, Throwable t) {
                    somethingError();
                    hideFullDialog();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            somethingError();
            hideFullDialog();
        }
    }


    // Launch Paytm payment flow
    /*private void launchPayTMCheckout(int totalPrice) {
        PaytmPGService Service = PaytmPGService.getStagingService("");
       // User user = UserDetails.getUser();
        String OrderId = "order" + System.currentTimeMillis();
        // Create map of input parameters
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID", "pUjmsA29526237007102");
        paramMap.put("ORDER_ID", OrderId);
        paramMap.put("CUST_ID", "user.getUsername()");
        paramMap.put("EMAIL", "user.getUsername()");
        paramMap.put("TXN_AMOUNT", totalPrice + ".00");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("WEBSITE", "WEBSTAGING");
        // This is the staging value. Production value is available in your dashboard
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        // This is the staging value. Production value is available in your dashboard
        paramMap.put("CALLBACK_URL", "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + OrderId);

        //APIInterface apiInterface = APIClient.getApiInterface();
        // for better security we have to create hash from each of the input parameter.
        // while transferring data this avoid data manipulation
        Call<ChecksumResponse> call = mAPIInterface.getCheckSum(paramMap);
        call.enqueue(new Callback<ChecksumResponse>() {
            @Override
            public void onResponse(Call<ChecksumResponse> call, Response<ChecksumResponse> response) {
                paramMap.put("CHECKSUMHASH", response.body().getChecksum());
                try {

                    PaytmOrder Order = new PaytmOrder(paramMap);

                    Service.initialize(Order, null);

                    Service.startPaymentTransaction(OrderSummaryActivity.this, true, true, new PaytmPaymentTransactionCallback() {
                        @Override
                        public void onTransactionResponse(Bundle inResponse) {
                            // Paytm payment response
                            Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                            // Reset the cart
                            clearCart();
                            cartCount = 0;
                            fragmentManager.beginTransaction().replace(R.id.main_content, productsFragment).commit();
                            textViewCartCount.setVisibility(View.GONE);
                        }

                        @Override
                        public void networkNotAvailable() {
                        }

                        @Override
                        public void clientAuthenticationFailed(String inErrorMessage) {
                        }

                        @Override
                        public void someUIErrorOccurred(String inErrorMessage) {
                        }

                        @Override
                        public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                        }

                        @Override
                        public void onBackPressedCancelTransaction() {
                        }

                        @Override
                        public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        }

                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ChecksumResponse> call, Throwable t) {
                Log.d("error is", t.getStackTrace().toString());
            }
        });
    }
*/
    private void getExtra() {
        summaryOrderData = getIntent();
        if (summaryOrderData != null) {
            addressOne = summaryOrderData.getStringExtra("shippingAddressOne");
            addressTwo = summaryOrderData.getStringExtra("shippingAddressTwo");
            addressThree = summaryOrderData.getStringExtra("shippingAddressThree");
            addressFour = summaryOrderData.getStringExtra("shippingAddressFour");
            addressFive = summaryOrderData.getStringExtra("shippingAddressFive");

            billaddressOne = summaryOrderData.getStringExtra("billaddressFullName");
            billaddressTwo = summaryOrderData.getStringExtra("billaddressLineOne");
            billaddressThree = summaryOrderData.getStringExtra("billaddressLineTwo");
            billaddressFour = summaryOrderData.getStringExtra("billaddressLineThree");
            billaddressFive = summaryOrderData.getStringExtra("billaddressLineFour");
        }
        Bundle b = getIntent().getExtras();
        if (b !=null){
            Log.e("Bundle",""+b);
            int int_Payment = b.getInt("Payment");
            if (int_Payment == 1){
                GrandTotal = Integer.valueOf(b.getString("Amount"));
             setSuccessPayment();
            }
            if (int_Payment == 0){

            }
        }
    }

    private void callAPI(boolean fromCheckout) {
        getMyCartList(fromCheckout);
        getMyCartListTotalCount(deviceId, session.getIboKeyId());
    }

    HashMap<String, String> productIDquantity = new HashMap<>();

    private void callConfirmOrder(Call<CardDetailsModelEcom> wsCallFinalCheckout) {
       // mProgressDialog.setVisibility(View.VISIBLE);

        Call<CartListModelEcom> wsCallingEvents = mAPIInterface.getMyCartListEcom(deviceId, session.getIboKeyId(), cityId);
        wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
            @Override
            public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("Ordersummary", "CartAPI: " + response);
                        CartListModelEcom cartListModelEcom = response.body();

                        if (cartListModelEcom.getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                            noRecordsFound();
                            //  failCheckout(false);
                        } else if (cartListModelEcom.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            MyCartData myCartDataNew = cartListModelEcom.getData();
                            int newSubtotal = myCartDataNew.getSubTotal();
                            int newNV = myCartDataNew.getTotalNV();
                            int newPV = myCartDataNew.getTotalPV();
                            int newBV = myCartDataNew.getTotalBV();
                            Log.d("Subtotal==", newSubtotal + " : " + subtotalnowallet);
                            Log.d("NV==", newNV + " : ");
                            Log.d("PV==", newPV + " : ");
                            Log.d("BV==", newBV + " : ");

                            HashMap<String, String> productIDquantityNew = new HashMap<>();
                            productIDquantityNew.putAll(addIdQntity(myCartDataNew));
                            try {
                                boolean equal = productIDquantity.equals(productIDquantityNew) && productIDquantityNew.equals(productIDquantity);
                                Log.d("COMPARE==", "equal array : " + equal);
                                // Toast.makeText(OrderSummaryActivity.this, "OBJECTS MATCHING : " + cartListModelEcomObj.equals(myCartDataNew), Toast.LENGTH_SHORT).show();
                                // Log.d("COMPARE==", "OBJECTS : " + cartListModelEcomObj.equals(myCartDataNew));
                                if (equal) {
                                    boolean tempwalletshow = myCartDataNew.getIsEwalletOnOff().equals("1");
                                    if (tempwalletshow == showEWallet) {
                                        callFinalCheckout(wsCallFinalCheckout);
                                        //  if()
                                    } else {
                                        showEWallet = tempwalletshow;
                                        setEwalletBal();
                                        failCheckout(true);
                                    }
                                } else
                                    failCheckout(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                                failCheckout(true);
                            }
                        } else {
                            assert response.body() != null;
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                failCheckout(false);
                            }
                        }
                    } else
                        failCheckout(false);
                } else
                    failCheckout(false);
            }

            @Override
            public void onFailure(Call<CartListModelEcom> call, Throwable t) {
                failCheckout(false);
            }
        });
    }

    private void setEwalletBal() {
        if (showEWallet) {
            cbWallet.setOnCheckedChangeListener((buttonView, isChecked) -> setPaymentData(isChecked));
        } else {
            GrandTotal = grandtotalNowallet;
            shippingcharge = shippingNowallet;
            subtotal = 0;

            totalCartAmount = GrandTotal;
            ewalletamount = String.valueOf(subtotal);
            tvOrderGrandTotalValue.setText("" + GrandTotal);
            tvorderwallet.setText("" + subtotal);
            tvOrderShipping.setText("" + shippingcharge);
            cbWallet.setChecked(false);
            laywalletdata.setVisibility(View.GONE);
        }
    }

    private void callFinalCheckout(Call<CardDetailsModelEcom> wsCallingEvents) {

        wsCallingEvents.enqueue(new Callback<CardDetailsModelEcom>() {
            @Override
            public void onResponse(Call<CardDetailsModelEcom> call, Response<CardDetailsModelEcom> response) {
                cardData.clear();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("CARDAPI1", "CARDAPI1: " + response);
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                            somethingError();
                            setSelection(true);
                        } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            Gson gson = new Gson();
                            String json = gson.toJson(response.body().getData());

                            Log.d("CARDAPI2", "CARDAPI2: " + json);
                            CardData carddata = response.body().getData();
                            key = carddata.getShippingKey();
                            amount = carddata.getShippingAmount();
                            currency = carddata.getShippingCurrency();
                            name = carddata.getShippingName();
                            orderId = carddata.getShippingOrderId();
                            receiptId = carddata.getReceiptId();
                            email = carddata.getShippingEmail();
                            mobileNo = carddata.getShippingMobileNo();
                            address = carddata.getShippingAddress();
                            description = carddata.getShippingDescription();
                            theme = carddata.getShippingTheme();


                            txnid = carddata.getShippingOrderId();
                            txtToken = carddata.getTxnToken();
                            mid = carddata.getMid();
                            backUrl = carddata.getPaytmGatewayURL();

                            firstName = carddata.getFirstname();
                            productInfo = carddata.getProductinfo();
                            hash = carddata.getHash();

                            txtToken = carddata.getTxnToken();
                            mid = carddata.getMid();
                            backUrl = carddata.getPaytmGatewayURL();



                            Log.e("Amount",amount);

                            if (showEWallet && amount.equals("0")) {
                                setSuccessPayment();
                                setUPITransactionID("pay_" + orderId,
                                        orderId,
                                        "pay_" + orderId,
                                        "success",
                                        "success",
                                        orderId);
                                //   markWalletDelete();
                            } else if (amount.equals("0")) {
                                setSuccessPayment();
                                setUPITransactionID("pay_" + orderId,
                                        orderId,
                                        "pay_" + orderId,
                                        "success",
                                        "success",
                                        orderId);
                            } else {
                                if (Integer.parseInt(ewalletamount) > 0) {
                                    SharedPrefEwallet.edit().putString("PREFwallet", ewalletamount).apply();
                                    SharedPrefEwallet.edit().putString("PREFOrderId", orderId).apply();
                                }

                                MarkCartDelete();
                                if(paymentPaytm){
                                  //  btnProcessEvent();

                                    initUiSdk(preparePayUBizParams());
                                }else{
                                 //   navigateToBaseActivity();

                                    initUiSdk(preparePayUBizParams());
                                }

//                                if(paymentPaytm)
//                                btnProcessEvent();
//                                else
//                                    startPayment(carddata.getRazorpay().equals("1"),
//                                            carddata.getUPIModelList());
                            }
                        } else {
                            setSelection(true);
                            somethingError();
                        }
                    } else {
                        setSelection(true);
                        somethingError();
                    }
                } else {
                    somethingError();
                    setSelection(true);
                }
            }

            @Override
            public void onFailure(Call<CardDetailsModelEcom> call, Throwable t) {
                Log.d("CARDAPI Error", "CARDAPI Error: " + t);
                Utils.hideProgressDialoug();
                setButtonSelection(true);
                somethingError();
                setSelection(true);
            }
        });
    }

    private void sendTransaction() {
        SharedPrefTransID = getSharedPreferences(PrefUtils.prefTransaction, 0);

        String transID = SharedPrefTransID.getString("PREFtransId", "0");
        String orderID = SharedPrefTransID.getString("PREFOrderId", "0");
        String approval = SharedPrefTransID.getString("PREFAproval", "0");
        String response = SharedPrefTransID.getString("PREFResponse", "0");
        String status = SharedPrefTransID.getString("PREFStatus", "0");
        String refId = SharedPrefTransID.getString("PREFtxnRef", "0");
        Log.d("transaction::", "sendTransaction " + transID + " : " + orderID + " : " + approval + " : " + response + " : " + status + " : " + refId);
        if (!transID.equals("0")) {
            // call API for submitting transaction
            callAPITransactionUPI(orderID, transID, approval, response, status, refId);
        }

    }

    private void sendEwalletTransaction() {


        String wallet = SharedPrefEwallet.getString("PREFwallet", "");
        String orderID = SharedPrefEwallet.getString("PREFOrderId", "");

        Log.d("transaction::", "send Ewallet Transaction " + wallet + " : " + orderID);
        if (TextUtils.isEmpty(wallet) && TextUtils.isEmpty(orderID))
            callAPI(false);
        else
            markWalletDelete(orderID, wallet, false);

    }

    private void callAPITransactionUPI(String OrderID, String TxnId, String ApprovalRef, String ResponseCode, String Status, String txnRef) {
        mAPIInterface = APIClient.getClient(OrderSummaryActivity.this).create(APIInterface.class);
        //Log.d("result::", "call transaction:: ");
        Log.d("transaction::", "callAPITransactionUPI: " + TxnId + " : " + OrderID + " : " + ApprovalRef + " : " + ResponseCode + " : " + Status + " : " + txnRef);

        Call<AddAddressDetail> wsCallingEvents = mAPIInterface.successUPI(OrderID, TxnId, ApprovalRef, ResponseCode, Status, txnRef, "GooglePay");
        wsCallingEvents.enqueue(new Callback<AddAddressDetail>() {
            @Override
            public void onResponse(Call<AddAddressDetail> call, Response<AddAddressDetail> response) {
                try {
                    if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                        SharedPrefTransID.edit().clear().apply();
                        // Toast.makeText(OrderSummaryActivity.this, "Clear Transaction", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddAddressDetail> call, Throwable t) {
                mProgressDialog.setVisibility(View.GONE);
                setbtnselection(true);
            }
        });
    }

    boolean showEWallet = false;
    CartListModelEcom cartListModelEcomObj;

    private void getMyCartList(boolean fromcheckout) {
        productIDquantity.clear();
        mProgressDialog.setVisibility(View.VISIBLE);
        Call<CartListModelEcom> wsCallingEvents = mAPIInterface.getMyCartListEcom(deviceId, session.getIboKeyId(), cityId);
        wsCallingEvents.enqueue(new Callback<CartListModelEcom>() {
            @Override
            public void onResponse(Call<CartListModelEcom> call, Response<CartListModelEcom> response) {
                if (mProgressDialog != null) {
                    mProgressDialog.setVisibility(View.GONE);
                }

                cartModels.clear();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("Ordersummary", "CartAPI: " + response);
                        cartListModelEcomObj = response.body();

                        if (cartListModelEcomObj.getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                            noRecordsFound();
                        } else if (cartListModelEcomObj.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            myCartData = cartListModelEcomObj.getData();
                            showEWallet = myCartData.getIsEwalletOnOff().equals("1");

                            setEwalletBal();

                            productIDquantity.clear();
                            productIDquantity.putAll(addIdQntity(myCartData));
                            nestedScrollView.setVisibility(View.VISIBLE);
                            btnConfirmOrder.setVisibility(View.VISIBLE);
                            cartModels.addAll(myCartData.getCart());
                            Log.d("Ordersummary", "CartAPIIn: " + myCartData.getCart().size());
                            final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderSummaryActivity.this);
                            rvSummary.setLayoutManager(mLayoutManager);
                            rvSummary.setItemAnimator(new DefaultItemAnimator());
                            orderSummaryAdapter = new OrderSummaryAdapter(OrderSummaryActivity.this, cartModels);

                            walletamount = myCartData.getEwalletAmount();

                            subtotal = myCartData.getSubTotal();
                            subtotalnowallet = myCartData.getSubTotal();
                            subtotalcheckedwallet = myCartData.getSubtotalWithEwallet();

                            shippingcharge = myCartData.getShippingCharge();
                            shippingcheckedwallet = myCartData.getShippingWithEwallet();
                            shippingNowallet = myCartData.getShippingCharge();

                            grandtotalcheckedwallet = myCartData.getGrandTotalWithEwallet();
                            GrandTotal = myCartData.getGrandTotal();
                            grandtotalNowallet = myCartData.getGrandTotal();

                            tvOrderSubtotalValue.setText("" + subtotal);
                            try {
                                isFreeze = myCartData.getEwalletfreeze();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (showEWallet) {
                                laywalletdata.setVisibility(View.VISIBLE);
                                tvwalletsettle.setText("Settle E-wallet balance (" + walletamount + ")");
                                if (!fromcheckout) {
                                    switchFreeze = isFreeze;
                                    cbWallet.setChecked(false);
                                    setPaymentData(false);
                                    if (isFreeze && walletamount > 0)
                                        frezeamountDialog(true);
                                } else {
                                    if (isFreeze && walletamount > 0 && cbWallet.isChecked() && !switchFreeze)
                                        frezeamountDialog(true);
                                    if (isFreeze) {
                                        cbWallet.setChecked(false);
                                        setPaymentData(false);
                                    } else {
                                        setPaymentData(cbWallet.isChecked());
                                    }
                                    switchFreeze = isFreeze;
                                }
                                cbWallet.setEnabled(!isFreeze);
                            } else {
                                tvOrderGrandTotalValue.setText("" + GrandTotal);
                                totalCartAmount = GrandTotal;
                                tvOrderShipping.setText("" + shippingcharge);
                            }
                            rvSummary.setAdapter(orderSummaryAdapter);
                        } /*else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                        }*/
                        if (cartModels.size() > 0) {
                            rvSummary.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CartListModelEcom> call, Throwable t) {
                mProgressDialog.setVisibility(View.GONE);
                Log.d("CartAPI", "CartAPI: " + t);
            }
        });

    }

    private HashMap<String, String> addIdQntity(MyCartData data) {
        HashMap<String, String> meMap = new HashMap<>();
        List<MyCart> mycart = data.getCart();
        for (int i = 0; i < mycart.size(); i++) {
            String id = mycart.get(i).getProductId();
            int q = mycart.get(i).getCartQuantity();
            //String idq = id + "-" + q;
            int countNew;
            if (meMap.containsKey(id)) {
                String count = meMap.get(id);
                countNew = Integer.parseInt(count) + q;
                meMap.remove(id);
            } else {
                countNew = q;
            }
            meMap.put(id, String.valueOf(countNew));
        }
        return meMap;
    }

    void noRecordsFound() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(OrderSummaryActivity.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);

            textTitle.setText("Cart is Empty");
            textContent.setText("Looks like you have no items in your shopping cart.");
            btnConfirm.setText("OK");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> {
                loading.dismiss();
                finish();
            });
        });

        try {
            loading.show();

        } catch (WindowManager.BadTokenException e) {
            //use a log message
        }
    }

    private void frezeamountDialog(boolean freezeMessage) {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(OrderSummaryActivity.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);

            textTitle.setText("Message");
            textContent.setText(freezeMessage ? "E-wallet is currently unavailable." : "Now, you can use your e-wallet balance.");
            btnConfirm.setText("OK");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> loading.dismiss());
        });
        try {
            loading.show();
        } catch (WindowManager.BadTokenException e) {
            //use a log message
        }
    }

    String ewalletamount = "0";

    private void setPaymentData(boolean selWallet) {

        if (selWallet) {
            GrandTotal = grandtotalcheckedwallet;
            shippingcharge = shippingcheckedwallet;
            subtotal = subtotalcheckedwallet;
        } else {
            GrandTotal = grandtotalNowallet;
            shippingcharge = shippingNowallet;
            subtotal = 0;
        }
        totalCartAmount = GrandTotal;
        ewalletamount = String.valueOf(subtotal);
        tvOrderGrandTotalValue.setText("" + GrandTotal);
        tvorderwallet.setText("" + subtotal);
        tvOrderShipping.setText("" + shippingcharge);
        Log.d("setPaymentData", selWallet + "" + shippingcharge + "" + GrandTotal);
    }

    private void getWallet(Call<CardDetailsModelEcom> wsCallingEventsCheckout) {
        finalamount = 0;
        mProgressDialog.setVisibility(View.VISIBLE);
        Call<WalletFreezeModel> wsCallingEvents = mAPIInterface.getBalWalletAmount(session.getIboKeyId());
        wsCallingEvents.enqueue(new Callback<WalletFreezeModel>() {
            @Override
            public void onResponse(Call<WalletFreezeModel> call, Response<WalletFreezeModel> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        WalletFreezeModel data = response.body();
                        if (data.getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                            finalamount = 0;
                            if (finalamount == walletamount)
                                callConfirmOrder(wsCallingEventsCheckout);
                            else
                                failCheckout(true);
                        } else if (data.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            // sd
                            finalamount = data.getData().getEwalletBalance();
                            boolean tempFreeze = data.getData().getIsEwalletfreeze();
                            if (finalamount == walletamount && isFreeze == tempFreeze) {
                                callConfirmOrder(wsCallingEventsCheckout);
                            } else if (!isFreeze && tempFreeze) {
                                failCheckout(true);
                            } else
                                failCheckout(true);
                        }
                    } else
                        failCheckout(false);
                } else
                    failCheckout(false);
            }

            @Override
            public void onFailure(Call<WalletFreezeModel> call, Throwable t) {
                failCheckout(false);
                setButtonSelection(true);
            }
        });
    }

    private void failCheckout(boolean mismatch) {
        if (showEWallet)
            Toast.makeText(this, mismatch ? "E-wallet balance has been updated. Kindly place your order again." : "Something went wrong, Please try again.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, mismatch ? "Cart has been updated. Kindly place your order again." : "Something went wrong, Please try again.", Toast.LENGTH_LONG).show();

        callAPI(true);
        setSelection(true);
    }

    public void somethingError() {
        Toast.makeText(this, "Something went wrong, Please try again.", Toast.LENGTH_LONG).show();
    }

    public void showBottomSheet(View view) {
        ActionBottomDialogFragment addPhotoBottomDialogFragment =
                ActionBottomDialogFragment.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                ActionBottomDialogFragment.TAG);
    }

    @Override
    public void onItemClick(String item) {
    }

//    @Override
//    public void onPaymentSuccess(String s, PaymentData paymentData) {
//        try {
//            setSuccessPayment();
//        } catch (Exception e) {
//            Toast.makeText(this, "error in success: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            Log.d("ERROR==", ">>>> " + e.getMessage());
//        }
//    }
    SweetAlertDialogCart dialogSuccess;
private void initSuccessDialog(){
    dialogSuccess = new SweetAlertDialogCart(OrderSummaryActivity.this, SweetAlertDialogCart.SUCCESS_TYPE);
    dialogSuccess.setCancelable(true);
    dialogSuccess.setConfirmText("OK");
    dialogSuccess.setOnShowListener(dialog -> {
        SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
        MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
        MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
        MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
        textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textContent.setTypeface(typeface);
        textTitle.setTypeface(typeface);
        btnConfirm.setTypeface(typeface);
        alertDialog.setCancelable(false);

        textTitle.setText("Success");
        textContent.setText("Paid ₹" + GrandTotal);
        btnConfirm.setText("OK");
        Log.d("GrandTotal Decimal ", "GrandTotal Decimal: " + GrandTotal);
        textContent.setGravity(Gravity.NO_GRAVITY);
        btnConfirm.setOnClickListener(v -> {
            // lnDwarka.setVisibility( View.VISIBLE );
            Intent placeOderIntent = new Intent(OrderSummaryActivity.this, MainActivity.class);
            placeOderIntent.putExtra("SELECTEDVALUE", 1);
            placeOderIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(placeOderIntent);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            dialogSuccess.dismiss();
            finish();
        });
    });
}
    private void setSuccessPayment() {
        Log.d("ERROR==", ">>>> setSuccessPayment call");
        clearEwalletPref();
        setbtnselection(true);
        MarkCartDelete();

        SharedPreferences sharedPrefs = getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.apply();

        new Handler().postDelayed(() -> dialogSuccess.show(), 500);
       // Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        setSelection(true);
    }

//    @Override
//    public void onPaymentError(int i, String  s, PaymentData paymentData) {
//        Log.d("Order Error", "Order Error: " + s + " Payment ID: " + paymentData.getPaymentId() +
//                " Signature: " + paymentData.getSignature() + " Name:" + paymentData.getUserContact() +
//                " Email:" + paymentData.getUserEmail() + " External Wallet:" + paymentData.getExternalWallet() +
//                "Order ID: " + paymentData.getOrderId() + " All Data:" + paymentData.getData());
//
//
//        if (i == 0) {
//            paymentCancelled();
//        } else if (i == 2) {
//            callEwalletClear();
//            paymentCancelledInternetIssue();
//        } else {
//            callEwalletClear();
//            SweetAlertDialogCart loading = new SweetAlertDialogCart(OrderSummaryActivity.this, SweetAlertDialogCart.ERROR_TYPE);
//            loading.setCancelable(true);
//            loading.setConfirmText("OK");
//            loading.setOnShowListener(dialog -> {
//                SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
//                MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
//                MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
//                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
//                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
//                textContent.setTypeface(typeface);
//                textTitle.setTypeface(typeface);
//                btnConfirm.setTypeface(typeface);
//                alertDialog.setCancelable(false);
//                textContent.setText("If the amount was debited, kindly wait for 8 hours until we verify and update your payment. \n Error: " + i + "\n" + s);
//                textTitle.setText("Error: " + i);
//                btnConfirm.setText("OK");
//                textContent.setGravity(Gravity.NO_GRAVITY);
//                btnConfirm.setOnClickListener(v -> loading.dismiss());
//            });
//            new Handler().postDelayed(() -> loading.show(), 500);
//        }
//    }

    private void callEwalletClear() {
        setbtnselection(true);
        String wallet = SharedPrefEwallet.getString("PREFwallet", "");
        String orderID = SharedPrefEwallet.getString("PREFOrderId", "");
        markWalletDelete(orderID, wallet, true);
    }

    private void markWalletDelete(String orderID, String ewalletAmount, boolean fromCheckout) {
        if (!TextUtils.isEmpty(orderID) && Integer.parseInt(ewalletAmount) > 0) {
            setbtnselection(true);
            cbWallet.setEnabled(false);
            Call<MarkCartDeleteModel> wsCallingEvents = mAPIInterface.deleteEwallet(orderID, session.getIboKeyId(), ewalletAmount);
            wsCallingEvents.enqueue(new Callback<MarkCartDeleteModel>() {
                @Override
                public void onResponse(Call<MarkCartDeleteModel> call, Response<MarkCartDeleteModel> response) {
                    if (!fromCheckout) {
                        clearEwalletPref();
                    }
                    callAPI(fromCheckout);
                    setbtnselection(true);
                    cbWallet.setEnabled(!isFreeze);
                }

                @Override
                public void onFailure(Call<MarkCartDeleteModel> call, Throwable t) {
                    setbtnselection(true);
                    cbWallet.setEnabled(!isFreeze);
                    if (!fromCheckout)
                        callAPI(false);
                }
            });
        } else {
            if (!fromCheckout)
                callAPI(false);
        }
    }
    public void startPayment(Boolean razorpay, List<CardData.UPIModelList> upiModelList) {
        setbtnselection(true);
        String paymentMethod = "";

        if (paymentitem.equals("UPI")) {
            paymentMethod = "upi";
            ///paymentMethod = "card";
        } else if (paymentitem.equals("Credit / Debit Card")) {
            paymentMethod = "card";
        } else if (paymentitem.equals("Wallet")) {
            paymentMethod = "wallet";
        } else if (paymentitem.equals("Net Banking")) {
            paymentMethod = "netbanking";
        }

        if (!razorpay && paymentMethod.equals("upi")) {
            startUpiDialog(upiModelList);
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
                // options.put("receipt", "12345");
                options.put("receipt", receiptId);
                options.put("order_id", orderId);
                options.put("payment_capture", false);

                JSONObject preFill = new JSONObject();
                preFill.put("email", email);
                preFill.put("contact", mobileNo);

                preFill.put("method", paymentMethod);
                options.put("prefill", preFill);

                co.open(activity, options);
            } catch (Exception e) {
                Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                        .show();
                e.printStackTrace();
                clearEwalletPref();
            }
            setSelection(true);
        }
    }

    private void clearEwalletPref() {
        SharedPrefEwallet.edit().clear().apply();
    }

    RecyclerView rvupiApps;
    Uri uriapp;
    AlertDialog dialogApp;
    ImageView imgClose;
    MyButtonEcom btnApply;

    private void startUpiDialog(List<CardData.UPIModelList> upiModelList) {
        String mc = "BCR2DN4TVD6PFRLV";
        uriapp = new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", getString(R.string.vpa))
                .appendQueryParameter("pn", getString(R.string.payee))
                .appendQueryParameter("tr", orderId)
                .appendQueryParameter("tn", description)
                .appendQueryParameter("mc", "5411")
                .appendQueryParameter("am", String.valueOf(amount))
                .appendQueryParameter("cu", "INR")
                .build();

//        String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
//        int GOOGLE_PAY_REQUEST_CODE = 123;
//
//        Uri uri =
//                new Uri.Builder()
//                        .scheme("upi")
//                        .authority("pay")
//                        .appendQueryParameter("pa",  getString(R.string.vpa))
//                        .appendQueryParameter("pn", getString(R.string.payee))
//                        .appendQueryParameter("mc", "BCR2DN4TVD6PFRLV")
//                        .appendQueryParameter("tr", orderId)
//                        .appendQueryParameter("tn", description)
//                        .appendQueryParameter("am", String.valueOf(amount))
//                        .appendQueryParameter("url", "https://nebulacare.in")
//                        .appendQueryParameter("cu", "INR")
//                        .build();
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(uri);
//        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
//        startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);


        if (upiModelList.size() > 0) {
            // Create an alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // set the custom layout
            final View customLayout = getLayoutInflater().inflate(R.layout.dialog_upi_app, null);
            builder.setView(customLayout);
            dialogApp = builder.create();
            rvupiApps = customLayout.findViewById(R.id.rvApps);
            imgClose = customLayout.findViewById(R.id.img_sort_close);
            btnApply = customLayout.findViewById(R.id.btn_apply);
            imgClose.setOnClickListener(v -> {
                callEwalletClear();
                // markWalletDelete(orderId, ewalletamount, true);
                dialogApp.dismiss();
               /* imgBackArrow.setEnabled(true);
                btnConfirmOrder.setEnabled(true);*/
            });
            btnApply.setOnClickListener(v -> {
                if (selmodel != null && !TextUtils.isEmpty(selmodel.getPackageName()))
                    callUPI(selmodel);
                else {
                    alertSelectpayment();
                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvupiApps.setLayoutManager(mLayoutManager);
            MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, upiModelList);
            rvupiApps.setAdapter(adapter);
            dialogApp.setCancelable(false);
            dialogApp.show();
        } else {
            clearEwalletPref();
            Toast.makeText(this, "This payment mode is not available on your device.", Toast.LENGTH_LONG).show();
        }
        setSelection(true);
    }

    private void alertSelectpayment() {
        SweetAlertDialogCart loading = new SweetAlertDialogCart(OrderSummaryActivity.this, SweetAlertDialogCart.WARNING_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);
            textTitle.setText("Error");
            textContent.setText("Please select payment Mode");
            btnConfirm.setText("OK");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(v -> loading.dismiss());
        });

        loading.show();
    }


    CardData.UPIModelList selmodel = null;

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private LayoutInflater mInflater;
        List<CardData.UPIModelList> upiModelList;
        int pos = -1;
        Context context;

        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context, List<CardData.UPIModelList> upiModelList) {
            this.mInflater = LayoutInflater.from(context);
            this.upiModelList = upiModelList;
            this.context = context;
            selmodel = new CardData().new UPIModelList();
        }

        // inflates the cell layout from xml when needed
        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.recyclerview_item_upi, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each cell
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CardData.UPIModelList model = upiModelList.get(position);
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
                    LinearLayout card = (LinearLayout) view.findViewById(R.id.laydata);
                    card.setBackgroundColor(Color.WHITE);
                }
                holder.layout.setBackgroundColor(context.getResources().getColor(R.color.pdlg_color_yellow_light));
                pos = position;
            });
        }

        // total number of cells
        @Override
        public int getItemCount() {
            return upiModelList.size();
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

    private void callUPI(CardData.UPIModelList model) {
        //try {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uriapp);
        intent.setPackage(model.getPackageName());
       try{
            startActivityForResult(intent, GOOGLE_REQUEST_CODE);
            dialogApp.dismiss();
        } catch (Exception e){
           e.printStackTrace();
            Toast.makeText(this, "This payment mode is not available on your device.", Toast.LENGTH_LONG).show();
        }
    }

    public void getList(Intent prototype, String[] forbiddenChoices) throws PackageManager.NameNotFoundException {
        ArrayList<AppUtilsModel> appUtilsModels = new ArrayList<>();
        appUtilsModels.clear();

        Intent dummy = new Intent(prototype.getAction());
        dummy.setData(prototype.getData());
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(dummy, 0);

        if (!resInfo.isEmpty()) {
            for (ResolveInfo resolveInfo : resInfo) {
                if (resolveInfo.activityInfo != null && Arrays.asList(forbiddenChoices).contains(resolveInfo.activityInfo.packageName)) {
                    AppUtilsModel model = new AppUtilsModel();
                    model.setAppName(String.valueOf(resolveInfo.activityInfo.loadLabel(getPackageManager())));
                    model.setAppPackage(resolveInfo.activityInfo.packageName);
                    model.setAppIcon(getPackageManager().getApplicationIcon(resolveInfo.activityInfo.packageName));
                    appUtilsModels.add(model);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setbtnselection(true);
        //utils.checkExpireUser(mAPIInterface, this, session);
        utilsVersion.checkVersion(this);
        if (mProgressDialog != null) {
            mProgressDialog.setVisibility(View.GONE);
        }

    }

    private void paymentFail() {
        SweetAlertDialog loading = new SweetAlertDialog(OrderSummaryActivity.this, SweetAlertDialog.ERROR_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
            MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
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
        new Handler().postDelayed(() -> loading.show(), 500);
    }


    private void getMyCartListTotalCount(String deviceId, String userID) {

        Call<CartListTotalCountModelEcom> wsCallingEvents = mAPIInterface.getMyCartTotalCountListEcom(deviceId, userID);
        wsCallingEvents.enqueue(new Callback<CartListTotalCountModelEcom>() {
            @Override
            public void onResponse(Call<CartListTotalCountModelEcom> call, Response<CartListTotalCountModelEcom> response) {
                myTotalCountCartData.clear();
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        Log.d("Ordersummary", "CartAPI: " + response);
                        if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                            tvTotalOrder.setText("");
                        } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            Log.d("Ordersummary", "CartAPIIn: " + response.body().getData());
                            String count = String.valueOf(response.body().getData().getCartTotalCount());
                            tvTotalOrder.setText(count);
                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CartListTotalCountModelEcom> call, Throwable t) {
                Log.d("CartAPI", "CartAPI: " + t);
            }
        });
    }

    private void MarkCartDelete() {
        Log.d("ERROR==", ">>>> MarkCartDelete call");
        mProgressDialog.setVisibility(View.VISIBLE);
        Call<MarkCartDeleteModel> wsCallingEvents = mAPIInterface.deleteCart(isdeleted, session.getIboKeyId(), deviceId);
        wsCallingEvents.enqueue(new Callback<MarkCartDeleteModel>() {
            @Override
            public void onResponse(Call<MarkCartDeleteModel> call, Response<MarkCartDeleteModel> response) {
                mProgressDialog.setVisibility(View.GONE);
                /*if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        assert response.body() != null;
                        MarkCartDeleteModel mData = response.body();
                        int statuscode = mData.getStatusCode();
                        if (statuscode == REQUEST_STATUS_CODE_NO_RECORDS) {
                        } else if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                        }
                    } else if (response.code() == 401) {
                    }
                } else {
                }*/
            }

            @Override
            public void onFailure(Call<MarkCartDeleteModel> call, Throwable t) {
                setbtnselection(true);
            }
        });
    }


    private void paymentCancelled() {
        callEwalletClear();
        SweetAlertDialogCart loading = new SweetAlertDialogCart(OrderSummaryActivity.this, SweetAlertDialogCart.ERROR_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
            textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            textContent.setTypeface(typeface);
            textTitle.setTypeface(typeface);
            btnConfirm.setTypeface(typeface);
            alertDialog.setCancelable(false);
            textTitle.setText("Payment Cancelled.");
            textContent.setText("If the amount was debited, kindly wait for 8 hours until we verify and update your payment.");
            btnConfirm.setText("OK");
            // textContent.setText("Pin you have entered is Invalid.");
            textContent.setGravity(Gravity.NO_GRAVITY);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loading.dismiss();
                    mProgressDialog.setVisibility(View.GONE);
                }
            });
        });
        new Handler().postDelayed(() -> loading.show(), 500);
    }


    private void paymentCancelledInternetIssue() {

        SweetAlertDialogCart loading = new SweetAlertDialogCart(OrderSummaryActivity.this, SweetAlertDialogCart.ERROR_TYPE);
        loading.setCancelable(true);
        loading.setConfirmText("OK");
        loading.setOnShowListener(dialog -> {
            SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
            MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
            MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
            MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
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
        new Handler().postDelayed(() -> loading.show(), 500);
    }


    @Override
    public void onBackPressed() {
        if (mProgressDialog.isShown()) {
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_payment_upi:
                rlPaymentUPI.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_checked));
                rlPaymentCard.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_unchecked));
                rlPaymentWallet.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_unchecked));
                rlPaymentNetBanking.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_unchecked));
                paymentitem = "UPI";
                break;
            case R.id.rl_payment_card:
                rlPaymentUPI.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_unchecked));
                rlPaymentCard.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_checked));
                rlPaymentWallet.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_unchecked));
                rlPaymentNetBanking.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_unchecked));
                paymentitem = "Credit / Debit Card";
                break;
            case R.id.rl_payment_wallet:
                rlPaymentUPI.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_unchecked));
                rlPaymentCard.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_unchecked));
                rlPaymentWallet.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_checked));
                rlPaymentNetBanking.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_unchecked));
                paymentitem = "Wallet";
                break;
            case R.id.rl_payment_net_banking:
                rlPaymentUPI.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_unchecked));
                rlPaymentCard.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_unchecked));
                rlPaymentWallet.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_unchecked));
                rlPaymentNetBanking.setBackground(ContextCompat.getDrawable(OrderSummaryActivity.this, R.drawable.radiobutton_selector_checked));
                paymentitem = "Net Banking";
                break;
        }
    }

    private void setbtnselection(boolean b) {
        imgBackArrow.setEnabled(b);
//        btnConfirmOrder.setEnabled(b);
//        btnConfirmOrder.setClickable(b);
    }

    private void setSelection(boolean b) {
        rlPaymentUPI.setEnabled(b);
        rlPaymentCard.setEnabled(b);
        rlPaymentWallet.setEnabled(b);
        rlPaymentNetBanking.setEnabled(b);
        cbWallet.setEnabled(!isFreeze && b);
        setbtnselection(b);
        if (b)
            if (mProgressDialog != null) {
                mProgressDialog.setVisibility(View.GONE);
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("result::", requestCode + " : onActivityResult : " + resultCode);
//
//        if (requestCode == 1000) {
//            if (data != null) {
//                Log.e("Payresponse",""+data.getStringExtra("payu_response"));
//                Log.e("PayUData",""+data.getStringExtra("result"));
//                String datajson = data.getStringExtra("result");
//                JSONObject jsondetails = null;
//                try {
//                    jsondetails = new JSONObject(datajson);
//                    String status = jsondetails.getString("status");
//                    Log.e("PayUData",""+jsondetails + " "+status);
//                    if (status.equals("success")) {
//                        callpayUwebhook(jsondetails);
//                    } else if (status.equalsIgnoreCase("fail")) {
//                        callEwalletClear();
//                        paymentFail();
//                    } else {
//                        paymentCancelled();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            } else {
//                Toast.makeText(this, "could_not_receive_data", Toast.LENGTH_LONG).show();
//            }
//        }
//else {
//            if (requestCode == ActivityRequestCode && data != null) {
//
//                Bundle bundle = data.getExtras();
//                Log.e("TAG", " bundle " + bundle);
//
//                if (bundle != null) {
//                   /* for (String key : bundle.keySet()) {
//                        Log.e("TAG", key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
//                    }*/
//                    Log.e("TAG", " data " + data.getStringExtra("nativeSdkForMerchantMessage"));
//                    Log.e("TAG", " data response - " + data.getStringExtra("response"));
//                    String datajson = data.getStringExtra("response");
//                    try {
//                        JSONObject jsondetails = new JSONObject(datajson);
//                        String status = jsondetails.getString("STATUS");
//                        Log.d("status==", " : " + status);
//                        Log.d("jsondetails", jsondetails + " : ");
//                        if (status.equalsIgnoreCase("TXN_SUCCESS")) {
//
//                          //  callpaytmwebhook(jsondetails);
//
//                        } else if (status.equalsIgnoreCase("TXN_FAILURE")) {
//                            callEwalletClear();
//                            paymentFail();
//                        } else {
//                            paymentCancelled();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
///*
// data response - {"BANKNAME":"WALLET","BANKTXNID":"1395841115",
// "CHECKSUMHASH":"7jRCFIk6mrep+IhnmQrlrL43KSCSXrmM+VHP5pH0hekXaaxjt3MEgd1N9mLtWyu4VwpWexHOILCTAhybOo5EVDmAEV33rg2VAS/p0PXdk\u003d",
// "CURRENCY":"INR","GATEWAYNAME":"WALLET","MID":"EAc0553138556","ORDERID":"100620202152",
// "PAYMENTMODE":"PPI","RESPCODE":"01","RESPMSG":"Txn Success","STATUS":"TXN_SUCCESS",
// "TXNAMOUNT":"2.00","TXNDATE":"2020-06-10 16:57:45.0","TXNID":"20200610111212800110168328631290118"}
//  */
//                 /*   Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage")
//                            + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();*/
//                } else {
//                    Log.e("TAG", " payment failed");
//                    paymentCancelled();
//                }
//                hideFullDialog();
//
//                mProgressDialog.setVisibility(View.GONE);
//            } else {
//                if (data != null) {
//                    Log.d("result::", requestCode + " : onActivityResult data: " + resultCode + " : " + data);
//                    Log.d("result::", " : Bundle : " + data.getExtras());
//                    if (resultCode == Activity.RESULT_OK) {
//                        String Status = data.getStringExtra("Status");
//                        String paymentResponse = data.getStringExtra("response");
//                        Log.d("result::", Status + " : paymentResponse : " + paymentResponse);
//
//                        if (TextUtils.isEmpty(Status)) {
//                            assert paymentResponse != null;
//                            String[] arrayString = paymentResponse.split("&");
//                            JSONObject jsonObject = new JSONObject();
//                            for (String strData : arrayString) {
//                                String[] kayvalue = strData.split("=");
//                                try {
//                                    if (kayvalue.length > 1)
//                                        jsonObject.put(kayvalue[0], kayvalue[1]);
//                                    else
//                                        jsonObject.put(kayvalue[0], "");
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            try {
//                                Status = jsonObject.getString("Status");
//                                Log.d("result::", Status + " : Status : String ");
//                                doSuccess(jsonObject.getString("txnId"),
//                                        orderId,
//                                        jsonObject.getString("ApprovalRefNo"),
//                                        jsonObject.getString("responseCode"),
//                                        Status,
//                                        jsonObject.getString("txnRef"));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                       /*imgBackArrow.setEnabled(true);
//                        btnConfirmOrder.setEnabled(true);*/
//                                paymentCancelled();
//                            }
//                        } else {
//                            assert Status != null;
//                            doSuccess(data.getStringExtra("txnId"),
//                                    orderId,
//                                    data.getStringExtra("ApprovalRefNo"),
//                                    data.getStringExtra("responseCode"),
//                                    Status,
//                                    data.getStringExtra("txnRef"));
//                        }
//                    } else {
//               /* imgBackArrow.setEnabled(true);
//                  btnConfirmOrder.setEnabled(true);*/
//                        paymentCancelled();
//                    }
//                } else {
//                    Log.d("result::", "cancel dialog : " + resultCode);
//                    // Toast.makeText(this, "cancel dialog : ", Toast.LENGTH_LONG).show();
//          /*  imgBackArrow.setEnabled(true);
//              btnConfirmOrder.setEnabled(true);*/
//                    paymentCancelled();
//                }
//            }
//        }
    }

    private void doSuccess(String transID, String orderId, String aproval, String response, String Status, String txnRef) {
        if (Status.equalsIgnoreCase("success")) {
            setSuccessPayment();
            setUPITransactionID(transID,
                    orderId,
                    aproval,
                    response,
                    Status,
                    txnRef);
        } else if (Status.equalsIgnoreCase("failure")) {
            callEwalletClear();
            paymentFail();
        } else {
            //markWalletDelete(orderId, ewalletamount, true);
           /* imgBackArrow.setEnabled(true);
            btnConfirmOrder.setEnabled(true);*/
            paymentCancelled();
        }
    }

    private void setUPITransactionID(String transID, String orderId, String aproval, String response, String Status, String txnRef) {
        Log.d("transaction::", " setUPITransactionID : " + transID + " : " + orderId + " : " + aproval + " : " + response + " : " + Status + " : " + txnRef);
        SharedPrefTransID.edit().putString("PREFtransId", transID).apply();
        SharedPrefTransID.edit().putString("PREFOrderId", orderId).apply();
        SharedPrefTransID.edit().putString("PREFAproval", aproval).apply();
        SharedPrefTransID.edit().putString("PREFResponse", response).apply();
        SharedPrefTransID.edit().putString("PREFStatus", Status).apply();
        SharedPrefTransID.edit().putString("PREFtxnRef", txnRef).apply();
        sendTransaction();
    }

//    /**
//     * This method prepares all the payments params to be sent to PayuBaseActivity.java
//     */
//    @SuppressLint({"JavascriptInterface", "WrongConstant"})
//    public void navigateToBaseActivity() {
//
//
//        txnAmountString = amount;// txnAmount.getText().toString();
//        midString = mid;// mid.getText().toString();
//
//        orderIdString = orderId;//orderId.getText().toString();
//        txnTokenString = txtToken;// txnToken.getText().toString();
//
//        merchantKey="0w2qzK";
//
//        int environment;
//
//        environment = PayuConstants.PRODUCTION_ENV;
//        userCredentials = merchantKey + ":" + email;
//
//        //TODO Below are mandatory params for hash genetation
//        mPaymentParams = new PaymentParams();
//        /**
//         * For Test Environment, merchantKey = please contact mobile.integration@payu.in with your app name and registered email id
//
//         */
//        mPaymentParams.setKey(merchantKey);
//        mPaymentParams.setAmount(amount);
//        mPaymentParams.setProductInfo("product_info");
//        mPaymentParams.setFirstName("TEST");
//        mPaymentParams.setEmail("");
//        mPaymentParams.setPhone("");
//
//
////        mPaymentParams.setBeneficiaryAccountNumber("50100041412026");
//
//        mPaymentParams.setSubventionAmount(amount);
//        mPaymentParams.setSubventionEligibility("all");
//
//        /*
//         * Transaction Id should be kept unique for each transaction.
//         * */
//        mPaymentParams.setTxnId(orderIdString);
//        // mPaymentParams.setTxnId("1587113659761");
//
//        /**
//         * Surl --> Success url is where the transaction response is posted by PayU on successful transaction
//         * Furl --> Failre url is where the transaction response is posted by PayU on failed transaction
//         */
//        // mPaymentParams.setSurl(" https://www.fitternity.com/paymentsuccessandroid");
//        mPaymentParams.setSurl("https://payuresponse.firebaseapp.com/success");
//        mPaymentParams.setFurl("https://payuresponse.firebaseapp.com/failure");
//        //  mPaymentParams.setFurl("https://www.fitternity.com/paymentsuccessandroid");
//        mPaymentParams.setNotifyURL(mPaymentParams.getSurl());  //for lazy pay
//
//        /*
//         * udf1 to udf5 are options params where you can pass additional information related to transaction.
//         * If you don't want to use it, then send them as empty string like, udf1=""
//         * */
//        mPaymentParams.setUdf1("udf1");
//        mPaymentParams.setUdf2("udf2");
//        mPaymentParams.setUdf3("udf3");
//        mPaymentParams.setUdf4("udf4");
//        mPaymentParams.setUdf5("udf5");
//
//        /**
//         * These are used for store card feature. If you are not using it then user_credentials = "default"
//         * user_credentials takes of the form like user_credentials = "merchant_key : user_id"
//         * here merchant_key = your merchant key,
//         * user_id = unique id related to user like, email, phone number, etc.
//         * */
//        mPaymentParams.setUserCredentials(userCredentials);
//
//        //TODO Pass this param only if using offer key
//        // mPaymentParams.setOfferKey("YONOYSF@6445");
//
//        //TODO Sets the payment environment in PayuConfig object
//        payuConfig = new PayuConfig();
//        payuConfig.setEnvironment(environment);
//        //TODO It is recommended to generate hash from server only. Keep your key and salt in server side hash generation code.
//        // generateHashFromServer(mPaymentParams);
//
//        /**
//         * Below approach for generating hash is not recommended. However, this approach can be used to test in PRODUCTION_ENV
//         * if your server side hash generation code is not completely setup. While going live this approach for hash generation
//         * should not be used.
//         * */
//        if(environment== PayuConstants.STAGING_ENV){
//            //  salt = " ";
//            salt = "Oa3o6OCxGvidPIIxnP2tlZ7Wq9z1VEpU";
//        }else {
//            //Production Env
//            salt = "Oa3o6OCxGvidPIIxnP2tlZ7Wq9z1VEpU";
//
//        }
//        generateHashFromSDK(mPaymentParams, salt);
//
//
////         for Latest payUmoney
//
////        Bundle b =  new Bundle();
////        b.putString("Firstname",firstName);
////        b.putString("description",productInfo);
////        b.putString("hash",hash);
////        b.putString("txnid",txnid);
////        b.putString("amount",amount);
////        b.putString("email",emailId);
////        b.putString("phone",phone);
////        b.putString("surl",SUCCESS_URL);
////        b.putString("furl",FAILED_URL);
////        Intent i_PayUMoney = new Intent(OrderSummaryActivity.this,PayUMoney.class);
////        i_PayUMoney.putExtras(b);
////        startActivity(i_PayUMoney);
//
//
////        webView = new WebView(this);
////        setContentView(webView);
////
////        JSONObject productInfoObj = new JSONObject();
////        JSONArray productPartsArr = new JSONArray();
////        JSONObject productPartsObj1 = new JSONObject();
////        JSONObject paymentIdenfierParent = new JSONObject();
////        JSONArray paymentIdentifiersArr = new JSONArray();
////        JSONObject paymentPartsObj1 = new JSONObject();
////        JSONObject paymentPartsObj2 = new JSONObject();
////        try {
////            // Payment Parts
////            productPartsObj1.put("name", firstName);
////            productPartsObj1.put("description", productInfo);
////            productPartsObj1.put("value", amount);
////            productPartsObj1.put("isRequired", "true");
////            productPartsObj1.put("settlementEvent", "EmailConfirmation");
////            productPartsArr.put(productPartsObj1);
////            productInfoObj.put("paymentParts", productPartsArr);
////
////            // Payment Identifiers
////            paymentPartsObj1.put("field", "CompletionDate");
////            paymentPartsObj1.put("value", "31/10/2012");
////            paymentIdentifiersArr.put(paymentPartsObj1);
////
////            paymentPartsObj2.put("field", "TxnId");
////            paymentPartsObj2.put("value", txnid);
////            paymentIdentifiersArr.put(paymentPartsObj2);
////
////            paymentIdenfierParent.put("paymentIdentifiers",
////                    paymentIdentifiersArr);
////            productInfoObj.put("", paymentIdenfierParent);
////        } catch (JSONException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
////
////        productInfo = productInfoObj.toString();
////
////        Log.e("TAG", productInfoObj.toString());
////
////        Random rand = new Random();
////        String rndm = Integer.toString(rand.nextInt())
////                + (System.currentTimeMillis() / 1000L);
////
////
////        hash = hashCal("SHA-512", merchant_key + "|" + txnid + "|" + amount
////                + "|" + productInfo + "|" + firstName + "|" + emailId
////                + "|||||||||||" + salt);
////
////        action1 = base_url.concat("/_payment");
////
////        webView.setWebViewClient(new WebViewClient() {
////
////            @Override
////            public void onReceivedError(WebView view, int errorCode,
////                                        String description, String failingUrl) {
////                // TODO Auto-generated method stub
////                Toast.makeText(OrderSummaryActivity.this, "Oh no! " + description,
////                        Toast.LENGTH_SHORT).show();
////            }
////
////            @Override
////            public void onReceivedSslError(WebView view,
////                                           SslErrorHandler handler, SslError error) {
////                // TODO Auto-generated method stub
////                handler.proceed();
////            }
////
////            @Override
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////
////                if (url.equals(SUCCESS_URL)) {
////
////                } else {
////
////                }
////                return super.shouldOverrideUrlLoading(view, url);
////            }
////            @Override
////            public void onPageFinished(WebView view, String url) {
////
////                if (url.equals(SUCCESS_URL)) {
////                    setSuccessPayment();
////
////                } else if (url.equals(FAILED_URL)) {
////                    Log.e("Url","Fail");
////                }
////                super.onPageFinished(view, url);
////            }
////        });
////
////        webView.setVisibility(View.VISIBLE);
////        webView.getSettings().setBuiltInZoomControls(true);
////        webView.getSettings().setCacheMode(2);
////        webView.getSettings().setDomStorageEnabled(true);
////        webView.clearHistory();
////        webView.clearCache(true);
////        webView.getSettings().setJavaScriptEnabled(true);
////        webView.getSettings().setSupportZoom(true);
////        webView.getSettings().setUseWideViewPort(false);
////        webView.getSettings().setLoadWithOverviewMode(false);
////
////        webView.addJavascriptInterface(new PayUJavaScriptInterface(OrderSummaryActivity.this),
////                "PayUMoney");
////        Map<String, String> mapParams = new HashMap<String, String>();
////        mapParams.put("key", merchant_key);
////        mapParams.put("hash", hash);
////        mapParams.put("txnid", txnid);
////        mapParams.put("service_provider", "payu_paisa");
////        mapParams.put("amount", amount);
////        mapParams.put("firstname", firstName);
////        mapParams.put("email", emailId);
////        mapParams.put("phone", phone);
////
////        mapParams.put("productinfo", productInfo);
////        mapParams.put("surl", SUCCESS_URL);
////        mapParams.put("furl", FAILED_URL);
////        mapParams.put("lastname", "");
////
////        mapParams.put("address1", "");
////        mapParams.put("address2", "");
////        mapParams.put("city", "");
////        mapParams.put("state", "");
////
////        mapParams.put("country", "");
////        mapParams.put("zipcode", "");
////        mapParams.put("udf1", "");
////        mapParams.put("udf2", "");
////
////        mapParams.put("udf3", "");
////        mapParams.put("udf4", "");
////        mapParams.put("udf5", "");
////        // mapParams.put("pg", (empty(PayMentGateWay.this.params.get("pg"))) ?
////        // ""
////        // : PayMentGateWay.this.params.get("pg"));
////        webview_ClientPost(webView, action1, mapParams.entrySet());
////
//    }

    public class PayUJavaScriptInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        PayUJavaScriptInterface(Context c) {
            mContext = c;
        }

        public void success(long id, final String paymentId) {

            mHandler.post(new Runnable() {

                public void run() {
                    mHandler = null;
                    Toast.makeText(OrderSummaryActivity.this, "Success",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void webview_ClientPost(WebView webView, String url,
                                   Collection<Map.Entry<String, String>> postData) {
        StringBuilder sb = new StringBuilder();

        sb.append("<html><head></head>");
        sb.append("<body onload='form1.submit()'>");
        sb.append(String.format("<form id='form1' action='%s' method='%s'>",
                url, "post"));
        for (Map.Entry<String, String> item : postData) {
            sb.append(String.format(
                    "<input name='%s' type='hidden' value='%s' />",
                    item.getKey(), item.getValue()));
        }
        sb.append("</form></body></html>");
        Log.d("tag", "webview_ClientPost called");
        webView.loadData(sb.toString(), "text/html", "utf-8");
    }

    public boolean empty(String s) {
        if (s == null || s.trim().equals(""))
            return true;
        else
            return false;
    }
//
//    // deprecated, should be used only for testing.
//    private PostData calculateHash(String key, String command, String var1, String salt) {
//        checksum = null;
//        checksum = new PayUChecksum();
//        checksum.setKey(key);
//        checksum.setCommand(command);
//        checksum.setVar1(var1);
//        checksum.setSalt(salt);
//        return checksum.getHash();
//    }

    /******************************
     * Client hash generation
     ***********************************/
    // Do not use this, you may use this only for testing.
    // lets generate hashes.
    // This should be done from server side..
    // Do not keep salt anywhere in app.
    private String calculateHash(String hashString) {
        try {
            StringBuilder hash = new StringBuilder();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
            return hash.toString();
        } catch (Exception e) {
            return "ERROR";
        }
    }

//    /******************************
//     * Client hash generation
//     ***********************************/
//    // Do not use this, you may use this only for testing.
//    // lets generate hashes.
//    // This should be done from server side..
//    // Do not keep salt anywhere in app.
//    public void generateHashFromSDK(PaymentParams mPaymentParams, String salt) {
//        PayuHashes payuHashes = new PayuHashes();
//        PostData postData = new PostData();
//
////        if(mPaymentParams.getBeneficiaryAccountNumber()== null){
//
//        // payment Hash;
//        checksum = null;
//        checksum = new PayUChecksum();
//        checksum.setAmount(mPaymentParams.getAmount());
//        checksum.setKey(mPaymentParams.getKey());
//        checksum.setTxnid(mPaymentParams.getTxnId());
//        checksum.setEmail(mPaymentParams.getEmail());
//        checksum.setSalt(salt);
//        checksum.setProductinfo(mPaymentParams.getProductInfo());
//        checksum.setFirstname(mPaymentParams.getFirstName());
//        checksum.setUdf1(mPaymentParams.getUdf1());
//        checksum.setUdf2(mPaymentParams.getUdf2());
//        checksum.setUdf3(mPaymentParams.getUdf3());
//        checksum.setUdf4(mPaymentParams.getUdf4());
//        checksum.setUdf5(mPaymentParams.getUdf5());
//
//        postData = checksum.getHash();
//        if (postData.getCode() == PayuErrors.NO_ERROR) {
//            payuHashes.setPaymentHash(postData.getResult());
//        }
//
//        if (mPaymentParams.getSubventionAmount() != null && !mPaymentParams.getSubventionAmount().isEmpty()){
//            subventionHash = calculateHash(""+mPaymentParams.getKey()+"|"+mPaymentParams.getTxnId()+"|"+mPaymentParams.getAmount()+"|"+mPaymentParams.getProductInfo()+"|"+mPaymentParams.getFirstName()+"|"+mPaymentParams.getEmail()+"|"+mPaymentParams.getUdf1()+"|"+mPaymentParams.getUdf2()+"|"+mPaymentParams.getUdf3()+"|"+mPaymentParams.getUdf4()+"|"+mPaymentParams.getUdf5()+"||||||"+salt+"|"+mPaymentParams.getSubventionAmount());
//        }
//
//        /*}
//
//        else {
//            String hashString = merchantKey + "|" + mPaymentParams.getTxnId() + "|" + mPaymentParams.getAmount() + "|" + mPaymentParams.getProductInfo() + "|" + mPaymentParams.getFirstName() + "|" + mPaymentParams.getEmail() + "|" + mPaymentParams.getUdf1() + "|" + mPaymentParams.getUdf2() + "|" + mPaymentParams.getUdf3() + "|" + mPaymentParams.getUdf4() + "|" + mPaymentParams.getUdf5() + "||||||{\"beneficiaryAccountNumber\":\"" +mPaymentParams.getBeneficiaryAccountNumber()+ "\"}|" + salt;
//
//            paymentHash1 = calculateHash(hashString);
//            payuHashes.setPaymentHash(paymentHash1);
//
//
//
//        }*/
//
//        // checksum for payemnt related details
//        // var1 should be either user credentials or default
//        String var1 = mPaymentParams.getUserCredentials() == null ? PayuConstants.DEFAULT : mPaymentParams.getUserCredentials();
//        String key = mPaymentParams.getKey();
//
//        if ((postData = calculateHash(key, PayuConstants.PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) // Assign post data first then check for success
//            payuHashes.setPaymentRelatedDetailsForMobileSdkHash(postData.getResult());
//        //vas
//        if ((postData = calculateHash(key, PayuConstants.VAS_FOR_MOBILE_SDK, PayuConstants.DEFAULT, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
//            payuHashes.setVasForMobileSdkHash(postData.getResult());
//
//        // getIbibocodes
//        if ((postData = calculateHash(key, PayuConstants.GET_MERCHANT_IBIBO_CODES, PayuConstants.DEFAULT, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
//            payuHashes.setMerchantIbiboCodesHash(postData.getResult());
//
//        if (!var1.contentEquals(PayuConstants.DEFAULT)) {
//            // get user card
//            if ((postData = calculateHash(key, PayuConstants.GET_USER_CARDS, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) // todo rename storedc ard
//                payuHashes.setStoredCardsHash(postData.getResult());
//            // save user card
//            if ((postData = calculateHash(key, PayuConstants.SAVE_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
//                payuHashes.setSaveCardHash(postData.getResult());
//            // delete user card
//            if ((postData = calculateHash(key, PayuConstants.DELETE_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
//                payuHashes.setDeleteCardHash(postData.getResult());
//            // edit user card
//            if ((postData = calculateHash(key, PayuConstants.EDIT_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
//                payuHashes.setEditCardHash(postData.getResult());
//        }
//
//        if (mPaymentParams.getOfferKey() != null) {
//            postData = calculateHash(key, PayuConstants.OFFER_KEY, mPaymentParams.getOfferKey(), salt);
//            if (postData.getCode() == PayuErrors.NO_ERROR) {
//                payuHashes.setCheckOfferStatusHash(postData.getResult());
//            }
//        }
//
//        if (mPaymentParams.getOfferKey() != null && (postData = calculateHash(key, PayuConstants.CHECK_OFFER_STATUS, mPaymentParams.getOfferKey(), salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) {
//            payuHashes.setCheckOfferStatusHash(postData.getResult());
//        }
//
//        // we have generated all the hases now lest launch sdk's ui
//        launchSdkUI(payuHashes);
//    }
//
//
//    /**
//     * This method adds the Payuhashes and other required params to intent and launches the PayuBaseActivity.java
//     *
//     * @param payuHashes it contains all the hashes generated from merchant server
//     */
//    public void launchSdkUI(PayuHashes payuHashes) {
//
//        Intent intent = new Intent(this, PayUBaseActivity.class);
//        intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
//        intent.putExtra(PayuConstants.PAYMENT_PARAMS, mPaymentParams);
//        intent.putExtra(SdkUIConstants.SUBVENTION_HASH, subventionHash);
//        //  intent.putExtra(SdkUIConstants.SI_HASH,siHash);
//        intent.putExtra(PayuConstants.SALT,salt);
//        intent.putExtra(PayuConstants.PAYU_HASHES, payuHashes);
//        startActivityForResult(intent, PayuConstants.PAYU_REQUEST_CODE);
//    }



    private void initUiSdk(PayUPaymentParams payUPaymentParams) {
        Log.e("Payment",""+bl_Payment);

        PayUCheckoutPro.open(
                this,
                payUPaymentParams,
             //   getCheckoutProConfig(),
                new PayUCheckoutProListener() {

                    @Override
                    public void onPaymentSuccess(Object response) {
                        Utils.hideProgressDialoug();
                        setButtonSelection(true);
                        Log.e("PayUResponse",""+response);
                      //  showAlertDialog(response);
                        try {
                            setSuccessPayment();
                        } catch (Exception e) {
                            Log.d("ERROR==", ">>>> " + e.getMessage());
                        }

                    }


                    @Override
                    public void onPaymentFailure(Object response) {
                        setButtonSelection(true);
                        Utils.hideProgressDialoug();
                      //  showAlertDialog(response);
                        hideFullDialog();
                        setSelection(true);
                        bl_Payment = false;
                        paymentFail();
                    }

                    @Override
                    public void onPaymentCancel(boolean isTxnInitiated) {
                        setButtonSelection(true);
                        Utils.hideProgressDialoug();
                      //  showSnackBar(getResources().getString(R.string.transaction_cancelled_by_user));
                        hideFullDialog();
                        setSelection(true);
                        bl_Payment = false;
                        paymentCancelled();
                    }

                    @Override
                    public void onError(ErrorResponse errorResponse) {
                        setButtonSelection(true);
                        Utils.hideProgressDialoug();
                        String errorMessage = errorResponse.getErrorMessage();
                      //  Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show();
                        if (TextUtils.isEmpty(errorMessage))
                            errorMessage = "some_error_occurred";
                       // showSnackBar(errorMessage);
                        setSelection(true);
                        bl_Payment = false;
                    }

                    @Override
                    public void setWebViewProperties(@Nullable WebView webView, @Nullable Object o) {
                        //For setting webview properties, if any. Check Customized Integration section for more details on this
                        webView.setWebChromeClient(new CheckoutProWebChromeClient((Bank)o));
                    }

                    @Override
                    public void generateHash(HashMap<String, String> valueMap, PayUHashGenerationListener hashGenerationListener) {
                        String hashName = valueMap.get(PayUCheckoutProConstants.CP_HASH_NAME);
                        String hashData = valueMap.get(PayUCheckoutProConstants.CP_HASH_STRING);
                        if (!TextUtils.isEmpty(hashName) && !TextUtils.isEmpty(hashData)) {
                            //Generate Hash from your backend here
                            String hash = null;
                            if (hashName.equalsIgnoreCase(PayUCheckoutProConstants.CP_LOOKUP_API_HASH)){
                                //Calculate HmacSHA1 HASH for calculating Lookup API Hash
                                ///Do not generate hash from local, it needs to be calculated from server side only. Here, hashString contains hash created from your server side.

                                hash = calculateHmacSHA1Hash(hashData, Constants.merchantKey);
                            } else {

                                //Calculate SHA-512 Hash here
                                hash = calculateHash(hashData + Constants.saltKey);
                            }

                            HashMap<String, String> dataMap = new HashMap<>();
                            dataMap.put(hashName, hash);
                            hashGenerationListener.onHashGenerated(dataMap);
                        }
                    }
                }
        );

        bl_Payment = true;
    }

    private void setButtonSelection(boolean b) {
        btnConfirmOrder.setEnabled(b);
        btnConfirmOrder.setClickable(b);
    }


    class CheckoutProWebChromeClient extends PayUWebChromeClient {
        public CheckoutProWebChromeClient(Bank bank){
            super(bank);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    }


    private PayUPaymentParams preparePayUBizParams() {
        HashMap<String, Object> additionalParams = new HashMap<>();
        additionalParams.put(PayUCheckoutProConstants.CP_UDF1, "udf1");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF2, "udf2");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF3, "udf3");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF4, "udf4");
        additionalParams.put(PayUCheckoutProConstants.CP_UDF5, "udf5");

        //Below params should be passed only when integrating Multi-currency support
        //TODO Please pass your own Merchant Access Key below as provided by your Key Account Manager at PayU.
//        additionalParams.put(PayUCheckoutProConstants.CP_MERCHANT_ACCESS_KEY,CP_MERCHANT_ACCESS_KEY testMerchantAccessKey);

//        Below params should be passed only when sodexo payment option is enabled and want to show saved sodexo card
        //TODO Please pass sodexosrcid for sodexo card which will be recieved in new sodexo card txn response
//        additionalParams.put(PayUCheckoutProConstants.SODEXO_SOURCE_ID, sodexosrcid);

        PayUSIParams siDetails = null;
//        if(binding.switchSiOnOff.isChecked()) {
//            siDetails  = new PayUSIParams.Builder().setIsFreeTrial(binding.layoutSiDetails.spFreeTrial.isChecked())
//                    .setBillingAmount(binding.layoutSiDetails.etBillingAmountValue.getText().toString())
//                    .setBillingCycle(PayUBillingCycle.valueOf(binding.layoutSiDetails.etBillingCycleValue.getSelectedItem().toString()))
//                    .setBillingInterval(Integer.parseInt(binding.layoutSiDetails.etBillingIntervalValue.getText().toString()))
//                    .setPaymentStartDate(binding.layoutSiDetails.etPaymentStartDateValue.getText().toString())
//                    .setPaymentEndDate(binding.layoutSiDetails.etPaymentEndDateValue.getText().toString())
//                    .setRemarks(binding.layoutSiDetails.etRemarksValue.getText().toString())
//                    .setBillingLimit(PayuBillingLimit.valueOf(binding.layoutSiDetails.etBillingLimitValue.getSelectedItem().toString()))
//                    .setBillingRule(PayuBillingRule.valueOf(binding.layoutSiDetails.etBillingRuleValue.getSelectedItem().toString()))
//                    .build();
//
//        }


        PayUPaymentParams.Builder builder = new PayUPaymentParams.Builder();
        builder.setAmount(amount)
                .setIsProduction(true)
                .setProductInfo(name)
                .setKey(Constants.merchantKey)
                .setPhone(mobileNo)
                .setTransactionId(txnid)
                .setFirstName("firstName")
                .setEmail(email)
                .setSurl(SUCCESS_URL)
                .setFurl(FAILED_URL)
                .setUserCredential(Constants.merchantKey + ":"+email)
                .setAdditionalParams(additionalParams)
                .setPayUSIParams(siDetails);
        PayUPaymentParams payUPaymentParams = builder.build();

        return payUPaymentParams;
    }




    private String getHexString(byte[] array){
        StringBuilder hash = new StringBuilder();
        for (byte hashByte : array) {
            hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
        }
        return hash.toString();
    }


    /**
     * Hash Should be generated from your sever side only.
     *
     * Do not use this, you may use this only for testing.
     * This should be done from server side..
     * Do not keep salt anywhere in app.
     * */
    private String calculateHmacSHA1Hash(String data, String key) {
        String HMAC_SHA1_ALGORITHM = "HmacSHA1";
        String result = null;

        try {
            Key signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = getHexString(rawHmac);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}

