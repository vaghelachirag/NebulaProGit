package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Base2Activity;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.SiteProductsAdapter;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.SiteProductList;
import com.nebulacompanies.ibo.model.SiteProductModel;
import com.nebulacompanies.ibo.view.MyTextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

/**
 * Created by Palak Mehta on 8/6/2016.
 */

public class SiteProducts extends Base2Activity  /*implements AdapterView.OnItemClickListener*/ {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView listView;
    private APIInterface mAPIInterface;
    public static ArrayList<SiteProductList> arrayListSiteProducts = new ArrayList<>();
    public static final String TAG = "SiteProducts";
    SiteProductsAdapter siteProductsAdapter;
    Boolean isRefreshed = false;
    LinearLayout imageLinearLayout;
    ProgressDialog mProgressDialog;
    private int type;
    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    String prokey;
    Toolbar toolbar;

    SharedPreferences prefs;
    RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences prefsFirstTimeSiteProgress;
    boolean isBackMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site_progress);
        prefsFirstTimeSiteProgress = getSharedPreferences(PrefUtils.prefScrollsite, MODE_PRIVATE);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            prokey = b.getString("keyPro");
            isBackMain = b.getBoolean("siteProgressBack123");
        }

        setActionBar();
        init();
        setupWindowAnimations();
        prefs = PreferenceManager.getDefaultSharedPreferences(SiteProducts.this);

        SharedPreferences skipMainGet = getSharedPreferences(PrefUtils.prefSkipmain, 0);
        boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
        if (!isSkipMain) {
            disableScroll();
        }


        String projectList = prefs.getString("site_list", "");
        if (projectList != null && projectList != "") {
            LoadSiteProductData();
        } else {
            getSiteProductList();
        }
    }

    void setActionBar() {
        toolbar = (Toolbar) findViewById(R.id.product_toolbar);
        setSupportActionBar(toolbar);
        //if(getActionBar() != null) {
        getSupportActionBar().setTitle("");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void init() {

        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);


        initError();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.site_progress_swipe_refresh_layout);


        listView = (RecyclerView) findViewById(R.id.site_progress_recyclerview);

       /* if (isBackMain) {
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    return false;
                }
            });
            mSwipeRefreshLayout.setEnabled(true);
        }*/


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }

        });

        // listView.setOnItemClickListener(this);
        imageLinearLayout = (LinearLayout)

                findViewById(R.id.site_progress_image_icon);
        imageLinearLayout.setBackgroundResource(R.drawable.site_progress_page_design);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }

        DividerItemDecoration myDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_recyclerview));
        listView.addItemDecoration(myDivider);

    }


    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            if (siteProductsAdapter != null) {
                siteProductsAdapter.clearData();
                siteProductsAdapter.notifyDataSetChanged();
            }

            getSiteProductList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            // noInternetConnection();
            LoadSiteProductData();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getSiteProductList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mProgressDialog = new ProgressDialog(this, R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);

            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Call<SiteProductModel> wsCallingSiteProducts = mAPIInterface.getSiteProductList();
            wsCallingSiteProducts.enqueue(new Callback<SiteProductModel>() {
                @Override
                public void onResponse(Call<SiteProductModel> call, Response<SiteProductModel> response) {

                    if (SiteProducts.this != null && !SiteProducts.this.isFinishing() && mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    arrayListSiteProducts.clear();
                    if (response.isSuccessful()) {

                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                arrayListSiteProducts.addAll(response.body().getData());
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                listView.setLayoutManager(mLayoutManager);
                                listView.setItemAnimator(new DefaultItemAnimator());
                                siteProductsAdapter = new SiteProductsAdapter(SiteProducts.this, arrayListSiteProducts, prokey);
                                listView.setAdapter(siteProductsAdapter);
                                prefs = PreferenceManager.getDefaultSharedPreferences(SiteProducts.this);
                                SharedPreferences.Editor editor = prefs.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(arrayListSiteProducts);
                                editor.putString("site_list", json);
                                editor.apply();

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }

                            if (arrayListSiteProducts.size() > 0) {
                                llEmpty.setVisibility(View.GONE);
                                // listView.setVisibility(View.VISIBLE);
                            } else {
                                llEmpty.setVisibility(View.VISIBLE);
                                // listView.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<SiteProductModel> call, Throwable t) {
                    mProgressDialog.dismiss();

                    mSwipeRefreshLayout.setEnabled(false);
                    serviceUnavailable();
                }
            });
        } else {
            noInternetConnection();
        }
    }

    private void disableScroll() {

        if (!prefsFirstTimeSiteProgress.getBoolean("firstTimeSiteProgressFirstMainOne", false)) {
            // run your one time code here
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    SharedPreferences skipEventClick = getSharedPreferences(PrefUtils.prefFirstone, 0);
                    boolean isSkipEvent = skipEventClick.getBoolean(PrefUtils.prefFirstone, false);

                    if (!isSkipEvent) {
                        listView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {

                                return true;
                            }
                        });
                       /* mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(false);*/
                        SharedPreferences guideViewSkipClickEvent = getSharedPreferences(PrefUtils.prefSecondone, 0);
                        SharedPreferences.Editor etClickEvent = guideViewSkipClickEvent.edit();
                        etClickEvent.putBoolean(PrefUtils.prefSecondone, true);
                        etClickEvent.apply();
                    }
                    return true;
                }
            });

            listView.setClickable(true);
            listView.setFocusable(true);
            listView.setEnabled(true);
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
            // mark first time has ran.
            SharedPreferences.Editor editor = prefsFirstTimeSiteProgress.edit();
            editor.putBoolean("firstTimeSiteProgressFirstMainOne", true);
            editor.apply();
        }


    }

    public void LoadSiteProductData() {
        prefs = PreferenceManager.getDefaultSharedPreferences(SiteProducts.this);
        Gson gson = new Gson();
        String json = prefs.getString("site_list", null);
        Type type = new TypeToken<ArrayList<SiteProductList>>() {
        }.getType();
        arrayListSiteProducts = gson.fromJson(json, type);
        if (json != null) {
            if (siteProductsAdapter == null)
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
            listView.setLayoutManager(mLayoutManager);
            listView.setItemAnimator(new DefaultItemAnimator());
            siteProductsAdapter = new SiteProductsAdapter(SiteProducts.this, arrayListSiteProducts, prokey);
            listView.setAdapter(siteProductsAdapter);
        } else {
            noInternetConnection();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    void initError() {
        llEmpty = (LinearLayout) findViewById(R.id.llEmpty);
        imgError = (ImageView) findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView) findViewById(R.id.txtErrorTitle);
        txtRetry = (MyTextView) findViewById(R.id.txtRetry);
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
        listView.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (SiteProducts.this != null && !SiteProducts.this.isFinishing() && mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    //Animation stuff

    @SuppressLint("NewApi")
    private void setupWindowAnimations() {
        Transition transition;

        if (type == TYPE_PROGRAMMATICALLY) {
            transition = buildEnterTransition();
        } else {
            //transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom);
        }
        //getWindow().setEnterTransition(transition);
    }


    @SuppressLint("NewApi")
    private Visibility buildEnterTransition() {
        Slide enterTransition = new Slide();
       // enterTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        enterTransition.setSlideEdge(Gravity.RIGHT);
        return enterTransition;
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*if (walkthroughSiteProgess.getSiteMainBack()) {

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        ViewTarget navigationButtonViewTarget = ViewTargets.navigationButtonViewTarget(toolbar);
                        new ShowcaseView.Builder(com.nebulacompanies.nebula.gui.SiteProducts.this)
                                .withMaterialShowcase()
                                .setTarget(navigationButtonViewTarget)
                                .setStyle(R.style.CustomShowcaseTheme2)
                                //.setContentTitle(R.string.company_profile)
                                .setContentText("To go to your home screen please click here.")
                                .build()
                                .show();
                    } catch (ViewTargets.MissingViewException e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);*/


    }
       /* SharedPreferences sp = getSharedPreferences("siteProgressBack", 0);
        boolean isBackProjects = sp.getBoolean("isSiteProgressBack", false);*/
        /*if (isBackMain) {
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    return false;
                }
            });
            mSwipeRefreshLayout.setEnabled(true);
        }

    }*/



}