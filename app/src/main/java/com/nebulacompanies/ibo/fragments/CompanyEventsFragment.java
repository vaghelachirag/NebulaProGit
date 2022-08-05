package com.nebulacompanies.ibo.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.CompanyEventsAdapter;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.EventList;
import com.nebulacompanies.ibo.model.Events;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.view.MyTextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

/**
 * Created by Palak Mehta on 11/5/2016.
 */

public class CompanyEventsFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView listView;
    private APIInterface mAPIInterface;
    public static ArrayList<EventList> arrayListEvents = new ArrayList<>();
    public static final String TAG = "Events";
    CompanyEventsAdapter companyEventsAdapter;
    Boolean isRefreshed = false;
    Boolean isNotificationClicked = false;
    ConnectionDetector cd;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle,txtErrorContent, txtRetry;
    String param;
    String message;
    private int type;


    MaterialProgressBar mProgressDialog;
    RecyclerView.LayoutManager mLayoutManager;

    SharedPreferences prefs;
    SharedPreferences prefsFirstTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.company_event, container, false);


        init(view);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String companyList = prefs.getString("company_list", "");
        if (companyList != null && companyList != "") {
            LoadEventsData();
        } else {
            getEventList();
        }

       /* NewCompanyEvent activity = (NewCompanyEvent) getActivity();
        String getData = activity.sendData();

        if (getData!=null){
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
        }*/
        return view;
    }

    void init(View view) {
        cd = new ConnectionDetector(getActivity());
    //    Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        initError(view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.company_event_swipe_refresh_layout);
        mProgressDialog = view.findViewById(R.id.progresbar);
        listView = (RecyclerView) view.findViewById(R.id.company_events_recyclerview);
        /*DividerItemDecoration myDivider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_recyclerview));
        listView.addItemDecoration(myDivider);*/
        //   prefsFirstTime = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listView.setClickable(true);
        listView.setFocusable(true);
        listView.setEnabled(true);

        prefsFirstTime = getActivity().getSharedPreferences(PrefUtils.prefScroll, MODE_PRIVATE);
        SharedPreferences skipMainGet = getActivity().getSharedPreferences(PrefUtils.prefSkipmain, 0);
        boolean isSkipMain = skipMainGet.getBoolean("isSkipMain", false);
        if (!isSkipMain) {
            disableScroll();
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                }
            }
        });


        SharedPreferences skipEventClick = getActivity().getSharedPreferences(PrefUtils.prefSkipevent, 0);
        boolean isSkipEvent = skipEventClick.getBoolean(PrefUtils.prefSkipevent, false);

       /* if (!isSkipEvent){
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    return false;
                }
            });
            listView.setClickable(true);
            listView.setFocusable(true);
            listView.setEnabled(true);
            SharedPreferences guideViewSkipClickEvent = getContext().getSharedPreferences(PrefUtils.prefSkipeventclick, 0);
            SharedPreferences.Editor etClickEvent = guideViewSkipClickEvent.edit();
            etClickEvent.putBoolean(PrefUtils.prefSkipeventclick, true);
            etClickEvent.apply();
        }*/
    }

    private void disableScroll() {

        if (!prefsFirstTime.getBoolean("firstTimeEventMain", false)) {
            // run your one time code here
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    SharedPreferences skipEventClick = getActivity().getSharedPreferences(PrefUtils.prefSkipevent, 0);
                    boolean isSkipEvent = skipEventClick.getBoolean(PrefUtils.prefSkipevent, false);

                    if (!isSkipEvent) {
                        listView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                        listView.setClickable(true);
                        listView.setFocusable(true);
                        listView.setEnabled(true);
                        SharedPreferences guideViewSkipClickEvent = getContext().getSharedPreferences(PrefUtils.prefSkipeventclick, 0);
                        SharedPreferences.Editor etClickEvent = guideViewSkipClickEvent.edit();
                        etClickEvent.putBoolean(PrefUtils.prefSkipeventclick, true);
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
            SharedPreferences.Editor editor = prefsFirstTime.edit();
            editor.putBoolean("firstTimeEventMain", true);
            editor.apply();
        }
    }


    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            if (companyEventsAdapter != null) {
                companyEventsAdapter.clearData();
                companyEventsAdapter.notifyDataSetChanged();
            }
            getEventList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            // noInternetConnection();
            LoadEventsData();
        }
    }

    private void getEventList() {
        if (Utils.isNetworkAvailable(getActivity())) {
            //mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);

            mProgressDialog.setVisibility(View.VISIBLE);

            Call<Events> wsCallingEvents = mAPIInterface.getEventList();
            wsCallingEvents.enqueue(new Callback<Events>() {
                @Override
                public void onResponse(Call<Events> call, Response<Events> response) {

                    mProgressDialog.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                    arrayListEvents.clear();
                    if (response.isSuccessful()) {

                        if (response.code() == 200) {

                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                                arrayListEvents.addAll(response.body().getData());

                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                listView.setLayoutManager(mLayoutManager);
                                //  listView.setItemAnimator(new DefaultItemAnimator());
                                companyEventsAdapter = new CompanyEventsAdapter(getActivity(), arrayListEvents);
                                listView.setAdapter(companyEventsAdapter);
                                if (getActivity() != null) {
                                    prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    SharedPreferences.Editor editor = prefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(arrayListEvents);
                                    editor.putString("company_list", json);
                                    editor.apply();
                                }
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }
                            if (arrayListEvents.size() > 0) {
                                llEmpty.setVisibility(View.GONE);
                                listView.setVisibility(View.VISIBLE);
                            } else {
                                llEmpty.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<Events> call, Throwable t) {
                    mSwipeRefreshLayout.setEnabled(false);
                    mProgressDialog.setVisibility(View.INVISIBLE);
                    serviceUnavailable();
                }
            });
        } else {
            noInternetConnection();
        }
    }


    void initError(View view) {
        llEmpty = (LinearLayout) view.findViewById(R.id.llEmpty1);
        imgError = (ImageView) view.findViewById(R.id.imgError1);
        txtErrorTitle = (MyTextView) view.findViewById(R.id.txtErrorTitle1);
        txtErrorContent =(MyTextView) view.findViewById(R.id.txtErrorsubTitle1);
        txtRetry = (MyTextView) view.findViewById(R.id.txtRetry1);
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
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        txtErrorContent.setVisibility(View.VISIBLE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    public void LoadEventsData() {
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = prefs.getString("company_list", null);
        Type type = new TypeToken<ArrayList<EventList>>() {
        }.getType();
        arrayListEvents = gson.fromJson(json, type);
        if (json != null) {
            if (companyEventsAdapter == null)
                mLayoutManager = new LinearLayoutManager(getActivity());
            listView.setLayoutManager(mLayoutManager);
            listView.setItemAnimator(new DefaultItemAnimator());
            companyEventsAdapter = new CompanyEventsAdapter(getActivity(), arrayListEvents);
            listView.setAdapter(companyEventsAdapter);

        } else {
            noInternetConnection();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        /*SharedPreferences skipEventClick = getActivity().getSharedPreferences(PrefUtils.prefSkipevent, 0);
        boolean isSkipEvent = skipEventClick.getBoolean(PrefUtils.prefSkipevent, false);
*/

        SharedPreferences sp = getActivity().getSharedPreferences(PrefUtils.prefFullscreen, 0);
        boolean isBackProjects = sp.getBoolean("isFullScreenBackEvent", false);
        if (isBackProjects) {
            listView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    return false;
                }
            });
            mSwipeRefreshLayout.setEnabled(true);
        }
       /*if (!isSkipEvent){
           listView.setOnTouchListener(new View.OnTouchListener() {
               @Override
               public boolean onTouch(View v, MotionEvent event) {

                   return false;
               }
           });*/

          /*listView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View v, MotionEvent event) {

                  listView.setClickable(true);
                  listView.setFocusable(true);
                  listView.setEnabled(true);
                  SharedPreferences guideViewSkipClickEvent = getContext().getSharedPreferences(PrefUtils.prefSkipeventclick, 0);
                  SharedPreferences.Editor etClickEvent = guideViewSkipClickEvent.edit();
                  etClickEvent.putBoolean(PrefUtils.prefSkipeventclick, true);
                  etClickEvent.apply();

                  return false;
              }
          });*/

        // }
    }

}
