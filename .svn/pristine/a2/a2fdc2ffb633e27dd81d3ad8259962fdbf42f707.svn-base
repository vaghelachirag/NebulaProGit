package com.nebulacompanies.ibo.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.BuildConfig;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.AspectRatioImageView;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.BadgeAdapter;
import com.nebulacompanies.ibo.adapter.IncomeListAdapter;
import com.nebulacompanies.ibo.model.IncomeList;
import com.nebulacompanies.ibo.model.IncomeListDetails;
import com.nebulacompanies.ibo.model.IncomeSummary;
import com.nebulacompanies.ibo.model.IncomeSummaryDetails;
import com.nebulacompanies.ibo.model.RowItemInfo;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyBoldTextView;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.walk.FontUtil;
import com.nebulacompanies.ibo.walk.SpotlightView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());
import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Sagar Virvani on 15-02-2018.
 */

public class MyIncomeFragment extends Fragment implements View.OnClickListener /*, AsyncResponse */ {

    SwipeRefreshLayout mSwipeRefreshLayout;
    MyTextView nocurrentBadgeTextView, currentBadgeTextTextView, myIncomeTextView, upcomingBadgeTextView, incomeStartTextView, incomeEndTextView,
            lastPayoutTextView, lastPayoutAmountTextView, lastPayoutDateTextView, lastPayoutDateTextTextView, payoutDetailsTextView, tvId, tvName;
    ImageView currentBadgeImageView, upcomingBadgeImageView, infoBadgeImageView;
    AspectRatioImageView myIncomeShare;
    MyBoldTextView myIncomeTextTextView;
    ProgressBar incomeProgressBar;
    ListView listView;
    ImageView imgIncomeClose;
    List<RowItemInfo> rowItemInfo;
    LinearLayout incomeLinearLayout;
    int index = -1;
    int percent = 0;
    /*Session session;
    String lang;*/
    SharedPreferences.Editor editor;
    //Error View
    private LinearLayout llEmpty, image_share_layout;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry, txtNext;

    ConnectionDetector cd;
    public static final String TAG = "MyIncome";
    Boolean isRefreshed = false;
    private APIInterface mAPIInterface;
    Session session;
    public LinearLayout lnMyIncome, lnPayoutDetails;

