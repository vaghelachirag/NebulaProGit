package com.nebulacompanies.ibo.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.NewJoineesAdapter;
import com.nebulacompanies.ibo.ecom.BusinessActivity;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.NewJoineeList;
import com.nebulacompanies.ibo.model.NewJoinees;
import com.nebulacompanies.ibo.util.Session;
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
 * Created by Palak Mehta on 22-Feb-18.
 */

public class NewJoineesFragment extends Fragment {

    ListView listView;
    public static final String TAG = "New Joinees";
    Boolean isRefreshed = false;
    NewJoineesAdapter newJoineesAdapter;
    private ArrayList<NewJoineeList> arrayListNewJoieeLists = new ArrayList<>();

    //Error View
    private LinearLayout llEmpty,lynologin;
    MyButtonEcom btnlogin;
  //  private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry, txtSubError;
    Session session;
  //  SharedPreferences.Editor editor;
    SharedPreferences prefs;
    IntentFilter intentFilter;
    UpdatesFragment parentFrag;
    Utils utils = new Utils();

    private BroadcastReceiver rankReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            if (action.equals(UpdatesFragment.REFRESH_JOINEE)) {
               //if(session.getLogin())
                    getNewJoineesData();


            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_joinees, container, false);
        init(view);

        prefs = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
       // ;
      /*  String newJoinees = prefs.getString("New_joinees", "");
        if (newJoinees != null && newJoinees != "") {
            LoadData();
        } else {
            getNewJoineesData(); //New_joinees
        }*/
        intentFilter = new IntentFilter();
        intentFilter.addAction(UpdatesFragment.REFRESH_JOINEE);

        getActivity().registerReceiver(rankReceiver, intentFilter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(rankReceiver);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            return;

        session = new Session(getActivity());
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            if (arrayListNewJoieeLists.size() > 0) {
                newJoineesAdapter = new NewJoineesAdapter(getActivity(), arrayListNewJoieeLists);
                listView.setAdapter(newJoineesAdapter);
                newJoineesAdapter.notifyDataSetChanged();
            } else {
                getNewJoinees();
            }
        }
    }
