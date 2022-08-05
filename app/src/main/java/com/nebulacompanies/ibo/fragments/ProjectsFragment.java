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
import com.nebulacompanies.ibo.activities.ProductListHyderabad;
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

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());


/**
 * Created by Sagar Virvani on 22-02-2018.
 */

public class ProjectsFragment extends Fragment /*implements AdapterView.OnItemClickListener*/ {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView listView;

    ConnectionDetector cd;
    public static final String TAG = "ProjectsFragment";
    Boolean isRefreshed = false;
    private APIInterface mAPIInterface;
    ProductListAdapter productListAdapter;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;
    Session session;
    RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<ProjectList> arrayListProjectList = new ArrayList<>();
    String ProjectName;
    SharedPreferences prefs;

    public static ProjectsFragment newInstance() {
        ProjectsFragment f = new ProjectsFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.products, container, false);
        init(view);


        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String projectList = prefs.getString("projects_list", "");
        if (projectList != null && projectList != "") {
            LoadProjectData();
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

      /*  listView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), listView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        switch (arrayListProjectList.get(position).getProjectId()) {
                           *//* case ID_HAWTHORN_DWARKA:
                                Intent i2 = new Intent(getActivity(), ProductListDwarka.class);
                                i2.putExtra("ProjectId", arrayListProjectList.get(position).getProjectId());
                                startActivity(i2);
                                break;*//*

                            case ID_AAVAAS_CHANGODER_COMPLEX:
                            case ID_AAVAAS_HYDERABD:
                            case ID_HOLIDAY:
                            case ID_AAVAAS_CHENNAI:
                            case ID_AAVAAS_CHANGODER:
                            case ID_HAWTHORN_DWARKA:
                                Intent i3 = new Intent(getActivity(), ProductListHyderabad.class);
                                i3.putExtra("ProjectId", arrayListProjectList.get(position).getProjectId());
                                i3.putExtra("ProjectName", arrayListProjectList.get(position).getProjectName());
                                startActivity(i3);
                                break;
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );*/


        listView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), listView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent i3 = new Intent(getActivity(), ProductListHyderabad.class);
                        i3.putExtra("ProjectId", arrayListProjectList.get(position).getProjectId());
                        i3.putExtra("ProjectName", arrayListProjectList.get(position).getProjectName());
                        startActivity(i3);
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
            final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
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
                                    for (ProjectList projectList : response.body().getData()) {
                                        ProjectName = projectList.getProjectName();
                                        ArrayList<String> ProjectList = new ArrayList<>();
                                        if (!ProjectName.equalsIgnoreCase("Holiday")) {
                                            arrayListProjectList.add(projectList);
                                        }

                                    }
                                    prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    SharedPreferences.Editor editor = prefs.edit();
                                    Gson gson = new Gson();
                                    String json = gson.toJson(arrayListProjectList);
                                    editor.putString("projects_list", json);
                                    editor.apply();
                               /* productListAdapter = new ProductListAdapter(getActivity(), arrayListProjectList);
                                listView.setAdapter(productListAdapter);
                                productListAdapter.notifyDataSetChanged();*/
                                    mLayoutManager = new LinearLayoutManager(getActivity());
                                    listView.setLayoutManager(mLayoutManager);
                                    listView.setItemAnimator(new DefaultItemAnimator());
                                    productListAdapter = new ProductListAdapter(getActivity(), arrayListProjectList, "MyProject");
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
                    listView.setVisibility(View.VISIBLE);
                    serviceUnavailable();
                }
            });
        } else {
            LoadProjectData();
        }

    }

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
            mSwipeRefreshLayout.setRefreshing(false);
            LoadProjectData();
        }
    }

   /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (arrayListProjectList.get(position).getProjectId()) {
            case ID_HAWTHORN_DWARKA:
                Intent i2 = new Intent(getActivity(), ProductListDwarka.class);
                i2.putExtra("ProjectId", arrayListProjectList.get(position).getProjectId());
                startActivity(i2);
                break;

            case ID_AAVAAS_CHANGODER_COMPLEX:
            case ID_AAVAAS_HYDERABD:
            case ID_HOLIDAY:
            case ID_AAVAAS_CHENNAI:
            case ID_AAVAAS_CHANGODER:
                Intent i3 = new Intent(getActivity(), ProductListHyderabad.class);
                i3.putExtra("ProjectId", arrayListProjectList.get(position).getProjectId());
                i3.putExtra("ProjectName",arrayListProjectList.get(position).getProjectName());
                startActivity(i3);
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
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProject();
            }
        });
    }

    public void LoadProjectData() {
         prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = prefs.getString("projects_list", null);
        Type type = new TypeToken<ArrayList<ProjectList>>() {
        }.getType();
        arrayListProjectList = gson.fromJson(json, type);
        if (json != null) {
            if (productListAdapter == null)
                productListAdapter = new ProductListAdapter(getActivity(), arrayListProjectList, "MyProject");

            mLayoutManager = new LinearLayoutManager(getActivity());
            listView.setLayoutManager(mLayoutManager);
            listView.setItemAnimator(new DefaultItemAnimator());
            listView.setAdapter(productListAdapter);
            productListAdapter.notifyDataSetChanged();
            listView.setVisibility(View.VISIBLE);
            Log.i("INFO", "Call For arrayList:-" + arrayListProjectList.size());
            Log.i("INFO", "Call For arrayList:-" + arrayListProjectList);
        } else {
            noInternetConnection();
        }
    }

    public void hideSpotLight() {
        if (productListAdapter!=null){
            productListAdapter.hideSpotLight();
        }

    }


}