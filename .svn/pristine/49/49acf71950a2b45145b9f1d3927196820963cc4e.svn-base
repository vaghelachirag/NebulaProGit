package com.nebulacompanies.ibo.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.DashboardUpdatesAdapter;
import com.nebulacompanies.ibo.ecom.BusinessActivity;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.NewUpdateList;
import com.nebulacompanies.ibo.model.UpdateList;
import com.nebulacompanies.ibo.model.Updates;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.EndlessScrollListener;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;

/**
 * Created by Palak Mehta on 21-Feb-18.
 */

public class TeamUpdatesFragment extends Fragment {

    ListView listView;
    public static final String TAG = "Team Updates";
    Boolean isRefreshed = false;
    DashboardUpdatesAdapter updatesAdapter;
    private ArrayList<UpdateList> arrayListTeamUpdates = new ArrayList<>();
    SharedPreferences.Editor editor;
    //Error View
    private LinearLayout llEmpty,lynologin;
    MyButtonEcom btnLogin;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry, txtSubError;
    Session session;
    int pageIndex = 1;
    int pageLength = 15;
    NewUpdateList newUpdateList;
    int totalRecords = 0;
    SharedPreferences prefs;
    IntentFilter intentFilter;
    Utils utils = new Utils();
    private BroadcastReceiver teamUpdate = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            if (action.equals(UpdatesFragment.REFRESH_UPDATETEAM)) {
                //loadFullData();
                refreshContent();
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_updates, container, false);
        arrayListTeamUpdates.clear();
        init(view);
        intentFilter = new IntentFilter();
        intentFilter.addAction(UpdatesFragment.REFRESH_UPDATETEAM);

        getActivity().registerReceiver(teamUpdate, intentFilter);
        session = new Session(getActivity());
       // mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        //getTeamUpdates(pageIndex, pageLength);
        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(teamUpdate);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            return;
    }
