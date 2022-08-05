package com.nebulacompanies.ibo.ecom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.AddAddressDetail;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.StateDetails;
import com.nebulacompanies.ibo.model.StateList;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class AddAddressAcountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private APIInterface mAPIInterface;
    Toolbar toolbar;
    ImageView imgBackArrow,imgSearch;
    MyButtonEcom btnDeliver;
    Spinner spinnerAddressCity, spinnerAddressState, spinnerAddressType;
    NebulaEditTextEcom tvAddAddressName, tvAddAddressMobile, tvAddAddressPincode, tvAddAddressOne, tvAddAddressTwo, tvAddAddressLandmark;
    MyTextViewEcom tvToolbarTitle;
    MaterialProgressBar mProgressDialog;


    List<String> categories;
    ArrayList<String> listState = new ArrayList<>();
    ArrayList<Integer> listStateID = new ArrayList<>();
    ArrayList<String> listCity = new ArrayList<>();
    public static ArrayList<StateList> arraystateList = new ArrayList<>();


    Typeface typeface;
    String deviceId,uniqueID;
    String addressMobile, addressName, addressLineOne, addressLineTwo, addressCity, addressState, addressPincode, addressType, addressLandmark;
    int addressID, editClickValue,state,city;
    String selectedState, stateName, selectedcity;

    Intent editAddressData;

    Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_account);
        Utils.darkenStatusBar(this, R.color.colorNotification);

        /*TelephonyManager TelephonyMgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();*/

        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);

        initSharedPreference();
        initUI();

        //btnDeliver.setTypeface(typeface);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_textview_ecom, categories);
        // Drop down layout style - list view with radio button
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.setDropDownViewResource(R.layout.spinner_textview_ecom);
        spinnerAddressType.setAdapter(dataAdapter);
        categories.add(0, "Select an Address Type*");
        categories = new ArrayList<String>();
        categories.add("Home (7 am - 9 pm delivery)");
        categories.add("Office/Commercial (10 am - 6 am delivery)");

        initIntentData();
        initOnClick();

        if (Utils.isNetworkAvailable(getApplicationContext())) {
            getState();
        }
    }


    void initSharedPreference(){

        deviceId = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid,null);

        Log.d("MDEVICEID uniqueID 1", "MDEVICEID uniqueID 1: "+ uniqueID);
        if (deviceId == null || deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = this.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid,randomID);
                editor.apply();
                deviceId =  randomID;
            } else {
                deviceId = uniqueID;
            }
        }
        Log.d("MDEVICEIDaddressacount1", "MDEVICEIDaddressacount1: "+ deviceId);

    }

    void initUI(){
         spinnerAddressCity = findViewById(R.id.spinner_address_city);
        spinnerAddressState = findViewById(R.id.spinner_address_state);
        spinnerAddressType = findViewById(R.id.spinner_address_type);
        tvAddAddressName =  findViewById(R.id.tv_add_address_name);
        tvAddAddressMobile =  findViewById(R.id.tv_add_address_mobile);
        tvAddAddressPincode =  findViewById(R.id.tv_add_address_pincode);
        tvAddAddressOne =  findViewById(R.id.tv_add_address_one);
        tvAddAddressTwo =  findViewById(R.id.tv_add_address_two);
        tvAddAddressLandmark = findViewById(R.id.tv_add_address_landmark);

        //getting the toolbar
        toolbar = findViewById(R.id.toolbar_search);
        mProgressDialog = findViewById(R.id.progresbar);
        imgSearch = toolbar.findViewById(R.id.img_search);
        imgSearch.setVisibility(View.GONE);
        imgBackArrow =  findViewById(R.id.img_back);
        tvToolbarTitle =  toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("Add Address");
        imgBackArrow =  findViewById(R.id.img_back);
        btnDeliver =  findViewById(R.id.btn_deliver);

        //Spinner spinner = (Spinner) findViewById(R.id.spinner_address_type);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/ember.ttf");
    }

    void initIntentData(){
        editAddressData = getIntent();
        if (editAddressData != null) {
            editClickValue = editAddressData.getIntExtra("address_edit_click", 1);
            addressID = editAddressData.getIntExtra("address_id", 0);
            addressMobile = editAddressData.getStringExtra("address_mobile");
            addressName = editAddressData.getStringExtra("address_name");
            addressLineOne = editAddressData.getStringExtra("address_one");
            addressLineTwo = editAddressData.getStringExtra("address_two");
            addressCity = editAddressData.getStringExtra("address_city");
            addressState = editAddressData.getStringExtra("address_state");
            addressPincode = editAddressData.getStringExtra("address_pincode");
            addressType = editAddressData.getStringExtra("address_type");
            addressLandmark = editAddressData.getStringExtra("address_landmark");

            tvAddAddressName.setText(addressName);
            tvAddAddressMobile.setText(addressMobile);
            tvAddAddressOne.setText(addressLineOne);
            tvAddAddressTwo.setText(addressLineTwo);
            spinnerAddressCity.setPrompt(addressCity);
            spinnerAddressState.setPrompt(addressState);
            tvAddAddressPincode.setText(addressPincode);
            spinnerAddressType.setPrompt(addressType);
            tvAddAddressLandmark.setText(addressLandmark);

        }
    }

    void initOnClick()
    {
         imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (tvAddAddressName.getText().toString().length() == 0 ||
                        tvAddAddressMobile.getText().toString().length() == 0 ||
                        tvAddAddressMobile.getText().toString().length() < 10 ||
                        tvAddAddressLandmark.getText().toString().length() == 0 ||
                        tvAddAddressPincode.getText().toString().length() == 0 ||
                        tvAddAddressPincode.getText().toString().length() < 6 ||
                        tvAddAddressOne.getText().toString().length() == 0 ||
                        tvAddAddressTwo.getText().toString().length() == 0 ||
                        spinnerAddressState.getSelectedItem().toString().equals("State*") ||
                        spinnerAddressCity.getSelectedItem().toString().equals("City*") ||
                        spinnerAddressType.getSelectedItem().toString().equals("Select an Address Type*")) {
                    checkValidation();
                } else {
                    addAddressDetails(tvAddAddressMobile.getText().toString(), tvAddAddressName.getText().toString(),
                            tvAddAddressOne.getText().toString(), tvAddAddressTwo.getText().toString(), tvAddAddressLandmark.getText().toString(), spinnerAddressCity.getSelectedItem().toString(),
                            spinnerAddressState.getSelectedItem().toString(),
                            tvAddAddressPincode.getText().toString(), spinnerAddressType.getSelectedItem().toString(), deviceId);

                }


            }
        });

    }

    void getState() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            Call<StateDetails> wsCallingStates = mAPIInterface.getStates("India");
            wsCallingStates.enqueue(new Callback<StateDetails>() {
                @Override
                public void onResponse(Call<StateDetails> call, Response<StateDetails> response) {
                    if (response.isSuccessful()) {
                        // mProgressDialog1.dismiss();
                        listState.clear();
                        listStateID.clear();
                        for (StateList stateList : response.body().getData()) {

                            String state = stateList.getStateName();
                            int state_id = stateList.getStateId();

                            android.util.Log.i("INFO", " responseString state : " + state);
                            Log.i("INFO", " responseString state_id : " + state_id);
                            if (!listState.contains(state)) {
                                listState.add(state);
                            }
                            Collections.sort(listState);

                            listStateID.add(state_id);

                            arraystateList.add(stateList);
                        }
                        listState.add(0, "State*");

                        addToSpinner();

                       /* if (sponsoredt.getText().toString().length() > 0) {
                            stateSpinner.requestFocus();
                            stateSpinner.performClick();
                        }*/

                    } else {
                        //   mProgressDialog1.dismiss();
                        //serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<StateDetails> call, Throwable t) {
                    // mProgressDialog1.dismiss();
                    //Log.e(TAG, "ERROR : " + t.getMessage());
                    //serviceUnavailable();
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(AddAddressAcountActivity.this);
        }
    }

    void addToSpinner() {

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(AddAddressAcountActivity.this, R.layout.spinner_textview_ecom, listState) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View spinnerview = super.getDropDownView(position, convertView, parent);
                TextView spinnerstatetextview = (TextView) spinnerview;
                if (position == 0) {
                    //Set the disable spinner item color fade .
                    spinnerstatetextview.setTextColor(Color.parseColor("#bcbcbb"));
                } else {
                    spinnerstatetextview.setTextColor(Color.BLACK);
                }
                return spinnerview;
            }
        };

        stateAdapter.setDropDownViewResource(R.layout.spinner_textview_item_ecom);
        spinnerAddressState.setAdapter(stateAdapter);

        getCity();
    }

    void getCity() {
        spinnerAddressState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerAddressState.clearFocus();

                if (!spinnerAddressState.getSelectedItem().toString().equals("State*")) {
                    // if (sponsoredt.getText().toString().length() > 0) {
                    spinnerAddressCity.requestFocus();
                    spinnerAddressCity.performClick();
                    // }
                }

                selectedState = spinnerAddressState.getSelectedItem().toString();
                state = spinnerAddressState.getSelectedItemPosition();
                int s_id = -1;
                for (int i = 0; i < arraystateList.size(); i++) {
                    stateName = arraystateList.get(i).getStateName();
                    if (arraystateList.get(i).getStateName().equals(selectedState)) {
                        s_id = arraystateList.get(i).getStateId();
                    }
                }
                listCity.clear();
                getCityList(s_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void getCityList(int stateId) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            /*final ProgressDialog mProgressDialog2 = new ProgressDialog(RegistationFullActivity.this, R.style.MyTheme);
            mProgressDialog2.show();
            mProgressDialog2.setCancelable(true);
            mProgressDialog2.setContentView(R.layout.progressdialog_ecom);
            mProgressDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
            Call<JsonObject> wsCallingCities = mAPIInterface.getCities(stateId);
            wsCallingCities.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        Log.i("INFO", " responseString Cities : " + response.body().toString());
                        //mProgressDialog2.dismiss();

                        String responseString = response.body().toString();
                        try {
                            JSONObject jsonObject = new JSONObject(responseString);
                            if (jsonObject.getInt("StatusCode") == REQUEST_STATUS_CODE_SUCCESS) {
                                JSONArray dataArray = jsonObject.getJSONArray("Data");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject object = dataArray.getJSONObject(i);
                                    String city = object.getString("CityName");
                                    Log.i("INFO", " responseString city : " + city);
                                    if (!listCity.contains(city)) {
                                        listCity.add(city);
                                    }
                                    Collections.sort(listCity);
                                }

                                listCity.add(0, "City*");

                                //if (sponsoredt.getText().toString().length() > 0) {
                                addToCitySpinner();
                                // }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        //  mProgressDialog2.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    //  mProgressDialog2.dismiss();
                    //Log.e(TAG, "ERROR : " + t.getMessage());
                    //serviceUnavailable();
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(AddAddressAcountActivity.this);
        }
    }

    void addToCitySpinner() {
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(AddAddressAcountActivity.this, R.layout.spinner_textview_ecom, listCity) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View spinnerview = super.getDropDownView(position, convertView, parent);
                TextView spinnercitytextview = (TextView) spinnerview;

                if (position == 0) {
                    //Set the disable spinner item color fade .
                    spinnercitytextview.setTextColor(Color.parseColor("#bcbcbb"));
                } else {
                    spinnercitytextview.setTextColor(Color.BLACK);
                }
                return spinnerview;
            }
        };
        cityAdapter.setDropDownViewResource(R.layout.spinner_textview_item_ecom);
        spinnerAddressCity.setAdapter(cityAdapter);

        spinnerAddressCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedcity = spinnerAddressCity.getSelectedItem().toString();
                city = spinnerAddressCity.getSelectedItemPosition();
                spinnerAddressCity.clearFocus();
                //alternatenumberedt.requestFocus();
                // numberedt.requestFocus();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    private void addAddressDetails(String mobile, String fullName, String addressLine1, String addressLine2,
                                   String landmark, String city, String state, String pinCode, String addressType, String deviceId) {
         if (Utils.isNetworkAvailable(getApplicationContext())) {
             //final ProgressDialog mProgressDialog1 = new ProgressDialog(AddAddressAcountActivity.this, R.style.MyTheme);
      /*  mProgressDialog1.show();
        mProgressDialog1.setCancelable(true);
        mProgressDialog1.setContentView(R.layout.progressdialog_ecom);
        mProgressDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/

             mProgressDialog.setVisibility(View.VISIBLE);


             Call<AddAddressDetail> wsCallingAddAddress = mAPIInterface.AddAddressDetails(mobile, fullName, addressLine1,
                     addressLine2, landmark, city, state, pinCode, addressType, deviceId);
             wsCallingAddAddress.enqueue(new Callback<AddAddressDetail>() {
                 @Override
                 public void onResponse(Call<AddAddressDetail> call, Response<AddAddressDetail> response) {
                     if (response.isSuccessful()) {
                         Log.d("Response Address Start", "Response Address Start " + response.body().getMessage());
                         if (mProgressDialog != null) {
                             mProgressDialog.setVisibility(View.GONE);
                         }
                         if (response.code() == 200) {
                             Log.d("Response Address 200", "Response Address 200 " + response.body().getMessage());
                             if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                 Log.d("Response Address Suc", "Response Address Success " + response.body().getMessage());
                                 SweetAlertDialog loading = new SweetAlertDialog(AddAddressAcountActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                 loading.setCancelable(true);
                                 loading.setConfirmText("OK");
                                 loading.setOnShowListener(new DialogInterface.OnShowListener() {
                                     @Override
                                     public void onShow(DialogInterface dialog) {
                                         SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                                         MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                                         MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                                         MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                                         textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                         textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                         // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                         textContent.setTypeface(typeface);
                                         textTitle.setTypeface(typeface);
                                         btnConfirm.setTypeface(typeface);
                                         alertDialog.setCancelable(false);
                                         textTitle.setText(response.body().getMessage());
                                         textTitle.setText("Your Address Added Successfully ");
                                         btnConfirm.setText("OK");
                                         // textContent.setText("Pin you have entered is Invalid.");
                                         textContent.setGravity(Gravity.NO_GRAVITY);
                                         btnConfirm.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 loading.dismiss();
                                                 //finish();
                                                 Intent editAddress = new Intent(AddAddressAcountActivity.this, MyAddressActivity.class);
                                                 editAddress.putExtra("addAddressCallBack", "addAddressCallBack");
                                                 editAddress.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                 startActivity(editAddress);
                                                 overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                                             }
                                         });
                                     }
                                 });

                                 loading.show();
                             }
                         }
                     }
                 }

                 @Override
                 public void onFailure(Call<AddAddressDetail> call, Throwable t) {
                     mProgressDialog.setVisibility(View.GONE);
                     Log.d("Response Address Error", "Response Address Error " + t);
                 }
             });
         }else{
             utils.dialogueNoInternet(this);
         }
    }


    public Boolean checkValidation() {
        boolean ret1 = true;
        if (tvAddAddressName.getText().toString().length() == 0) {
            tvAddAddressName.setError("Invalid Input");
           // tvAddAddressName.requestFocus();
            ret1 = false;
        }
        if (tvAddAddressMobile.getText().toString().length() == 0 ||tvAddAddressMobile.getText().toString().length() < 10) {
            tvAddAddressMobile.setError("Invalid Input");
            //tvAddAddressMobile.requestFocus();
            ret1 = false;
        }
        if (tvAddAddressPincode.getText().toString().length() == 0 ||
                tvAddAddressPincode.getText().toString().length() < 6 ) {
            tvAddAddressPincode.setError("Invalid Input");
            // tvAddAddressPincode.requestFocus();
            ret1 =false;
        }
        if (tvAddAddressOne.getText().toString().length() == 0) {
            tvAddAddressOne.setError("Invalid Input");
           // tvAddAddressOne.requestFocus();
            ret1 =false;
        }
        if (tvAddAddressTwo.getText().toString().length() == 0) {
            tvAddAddressTwo.setError("Invalid Input");
            //tvAddAddressTwo.requestFocus();
            ret1 = false;
        }
        if (tvAddAddressLandmark.getText().toString().length() == 0) {
            tvAddAddressLandmark.setError("Invalid Input");
           // tvAddAddressLandmark.requestFocus();
            ret1 = false;
        }
        if (spinnerAddressState.getSelectedItem() != null && spinnerAddressState.getSelectedItem().toString().equals("State*")) {
            ((TextView) spinnerAddressState.getSelectedView()).setError("State*");
            ret1 = false;
        }
        if (spinnerAddressCity.getSelectedItem() != null && spinnerAddressCity.getSelectedItem().toString().equals("City*")) {
            ((TextView) spinnerAddressCity.getSelectedView()).setError("City*");
            ret1 = false;
        }
        if (spinnerAddressType.getSelectedItem() != null && spinnerAddressType.getSelectedItem().toString().equals("Select an Address Type*")) {
            ((TextView) spinnerAddressType.getSelectedView()).setError("Select an Address Type*");
            ret1 = false;
        }
        return ret1;
    }


}