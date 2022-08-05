package com.nebulacompanies.ibo.activities;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.RankAndVolumeList;
import com.nebulacompanies.ibo.model.RankAndVolumes;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Config.TESTING_API;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class MyDownlineActivityWeb extends AppCompatActivity {

    String URL = TESTING_API + "/IBO/Sales/MyDownlineSalesMobileView?Token=";
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
    private MyTextView txtErrorTitle, txtRetry;
    ConnectionDetector cd;
    RelativeLayout rlEcom;
    ImageView imgRank;
    MyTextView tvName, tvRank;
    private APIInterface mAPIInterface;
    RankAndVolumeList rankAndVolumeList;
    String rank;
    MyTextViewEcom txtTitle;
    ImageView imgBack;
    RelativeLayout laytoolbar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity_pv);
        session = new Session(this);
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        getRankAndVolumes();
        init();
    }

    void init() {
        // mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipe_refresh_layout );
        laytoolbar = findViewById(R.id.toolbarlay);
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
        //Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        initError();
        Ssession = session.getAccessToken();

        // webView.setWebChromeClient( new MyWebChromeClient() );
        webView.setWebViewClient(new MyWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        String newString = Ssession.replace("bearer ", "");
        String merge = URL + newString;

        if (Utils.isNetworkAvailable(getApplicationContext())) {
            webView.loadUrl(merge);
            Log.d("URLWebview:", "URLWebview:" + merge);
        } else {
            noInternetConnection();
        }

        rlEcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //webView.loadUrl(webView.getUrl());
                webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                webView.loadUrl(merge);
            }
        });
        imgBack.setOnClickListener(v -> onBackPressed());
      /*  mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.
                webView.loadUrl(webView.getUrl());
            }
        });*/
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
            webView.setWebViewClient(new MyWebClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            String newString = Ssession.replace("bearer ", "");
            String merge = URL + newString;

            webView.loadUrl(merge);
            Log.d("URLWebview:", "URLWebview:" + merge);
            webView.loadUrl(merge);
        }
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

        @Override
       /* public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.d("WEB ERROR","Income :: "+error);
        }*/

        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Log.e("ServerError", "onReceivedError = " + errorCode);
            serviceUnavailable();

        }
    }

    void getRankAndVolumes() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
            Call<RankAndVolumes> wsCallingRankAndVolumes = mAPIInterface.getRankAndVolumes();
            wsCallingRankAndVolumes.enqueue(new Callback<RankAndVolumes>() {
                @Override
                public void onResponse(Call<RankAndVolumes> call, Response<RankAndVolumes> response) {

                    if (response.isSuccessful()) {

                        if (response.code() == 200) {

                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                                rankAndVolumeList = response.body().getData();

                                Log.d("RankGET", "RankGET: " + rankAndVolumeList.getCurrentRank());

                                rank = session.getLoginID() + " - " + session.getUserName();
                                // rank = session.getUserName()+" ("+rankAndVolumeList.getCurrentRank()+")";

                                tvName.setText(rank);
                                tvRank.setText("(" + rankAndVolumeList.getCurrentRank() + ")");

                                Glide.with(getApplicationContext())
                                        .load(rankAndVolumeList.getCurrentRankIcon()).fitCenter()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(imgRank);

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<RankAndVolumes> call, Throwable t) {
                }
            });
        }
    }
}
