package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.snackbar.Snackbar;
import com.payu.base.models.ErrorResponse;
import com.payu.base.models.PayUPaymentParams;
import com.payu.base.models.PayUSIParams;
import com.payu.base.models.PaymentMode;
import com.payu.base.models.PaymentType;
import com.payu.checkoutpro.PayUCheckoutPro;
import com.payu.checkoutpro.models.PayUCheckoutProConfig;
import com.payu.checkoutpro.utils.PayUCheckoutProConstants;
import com.payu.ui.model.listeners.PayUCheckoutProListener;
import com.payu.ui.model.listeners.PayUHashGenerationListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PayUMoney  extends AppCompatActivity {

    //    WebView webView;
//
//    String merchant_key = "0w2qzK";
//    String salt = "Oa3o6OCxGvidPIIxnP2tlZ7Wq9z1VEpU";
//    String action1 = "";
//    String base_url = "https://secure.payu.in";
//    // int error = 0;
//    // String hashString = "";
//    // Map<String, String> params;
//    String txnid = "TXN8367286482920";
//    String amount = "10";
//    String productInfo = "";
//    String firstName = "Rajesh";
//    String emailId = "rajeshmcashc10@gmail.com";
//
//    private String SUCCESS_URL = "https://www.payumoney.com/mobileapp/payumoney/failure.php";
//    private String FAILED_URL = "https://www.payumoney.com/mobileapp/payumoney/success.php";
//    private String phone = "7878934042";
//    private String serviceProvider = "payu_paisa";
//    private String hash = "";
//
//    Handler mHandler = new Handler();
//
//     PayUMoney obj_PayMoney ;


    private final String email = "snooze@payu.in";
    private final String phone = "9999999999";
    private final String merchantName = "RH Group";
    private final String surl = "https://payuresponse.firebaseapp.com/success";
    private final String furl = "https://payuresponse.firebaseapp.com/failure";
    private final String amount = "1.0";
    private final String testKey = "IUIaFM";
    private final String testSalt = "<Please_add_salt_here>";
    /**
     * Enter below keys when integrating Multi Currency Payments.
     * To get these credentials, please reach out to your Key Account Manager at PayU
     * */
    private final String testMerchantAccessKey = "gtKFFx";
    private final String testMerchantSecretKey = "wia56q6O";

    private final String prodKey = "0w2qzK";
    private final String prodSalt = "Oa3o6OCxGvidPIIxnP2tlZ7Wq9z1VEpU";


    private long mLastClickTime;
    private final String[] billingCycle = {  "DAILY", "WEEKLY", "MONTHLY", "YEARLY", "ONCE", "ADHOC"};
    private final String[] billingRule = {"MAX", "EXACT"};
    private final String[] billingLimit = {"ON", "BEFORE", "AFTER"};
    private final String[] noteCategory = {"CARD", "NB", "WALLET", "UPI", "EMI", "COMMON", "NULL"};

    @SuppressLint({"JavascriptInterface", "WrongConstant"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUiSdk(preparePayUBizParams());


//        obj_PayMoney = this;
//
//        webView = new WebView(this);
//        setContentView(webView);
//
//        getBundleData();
//
//        JSONObject productInfoObj = new JSONObject();
//        JSONArray productPartsArr = new JSONArray();
//        JSONObject productPartsObj1 = new JSONObject();
//        JSONObject paymentIdenfierParent = new JSONObject();
//        JSONArray paymentIdentifiersArr = new JSONArray();
//        JSONObject paymentPartsObj1 = new JSONObject();
//        JSONObject paymentPartsObj2 = new JSONObject();
//        try {
//            // Payment Parts
//            productPartsObj1.put("name", firstName);
//            productPartsObj1.put("description", productInfo);
//            productPartsObj1.put("value", "1");
//            productPartsObj1.put("isRequired", "true");
//            productPartsObj1.put("settlementEvent", "EmailConfirmation");
//            productPartsArr.put(productPartsObj1);
//            productInfoObj.put("paymentParts", productPartsArr);
//
//            // Payment Identifiers
//            paymentPartsObj1.put("field", "CompletionDate");
//            paymentPartsObj1.put("value", "31/10/2012");
//            paymentIdentifiersArr.put(paymentPartsObj1);
//
//            paymentPartsObj2.put("field", "TxnId");
//            paymentPartsObj2.put("value", txnid);
//            paymentIdentifiersArr.put(paymentPartsObj2);
//
//            paymentIdenfierParent.put("paymentIdentifiers",
//                    paymentIdentifiersArr);
//            productInfoObj.put("", paymentIdenfierParent);
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        productInfo = productInfoObj.toString();
//
//        Log.e("TAG", productInfoObj.toString());
//
//        Random rand = new Random();
//        String rndm = Integer.toString(rand.nextInt())
//                + (System.currentTimeMillis() / 1000L);
//
//
//        hash = hashCal("SHA-512", merchant_key + "|" + txnid + "|" + amount
//                + "|" + productInfo + "|" + firstName + "|" + emailId
//                + "|||||||||||" + salt);
//
//        action1 = base_url.concat("/_payment");
//
//
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode,
//                                        String description, String failingUrl) {
//                // TODO Auto-generated method stub
//                Toast.makeText(PayUMoney.this, "Oh no! " + description,
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view,
//                                           SslErrorHandler handler, SslError error) {
//                // TODO Auto-generated method stub
//                handler.proceed();
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//                if (url.equals(SUCCESS_URL)) {
//
//                } else {
//
//                }
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//            @Override
//            public void onPageFinished(WebView view, String url) {
//
//                if (url.equals(SUCCESS_URL)) {
//
//                    Bundle b = new Bundle();
//                    b.putInt("Payment",1);
//                    b.putString("Amount",amount);
//                    Intent i  = new Intent(obj_PayMoney,OrderSummaryActivity.class);
//                    i.putExtras(b);
//                    startActivity(i);
//                    finish();
//
//                } else if (url.equals(FAILED_URL)) {
//                    Log.e("Url","Fail");
//                    Bundle b = new Bundle();
//                    b.putInt("Payment",0);
//                    Intent i  = new Intent(obj_PayMoney,OrderSummaryActivity.class);
//                    i.putExtras(b);
//                    startActivity(i);
//                    finish();
//                }
//                super.onPageFinished(view, url);
//            }
//        });
//
//        webView.setVisibility(View.VISIBLE);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setCacheMode(2);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.clearHistory();
//        webView.clearCache(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setUseWideViewPort(false);
//        webView.getSettings().setLoadWithOverviewMode(false);
//
//        webView.addJavascriptInterface(new PayUJavaScriptInterface(PayUMoney.this),
//                "PayUMoney");
//        Map<String, String> mapParams = new HashMap<String, String>();
//        mapParams.put("key", merchant_key);
//        mapParams.put("hash", hash);
//        mapParams.put("txnid", txnid);
//        mapParams.put("service_provider", "payu_paisa");
//        mapParams.put("amount", amount);
//        mapParams.put("firstname", firstName);
//        mapParams.put("email", emailId);
//        mapParams.put("phone", phone);
//
//        mapParams.put("productinfo", productInfo);
//        mapParams.put("surl", SUCCESS_URL);
//        mapParams.put("furl", FAILED_URL);
//        mapParams.put("lastname", "");
//
//        mapParams.put("address1", "");
//        mapParams.put("address2", "");
//        mapParams.put("city", "");
//        mapParams.put("state", "");
//
//        mapParams.put("country", "");
//        mapParams.put("zipcode", "");
//        mapParams.put("udf1", "");
//        mapParams.put("udf2", "");
//
//        mapParams.put("udf3", "");
//        mapParams.put("udf4", "");
//        mapParams.put("udf5", "");
//        // mapParams.put("pg", (empty(PayMentGateWay.this.params.get("pg"))) ?
//        // ""
//        // : PayMentGateWay.this.params.get("pg"));
//        webview_ClientPost(webView, action1, mapParams.entrySet());
//
//    }
//
//    private void getBundleData() {
//        Bundle b = getIntent().getExtras();
//
//        if(b !=null){
//            txnid  = b.getString("txnid");
//            amount  = b.getString("amount");
//            firstName  = b.getString("Firstname");
//            emailId  = b.getString("email");
//            phone  = b.getString("phone");
//            Log.e("TransctionId",""+b);
//        }
//    }
//
//    public class PayUJavaScriptInterface {
//        Context mContext;
//
//        /** Instantiate the interface and set the context */
//        PayUJavaScriptInterface(Context c) {
//            mContext = c;
//        }
//
//        public void success(long id, final String paymentId) {
//            mHandler.post(new Runnable() {
//                public void run() {
//                    mHandler = null;
//                    Toast.makeText(PayUMoney.this, "Success",
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
//    public void webview_ClientPost(WebView webView, String url,
//                                   Collection<Map.Entry<String, String>> postData) {
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("<html><head></head>");
//        sb.append("<body onload='form1.submit()'>");
//        sb.append(String.format("<form id='form1' action='%s' method='%s'>",
//                url, "post"));
//        for (Map.Entry<String, String> item : postData) {
//            sb.append(String.format(
//                    "<input name='%s' type='hidden' value='%s' />",
//                    item.getKey(), item.getValue()));
//        }
//        sb.append("</form></body></html>");
//        Log.d("tag", "webview_ClientPost called");
//        webView.loadData(sb.toString(), "text/html", "utf-8");
//    }
//
//    public boolean empty(String s) {
//        if (s == null || s.trim().equals(""))
//            return true;
//        else
//            return false;
//    }
//
//    public String hashCal(String type, String str) {
//        byte[] hashseq = str.getBytes();
//        StringBuffer hexString = new StringBuffer();
//        try {
//            MessageDigest algorithm = MessageDigest.getInstance(type);
//            algorithm.reset();
//            algorithm.update(hashseq);
//            byte messageDigest[] = algorithm.digest();
//
//            for (int i = 0; i < messageDigest.length; i++) {
//                String hex = Integer.toHexString(0xFF & messageDigest[i]);
//                if (hex.length() == 1)
//                    hexString.append("0");
//                hexString.append(hex);
//            }
//        } catch (NoSuchAlgorithmException nsae) {
//        }
//        return hexString.toString();
//
//    }
//
//}

    }

    private void initUiSdk(PayUPaymentParams payUPaymentParams) {
        PayUCheckoutPro.open(
                this,
                payUPaymentParams,
                //  getCheckoutProConfig(),
                new PayUCheckoutProListener() {

                    @Override
                    public void onPaymentSuccess(Object response) {
                        showAlertDialog(response);

                    }

                    @Override
                    public void onPaymentFailure(Object response) {
                        showAlertDialog(response);
                    }

                    @Override
                    public void onPaymentCancel(boolean isTxnInitiated) {
                      //  showSnackBar(getResources().getString(R.string.transaction_cancelled_by_user));
                    }

                    @Override
                    public void onError(ErrorResponse errorResponse) {
                        String errorMessage = errorResponse.getErrorMessage();
                        if (TextUtils.isEmpty(errorMessage))
                            errorMessage = "some_error_occurred";
                      //  showSnackBar(errorMessage);
                    }

                    @Override
                    public void setWebViewProperties(@Nullable WebView webView, @Nullable Object o) {
                        //For setting webview properties, if any. Check Customized Integration section for more details on this
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

                                hash = calculateHmacSHA1Hash(hashData, testMerchantSecretKey);
                            } else {

                                //Calculate SHA-512 Hash here
                                hash = calculateHash(hashData + prodSalt);
                            }

                            HashMap<String, String> dataMap = new HashMap<>();
                            dataMap.put(hashName, hash);
                            hashGenerationListener.onHashGenerated(dataMap);
                        }
                    }
                }
        );
    }

    /**
     * Hash Should be generated from your sever side only.
     *
     * Do not use this, you may use this only for testing.
     * This should be done from server side..
     * Do not keep salt anywhere in app.
     * */
    private String calculateHash(String hashString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            return getHexString(mdbytes);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
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


    private void showSnackBar(String message) {

    }



    private String getHexString(byte[] array){
        StringBuilder hash = new StringBuilder();
        for (byte hashByte : array) {
            hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
        }
        return hash.toString();
    }
    private void showAlertDialog(Object response){
        HashMap<String,Object> result = (HashMap<String, Object>) response;
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(
                        "Payu's Data : " + result.get(PayUCheckoutProConstants.CP_PAYU_RESPONSE) + "\n\n\n Merchant's Data: " + result.get(
                                PayUCheckoutProConstants.CP_MERCHANT_RESPONSE
                        )
                )
                .setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
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
        builder.setAmount("1.00")
                .setIsProduction(true)
                .setProductInfo("Macbook Pro")
                .setKey(prodKey)
                .setPhone(phone)
                .setTransactionId(String.valueOf(System.currentTimeMillis()))
                .setFirstName("John")
                .setEmail(email)
                .setSurl(surl)
                .setFurl(furl)
                .setUserCredential(prodKey + ":john@yopmail.com")
                .setAdditionalParams(additionalParams)
                .setPayUSIParams(siDetails);
        PayUPaymentParams payUPaymentParams = builder.build();


        PayUCheckoutProConfig payUCheckoutProConfig = new PayUCheckoutProConfig ();
        payUCheckoutProConfig.setShowCbToolbar(false); //hide toolbar


        ArrayList<PaymentMode> checkoutOrderList = new ArrayList<>();
        checkoutOrderList.add(new PaymentMode(PaymentType.CARD));
        checkoutOrderList.add(new PaymentMode(PaymentType.UPI));
        payUCheckoutProConfig.setPaymentModesOrder (checkoutOrderList);


        return payUPaymentParams;
    }


}
