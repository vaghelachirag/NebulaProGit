package com.nebulacompanies.ibo.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.SalesAavaas;
import com.nebulacompanies.ibo.activities.SalesDwarka;
import com.nebulacompanies.ibo.activities.SalesHoliday;
import com.nebulacompanies.ibo.adapter.ProductListAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.ProjectDetail;
import com.nebulacompanies.ibo.model.ProjectList;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.view.RecyclerItemClickListener;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.ID_HAWTHORN_DWARKA;
import static com.nebulacompanies.ibo.util.Constants.ID_HOLIDAY;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

/**
 * Created by Sagar Virvani on 20-02-2018.
 */

public class MySalesFragment extends Fragment /*implements AdapterView.OnItemClickListener*/ {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView listView;

    ConnectionDetector cd;
    public static final String TAG = "MySalesFragment";
    Boolean isRefreshed = false;
    private APIInterface mAPIInterface;
    ProductListAdapter productListAdapter;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    public static ArrayList<ProjectList> arrayListProjectList = new ArrayList<>();
    Session session;
    RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog mProgressDialog;
    int ProjectId;
    SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.products, container, false);
        init(view);
        // my_sales_list
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String salesList = prefs.getString("my_sales_list", "");
        if (salesList != null && salesList != "") {
            LoadMySalesData();
        } else {
            getProject();
        }

        return view;
    }

    void init(View view) {
        cd = new ConnectionDetector(getActivity());
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();

        session = new Session(getActivity());
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        initError(view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.products_swipe_refresh_layout);
        listView = (RecyclerView) view.findViewById(R.id.nebula_products);
        DividerItemDecoration myDivider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        myDivider.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_recyclerview));
        listView.addItemDecoration(myDivider);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
        listView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), listView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        switch (arrayListProjectList.get(position).getProjectId()) {
                            case ID_HAWTHORN_DWARKA:
                                Intent i2 = new Intent(getActivity(), SalesDwarka.class);
                                i2.putExtra("ProjectId", arrayListProjectList.get(position).getProjectId());
                                i2.putExtra("ProjectName", arrayListProjectList.get(position).getProjectName());
                                startActivity(i2);
                                break;
                           /* case ID_AAVAAS_CHANGODER:
                            case ID_AAVAAS_CHANGODER_COMPLEX:
                            case ID_AAVAAS_HYDERABD:
                            case ID_AAVAAS_CHENNAI:
                                Intent i1 = new Intent(getActivity(), SalesAavaas.class);
                                i1.putExtra("ProjectId", arrayListProjectList.get(position).getProjectId());
                                i1.putExtra("ProjectName", arrayListProjectList.get(position).getProjectName());
                                startActivity(i1);
                                break;*/

                            case ID_HOLIDAY:
                                Intent i3 = new Intent(getActivity(), SalesHoliday.class);
                                i3.putExtra("ProjectId", arrayListProjectList.get(position).getProjectId());
                                i3.putExtra("ProjectName", arrayListProjectList.get(position).getProjectName());
                                startActivity(i3);
                                break;

                            default:
                                Intent i1 = new Intent(getActivity(), SalesAavaas.class);
                                i1.putExtra("ProjectId", arrayListProjectList.get(position).getProjectId());
                                i1.putExtra("ProjectName", arrayListProjectList.get(position).getProjectName());
                                startActivity(i1);
                               // Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    private void getProject() {
        if (Utils.isNetworkAvailable(getActivity())) {
            mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Call<ProjectDetail> wsCallingProjectDetail = mAPIInterface.getProjectDetail();
            wsCallingProjectDetail.enqueue(new Callback<ProjectDetail>() {
                @Override
                public void onResponse(Call<ProjectDetail> call, Response<ProjectDetail> response) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                    if (arrayListProjectList != null) {
                        arrayListProjectList.clear();
                        if (response.isSuccessful()) {

                            if (response.code() == 200) {
                                if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {

                                    //arrayListProjectList.addAll(response.body().getData());
                                    arrayListProjectList.clear();
                                    for (ProjectList projectList : response.body().getData()) {
                                        ProjectId = projectList.getProjectId();
                                        ArrayList<Integer> ProjectList = new ArrayList<>();
                                        if (ProjectId != ID_HOLIDAY) {
                                            arrayListProjectList.add(projectList);
                                        }
                                    }
                                    prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    SharedPreferences.Editor editor = prefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(arrayListProjectList);
                                    editor.putString("my_sales_list", json);
                                    editor.apply();
                               /* productListAdapter = new ProductListAdapter(getActivity(), arrayListProjectList);
                                listView.setAdapter(productListAdapter);
                                productListAdapter.notifyDataSetChanged();*/
                                    mLayoutManager = new LinearLayoutManager(getActivity());
                                    listView.setLayoutManager(mLayoutManager);
                                    listView.setItemAnimator(new DefaultItemAnimator());
                                    productListAdapter = new ProductListAdapter(getActivity(), arrayListProjectList, "Mysale");
                                    listView.setAdapter(productListAdapter);
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                    noRecordsFound();
                                } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                        || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    serviceUnavailable();


                                    if (arrayListProjectList.size() > 0) {
                                        llEmpty.setVisibility(View.GONE);
                                        listView.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                llEmpty.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.GONE);

                            }
                        } else {
                            serviceUnavailable();
                        }
                    } else {
                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<ProjectDetail> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        } else {
            LoadMySalesData();
        }
    }

    /*private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new MySales(), getResources().getString(R.string.my_sales));
        adapter.addFragment(new MyPurchases(), getResources().getString(R.string.my_purchases));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }*/
    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            if (productListAdapter != null) {
                productListAdapter.clearData();
                productListAdapter.notifyDataSetChanged();
            }
            getProject();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            LoadMySalesData();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

   /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (arrayListProjectList.get(position).getProjectId()) {
            case ID_HAWTHORN_DWARKA:
                Intent i2 = new Intent(getActivity(), SalesDwarka.class);
                i2.putExtra("ProjectId", arrayListProjectList.get(position).getProjectId());
                i2.putExtra("ProjectName", arrayListProjectList.get(position).getProjectName());
                startActivity(i2);
                break;
            case ID_AAVAAS_CHANGODER:
            case ID_AAVAAS_CHANGODER_COMPLEX:
            case ID_AAVAAS_HYDERABD:
                Intent i1 = new Intent(getActivity(), SalesAavaas.class);
                i1.putExtra("ProjectId", arrayListProjectList.get(position).getProjectId());
                i1.putExtra("ProjectName", arrayListProjectList.get(position).getProjectName());
                startActivity(i1);
                break;

            case ID_HOLIDAY:
                Intent i3 = new Intent(getActivity(), SalesHoliday.class);
                i3.putExtra("ProjectId", arrayListProjectList.get(position).getProjectId());
                i3.putExtra("ProjectName", arrayListProjectList.get(position).getProjectName());
                startActivity(i3);
                break;

            default:
                Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_LONG).show();
                break;
        }
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

    public void LoadMySalesData() {
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = prefs.getString("my_sales_list", null);
        Type type = new TypeToken<ArrayList<ProjectList>>() {
        }.getType();
        arrayListProjectList = gson.fromJson(json, type);
        if (json != null) {
            if (productListAdapter == null)
                productListAdapter = new ProductListAdapter(getActivity(), arrayListProjectList, "Mysale");
            mLayoutManager = new LinearLayoutManager(getActivity());
            listView.setLayoutManager(mLayoutManager);
            listView.setItemAnimator(new DefaultItemAnimator());
            listView.setAdapter(productListAdapter);
            productListAdapter.notifyDataSetChanged();
            listView.setVisibility(View.VISIBLE);
            /* listView.setAdapter(productListAdapter);
            productListAdapter.notifyDataSetChanged();
            // lnGold.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);*/

            Log.i("INFO", "Call For arrayList:-" + arrayListProjectList.size());
            Log.i("INFO", "Call For arrayList:-" + arrayListProjectList);
        } else {
            noInternetConnection();
        }
    }

    public void hideSpotLight() {
        if (productListAdapter!=null) {
            productListAdapter.hideSpotLight();
        }
    }
}