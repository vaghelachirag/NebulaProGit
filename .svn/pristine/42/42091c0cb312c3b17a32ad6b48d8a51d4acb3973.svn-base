package com.nebulacompanies.ibo.web;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.net.URISyntaxException;
import java.util.Objects;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static com.nebulacompanies.ibo.util.Config.TESTING_API;

public class EventsFragmentWeb extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    WebView webView;
    String URL = TESTING_API + "/IBO/Event/IndexMobileView?Token=";
    String URL_WITHOUT_TOKEN = TESTING_API + "/IBO/Event/IndexMobileViewV2";
    // String URL_TOUCH = TESTING_API + "/IBO/Event/";
    // String callTouchUrl="";
    Session session;
    String Ssession;
    LinearLayout lnProgressBar;
    // private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;

    MaterialProgressBar materialProgressBar;
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
        View view = inflater.inflate(R.layout.web_activity, container, false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        session = new Session(Objects.requireNonNull(getActivity()));
        utils = new Utils();
        init(view);
        return view;
    }

    String clickUrl = "";

    void init(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        lnProgressBar = view.findViewById(R.id.ln_progressBar);
        materialProgressBar = view.findViewById(R.id.progresbar);
        webView = view.findViewById(R.id.webView);
        rlEcom = view.findViewById(R.id.rl_ecom);
        cd = new ConnectionDetector(getActivity());
        initError(view);

        /*webView.setWebViewClient(getWebViewClient());
         */
        materialProgressBar.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebClient());
        // webView.setWebChromeClient(new WebChromeClient());
       /* webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( URLUtil.isNetworkUrl(url) ) {
                    return false;
                }
                if (appInstalledOrNot(url)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity( intent );
                } else {
                    if (!url.startsWith("http://") && !url.startsWith("https://"))
                        url = "http://" + url;
                    // do something if app is not installed
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
                return true;
            }

        });*/

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        Ssession = session.getAccessToken();
        mSwipeRefreshLayout.setEnabled(false);

        rlEcom.setVisibility(View.VISIBLE);
        rlEcom.setOnClickListener(view1 -> {
            loadURL();
        });

        loadURL();
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void loadURL() {

        if (!session.getLogin()) {
            if (Utils.isNetworkAvailable(getActivity())) {

                webView.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                rlEcom.setVisibility(View.VISIBLE);
                webView.loadUrl(URL_WITHOUT_TOKEN);
                materialProgressBar.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                noInternetConnection();
            }
        } else {
            if (Utils.isNetworkAvailable(getActivity())) {
                String merge = URL + Ssession;//"meet.google.com/gvy-kqkp-qtk";//https://us02web.zoom.us/j/88664387954?pwd=Y3R4ZDVzdDZXUFdwNHZ6ZzlGSHVJdz09";//
                Log.d("URLWebview:", "URLWebview:" + merge);
                webView.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                rlEcom.setVisibility(View.VISIBLE);
                webView.loadUrl(merge);
                materialProgressBar.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
                noInternetConnection();
            }
        }
    }


    void initError(View view) {
        llEmpty = view.findViewById(R.id.llEmpty1);
        imgError = view.findViewById(R.id.imgError1);
        txtErrorTitle = view.findViewById(R.id.txtErrorTitle1);
        txtRetry = view.findViewById(R.id.txtRetry1);
        txtErrorsub = view.findViewById(R.id.txtErrorsubTitle1);
        txtRetry.setOnClickListener(v -> {
            if (!session.getLogin()) {
                utils.openLogin(getActivity());
            } else {
                loadURL();
            }
        });
    }

/*
    void noLogin() {
        txtErrorTitle.setVisibility(View.GONE);
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
    }*/


    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_title);
        txtErrorsub.setText(R.string.error_content);
        txtRetry.setText(R.string.error_retry);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtErrorsub.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.GONE);
        materialProgressBar.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtErrorsub.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.GONE);
        materialProgressBar.setVisibility(View.GONE);
    }

    public class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            materialProgressBar.setVisibility(View.VISIBLE);
            Log.d("URL", "URL: onPageStarted " + url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            lnProgressBar.setVisibility(View.GONE);
            materialProgressBar.setVisibility(View.GONE);
            Log.d("URL", "URL: Override " + url + " : " + URLUtil.isNetworkUrl(url));
            if (url.startsWith(TESTING_API + "/Home/EventRedirector?Id=")) {
                /* if(url.startsWith("http://203.88.139.169:9064/Home/EventRedirector?Id=")){*/
                Log.d("URLStart", "URL: &Extra=1 " + url);
               // url = url.replace(TESTING_API + "/Home/EventRedirector?Id=", "");
               // url = url.startsWith("http") ? url : ("https://" + url);
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            } else {
                view.loadUrl(url);
                Log.d("URLELSE", "URL: &Extra=1 " + url);
                return false;
            }

            /*if (url.endsWith("&Extra=1")) {
                Log.d("URL", "URL: &Extra=1 ");
               // url = url.replace("&Extra=1", "");
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url.replace("&Extra=1", ""))));
                return true;
            } else {
                view.loadUrl(url);
                Log.d("URLELSE", "URL: &Extra=1 "+url);
                return false;
            }*/
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);
            Log.d("URL", "URL: Finish " + url);
            lnProgressBar.setVisibility(View.GONE);
            materialProgressBar.setVisibility(View.INVISIBLE);
            // mProgressDialog.setVisibility(View.GONE);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Log.e("ServerError", "onReceivedError = " + errorCode);
           if (Utils.isNetworkAvailable(getActivity())) {
                serviceUnavailable();
            } else
                noInternetConnection();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
       /* mSwipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (webView.getScrollY() == 0) {
                            mSwipeRefreshLayout.setEnabled(true);
                            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                    loadURL();
                                }
                            });
                        } else {
                            mSwipeRefreshLayout.setEnabled(false);
                        }
                    }
                });*/
    }

    @Override
    public void onStop() {
        //  mSwipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }
}
