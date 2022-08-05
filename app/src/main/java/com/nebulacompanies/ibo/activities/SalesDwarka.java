package com.nebulacompanies.ibo.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.AsyncComplex;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.SalesAdapter;
import com.nebulacompanies.ibo.model.MySalesAavaasDetails;
import com.nebulacompanies.ibo.model.MySalesList;
import com.nebulacompanies.ibo.model.SalesValues;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Session;
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
 * Created by Palak Mehta on 9/17/2016.
 */
public class SalesDwarka extends AppCompatActivity /*implements AsyncResponse */{

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView lview;
    MyTextView custNameTextView, aptTextView, custInfoTextView, saleInfoTextView;
    LinearLayout linearLayout;
    View view;
    AsyncComplex asyncComplex1;
    String MY_SALES_API = null;

    ArrayList<String> subcategory = new ArrayList<>();
    ArrayList<String> hyderabad_product = new ArrayList<>();
    ArrayList<Integer> sale_id = new ArrayList<>();
    ArrayList<String> customer_name = new ArrayList<>();
    ArrayList<String> apartment = new ArrayList<>();
    ArrayList<String> purchase_date = new ArrayList<>();
    ArrayList<String> gender = new ArrayList<>();
    ArrayList<String> state = new ArrayList<>();
    ArrayList<String> city = new ArrayList<>();
    ArrayList<String> mobile = new ArrayList<>();
    ArrayList<String> pincode = new ArrayList<>();
    ArrayList<Integer> investment = new ArrayList<>();
    ArrayList<String> payment_plan = new ArrayList<>();
    ArrayList<Integer> receipt = new ArrayList<>();
    ArrayList<Integer> token_amount = new ArrayList<>();
    ArrayList<Integer> dp_20 = new ArrayList<>();
    ArrayList<Integer> discount = new ArrayList<>();
    ArrayList<Integer> monthly_installment = new ArrayList<>();
    ArrayList<String> next_installment_date = new ArrayList<>();
    SalesValues salesValues;
    ArrayList arrayList;
    SalesAdapter salesAdapter;
    Session session;
    Boolean isRefreshed = false;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    ConnectionDetector cd;
    private APIInterface mAPIInterface;
    public static final String TAG = "MySalesDwarka";

