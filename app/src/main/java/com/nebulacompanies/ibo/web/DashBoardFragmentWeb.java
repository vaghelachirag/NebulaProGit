package com.nebulacompanies.ibo.web;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.RegistrationUpdateKYCWebview;
import com.nebulacompanies.ibo.dwarkaPackage.DwarkaDashBoardActivity;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.GetIboDetails;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.Objects;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Config.TESTING_API;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.SetDateFormat.SetRegistationDateFormat;

public class DashBoardFragmentWeb extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    WebView webView;
    String URL = TESTING_API + "/IBO/Dashboard/IndexMobileView?token=";
    Session session;
    String Ssession;
    LinearLayout lnProgressBar;
    MaterialProgressBar materialProgressBar;
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    GifImageView mProgressDialog;
    private APIInterface mAPIInterface;
    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    ConnectionDetector cd;
    RelativeLayout rlEcom;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_activity, container, false);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        session = new Session(getActivity());
        init(view);
        return view;
    }


    @SuppressLint("SetJavaScriptEnabled")
    void init(View view) {
        mAPIInterface = APIClient.getClient( getActivity() ).create( APIInterface.class );
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mProgressDialog = (GifImageView) view.findViewById(R.id.progressBar);
        lnProgressBar = (LinearLayout) view.findViewById(R.id.ln_progressBar);
        materialProgressBar = (MaterialProgressBar) view.findViewById(R.id.progresbar);
        webView = (WebView) view.findViewById(R.id.webView);
        rlEcom = (RelativeLayout) view.findViewById(R.id.rl_ecom);
        cd = new ConnectionDetector(getActivity());
        //Utils.isNetworkAvailable(getActivity()) = cd.isConnectingToInternet();
        initError(view);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        Ssession = session.getAccessToken();

        if (Utils.isNetworkAvailable(getActivity())) {
            webView.setWebViewClient(getWebViewClient());
            String merge = URL + Ssession;
            Log.d("URLWebview:", "URLWebview:" + merge);
            webView.loadUrl(merge);
        } else {
            noInternetConnection();
        }

        rlEcom.setOnClickListener(view1 -> {
            Intent cp = new Intent(getActivity(), DwarkaDashBoardActivity.class);
            cp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(cp);
        });


        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
            webView.loadUrl(webView.getUrl());
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) Objects.requireNonNull(getActivity()). getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
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
              /*  lnProgressBar.setVisibility(View.VISIBLE);
                mProgressDialog.setVisibility(View.VISIBLE);*/

                materialProgressBar.setVisibility(View.VISIBLE);
                rlEcom.setVisibility(View.GONE);
                view.loadUrl(request.getUrl().toString());
                //view.loadUrl(request.getUrl().toString(), getCustomHeaders());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                /*lnProgressBar.setVisibility(View.VISIBLE);
                mProgressDialog.setVisibility(View.VISIBLE);*/
                materialProgressBar.setVisibility(View.VISIBLE);
                rlEcom.setVisibility(View.GONE);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
                materialProgressBar.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                rlEcom.setVisibility(View.VISIBLE);
                getIboDetails();

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
                            webView.reload();
                        } else {
                            mSwipeRefreshLayout.setEnabled( false );
                        }
                    }
                });
    }

    @Override
    public void onStop() {
        mSwipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }


    private void getIboDetails() {
        if (Utils.isNetworkAvailable(getActivity())) {
            Call<GetIboDetails> wsCallingGetIboDetails = mAPIInterface.getGetIboDetails( session.getLoginID() );
            Log.i( "getIboDetails", "getIboDetails code: " + wsCallingGetIboDetails );
            wsCallingGetIboDetails.enqueue( new Callback<GetIboDetails>() {
                @Override
                public void onResponse(Call<GetIboDetails> call, Response<GetIboDetails> response) {
                    if (response.isSuccessful()) {
                        Log.i( "getIboDetails", "getIboDetails code: " + response );
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                Long date = SetRegistationDateFormat( response.body().getData().getCreatedOn() );
                                long ValiDate = 1555945980000l;
                                if (date > ValiDate) {
                                    //  Toast.makeText(DashboardActivity.this, "Registation not do", Toast.LENGTH_SHORT).show();
                                    if (response.body().getData().getGender().isEmpty() || response.body().getData().getAddress().isEmpty() || response.body().getData().getState().isEmpty() ||
                                            response.body().getData().getCity().isEmpty() || response.body().getData().getPincode().isEmpty() ||
                                            response.body().getData().getAlternateMobile().isEmpty() || response.body().getData().getPanNo().isEmpty() ||
                                            response.body().getData().getAadhaarNo().isEmpty() || response.body().getData().getNomineeName().isEmpty() || response.body().getData().getRelation().isEmpty()
                                            || response.body().getData().getAadhaarNo().isEmpty() || response.body().getData().getAccountHolderName().isEmpty() || response.body().getData().getIFSCNo().isEmpty() ||
                                            response.body().getData().getBranchName().isEmpty() || response.body().getData().getBranchCity().isEmpty() || response.body().getData().getBankId().equals( 0 )) {

                                        //  Intent registations = new Intent(DashboardActivity.this, UpdateProfile.class);
                                       /* Intent registations = new Intent( DashboardActivity.this, RegistrationUpdateKYCWebview.class );
                                        registations.putExtra( "IBOID", session.getLoginID() );
                                        registations.putExtra( "enter", false );
                                        registations.putExtra( "sponsorID", response.body().getData().getSponsorID() );
                                        registations.putExtra( "sponsorName", response.body().getData().getSponsorID() );
                                        registations.putExtra( "placementID", response.body().getData().getPlacementID() );
                                        registations.putExtra( "placementName", response.body().getData().getPlacementName() );
                                        registations.putExtra( "firstName", response.body().getData().getFirstName() );
                                        registations.putExtra( "middleName", response.body().getData().getMiddleName() );
                                        registations.putExtra( "lastName", response.body().getData().getLastName() );
                                        registations.putExtra( "dOB", response.body().getData().getDOB() );
                                        registations.putExtra( "phoneNumber", response.body().getData().getPhoneNumber() );
                                        registations.putExtra( "EmailAddress", response.body().getData().getEmailAddress() );
                                        registations.putExtra( "gender", response.body().getData().getGender() );
                                        registations.putExtra( "address", response.body().getData().getAddress() );
                                        registations.putExtra( "state", response.body().getData().getState() );
                                        registations.putExtra( "city", response.body().getData().getCity() );
                                        registations.putExtra( "pincode", response.body().getData().getPincode() );
                                        registations.putExtra( "alternateMobile", response.body().getData().getAlternateMobile() );
                                        registations.putExtra( "panNo", response.body().getData().getPanNo() );
                                        registations.putExtra( "aadhaarNo", response.body().getData().getAadhaarNo() );
                                        registations.putExtra( "nomineeName", response.body().getData().getNomineeName() );
                                        registations.putExtra( "relation", response.body().getData().getRelation() );
                                        registations.putExtra( "accountNo", response.body().getData().getAccountNo() );
                                        registations.putExtra( "accountHolderName", response.body().getData().getAccountHolderName() );
                                        registations.putExtra( "iFSCNo", response.body().getData().getIFSCNo() );
                                        registations.putExtra( "bankId", response.body().getData().getBankId() );
                                        registations.putExtra( "branchName", response.body().getData().getBranchName() );
                                        registations.putExtra( "branchCity", response.body().getData().getBranchCity() );

                                        startActivity( registations );*/

                                        //profileIncompleteDialog();
                                        showOnClickProfileDialog();
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetIboDetails> call, Throwable t) {
                }
            } );
        }
    }


    private void showOnClickProfileDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder( getActivity());
        View alertView = getLayoutInflater().inflate(R.layout.alert_profile_check, null);
        //Set the view
        alert.setView(alertView);
        alert.setCancelable( true );
        //Show alert
        final AlertDialog alertDialog = alert.show();
        //Can not close the alert by touching outside.
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable( Color.TRANSPARENT));

        MyButtonEcom btnProfile = (MyButtonEcom) alertDialog.findViewById( R.id.btn_profile );

        btnProfile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registations = new Intent( getActivity(), RegistrationUpdateKYCWebview.class );
                startActivity( registations );
                alertDialog.dismiss();
            }
        } );

    }


}
