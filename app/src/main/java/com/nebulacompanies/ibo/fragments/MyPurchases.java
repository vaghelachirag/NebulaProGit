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
import com.nebulacompanies.ibo.adapter.PurchaseAdapter;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.MyPurchasesAavaasDetails;
import com.nebulacompanies.ibo.model.MySalesList;
import com.nebulacompanies.ibo.model.PurchaseValues;
import com.nebulacompanies.ibo.util.ConnectionDetector;
import com.nebulacompanies.ibo.util.Constants;
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
import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

/**
 * Created by Palak Mehta on 7/6/2016.
 */
public class MyPurchases extends Fragment /*implements AsyncResponse */{

    SwipeRefreshLayout mSwipeRefreshLayout;
    ListView lview;
    MyTextView purchaseDateTextView, apartmentTextView, saleInfoTextView, vacationTextView, vacationDateTextView;
    LinearLayout vacationLinearLayout,lnMyPurchase;
    ArrayList<String> subcategory = new ArrayList<>();
    ArrayList<String> hyderabad_product = new ArrayList<>();
    ArrayList<Integer> sale_id = new ArrayList<>();
    ArrayList<String> purchase_date = new ArrayList<>();
    ArrayList<String> apartment = new ArrayList<>();
    ArrayList<Integer> receipt = new ArrayList<>();
    ArrayList<Integer> investment = new ArrayList<>();
    ArrayList<String> payment_plan = new ArrayList<>();
    ArrayList<Integer> token_amount = new ArrayList<>();
    ArrayList<Integer> dp_20 = new ArrayList<>();
    ArrayList<Integer> discount = new ArrayList<>();
    ArrayList<Integer> monthly_installment = new ArrayList<>();
    ArrayList<String> next_installment_date = new ArrayList<>();
    ArrayList<String> product = new ArrayList<>();

    String project;
    PurchaseValues purchaseValues;
    ArrayList arrayList;
    PurchaseAdapter purchaseAdapter;
    Session session;
    Boolean isRefreshed = false;

    //Error View
    private LinearLayout llEmpty;
    private ImageView imgError;
    private MyTextView txtErrorTitle, txtRetry;

    ConnectionDetector cd;

    private APIInterface mAPIInterface;
    public static final String TAG = "MyPurchases";
    int projectId;

    public static ArrayList<MySalesList> arrayListMyPurchasesAavaasList= new ArrayList<>();

