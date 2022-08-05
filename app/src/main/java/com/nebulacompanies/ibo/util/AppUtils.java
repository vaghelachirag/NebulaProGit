package com.nebulacompanies.ibo.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.gson.JsonObject;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIClientCustomer;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.Amenities;
import com.nebulacompanies.ibo.model.HolidaysCRCharges;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.HOLIDAY_PACKAGE_FEES_CUSTOMER;
import static com.nebulacompanies.ibo.util.Const.HOLIDAY_PACKAGE_IBO;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;

/**
 * Created by NebulaApplication-1 on 08-09-2017.
 */

public class AppUtils {


    private static ArrayList<HolidaysCRCharges> mArrayListCharger = new ArrayList<>();
    public static ArrayList<Amenities> amenitiesArrayList = new ArrayList<>();
    private static String date;
    public static double mHolidayPackageFreeCustomer = 0.0;
    public static double mHolidayPackageFreeIBO = 0.0;

   /* public static String prefFamily = "PREFERNCE_Family";
    public static String prefWeekly = "PREFERNCE_Weekly";
    public static String prefCommunity = "PREFERNCE_Community";
    public static String prefDash="PREFERNCE_Dashboard";*/


    /**
     * This method for date selection.
     *
     * @param mContext The Current class context.
     * @return The Selected date.
     */
    public static final String getDate(final Context mContext) {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                date = datePicker.getDayOfMonth() + "/" + datePicker.getMonth() + "/" + datePicker.getYear();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));

        datePickerDialog.show();
        return date;
    }

    /**
     * This method for Display error message.
     *
     * @param mContext The current class context.
     * @param mMessage The display message.
     */
    public static final void displayErrorMessage(final Context mContext, final String mMessage) {
        StyleableToast.makeText(mContext, mMessage, Toast.LENGTH_LONG, R.style.StyleToastWhile).show();
    }

    /**
     * This method for display network offline message
     *
     * @param mContext The current class context.
     */
    public static final void displayNetworkErrorMessage(final Context mContext) {
        //StyleableToast.makeText(mContext, mContext.getString(R.string.error_msg_network), Toast.LENGTH_LONG, R.style.StyleToastNetwork).show();
        Toast.makeText(mContext, mContext.getString(R.string.Network_is_not_available), Toast.LENGTH_SHORT).show();
    }

    public static final void displayServerErrorMessage(final Context mContext) {
        //StyleableToast.makeText(mContext, mContext.getString(R.string.error_msg_network), Toast.LENGTH_LONG, R.style.StyleToastNetwork).show();
       Toast.makeText(mContext, mContext.getString(R.string.error_internal_server), Toast.LENGTH_LONG).show();
    }


    public static boolean isOnline(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED);

    }


    public static void displayAlertErrorNetwork(final Context mContext) {
        FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(mContext)
                //.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_cloud_off_black_24dp))
                .setImageResource(R.drawable.ic_cloud_off_black_24dp)
                .setTextTitle("Oops.!")
                .setBackgroundColor(R.color.white)
                .setTextSubTitle("No internet connection.")
                .setBody("No internet connection. We can notify you when you can view this page.")
                .setNegativeColor(R.color.colorNegative)
                .setPositiveButtonText("Ok")
                .setPositiveColor(R.color.colorPositive)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                /* .setAutoHide(true)*/
                .build();
        alert.show();
    }


    public static void getHolidayCharges(final Context mContext) {
        if (AppUtils.isOnline(mContext)) {
            APIInterface mAPIInterface = APIClient.getClient(mContext).create(APIInterface.class);
            Call<JsonObject> requestCall = mAPIInterface.getHolidayCharges();
            requestCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try {
                                String responseString = response.body().toString();
                                JSONObject jsonObject = new JSONObject(responseString);
                                if (jsonObject.has("StatusCode") && jsonObject.getInt("StatusCode") == REQUEST_STATUS_CODE_SUCCESS) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                                    mArrayListCharger.clear();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject dataObject = jsonArray.getJSONObject(i);
                                        HolidaysCRCharges itemObject = new HolidaysCRCharges();
                                        itemObject.setAmount(dataObject.getDouble("Amount"));
                                        itemObject.setName(dataObject.getString("Name"));
                                        if (dataObject.getString("Name").equalsIgnoreCase(HOLIDAY_PACKAGE_FEES_CUSTOMER))
                                            mHolidayPackageFreeCustomer = dataObject.getDouble("Amount");

                                        if (dataObject.getString("Name").equalsIgnoreCase(HOLIDAY_PACKAGE_IBO))
                                            mHolidayPackageFreeIBO = dataObject.getDouble("Amount");

                                        //if (dataObject.getString("Name").equalsIgnoreCase(NAME_CHANGE))
                                        //mNameChangeFree = dataObject.getDouble("Amount");
                                        mArrayListCharger.add(itemObject);
                                    }
                                }
                                // AppUtils.displayErrorMessage(mContext, jsonObject.getString("Message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else
                            AppUtils.displayErrorMessage(mContext, "Something went wrong!Please try again");
                    }/* else
                        DisplayEmptyDialog(mContext, -1);*/
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    android.util.Log.e(getClass().getSimpleName(), "ERROR " + t.getMessage());
                }
            });

        } else
            AppUtils.displayNetworkErrorMessage(mContext);
    }



}