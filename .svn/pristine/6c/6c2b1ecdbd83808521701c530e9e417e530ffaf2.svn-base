package com.nebulacompanies.ibo.web;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.util.Linkify;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.LoginActivity;
import com.nebulacompanies.ibo.activities.RegistrationActivityWebview;
import com.nebulacompanies.ibo.ecom.MainActivity;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.sms.OtpReceivedInterface;
import com.nebulacompanies.ibo.sms.SmsBroadcastReceiver;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.Scanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import pl.droidsonroids.gif.GifImageView;

import static com.nebulacompanies.ibo.util.Config.TESTING_API;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class RegistrationFragmentWeb extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        OtpReceivedInterface, GoogleApiClient.OnConnectionFailedListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    WebView webView;
    String URL = TESTING_API + "/Structure/Register/IndexMobileView?Token=";
    String URLChange = TESTING_API + "/Structure/Register/IndexMobileView?&isloggedin=true";
    Session session;
    String Ssession;
    LinearLayout lnProgressBar;
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    GifImageView mProgressDialog;
    MaterialProgressBar materialProgressBar;
    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle,txtErrorsubTitle1, txtRetry;
    ConnectionDetector cd;
    RelativeLayout rlEcom;
    SmsBroadcastReceiver mSmsBroadcastReceiver;
    String iboNumber;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_activity, container, false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        session = new Session(getActivity());
        init(view);

        mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        getActivity().getApplicationContext().registerReceiver(mSmsBroadcastReceiver, intentFilter);


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(mSmsBroadcastReceiver);
        }catch (Exception e){e.printStackTrace();}
    }
    void init(View view) {

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mProgressDialog = (GifImageView) view.findViewById(R.id.progressBar);
        lnProgressBar = (LinearLayout) view.findViewById(R.id.ln_progressBar);
        materialProgressBar = (MaterialProgressBar) view.findViewById(R.id.progresbar);
        webView = (WebView) view.findViewById(R.id.webView);
        rlEcom = (RelativeLayout) view.findViewById(R.id.rl_ecom);
        cd = new ConnectionDetector(getActivity());
        // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        initError(view);

        WebView mWebView = new WebView(getActivity());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new MyWebClient());
        Ssession = session.getAccessToken();


        rlEcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cp = new Intent(getActivity(), MainActivity.class);
                cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cp);
                //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        loadURL();
        Handler mHand = new Handler();
        mHand.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                materialProgressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        }, 5000);
    }

    private void loadURL() {
        if (Utils.isNetworkAvailable(getActivity())) {
            llEmpty.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(URL + Ssession + "&isloggedin=true");
            startSMSListener();

            String merge = URL + Ssession + "&isloggedin=true";
            Log.d("URLWebview:", "URLWebview:" + merge);

        } else {
            noInternetConnection();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }


    void initError(View view) {
        llEmpty = (LinearLayout) view.findViewById(R.id.llEmpty1);
        imgError = (ImageView) view.findViewById(R.id.imgError1);
        txtErrorTitle = (MyTextView) view.findViewById(R.id.txtErrorTitle1);
        txtErrorsubTitle1= (MyTextView) view.findViewById(R.id.txtErrorsubTitle1);
        txtRetry = (MyTextView) view.findViewById(R.id.txtRetry1);
        txtRetry.setOnClickListener(v -> loadURL());
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            llEmpty.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(URL + session.getAccessToken());
            mSwipeRefreshLayout.setRefreshing(false);
            startSMSListener();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);

        }
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_title);
        txtErrorsubTitle1.setText(R.string.error_content);
        txtRetry.setText(R.string.error_retry);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        txtErrorsubTitle1.setVisibility(View.VISIBLE);
    }


    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        txtErrorsubTitle1.setVisibility(View.GONE);
    }

   /* @Override
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
    }*/

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
        webView.loadUrl("javascript: (function() {$('form').find(':text.autootp').filter(':visible:first').val('" + splitNumber + "');VerifyOTP();}) ();");
    }

    @Override
    public void onOtpTimeout() {

    }

    public void startSMSListener() {
        SmsRetrieverClient mClient = SmsRetriever.getClient(getActivity());
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
                Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void alertSuccess() {
        SweetAlertDialog loading = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
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
                textContent.setText("Registered Associate ID is " + iboNumber + ".\nSMS and Email sent to the registered number.");
                // textContent.setText("Registration is successful.\n SMS and Email sent to the registered number.");
                Linkify.addLinks(textContent, Linkify.WEB_URLS);
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                        getActivity().finish();
                        loading.dismiss();

                    }
                });
            }
        });
        loading.show();
    }

    public class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            materialProgressBar.setVisibility(View.GONE);

        }

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
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("Web reg error==", "onPageFinished");
            lnProgressBar.setVisibility(View.GONE);
            materialProgressBar.setVisibility(View.GONE);
            mProgressDialog.setVisibility(View.GONE);
            Log.d("URL", "URL: " + url);
            if (url.contains(URLChange)) {

                alertSuccess();

                // String urlChange =URLChange; //http://203.88.139.169:9064/Structure/Register/IndexMobileView?Isloggedin=False&IBOID=55205

                String[] separated = url.split("&");
                String str1 = separated[0];  //http://203.88.139.169:9064/Structure/Register/IndexMobileView

                String[] str2 = separated[1].split("=");

                String str3 = str2[0];  //IBOID
                iboNumber = str2[1];  //55205


            }
        }

        @Override
       /* public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.d("WEB ERROR","Registration:: "+error);
        }*/

        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Log.e("ServerError", "onReceivedError = " + errorCode);
            //Toast.makeText(getActivity(), "Error2", Toast.LENGTH_SHORT).show();
            if (Utils.isNetworkAvailable(getActivity())) {

                serviceUnavailable();
            } else
                noInternetConnection();

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            Log.e("ServerError1", "onReceivedError = " + errorResponse + view.getUrl() + " : " + errorResponse.getReasonPhrase() + " : " + errorResponse.getStatusCode() + ":" + errorResponse.getData() + ":" + errorResponse.getMimeType());
            //  Toast.makeText(getActivity(), "Error1"+errorResponse , Toast.LENGTH_SHORT).show();
            // serviceUnavailable();
        }
    }
}
