package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.sms.OtpReceivedInterface;
import com.nebulacompanies.ibo.sms.SmsBroadcastReceiver;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.Scanner;
import java.util.StringTokenizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import pl.droidsonroids.gif.GifImageView;

import static com.nebulacompanies.ibo.util.Config.TESTING_API;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class RegistrationActivityWebview extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        OtpReceivedInterface, GoogleApiClient.OnConnectionFailedListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    WebView webView;
    String URL = TESTING_API + "/Structure/Register/IndexMobileView?Token=&isloggedin=false";
    // String URL = "https://www.google.com/";
    //String URLChange = "https://www.extremetech.com/extreme/303740-car-of-the-year-extremetechs-best-cars-for-2020";
    String URLChange = TESTING_API + "/Structure/Register/IndexMobileView?Isloggedin=False";
    Session session;
    String Ssession;
    LinearLayout lnProgressBar;
    GoogleApiClient mGoogleApiClient;
    SmsBroadcastReceiver mSmsBroadcastReceiver;
    private int RESOLVE_HINT = 2;
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    GifImageView mProgressDialog;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    ConnectionDetector cd;
    String iboNumber;
    MaterialProgressBar materialProgressBar;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_webview);
        session = new Session(this);
        init();

        mSmsBroadcastReceiver = new SmsBroadcastReceiver();

        //set google api client for hint request
      /*  mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.CREDENTIALS_API)
                .build();*/

        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
           unregisterReceiver(mSmsBroadcastReceiver);
        }catch (Exception e){e.printStackTrace();}
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetJavaScriptEnabled")
    void init() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mProgressDialog = (GifImageView) findViewById(R.id.progressBar);
        lnProgressBar = (LinearLayout) findViewById(R.id.ln_progressBar);
        materialProgressBar = (MaterialProgressBar) findViewById(R.id.progresbar);
        webView = (WebView) findViewById(R.id.webView);


        cd = new ConnectionDetector(this);
        //Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        initError();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        Ssession = session.getAccessToken();

        if (Utils.isNetworkAvailable(getApplicationContext())) {
            webView.loadUrl(URL + Ssession);
            // webView.addJavascriptInterface(new MyJavaScriptInterface(this),"android");
            //webView.loadUrl(URL );
            startSMSListener();
            webView.setWebViewClient(getWebViewClient());
            String merge = URL + Ssession;
            Log.d("URLWebview:", "URLWebview:" + merge);
            // webView.addJavascriptInterface(new WebAppInterface(this), "android");
        } else {
            noInternetConnection();
        }


    }

    void initError() {
        llEmpty = (LinearLayout) findViewById(R.id.llEmpty1);
        imgError = (ImageView) findViewById(R.id.imgError1);
        txtErrorTitle = (MyTextView) findViewById(R.id.txtErrorTitle1);
        txtRetry = (MyTextView) findViewById(R.id.txtRetry1);
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        });
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            llEmpty.setVisibility(View.GONE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(URL + session.getAccessToken());
            webView.setWebViewClient(getWebViewClient());
            mSwipeRefreshLayout.setRefreshing(false);
            startSMSListener();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            //noInternetConnection();
        }
    }



    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(this);
        Task<Void> mTask = mClient.startSmsRetriever();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Toast.makeText(getActivity(), "SMS Retriever starts", Toast.LENGTH_LONG).show();
            }
        });
        mTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);

        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        });
    }

    private WebViewClient getWebViewClient() {

        return new WebViewClient() {

            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                lnProgressBar.setVisibility(View.VISIBLE);
                mProgressDialog.setVisibility(View.VISIBLE);
                materialProgressBar.setVisibility(View.VISIBLE);
                view.loadUrl(request.getUrl().toString());
                //view.loadUrl(request.getUrl().toString(), getCustomHeaders());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                lnProgressBar.setVisibility(View.VISIBLE);
                mProgressDialog.setVisibility(View.VISIBLE);
                materialProgressBar.setVisibility(View.VISIBLE);
                // view.loadUrl(url);
                //view.loadUrl(url, getCustomHeaders());

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                lnProgressBar.setVisibility(View.GONE);
                mProgressDialog.setVisibility(View.GONE);
                materialProgressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
                Log.d("URL","URL: "+url);

                if (url.contains(URLChange)) {

                    alertSuccess();

                    // String urlChange =URLChange; //http://203.88.139.169:9064/Structure/Register/IndexMobileView?Isloggedin=False&IBOID=55205

                    String[] separated = url.split("&");
                    String str1 = separated[0];  //http://203.88.139.169:9064/Structure/Register/IndexMobileView
                    Log.d("String1","String1"+str1);

                    String str2 = separated[1];
                    Log.d("String11","String11"+str2);

                    String[] str3 = str2.split("=");
                    Log.d("String2","String2"+str3);

                    String str4 = str3[0];  //IBOID
                    iboNumber = str3[1];  //55205

                }

                // view.loadUrl("javascript:window.android.onUrlChange(window.location.href);");
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mSwipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (webView.getScrollY() == 0) {
                            mSwipeRefreshLayout.setEnabled(true);
                            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                    refreshContent();
                                }
                            });
                        } else {
                            mSwipeRefreshLayout.setEnabled(false);
                        }
                    }
                });
    }

    @Override
    public void onStop() {
        mSwipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onOtpReceived(String otp) {
        // Toast.makeText(this, "Otp Received " + otp, Toast.LENGTH_LONG).show();
        Log.d("OTP:", "OTP: " + otp);
        int splitNumber = new Scanner(otp).useDelimiter("\\D+").nextInt();
        // webView.loadUrl("javascript: (function() {document.getElementsByClassName('autootp').value= '" + splitNumber + "';VerifyOTP();}) ();");
        webView.loadUrl("javascript: (function() {$('form').find(':text.autootp').filter(':visible:first').val('"+splitNumber+"');VerifyOTP();}) ();");
    }

    @Override
    public void onOtpTimeout() {
        // Toast.makeText(this, "Time out, please resend", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result if we want hint number
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                    // credential.getId();  <-- will need to process phone number string
                    //  webView.loadUrl("javascript: (function() {document.getElementById('OTP').value= '"+credential.getId()+"';VerifyOTP();}) ();" );
                }

            }
        }
    }


    public class MyJavaScriptInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        MyJavaScriptInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void onUrlChange(String url) {
            Log.d("hydrated", "onUrlChange " + url);

            if (url.contains(URLChange)) {
                Toast.makeText(mContext, url, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RegistrationActivityWebview.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    private void alertSuccess(){
        SweetAlertDialog loading = new SweetAlertDialog(RegistrationActivityWebview.this, SweetAlertDialog.SUCCESS_TYPE);
        loading.setCancelable(false);
        loading.setConfirmText("OK");
        loading.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);

                alertDialog.setCancelable(false);
                textTitle.setText("Success");
                btnConfirm.setText("OK");
                textContent.setText("Registered Associate ID is "+ iboNumber +".\nSMS and Email sent to the registered\nnumber.");
                // textContent.setText("Registration is successful.\n SMS and Email sent to the registered number.");
                Linkify.addLinks(textContent, Linkify.WEB_URLS);
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(v -> {
                    loading.dismiss();
                    Intent intent = new Intent(RegistrationActivityWebview.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finishAffinity();
                });
            }
        });
        if(!isFinishing())
        loading.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