    String Unit;
    String ProductCategory;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_purchases, container, false);

        init(view);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null)
            return;

        projectId=getArguments().getInt("ProjectId");
        session = new Session(getActivity());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (purchaseAdapter == null ){
                if (Utils.isNetworkAvailable(getActivity())) {

                    if (projectId== Constants.ID_HOLIDAY){
                        MyPurchasesListVacation();
                    }else {
                        MyPurchasesList();
                    }

                }else {
                    noInternetConnection();
                }
            }
        }
    }


    private void init(View view) {
        cd = new ConnectionDetector(getActivity());
        //Utils.isNetworkAvailable(getApplicationContext()) = cd.isConnectingToInternet();
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        initError(view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.aavaas_purchases_swipe_refresh_layout);
        lview = (ListView) view.findViewById(R.id.listview_my_purchases);
        purchaseDateTextView = (MyTextView) view.findViewById(R.id.purchase_date);
        apartmentTextView = (MyTextView) view.findViewById(R.id.apartment);
        saleInfoTextView = (MyTextView) view.findViewById(R.id.sale_info);
        vacationTextView = (MyTextView) view.findViewById(R.id.vacation_text);
        vacationDateTextView = (MyTextView) view.findViewById(R.id.vacation_date);
        vacationLinearLayout = (LinearLayout) view.findViewById(R.id.vacation_linearlayout);
        lnMyPurchase = (LinearLayout) view.findViewById(R.id.ln_my_purchase);

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
    private void MyPurchasesList() {
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
            Call<MyPurchasesAavaasDetails> wsCallingMyPurchasesAavaasDetails = mAPIInterface.getMyPurchase("Real Estate","0","0",projectId);
            wsCallingMyPurchasesAavaasDetails.enqueue(new Callback<MyPurchasesAavaasDetails>() {
                @Override
                public void onResponse(Call<MyPurchasesAavaasDetails> call, Response<MyPurchasesAavaasDetails> response) {

                    if (MyPurchases.this != null && !getActivity().isFinishing() && mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.isSuccessful()) {
                        arrayListMyPurchasesAavaasList.clear();
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                arrayListMyPurchasesAavaasList.addAll(response.body().getData());
                                purchaseAdapter = new PurchaseAdapter(getActivity(), arrayListMyPurchasesAavaasList);
                                lview.setAdapter(purchaseAdapter);
                                purchaseAdapter.notifyDataSetChanged();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                serviceUnavailable();
                            }

                            if (arrayListMyPurchasesAavaasList.size() > 0) {
                                llEmpty.setVisibility(View.GONE);
                                lview.setVisibility(View.VISIBLE);
                            } else {
                                llEmpty.setVisibility(View.VISIBLE);
                                lview.setVisibility(View.GONE);
                            }
                        }
                        else{
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
                public void onFailure(Call<MyPurchasesAavaasDetails> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.dismiss();
                    Log.e(TAG, "ERROR : " + t.getMessage());
                    serviceUnavailable();
                }
            });
        }
        else {
            noInternetConnection();
        }
    }

    private void MyPurchasesListVacation() {
        Call<MyPurchasesAavaasDetails> wsCallingMyPurchasesHolidayDetails = mAPIInterface.getMyPurchaseVacation("","0","0",Constants.ID_HOLIDAY);
        wsCallingMyPurchasesHolidayDetails.enqueue(new Callback<MyPurchasesAavaasDetails>() {
            @Override
            public void onResponse(Call<MyPurchasesAavaasDetails> call, Response<MyPurchasesAavaasDetails> response) {
                if (response.isSuccessful()){
                    if (response.code()==200)
                    {
                        for (MySalesList MyPurchasesHolidayList:response.body().getData()) {
                            if (MyPurchasesHolidayList.getProductName().equals("Vacation 6000"))
                            {
                                vacationLinearLayout.setVisibility(View.VISIBLE);
                                vacationDateTextView.setText(SetGmtTime(MyPurchasesHolidayList.getBookingDate()));
                            }
                            else
                            {
                                vacationLinearLayout.setVisibility(View.GONE);

                            }
                        }
                    }
                }
                else{
                    /*if (response.code() == 401) {
                        //Redirect to Login Page
                        //doLogout(getActivity(), session);
                    }
                    else if (response.code() == 500) {
                        AppUtils.displayServiceUnavailableMessage(getActivity());
                    }*/
                    //AppUtils.displayServiceUnavailableMessage(getActivity());

                }
            }

            @Override
            public void onFailure(Call<MyPurchasesAavaasDetails> call, Throwable t) {

            }
        });


    }

    /*private void callAPI(){
        if(session != null) {
            MY_PURCHASES_API = Config.NEB_API + "/API/MySalePurchase/GetMyProductPurchase?MemberID=" + session.getLoginID();
            asyncComplex2 = new AsyncComplex(getActivity(), MY_PURCHASES_API, "MY_PURCHASES", isRefreshed);
            asyncComplex2.asyncResponse = this;
            StartAsyncTaskInParallel(asyncComplex2);
        }
    }*/

    private void refreshContent() {
        if (Utils.isNetworkAvailable(getActivity())) {
            isRefreshed = true;
            if(purchaseAdapter != null) {
                purchaseAdapter.clearData();
                purchaseAdapter.notifyDataSetChanged();
            }
            //callAPI();
           /* MyPurchasesList();
            if (projectId== Constants.ID_HOLIDAY){
                MyPurchasesListVacation();
            }*/
            if (projectId== Constants.ID_HOLIDAY){
                MyPurchasesListVacation();
            }else {
                MyPurchasesList();
            }
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
        int sum = 0;

        arrayList = new ArrayList<>();

        if(Tag.equals("MY_PURCHASES") && !response.contains("No Data Found")){
            try {
                JSONObject object = new JSONObject(response);
                JSONArray new_array = object.getJSONArray("Data");

                Log.i("INFO", "new_array :" + new_array);

                for (int i = 0; i < new_array.length(); i++) {
                    try {
                        JSONObject jsonObject = new_array.getJSONObject(i);
                        if (jsonObject.getString("Sale_ID").toString().equals("0")) {
                        }
                        else {
                            subcategory.add(jsonObject.getString("Subcategory").toString());
                            hyderabad_product.add(jsonObject.getString("HydderabadProduct").toString());
                            sale_id.add(jsonObject.getInt("Sale_ID"));
                            purchase_date.add(jsonObject.getString("Purchase_Date").toString());
                            apartment.add(jsonObject.getString("Apartment").toString());
                            receipt.add(jsonObject.getInt("Receipt"));
                            investment.add(jsonObject.getInt("Investment"));
                            payment_plan.add(jsonObject.getString("Payment_Options").toString());
                            token_amount.add(jsonObject.getInt("Token_Amount"));
                            dp_20.add(jsonObject.getInt("C20__Amount"));
                            discount.add(jsonObject.getInt("Discount"));
                            monthly_installment.add(jsonObject.getInt("Installment"));
                            next_installment_date.add(jsonObject.getString("Installment_Date").toString());
                            product.add(jsonObject.getString("Product").toString());
                        }

                        Log.i("INFO", "product :" + product.get(i).toString());
                        Log.i("INFO", "purchase_date :" + purchase_date.get(i).toString());

                        if(project.equals(Config.AAVAAS_AHMEDABAD)) {
                            if (product.get(i).toString().equals("Vacation 6000")) {
                                vacationLinearLayout.setVisibility(View.VISIBLE);
                                vacationDateTextView.setText(SetDateFormat3(purchase_date.get(i).toString()));
                                noRecordsTextView.setVisibility(View.VISIBLE);
                            }
                            else if (subcategory.get(i).toString().equals("Aavaas")) {
                                vacationLinearLayout.setVisibility(View.GONE);
                                purchaseValues = new PurchaseValues(subcategory.get(i).toString(), hyderabad_product.get(i).toString(), sale_id.get(i).toString(), purchase_date.get(i).toString(),
                                        apartment.get(i).toString(), receipt.get(i).toString(), investment.get(i).toString(), payment_plan.get(i).toString(), token_amount.get(i).toString(),
                                        dp_20.get(i).toString(), discount.get(i).toString(), monthly_installment.get(i).toString(), next_installment_date.get(i).toString(), product.get(i).toString());
                                arrayList.add(purchaseValues);
                            }
                        }
                        else if(project.equals(Config.AAVAAS_HYDERABAD)){
                            if (subcategory.get(i).toString().equals("AVS Hyderabad")) {
                                vacationLinearLayout.setVisibility(View.GONE);
                                purchaseValues = new PurchaseValues(subcategory.get(i).toString(), hyderabad_product.get(i).toString(), sale_id.get(i).toString(), purchase_date.get(i).toString(),
                                        apartment.get(i).toString(), receipt.get(i).toString(), investment.get(i).toString(), payment_plan.get(i).toString(), token_amount.get(i).toString(),
                                        dp_20.get(i).toString(), discount.get(i).toString(), monthly_installment.get(i).toString(), next_installment_date.get(i).toString(), product.get(i).toString());
                                arrayList.add(purchaseValues);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                purchaseAdapter = new PurchaseAdapter(getActivity(), arrayList);
                lview.setAdapter(purchaseAdapter);
                purchaseAdapter.notifyDataSetChanged();

                if(purchaseAdapter.getCount() == 0) {
                    noRecordsTextView.setVisibility(View.VISIBLE);
                }else{
                    noRecordsTextView.setVisibility(View.GONE);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
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
        lnMyPurchase.setVisibility(View.VISIBLE);

    }

    void serviceUnavailable(){
        txtErrorTitle.setText(R.string.error_service_unavailable);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
        lnMyPurchase.setVisibility(View.VISIBLE);

    }

    void noInternetConnection(){
        txtErrorTitle.setText(R.string.error_msg_network);
        imgError.setImageResource(R.drawable.ic_cloud_off_black_24dp);
        llEmpty.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
        lview.setVisibility(View.GONE);
        lnMyPurchase.setVisibility(View.VISIBLE);
    }
}
