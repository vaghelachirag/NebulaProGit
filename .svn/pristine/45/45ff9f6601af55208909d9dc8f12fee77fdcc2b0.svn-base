package com.nebulacompanies.ibo.fragments;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.SalesAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
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
 * Created by Palak Mehta on 7/6/2016.
 */
public class MySales extends Fragment /*implements AsyncResponse*/ {

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView lview;
    String project = "";
    int projectId;
    SalesValues salesValues;
    SalesAdapter salesAdapter;
    Session session;
    Boolean isRefreshed = false;
    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    ConnectionDetector cd;

    private APIInterface mAPIInterface;
    public static final String TAG = "MySales";

    public static ArrayList<MySalesList> arrayListMySalesAavaasList= new ArrayList<>();
    LinearLayout lnMySales;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_sales_new, container, false);

        init(view);
        MySalesList();

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null)
            return;
       /* project = getArguments().getString("ProjectName");*/
        projectId=getArguments().getInt("ProjectId");
        session = new Session(getActivity());
        cd = new ConnectionDetector(getActivity().getApplicationContext());
       // Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
    }

    private void init(View view) {
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        initError(view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sales_swipe_refresh_layout);
        lview = (ListView) view.findViewById(R.id.listview_my_sales);
        lnMySales = (LinearLayout) view.findViewById(R.id.ln_rank_bonus);

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

    private void MySalesList() {
        if (Utils.isNetworkAvailable(getActivity())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
           /* mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Call<MySalesAavaasDetails> wsCallingMySalesAavaasDetails = mAPIInterface.getMySalesAavaas("Real Estate","0","0",projectId);
            wsCallingMySalesAavaasDetails.enqueue(new Callback<MySalesAavaasDetails>() {
                @Override
                public void onResponse(Call<MySalesAavaasDetails> call, Response<MySalesAavaasDetails> response) {

                    if(mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {
                        arrayListMySalesAavaasList.clear();
                        lnMySales.setVisibility(View.VISIBLE);
                        if (response.code()==200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                arrayListMySalesAavaasList.addAll(response.body().getData());
                                salesAdapter = new SalesAdapter(getActivity(), arrayListMySalesAavaasList,projectId);
                                lview.setAdapter(salesAdapter);

                                salesAdapter.notifyDataSetChanged();

                            }
                            else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                lnMySales.setVisibility(View.VISIBLE);
                                noRecordsFound();
                            }
                            else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                lnMySales.setVisibility(View.VISIBLE);
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
                            lnMySales.setVisibility(View.VISIBLE);
                            serviceUnavailable();
                        }
                    }
                    else {
                        /*if (response.code() == 401) {
                            //Redirect to Login Page
                            //doLogout(getActivity(), session);
                        }
                        else if (response.code() == 500) {
                            serviceUnavailable();
                        }*/
                        serviceUnavailable();
                    }
                }
                @Override
                public void onFailure(Call<MySalesAavaasDetails> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        }
        else {
            lnMySales.setVisibility(View.VISIBLE);
            noInternetConnection();
        }
    }



    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            if(salesAdapter != null) {
                salesAdapter.clearData();
                salesAdapter.notifyDataSetChanged();
            }
            MySalesList();
            mSwipeRefreshLayout.setRefreshing(true);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnection();
        }
    }

    /*@Override
    public void processFinish(String output, String Tag) {
        if(output == null) {
            output = "THERE WAS AN ERROR";
        }

        String response = output.toString();
        Log.i("INFO", "response :" + response);

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
                        else{
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

                        if(project.equals(Config.AAVAAS_AHMEDABAD)) {
                            if (subcategory.get(i).toString().equals("Aavaas")) {
                                salesValues = new SalesValues(subcategory.get(i).toString(), hyderabad_product.get(i).toString(), sale_id.get(i).toString(),
                                        customer_name.get(i).toString(), apartment.get(i).toString(), purchase_date.get(i).toString(), gender.get(i).toString(),
                                        state.get(i).toString(), city.get(i).toString(), mobile.get(i).toString(), pincode.get(i).toString(),
                                        investment.get(i).toString(), payment_plan.get(i).toString(), receipt.get(i).toString(), token_amount.get(i).toString(),
                                        dp_20.get(i).toString(), discount.get(i).toString(), monthly_installment.get(i).toString(),
                                        next_installment_date.get(i).toString());
                                arrayList.add(salesValues);
                            }
                        }
                        else if(project.equals(Config.AAVAAS_HYDERABAD)){
                            if (subcategory.get(i).toString().equals("AVS Hyderabad")) {
                                salesValues = new SalesValues(subcategory.get(i).toString(), hyderabad_product.get(i).toString(), sale_id.get(i).toString(),
                                        customer_name.get(i).toString(), apartment.get(i).toString(), purchase_date.get(i).toString(), gender.get(i).toString(),
                                        state.get(i).toString(), city.get(i).toString(), mobile.get(i).toString(), pincode.get(i).toString(),
                                        investment.get(i).toString(), payment_plan.get(i).toString(), receipt.get(i).toString(), token_amount.get(i).toString(),
                                        dp_20.get(i).toString(), discount.get(i).toString(), monthly_installment.get(i).toString(),
                                        next_installment_date.get(i).toString());
                                arrayList.add(salesValues);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                salesAdapter = new SalesAdapter(getActivity(), arrayList);
                lview.setAdapter(salesAdapter);
                salesAdapter.notifyDataSetChanged();

                if(salesAdapter.getCount() == 0) {
                    noRecordsTextView.setVisibility(View.VISIBLE);
                }else{
                    noRecordsTextView.setVisibility(View.GONE);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if (response.contains("No Data Found")) {
            noRecordsTextView.setVisibility(View.VISIBLE);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(AsyncComplex task) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.execute();
    }
*/
    void initError(View view){
        llEmpty = (LinearLayout)view.findViewById(R.id.llEmpty);
        imgError = (ImageView)view.findViewById(R.id.imgError);
        txtErrorTitle = (MyTextView)view.findViewById(R.id.txtErrorTitle);
        txtRetry = (MyTextView)view. findViewById(R.id.txtRetry);
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
        lview.setVisibility(View.GONE);
    }

    void serviceUnavailable(){
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);

    }

    void noInternetConnection(){
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
    }
}