*/
    void init(View view) {
        initError(view);
        listView = (ListView) view.findViewById(R.id.listview_new_joinees);
        lynologin =view.findViewById(R.id.ly_no_login);
        btnlogin = view.findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(v-> utils.openLoginDialog(getActivity(),utils.gotoMyBusiness) );

        ViewCompat.setNestedScrollingEnabled(listView, true);
        parentFrag = ((UpdatesFragment) NewJoineesFragment.this.getParentFragment());

        /*listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null) {
                    //mSwipeRefreshLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });*/

       /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });*/
    }


    public void refreshContent() {

        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            // editor.remove("New_joinees").commit();
            if (newJoineesAdapter != null) {
                newJoineesAdapter.clearData();
                newJoineesAdapter.notifyDataSetChanged();
            }
            getNewJoineesData();
            //mSwipeRefreshLayout.setRefreshing(true);
        } else {
            // mSwipeRefreshLayout.setRefreshing(false);
            // noInternetConnection();
            try {
                parentFrag.stopAnim(parentFrag.imageViewNewjoinee,txtRetry);
            } catch (Exception e) {
            }
            noInternetConnection();
          //  LoadData();
        }
    }

    void getNewJoineesData() {
        if(session.getLogin()) {
            lynologin.setVisibility(View.GONE);
            if (Utils.isNetworkAvailable(getActivity())) {

                //final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/

           /* if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
*/

                Call<NewJoinees> wsCallingDownlineRanks =  BusinessActivity.mAPIInterface.getNewJoinees();
                wsCallingDownlineRanks.enqueue(new Callback<NewJoinees>() {
                    @Override
                    public void onResponse(Call<NewJoinees> call, Response<NewJoinees> response) {
                        try {
                            parentFrag.stopAnim(parentFrag.imageViewNewjoinee, txtRetry);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                   /* if (mProgressDialog != null & mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }*/
                        //mSwipeRefreshLayout.setRefreshing(false);

                        if (arrayListNewJoieeLists != null) {
                            arrayListNewJoieeLists.clear();
                            if (response.isSuccessful()) {

                                if (response.code() == 200) {
                                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                                        arrayListNewJoieeLists.addAll(response.body().getData());
                                   /* if (getActivity() != null) {
                                        prefs = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
                                        editor = prefs.edit();
                                        Gson gson = new Gson();
                                        String json = gson.toJson(arrayListNewJoieeLists);
                                        editor.putString("New_joinees", json);
                                        editor.apply();
                                    }*/
                                        newJoineesAdapter = new NewJoineesAdapter(getActivity(), arrayListNewJoieeLists);
                                        listView.setAdapter(newJoineesAdapter);
                                        newJoineesAdapter.notifyDataSetChanged();

                                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                        noRecordsFound();
                                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                            || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                        serviceUnavailable();
                                    }

                                    if (arrayListNewJoieeLists.size() > 0) {
                                        llEmpty.setVisibility(View.GONE);
                                        listView.setVisibility(View.VISIBLE);
                                    } else {
                                        llEmpty.setVisibility(View.VISIBLE);
                                        listView.setVisibility(View.GONE);
                                    }
                                }
                                androidx.core.view.ViewCompat.setNestedScrollingEnabled(listView, true);
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

                    }

                    @Override
                    public void onFailure(Call<NewJoinees> call, Throwable t) {
                        try {
                            parentFrag.stopAnim(parentFrag.imageViewNewjoinee, txtRetry);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //  mProgressDialog.dismiss();
                        // Log.e(TAG, "ERROR : " + t.getMessage());
                        serviceUnavailable();
                    }
                });

            } else {
                try {
                    parentFrag.stopAnim(parentFrag.imageViewNewjoinee, txtRetry);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                noInternetConnection();
                // LoadData();
            }
        }else{
            listView.setVisibility(View.GONE);
            lynologin.setVisibility(View.VISIBLE);
        }

    }

    void initError(View view) {
        llEmpty = (LinearLayout) view.findViewById(R.id.llEmpty);
        //imgError = (ImageView) view.findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView) view.findViewById(R.id.txtErrorTitle);
        txtSubError = (MyTextView) view.findViewById(R.id.txtErrorSubTitle);
        txtRetry = (MyTextView) view.findViewById(R.id.txtRetry);
        txtRetry.setOnClickListener(v -> refreshContent());
    }

    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
        txtSubError.setVisibility(View.GONE);
      //  imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
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
       // imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtSubError.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
    }

    /*public void loadFullData() {
        prefs = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
        ;
        Gson gson = new Gson();
        String json = prefs.getString("New_joinees", null);
        Log.d("Update==","load full data : NewJoinee");
        Type type = new TypeToken<ArrayList<NewJoineeList>>() {
        }.getType();
        arrayListNewJoieeLists = gson.fromJson(json, type);
        if (json != null) {
            if (newJoineesAdapter == null)
                newJoineesAdapter = new NewJoineesAdapter(getActivity(), arrayListNewJoieeLists);
            listView.setAdapter(newJoineesAdapter);
            newJoineesAdapter.notifyDataSetChanged();
        } else {
            refreshContent();
        }
    }*/

    public void LoadData() {
        prefs = getActivity().getSharedPreferences(PrefUtils.prefDash, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("New_joinees", null);
        Type type = new TypeToken<ArrayList<NewJoineeList>>() {
        }.getType();
        arrayListNewJoieeLists = gson.fromJson(json, type);
        if (json != null) {
            if (newJoineesAdapter == null)
                newJoineesAdapter = new NewJoineesAdapter(getActivity(), arrayListNewJoieeLists);
            listView.setAdapter(newJoineesAdapter);
            newJoineesAdapter.notifyDataSetChanged();
        } else {
            noInternetConnection();
        }
    }
}
