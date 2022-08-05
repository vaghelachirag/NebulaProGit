package com.nebulacompanies.ibo.web;

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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
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

public class MyIncomeFragmentWeb extends Fragment {


    //String URL = TESTING_API + "/IBO/Income/IndexMobileView?Token=";
    String URL = TESTING_API + "/IBO/Income/IndexMobileView?Token=";
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_activity_pv, container, false);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        session = new Session(getActivity());
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        getRankAndVolumes();
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
        //Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        initError(view);
        Ssession = session.getAccessToken();

        // webView.setWebChromeClient( new MyWebChromeClient() );
        webView.setWebViewClient(new MyWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        String newString = Ssession.replace("bearer ", "");
        String merge = URL + newString;


            if (Utils.isNetworkAvailable(getActivity())) {

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
      /*  mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.
                webView.loadUrl(webView.getUrl());
            }
        });*/
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

    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
            materialProgressBar.setVisibility(View.VISIBLE);
            materialProgressBar.setProgress(newProgress);
        }
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            llEmpty.setVisibility(View.GONE);
            webView.setWebViewClient(new MyWebClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            String newString = Ssession.replace("bearer ", "");
            String merge = URL + newString;

            webView.loadUrl(merge);
            //Log.d("URLWebview:", "URLWebview:" + merge);


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

   /* @Override
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
            super.onPageStarted(view, url, favicon);
            materialProgressBar.setVisibility(View.GONE);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith("/IBOPayoutShare/10005.jpg")) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            } else
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
        if (Utils.isNetworkAvailable(getActivity())) {
            mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
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

                                Glide.with(getActivity())
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