    public static ArrayList<MySalesList> arrayListMySalesAavaasList= new ArrayList<>();
    String DwarkaProduct;
    int projectId;
    LinearLayout lnRankBonus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_sales);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            projectId=b.getInt("ProjectId");
            DwarkaProduct=b.getString("ProjectName");
        }
        setActionBar();
        init();
        getMySalesDwarkaList();
    }

    void setActionBar(){
        TextView tv = new TextView(getApplicationContext());
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), Config.FONT_STYLE);
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText(DwarkaProduct); // ActionBar title text
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(R.string.product_list);
       // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#570054")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nebula_new_dark)));

    }
    void init() {
        cd = new ConnectionDetector(getApplicationContext());
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();

        session = new Session(this);
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        initError();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sales_swipe_refresh_layout);
        lview = (ListView) findViewById(R.id.listview_my_sales);
        custNameTextView = (MyTextView) findViewById(R.id.cust_name_);
        aptTextView = (MyTextView) findViewById(R.id.apartment_);
        linearLayout = (LinearLayout) findViewById(R.id.tablelayout8);
        lnRankBonus = (LinearLayout) findViewById(R.id.ln_rank_bonus);
        view = (View) findViewById(R.id.apartment_view);
        //noRecordsTextView = (TextView) findViewById(R.id.no_records);
        custInfoTextView = (MyTextView) findViewById(R.id.cust_info_);
        saleInfoTextView = (MyTextView) findViewById(R.id.sale_info_);

        aptTextView.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        linearLayout.setWeightSum(10);

        lview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lview.getChildAt(0) != null) {
                    mSwipeRefreshLayout.setEnabled(lview.getFirstVisiblePosition() == 0 && lview.getChildAt(0).getTop() == 0);
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });
    }

    private void getMySalesDwarkaList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(this, R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Call<MySalesAavaasDetails> wsCallingMySalesDwarkaDetails = mAPIInterface.getMySalesAavaas("Real Estate","0","0",projectId);
            wsCallingMySalesDwarkaDetails.enqueue(new Callback<MySalesAavaasDetails>() {
                @Override
                public void onResponse(Call<MySalesAavaasDetails> call, Response<MySalesAavaasDetails> response) {
                    if(mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {
                        arrayListMySalesAavaasList.clear();
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                arrayListMySalesAavaasList.addAll(response.body().getData());
                                salesAdapter = new SalesAdapter(SalesDwarka.this, arrayListMySalesAavaasList,projectId);
                                lview.setAdapter(salesAdapter);
                                salesAdapter.notifyDataSetChanged();
                                lnRankBonus.setVisibility(View.VISIBLE);
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }

                            if (arrayListMySalesAavaasList.size() > 0) {
                                llEmpty.setVisibility(View.GONE);
                                lview.setVisibility(View.VISIBLE);
                            } else {
                                llEmpty.setVisibility(View.VISIBLE);
                                lview.setVisibility(View.GONE);
                            }
                        }
                        else{
                            lnRankBonus.setVisibility(View.VISIBLE);
                            serviceUnavailable();
                        }
                    }
                    else{
                        /*if (response.code() == 401) {
                            //Redirect to Login Page
                            //doLogout(SalesDwarka.this, session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                        lnRankBonus.setVisibility(View.VISIBLE);
                        serviceUnavailable();
                    }
                }
                @Override
                public void onFailure(Call<MySalesAavaasDetails> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        }
        else {
            lnRankBonus.setVisibility(View.VISIBLE);
            noInternetConnection();
        }
    }

    /*private void callAPI(){
        if(session != null) {
            MY_SALES_API = Config.NEB_API + "/API/MySalePurchase/GetMyProductSale?SaleMemberID=" + session.getLoginID();
            asyncComplex1 = new AsyncComplex(this, MY_SALES_API, "MY_SALES", isRefreshed);
            asyncComplex1.asyncResponse = this;
            StartAsyncTaskInParallel(asyncComplex1);
        }
    }*/

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            isRefreshed = true;
            if(salesAdapter != null) {
                salesAdapter.clearData();
                salesAdapter.notifyDataSetChanged();
            }
            getMySalesDwarkaList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            lnRankBonus.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);

        }
    }

   /* @Override
    public void processFinish(String output, String Tag) {
        if(output == null) {
            output = "THERE WAS AN ERROR";
        }

        String response = output.toString();
        Log.i("INFO", "response :" + response);
        int sum = 0;

        if(Tag.equals("MY_SALES") && !response.contains("No Data Found")){
            try {
                JSONObject object = new JSONObject(response);
                JSONArray new_array = object.getJSONArray("Data");
                arrayList = new ArrayList<>();

                for (int i = 0; i < new_array.length(); i++) {
                    try {
                        JSONObject jsonObject = new_array.getJSONObject(i);
                        if (jsonObject.getString("Sale_ID").toString().equals("0")) {
                        }
                        else {
                            subcategory.add(jsonObject.getString("Subcategory").toString());
                            hyderabad_product.add(jsonObject.getString("HydderabadProduct").toString());
                            sale_id.add(jsonObject.getInt("Sale_ID"));
                            customer_name.add(jsonObject.getString("CustomerName").toString());
                            apartment.add(jsonObject.getString("Apartment").toString());
                            purchase_date.add(jsonObject.getString("Purchase_Date").toString());
                            gender.add(jsonObject.getString("Sex").toString());
                            state.add(jsonObject.getString("State").toString());
                            city.add(jsonObject.getString("City").toString());
                            mobile.add(jsonObject.getString("Mobile").toString());
                            pincode.add(jsonObject.getString("Pincode").toString());
                            investment.add(jsonObject.getInt("Investment"));
                            payment_plan.add(jsonObject.getString("Payment_Options").toString());
                            receipt.add(jsonObject.getInt("Receipt"));
                            token_amount.add(jsonObject.getInt("Token_Amount"));
                            dp_20.add(jsonObject.getInt("C20__Amount"));
                            discount.add(jsonObject.getInt("Discount"));
                            monthly_installment.add(jsonObject.getInt("Installment"));
                            next_installment_date.add(jsonObject.getString("Installment_Date").toString());
                        }

                        Log.i("INFO", "subcategory :" + subcategory.get(i).toString());
                        if(subcategory.get(i).toString().equals("Dwarka")) {
                            salesValues = new SalesValues(subcategory.get(i).toString(), hyderabad_product.get(i).toString(), sale_id.get(i).toString(),
                                    customer_name.get(i).toString(), apartment.get(i).toString(), purchase_date.get(i).toString(), gender.get(i).toString(),
                                    state.get(i).toString(), city.get(i).toString(), mobile.get(i).toString(), pincode.get(i).toString(),
                                    investment.get(i).toString(), payment_plan.get(i).toString(), receipt.get(i).toString(), token_amount.get(i).toString(),
                                    dp_20.get(i).toString(), discount.get(i).toString(), monthly_installment.get(i).toString(),
                                    next_installment_date.get(i).toString());
                            arrayList.add(salesValues);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                salesAdapter = new SalesAdapter(this, arrayList);
                lview.setAdapter(salesAdapter);
                salesAdapter.notifyDataSetChanged();

                if(salesAdapter.getCount() == 0) {
                   *//* noRecordsTextView.setVisibility(View.VISIBLE);*//*
                }else{
                    *//*noRecordsTextView.setVisibility(View.GONE);*//*
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            *//*noRecordsTextView.setVisibility(View.VISIBLE);*//*
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(AsyncComplex task) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void initError(){
        llEmpty = (LinearLayout)findViewById(R.id.llEmpty);
        imgError = (ImageView)findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView)findViewById(R.id.txtErrorTitle);
        txtRetry = (MyTextView)findViewById(R.id.txtRetry);
        txtRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshContent();
            }
        });
    }

    void noRecordsFound(){
        txtErrorTitle.setText(R.string.error_no_records);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.GONE);
        lnRankBonus.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
    }

    void serviceUnavailable(){
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
        lnRankBonus.setVisibility(View.VISIBLE);
    }

    void noInternetConnection(){
        txtErrorTitle.setText(R.string.error_msg_network);
        lnRankBonus.setVisibility(View.VISIBLE);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
    }
}
