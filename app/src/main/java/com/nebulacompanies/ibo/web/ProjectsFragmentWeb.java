package com.nebulacompanies.ibo.web;

import android.app.ProgressDialog;
import android.content.Intent;
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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.LoginActivity;
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

public class ProjectsFragmentWeb extends Fragment {

    String URL = TESTING_API + "/IBO/Sales/ProductsMobileView?Token=";
    String URL_WITHOUT_TOKEN = TESTING_API + "/IBO/Sales/ProductsWithoutLoginMobileView";

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
    MyTextView tvName, tvRank;

    Utils utils;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_activity_pv, container, false);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        session = new Session(getActivity());
        utils = new Utils();
        init(view);
        if(session.getLogin())
        getRankAndVolumes();
        return view;
    }


    void init(View view) {
        mProgressDialog = (GifImageView) view.findViewById(R.id.progressBar);
        lnProgressBar = (LinearLayout) view.findViewById(R.id.ln_progressBar);
        materialProgressBar = (MaterialProgressBar) view.findViewById(R.id.progresbar);
        tvName = (MyTextView) view.findViewById(R.id.tv_name);
        tvRank = (MyTextView) view.findViewById(R.id.tv_rank);
        webView = (WebView) view.findViewById(R.id.webView);
        rlEcom = (RelativeLayout) view.findViewById(R.id.rl_ecom);
        cd = new ConnectionDetector(getActivity());
        initError(view);

        Ssession = session.getAccessToken();

        // webView.setWebChromeClient( new MyWebChromeClient() );
        webView.setWebViewClient(new MyWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        rlEcom.setOnClickListener(view1 -> loadURL());
        loadURL();
    }

    private void loadURL() {

        String newString = Ssession.replace("bearer ", "");
        String merge = URL + newString;

        if (!session.getLogin()) {
            if (Utils.isNetworkAvailable(getActivity())) {
                materialProgressBar.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                rlEcom.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl(URL_WITHOUT_TOKEN);
                Log.d("URLWebview:", "URLWebview:" + merge);
            } else {
                noInternetConnection();
            }
        } else {

            if (Utils.isNetworkAvailable(getActivity())) {
                materialProgressBar.setVisibility(View.VISIBLE);
                llEmpty.setVisibility(View.GONE);
                rlEcom.setVisibility(View.VISIBLE);
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl(merge);
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
        txtErrorsub = (MyTextView) view.findViewById(R.id.txtErrorsubTitle1);
        txtRetry = (MyTextView) view.findViewById(R.id.txtRetry1);
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
        materialProgressBar.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorsub.setText(R.string.error_content);
        txtErrorsub.setVisibility(View.VISIBLE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        materialProgressBar.setVisibility(View.GONE);
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
            if (Utils.isNetworkAvailable(getActivity()))
                serviceUnavailable();
            else
                noInternetConnection();
        }
    }

    private APIInterface mAPIInterface;
    RankAndVolumeList rankAndVolumeList;
    String rank;

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

                           /*     Glide.with(getApplicationContext())
                                        .load(rankAndVolumeList.getCurrentRankIcon()).fitCenter()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(imgRank);*/


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
