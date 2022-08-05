package com.nebulacompanies.ibo.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.CustomerSupportCategoryAdaper;
import com.nebulacompanies.ibo.ecom.CustomerSupport;
import com.nebulacompanies.ibo.ecom.ProductDescription;
import com.nebulacompanies.ibo.ecom.adapter.CustomerSupportAdapter;

import com.nebulacompanies.ibo.ecom.adapter.ProductColorsAdapter;
import com.nebulacompanies.ibo.ecom.adapter.ProductWeightAdapter;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportCategory;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportTicket;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.io.File;
import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;

public class RaiseQueryScreen extends Fragment {

    Session sessionNormal;
    MaterialProgressBar progressBar;
    MyTextViewEcom tvOrder, tvProfileChanges, tvPayout, tvLogin, tvBank, tvBvNvpv;
    RecyclerView recyclerView;
    APIInterface mAPIInterface;
    Utils utils = new Utils();
    CustomerSupportCategoryAdaper customerSupportCategoryAdaper;

    ArrayList<CustomerSupportCategory.SupportList> supportListArrayList = new ArrayList<>();

    LinearLayout layEmpty, layQuery;
    MyTextView txTitle, txtSubtitle, txtRetry;
    ImageView imgError;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.raise_query, container, false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        sessionNormal = new Session(getActivity());
        if (!sessionNormal.getLogin())
            utils.openNoSession(getActivity(), utils.gotoIbosupport);
        init(view);
        initOnClick();
        if (sessionNormal.getLogin()) {
            initCategoryList();
            getCustomerSupporttCategoryList();
        }
        return view;
    }

    void init(View view) {

        mAPIInterface = APIClient.getClient(getContext()).create(APIInterface.class);
        progressBar = view.findViewById(R.id.progresbar);
        tvOrder = view.findViewById(R.id.tv_orders);
        tvProfileChanges = view.findViewById(R.id.tv_profile_changes);
        tvPayout = view.findViewById(R.id.tv_payout);
        tvLogin = view.findViewById(R.id.tv_login);
        tvBank = view.findViewById(R.id.tv_bank_detail);
        tvBvNvpv = view.findViewById(R.id.tv_nv_bv_pv);
        recyclerView = view.findViewById(R.id.rv_cs_category);
        layQuery = view.findViewById(R.id.lay_query);

        layEmpty = view.findViewById(R.id.llEmpty1);
        txTitle = view.findViewById(R.id.txtErrorTitle1);
        txtSubtitle = view.findViewById(R.id.txtErrorsubTitle1);
        imgError = view.findViewById(R.id.imgError1);
        txtRetry = view.findViewById(R.id.txtRetry1);
        layEmpty.setVisibility(View.GONE);
        txtRetry.setOnClickListener(v -> {
            if (!sessionNormal.getLogin()) {
                utils.openLogin(getActivity());
            } else {
                getCustomerSupporttCategoryList();
            }

        });

    }


    private void initCategoryList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        customerSupportCategoryAdaper = new CustomerSupportCategoryAdaper(getContext(), supportListArrayList);
        recyclerView.setAdapter(customerSupportCategoryAdaper);
        recyclerView.setHasFixedSize(true);

    }


    void initOnClick() {
        tvOrder.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getActivity())) {
                Intent intent = new Intent(getActivity(), CustomerSupport.class);
                startActivity(intent);
            } else {
                utils.dialogueNoInternet(getActivity());
            }
        });
        tvProfileChanges.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getActivity())) {
                Intent intent = new Intent(getActivity(), CustomerSupport.class);
                startActivity(intent);
            } else {
                utils.dialogueNoInternet(getActivity());
            }
        });
        tvPayout.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getActivity())) {
                Intent intent = new Intent(getActivity(), CustomerSupport.class);
                startActivity(intent);
            } else {
                utils.dialogueNoInternet(getActivity());
            }
        });
        tvLogin.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getActivity())) {
                Intent intent = new Intent(getActivity(), CustomerSupport.class);
                startActivity(intent);
            } else {
                utils.dialogueNoInternet(getActivity());
            }
        });
        tvBank.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getActivity())) {
                Intent intent = new Intent(getActivity(), CustomerSupport.class);
                startActivity(intent);
            } else {
                utils.dialogueNoInternet(getActivity());
            }
        });
        tvBvNvpv.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getActivity())) {
                Intent intent = new Intent(getActivity(), CustomerSupport.class);
                startActivity(intent);
            } else {
                utils.dialogueNoInternet(getActivity());
            }
        });

    }


    private void getCustomerSupporttCategoryList() {

        if (Utils.isNetworkAvailable(getContext())) {
            Call<CustomerSupportCategory> wsCallingEvents = mAPIInterface.getSupportCategoryList();
            wsCallingEvents.enqueue(new Callback<CustomerSupportCategory>() {
                @Override
                public void onResponse(Call<CustomerSupportCategory> call, Response<CustomerSupportCategory> response) {
                    int statuscode = 0;
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            assert response.body() != null;
                            CustomerSupportCategory mData = response.body();
                            statuscode = mData.getStatusCode();
                            if (statuscode == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noDataFound();
                            } else if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                recyclerView.setVisibility(View.VISIBLE);
                                supportListArrayList.clear();
                                supportListArrayList.addAll(mData.getData());
                                customerSupportCategoryAdaper.notifyDataSetChanged();
                                showDataFound();
                                // Toast.makeText(getContext(), "API called", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<CustomerSupportCategory> call, Throwable t) {
                    noDataFound();
                }
            });
        } else {
            noInternetConnection();
        }
    }

    private void noInternetConnection() {
        txTitle.setText(R.string.error_title);
        txtSubtitle.setText(R.string.error_content);
        txtRetry.setText(R.string.error_retry);
        txtRetry.setVisibility(View.VISIBLE);
        layEmpty.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void noDataFound() {
        txTitle.setText("No Record Found");
        txtSubtitle.setVisibility(View.GONE);
        txtSubtitle.setText(R.string.error_content);
        txtRetry.setText(R.string.error_retry);
        txtRetry.setVisibility(View.VISIBLE);
        layEmpty.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    void noLogin() {
        txTitle.setVisibility(View.GONE);
        txTitle.setText(R.string.login_title);
        txtSubtitle.setText(R.string.login_content);
        txtRetry.setText(R.string.login_retry);
        imgError.setImageResource(R.drawable.ic_baseline_login_24);
        layQuery.setVisibility(View.GONE);
        layEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtSubtitle.setVisibility(View.VISIBLE);
    }

    private void showDataFound() {
        layEmpty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

}
