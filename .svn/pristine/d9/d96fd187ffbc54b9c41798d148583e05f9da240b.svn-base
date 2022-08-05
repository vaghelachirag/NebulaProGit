package com.nebulacompanies.ibo.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nebulacompanies.ibo.Base2Activity;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.EventImagesAdapter;
import com.nebulacompanies.ibo.adapter.EventItemAdapter;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.PaginationScrollListener;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.NewSubEventDetails;
import com.nebulacompanies.ibo.model.SubEventList;
import com.nebulacompanies.ibo.showcaseviewback.ShowcaseView;
import com.nebulacompanies.ibo.showcaseviewback.ViewTarget;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UserAuthorization;
import com.nebulacompanies.ibo.util.ViewTargets;
import com.nebulacompanies.ibo.view.MyBoldTextView;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());
import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 8/6/2016.
 */
public class ViewEvents extends Base2Activity {

    SwipeRefreshLayout mSwipeRefreshLayout;
    String event;
    String eventDate;
    private APIInterface mAPIInterface;
    EventImagesAdapter eventImagesAdapter;
    public static ArrayList<SubEventList> arrayListEvents = new ArrayList<>();
    public static final String TAG = "ViewEvents";
    Boolean isRefreshed = false;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    MyTextView tvDateValue;
    private MyTextView txtErrorTitle, txtErrorContent,txtRetry, tvDescp;
    private MyBoldTextView tvTitle, tvDate;

    ConnectionDetector cd;
    Session session;
    UserAuthorization mUserAuthorization;

    final String PREFS_WALKTHROUGH_EVENT_BACK = "eventbacktwalkthrough";
    SharedPreferences walkthroughEventBack;
    Toolbar toolbar;
    boolean OnBack;
    private static final int PAGE_START = 1;
    EventItemAdapter adapter;
    RecyclerView rv;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private static final int TOTAL_PAGES = 150;
    private int currentPage = PAGE_START;

    GridLayoutManager gridLayoutManager;

