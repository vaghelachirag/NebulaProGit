package com.nebulacompanies.ibo.ecom;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Objects;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;

public class GenerateCoupon extends AppCompatActivity {

    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface;
    Session session;
    Toolbar toolbar;
    MyTextViewEcom tvToolbarTitle;
    ImageView imgBackArrow;
    RecyclerView recyclerView;
    GenerateCouponAdapter generateCouponAdapter;
    private ArrayList<GenerateCouponModel.Datum> mCouponList =new ArrayList<>();
    RelativeLayout llEmpty;
    ImageView imgError;
    MyBoldTextViewEcom txtErrorTitle;
    MyTextViewEcom txtErrorContent, txtRetry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_coupon);
        Utils.darkenStatusBar(this,R.color.colorNotification);

        initUI();
        initCouponList();
        getCouponList();
    }

    void  initUI(){
        mProgressDialog = findViewById(R.id.progresbar);

        toolbar = findViewById(R.id.toolbar_search);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("Generate Coupon");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        imgBackArrow = findViewById(R.id.img_back);

        recyclerView = findViewById(R.id.recycler_view);


        imgBackArrow.setOnClickListener(view -> onBackPressed());

        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        //no records
        llEmpty = findViewById(R.id.llEmpty);
        imgError = findViewById(R.id.imgError);
        txtErrorTitle = findViewById(R.id.txtErrorTitle);
        txtErrorContent = findViewById(R.id.txt_error_content);
        txtRetry = findViewById(R.id.txtRetry);

        txtRetry.setOnClickListener(v -> {
            getCouponList();
        });
    }

    private void initCouponList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        generateCouponAdapter = new GenerateCouponAdapter(this, mCouponList);
        recyclerView.setAdapter(generateCouponAdapter);
        recyclerView.setHasFixedSize(true);

    }
    private void getCouponList() {
        if (Utils.isNetworkAvailable(this)) {

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