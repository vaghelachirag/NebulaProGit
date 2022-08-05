package com.nebulacompanies.ibo.activities;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.view.MyTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class PrivacyPolicyActivity extends AppCompatActivity {


    private WebView mWebView;
    LinearLayout llEmpty;
    ImageView imgError;
    MyTextView txtErrorTitle,txtRetry;
    private ProgressBar mProgressBar;
    SwipeRefreshLayout finalMySwipeRefreshLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);


        mWebView = (WebView) findViewById(R.id.web_view);

        //empty layout
        llEmpty = (LinearLayout) findViewById(R.id.llEmpty1);
        imgError = (ImageView) findViewById(R.id.imgError1);
        txtErrorTitle = (MyTextView) findViewById(R.id.txtErrorTitle1);
        txtRetry = (MyTextView) findViewById(R.id.txtRetry1);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://www.nebulacompanies.com/privacy.php");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.clearCache(true);
        mWebView.clearFormData();
        mWebView.clearHistory();
        mWebView.clearSslPreferences();
        mWebView.reload();
        //SwipeRefreshLayout

        finalMySwipeRefreshLayout1 = findViewById(R.id.swiperefresh);
        finalMySwipeRefreshLayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.
                mWebView.loadUrl(mWebView.getUrl());
            }
        });

        // Get the widgets reference from XML layout
        mProgressBar = findViewById(R.id.pb);
        mWebView.setWebViewClient(new WebViewClient() {
                                      @Override
                                      public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                          // Visible the progressbar
                                          mProgressBar.setVisibility(View.VISIBLE);
                                      }

                                      @Override
                                      public void onPageFinished(WebView view, String url) {
                                          finalMySwipeRefreshLayout1.setRefreshing(false);
                                          mProgressBar.setVisibility(View.GONE);
                                          view.clearCache(true);
                                      }

                                      @Override
                                      public void onReceivedError(WebView view, int errorCode,
                                                                  String description, String failingUrl) {
                                          Log.e("ServerError", "onReceivedError = " + errorCode);
                                          serviceUnavailable();

                                      }
                                  }

        );


        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
                // Update the progress bar with page loading progress
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    // Hide the progressbar
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);

    }
}