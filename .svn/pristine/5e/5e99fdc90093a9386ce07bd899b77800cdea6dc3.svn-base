package com.nebulacompanies.ibo.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.DashboardUpdatesAdapter;
import com.nebulacompanies.ibo.ecom.BusinessActivity;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.NewUpdateList;
import com.nebulacompanies.ibo.model.PVTable;
import com.nebulacompanies.ibo.model.UpdateList;
import com.nebulacompanies.ibo.model.Updates;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.EndlessScrollListener;
import com.nebulacompanies.ibo.view.MyTextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.fragments.UpdatesFragment.arrayListUpdates;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());


/**
 * Created by Palak Mehta on 21-Feb-18.
 */

public class PersonalUpdatesFragment extends Fragment {

    ListView listView;

    public static final String TAG = "Personal Updates";
    Boolean isRefreshed = false;
    DashboardUpdatesAdapter updatesAdapter;
    private ArrayList<UpdateList> arrayListPersonalUpdates = new ArrayList<>();

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry, txtSubError;
    Session session;
    int pageIndex = 1;
    int pageLength = 15;

    NewUpdateList newUpdateList;
    SharedPreferences.Editor editor;
    int totalRecords = 0;
    SharedPreferences prefs;
    IntentFilter intentFilter;
    LinearLayout lnnologin;
    MyButtonEcom btnLogin;
    Utils utils = new Utils();
    /*SharedPreferences prefPV;
    SharedPreferences.Editor edtPV;*/
    private BroadcastReceiver personalUpdate = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            if (action.equals(UpdatesFragment.REFRESH_UPDATE)) {
                //loadFullData();
                 refreshContent();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_updates, container, false);
       /* prefPV = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
        edtPV = prefPV.edit();*/
        init(view);
       /* prefs =  getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);;
        String personalUpdates = prefs.getString("personal_list", "");
        if (personalUpdates != null && personalUpdates != "") {
            LoadData();
        } else {
             arrayListPersonalUpdates.clear();
            getPersonalUpdates(pageIndex, pageLength);
        }*/

        arrayListPersonalUpdates.clear();
        //getPersonalUpdates(pageIndex, pageLength);

        intentFilter = new IntentFilter();
        intentFilter.addAction(UpdatesFragment.REFRESH_UPDATE);

        getActivity().registerReceiver(personalUpdate, intentFilter);
        // LoadData();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(personalUpdate);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            return;

