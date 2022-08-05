package com.nebulacompanies.ibo.web;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
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

import com.nebulacompanies.ibo.R;
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

public class MyPVDashWeb extends Fragment {


    String URL = TESTING_API + "/IBO/Dashboard/PVTreeMobileView?Token=";
  //  SwipeRefreshLayout mSwipeRefreshLayout;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.web_activity_pv, container, false );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
        }
        session = new Session( getActivity() );
        init( view );


        return view;
    }


    void init(View view) {
       // mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipe_refresh_layout );
        mProgressDialog = (GifImageView) view.findViewById( R.id.progressBar );
        lnProgressBar = (LinearLayout) view.findViewById( R.id.ln_progressBar );
        materialProgressBar = (MaterialProgressBar) view.findViewById( R.id.progresbar );
        webView = (WebView) view.findViewById( R.id.webView );
        rlEcom = (RelativeLayout) view.findViewById( R.id.rl_ecom );
        cd = new ConnectionDetector( getActivity() );
        //Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        initError( view );

        Ssession = session.getAccessToken();

        // webView.setWebChromeClient( new MyWebChromeClient() );
        webView.setWebViewClient( new MyWebClient() );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );

        String newString = Ssession.replace( "bearer ", "" );
        String merge = URL + newString;


        if (Utils.isNetworkAvailable(getActivity())) {
            webView.loadUrl( merge );
            Log.d( "URLWebview:", "URLWebview:" + merge );
        } else {
            noInternetConnection();
        }
       /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.loadUrl(webView.getUrl());
            }
        });*/

        rlEcom.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //webView.loadUrl(webView.getUrl());
                webView.getSettings().setCacheMode( WebSettings.LOAD_NO_CACHE);
                webView.loadUrl( merge );
            }
        } );
    }


    void initError(View view) {
        llEmpty = (LinearLayout) view.findViewById( R.id.llEmpty1 );
        imgError = (ImageView) view.findViewById( R.id.imgError1 );
        txtErrorTitle = (MyTextView) view.findViewById( R.id.txtErrorTitle1 );
        txtRetry = (MyTextView) view.findViewById( R.id.txtRetry1 );
        txtRetry.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        } );
    }

    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
            materialProgressBar.setVisibility( View.VISIBLE );
            materialProgressBar.setProgress( newProgress );
        }
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            llEmpty.setVisibility( View.GONE );
            webView.setWebViewClient( new MyWebClient() );
            webView.getSettings().setJavaScriptEnabled( true );
            webView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );

            String newString = Ssession.replace( "bearer ", "" );
            String merge = URL + newString;

            webView.loadUrl( merge );
            Log.d( "URLWebview:", "URLWebview:" + merge );


            webView.loadUrl( merge );
        }
    }

    void noInternetConnection() {
        txtErrorTitle.setText( R.string.error_msg_network );
        imgError.setImageResource( R.drawable.ic_cloud_off_black_24dp );
        llEmpty.setVisibility( View.VISIBLE );
        txtRetry.setVisibility( View.VISIBLE );

        txtRetry.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        } );
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);

    }
    /*@Override
    public void onStart() {
        super.onStart();
        mSwipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener( mOnScrollChangedListener =
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (webView.getScrollY() == 0) {
                            mSwipeRefreshLayout.setEnabled(true);
                            webView.reload();
                        } else {
                            mSwipeRefreshLayout.setEnabled( false );
                        }
                    }
                } );
    }

    @Override
    public void onStop() {
        mSwipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener( mOnScrollChangedListener );
        super.onStop();
    }*/


    public class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted( view, url, favicon );
            materialProgressBar.setVisibility( View.GONE );
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl( url );
            materialProgressBar.setVisibility( View.GONE );
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished( view, url );
            materialProgressBar.setVisibility( View.GONE );
          //  mSwipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Log.e("ServerError", "onReceivedError = " + errorCode);
            serviceUnavailable();

        }

    }
}