package com.nebulacompanies.ibo.ecom.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.MyCouponAdapter;
import com.nebulacompanies.ibo.ecom.model.PromoCodeModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;

public class PromoCodesFragment extends Fragment {

    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface;
    Session session;
    RecyclerView recyclerView;
    MyCouponAdapter myCouponAdapter;
    List<PromoCodeModel.Datum> promodata = new ArrayList<>();
    RelativeLayout llEmpty;
    ImageView imgError;
    MyBoldTextViewEcom txtErrorTitle;
    MyTextViewEcom txtErrorContent, txtRetry;
    private BroadcastReceiver couponReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //you can update textBox here
            String action = intent.getAction();
            if (action.equals(Utils.actionCoupon)) {
                //loadFullData();
                getPromoList();
            }
        }
    };

    public PromoCodesFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_promo_codes, container, false);

        mProgressDialog = view.findViewById(R.id.progresbar);
        recyclerView = view.findViewById(R.id.recycler_view);

        //no records
        llEmpty = view.findViewById(R.id.llEmpty);
        imgError = view.findViewById(R.id.imgError);
        txtErrorTitle = view.findViewById(R.id.txtErrorTitle);
        txtErrorContent = view.findViewById(R.id.txt_error_content);
        txtRetry = view.findViewById(R.id.txtRetry);

        txtRetry.setOnClickListener(v -> getPromoList());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initCouponList();
        getPromoList();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Utils.actionCoupon);
        getActivity().registerReceiver(couponReceiver, intentFilter);

    }

    private void initCouponList() {
        session = new Session(getActivity());
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myCouponAdapter = new MyCouponAdapter(getActivity(), promodata);
        recyclerView.setAdapter(myCouponAdapter);
        recyclerView.setHasFixedSize(true);

    }


    private void getPromoList() {
        if (Utils.isNetworkAvailable(getActivity())) {

            mProgressDialog.setVisibility(View.VISIBLE);

            Call<PromoCodeModel> wsCallingEvents = mAPIInterface.getPromoCodes(session.getIboKeyId());
            wsCallingEvents.enqueue(new Callback<PromoCodeModel>() {
                @Override
                public void onResponse(Call<PromoCodeModel> call, Response<PromoCodeModel> response) {

                    mProgressDialog.setVisibility(View.GONE);
                    if (response.isSuccessful()) {

                        if (response.code() == 200) {
                            assert response.body() != null;
                            PromoCodeModel mData = response.body();
                            promodata.clear();
                            int statuscode = mData.getStatusCode();

                            if (statuscode == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();

                            } else if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                promodata.addAll(mData.getData());
                                myCouponAdapter.notifyDataSetChanged();
                                llEmpty.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }

                        } else if (response.code() == 401) {
                            serviceUnavailable();
                        }

                    } else {

                        serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<PromoCodeModel> call, Throwable t) {

                    serviceUnavailable();
                }
            });
        } else {
            noInternetAvailable();
        }
    }


    void serviceUnavailable() {
        txtErrorTitle.setText(R.string.error_service_unavailable);
        txtErrorContent.setText(R.string.error_coupon);
        txtErrorContent.setVisibility(View.GONE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

    }

    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
        txtErrorContent.setText(R.string.error_promo);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtErrorContent.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    private void noInternetAvailable() {
        recyclerView.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        //  imgError.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        txtErrorContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(couponReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}