    ArrayList<String> imagepic = new ArrayList<String>();
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<Integer> count = new ArrayList<Integer>();
    /*List<SubEventList> results;*/
    SharedPreferences prefsFirstTime;
    SharedPreferences prefsFirstTimeSwipe;
    boolean first_time;
    MaterialProgressBar mProgressBar;
    MyTextViewEcom toolbarTitle;
    ImageView imgBack;
    RelativeLayout rltoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_projects);
        Utils.darkenStatusBar(this, R.color.colorNotification);


        cd = new ConnectionDetector(getApplicationContext());
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            event = b.getString("Event_Name");
            eventDate = b.getString(" ");
            OnBack = b.getBoolean("OnBack");
            first_time = b.getBoolean("first_time");
        }
        session = new Session(this);
        mUserAuthorization = new UserAuthorization(this);
        prefsFirstTime = getSharedPreferences(PrefUtils.prefviewevents, MODE_PRIVATE);
        prefsFirstTimeSwipe = getSharedPreferences(PrefUtils.prefvieweventsswipes, MODE_PRIVATE);

        //setActionBar();
        init();



    }

    /*void setActionBar() {
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_projects);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(event);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }*/

    void init() {
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.projects_view_swipe_refresh_layout);
        mProgressBar = findViewById(R.id.progresbar);
        rltoolbar = findViewById(R.id.toolbarlay);
        rltoolbar.setVisibility(View.VISIBLE);
        toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        toolbarTitle = findViewById(R.id.toolbar_title1);
        toolbarTitle.setText(event);
        imgBack = findViewById(R.id.img_back);
        rv = (RecyclerView) findViewById(R.id.recycler_view);
        tvDate = (MyBoldTextView) findViewById(R.id.tv_date);
        tvDateValue = (MyTextView) findViewById(R.id.tv_date_value);
        tvDescp = (MyTextView) findViewById(R.id.tv);
        tvTitle = (MyBoldTextView) findViewById(R.id.tv_title);
        tvDate.setVisibility(View.VISIBLE);
        tvDateValue.setVisibility(View.VISIBLE);

        tvDateValue.setText(eventDate);
        tvTitle.setText("About the event");

        if (first_time) {
            disableScroll();
        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (first_time) {
                    if (!prefsFirstTimeSwipe.getBoolean("firstTimeEventView", false)) {

                        mSwipeRefreshLayout.setRefreshing(false);
                        mSwipeRefreshLayout.setEnabled(false);

                        rv.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {

                                rv.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        return true;
                                    }
                                });
                                return true;
                            }
                        });
                    }

                    SharedPreferences.Editor editor = prefsFirstTimeSwipe.edit();
                    editor.putBoolean("firstTimeEventView", true);
                    editor.apply();

                } else {
                    refreshContent();
                }
            }
        });

        adapter = new EventItemAdapter(ViewEvents.this, event, imagepic, date, count, first_time);


        gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(gridLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        rv.setAdapter(adapter);
        rv.setNestedScrollingEnabled(false);

        PageScroll();
        loadFirstPage();

    }


    private void disableScroll() {
        SharedPreferences skipMainGet = getSharedPreferences(PrefUtils.prefSkipmain, 0);
        boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
        SharedPreferences skipModuleGet = getSharedPreferences(PrefUtils.prefModuleskip, 0);
        boolean isSkipModule = skipModuleGet.getBoolean("guideviewModuleskip", false);
        if (!isSkipMain && !isSkipModule) {
            if (!prefsFirstTime.getBoolean("firstTimeEventView", false)) {
                // run your one time code here
                rv.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setEnabled(false);
                // mark first time has ran.
                SharedPreferences.Editor editor = prefsFirstTime.edit();
                editor.putBoolean("firstTimeEventView", true);
                editor.apply();
            }
        }
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

    private void refreshContent() {
        //progressBar.setVisibility(View.VISIBLE);
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            if (callTopRatedApi().isExecuted())
                callTopRatedApi().cancel();

            // TODO: Check if data is stale.
            //  Execute network request if cache is expired; otherwise do not update data.
            adapter.clear();
            adapter.notifyDataSetChanged();
            date.clear();
            imagepic.clear();
            count.clear();
            loadFirstPage();
            PageScroll();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnection();
        }
    }

    private void loadFirstPage() {

       if(Utils.isNetworkAvailable(getApplicationContext())) {
           currentPage = PAGE_START;
           mProgressBar.setVisibility(View.VISIBLE);
           callTopRatedApi().enqueue(new Callback<NewSubEventDetails>() {
               @Override
               public void onResponse(Call<NewSubEventDetails> call, Response<NewSubEventDetails> response) {

                   mProgressBar.setVisibility(View.GONE);
                   mSwipeRefreshLayout.setRefreshing(false);
                   if (response.isSuccessful()) {
                       // Got data. Send it to adapter
                       List<SubEventList> results = response.body().getData().getData();
                       // progressBar.setVisibility(View.GONE);
                       adapter.addAll(results);

                       if (!response.body().getMessage().equals("No records found!")) {

                           String Descp = results.get(0).getEventDescription();

                           if (results.get(0).getEventDescription() != null) {
                               tvDescp.setText(Descp.toString());
                           }
                       } else {
                           noRecordsFound();
                       }
                       for (int i = 0; i < results.size(); i++) {
                           if (!response.body().getData().getData().contains(results.get(i).getEventPic())) {
                               imagepic.add(results.get(i).getEventPic());
                               date.add(SetGmtTime(results.get(i).getCratedOn()));
                               count.add(results.get(i).getLinkCount());
                           }
                       }
                       if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
                       else isLastPage = true;
                   }
               }

               @Override
               public void onFailure(Call<NewSubEventDetails> call, Throwable t) {
                   t.printStackTrace();
               }
           });
       }
       else{
            mProgressBar.setVisibility(View.GONE);
            noInternetConnection();
       }
    }

    private void loadNextPage() {
        callTopRatedApi().enqueue(new Callback<NewSubEventDetails>() {
            @Override
            public void onResponse(Call<NewSubEventDetails> call, Response<NewSubEventDetails> response) {
                adapter.removeLoadingFooter();
                isLoading = false;

                List<SubEventList> results = response.body().getData().getData();
                adapter.addAll(results);
                //ImagePass(results);
                for (int i = 0; i < results.size(); i++) {
                    if (!response.body().getData().getData().contains(results.get(i).getEventPic())) {
                        imagepic.add(results.get(i).getEventPic());
                        date.add(SetGmtTime(results.get(i).getCratedOn()));
                        count.add(results.get(i).getLinkCount());
                    }
                }
                if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                else isLastPage = true;
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<NewSubEventDetails> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private Call<NewSubEventDetails> callTopRatedApi() {
        return mAPIInterface.getEvent(currentPage, 15, event, "None");
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onBackPressed() {
        if (OnBack) {
            SharedPreferences sp = getSharedPreferences(PrefUtils.prefFullscreen, MODE_PRIVATE);
            SharedPreferences.Editor et = sp.edit();
            et.putBoolean("isFullScreenBackEvent", true);
            et.apply();

            Intent intent = new Intent(this, NewCompanyEvent.class);
            intent.putExtra("OnBack", OnBack);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            ActivityCompat.finishAffinity(ViewEvents.this);

        } else {
            SharedPreferences sp = getSharedPreferences(PrefUtils.prefFullscreen, MODE_PRIVATE);
            SharedPreferences.Editor et = sp.edit();
            et.putBoolean("isFullScreenBackEvent", true);
            et.apply();
            super.onBackPressed();
        }
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

    }


    void initError() {
        llEmpty = (LinearLayout) findViewById(R.id.llEmpty1);
        imgError = (ImageView) findViewById(R.id.imgError1);
        txtErrorTitle = (MyTextView) findViewById(R.id.txtErrorTitle1);
        txtErrorContent =(MyTextView)findViewById(R.id.txtErrorsubTitle1);
        txtRetry = (MyTextView) findViewById(R.id.txtRetry1);
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llEmpty.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
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
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setVisibility(View.VISIBLE);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!prefsFirstTime.getBoolean("firstTimeViewEventsBack", false)) {
            if (OnBack) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ViewTarget navigationButtonViewTarget = ViewTargets.navigationButtonViewTarget(toolbar);
                            new ShowcaseView.Builder(ViewEvents.this)
                                    .withMaterialShowcase()
                                    .setTarget(navigationButtonViewTarget)
                                    .setStyle(R.style.CustomShowcaseTheme2)
                                    .setContentText("To go to your home screen please click here.")
                                    .build()
                                    .show();
                        } catch (ViewTargets.MissingViewException e) {
                            e.printStackTrace();
                        }
                    }
                }, 5000);
                SharedPreferences.Editor editor = prefsFirstTime.edit();
                editor.putBoolean("firstTimeViewEventsBack", true);
                editor.apply();
            }
        }
    }


}