/*
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            prefs =  getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);;
            String personalUpdates = prefs.getString("team_list", "");
            if (personalUpdates != null && personalUpdates != "") {
                LoadData();
            } else {
                getTeamUpdates(pageIndex, pageLength);
            }

        }
    }*/


    void init(View view) {
        initError(view);
        listView = (ListView) view.findViewById(R.id.listview_team_updates);
        lynologin =view.findViewById(R.id.ly_no_login);
        btnLogin =view.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v-> utils.openLoginDialog(getActivity(),utils.gotoMyBusiness));

        ViewCompat.setNestedScrollingEnabled(listView, true);
        
       /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });*/

        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(final int page, int totalItemsCount) {

                new Handler().postDelayed(() -> {
                    if (totalRecords > 15) {
                        getTeamUpdates(pageIndex, pageLength);
                    }
                }, 1000);
                Log.i(TAG, "onLoadMore page and length: " + page + "," + pageLength + "," + totalItemsCount);
                // return true;
                return true;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);

                if (listView.getChildAt(0) != null) {
                    //mSwipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                super.onScrollStateChanged(view, scrollState);
            }
        });

    }

    UpdatesFragment parentFrag;

    private void refreshContent() {
        parentFrag = ((UpdatesFragment) TeamUpdatesFragment.this.getParentFragment());

        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            if (updatesAdapter != null) {
                updatesAdapter.clearData();
                updatesAdapter.notifyDataSetChanged();
            }
            arrayListTeamUpdates.clear();
            getTeamUpdates(pageIndex, pageLength);
            //mSwipeRefreshLayout.setRefreshing(true);
        } else {
            try {
                parentFrag.stopAnim(parentFrag.imageTeamupdate,txtRetry);
            } catch (Exception e) {
            }
            // mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnection();
        }
    }

    void getTeamUpdates(int page_index, final int page_length) {
        if(session.getLogin()) {
            lynologin.setVisibility(View.GONE);
            if (Utils.isNetworkAvailable(getActivity())) {
          /*  final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/
                Call<Updates> wsCallingUpdates =  BusinessActivity.mAPIInterface.getTeamUpdates(page_index, page_length);
                wsCallingUpdates.enqueue(new Callback<Updates>() {
                    @Override
                    public void onResponse(Call<Updates> call, Response<Updates> response) {
                        try {
                            parentFrag.stopAnim(parentFrag.imageTeamupdate, txtRetry);
                        } catch (Exception e) {
                        }

                /*    if (mProgressDialog != null & mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }*/
                        //mSwipeRefreshLayout.setRefreshing(false);

                        if (response.isSuccessful()) {
                            if (arrayListTeamUpdates != null) {
                                arrayListTeamUpdates.clear();
                                if (response.code() == 200) {
                                    // arrayListUpdates.clear();
                                    //arrayListTeamUpdates.clear();

                                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                        showRecords();
                                        newUpdateList = response.body().getData();
                                        totalRecords = newUpdateList.getTotalRecords();
                                        arrayListTeamUpdates.addAll(newUpdateList.getData());
                                  /*  if (getActivity() != null) {
                                        prefs = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE); 
                                        editor = prefs.edit();
                                        Gson gson = new Gson();
                                        String json = gson.toJson(response.body().getData());
                                        editor.putString("team_list", json);
                                        editor.apply();
                                    }*/
                                        if (arrayListTeamUpdates.size() > 0) {
                                            updatesAdapter = new DashboardUpdatesAdapter(getActivity(), arrayListTeamUpdates);
                                            listView.setAdapter(updatesAdapter);
                                            updatesAdapter.notifyDataSetChanged();
                                        }
                                /* arrayListUpdates.addAll(response.body().getData());

                                /*for (int i = 0; i < arrayListUpdates.size(); i++) {
                                    if (arrayListUpdates.get(i).getNotificationType().contains("Team")) {
                                        arrayListTeamUpdates.add(arrayListUpdates.get(i));
                                    }
                                }*/

                               /* if (arrayListTeamUpdates.size() > 0) {
                                    updatesAdapter = new DashboardUpdatesAdapter(getActivity(), arrayListTeamUpdates);
                                    listView.setAdapter(updatesAdapter);
                                    updatesAdapter.notifyDataSetChanged();
                                } else if(arrayListTeamUpdates.size() <= 0){
                                    noRecordsFound();
                                }else {
                                    noRecordsFound();
                                }*/
                                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                        //noRecordsFound();
                                        newUpdateList = response.body().getData();
                                        totalRecords = newUpdateList.getTotalRecords();
                                        if (totalRecords == 0) {
                                            noRecordsFound();
                                        }

                                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                            || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                        serviceUnavailable();
                                    }

                                    if (arrayListTeamUpdates.size() > 0) {
                                        llEmpty.setVisibility(View.GONE);
                                        listView.setVisibility(View.VISIBLE);
                                    } else {
                                        llEmpty.setVisibility(View.VISIBLE);
                                        listView.setVisibility(View.GONE);
                                    }
                                } else
                                    serviceUnavailable();
                                androidx.core.view.ViewCompat.setNestedScrollingEnabled(listView, true);
                            } else {
                                //   mProgressDialog.dismiss();
                                serviceUnavailable();
                            }
                        } else
                            serviceUnavailable();
                    }

                    @Override
                    public void onFailure(Call<Updates> call, Throwable t) {
                        try {
                            parentFrag.stopAnim(parentFrag.imageTeamupdate, txtRetry);
                        } catch (Exception e) {
                        }
                        //  mProgressDialog.dismiss();
                        Log.e(TAG, "ERROR : " + t.getMessage());
                        serviceUnavailable();
                    }
                });
            } else {
                try {
                    parentFrag.stopAnim(parentFrag.imageTeamupdate, txtRetry);
                } catch (Exception e) {
                }
                noInternetConnection();
                //  LoadData();
            }
        }else{
            listView.setVisibility(View.GONE);
            lynologin.setVisibility(View.VISIBLE);
        }
    }

    void initError(View view) {
        llEmpty =  view.findViewById(R.id.llEmpty);
        imgError =  view.findViewById(R.id.imgError);
        txtErrorTitle =  view.findViewById(R.id.txtErrorTitle);
        txtSubError =  view.findViewById(R.id.txtErrorSubTitle);
        txtRetry =  view.findViewById(R.id.txtRetry);
        txtRetry.setOnClickListener(v -> refreshContent());
    }

    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
       // imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        txtSubError.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
      //  imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        txtSubError.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_title);
        txtSubError.setText(R.string.error_content);
    //  imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        txtSubError.setVisibility(View.VISIBLE);
    }

    void showRecords() {
        llEmpty.setVisibility(View.GONE);
        txtRetry.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

    private void loadFullData() {
        prefs = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("team_list", null);
        Log.d("Update==","load full data : Team Update");
        newUpdateList = gson.fromJson(json, NewUpdateList.class);
        if (json != null) {
            totalRecords = newUpdateList.getTotalRecords();

            totalRecords = newUpdateList.getTotalRecords();

            arrayListTeamUpdates.addAll(newUpdateList.getData());
            if (updatesAdapter == null) {
                updatesAdapter = new DashboardUpdatesAdapter(getActivity(), arrayListTeamUpdates);
                listView.setAdapter(updatesAdapter);
                updatesAdapter.notifyDataSetChanged();
            }
        } else {
            refreshContent();
        }
    }

    private void LoadData() {

        prefs = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
        ;
        Gson gson = new Gson();
        String json = prefs.getString("team_list", null);

        newUpdateList = gson.fromJson(json, NewUpdateList.class);
        if (json != null) {
            totalRecords = newUpdateList.getTotalRecords();

            totalRecords = newUpdateList.getTotalRecords();

            arrayListTeamUpdates.addAll(newUpdateList.getData());
            if (updatesAdapter == null) {
                updatesAdapter = new DashboardUpdatesAdapter(getActivity(), arrayListTeamUpdates);
                listView.setAdapter(updatesAdapter);
                updatesAdapter.notifyDataSetChanged();
            }
        } else {
            listView.setVisibility(View.GONE);
            noInternetConnection();
        }
    }
}
