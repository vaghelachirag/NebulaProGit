package com.nebulacompanies.ibo.web;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.DashboardActivity;
import com.nebulacompanies.ibo.activities.LoginActivity;
import com.nebulacompanies.ibo.ecom.MyCartActivity;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.Date;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import pl.droidsonroids.gif.GifImageView;

import static com.nebulacompanies.ibo.util.Config.TESTING_API;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class MySalesFragmentWeb extends Fragment {

    String URL = TESTING_API + "/IBO/Sales/MySalesMobileView?Token=";
    // SwipeRefreshLayout mSwipeRefreshLayout;
    WebView webView;

    Session session;
    String Ssession;
    LinearLayout lnProgressBar;
    MaterialProgressBar materialProgressBar;
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    GifImageView mProgressDialog;
    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtErrorsub, txtRetry;
    ConnectionDetector cd;
    RelativeLayout rlEcom;
    Utils utils;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_activity_pv, container, false);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        session = new Session(getActivity());
        utils = new Utils();

        if (!session.getLogin())
        utils.openNoSession(getActivity(), utils.gotoMysales);

        init(view);
        return view;
    }


    void init(View view) {
        //  mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipe_refresh_layout );
        mProgressDialog = (GifImageView) view.findViewById(R.id.progressBar);
        lnProgressBar = (LinearLayout) view.findViewById(R.id.ln_progressBar);
        materialProgressBar = (MaterialProgressBar) view.findViewById(R.id.progresbar);
       // materialProgressBar.setVisibility(View.GONE);
        webView = (WebView) view.findViewById(R.id.webView);
        rlEcom = (RelativeLayout) view.findViewById(R.id.rl_ecom);
        cd = new ConnectionDetector(getActivity());
        // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        initError(view);

        Ssession = session.getAccessToken();

        webView.setWebViewClient(new MyWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

     /*   WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }*/

     /*   mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.
                webView.loadUrl(webView.getUrl());
            }
        });*/

        rlEcom.setOnClickListener(view1 -> {
            //webView.loadUrl(webView.getUrl());
            loadURL();
        });
        if (session.getLogin())
            loadURL();
       /* else
            utils.openNoSession(getActivity(), utils.gotoMysales);*/
    }

    /* @Override
     public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);

         // check if the request code is same as what is passed  here it is 2
         if (resultCode == Activity.RESULT_CANCELED) {
             if (requestCode == utils.gotoMysales) {
                *//* navigationView.getMenu().findItem(R.id.nav_my_sales).setChecked(false);
                 navigationView.setCheckedItem(R.id.nav_home);*//*
             }
         } else if (resultCode == Activity.RESULT_OK) {
             // update Drawer Info
             if (requestCode == utils.gotoMysales) {
              *//*   openMySales();
                 replaceFragment();*//*
             }
         }
     }*/
    private void loadURL() {
        String newString = Ssession.replace("bearer ", "");
        String merge = URL + newString;

        if (!session.getLogin()) {
            noLogin();
        } else {
            if (Utils.isNetworkAvailable(getActivity())) {
                materialProgressBar.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                rlEcom.setVisibility(View.VISIBLE);
                webView.loadUrl(merge);
                Log.d("URLWebview:", "URLWebview:" + merge);
            } else {
                noInternetConnection();
            }
        }
    }


    void initError(View view) {
        llEmpty = (LinearLayout) view.findViewById(R.id.llEmpty1);
        imgError = (ImageView) view.findViewById(R.id.imgError1);
        txtErrorTitle = (MyTextView) view.findViewById(R.id.txtErrorTitle1);
        txtRetry = (MyTextView) view.findViewById(R.id.txtRetry1);
        txtErrorsub = (MyTextView) view.findViewById(R.id.txtErrorsubTitle1);
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!session.getLogin()) {
                    utils.openLogin(getActivity());
                } else {
                    loadURL();
                }
            }
        });
    }

   /* private void openLogin() {
        Intent login = new Intent(getContext(), LoginActivity.class);
        startActivity(login);
        getActivity().finishAffinity();
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }*/

    void noLogin() {
        mProgressDialog.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.GONE);
        materialProgressBar.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.login_title);
        txtErrorTitle.setVisibility(View.GONE);
        txtErrorsub.setText(R.string.login_content);
        txtRetry.setText(R.string.login_retry);
        imgError.setImageResource(R.drawable.ic_baseline_login_24);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtErrorsub.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        rlEcom.setVisibility(View.GONE);
    }


    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
            materialProgressBar.setVisibility(View.VISIBLE);
            materialProgressBar.setProgress(newProgress);
        }
    }

    void noInternetConnection() {
        materialProgressBar.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorsub.setText(R.string.error_content);
        txtErrorsub.setVisibility(View.VISIBLE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);

    }

    void serviceUnavailable() {
        materialProgressBar.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtErrorsub.setVisibility(View.GONE);
    }


    public class MyWebClient extends WebViewClient {
        // long a, b;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //  a = (new Date()).getTime();

            materialProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            materialProgressBar.setVisibility(View.GONE);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //  b = (new Date()).getTime();
            //  long timeToLoad = b - a;
            //  Log.d("timetoload==",": "+timeToLoad);
            materialProgressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            if (Utils.isNetworkAvailable(getActivity()))
                serviceUnavailable();
            else
                noInternetConnection();
        }

    }
}
