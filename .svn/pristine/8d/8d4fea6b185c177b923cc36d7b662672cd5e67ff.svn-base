package com.nebulacompanies.ibo.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.OrderListAdapter;
import com.nebulacompanies.ibo.ecom.fragment.MyTrackorderFragment;
import com.nebulacompanies.ibo.ecom.model.OrderListModel;
import com.nebulacompanies.ibo.ecom.model.OrderListModelEcom;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class OrderListActivity extends AppCompatActivity  {
    MyTextViewEcom txtTitle;
    ImageView imgBack;
    RelativeLayout laytoolbar;
    //Error View
    private RelativeLayout llEmpty;
    private ImageView imgError;
    private MyTextViewEcom txtErrorContent, txtRetry;
    MyBoldTextViewEcom txtErrorTitle;
    RecyclerView recList;
    MaterialProgressBar mProgressDialog;
    ArrayList<OrderListModel> orderListModel = new ArrayList<>();
    private OrderListAdapter myOrderDetailsActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Utils.darkenStatusBar(this, R.color.colorNotification);

        laytoolbar = findViewById(R.id.toolbarlay);
        txtTitle = findViewById(R.id.toolbar_title1);
        imgBack = findViewById(R.id.img_back);
        recList = findViewById(R.id.recycler_view);
        laytoolbar.setVisibility(View.VISIBLE);
        txtTitle.setText("My Orders");
        //setSupportActionBar(toolbar);
        imgBack.setOnClickListener(v -> onBackPressed());
//        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);

        // Error View
        llEmpty = (RelativeLayout) findViewById(R.id.llEmpty);
        imgError = (ImageView) findViewById(R.id.imgError);
        txtErrorTitle = (MyBoldTextViewEcom) findViewById(R.id.txtErrorTitle);
        txtErrorContent = (MyTextViewEcom) findViewById(R.id.txt_error_content);
        txtRetry = (MyTextViewEcom) findViewById(R.id.txtRetry);
        mProgressDialog = findViewById(R.id.progresbar);
        txtRetry.setOnClickListener(v->{
            getOrderList();
        });
        getOrderList();
    }
    APIInterface  mAPIInterfaceTrackOrder;
    private void getOrderList() {
        if (Utils.isNetworkAvailable(OrderListActivity.this)) {

            mProgressDialog.setVisibility(View.VISIBLE);

            mAPIInterfaceTrackOrder = APIClient.getClient(getApplicationContext()).create(APIInterface.class);
            Call<OrderListModelEcom> wsCallingEvents = mAPIInterfaceTrackOrder.getOrderListEcom();
            wsCallingEvents.enqueue(new Callback<OrderListModelEcom>() {
                @Override
                public void onResponse(Call<OrderListModelEcom> call, Response<OrderListModelEcom> response) {
                    // if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.setVisibility(View.GONE);
                    // }
                    // mSwipeRefreshLayout.setRefreshing(false);
                    orderListModel.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            OrderListModelEcom orderListModelEcom = response.body();
                            if (orderListModelEcom.getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                // noRecordsFound();
                            } else if (orderListModelEcom.getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                llEmpty.setVisibility(View.GONE);
                                orderListModel.addAll(orderListModelEcom.getData());
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(OrderListActivity.this);
                                recList.setLayoutManager(mLayoutManager);
                                recList.setItemAnimator(new DefaultItemAnimator());
                                myOrderDetailsActivity = new OrderListAdapter(OrderListActivity.this, orderListModel);
                                //productsAdapter = new ProductsAdapter(getActivity(),productArrayList);
                                recList.setAdapter(myOrderDetailsActivity);

                                Log.e("ORDERListAPI","ORDERListAPI: " +  new Gson().toJson(response.body()));
                            } else if (orderListModelEcom.getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || orderListModelEcom.getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }
                            if (orderListModel.size() > 0) {
                                llEmpty.setVisibility(View.GONE);
                                recList.setVisibility(View.VISIBLE);
                            } else {
                                //   llEmpty.setVisibility(View.VISIBLE);
                                //   listView.setVisibility(View.GONE);
                                recList.setVisibility(View.GONE);
                                noRecordsFound();
                            }
                        }  else {
                            //  serviceUnavailable();
                            recList.setVisibility(View.GONE);
                            noRecordsFound();
                        }
                    }else if (response.code()==-1){
                        recList.setVisibility(View.GONE);
                        noRecordsFound();
                    } else {
                        //  serviceUnavailable();
                        recList.setVisibility(View.GONE);
                        noRecordsFound();
                    }
                }

                @Override
                public void onFailure(Call<OrderListModelEcom> call, Throwable t) {
                    // mSwipeRefreshLayout.setEnabled(false);
                    mProgressDialog.setVisibility(View.GONE);
                    Log.e("ORDERListAPIError","ORDERListAPI: " +  t);
                    if (t.equals( "java.io.IOException: failed to rename" )){
                        recList.setVisibility(View.GONE);
                        //noRecordsFound();
                    }else {
                        recList.setVisibility(View.GONE);
                        noRecordsFound();
                    }

                   /* if (orderListModel.size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noRecordsFound();
                    }*/
                    //  serviceUnavailable();
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    //Error View
    void noRecordsFound() {
        txtErrorTitle.setText("You have not placed any orders yet.");
        txtErrorContent.setText("Looks like you have no orders in your order list.");
        imgError.setImageResource(R.drawable.ic_shopping_bag);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        recList.setVisibility(View.GONE);
    }

    private void noInternetAvailable() {
        recList.setVisibility(View.GONE);
        mProgressDialog.setVisibility(View.GONE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        txtErrorTitle.setText(R.string.error_title);
        txtErrorContent.setText(R.string.error_content);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
    }
}
