package com.nebulacompanies.ibo.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.MyDownlineSponsorAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.IBOList;
import com.nebulacompanies.ibo.model.IBOListDetails;
import com.nebulacompanies.ibo.model.IBOListSponsor;
import com.nebulacompanies.ibo.util.ConnectionDetector;
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
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

/**
 * Created by Palak Mehta on 20-Apr-18.
 */

public class IBOListSponsorTree extends Fragment implements /*AsyncResponse,*/ SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView lview;
    MyDownlineSponsorAdapter myDownlineAdapter;
    SearchView mSearchView;

    Session session;
    Boolean isRefreshed = false;

    private APIInterface mAPIInterface;
    public static final String TAG = "SponsorTree";
    ConnectionDetector cd;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    IBOList iboList;
    int totalRecords = 0;
    private ArrayList<IBOListDetails> arrayListIBOList = new ArrayList<>();

    int startIndex = 0;
    int pageIndex = 1;
    int pageLength = 15;
    public int firstVisibleItem_, visibleItemCount_, totalItemCount_;
    private View mFooterView;
    private LinearLayout lnSponserTree;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    int totalListSponsorCount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sponsor_tree, container, false);
        init(view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            return;
        session = new Session(getActivity());
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//

        if (isVisibleToUser) {
            if (myDownlineAdapter == null) {

                    //getSponsorTreeList();
                    prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    String name = prefs.getString("sponsorList", "");
                    if (name != null && name != "") {
                        arrayListIBOList.clear();
                        // showRecords();
                        LoadData();
                    } else {
                        if (Utils.isNetworkAvailable(getContext())) {
                        arrayListIBOList.clear();
                        getSponsorTreeList(pageIndex, pageLength, "");
                    }else {
                            LoadData();
                        }
                    }

                }
            if (mSearchView != null) {
                mSearchView.setQuery("", false);
                mSearchView.clearFocus();
            }
        }
    }

    private void init(View view) {
        cd = new ConnectionDetector(getActivity());
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        initError(view);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sponsor_swipe_refresh_layout);
        lview = (ListView) view.findViewById(R.id.listview_my_downline1);
        mSearchView = (SearchView) view.findViewById(R.id.search_view_sponsor);
        lnSponserTree = (LinearLayout) view.findViewById(R.id.ln_sponser_tree);

        mSearchView.setOnCloseListener(this);
        lview.setTextFilterEnabled(true);
        setupSearchView();

        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.loading_view, null);
        lview.addFooterView(mFooterView);

        /*lview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lview.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(lview.getFirstVisiblePosition() == 0 && lview.getChildAt(0).getTop() == 0);
                }
            }
        }); */

        lview.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(final int page, int totalItemsCount) {
                if (Utils.isNetworkAvailable(getContext())) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (totalRecords > 15) {
                                if (mSearchView.getQuery().toString().length() > 0) {
                                  //  getSponsorTreeList(page, pageLength, mSearchView.getQuery().toString());
                                } else {

                                    Parcelable state = lview.onSaveInstanceState();
                                    lview.setAdapter(myDownlineAdapter);
                                    lview.onRestoreInstanceState(state);
                                    getSponsorTreeList(page, pageLength, "");
                                }
                            }

                        }
                    }, 1000);
                }

                Log.i(TAG, "onLoadMore page and length: " + page + "," + pageLength + "," + totalItemsCount);
                totalListSponsorCount = totalItemsCount;
               return true;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);

                if (lview.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(lview.getFirstVisiblePosition() == 0 && lview.getChildAt(0).getTop() == 0);
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                super.onScrollStateChanged(view, scrollState);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void getSponsorTreeList(int page_index, int page_length, String search_text) {
        if (Utils.isNetworkAvailable(getContext())) {
            // final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
           /* if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
           */
            mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
            Call<IBOListSponsor> wsCallingSponsorTreeDetails = mAPIInterface.getSponsortTree(page_index, page_length, search_text);
            wsCallingSponsorTreeDetails.enqueue(new Callback<IBOListSponsor>() {
                @Override
                public void onResponse(Call<IBOListSponsor> call, Response<IBOListSponsor> response) {
                  /*  if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }*/
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (arrayListIBOList != null) {
                        // arrayListIBOList.clear();


                        if (response.isSuccessful()) {
                            lnSponserTree.setVisibility(View.VISIBLE);
                            //arrayListIBOList.clear();

                            if (response.code() == 200) {
                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                    //mProgressDialog.dismiss();
                                    showRecords();

                                    iboList = response.body().getData();
                                    if (getActivity()!=null) {
                                        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                        editor = prefs.edit();
                                        Gson gson = new Gson();
                                        String json = gson.toJson(response.body().getData());
                                        editor.putString("sponsorList", json);
                                        editor.apply();
                                    }
                                    totalRecords = iboList.getTotalRecord();
                                    arrayListIBOList.addAll(iboList.getMyDownline());

                                    myDownlineAdapter = new MyDownlineSponsorAdapter(getActivity(), arrayListIBOList);
                                    Parcelable state = lview.onSaveInstanceState();
                                    lview.setAdapter(myDownlineAdapter);
                                    lview.onRestoreInstanceState(state);


                                    /*lview.setAdapter(myDownlineAdapter);
                                    myDownlineAdapter.notifyDataSetChanged();*/
                                   // lview.setSelection(totalListSponsorCount);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // mProgressDialog.dismiss();
                                            lview.removeFooterView(mFooterView);
                                        }
                                    }, 10000);

                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                                    iboList = response.body().getData();
                                    totalRecords = iboList.getTotalRecord();
                                    if (totalRecords == 0) {
                                        noRecordsFound();
                                    }

                                    lview.removeFooterView(mFooterView);
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    serviceUnavailable();
                                }
                            } else {
                                serviceUnavailable();
                            }

                        }
                    /*else if (response.code() == 401) {

                        }

                        else if (response.code() == 500) {

                        }*/
                    } else {
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<IBOListSponsor> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    //   mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        } else {
            /*noInternetConnection();*/
            LoadData();
        }

    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search");
    }

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getContext())) {
            isRefreshed = true;
            if (myDownlineAdapter != null) {
                mSearchView.setQuery("", true);
                mSearchView.clearFocus();
                myDownlineAdapter.clearData();
                myDownlineAdapter.notifyDataSetChanged();
            }
            lview.addFooterView(mFooterView);
            arrayListIBOList.clear();
            getSponsorTreeList(pageIndex, pageLength, "");

            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            /*noInternetConnection();*/
            arrayListIBOList.clear();
            LoadData();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {

        if (newText.length()==0){
            arrayListIBOList.clear();
            getSponsorTreeList(pageIndex, pageLength, "");
        }
        else {
            try {
                myDownlineAdapter.getFilter().filter(newText);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return false;
    }

    @Override
    public boolean onClose() {
        lview.addFooterView(mFooterView);
        mSearchView.setQuery("", true);
        mSearchView.clearFocus();
        return true;
    }

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
        lview.setVisibility(View.GONE);
        lnSponserTree.setVisibility(View.VISIBLE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
        lnSponserTree.setVisibility(View.VISIBLE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
        lnSponserTree.setVisibility(View.VISIBLE);
    }

    void showRecords() {
        llEmpty.setVisibility(View.GONE);
        txtRetry.setVisibility(View.GONE);
        lview.setVisibility(View.VISIBLE);
        lnSponserTree.setVisibility(View.VISIBLE);
    }

    public void LoadData() {
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = prefs.getString("sponsorList", null);
        IBOList iboList = gson.fromJson(json, IBOList.class);
        if (json != null) {
            showRecords();
            arrayListIBOList.clear();
            totalRecords = iboList.getTotalRecord();
            arrayListIBOList.addAll(iboList.getMyDownline());

            myDownlineAdapter = new MyDownlineSponsorAdapter(getActivity(), arrayListIBOList);
            lview.setAdapter(myDownlineAdapter);
            myDownlineAdapter.notifyDataSetChanged();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // mProgressDialog.dismiss();
                    lview.removeFooterView(mFooterView);
                }
            }, 500);

        } else {
            noInternetConnection();
        }
    }
}