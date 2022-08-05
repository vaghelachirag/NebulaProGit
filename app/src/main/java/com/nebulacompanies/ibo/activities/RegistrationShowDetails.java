package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import pl.droidsonroids.gif.GifImageView;

import static com.nebulacompanies.ibo.util.Config.TESTING_API;

public class RegistrationShowDetails extends AppCompatActivity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    WebView webView;
    String URL = TESTING_API + "/Structure/Register/IndexMobileView?Token=";
    Session session;
    String Ssession;
    ImageView imgError;
    MyTextView txtErrorTitle,txtRetry;
    LinearLayout lnProgressBar,llEmpty;
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    GifImageView mProgressDialog;
    Button btnNext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_webview);
        session = new Session(this);
        init();
    }


    @SuppressLint("SetJavaScriptEnabled")
    void init(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow =  ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mProgressDialog = (GifImageView) findViewById(R.id.progressBar);
        lnProgressBar = (LinearLayout) findViewById(R.id.ln_progressBar);
        webView = (WebView) findViewById(R.id.webView);
        btnNext = (Button) findViewById(R.id.btnnext);
        llEmpty = (LinearLayout) findViewById(R.id.llEmpty1);
        imgError = (ImageView) findViewById(R.id.imgError1);
        txtErrorTitle = (MyTextView) findViewById(R.id.txtErrorTitle1);
        txtRetry = (MyTextView) findViewById(R.id.txtRetry1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        Ssession = session.getAccessToken();
        webView.loadUrl(URL + Ssession);
        webView.setWebViewClient(getWebViewClient());
        String merge = URL + Ssession;
        Log.d("URLWebview:", "URLWebview:" + merge);
        mSwipeRefreshLayout.setEnabled(false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent documents = new Intent(RegistrationShowDetails.this, UploadDocument.class);
                documents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(documents);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);

    }

    private WebViewClient getWebViewClient() {

        return new WebViewClient() {

            @Override
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                lnProgressBar.setVisibility(View.VISIBLE);
                mProgressDialog.setVisibility(View.VISIBLE);
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                lnProgressBar.setVisibility(View.VISIBLE);
                mProgressDialog.setVisibility(View.VISIBLE);
                view.loadUrl(url);
                //view.loadUrl(url, getCustomHeaders());
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                lnProgressBar.setVisibility(View.GONE);
                mProgressDialog.setVisibility(View.GONE);
                btnNext.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Log.e("ServerError", "onReceivedError = " + errorCode);
                serviceUnavailable();

            }
        };
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onStart() {
        super.onStart();
        webView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                Log.i("TAG", "+++ scrollchanged :-"+ webView.getScrollY());

            }
        });
    }

    @Override
    public void onStop() {
        mSwipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }
}
