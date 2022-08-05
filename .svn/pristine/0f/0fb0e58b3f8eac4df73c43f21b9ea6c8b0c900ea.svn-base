package com.nebulacompanies.ibo.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nebulacompanies.ibo.Base2Activity;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.NewSiteProgressImagesAdapter;
import com.nebulacompanies.ibo.adapter.SiteProgressItemAdapter;
import com.nebulacompanies.ibo.ecom.utils.PaginationScrollListener;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.NewSiteProgressImages;
import com.nebulacompanies.ibo.model.SiteProgressList;
import com.nebulacompanies.ibo.showcaseviewback.ShowcaseView;
import com.nebulacompanies.ibo.showcaseviewback.ViewTarget;
import com.nebulacompanies.ibo.util.ViewTargets;
import com.nebulacompanies.ibo.view.MyBoldTextView;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());
import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 9/15/2016.
 */
public class ViewSiteProgress extends Base2Activity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    String name, monthIntext, year;
    int productid, month;
    private APIInterface mAPIInterface;
    //public static ArrayList<SiteProgressImageList> arrayListSiteProgressImages = new ArrayList<>();
    public static final String TAG = "ViewSiteProgress";
    NewSiteProgressImagesAdapter siteProgressImagesAdapter;
    Boolean isRefreshed = false;
    Boolean isNotificationClicked = false;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry, tv;
    private MyBoldTextView tvTitle;
    Toolbar toolbar;
    boolean product_type_sub, OnBack, OnBackSiteProgress, OnBackSiteProduct;


    String PRODUCT_NAME;
    int ProjectId;

    private static final int PAGE_START = 1;
    SharedPreferences prefsFirstTime;
    RecyclerView rv;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int TOTAL_PAGES = 100;
    private int currentPage = PAGE_START;
    GridLayoutManager gridLayoutManager;

    ArrayList<String> imagepic = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<Integer> count = new ArrayList<Integer>();

    SiteProgressItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_projects);
        prefsFirstTime = getSharedPreferences(PrefUtils.prefSpotlight, MODE_PRIVATE);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            name = b.getString("Name");
            monthIntext = b.getString("MonthInText");
            year = b.getString("Year");
            productid = b.getInt("ProjectId");
            month = b.getInt("Month");
            product_type_sub = b.getBoolean("product_type_sub");
            OnBack = b.getBoolean("OnBack");
            PRODUCT_NAME = b.getString("Product_Name");
            ProjectId = b.getInt("ProjectId");

        }
        setActionBar();
        init();
        loadFirstPage();


       /* SharedPreferences skipMainGet = getSharedPreferences(PrefUtils.prefSkipmain, 0);
        boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
        if (!isSkipMain) {
            disableScroll();
        }*/
    }

    void setActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_projects);
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setTitle(monthIntext + " " + year);
    }

    void init() {

        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.projects_view_swipe_refresh_layout);
        rv = (RecyclerView) findViewById(R.id.recycler_view);
        //rv.setOnItemClickListener(this);

        tv = (MyTextView) findViewById(R.id.tv);
        tvTitle = (MyBoldTextView) findViewById(R.id.tv_title);


        tvTitle.setText("Construction Update");
        //tv.setText("Catch up with the images of construction updates from " + name);

        /*mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });*/

        if (product_type_sub){
            disableScroll();
        }
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (product_type_sub){
                    mSwipeRefreshLayout.setRefreshing(false);
                    mSwipeRefreshLayout.setEnabled(false);
                /*if (!prefsFirstTime.getBoolean("firstTimeSiteProgressSpotLight1", false)) {
                    SharedPreferences skipMainGet = getSharedPreferences(PrefUtils.prefSkipmain, 0);
                    boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
                    if (!isSkipMain) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(false);
                        disableScroll();
                    }

                    SharedPreferences.Editor editor = prefsFirstTime.edit();
                    editor.putBoolean("firstTimeSiteProgressSpotLight1", true);
                    editor.apply();*/
                } else {
                    refreshContent();
                }

            }
        });
      //  prefsFirstTime = PreferenceManager.getDefaultSharedPreferences(this);
        adapter = new SiteProgressItemAdapter(ViewSiteProgress.this, name, imagepic, date, count, monthIntext, year, productid, month, product_type_sub, OnBack, PRODUCT_NAME);

        gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(gridLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(adapter);
        rv.setNestedScrollingEnabled(false);


        PageScroll();
    }

    private void disableScroll() {

       // if (!prefsFirstTime.getBoolean("firstTimeSiteProgress", false)) {
            // run your one time code here
            rv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    SharedPreferences skipEventClick = getSharedPreferences(PrefUtils.PrefViewSiteprogress, 0);
                    boolean isSkipEvent = skipEventClick.getBoolean(PrefUtils.PrefViewSiteprogress, false);

                    if (!isSkipEvent) {
                        rv.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                        rv.setClickable(true);
                        rv.setFocusable(true);
                        rv.setEnabled(true);
                        SharedPreferences guideViewSkipClickEvent = getSharedPreferences(PrefUtils.prefProgressclick, 0);
                        SharedPreferences.Editor etClickEvent = guideViewSkipClickEvent.edit();
                        etClickEvent.putBoolean(PrefUtils.prefProgressclick, true);
                        etClickEvent.apply();
                    }
                    return true;
                }
            });


            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(false);
    }

    private void PageScroll() {
        rv.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            public void firstVisible() {
                mSwipeRefreshLayout.setEnabled(gridLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            if (siteProgressImagesAdapter != null) {
                siteProgressImagesAdapter.clearData();
                siteProgressImagesAdapter.notifyDataSetChanged();
            }
            date.clear();
            imagepic.clear();
            count.clear();
            adapter.clear();
            adapter.notifyDataSetChanged();
            loadFirstPage();
            PageScroll();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnection();
        }
    }

    private void loadNextPage() {
        callSiteProgressApi().enqueue(new Callback<NewSiteProgressImages>() {
            @Override
            public void onResponse(Call<NewSiteProgressImages> call, Response<NewSiteProgressImages> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                List<SiteProgressList> results = response.body().getData().getData();
                adapter.addAll(results);

                for (int i = 0; i < results.size(); i++) {
                    if (!response.body().getData().getData().contains(results.get(i).getThumbnailImage())) {
                        imagepic.add(results.get(i).getRootImage());
                        date.add(SetGmtTime(results.get(i).getCreatedOn()));
                    }
                }
                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<NewSiteProgressImages> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadFirstPage() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this, R.style.MyTheme);
        mProgressDialog.setCancelable(false);
        if (!isRefreshed) {
            mProgressDialog.show();
        }
        mProgressDialog.setContentView(R.layout.progressdialog);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        currentPage = PAGE_START;
        callSiteProgressApi().enqueue(new Callback<NewSiteProgressImages>() {
            @Override
            public void onResponse(Call<NewSiteProgressImages> call, Response<NewSiteProgressImages> response) {
                if (ViewSiteProgress.this != null && !ViewSiteProgress.this.isFinishing() && mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                mSwipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    // Got data. Send it to adapter
                    List<SiteProgressList> results = response.body().getData().getData();
                    // progressBar.setVisibility(View.GONE);
                    adapter.addAll(results);
                    String projectDescription = response.body().getData().getProjectDescription();

                    if (projectDescription != null) {
                        tv.setText(projectDescription.toString());
                    }

                    for (int i = 0; i < results.size(); i++) {
                        if (!response.body().getData().getData().contains(results.get(i).getThumbnailImage())) {
                            imagepic.add(results.get(i).getRootImage());
                            date.add(SetGmtTime(results.get(i).getCreatedOn()));
                        }
                    }
                    if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<NewSiteProgressImages> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private Call<NewSiteProgressImages> callSiteProgressApi() {
        return mAPIInterface.getSiteProgressImages(currentPage, 15, productid, month, year);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        rv.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {

        if (OnBack) {
            SharedPreferences sp = getSharedPreferences(PrefUtils.prefBackview, MODE_PRIVATE);
            SharedPreferences.Editor et = sp.edit();
            et.putBoolean("isSiteProgressBackView", true);
            et.apply();
           /* Intent i1 = new Intent(this, SiteProgress.class);
            i1.putExtra("ProjectId", ProjectId);
            i1.putExtra("Product_Name", PRODUCT_NAME);
            i1.putExtra("OnsiteProress", true);
            i1.putExtra("OnsiteProduct", true);
            i1.putExtra("OnBack", OnBack);
            i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i1);*/
        } else {
            super.onBackPressed();
        }
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!prefsFirstTime.getBoolean("firstTimeSiteProgressBack", false)) {
            if (OnBack) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ViewTarget navigationButtonViewTarget = ViewTargets.navigationButtonViewTarget(toolbar);
                            new ShowcaseView.Builder(ViewSiteProgress.this)
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
                }, 5000);
                SharedPreferences.Editor editor = prefsFirstTime.edit();
                editor.putBoolean("firstTimeSiteProgressBack", true);
                editor.apply();
            }
        }
    }
}
