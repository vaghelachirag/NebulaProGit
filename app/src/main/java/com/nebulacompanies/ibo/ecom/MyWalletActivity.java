package com.nebulacompanies.ibo.ecom;

import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.WalletAdapter;
import com.nebulacompanies.ibo.ecom.model.WalletHistory;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWalletActivity extends AppCompatActivity {
    Toolbar toolbar;
    MyTextViewEcom tvToolbarTitle;
    ImageView imgBackArrow;
    APIInterface mAPIInterface;
    Session session;
    MaterialProgressBar mProgressDialog;
    String userID;
    RecyclerView recyclerView;
    LinearLayout layEmpty;
    MyTextView txTitle, txtSubtitle, txtRetry;
    MyBoldTextViewEcom txtWallet;
    HorizontalScrollView horizontalScrollView;
ImageView imgError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_history);

        Utils.darkenStatusBar(this, R.color.colorNotification);
        toolbar = findViewById(R.id.toolbar_search);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        imgBackArrow = findViewById(R.id.img_back);
        mProgressDialog = findViewById(R.id.progresbar);
        layEmpty = findViewById(R.id.llEmpty);
        txTitle = findViewById(R.id.txtErrorTitle);
        txtSubtitle = findViewById(R.id.txtErrorSubTitle);
        imgError = findViewById(R.id.imgError);
        txtRetry = findViewById(R.id.txtRetry);
        txtWallet = findViewById(R.id.tv_balance);
        horizontalScrollView = findViewById(R.id.horizontal_view);

        tvToolbarTitle.setText("E-wallet statement");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imgBackArrow.setOnClickListener(view -> onBackPressed());
        recyclerView = findViewById(R.id.rv_wallet);
        layEmpty.setVisibility(View.GONE);
        horizontalScrollView.setVisibility(View.GONE);
       /* layEmpty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);*/

        txtRetry.setOnClickListener(v -> callWalletAPI());
        initData();
        initWalletList();
        callWalletAPI();
    }

    ArrayList<WalletHistory.Datum> dataList = new ArrayList<>();
    WalletAdapter walletAdapter;

    private void initWalletList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       /* walletAdapter = new WalletAdapter(this, dataList);
        recyclerView.setAdapter(walletAdapter);*/
    }

    private void callWalletAPI() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);
            Call<WalletHistory> wsCallingEvents = mAPIInterface.getWallethistory(userID);
            wsCallingEvents.enqueue(new Callback<WalletHistory>() {
                @Override
                public void onResponse(Call<WalletHistory> call, Response<WalletHistory> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            assert response.body() != null;
                            WalletHistory mdata = response.body();
                            dataList = (ArrayList<WalletHistory.Datum>) mdata.getData();
                            if (dataList.size() > 0) {
                                ArrayList<WalletHistory.Datum> mdataList = new ArrayList<>();
                                WalletHistory.Datum mdatam = new WalletHistory().new Datum();
                                mdataList.add(mdatam);
                                mdataList.addAll(dataList);
                                walletAdapter = new WalletAdapter(MyWalletActivity.this, mdataList);
                                recyclerView.setAdapter(walletAdapter);
                                showDataFound();
                            } else {
                                noDataFound();
                            }
                        } else {
                            noDataFound();
                        }
                    } else {
                        noDataFound();
                    }
                    mProgressDialog.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<WalletHistory> call, Throwable t) {
                    noDataFound();
                    mProgressDialog.setVisibility(View.GONE);
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
        horizontalScrollView.setVisibility(View.GONE);
    }

    private void noDataFound() {
        imgError.setVisibility(View.GONE);
        txTitle.setText("No Record Found");
        txtSubtitle.setVisibility(View.GONE);
        txtSubtitle.setText(R.string.error_content);
        txtRetry.setText(R.string.error_retry);
        txtRetry.setVisibility(View.VISIBLE);
        layEmpty.setVisibility(View.VISIBLE);
        horizontalScrollView.setVisibility(View.GONE);
    }

    private void showDataFound() {
        layEmpty.setVisibility(View.GONE);
        horizontalScrollView.setVisibility(View.VISIBLE);
    }

    private void initData() {
        session = new Session(this);
        userID = session.getIboKeyId();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        Double wallet = getIntent().getExtras().getDouble("wallet");
        txtWallet.setText(" ₹" + wallet);
    }
}