        session = new Session(getActivity());
       // mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("INFO", " responseString arrayListUpdates.size() : " + arrayListUpdates.size());

    }


    void init(View view) {
        initError(view);
        listView = (ListView) view.findViewById(R.id.listview_personal_updates);
        lnnologin = view.findViewById(R.id.ly_no_login);
        btnLogin = view.findViewById(R.id.btn_login);
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

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (totalRecords > 15) {
                            getPersonalUpdates(page, pageLength);
                        }

                    }
                }, 1000);


                Log.i(TAG, "onLoadMore page and length: " + page + "," + pageLength + "," + totalItemsCount);

                //   return true;
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

        parentFrag = ((UpdatesFragment) PersonalUpdatesFragment.this.getParentFragment());
        if(session.getLogin()) {
            if (Utils.isNetworkAvailable(getActivity())) {
                isRefreshed = true;
                // editor.remove("personal_list").commit();
                if (updatesAdapter != null) {
                    updatesAdapter.clearData();
                    updatesAdapter.notifyDataSetChanged();
                }
                arrayListPersonalUpdates.clear();
                getPersonalUpdates(pageIndex, pageLength);
                //mSwipeRefreshLayout.setRefreshing(true);
            } else {
                try {
                    parentFrag.stopAnim(parentFrag.imageUpdate, txtRetry);
                } catch (Exception e) {
                }
                //mSwipeRefreshLayout.setRefreshing(false);
                noInternetConnection();
                //  LoadData();
            }
        }else
            noLogin();
    }

    private void noLogin() {

        listView.setVisibility(View.GONE);
        lnnologin.setVisibility(View.VISIBLE);
        btnLogin.setOnClickListener(v -> utils.openLoginDialog(getActivity(), utils.gotoMyBusiness));
    }



    void getPersonalUpdates(int page_index, final int page_length) {
        if(session.getLogin()) {
            if (Utils.isNetworkAvailable(getActivity())) {
           /* final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
           if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
*/
                Call<Updates> wsCallingUpdates =  BusinessActivity.mAPIInterface.getPersonalUpdates(page_index, page_length);
                wsCallingUpdates.enqueue(new Callback<Updates>() {
                    @Override
                    public void onResponse(Call<Updates> call, Response<Updates> response) {
                        try {
                            parentFrag.stopAnim(parentFrag.imageUpdate, txtRetry);
                        } catch (Exception e) {
                        }
                   /* if (mProgressDialog != null & mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }*/
                        //mSwipeRefreshLayout.setRefreshing(false);

                        if (response.isSuccessful()) {

                            if (response.code() == 200) {
                                //arrayListUpdates.clear();
                                //arrayListPersonalUpdates.clear();

                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {


                               /* Type collectionType = new TypeToken<NewUpdateList>() {
                                }.getType();
                                Gson gson = new Gson();
                                String json = gson.toJson(response.body().getData(), collectionType);
*/
                                    //edtPV.putString(AppUtils.prefteam, json).apply();
                                    showRecords();

                                    //setData(json);
                                    newUpdateList = response.body().getData();

                                    totalRecords = newUpdateList.getTotalRecords();

                                    arrayListPersonalUpdates.addAll(newUpdateList.getData());

                              /*  if (getActivity() != null) {
                                    Gson gson = new Gson();
                                    prefs = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
                                    ;
                                    editor = prefs.edit();
                                    String json = gson.toJson(newUpdateList);
                                    editor.putString("personal_list", json);
                                    editor.apply();
                                }*/
                                    if (arrayListPersonalUpdates.size() > 0) {
                                        updatesAdapter = new DashboardUpdatesAdapter(getActivity(), arrayListPersonalUpdates);
                                        listView.setAdapter(updatesAdapter);
                                        updatesAdapter.notifyDataSetChanged();
                                    }
                                    // arrayListUpdates.addAll(response.body().getData());

                                /*for (int i = 0; i < arrayListUpdates.size(); i++) {
                                    if (arrayListUpdates.get(i).getNotificationType().contains("Personal")) {
                                        //arrayListPersonalUpdates.addAll(arrayListUpdates);
                                        arrayListPersonalUpdates.add(arrayListUpdates.get(i));
                                    }
                                }*/

                               /* if (arrayListPersonalUpdates.size() > 0) {
                                    updatesAdapter = new DashboardUpdatesAdapter(getActivity(), arrayListPersonalUpdates);
                                    listView.setVisibility(View.VISIBLE);
                                    listView.setAdapter(updatesAdapter);
                                    updatesAdapter.notifyDataSetChanged();
                                } else if (arrayListPersonalUpdates.size() <= 0) {
                                    noRecordsFound();
                                } else {
                                    noRecordsFound();
                                }*/
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    noRecordsFound();
                                    //  edtPV.putString(AppUtils.prefteam, null).apply();
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    serviceUnavailable();
                                    // edtPV.putString(AppUtils.prefteam, null).apply();
                                }

                                if (arrayListPersonalUpdates.size() > 0) {
                                    llEmpty.setVisibility(View.GONE);
                                    listView.setVisibility(View.VISIBLE);
                                } else {
                                    //  edtPV.putString(AppUtils.prefteam, null).apply();
                                    llEmpty.setVisibility(View.VISIBLE);
                                    listView.setVisibility(View.GONE);
                                }
                            }
                            ViewCompat.setNestedScrollingEnabled(listView, true);
                        } else {
                        /*if (response.code() == 401) {
                            //Redirect to Login Page
                            //doLogout(getActivity(), session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
//                        edtPV.putString(AppUtils.prefteam, null).apply();
                            serviceUnavailable();
                        }
                    }

                    @Override
                    public void onFailure(Call<Updates> call, Throwable t) {
                        try {
                            parentFrag.stopAnim(parentFrag.imageUpdate, txtRetry);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // mProgressDialog.dismiss();
                        Log.e(TAG, "ERROR : " + t.getMessage());
                        // edtPV.putString(AppUtils.prefteam, null).apply();
                        serviceUnavailable();
                    }
                });
            } else {
                try {
                    parentFrag.stopAnim(parentFrag.imageUpdate, txtRetry);
                } catch (Exception e) {
                    e.printStackTrace();

                }
                noInternetConnection();
                //  LoadData();
            }
        }else
            noLogin();
    }
/*
    private void setData(String jsondata) {
        Type collectionType = new TypeToken<NewUpdateList>() {
        }.getType();
        Gson gson = new Gson();


        // String data = prefPV.getString(AppUtils.prefteam, null);
        newUpdateList = gson.fromJson(jsondata, collectionType);
        // newUpdateList = response.body().getData();

        totalRecords = newUpdateList.getTotalRecords();

        arrayListPersonalUpdates.addAll(newUpdateList.getData());

        if (getActivity() != null) {
            prefs = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
            ;
            editor = prefs.edit();
            String json = gson.toJson(newUpdateList);
            editor.putString("personal_list", json);
            editor.apply();
        }
        if (arrayListPersonalUpdates.size() > 0) {
            updatesAdapter = new DashboardUpdatesAdapter(getActivity(), arrayListPersonalUpdates);
            listView.setAdapter(updatesAdapter);
            updatesAdapter.notifyDataSetChanged();
        }

    }*/

    void initError(View view) {
        llEmpty = (LinearLayout) view.findViewById(R.id.llEmpty);
        imgError = (ImageView) view.findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView) view.findViewById(R.id.txtErrorTitle);
        txtSubError = (MyTextView) view.findViewById(R.id.txtErrorSubTitle);
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
       // imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        txtSubError.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
       // imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
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

 /*   public void loadFullData() {
        Log.d("Update==","load full data : Personal Update");
        prefs = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
        ;
        Gson gson = new Gson();
        String json = prefs.getString("personal_list", null);

        NewUpdateList newUpdateList = gson.fromJson(json, NewUpdateList.class);
        if (json != null) {
            arrayListPersonalUpdates.clear();
            totalRecords = newUpdateList.getTotalRecords();
            arrayListPersonalUpdates.addAll(newUpdateList.getData());

            if (updatesAdapter == null) {
                updatesAdapter = new DashboardUpdatesAdapter(getActivity(), arrayListPersonalUpdates);
                listView.setAdapter(updatesAdapter);
                updatesAdapter.notifyDataSetChanged();
            }
        } else {
            refreshContent();
        }
    }
*/
    public void LoadData() {
        prefs = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("personal_list", null);

        NewUpdateList newUpdateList = gson.fromJson(json, NewUpdateList.class);
        if (json != null) {
            arrayListPersonalUpdates.clear();
            totalRecords = newUpdateList.getTotalRecords();
            arrayListPersonalUpdates.addAll(newUpdateList.getData());

            if (updatesAdapter == null) {
                updatesAdapter = new DashboardUpdatesAdapter(getActivity(), arrayListPersonalUpdates);
                listView.setAdapter(updatesAdapter);
                updatesAdapter.notifyDataSetChanged();
            }
        } else {
            noInternetConnection();
        }
    }
}
