
package com.nebulacompanies.ibo.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.preference.PreferenceManager;
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
import com.nebulacompanies.ibo.adapter.MyDownlinePlacementAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.IBOList;
import com.nebulacompanies.ibo.model.IBOListDetails;
import com.nebulacompanies.ibo.model.IBOListPlacement;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.EndlessScrollListener;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

/**
 * Created by Palak Mehta on 19-Apr-18.
 */

public class IBOListHolidays extends Fragment implements/* AsyncResponse,*/ SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView lview;
    MyDownlinePlacementAdapter myDownlineAdapter;
    SearchView mSearchView;
    Session session;
    Boolean isRefreshed = false;

    private APIInterface mAPIInterface;
    public static final String TAG = "PlacementTree";
    ConnectionDetector cd;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    IBOList iboList;
    int totalRecords = 5000;
    private ArrayList<IBOListDetails> arrayListPlacementTreeList = new ArrayList<>();

    int startIndex = 0;
    int pageIndex = 1;
    int pageLength = 15;
    public int firstVisibleItem_, visibleItemCount_, totalItemCount_;
    private View mFooterView;
    private LinearLayout lnPlacementTree;
    SharedPreferences.Editor editor;
    ArrayList<String> IboList = new ArrayList<>();
    SharedPreferences prefs;
    int totalListCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_placement_tree, container, false);
        init(view);


        arrayListPlacementTreeList.clear();
        Parcelable state = lview.onSaveInstanceState();
        lview.setAdapter(myDownlineAdapter);
        lview.onRestoreInstanceState(state);

            getPlacementTreeList(pageIndex, pageLength, "");

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            return;
        session = new Session(getActivity());

    }

    private void init(View view) {
        cd = new ConnectionDetector(getActivity());
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        initError(view);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.placement_swipe_refresh_layout);
        lview = (ListView) view.findViewById(R.id.listview_my_downline);
        mSearchView = (SearchView) view.findViewById(R.id.search_view_placement);
        lnPlacementTree = (LinearLayout) view.findViewById(R.id.ln_placement_tree);
        // mSearchView.setOnCloseListener(this);
        lview.setTextFilterEnabled(true);
        //  mSearchView.clearFocus();

        mSearchView.setQuery("", false);
        mSearchView.clearFocus();
        setupSearchView();

        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.loading_view, null);
        lview.addFooterView(mFooterView);

        lview.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(final int page, int totalItemsCount) {


                if (Utils.isNetworkAvailable(getActivity())) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //getPlacementTreeList(page, pageLength, "");
                            if (totalRecords > 15) {
                                if (mSearchView.getQuery().toString().length() > 0) {
                                    //  getPlacementTreeList(page, pageLength, mSearchView.getQuery().toString());
                                    //getPlacementTreeList(page, pageLength, "");
                                } else {

                                    Parcelable state = lview.onSaveInstanceState();
                                    lview.setAdapter(myDownlineAdapter);
                                    lview.onRestoreInstanceState(state);
                                        getPlacementTreeList(page, pageLength, "");

                                }
                            }

                        }
                    }, 1000);
                }


                Log.i(TAG, "onLoadMore page and length: " + page + "," + pageLength + "," + totalItemsCount);
                totalListCount = totalItemsCount;
                // return true;
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


    private void getPlacementTreeList(int page_index, final int page_length, String search_text) {
        if (Utils.isNetworkAvailable(getActivity())) {
            //  final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                // mProgressDialog.show();
            }
          /*  mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));*/
            Call<IBOListPlacement> wsCallingPlacementTreeDetails = mAPIInterface.getPlacementTree(page_index, page_length, search_text);
            wsCallingPlacementTreeDetails.enqueue(new Callback<IBOListPlacement>() {
                @Override
                public void onResponse(Call<IBOListPlacement> call, Response<IBOListPlacement> response) {
                  /*  if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }*/
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {
                        //arrayListPlacementTreeList.clear();

                        lnPlacementTree.setVisibility(View.VISIBLE);
                        if (response.code() == 200) {

                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                               /* if (mProgressDialog.isShowing()){
                                    mProgressDialog.dismiss();
                                }*/
                                showRecords();

                                iboList = response.body().getData();
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                editor = prefs.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(response.body().getData());
                                editor.putString("placement", json);
                                editor.apply();
                                totalRecords = iboList.getTotalRecord();
                               /* HashSet<IBOListDetails> hashSet = new HashSet<IBOListDetails>();
                                hashSet.addAll(iboList.getMyDownline());
                                arrayListPlacementTreeList.clear();
                                arrayListPlacementTreeList.addAll(hashSet);*/
                                /*for (IBOListDetails iboListDetails:iboList.getMyDownline())
                                {
                                    String iboid=iboListDetails.getIBOID();
                                    if (iboid.contains(iboListDetails.getIBOID()))
                                    {
                                        arrayListPlacementTreeList.addAll(arrayListPlacementTreeList);
                                    }
                                }*/

                                arrayListPlacementTreeList.addAll(iboList.getMyDownline());
                                /*HashSet<IBOListDetails> hashSet = new HashSet<IBOListDetails>();
                                hashSet.addAll(iboList.getMyDownline());
                                arrayListPlacementTreeList.clear();
                                arrayListPlacementTreeList.addAll(hashSet);*/

                               /* Set<IBOListDetails> set = new HashSet<>(arrayListPlacementTreeList);
                                arrayListPlacementTreeList.clear();
                                arrayListPlacementTreeList.addAll(set);*/

                                myDownlineAdapter = new MyDownlinePlacementAdapter(getActivity(), arrayListPlacementTreeList);
                                Parcelable state = lview.onSaveInstanceState();
                                lview.setAdapter(myDownlineAdapter);
                                lview.onRestoreInstanceState(state);
                                // myDownlineAdapter.notifyDataSetChanged();
                                // lview.setSelection(totalListCount);

                                /*myDownlineAdapter.registerDataSetObserver(new DataSetObserver() {
                                    @Override
                                    public void onChanged() {
                                        super.onChanged();
                                        lview.setSelection(myDownlineAdapter.getCount() - 1);
                                    }
                                });*/

                                /*lview.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                                lview.setStackFromBottom(true);*/

                                float ceil = (float) totalRecords / (float) page_length;
                                int roundInt = (int) Math.ceil(ceil);
                                //lview.smoothScrollToPosition(15);
                                //lview.smoothScrollToPosition(myDownlineAdapter.getCount());
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // mProgressDialog.dismiss();
                                        lview.removeFooterView(mFooterView);
                                    }
                                }, 10000);

                             /*   if (page_index>1)
                                {
                                    startIndex=startIndex+10;
                                    lview.smoothScrollToPosition(startIndex);
                                }*/

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

                        /*else if (response.code() == 401) {

                        }

                        else if (response.code() == 500) {

                        }*/
                    } else {
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<IBOListPlacement> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    // mProgressDialog.dismiss();
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
        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            if (myDownlineAdapter != null) {
                //startIndex=0;
                mSearchView.setQuery("", true);
                mSearchView.clearFocus();
                myDownlineAdapter.clearData();
                myDownlineAdapter.notifyDataSetChanged();
            }

            lview.addFooterView(mFooterView);
            arrayListPlacementTreeList.clear();
            Parcelable state = lview.onSaveInstanceState();
            lview.setAdapter(myDownlineAdapter);
            lview.onRestoreInstanceState(state);
            getPlacementTreeList(pageIndex, pageLength, "");

            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            /*noInternetConnection();*/
            arrayListPlacementTreeList.clear();
            LoadData();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mSearchView.clearFocus();
        return true;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Load your data here or do network operations here
            if (mSearchView != null) {
                mSearchView.setQuery("", false);
                mSearchView.clearFocus();
            }
        }
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        if (newText.length() == 0) {
            arrayListPlacementTreeList.clear();
            getPlacementTreeList(pageIndex, pageLength, "");
        } else {
            myDownlineAdapter.getFilter().filter(newText);
        }
        return true;
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
        lnPlacementTree.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        lview.setVisibility(View.GONE);
    }

    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lnPlacementTree.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
    }

    void noInternetConnection() {
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lnPlacementTree.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
    }

    void showRecords() {
        llEmpty.setVisibility(View.GONE);
        txtRetry.setVisibility(View.GONE);
        lview.setVisibility(View.VISIBLE);
    }

    public void LoadData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = prefs.getString("placement", null);
        IBOList iboList = gson.fromJson(json, IBOList.class);
        if (json != null) {
            lnPlacementTree.setVisibility(View.VISIBLE);
            totalRecords = iboList.getTotalRecord();
            arrayListPlacementTreeList.addAll(iboList.getMyDownline());

            myDownlineAdapter = new MyDownlinePlacementAdapter(getActivity(), arrayListPlacementTreeList);
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