    ListView listViewDashBoard;
    private ArrayList<IncomeListDetails> arrayListIncomeList = new ArrayList<>();
    IncomeListAdapter incomeListAdapter;
    int Sum = 0;
    ImageView btnBadgeShare;
    FrameLayout frameLayout;
    Bitmap mbitmap;
    RelativeLayout rlTitle;
    IncomeSummaryDetails incomeSummaryDetails;
    SpotlightView spotLightShare, spotLightBadge;
    SharedPreferences prefs;
    RelativeLayout rlOverlay;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_income, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.my_income));

        init(view);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String salesList = prefs.getString("income", "");
        String salesListNew = prefs.getString("incomeList", "");
        if (salesList != null && salesList != "" &&
                salesListNew != null && salesListNew != "") {
            LoadData();
        } else {
            getIncomeList();
            getIncomeListDashboard();
        }


        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(View view) {
        cd = new ConnectionDetector(getActivity());
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();

        session = new Session(getActivity());
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        initError(view);

        rlTitle = (RelativeLayout) view.findViewById(R.id.rl_title);
        rlOverlay = (RelativeLayout) view.findViewById(R.id.rlOverlay);
        incomeLinearLayout = (LinearLayout) view.findViewById(R.id.income_layout);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.my_income_swipe_refresh_layout);
        //  nocurrentBadgeTextView = (MyTextView) view.findViewById(R.id.no_current_badge);
        currentBadgeTextTextView = (MyTextView) view.findViewById(R.id.current_badge_text);
        myIncomeTextTextView = (MyBoldTextView) view.findViewById(R.id.my_income_text);
        myIncomeTextView = (MyTextView) view.findViewById(R.id.my_income);
        upcomingBadgeTextView = (MyTextView) view.findViewById(R.id.upcoming_badge_text);
        incomeStartTextView = (MyTextView) view.findViewById(R.id.income_start);
        incomeEndTextView = (MyTextView) view.findViewById(R.id.income_end);
        lastPayoutTextView = (MyTextView) view.findViewById(R.id.last_payout);
        lastPayoutAmountTextView = (MyTextView) view.findViewById(R.id.last_payout_amount);
        lastPayoutDateTextView = (MyTextView) view.findViewById(R.id.last_payout_date);
        lastPayoutDateTextTextView = (MyTextView) view.findViewById(R.id.last_payout_date_text);
        image_share_layout = (LinearLayout) view.findViewById(R.id.image_share_layout);
        tvId = (MyTextView) view.findViewById(R.id.tv_id);
        tvName = (MyTextView) view.findViewById(R.id.tv_name);
        // payoutDetailsTextView = (MyTextView) view.findViewById(R.id.payout_details);
        //currentBadgeImageView = (ImageView) view.findViewById(R.id.my_income_badge);
        myIncomeShare = (AspectRatioImageView) view.findViewById(R.id.my_income_share);
        btnBadgeShare = (ImageView) view.findViewById(R.id.btn_badge_share);
        upcomingBadgeImageView = (ImageView) view.findViewById(R.id.upcoming_badge);
        infoBadgeImageView = (ImageView) view.findViewById(R.id.info_badge);
        frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        infoBadgeImageView.setOnClickListener(this);
        incomeProgressBar = (ProgressBar) view.findViewById(R.id.income_progressbar);
        lnMyIncome = (LinearLayout) view.findViewById(R.id.ln_my_income);
        lnPayoutDetails = (LinearLayout) view.findViewById(R.id.ln_payout_details);
        txtNext = (MyTextView) view.findViewById(R.id.tv_next);
        //  payoutDetailsTextView.setOnClickListener(this);
        //ShimmerLayout shimmerText = (ShimmerLayout) view.findViewById(R.id.shimmer_text);
        // shimmerText.startShimmerAnimation();
        btnBadgeShare.setVisibility(View.INVISIBLE);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");
        // registerbtn.setText("show");
        // btnBadgeShare.setTypeface(typeface);
        tvId.setText("ID : " + session.getLoginID());
        tvName.setText("Name : " + session.getUserName());
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
       /* Point p=new Point();
        p.set(200, 1500);
        Bitmap b=waterMark(BitmapFactory.decodeResource(getResources(), R.id.my_income_share),"ID:10005" + System.getProperty("line.separator") + "Name: KK",p, Color.WHITE,1500,200,false);
        myIncomeShare.setImageBitmap(b);*/
      /*  if (Utils.isNetworkAvailable(getApplicationContext())) {
            callAPI();
        } else {
            Toast toast = makeText(getActivity(), R.string.Network_is_not_available, Toast.LENGTH_SHORT);
            toast.show();
        }*/
        listViewDashBoard = (ListView) view.findViewById(R.id.listview_income);
        spotLightShare = new SpotlightView(getActivity());
        spotLightBadge = new SpotlightView(getActivity());


        txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtNext.getText().toString().equals("Got it")) {
                    rlOverlay.setVisibility(View.GONE);
                }
            }
        });
       /* listViewDashBoard.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listViewDashBoard.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(listViewDashBoard.getFirstVisiblePosition() == 0 && listViewDashBoard.getChildAt(0).getTop() == 0);
                }
            }
        });*/

        listViewDashBoard.setOnTouchListener(new ListView.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });


        btnBadgeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btnBadgeShare.isEnabled()) {
                    btnBadgeShare.setEnabled(false);
                    screenShot(frameLayout);
                    // Now do something like…
                    Uri bmpUri = getLocalBitmapUri(myIncomeShare);
                    if (bmpUri != null) {
                        // Construct a ShareIntent with link to image
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                        shareIntent.setType("image/*");
                        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        // Launch sharing dialog for image
                        // startActivity(Intent.createChooser(shareIntent, "Share Rank"));
                        // startActivityForResult(Intent.createChooser(shareIntent, 0));
                        startActivityForResult(Intent.createChooser(shareIntent, "Share Rank"), 0);
                    } else {
                        Toast.makeText(getActivity(), "sharing failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 0) {
            // Make sure the request was successful
            if (resultCode == 0) {

                            // The user pressed ok
                            // Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                            SharedPreferences walkthroughIncome = getActivity().getSharedPreferences(PrefUtils.prefMyincome, 0);
                            if (walkthroughIncome.getBoolean("walkthrough_first_time_my_income", true)) {
                                rlOverlay.setVisibility(View.VISIBLE);
                                walkthroughIncome.edit().putBoolean("walkthrough_first_time_my_income", false).apply();

                }
            } else {
                // The user pressed cancel
                //Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        btnBadgeShare.setEnabled(true);
        btnBadgeShare.setClickable(true);
        // Toast.makeText(getActivity(),"Welcome back",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        //  Toast.makeText(getActivity(),"Welcome back Pause",Toast.LENGTH_SHORT).show();
    }


    private void getIncomeList() {
        if (Utils.isNetworkAvailable(getActivity())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
           /* mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Call<IncomeSummary> wsCallingProjectDetail = mAPIInterface.getIncomeSummary();
            wsCallingProjectDetail.enqueue(new Callback<IncomeSummary>() {
                @Override
                public void onResponse(Call<IncomeSummary> call, Response<IncomeSummary> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                incomeSummaryDetails = response.body().getData();
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                editor = prefs.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(response.body().getData());
                                editor.putString("income", json);
                                editor.apply();
                                DataBind();
                                /*NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
                                String TotalIncome = formatter.format(incomeSummaryDetails.getTotalIncome()).replace("Rs.", getResources().getString(R.string.Rs)).replace(".00", "");
                                String payout = formatter.format(incomeSummaryDetails.getRecentIncome()).replace("Rs.", getResources().getString(R.string.Rs)).replace(".00", "");
                                if (Config.incomeValues[0] > incomeSummaryDetails.getTotalIncome()) {
                                    index = -1;
                                    incomeStartTextView.setText("0");
                                    incomeEndTextView.setText("1L");
                                    percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[0]);
                                } else if (Config.incomeValues[0] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[1]) {
                                    index = 0;
                                    incomeStartTextView.setText("1L");
                                    incomeEndTextView.setText("5L");
                                    percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[1]);
                                    //break;
                                } else if (Config.incomeValues[1] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[2]) {
                                    index = 1;
                                    incomeStartTextView.setText("5L");
                                    incomeEndTextView.setText("10L");
                                    percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[2]);
                                    //break;
                                } else if (Config.incomeValues[2] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[3]) {
                                    index = 2;
                                    incomeStartTextView.setText("10L");
                                    incomeEndTextView.setText("25L");
                                    percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[3]);
                                    //break;
                                } else if (Config.incomeValues[3] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[4]) {
                                    index = 3;
                                    incomeStartTextView.setText("25L");
                                    incomeEndTextView.setText("50L");
                                    percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[4]);
                                    //break;
                                } else if (Config.incomeValues[4] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[5]) {
                                    index = 4;
                                    incomeStartTextView.setText("50L");
                                    incomeEndTextView.setText("1CR");
                                    percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[5]);
                                    //break;
                                } else if (Config.incomeValues[5] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[6]) {
                                    index = 5;
                                    incomeStartTextView.setText("1CR");
                                    incomeEndTextView.setText("2.5CR");
                                    percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[6]);
                                    //break;
                                } else if (Config.incomeValues[6] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[7]) {
                                    index = 6;
                                    incomeStartTextView.setText("2.5CR");
                                    incomeEndTextView.setText("5CR");
                                    percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[7]);
                                    //break;
                                } else if (Config.incomeValues[7] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[8]) {
                                    index = 7;
                                    incomeStartTextView.setText("5CR");
                                    incomeEndTextView.setText("10CR");
                                    percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[8]);
                                    //break;
                                } else if (Config.incomeValues[8] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[9]) {
                                    index = 8;
                                    incomeStartTextView.setText("10CR");
                                    incomeEndTextView.setText("25CR");
                                    percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[9]);
                                    //break;
                                } else if (Config.incomeValues[9] <= incomeSummaryDetails.getTotalIncome()) {
                                    index = 9;
                                    incomeStartTextView.setText("25CR");
                                    percent = 100;
                                    //break;
                                }
                                //}
                                incomeProgressBar.setProgress(percent);
                                Log.i("INFO", "index : " + index);
                                if (index == -1) {
                                    //   currentBadgeImageView.setVisibility(View.GONE);
                                    myIncomeShare.setVisibility(View.GONE);
                                    //    nocurrentBadgeTextView.setVisibility(View.VISIBLE);
                                    //upcomingBadgeImageView.setImageResource(Config.incomeImages[index + 1]);
                                } else {
                                    //   currentBadgeImageView.setVisibility(View.VISIBLE);
                                    myIncomeShare.setVisibility(View.VISIBLE);
                                    // nocurrentBadgeTextView.setVisibility(View.GONE);
                                    // currentBadgeImageView.setImageResource(Config.incomeImages[index]);
                                    myIncomeShare.setImageResource(Config.incomeRankImages[index]);
                                }

                                if (index < 9) {
                                    upcomingBadgeImageView.setImageResource(Config.incomeRankImages[index + 1]);
                                }
                             *//*   lnMyIncome.setVisibility(View.GONE);
                                lnPayoutDetails.setVisibility(View.GONE);*//*
                                myIncomeTextView.setText(TotalIncome);
                                lastPayoutAmountTextView.setText(payout);
                                lastPayoutDateTextTextView.setText(SetGmtTime(incomeSummaryDetails.getRecentIncomeDate()));
*/
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }
                        } else {
                            serviceUnavailable();
                        }
                    } else {
                       /*if (response.code() == 401) {
                           //Redirect to Login Page
                           //doLogout(getActivity(), session);
                       }
                       else if (response.code() == 500) {
                           serviceUnavailable();
                       }*/
                        serviceUnavailable();
                    }

                }

                @Override
                public void onFailure(Call<IncomeSummary> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        } else {
            //noInternetConnection();
        }
    }

    private void DataBind() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String TotalIncome = formatter.format(incomeSummaryDetails.getTotalIncome()).replace("Rs.", getResources().getString(R.string.Rs)).replace(".00", "");
        String payout = formatter.format(incomeSummaryDetails.getRecentIncome()).replace("Rs.", getResources().getString(R.string.Rs)).replace(".00", "");
        if (Config.incomeValues[0] > incomeSummaryDetails.getTotalIncome()) {
            index = -1;
            incomeStartTextView.setText("0");
            incomeEndTextView.setText("1L");
            percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[0]);
        } else if (Config.incomeValues[0] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[1]) {
            index = 0;
            incomeStartTextView.setText("1L");
            incomeEndTextView.setText("5L");
            percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[1]);
            //break;
        } else if (Config.incomeValues[1] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[2]) {
            index = 1;
            incomeStartTextView.setText("5L");
            incomeEndTextView.setText("10L");
            percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[2]);
            //break;
        } else if (Config.incomeValues[2] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[3]) {
            index = 2;
            incomeStartTextView.setText("10L");
            incomeEndTextView.setText("25L");
            percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[3]);
            //break;
        } else if (Config.incomeValues[3] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[4]) {
            index = 3;
            incomeStartTextView.setText("25L");
            incomeEndTextView.setText("50L");
            percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[4]);
            //break;
        } else if (Config.incomeValues[4] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[5]) {
            index = 4;
            incomeStartTextView.setText("50L");
            incomeEndTextView.setText("1CR");
            percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[5]);
            //break;
        } else if (Config.incomeValues[5] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[6]) {
            index = 5;
            incomeStartTextView.setText("1CR");
            incomeEndTextView.setText("2.5CR");
            percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[6]);
            //break;
        } else if (Config.incomeValues[6] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[7]) {
            index = 6;
            incomeStartTextView.setText("2.5CR");
            incomeEndTextView.setText("5CR");
            percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[7]);
            //break;
        } else if (Config.incomeValues[7] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[8]) {
            index = 7;
            incomeStartTextView.setText("5CR");
            incomeEndTextView.setText("10CR");
            percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[8]);
            //break;
        } else if (Config.incomeValues[8] <= incomeSummaryDetails.getTotalIncome() && incomeSummaryDetails.getTotalIncome() < Config.incomeValues[9]) {
            index = 8;
            incomeStartTextView.setText("10CR");
            incomeEndTextView.setText("25CR");
            percent = (int) ((incomeSummaryDetails.getTotalIncome() * 100.0f) / Config.incomeValues[9]);
            //break;
        } else if (Config.incomeValues[9] <= incomeSummaryDetails.getTotalIncome()) {
            index = 9;
            incomeStartTextView.setText("25CR");
            percent = 100;
            //break;
        }
        //}
        incomeProgressBar.setProgress(percent);
        Log.i("INFO", "index : " + index);
        if (index == -1) {
            //   currentBadgeImageView.setVisibility(View.GONE);
            myIncomeShare.setVisibility(View.GONE);
            //    nocurrentBadgeTextView.setVisibility(View.VISIBLE);
            //upcomingBadgeImageView.setImageResource(Config.incomeImages[index + 1]);
        } else {
            //   currentBadgeImageView.setVisibility(View.VISIBLE);
            myIncomeShare.setVisibility(View.VISIBLE);
            // nocurrentBadgeTextView.setVisibility(View.GONE);
            // currentBadgeImageView.setImageResource(Config.incomeImages[index]);
            myIncomeShare.setImageResource(Config.incomeRankImages[index]);
        }

        if (index < 9) {
            upcomingBadgeImageView.setImageResource(Config.incomeRankImages[index + 1]);
        }
        if (index == -1) {
            image_share_layout.setVisibility(View.GONE);
        }

        myIncomeTextView.setText(TotalIncome);
        lastPayoutAmountTextView.setText(payout);
        lastPayoutDateTextTextView.setText(SetGmtTime(incomeSummaryDetails.getRecentIncomeDate()));
    }

    private void getIncomeListDashboard() {
        if (Utils.isNetworkAvailable(getActivity())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            //mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Call<IncomeList> wsCallingIncomes = mAPIInterface.getIncomeList();
            wsCallingIncomes.enqueue(new Callback<IncomeList>() {
                @Override
                public void onResponse(Call<IncomeList> call, Response<IncomeList> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {

                        arrayListIncomeList.clear();
                        if (response.code() == 200) {

                            if (response.body().getStatuscode() == REQUEST_STATUS_CODE_SUCCESS) {
                                arrayListIncomeList.addAll(response.body().getData());
                                prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                editor = prefs.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(arrayListIncomeList);
                                editor.putString("incomeList", json);
                                editor.apply();
                                incomeListAdapter = new IncomeListAdapter(getActivity(), arrayListIncomeList);
                                listViewDashBoard.setAdapter(incomeListAdapter);
                                incomeListAdapter.notifyDataSetChanged();
                                lnMyIncome.setVisibility(View.VISIBLE);
                                lnPayoutDetails.setVisibility(View.VISIBLE);
                                btnBadgeShare.setVisibility(View.VISIBLE);
                                incomeLinearLayout.setVisibility(View.VISIBLE);


                                SharedPreferences walkthroughProduct = getActivity().getSharedPreferences(PrefUtils.prefMyincomebadge, 0);

                                if (walkthroughProduct.getBoolean("walkthrough_first_time_badge", true)) {

                                    spotLightBadge = new SpotlightView.Builder(getActivity())
                                            .introAnimationDuration(400)
                                            .enableRevealAnimation(true)
                                            .performClick(true)
                                            .fadeinTextDuration(400)
                                            .setTypeface(FontUtil.get(getActivity(), "fonts/Montserrat-Regular.ttf"))
                                            .headingTvColor(Color.parseColor("#eb273f"))
                                            .headingTvSize(18)
                                            .headingTvText("Income Badges")
                                            .subHeadingTvColor(Color.parseColor("#ffffff"))
                                            .subHeadingTvSize(16)
                                            .subHeadingTvText("Click to view the available income badges.")
                                            .maskColor(Color.parseColor("#dc000000"))
                                            .target(infoBadgeImageView) // btnBadgeShare
                                            .lineAnimDuration(400)
                                            .lineAndArcColor(Color.parseColor("#eb273f"))
                                            .dismissOnTouch(false)
                                            .dismissOnBackPress(false)
                                            /*.setListener(new SpotlightListener() {
                                                @Override
                                                public void onUserClicked(String s) {
                                                    onBackclicked();
                                                }
                                            })*/
                                            .enableDismissAfterShown(false)
                                            .show();
                                }
                                walkthroughProduct.edit().putBoolean("walkthrough_first_time_badge", false).apply();


                            } else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatuscode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatuscode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }
                        } else {
                            serviceUnavailable();
                        }
                    } else {
                        /*if (response.code() == 401) {
                            //Redirect to Login Page
                            //doLogout(IncomeDashboard.this, session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<IncomeList> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        } else {
            //noInternetConnection();
            LoadData();

        }
    }


    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            //callAPI();
            getIncomeList();
            getIncomeListDashboard();
            mSwipeRefreshLayout.setRefreshing(true);
            showRecords();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
           /* Toast toast1 = Toast.makeText(getActivity(), R.string.Network_is_not_available, Toast.LENGTH_SHORT);
            toast1.show();*/
            LoadData();
        }
    }

   /* public void callAPI() {
        if(session != null) {
            MY_INCOME_API = Config.NEB_API + "/API/Income/GetMyTotalIncome?MemberTotalIncome=10005" ;
            asyncComplex = new AsyncComplex(getActivity(), MY_INCOME_API, "MY_INCOME", isRefreshed);
            asyncComplex.asyncResponse = this;
            StartAsyncTaskInParallel(asyncComplex);
        }
    }

    @Override
    public void processFinish(String output, String Tag) {
        if(output == null) {
            output = "THERE WAS AN ERROR";
        }

        String[] data, values;
        String response = output.toString();
        Log.i("INFO", "response :" + response);


        *//*try {
            JSONObject jsonObj = new JSONObject(response);
            JSONArray arrJson= jsonObj.getJSONArray("Data");
            String[] arr=new String[arrJson.length()];
            for(int i=0;i<arrJson.length();i++) {
                arr[i] = arrJson.getString(i);
                Log.i("INFO", "arr[i] :" + arr[i]);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }*//*

        data = response.split("Data");
        values = data[1].replace(":", "").replace("\"", "").replace("}", "").split(",");

        if(Tag.equals("MY_INCOME") && !response.contains("No Data Found")){
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
            //lastPayoutAmountTextView.append(" " + values[2]);
            String payout = formatter.format(Double.parseDouble(values[2])).replace("Rs.", getResources().getString(R.string.Rs)).replace(".00", "");
            lastPayoutAmountTextView.setText(payout);
            lastPayoutDateTextTextView.setText(values[1]);

            Log.i("INFO", "Date :" + values[1]);
            Log.i("INFO", "Amount :" + values[2]);

            Double val = Double.parseDouble(values[0]);
            Log.i("INFO", "val :" + val);

            //String s = NumberFormat.getNumberInstance((new Locale("en", "IN"))).format(val);
            //myIncomeTextView.append("  " + String.valueOf(s));
            //myIncomeTextView.setText(getResources().getString(R.string.Rs)+ "  " + String.valueOf(s));

            String moneyString = formatter.format(val).replace("Rs.", getResources().getString(R.string.Rs)).replace(".00", "");
            Log.i("INFO", "moneyString : " + moneyString);
            myIncomeTextView.setText(moneyString);

            //for (int i=0; i < Config.incomeValues.length; i++) {
            Log.i("INFO", "Config.incomeValues[0] : " + Config.incomeValues[0]);
            Log.i("INFO", "val : " + val);

            if(Config.incomeValues[0] > val){
                index = -1;
                incomeStartTextView.setText("0");
                incomeEndTextView.setText("1L");
                percent = (int) ((val * 100.0f) / Config.incomeValues[0]);
            }
            else if (Config.incomeValues[0] <= val && val < Config.incomeValues[1]) {
                index = 0;
                incomeStartTextView.setText("1L");
                incomeEndTextView.setText("5L");
                percent = (int) ((val * 100.0f) / Config.incomeValues[1]);
                //break;
            }
            else if(Config.incomeValues[1] <= val && val < Config.incomeValues[2]){
                index = 1;
                incomeStartTextView.setText("5L");
                incomeEndTextView.setText("10L");
                percent = (int) ((val * 100.0f) / Config.incomeValues[2]);
                //break;
            }
            else if(Config.incomeValues[2] <= val && val < Config.incomeValues[3]){
                index = 2;
                incomeStartTextView.setText("10L");
                incomeEndTextView.setText("25L");
                percent = (int) ((val * 100.0f) / Config.incomeValues[3]);
                //break;
            }
            else if(Config.incomeValues[3] <= val && val < Config.incomeValues[4]){
                index = 3;
                incomeStartTextView.setText("25L");
                incomeEndTextView.setText("50L");
                percent = (int) ((val * 100.0f) / Config.incomeValues[4]);
                //break;
            }
            else if(Config.incomeValues[4] <= val && val < Config.incomeValues[5]){
                index = 4;
                incomeStartTextView.setText("50L");
                incomeEndTextView.setText("1CR");
                percent = (int) ((val * 100.0f) / Config.incomeValues[5]);
                //break;
            }
            else if(Config.incomeValues[5] <= val && val < Config.incomeValues[6]){
                index = 5;
                incomeStartTextView.setText("1CR");
                incomeEndTextView.setText("2.5CR");
                percent = (int) ((val * 100.0f) / Config.incomeValues[6]);
                //break;
            }
            else if(Config.incomeValues[6] <= val && val < Config.incomeValues[7]){
                index = 6;
                incomeStartTextView.setText("2.5CR");
                incomeEndTextView.setText("5CR");
                percent = (int) ((val * 100.0f) / Config.incomeValues[7]);
                //break;
            }
            else if(Config.incomeValues[7] <= val && val < Config.incomeValues[8]){
                index = 7;
                incomeStartTextView.setText("5CR");
                incomeEndTextView.setText("10CR");
                percent = (int) ((val * 100.0f) / Config.incomeValues[8]);
                //break;
            }
            else if(Config.incomeValues[8] <= val && val < Config.incomeValues[9]){
                index = 8;
                incomeStartTextView.setText("10CR");
                incomeEndTextView.setText("25CR");
                percent = (int) ((val * 100.0f) / Config.incomeValues[9]);
                //break;
            }
            else if(Config.incomeValues[9] <= val){
                index = 9;
                incomeStartTextView.setText("25CR");
                percent = 100;
                //break;
            }
            //}
            incomeProgressBar.setProgress(percent);
            Log.i("INFO", "index : " + index);
            if(index == -1){
                currentBadgeImageView.setVisibility(View.GONE);
                nocurrentBadgeTextView.setVisibility(View.VISIBLE);
                //upcomingBadgeImageView.setImageResource(Config.incomeImages[index + 1]);
            }else {
                currentBadgeImageView.setVisibility(View.VISIBLE);
                nocurrentBadgeTextView.setVisibility(View.GONE);
                currentBadgeImageView.setImageResource(Config.incomeImages[index]);
            }

            if (index < 9) {
                upcomingBadgeImageView.setImageResource(Config.incomeImages[index + 1]);
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
        else if (response.contains("Please reload")) {
            Toast toast1 = Toast.makeText(getActivity(), "Please reload..!", Toast.LENGTH_SHORT);
            toast1.show();
        }
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
           /* case R.id.payout_details:
                Intent a = new Intent(getActivity(), IncomeDashboard.class);
                startActivity(a);
                break;*/

            case R.id.info_badge:
                showInfo();
                break;
        }
    }

    private void showInfo() {
        LayoutInflater mInflater1 = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Dialog dialog1 = new Dialog(getActivity());
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationFade;
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View convertView1 = mInflater1.inflate(R.layout.list_info_my_income, null);
        dialog1.setContentView(convertView1);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //lp.height = 1150;
        lp.gravity = Gravity.CENTER;

        dialog1.getWindow().setAttributes(lp);

        listView = (ListView) convertView1.findViewById(R.id.myincome_show_list);
        imgIncomeClose = (ImageView) convertView1.findViewById(R.id.img_income_close);
        rowItemInfo = new ArrayList<RowItemInfo>();
        for (int i = 0; i < Config.incomeValues.length; i++) {
            RowItemInfo item = new RowItemInfo(Config.incomeImages[i], Config.incomeValuesRs[i], Config.incomeValues1[i]);
            rowItemInfo.add(item);
        }
        BadgeAdapter adapter = new BadgeAdapter(getActivity(), rowItemInfo, index + 1);
        listView.setAdapter(adapter);
        //adapter.setSelectedItem(Config.Index + 1);
        imgIncomeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();

                SharedPreferences walkthroughProduct = getActivity().getSharedPreferences(PrefUtils.prefRankshare, 0);

                if (walkthroughProduct.getBoolean("walkthrough_first_time_rank_share", true)) {
                    if (index == -1) {
                        image_share_layout.setVisibility(View.GONE);
                    } else {
                        spotLightShare = new SpotlightView.Builder(getActivity())
                                .introAnimationDuration(400)
                                .enableRevealAnimation(true)
                                .performClick(true)
                                .fadeinTextDuration(400)
                                .setTypeface(FontUtil.get(getActivity(), "fonts/Montserrat-Regular.ttf"))
                                .headingTvColor(Color.parseColor("#eb273f"))
                                .headingTvSize(18)
                                .headingTvText("Share")
                                .subHeadingTvColor(Color.parseColor("#ffffff"))
                                .subHeadingTvSize(14)
                                .subHeadingTvText("Share your income badge on social media.")
                                .maskColor(Color.parseColor("#dc000000"))
                                .target(btnBadgeShare)
                                .lineAnimDuration(400)
                                .lineAndArcColor(Color.parseColor("#eb273f"))
                                .dismissOnTouch(false)
                                .dismissOnBackPress(false)
                                .enableDismissAfterShown(false)
                                .show();
                    }
                }
                walkthroughProduct.edit().putBoolean("walkthrough_first_time_rank_share", false).apply();

            }

        });
        dialog1.show();
        //listView.setSelection(index + 1);
    }



   /* @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(AsyncComplex task) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }*/

    void initError(View view) {
        llEmpty = (LinearLayout) view.findViewById(R.id.llEmpty);
        imgError = (ImageView) view.findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView) view.findViewById(R.id.txtErrorTitle);
        txtRetry = (MyTextView) view.findViewById(R.id.txtRetry);
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        });
    }

    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        incomeLinearLayout.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        incomeLinearLayout.setVisibility(View.GONE);

    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        incomeLinearLayout.setVisibility(View.GONE);
    }

    void showRecords() {
        //txtErrorTitle.setText(R.string.error_msg_network);
        //imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.GONE);
        txtRetry.setVisibility(View.GONE);
        incomeLinearLayout.setVisibility(View.VISIBLE);
    }

    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {


            File file = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            if (Build.VERSION.SDK_INT > 24) {
                bmpUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", file);
            } else {
                bmpUri = Uri.fromFile(file);
            }
            Log.i("INFO", "Call for bmpUri:-" + bmpUri);
            //bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    public void screenShot(View view) {
        // mbitmap = getBitmapOFRootView(captureScreenShot);
        mbitmap = getBitmapOFRootView(btnBadgeShare);
        myIncomeShare.setImageBitmap(mbitmap);
        createImage(mbitmap);

    }

    public Bitmap getBitmapOFRootView(View v) {

       /* View rootview = v.getRootView();
        rlTitle.setVisibility(View.GONE);
        btnBadgeShare.setVisibility(View.GONE);
        myIncomeTextTextView.setVisibility(View.GONE);
        myIncomeTextView.setVisibility(View.GONE);
        lnPayoutDetails.setVisibility(View.GONE);

        rootview.setDrawingCacheEnabled(true);
        Bitmap bitmap1 = rootview.getDrawingCache();*/
        frameLayout.setDrawingCacheEnabled(true);
        Bitmap bitmap1 = frameLayout.getDrawingCache();
        return bitmap1;
    }


    public void createImage(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        File file = new File(Environment.getExternalStorageDirectory() +
                "/capturedscreenandroid.jpg");
        try {
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes.toByteArray());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void LoadData() {
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = prefs.getString("income", null);
        incomeSummaryDetails = gson.fromJson(json, IncomeSummaryDetails.class);
        String jsonIncomeList = prefs.getString("incomeList", null);
        Type type = new TypeToken<ArrayList<IncomeListDetails>>() {
        }.getType();
        arrayListIncomeList = gson.fromJson(jsonIncomeList, type);
        if (json != null && jsonIncomeList != null) {
            DataBind();
            incomeListAdapter = new IncomeListAdapter(getActivity(), arrayListIncomeList);
            listViewDashBoard.setAdapter(incomeListAdapter);
            incomeListAdapter.notifyDataSetChanged();
            incomeLinearLayout.setVisibility(View.VISIBLE);
            lnMyIncome.setVisibility(View.VISIBLE);
            lnPayoutDetails.setVisibility(View.VISIBLE);
            btnBadgeShare.setVisibility(View.VISIBLE);
            incomeLinearLayout.setVisibility(View.VISIBLE);
        } else {
            lnMyIncome.setVisibility(View.VISIBLE);
            noInternetConnection();
        }
    }

    public void hideSpotLight() {
        spotLightBadge.setVisibility(View.GONE);
        spotLightShare.setVisibility(View.GONE);
    }
}
