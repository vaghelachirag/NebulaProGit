package com.nebulacompanies.ibo.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.DownloadListener;
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

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.RankAndVolumeList;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import pl.droidsonroids.gif.GifImageView;

import static com.nebulacompanies.ibo.util.Config.TESTING_API;


public class DownloadsFragmentWeb extends Fragment {
    String URL = TESTING_API + "/IBO/Explore?Token=";
    String URL_WITHOUT_TOKEN = TESTING_API + "/IBO/ExploreWithoutLogin";
    // SwipeRefreshLayout mSwipeRefreshLayout;ExploreWithoutLogin
    WebView webView;

    Session session;
    String Ssession;
    LinearLayout lnProgressBar;
    MaterialProgressBar materialProgressBar;
    GifImageView mProgressDialog;
    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtErrorsub, txtRetry;
    ConnectionDetector cd;
    RelativeLayout rlEcom;
    ImageView imgRank;
    MyTextView tvName, tvRank;
    private APIInterface mAPIInterface;
    RankAndVolumeList rankAndVolumeList;
    String rank;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    String newString, merge;
    Utils utils;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloads_web, container, false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        session = new Session(getActivity());
        Utils utils = new Utils();
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);

        init(view);

        return view;
    }


    void init(View view) {
        // mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipe_refresh_layout );
        mProgressDialog = (GifImageView) view.findViewById(R.id.progressBar);
        lnProgressBar = (LinearLayout) view.findViewById(R.id.ln_progressBar);
        materialProgressBar = (MaterialProgressBar) view.findViewById(R.id.progresbar);
        webView = (WebView) view.findViewById(R.id.webView);
        rlEcom = (RelativeLayout) view.findViewById(R.id.rl_ecom);
        imgRank = (ImageView) view.findViewById(R.id.img_rank);
        tvName = (MyTextView) view.findViewById(R.id.tv_name);
        tvRank = (MyTextView) view.findViewById(R.id.tv_rank);
        cd = new ConnectionDetector(getActivity());
        initError(view);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        Ssession = session.getAccessToken();


        loadURL();

        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
        rlEcom.setOnClickListener(view1 -> loadURL());

    }

    private void loadURL() {

        if (!session.getLogin()) {
            if (Utils.isNetworkAvailable(getActivity())) {
                webView.setWebViewClient(new MyWebClient());
                webView.loadUrl(URL_WITHOUT_TOKEN);
                webView.setVisibility(View.VISIBLE);
                rlEcom.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                Log.d("URLWebview:", "URLWebview:" + merge);
            } else {
                noInternetConnection();
            }
        } else {
            if (Utils.isNetworkAvailable(getActivity())) {
                webView.setWebViewClient(new MyWebClient());
                merge = URL + Ssession;
                webView.loadUrl(merge);
                webView.setVisibility(View.VISIBLE);
                rlEcom.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
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


    void noLogin() {
        txtErrorTitle.setVisibility(View.GONE);
        mProgressDialog.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.GONE);
        materialProgressBar.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.login_title);
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
        mProgressDialog.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.GONE);
        materialProgressBar.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorsub.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        txtErrorsub.setVisibility(View.VISIBLE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        txtErrorsub.setVisibility(View.GONE);
    }


    public class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
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
            materialProgressBar.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Log.e("ServerError", "onReceivedError = " + errorCode);
            if (Utils.isNetworkAvailable(getActivity()))
                serviceUnavailable();
            else
                noInternetConnection();
        }
    }
}