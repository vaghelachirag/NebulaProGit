package com.nebulacompanies.ibo.web;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.MainActivity;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import pl.droidsonroids.gif.GifImageView;

import static com.nebulacompanies.ibo.util.Config.TESTING_API;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class PrivacyFragmentWeb extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    WebView webView;
    String URL = TESTING_API + "/IBO/Event/IndexMobileView?Token=";
    Session session;
    String Ssession;
    LinearLayout lnProgressBar;
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    GifImageView mProgressDialog;
    MaterialProgressBar materialProgressBar;
    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    ConnectionDetector cd;
    RelativeLayout rlEcom;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_activity, container, false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        session = new Session(getActivity());
        init(view);
        return view;
    }


    void init(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mProgressDialog = (GifImageView) view.findViewById(R.id.progressBar);
        lnProgressBar = (LinearLayout) view.findViewById(R.id.ln_progressBar);
        materialProgressBar = (MaterialProgressBar) view.findViewById(R.id.progresbar);
        webView = (WebView) view.findViewById(R.id.webView);
        rlEcom = (RelativeLayout) view.findViewById(R.id.rl_ecom);
        cd = new ConnectionDetector(getActivity());
        //Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        initError(view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        Ssession = session.getAccessToken();

        if (Utils.isNetworkAvailable(getActivity())) {
            webView.loadUrl(URL + Ssession);

            webView.setWebViewClient(getWebViewClient());
            String merge = URL + Ssession;
            Log.d("URLWebview:", "URLWebview:" + merge);

        } else {
            noInternetConnection();
        }

        rlEcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cp = new Intent(getActivity(), MainActivity.class);
                cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(cp);
                //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }


    void initError(View view) {
        llEmpty = (LinearLayout) view.findViewById(R.id.llEmpty1);
        imgError = (ImageView) view.findViewById(R.id.imgError1);
        txtErrorTitle = (MyTextView) view.findViewById(R.id.txtErrorTitle1);
        txtRetry = (MyTextView) view.findViewById(R.id.txtRetry1);
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        });
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            llEmpty.setVisibility(View.GONE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(URL + session.getAccessToken());
            webView.setWebViewClient(getWebViewClient());
            mSwipeRefreshLayout.setRefreshing(false);

        } else {
            mSwipeRefreshLayout.setRefreshing(false);

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
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                lnProgressBar.setVisibility(View.GONE);
                materialProgressBar.setVisibility(View.GONE);
                mProgressDialog.setVisibility(View.GONE);
                super.onPageFinished(view, url);
                Log.d("URL", "URL: " + url);
                view.clearCache(true);

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
}
