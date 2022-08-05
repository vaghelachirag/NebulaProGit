package com.nebulacompanies.ibo.ecom.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

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
import com.nebulacompanies.ibo.ecom.adapter.GenerateCouponAdapter;
import com.nebulacompanies.ibo.ecom.model.GenerateCouponModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;


public class GenerateCouponFragment extends Fragment {
    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface;
    Session session;
    RecyclerView recyclerView;
    GenerateCouponAdapter generateCouponAdapter;
    ArrayList<GenerateCouponModel.Datum> mCouponList = new ArrayList<>();
    RelativeLayout llEmpty;
    ImageView imgError;
    MyBoldTextViewEcom txtErrorTitle;
    MyTextViewEcom txtErrorContent, txtRetry;
    ProgressDialog mLoadProgressDialog;

    public GenerateCouponFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_generate_coupon, container, false);
        initUI(view);
        initCouponList();
        getCouponList();


        return view;
    }



    void initUI(View view) {
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        mProgressDialog = view.findViewById(R.id.progresbar);
        recyclerView = view.findViewById(R.id.recycler_view);
        //no records
        llEmpty = view.findViewById(R.id.llEmpty);
        imgError = view.findViewById(R.id.imgError);
        txtErrorTitle = view.findViewById(R.id.txtErrorTitle);
        txtErrorContent = view.findViewById(R.id.txt_error_content);
        txtRetry = view.findViewById(R.id.txtRetry);

        txtRetry.setOnClickListener(v -> {
            getCouponList();
        });
    }

    private void initCouponList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        generateCouponAdapter = new GenerateCouponAdapter(getActivity(), mCouponList);
        recyclerView.setAdapter(generateCouponAdapter);
        recyclerView.setHasFixedSize(true);

    }

    private void getCouponList() {
        if (Utils.isNetworkAvailable(getActivity())) {

            mProgressDialog.setVisibility(View.VISIBLE);

            Call<GenerateCouponModel> wsCallingEvents = mAPIInterface.getCoupon();
            wsCallingEvents.enqueue(new Callback<GenerateCouponModel>() {
                @Override
                public void onResponse(Call<GenerateCouponModel> call, Response<GenerateCouponModel> response) {

                    mProgressDialog.setVisibility(View.GONE);
                    if (response.isSuccessful()) {

                        if (response.code() == 200) {
                            assert response.body() != null;
                            GenerateCouponModel mData = response.body();
                            mCouponList.clear();
                            int statuscode = mData.getStatusCode();

                            if (statuscode == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                mCouponList.addAll(mData.getData());
                                generateCouponAdapter.notifyDataSetChanged();
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
                public void onFailure(Call<GenerateCouponModel> call, Throwable t) {
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
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        txtErrorContent.setVisibility(View.GONE);
    }

    void noRecordsFound() {
        txtErrorTitle.setText(R.string.error_no_records);
        txtErrorContent.setText(R.string.error_coupon);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        txtErrorContent.setVisibility(View.VISIBLE);
    }

    private void noInternetAvailable() {
        recyclerView.setVisibility(View.GONE);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        txtErrorContent.setVisibility(View.VISIBLE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        //  imgError.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
    }
}