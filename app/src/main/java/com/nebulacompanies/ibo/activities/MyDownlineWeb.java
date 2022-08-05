package com.nebulacompanies.ibo.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import pl.droidsonroids.gif.GifImageView;

import static com.nebulacompanies.ibo.util.Config.TESTING_API;

public class MyDownlineWeb extends AppCompatActivity {

    String URL = TESTING_API + "/Structure/Genealogy/TabularMobileView?Id=Placement&Token=";
    WebView webView;
    Session session;
    String Ssession;
    LinearLayout lnProgressBar;
    MaterialProgressBar materialProgressBar;
    GifImageView mProgressDialog;
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtSub, txtRetry;
    ConnectionDetector cd;
    RelativeLayout rlEcom;
    ImageView imgRank;
    MyTextView tvName, tvRank;
    MyTextViewEcom txtTitle;
    ImageView imgBack;
    RelativeLayout laytoolbar;
    Utils utils;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_pv);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        session = new Session(this);
        utils = new Utils();
        init();
        loadUrl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        utils.checkExpireUser(APIClient.getClient(this).create(APIInterface.class), this, session);
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
        txtTitle.setText("My Downline");
        cd = new ConnectionDetector(this);
        initError();
        Ssession = session.getAccessToken();

        webView.setWebViewClient(new MyWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        rlEcom.setOnClickListener(view -> loadUrl());
        imgBack.setOnClickListener(v -> onBackPressed());
    }

    private void loadUrl() {
        String newString = Ssession.replace("bearer ", "");
        String merge = URL + newString;

        if (!session.getLogin()) {
            mProgressDialog.setVisibility(View.GONE);
            lnProgressBar.setVisibility(View.GONE);
            materialProgressBar.setVisibility(View.GONE);
            utils.noLoginDailog(this);

        } else {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                llEmpty.setVisibility(View.GONE);
                txtRetry.setVisibility(View.GONE);
                rlEcom.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl(merge);
                Log.d("URLWebview:", "URLWebview:" + merge);
            } else {
                noInternetConnection();
            }
        }
    }


    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
            materialProgressBar.setVisibility(View.VISIBLE);
            materialProgressBar.setProgress(newProgress);
        }
    }

    void initError() {
        llEmpty = (LinearLayout) findViewById(R.id.llEmpty1);
        imgError = (ImageView) findViewById(R.id.imgError1);
        txtErrorTitle = (MyTextView) findViewById(R.id.txtErrorTitle1);
        txtSub = (MyTextView) findViewById(R.id.txtErrorsubTitle1);
        txtRetry = (MyTextView) findViewById(R.id.txtRetry1);
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!session.getLogin()) {
                    utils.openLogin(MyDownlineWeb.this);
                    //openLogin();
                } else {
                    loadUrl();
                }
            }
        });
    }

    /*private void openLogin() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finishAffinity();
        this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }*/


    void noLogin() {
        mProgressDialog.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.GONE);
        materialProgressBar.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.login_title);
        txtSub.setText(R.string.login_content);
        txtRetry.setText(R.string.login_retry);
        imgError.setImageResource(R.drawable.ic_baseline_login_24);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtSub.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        rlEcom.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        mProgressDialog.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.GONE);
        materialProgressBar.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_msg_network);
        txtSub.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtSub.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        mProgressDialog.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.GONE);
        materialProgressBar.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtSub.setVisibility(View.GONE);
        txtRetry.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
    }

    public class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            materialProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            materialProgressBar.setVisibility(View.GONE);
            if (url.contains("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } else {
                view.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            materialProgressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.e("ServerError", "onReceivedError = " + errorCode);
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                serviceUnavailable();
            } else {
                noInternetConnection();
            }
        }
    }
}
