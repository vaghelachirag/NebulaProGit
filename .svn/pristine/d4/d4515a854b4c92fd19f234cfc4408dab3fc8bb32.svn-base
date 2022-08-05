package com.nebulacompanies.ibo.activities;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import pl.droidsonroids.gif.GifImageView;

//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class SiteProductsWeb extends AppCompatActivity {

    String URL = "https://nebulacompanies.net/Structure/Event/SiteProgress";
    // SwipeRefreshLayout mSwipeRefreshLayout;
    WebView webView;

    Session session;
    String Ssession;
    LinearLayout lnProgressBar;
    MaterialProgressBar materialProgressBar;

    GifImageView mProgressDialog;
    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle,txtErrorContent, txtRetry;
    ConnectionDetector cd;
    RelativeLayout rlEcom;
    ImageView imgRank;
    MyTextView tvName, tvRank;


    MyTextViewEcom txtTitle;
    ImageView imgBack;
    RelativeLayout laytoolbar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_products_web);
        Utils.darkenStatusBar(this,R.color.colorNotification);
        session = new Session(this);


        init();
        loadURL();
    }

    void init() {
        laytoolbar = findViewById(R.id.toolbarlay);
        // mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipe_refresh_layout );
        mProgressDialog = (GifImageView) findViewById(R.id.progressBar);
        lnProgressBar = (LinearLayout) findViewById(R.id.ln_progressBar);
        materialProgressBar = (MaterialProgressBar) findViewById(R.id.progresbar);
        webView = (WebView) findViewById(R.id.webView);
        rlEcom = (RelativeLayout) findViewById(R.id.rl_ecom);
        imgRank = (ImageView) findViewById(R.id.img_rank);
        tvName = (MyTextView) findViewById(R.id.tv_name);
        tvRank = (MyTextView) findViewById(R.id.tv_rank);
        txtTitle = findViewById(R.id.toolbar_title1);
        imgBack = findViewById(R.id.img_back);

        laytoolbar.setVisibility(View.VISIBLE);
        txtTitle.setText("Construction Updates");
        cd = new ConnectionDetector(this);
        //Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        initError();
        Ssession = session.getAccessToken();

        // webView.setWebChromeClient( new MyWebChromeClient() );
        webView.setWebViewClient(new MyWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        rlEcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //webView.loadUrl(webView.getUrl());
                loadURL();
            }
        });
        imgBack.setOnClickListener(v -> onBackPressed());

    }

    private void loadURL() {
        String newString = Ssession.replace("bearer ", "");
        String merge = URL + newString;

        if (Utils.isNetworkAvailable(getApplicationContext())) {
            llEmpty.setVisibility(View.GONE);
            webView.loadUrl(URL);
            Log.d("URLWebview:", "URLWebview:" + merge);
        } else {
            materialProgressBar.setVisibility(View.GONE);
            noInternetConnection();
        }
    }

    void initError() {
        llEmpty = (LinearLayout) findViewById(R.id.llEmpty1);
        imgError = (ImageView) findViewById(R.id.imgError1);
        txtErrorTitle = (MyTextView) findViewById(R.id.txtErrorTitle1);
        txtErrorContent = findViewById(R.id.txtErrorsubTitle1);
        txtRetry = (MyTextView) findViewById(R.id.txtRetry1);
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadURL();
            }
        });
    }

    /*private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            llEmpty.setVisibility(View.GONE);
            webView.setWebViewClient(new MyWebClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            String newString = Ssession.replace("bearer ", "");
            String merge = URL + newString;

            webView.loadUrl(merge);
            Log.d("URLWebview:", "URLWebview:" + merge);


            webView.loadUrl(merge);
        }
    }*/

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setVisibility(View.VISIBLE);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);

        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadURL();
            }
        });
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);

    }

    public class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            materialProgressBar.setVisibility(View.GONE);
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
            materialProgressBar.setVisibility(View.GONE);
            // mSwipeRefreshLayout.setRefreshing(false);
        }

        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Log.e("ServerError", "onReceivedError = " + errorCode);
            serviceUnavailable();

        }
    }
}