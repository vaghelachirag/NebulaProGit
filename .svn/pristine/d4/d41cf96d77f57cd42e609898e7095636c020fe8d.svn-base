package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.BackDetails;
import com.nebulacompanies.ibo.model.BackList;
import com.nebulacompanies.ibo.model.FullRegistaionDetail;
import com.nebulacompanies.ibo.model.PanVerify;
import com.nebulacompanies.ibo.model.PlacementID;
import com.nebulacompanies.ibo.model.Registation;
import com.nebulacompanies.ibo.model.RegistationOTP;
import com.nebulacompanies.ibo.model.RelationDeatils;
import com.nebulacompanies.ibo.model.RelationList;
import com.nebulacompanies.ibo.model.SponsorID;
import com.nebulacompanies.ibo.model.StateDetails;
import com.nebulacompanies.ibo.model.StateList;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.view.NebulaEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.fragments.form.Validations.isIfscCodeValid;
import static com.nebulacompanies.ibo.fragments.form.Validations.isValidMobile;
import static com.nebulacompanies.ibo.fragments.form.Validations.isValidPhone;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class UpdateProfile extends AppCompatActivity implements TextWatcher, View.OnClickListener, View.OnKeyListener, TextView.OnEditorActionListener {


    CheckBox rechek;
    private APIInterface mAPIInterface;
    public static final String TAG = "Registation";
    Button registerbtn;
    RelativeLayout lnTc;
    NebulaEditText sponsoretxt, placementext;
    String isIBO;
    boolean entersponsor;
    ImageView imgBack;
    MyTextView policiesbtn, disclaimerbtn;
    Typeface typeface;
    int mYear, mMonth, mDay;
    SharedPreferences mySPrefs;
    SharedPreferences.Editor editor;
    Session session;
    ArrayList<String> listState = new ArrayList<>();
    ArrayList<Integer> listStateID = new ArrayList<>();
    ArrayList<String> listCity = new ArrayList<>();
    Spinner stateSpinner, citySpinner, bankSpinner, relationSpinner;
    public static ArrayList<StateList> arraystateList = new ArrayList<>();
    int state;
    int city;
    String selectedState, stateName, selectedcity, bankName = "";
    public static ArrayList<BackList> arraybackList = new ArrayList<>();
    ArrayList<String> listbank = new ArrayList<>();
    ArrayList<Integer> listbankID = new ArrayList<>();

    public static ArrayList<RelationList> arrayrelationList = new ArrayList<>();
    ArrayList<String> listrelation = new ArrayList<>();
    ArrayList<Integer> listrelationID = new ArrayList<>();
    RadioButton male, female;

    NebulaEditText sponsoredt, placementedt, firstnameedt, middlenameedt, lastnameedt,
            emailedt, numberedt, dobedt, panedt, ifscedt, accuountedt, addressedt,
            pincodeedt, alternatenumberedt, nomineeeedt, aadhaaredt, holdernameedt, branchnameedt, branchcityedt;
    Integer bankId;
    InputMethodManager imm;

    String sponsorID, sponsorName, placementID, placementName, firstName,
            middleName, lastName, dOB, phoneNumber, EmailAddress, gender, address,
            setstate, setcity, pincode, alternateMobile, panNo, aadhaarNo, nomineeName,
            relation, accountNo, accountHolderName, iFSCNo, branchName, branchCity;
    int setbankId = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_update_profile_animation);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            sponsorID = b.getString("sponsorID");
            sponsorName = b.getString("sponsorName");
            placementID = b.getString("placementID");
            placementName = b.getString("placementName");
            firstName = b.getString("firstName");
            middleName = b.getString("middleName");
            lastName = b.getString("lastName");
            dOB = b.getString("dOB");
            phoneNumber = b.getString("phoneNumber");
            EmailAddress = b.getString("EmailAddress");
            gender = b.getString("gender");
            address = b.getString("address");
            setstate = b.getString("state");
            setcity = b.getString("city");
            pincode = b.getString("pincode");
            alternateMobile = b.getString("alternateMobile");
            panNo = b.getString("panNo");
            aadhaarNo = b.getString("aadhaarNo");
            nomineeName = b.getString("nomineeName");
            relation = b.getString("relation");
            accountNo = b.getString("accountNo");
            accountHolderName = b.getString("accountHolderName");
            iFSCNo = b.getString("iFSCNo");
            setbankId = b.getInt("bankId");
            branchName = b.getString("branchName");
            branchCity = b.getString("branchCity");
        }
        session = new Session(this);
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        //setupWindowAnimations();
        sponsoredt = (NebulaEditText) findViewById(R.id.edt_sponsor_id);
        //   sponsoretxt = (MyTextView) findViewById(R.id.sponsor_id_txt);
        placementedt = (NebulaEditText) findViewById(R.id.edt_placement_id);
        //  placementext = (MyTextView) findViewById(R.id.placement_id_txt);
        firstnameedt = (NebulaEditText) findViewById(R.id.edt_reg_first_name);
        middlenameedt = (NebulaEditText) findViewById(R.id.edt_reg_middle_name);
        lastnameedt = (NebulaEditText) findViewById(R.id.edt_reg_last_name);
        emailedt = (NebulaEditText) findViewById(R.id.edt_reg_email);
        numberedt = (NebulaEditText) findViewById(R.id.edt_reg_mobile_number);

        rechek = (CheckBox) findViewById(R.id.cb_reg);
        registerbtn = (Button) findViewById(R.id.btn_register);

        lnTc = (RelativeLayout) findViewById(R.id.ln_tc);
        sponsoretxt = (NebulaEditText) findViewById(R.id.sponsor_id_txt);
        placementext = (NebulaEditText) findViewById(R.id.placement_id_txt);
        imgBack = (ImageView) findViewById(R.id.img_back_arrow);
        policiesbtn = (MyTextView) findViewById(R.id.policies);
        disclaimerbtn = (MyTextView) findViewById(R.id.disclaimerbtn);
        dobedt = (NebulaEditText) findViewById(R.id.edt_reg_dob);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        stateSpinner = findViewById(R.id.State_detail);
        citySpinner = findViewById(R.id.city_detail);
        panedt = findViewById(R.id.edt_reg_pan);
        ifscedt = findViewById(R.id.edt_ifsc_code);
        bankSpinner = findViewById(R.id.bank_detail);
        relationSpinner = findViewById(R.id.nominee_detail);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        bankSpinner = findViewById(R.id.bank_detail);
        relationSpinner = findViewById(R.id.nominee_detail);
        accuountedt = findViewById(R.id.edt_account_number);
        addressedt = findViewById(R.id.edt_address);
        pincodeedt = findViewById(R.id.edt_reg_pin_code);
        alternatenumberedt = findViewById(R.id.edt_reg_alternate_mobile_number);
        nomineeeedt = findViewById(R.id.edt_reg_nomineee);
        aadhaaredt = findViewById(R.id.edt_reg_aadhaar);
        holdernameedt = findViewById(R.id.edt_acc_holder_name);
        branchnameedt = findViewById(R.id.edt_branch_name);
        branchcityedt = findViewById(R.id.edt_branch_city);
        sponsoredt.addTextChangedListener(this);
        placementedt.addTextChangedListener(this);
        numberedt.addTextChangedListener(this);
        alternatenumberedt.addTextChangedListener(this);
        //  middlenameedt.addTextChangedListener(this);
        lastnameedt.addTextChangedListener(this);

        policiesbtn.setOnClickListener(this);
        disclaimerbtn.setOnClickListener(this);
        rechek.setTypeface(ResourcesCompat.getFont(UpdateProfile.this, R.font.montserrat_regular));
        rechek.setChecked(false);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        // registerbtn.setText("show");
        registerbtn.setTypeface(typeface);

        policiesbtn.setTypeface(typeface);
        disclaimerbtn.setTypeface(typeface);

        male.setTypeface(typeface);
        female.setTypeface(typeface);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (female.isChecked()) {
                    male.setChecked(false);
                    Config.gender = "female";
                }
            }
        });

        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (male.isChecked()) {
                    female.setChecked(false);
                    Config.gender = "male";
                }
            }
        });

        rechek.setOnClickListener(this);
        registerbtn.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        dobedt.setOnClickListener(this);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        /*if (!entersponsor) {
            sponsoredt.setText(isIBO);
            CallSponsorId(isIBO);
            sponsoredt.setClickable(false);
            sponsoredt.setEnabled(false);
        }*/
        panedt.setOnEditorActionListener(this);
        pincodeedt.setOnEditorActionListener(this);
        nomineeeedt.setOnEditorActionListener(this);
        accuountedt.setOnEditorActionListener(this);

        Checkdetials();
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            if (setstate.toString().length() == 0) {
                getState();
                stateSpinner.setEnabled(true);
                citySpinner.setEnabled(true);
            }
            if (relation.toString().length() == 0) {
                getRelactionDetails();
                relationSpinner.setEnabled(true);
            }
            if (setbankId == 0) {
                getBankDetails();
                bankSpinner.setEnabled(true);
            }
        }

    }

    private void Checkdetials() {
        sponsoredt.setText(sponsorID);
        if (sponsoredt.getText().toString().length() > 0) {
            sponsoredt.setEnabled(false);
            sponsoretxt.setEnabled(false);
            sponsoretxt.setText(sponsorName);
            placementedt.setText(placementID);
            placementext.setText(placementName);
            if (placementedt.getText().toString().length() > 0) {
                placementext.setEnabled(false);
                placementedt.setEnabled(false);
            }
            firstnameedt.setText(firstName);
            lastnameedt.setText(lastName);
            if (firstnameedt.getText().toString().length() > 0 && lastnameedt.getText().toString().length() > 0) {
                firstnameedt.setEnabled(false);
                lastnameedt.setEnabled(false);
            }
            middlenameedt.setText(middleName);
            if (middlenameedt.getText().toString().length() > 0) {
                middlenameedt.setEnabled(false);
            }
            dobedt.setText(dOB);
            if (dobedt.getText().toString().length() > 0) {
                dobedt.setEnabled(false);
            }
            emailedt.setText(EmailAddress);
            if (emailedt.getText().toString().length() > 0) {
                emailedt.setEnabled(false);
            }
            addressedt.setText(address);
            if (addressedt.getText().toString().length() > 0) {
                addressedt.setEnabled(false);
            }
            pincodeedt.setText(pincode);
            if (pincodeedt.getText().toString().length() > 0) {
                pincodeedt.setEnabled(false);
            }
            numberedt.setText(phoneNumber);
            if (numberedt.getText().toString().length() > 0) {
                numberedt.setEnabled(false);
            }
            alternatenumberedt.setText(alternateMobile);
            if (alternatenumberedt.getText().toString().length() > 0) {
                alternatenumberedt.setEnabled(false);
            }
            aadhaaredt.setText(aadhaarNo);
            if (aadhaaredt.getText().toString().length() > 0) {
                aadhaaredt.setEnabled(false);
            }
            panedt.setText(panNo);
            if (panedt.getText().toString().length() > 0) {
                panedt.setEnabled(false);
            }

            nomineeeedt.setText(nomineeName);
            if (nomineeeedt.getText().toString().length() > 0) {
                nomineeeedt.setEnabled(false);
            }
            accuountedt.setText(accountNo);

            if (accuountedt.getText().toString().length() > 0) {
                accuountedt.setEnabled(false);
            }
            holdernameedt.setText(firstnameedt.getText().toString() + " " + middlenameedt.getText().toString() + " " + lastnameedt.getText().toString());
            if (holdernameedt.getText().toString().length() > 0) {
                holdernameedt.setEnabled(false);
            }
            ifscedt.setText(iFSCNo);
            if (ifscedt.getText().toString().length() > 0) {
                ifscedt.setEnabled(false);
            }
            branchnameedt.setText(branchName);
            if (branchnameedt.getText().toString().length() > 0) {
                branchnameedt.setEnabled(false);
            }
            branchcityedt.setText(branchCity);
            if (branchcityedt.getText().toString().length() > 0) {
                branchcityedt.setEnabled(false);
            }
            if (gender.equalsIgnoreCase("male")) {
                male.setChecked(true);
                male.setClickable(false);
                female.setClickable(false);
            } else if (gender.equalsIgnoreCase("female")) {
                female.setChecked(true);
                male.setClickable(false);
                female.setClickable(false);
            }

            listState.clear();
            listCity.clear();
            listrelation.clear();

            listState.add(setstate);
            listCity.add(setcity);
            listrelation.add(relation);
            ArrayAdapter<String> setStateAdapter = new ArrayAdapter<>(UpdateProfile.this, R.layout.spinner_textview, listState);
            setStateAdapter.setDropDownViewResource(R.layout.spinner_textview_item);
            stateSpinner.setAdapter(setStateAdapter);

            ArrayAdapter<String> setCityAdapter = new ArrayAdapter<>(UpdateProfile.this, R.layout.spinner_textview, listCity);
            setCityAdapter.setDropDownViewResource(R.layout.spinner_textview_item);
            citySpinner.setAdapter(setCityAdapter);


            ArrayAdapter<String> setrelationAdapter = new ArrayAdapter<>(UpdateProfile.this, R.layout.spinner_textview, listrelation);
            setrelationAdapter.setDropDownViewResource(R.layout.spinner_textview_item);
            relationSpinner.setAdapter(setrelationAdapter);


            Call<BackDetails> wsCallingBackDetails = mAPIInterface.getGetBackDetails();
            wsCallingBackDetails.enqueue(new Callback<BackDetails>() {
                @Override
                public void onResponse(Call<BackDetails> call, Response<BackDetails> response) {
                    if (response.isSuccessful()) {
                        listbank.clear();
                        listbankID.clear();
                        for (BackList backList : response.body().getData()) {
                            String bankName = backList.getName();
                            int bankid = backList.getId();
                            if (setbankId == bankid) {
                                listbankID.add(bankid);
                                listbank.add(bankName);
                            }
                        }
                        ArrayAdapter<String> setbankAdapter = new ArrayAdapter<>(UpdateProfile.this, R.layout.spinner_textview, listbank);
                        setbankAdapter.setDropDownViewResource(R.layout.spinner_textview_item);
                        bankSpinner.setAdapter(setbankAdapter);

                    }
                }

                @Override
                public void onFailure(Call<BackDetails> call, Throwable t) {
                }
            });
        }


        stateSpinner.setEnabled(false);
        citySpinner.setEnabled(false);
        bankSpinner.setEnabled(false);
        relationSpinner.setEnabled(false);

          /*  setstate=stateSpinner.getSelectedItem().toString();
            if (setstate.length() > 0) {
                stateSpinner.setClickable(false);
                stateSpinner.setEnabled(false);
            }
            setcity = citySpinner.getSelectedItem().toString();
            if (setcity.length() > 0) {
                citySpinner.setClickable(false);
                citySpinner.setEnabled(false);
            }*/
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable == sponsoredt.getEditableText()) {
            if (sponsoredt.getText().toString().length() == 5) {
                CallSponsorId(sponsoredt.getText().toString());
            } else if (sponsoredt.getText().toString().length() == 0) {
                sponsoretxt.setText("");
            }
        } else if (editable == placementedt.getEditableText()) {
            if (sponsoredt.getText().toString().length() == 0) {
                placementedt.setError("Error");
                placementedt.requestFocus();
                placementext.setText("");
            } else if (placementedt.getText().toString().length() == 0) {
                placementext.setText("");
            } else if (placementedt.getText().toString().length() == 5) {
                CallplacementId(sponsoredt.getText().toString(), placementedt.getText().toString());
            }
        } else if (editable == numberedt.getEditableText()) {
            if (numberedt.getText().toString().length() > 0 && !isValidPhone(numberedt.getText().toString())) {

            } else if (isValidMobile(numberedt.getText().toString())) {

            }
        } else if (editable == alternatenumberedt.getEditableText()) {
            if (alternatenumberedt.getText().toString().equals(numberedt.getText().toString())) {
                alternatenumberedt.setError("Error");
                alternatenumberedt.requestFocus();
            }

        }


        }/*else if (editable == middlenameedt.getEditableText()) {
            if (middlenameedt.getText().toString().length() > 0)
                holdernameedt.setText(firstnameedt.getText().toString() + " " + middlenameedt.getText().toString());
        } else if (editable == lastnameedt.getEditableText()) {
            if (lastnameedt.getText().toString().length() > 0)
                holdernameedt.setText(firstnameedt.getText().toString() + " " + middlenameedt.getText().toString() + " " + lastnameedt.getText().toString());
        }*/

    private void CallSponsorId(String SponsorID) {
        Call<com.nebulacompanies.ibo.model.SponsorID> wsCallingSponsorID = mAPIInterface.getSponsorID(SponsorID);
        wsCallingSponsorID.enqueue(new Callback<SponsorID>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(Call<SponsorID> call, Response<SponsorID> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                        sponsoretxt.setText(response.body().getData());
                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                        if (sponsoredt.getText().toString().length() == 5) {
                            // Toast.makeText(RegistationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                           /* SweetAlertDialog loading = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.WARNING_TYPE);
                            loading.setCancelable(true);
                            loading.setConfirmText("OK");
                            loading.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialog) {
                                    SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                                    TextView textTitle = (TextView) alertDialog.findViewById(R.id.title_text);
                                    TextView textContent = (TextView) alertDialog.findViewById(R.id.content_text);
                                    Button btnConfirm = (Button) alertDialog.findViewById(R.id.confirm_button);
                                    textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                    textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                    btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                    textContent.setTypeface(typeface);
                                    textTitle.setTypeface(typeface);
                                    btnConfirm.setTypeface(typeface);
                                    //textTitle.setText("Error");
                                    // textContent.setText(response.body().getMessage());
                                    textContent.setText("As an associate you are not authorized to sponsor. ");
                                    btnConfirm.setText("OK");
                                    textContent.setGravity(Gravity.NO_GRAVITY);
                                    btnConfirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            loading.dismiss();
                                          //  finish();
                                        }
                                    });
                                }
                            });

                            loading.show();

                           registerbtn.setEnabled(false);
                            getotptxt.setEnabled(false);
                            registerbtn.setBackgroundResource(R.drawable.drawable_rounded_button_disabled);
                            getotptxt.setBackgroundResource(R.drawable.drawable_rounded_button_disabled);*/
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SponsorID> call, Throwable t) {
            }
        });

    }


    private void CallplacementId(String SponsorID, String placementID) {
        Call<PlacementID> wsCallingPlacementID = mAPIInterface.getPlacementID(SponsorID, placementID);
        wsCallingPlacementID.enqueue(new Callback<PlacementID>() {
            @Override
            public void onResponse(Call<PlacementID> call, Response<PlacementID> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                        placementext.setText(response.body().getData());

                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                        if (placementedt.getText().toString().length() == 5) {
                            Toast.makeText(UpdateProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PlacementID> call, Throwable t) {

            }
        });

    }

    private void MoblieGetOtp(String MobileNumber) {
        final ProgressDialog mProgressDialog = new ProgressDialog(UpdateProfile.this, R.style.MyTheme);
        mProgressDialog.show();

        mProgressDialog.setCancelable(false);
        mProgressDialog.setContentView(R.layout.progressdialog);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        Call<RegistationOTP> wsCallingRegistationOtp = mAPIInterface.getRegistationOtp(MobileNumber);
        wsCallingRegistationOtp.enqueue(new Callback<RegistationOTP>() {
            @Override
            public void onResponse(Call<RegistationOTP> call, Response<RegistationOTP> response) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                        Config.RegistationOTP = String.valueOf(response.body().getData());

                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {

                        SweetAlertDialog loading = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.ERROR_TYPE);
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
                                textTitle.setText(response.body().getMessage());
                                btnConfirm.setText("OK");
                                alertDialog.setCancelable(false);
                                // textContent.setText("Pin you have entered is Invalid.");
                                textContent.setGravity(Gravity.NO_GRAVITY);
                                btnConfirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loading.dismiss();
                                        //  finish();
                                    }
                                });
                            }
                        });

                        loading.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistationOTP> call, Throwable t) {
                mProgressDialog.dismiss();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getOtpTxt:

                MoblieGetOtp(numberedt.getText().toString());
                break;

            case R.id.cb_reg:
                if (!rechek.isChecked()) {
                    rechek.setChecked(false);
                } else {
                    rechek.setChecked(true);
                }
                break;

            case R.id.img_back_arrow:
                onBackPressed();
                break;


            case R.id.btn_register:
                if (Utils.isNetworkAvailable(getApplicationContext())) {
                    if (addressedt.getText().toString().length() == 0 || pincodeedt.getText().toString().length() == 0 || accuountedt.getText().toString().length() == 0 || ifscedt.getText().toString().length() == 0 || branchnameedt.getText().toString().length() == 0 || branchcityedt.getText().toString().length() == 0 ||
                            stateSpinner.getSelectedItem().toString().equals("State*") || citySpinner.getSelectedItem().toString().equals("City*")
                        /* || bankSpinner.getSelectedItem().toString().equals("*Please select Bank")*/) {
                        checkValidation();

                    } else {
                        if (relationSpinner.getSelectedItem().toString().equals("Relation")) {

                            if (setbankId == 0) {
                                sendUpdateDetails(session.getLoginID(), Config.gender, addressedt.getText().toString(), stateSpinner.getSelectedItem().toString(), citySpinner.getSelectedItem().toString(), pincodeedt.getText().toString(), alternatenumberedt.getText().toString(), panedt.getText().toString(), aadhaaredt.getText().toString(), nomineeeedt.getText().toString(), " ", bankId, holdernameedt.getText().toString(), accuountedt.getText().toString(), ifscedt.getText().toString(), branchnameedt.getText().toString(), branchcityedt.getText().toString());

                            } else {
                                sendUpdateDetails(session.getLoginID(), Config.gender, addressedt.getText().toString(), stateSpinner.getSelectedItem().toString(), citySpinner.getSelectedItem().toString(), pincodeedt.getText().toString(), alternatenumberedt.getText().toString(), panedt.getText().toString(), aadhaaredt.getText().toString(), nomineeeedt.getText().toString(), " ", setbankId, holdernameedt.getText().toString(), accuountedt.getText().toString(), ifscedt.getText().toString(), branchnameedt.getText().toString(), branchcityedt.getText().toString());
                                listbank.add(bankName);
                            }
                        } else {
                            if (setbankId == 0) {
                                sendUpdateDetails(session.getLoginID(), Config.gender, addressedt.getText().toString(), stateSpinner.getSelectedItem().toString(), citySpinner.getSelectedItem().toString(), pincodeedt.getText().toString(), alternatenumberedt.getText().toString(), panedt.getText().toString(), aadhaaredt.getText().toString(), nomineeeedt.getText().toString(), relationSpinner.getSelectedItem().toString(), bankId, holdernameedt.getText().toString(), accuountedt.getText().toString(), ifscedt.getText().toString(), branchnameedt.getText().toString(), branchcityedt.getText().toString());
                                listbank.add(bankName);
                            } else {
                                sendUpdateDetails(session.getLoginID(), Config.gender, addressedt.getText().toString(), stateSpinner.getSelectedItem().toString(), citySpinner.getSelectedItem().toString(), pincodeedt.getText().toString(), alternatenumberedt.getText().toString(), panedt.getText().toString(), aadhaaredt.getText().toString(), nomineeeedt.getText().toString(), relationSpinner.getSelectedItem().toString(), setbankId, holdernameedt.getText().toString(), accuountedt.getText().toString(), ifscedt.getText().toString(), branchnameedt.getText().toString(), branchcityedt.getText().toString());
                                listbank.add(bankName);
                            }
                        }
                    }
                }

                /*else {
                    errorNetworkDailogue();
                }*/
                break;

            case R.id.policies:
              /*  Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.TESTING_API + "/Home/Index2"));
                startActivity(browserIntent);*/
                openPrivacyPolicy();
                policiesbtn.setEnabled(false);

                break;
            case R.id.disclaimerbtn:
               /* Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.TESTING_API + "/Home/Index2"));
                startActivity(browser);*/
                disclaimerPolicy();
                disclaimerbtn.setEnabled(false);
                break;
            case R.id.edt_reg_dob:
                if (dobedt.isClickable()) {
                    dobedt.setEnabled(false);
                    getDateOfBirth();
                }
                break;
        }
    }




  /*  public Boolean checkValidation() {
        if (sponsoredt.getText().toString().length() == 0) {
            sponsoredt.setError("Error");
            sponsoredt.requestFocus();
            return false;
        }
        if (placementedt.getText().toString().length() == 0) {
            placementedt.setError("Error");
            placementedt.requestFocus();
            return false;
        }
        if (firstnameedt.getText().toString().length() == 0) {
            firstnameedt.setError("Error");
            firstnameedt.requestFocus();
            return false;
        }
        if (middlenameedt.getText().toString().length() == 0) {
            middlenameedt.setError("Error");
            middlenameedt.requestFocus();
            return false;
        }
        if (lastnameedt.getText().toString().length() == 0) {
            lastnameedt.setError("Error");
            lastnameedt.requestFocus();
            return false;
        }
        if (emailedt.getText().toString().length() == 0) {
            emailedt.setError("Error");
            emailedt.requestFocus();
            return false;
        }
        if (numberedt.getText().toString().length() == 0) {
            numberedt.setError("Error");
            numberedt.requestFocus();
            return false;
        }

        return true;
    }*/

    private void getregistation(String sponsoreId, String sponsoreName, String
            placementId, String placementName, String firstname, String middlename, String
                                        lastname, String emailId, String Dob, String number) {
        final ProgressDialog mProgressDialog = new ProgressDialog(UpdateProfile.this, R.style.MyTheme);
        mProgressDialog.show();

        mProgressDialog.setCancelable(false);
        mProgressDialog.setContentView(R.layout.progressdialog);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Call<Registation> wsCallingRegistation = mAPIInterface.getregistation(sponsoreId, sponsoreName, placementId, placementName, firstname, middlename, lastname, emailId, Dob, number);
        wsCallingRegistation.enqueue(new Callback<Registation>() {
            @Override
            public void onResponse(Call<Registation> call, Response<Registation> response) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                /*if (response.isSuccessful()) {
if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
Toast.makeText(RegistationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
} else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {
Toast.makeText(RegistationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
}
*/
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                        //Toast.makeText(RegistationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        //confirmDailogue();
                        SweetAlertDialog loading = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.SUCCESS_TYPE);
                        loading.setCancelable(false);
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
                              //  btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                textContent.setTypeface(typeface);
                                textTitle.setTypeface(typeface);
                                btnConfirm.setTypeface(typeface);
                                textTitle.setText(response.body().getMessage());
                                btnConfirm.setText("OK");
                                textContent.setText("Please complete your profile details by following link: \n https://nebulacompanies.net/");
                                Linkify.addLinks(textContent, Linkify.WEB_URLS);
                                textContent.setGravity(Gravity.NO_GRAVITY);
                                alertDialog.setCancelable(false);
                                btnConfirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //  loading.dismiss();
                                        /*Intent login = new Intent(RegistationActivity.this, LoginActivity.class);
                                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                        finish();*/
                                        if (!entersponsor) {
                                            Intent Dashboard = new Intent(UpdateProfile.this, DashboardActivity.class);
                                            Dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(Dashboard);
                                        } else {
                                            Intent login = new Intent(UpdateProfile.this, LoginActivity.class);
                                            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(login);
                                            // overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                        }

                                       /* session.setLogout(true);
                                        session.setLogin(false);
                                        // if (mySPrefs != null) {
                                        mySPrefs = PreferenceManager.getDefaultSharedPreferences(RegistationActivity.this);
                                        editor = mySPrefs.edit();
                                        editor.remove("rankAndVolume");
                                        editor.remove("bv_lists");
                                        editor.remove("income_list");
                                        editor.remove("personal_list");
                                        editor.remove("team_list");
                                        editor.remove("New_joinees");
                                        editor.remove("DownlineRank");
                                        editor.remove("projects_list");
                                        editor.remove("my_sales_list");
                                        editor.remove("income");
                                        editor.remove("incomeList");
                                        editor.remove("download_list");
                                        editor.remove("placement");
                                        editor.remove("sponsorList");
                                        editor.remove("SalesHoliday");
                                        editor.remove("ProductList");
                                        editor.apply();
                                        //  }
                                        startActivity(login);
                                        session.clear();
                                        Config.CUSTOMER_LOGIN_ID = "";*/
                                        loading.dismiss();

                                    }
                                });
                            }
                        });
                        loading.show();
                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {
                        // Toast.makeText(RegistationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        SweetAlertDialog loading = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.ERROR_TYPE);
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
                                textTitle.setText(response.body().getMessage());
                                btnConfirm.setText("OK");
                                alertDialog.setCancelable(false);
                                // textContent.setText("Pin you have entered is Invalid.");
                                textContent.setGravity(Gravity.NO_GRAVITY);
                                btnConfirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loading.dismiss();
                                        //  finish();
                                    }
                                });
                            }
                        });

                        loading.show();
                    }


                }

            }

            @Override
            public void onFailure(Call<Registation> call, Throwable t) {
                mProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    //Terms and Conditions Popup
    private void openPrivacyPolicy() {

        ///SalesAdapter.ViewHolder2 holder2 = null;
        LayoutInflater mInflater2 = (LayoutInflater)
                this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Dialog dialog1 = new Dialog(UpdateProfile.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        View convertView1 = null;
        if (convertView1 == null) {
            convertView1 = mInflater2.inflate(R.layout.term_condition_promotions, null);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog1.setCancelable(true);
            dialog1.setContentView(convertView1);
            dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            MyTextView policy = (MyTextView) convertView1.findViewById(R.id.tv_policy);
            ImageView imgPolicyClose = (ImageView) convertView1.findViewById(R.id.img_policy_close);
            policy.setText(Html.fromHtml(getString(R.string.policy)));
            imgPolicyClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    policiesbtn.setEnabled(true);
                    dialog1.dismiss();
                }
            });
        }

        dialog1.show();
    }


    //Disclaimer Popup
    private void disclaimerPolicy() {

        ///SalesAdapter.ViewHolder2 holder2 = null;
        LayoutInflater mInflater2 = (LayoutInflater)
                this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Dialog dialog1 = new Dialog(UpdateProfile.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        View convertView1 = null;
        if (convertView1 == null) {
            convertView1 = mInflater2.inflate(R.layout.disclaimer, null);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog1.setCancelable(true);
            dialog1.setContentView(convertView1);
            dialog1.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            MyTextView policy = (MyTextView) convertView1.findViewById(R.id.tv_policy);
            ImageView imgPolicyClose = (ImageView) convertView1.findViewById(R.id.img_policy_close);
            policy.setText(Html.fromHtml(getString(R.string.disclaimer_txt)));
            imgPolicyClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disclaimerbtn.setEnabled(true);
                    dialog1.dismiss();
                }
            });
        }
if(!dialog1.isShowing())
        dialog1.show();
    }


    private void confirmDailogue() {
        SweetAlertDialog loading = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.SUCCESS_TYPE);
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
                //btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                textTitle.setText("Success");
                btnConfirm.setText("OK");
                textContent.setText("Pin Verified Successfully.");
                textContent.setGravity(Gravity.NO_GRAVITY);
                alertDialog.setCancelable(false);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loading.dismiss();
                        Intent login = new Intent(UpdateProfile.this, LoginActivity.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(login);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        finish();
                    }
                });
            }
        });
        loading.show();
    }

    void getDateOfBirth() {
        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.YEAR, -18);
        mYear = minDate.get(Calendar.YEAR);
        mMonth = minDate.get(Calendar.MONTH);
        mDay = minDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                int s = monthOfYear + 1;
                String a = dayOfMonth + "-" + s + "-" + year;
                dobedt.setText("" + a);
                numberedt.requestFocus();
            }
        };

        DatePickerDialog d = new DatePickerDialog(UpdateProfile.this, dpd, mYear, mMonth, mDay);
        d.getDatePicker().setMaxDate(minDate.getTimeInMillis());

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(dobedt.getWindowToken(), 0);
        }

        d.show();

        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dobedt.setEnabled(true);
                dobedt.setClickable(true);
            }
        });

    }


    //No Internet Dialogue
    private void errorNetworkDailogue() {
        SweetAlertDialog loading = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.ERROR_TYPE);
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
              //  btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textContent.setTypeface(typeface);
                textTitle.setTypeface(typeface);
                btnConfirm.setTypeface(typeface);
                textTitle.setText("Network Error");
                textContent.setText(R.string.error_msg_network);
                btnConfirm.setText("OK");
                textContent.setGravity(Gravity.NO_GRAVITY);
                alertDialog.setCancelable(false);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loading.dismiss();
                        //  finish();
                    }
                });
            }
        });

        loading.show();
    }


    void getState() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog1 = new ProgressDialog(UpdateProfile.this, R.style.MyTheme);
            mProgressDialog1.show();
            mProgressDialog1.setCancelable(true);
            mProgressDialog1.setContentView(R.layout.progressdialog);
            mProgressDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Call<StateDetails> wsCallingStates = mAPIInterface.getStates("India");
            wsCallingStates.enqueue(new Callback<StateDetails>() {
                @Override
                public void onResponse(Call<StateDetails> call, Response<StateDetails> response) {
                    if (response.isSuccessful()) {
                        mProgressDialog1.dismiss();
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

                        /*if (sponsoredt.getText().toString().length() > 0) {
                            stateSpinner.requestFocus();
                            stateSpinner.performClick();
                        }*/

                    } else {
                        mProgressDialog1.dismiss();
                        //serviceUnavailable();
                    }
                }

                @Override
                public void onFailure(Call<StateDetails> call, Throwable t) {
                    mProgressDialog1.dismiss();
                    //Log.e(TAG, "ERROR : " + t.getMessage());
                    //serviceUnavailable();
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(UpdateProfile.this);
        }
    }

    void addToSpinner() {

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(UpdateProfile.this, R.layout.spinner_textview, listState) {
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
                    spinnerstatetextview.setTextColor(Color.WHITE);
                }
                return spinnerview;
            }
        };

        stateAdapter.setDropDownViewResource(R.layout.spinner_textview_item);
        stateSpinner.setAdapter(stateAdapter);

        getCity();
    }

    void getCity() {
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateSpinner.clearFocus();

                if (!stateSpinner.getSelectedItem().toString().equals("State*")) {
                    if (sponsoredt.getText().toString().length() > 0) {
                        citySpinner.requestFocus();
                        citySpinner.performClick();
                    }
                }

                selectedState = stateSpinner.getSelectedItem().toString();
                state = stateSpinner.getSelectedItemPosition();
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
            final ProgressDialog mProgressDialog2 = new ProgressDialog(UpdateProfile.this, R.style.MyTheme);
            mProgressDialog2.show();
            mProgressDialog2.setCancelable(true);
            mProgressDialog2.setContentView(R.layout.progressdialog);
            mProgressDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Call<JsonObject> wsCallingCities = mAPIInterface.getCities(stateId);
            wsCallingCities.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        Log.i("INFO", " responseString Cities : " + response.body().toString());
                        mProgressDialog2.dismiss();

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

                                if (sponsoredt.getText().toString().length() > 0) {
                                    addToCitySpinner();
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        mProgressDialog2.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    mProgressDialog2.dismiss();
                    //Log.e(TAG, "ERROR : " + t.getMessage());
                    //serviceUnavailable();
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(UpdateProfile.this);
        }
    }

    void addToCitySpinner() {
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(UpdateProfile.this, R.layout.spinner_textview, listCity) {
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
                    spinnercitytextview.setTextColor(Color.WHITE);
                }
                return spinnerview;
            }
        };
        cityAdapter.setDropDownViewResource(R.layout.spinner_textview_item);
        citySpinner.setAdapter(cityAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedcity = citySpinner.getSelectedItem().toString();
                city = citySpinner.getSelectedItemPosition();
                citySpinner.clearFocus();
                alternatenumberedt.requestFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
      /*  if (alternatenumberedt.requestFocus()){
            imm.toggleSoftInputFromWindow(alternatenumberedt.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        }*/
    }


    /*private void getBankDetails() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog1 = new ProgressDialog(UpdateProfile.this, R.style.MyTheme);
            mProgressDialog1.show();
            mProgressDialog1.setCancelable(true);
            mProgressDialog1.setContentView(R.layout.progressdialog);
            mProgressDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Call<BackDetails> wsCallingBackDetails = mAPIInterface.getGetBackDetails();
            wsCallingBackDetails.enqueue(new Callback<BackDetails>() {
                @Override
                public void onResponse(Call<BackDetails> call, Response<BackDetails> response) {
                    if (response.isSuccessful()) {
                        mProgressDialog1.dismiss();
                        listbank.clear();
                        listbankID.clear();
                        listbankID.add(0,0);
                        for (BackList backList : response.body().getData()) {

                            String bankName = backList.getName();
                            int bankid = backList.getId();

                            android.util.Log.i("INFO", " responseString state : " + bankName);
                            Log.i("INFO", " responseString state_id : " + bankid);

                            listStateID.add(bankid);
                            listbank.add(bankName);
                            arraybackList.add(backList);
                        }
                        listbank.add(0, "*Please select Bank");

                        addToSpinnerBack();

                    } else {
                        mProgressDialog1.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<BackDetails> call, Throwable t) {
                    mProgressDialog1.dismiss();
                    //Log.e(TAG, "ERROR : " + t.getMessage());
                    //serviceUnavailable();
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(UpdateProfile.this);
        }
    }*/

    /*private void addToSpinnerBack() {
        ArrayAdapter<String> BackAdapter = new ArrayAdapter<String>(UpdateProfile.this, R.layout.spinner_textview, listbank) {
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
                    spinnerstatetextview.setTextColor(Color.WHITE);
                }
                return spinnerview;
            }
        };

        BackAdapter.setDropDownViewResource(R.layout.spinner_textview);
        bankSpinner.setAdapter(BackAdapter);

        bankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bankId=listbankID.get(i);
                Log.i("INFO","call bank id:-"+bankId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }*/


    private void getBankDetails() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog1 = new ProgressDialog(UpdateProfile.this, R.style.MyTheme);
            mProgressDialog1.show();
            mProgressDialog1.setCancelable(true);
            mProgressDialog1.setContentView(R.layout.progressdialog);
            mProgressDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Call<BackDetails> wsCallingBackDetails = mAPIInterface.getGetBackDetails();
            wsCallingBackDetails.enqueue(new Callback<BackDetails>() {
                @Override
                public void onResponse(Call<BackDetails> call, Response<BackDetails> response) {
                    if (response.isSuccessful()) {
                        mProgressDialog1.dismiss();
                        listbank.clear();
                        listbankID.clear();
                        listbankID.add(0, 0);
                        for (BackList backList : response.body().getData()) {

                            String bankName = backList.getName();
                            int bankid = backList.getId();

                            android.util.Log.i("INFO", " responseString state : " + bankName);
                            Log.i("INFO", " responseString state_id : " + bankid);
                            listbankID.add(bankid);
                            listbank.add(bankName);

                            arraybackList.add(backList);
                        }
                        listbank.add(0, "Bank*");

                        addToSpinnerBack();

                       /* if (sponsoredt.getText().toString().length() > 0) {
                            bankSpinner.requestFocus();
                            bankSpinner.performClick();
                        }*/

                    } else {
                        mProgressDialog1.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<BackDetails> call, Throwable t) {
                    mProgressDialog1.dismiss();
                    //Log.e(TAG, "ERROR : " + t.getMessage());
                    //serviceUnavailable();
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(UpdateProfile.this);
        }
    }

    private void addToSpinnerBack() {
        ArrayAdapter<String> BackAdapter = new ArrayAdapter<String>(UpdateProfile.this, R.layout.spinner_textview, listbank) {
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
                    spinnerstatetextview.setTextColor(Color.WHITE);
                }
                return spinnerview;
            }
        };

        BackAdapter.setDropDownViewResource(R.layout.spinner_textview_item);
        bankSpinner.setAdapter(BackAdapter);

        bankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bankName = listbank.get(i);
                bankId = listbankID.get(i);
                Log.i("INFO", "call bank id:-" + bankId);
                Log.i("INFO", "Call bank Name:-" + bankName);
                //  ifscedt.requestFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getRelactionDetails() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Call<RelationDeatils> wsCallingRelationDetails = mAPIInterface.getGetRelationDetails();
            wsCallingRelationDetails.enqueue(new Callback<RelationDeatils>() {
                @Override
                public void onResponse(Call<RelationDeatils> call, Response<RelationDeatils> response) {
                    if (response.isSuccessful()) {
                        listrelation.clear();
                        listrelationID.clear();
                        for (RelationList relationList : response.body().getData()) {

                            String relationName = relationList.getName();
                            int relationid = relationList.getId();

                            android.util.Log.i("INFO", " responseString state : " + relationName);
                            Log.i("INFO", " responseString state_id : " + relationid);
                            if (!listrelation.contains(relationName)) {
                                listrelation.add(relationName);
                            }
                            Collections.sort(listrelation);

                            listrelationID.add(relationid);

                            arrayrelationList.add(relationList);
                        }
                        listrelation.add(0, "Relation");

                        addToSpinnerRelation();

                      /*  if (sponsoredt.getText().toString().length() > 0) {
                            relationSpinner.requestFocus();
                            relationSpinner.performClick();
                            aadhaaredt.requestFocus();
                        }*/

                    }
                }

                @Override
                public void onFailure(Call<RelationDeatils> call, Throwable t) {
                    //Log.e(TAG, "ERROR : " + t.getMessage());
                    //serviceUnavailable();
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(UpdateProfile.this);
        }
    }

    private void addToSpinnerRelation() {
        ArrayAdapter<String> RelationAdapter = new ArrayAdapter<String>(UpdateProfile.this, R.layout.spinner_textview, listrelation) {
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
                    spinnerstatetextview.setTextColor(Color.WHITE);
                }
                return spinnerview;
            }
        };

        RelationAdapter.setDropDownViewResource(R.layout.spinner_textview_item);
        relationSpinner.setAdapter(RelationAdapter);
    }


    /*private void getIboDetails() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Call<GetIboDetails> wsCallingGetIboDetails = mAPIInterface.getGetIboDetails(session.getLoginID());
            wsCallingGetIboDetails.enqueue(new Callback<GetIboDetails>() {
                @Override
                public void onResponse(Call<GetIboDetails> call, Response<GetIboDetails> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                sponsoredt.setText(response.body().getData().getSponsorID());
                                sponsoretxt.setText(response.body().getData().getSponsorName());
                                placementedt.setText(response.body().getData().getPlacementID());
                                placementext.setText(response.body().getData().getPlacementName());
                                firstnameedt.setText(response.body().getData().getFirstName());
                                middlenameedt.setText(response.body().getData().getMiddleName());
                                lastnameedt.setText(response.body().getData().getLastName());
                                dobedt.setText(response.body().getData().getDOB());
                                emailedt.setText(response.body().getData().getEmailAddress());
                                numberedt.setText(response.body().getData().getPhoneNumber());
                                if (sponsoredt.getText().toString().length() > 0 || sponsoretxt.getText().toString().length() > 0) {
                                    sponsoredt.setEnabled(false);
                                    sponsoretxt.setEnabled(false);
                                }
                                if (placementedt.getText().toString().length() > 0 || placementext.getText().toString().length() > 0) {
                                    placementedt.setEnabled(false);
                                    placementext.setEnabled(false);
                                }
                                if (firstnameedt.getText().toString().length() > 0) {
                                    firstnameedt.setEnabled(false);
                                }
                                if (middlenameedt.getText().toString().length() > 0) {
                                    middlenameedt.setEnabled(false);
                                }
                                if (lastnameedt.getText().toString().length() > 0) {
                                    lastnameedt.setEnabled(false);
                                }

                                if (numberedt.getText().toString().length() > 0) {
                                    numberedt.setEnabled(false);
                                }
                                if (emailedt.getText().toString().length() > 0) {
                                    emailedt.setEnabled(false);
                                }
                                if (dobedt.getText().toString().length() > 0) {
                                    dobedt.setEnabled(false);
                                }

                                holdernameedt.setText(firstnameedt.getText().toString() + " " + middlenameedt.getText().toString() + " " + lastnameedt.getText().toString());


                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetIboDetails> call, Throwable t) {
                }
            });
        }
    }*/


    public Boolean checkValidation() {
        if (addressedt.getText().toString().length() == 0) {
            addressedt.setError("Error");
            addressedt.requestFocus();
            return false;
        }
        if (pincodeedt.getText().toString().length() == 0 || pincodeedt.getText().toString().length() < 6) {
            pincodeedt.setError("Error");
            pincodeedt.requestFocus();
            return false;
        }
        if (stateSpinner.getSelectedItem() != null && stateSpinner.getSelectedItem().toString().equals("State*")) {
            ((TextView) stateSpinner.getSelectedView()).setError("State*");
            return false;
        }
        if (citySpinner.getSelectedItem() != null && citySpinner.getSelectedItem().toString().equals("City*")) {
            ((TextView) citySpinner.getSelectedView()).setError("City*");
            return false;
        }
        if (alternatenumberedt.getText().toString().length() > 0) {
            if (alternatenumberedt.getText().toString().equals(numberedt.getText().toString()) || alternatenumberedt.getText().toString().length() < 10) {
                alternatenumberedt.setError("Error");
                alternatenumberedt.requestFocus();
            }
            return false;
        }
        if (accuountedt.getText().toString().length() == 0 ) {
            accuountedt.setError("Error");
            accuountedt.requestFocus();
            return false;
        }
        if (holdernameedt.getText().toString().length() == 0) {
            accuountedt.setError("Error");
            accuountedt.requestFocus();
            return false;
        }
        if (bankSpinner.getSelectedItem() != null && bankSpinner.getSelectedItem().toString().equals("Bank*")) {
            ((TextView) bankSpinner.getSelectedView()).setError("Bank*");
            return false;
        }
        if (!isIfscCodeValid(ifscedt.getText().toString()) || ifscedt.getText().toString().length() < 11) {
            ifscedt.setError("Error");
            ifscedt.requestFocus();
            return false;
        }
        if (branchnameedt.getText().toString().length() == 0) {
            branchnameedt.setError("Error");
            branchnameedt.requestFocus();
            return false;
        }
        if (branchcityedt.getText().toString().length() == 0) {
            branchcityedt.setError("Error");
            branchcityedt.requestFocus();
            return false;
        }

        return true;
    }


    private void sendUpdateDetails(String loginID, String gender, String address, String state, String city, String pincode, String alternatenumber, String pancard, String aadhaarcard, String nomineeeName, String relation, Integer bankId, String holdername, String accuount, String ifsc, String branchname, String branchcity) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog1 = new ProgressDialog(UpdateProfile.this, R.style.MyTheme);
            mProgressDialog1.show();
            mProgressDialog1.setCancelable(true);
            mProgressDialog1.setContentView(R.layout.progressdialog);
            mProgressDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Call<FullRegistaionDetail> wsCallingFullRegistaionDetail = mAPIInterface.SendRegistaionUpdateDetail(loginID, gender, address, state, city, pincode, alternatenumber, pancard, aadhaarcard, nomineeeName, relation, bankId, holdername, accuount, ifsc, branchname, branchcity);
            wsCallingFullRegistaionDetail.enqueue(new Callback<FullRegistaionDetail>() {
                @Override
                public void onResponse(Call<FullRegistaionDetail> call, Response<FullRegistaionDetail> response) {
                    if (response.isSuccessful()) {
                        if (mProgressDialog1.isShowing() && mProgressDialog1 != null) {
                            mProgressDialog1.dismiss();
                        }
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                SweetAlertDialog loading = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.SUCCESS_TYPE);
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
                                        textTitle.setText(response.body().getMessage());
                                        btnConfirm.setText("OK");
                                        alertDialog.setCancelable(false);
                                        // textContent.setText("Pin you have entered is Invalid.");
                                        textContent.setGravity(Gravity.NO_GRAVITY);
                                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                loading.dismiss();
                                               /* Intent intent = new Intent(UpdateProfile.this, DashboardActivity.class);
                                                intent.putExtra("updateDone","updateDone");
                                                startActivity(intent);*/
                                                finish();
                                            }
                                        });
                                    }
                                });

                                loading.show();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {

                                SweetAlertDialog loading = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.ERROR_TYPE);
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
                                        textTitle.setText(response.body().getMessage());
                                        btnConfirm.setText("OK");
                                        alertDialog.setCancelable(false);
                                        // textContent.setText("Pin you have entered is Invalid.");
                                        textContent.setGravity(Gravity.NO_GRAVITY);
                                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                               /* sponsoredt.getText().clear();
                                                placementedt.getText().clear();
                                                firstnameedt.getText().clear();
                                                middlenameedt.getText().clear();
                                                lastnameedt.getText().clear();
                                                dobedt.getText().clear();
                                                addressedt.getText().clear();
                                                pincodeedt.getText().clear();
                                                emailedt.getText().clear();
                                                numberedt.getText().clear();
                                                alternatenumberedt.getText().clear();
                                                nomineeeedt.getText().clear();
                                                aadhaaredt.getText().clear();
                                                panedt.getText().clear();
                                                accuountedt.getText().clear();
                                                ifscedt.getText().clear();
                                                branchnameedt.getText().clear();
                                                branchcityedt.getText().clear();
                                                holdernameedt.getText().clear();
                                                arraystateList.clear();
                                                arraybackList.clear();
                                                arrayrelationList.clear();*/
                                                loading.dismiss();

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
                public void onFailure(Call<FullRegistaionDetail> call, Throwable t) {
                    mProgressDialog1.dismiss();
                }
            });
        } else {
            Toast.makeText(this, R.string.error_msg_network, Toast.LENGTH_SHORT).show();
        }

    }

    private void sendFullDetails(String sponsorid, String sponsorename, String placementid, String placementname, String firstname, String middlename, String lastname, String email, String number, String dob, String gender, String adderss, String stateName, String cityname, String pincode, String alternatenumber, String pancard, String aadhaarcard, String nomineee, String relation, Integer bankId, String holdername, String accuount, String ifsc, String branchname, String branchcity) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog1 = new ProgressDialog(UpdateProfile.this, R.style.MyTheme);
            mProgressDialog1.show();
            mProgressDialog1.setCancelable(true);
            mProgressDialog1.setContentView(R.layout.progressdialog);
            mProgressDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Call<FullRegistaionDetail> wsCallingFullRegistaionDetail = mAPIInterface.SendRegistaionFullDetail(sponsorid, sponsorename, placementid, placementname, firstname, middlename, lastname, email, number, dob, gender, adderss, stateName, cityname, pincode, alternatenumber, pancard, aadhaarcard, nomineee, relation, bankId, holdername, accuount, ifsc, branchname, branchcity);
            wsCallingFullRegistaionDetail.enqueue(new Callback<FullRegistaionDetail>() {
                @Override
                public void onResponse(Call<FullRegistaionDetail> call, Response<FullRegistaionDetail> response) {
                    if (response.isSuccessful()) {
                        if (mProgressDialog1.isShowing() && mProgressDialog1 != null) {
                            mProgressDialog1.dismiss();
                        }
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                SweetAlertDialog loading = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.SUCCESS_TYPE);
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
                                      //  btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                        textContent.setTypeface(typeface);
                                        textTitle.setTypeface(typeface);
                                        btnConfirm.setTypeface(typeface);
                                        textTitle.setText(response.body().getMessage());
                                        btnConfirm.setText("OK");
                                        alertDialog.setCancelable(false);
                                        // textContent.setText("Pin you have entered is Invalid.");
                                        textContent.setGravity(Gravity.NO_GRAVITY);
                                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                loading.dismiss();
                                            }
                                        });
                                    }
                                });

                                loading.show();
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {

                                SweetAlertDialog loading = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.ERROR_TYPE);
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
                                        textTitle.setText(response.body().getMessage());
                                        btnConfirm.setText("OK");
                                        alertDialog.setCancelable(false);

                                        // textContent.setText("Pin you have entered is Invalid.");
                                        textContent.setGravity(Gravity.NO_GRAVITY);
                                        btnConfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                loading.dismiss();

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
                public void onFailure(Call<FullRegistaionDetail> call, Throwable t) {
                    mProgressDialog1.dismiss();
                }
            });
        } else {
            Toast.makeText(this, R.string.error_msg_network, Toast.LENGTH_SHORT).show();
        }
    }


    private void getPanCardDetails() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            final ProgressDialog mProgressDialog1 = new ProgressDialog(UpdateProfile.this, R.style.MyTheme);
            mProgressDialog1.show();
            mProgressDialog1.setCancelable(true);
            mProgressDialog1.setContentView(R.layout.progressdialog);
            mProgressDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Call<PanVerify> wsCallingPanVerify = mAPIInterface.getGetPanVerify(panedt.getText().toString());
            wsCallingPanVerify.enqueue(new Callback<PanVerify>() {
                @Override
                public void onResponse(Call<PanVerify> call, Response<PanVerify> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                if (response.body().isData()) {
                                    /*panedt.setError("Error");
                                    panedt.requestFocus();
                                    panedt.setText(null);*/
                                    SweetAlertDialog loading = new SweetAlertDialog(UpdateProfile.this, SweetAlertDialog.ERROR_TYPE);
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
                                            textTitle.setText(R.string.invalid_pancard);
                                            btnConfirm.setText("OK");
                                            alertDialog.setCancelable(false);
                                            // textContent.setText("Pin you have entered is Invalid.");
                                            textContent.setGravity(Gravity.NO_GRAVITY);
                                            btnConfirm.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    panedt.requestFocus();
                                                    panedt.setText(null);
                                                    loading.dismiss();
                                                    imm.toggleSoftInputFromWindow(alternatenumberedt.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);

                                                }
                                            });
                                        }
                                    });

                                    loading.show();
                                } else {
                                    accuountedt.requestFocus();
                                    imm.toggleSoftInputFromWindow(alternatenumberedt.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                                }
                            }
                        }
                        if (mProgressDialog1.isShowing() && mProgressDialog1 != null) {
                            mProgressDialog1.dismiss();
                        }
                    } else {
                        mProgressDialog1.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<PanVerify> call, Throwable t) {
                    mProgressDialog1.dismiss();
                }
            });
        } else {
            AppUtils.displayNetworkErrorMessage(UpdateProfile.this);
        }
    }


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.edt_reg_pin_code:
                if ((event.getAction() == KeyEvent.ACTION_DOWN) ||
                        (keyCode == KeyEvent.KEYCODE_ENTER) || (keyCode == KeyEvent.KEYCODE_MEDIA_NEXT)) {
                    // Perform action on key press
                    if (imm.isAcceptingText()) {
                        imm.hideSoftInputFromWindow(pincodeedt.getWindowToken(), 0);
                        getState();
                    }
                    return true;
                }
                break;

            case R.id.edt_reg_pan:
                //   if (actionId == EditorInfo.IME_ACTION_NEXT) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) ||
                        (keyCode == KeyEvent.KEYCODE_ENTER) || (keyCode == KeyEvent.KEYCODE_MEDIA_NEXT)) {
                    // Perform action on key press
                    if (imm.isAcceptingText()) {
                        imm.hideSoftInputFromWindow(panedt.getWindowToken(), 0);
                        getPanCardDetails();
                    }
                    return true;
                }
                break;

        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        switch (view.getId()) {
            case R.id.edt_reg_pin_code:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    // Perform action on key press
                    if (imm.isAcceptingText()) {
                        imm.hideSoftInputFromWindow(pincodeedt.getWindowToken(), 0);
                        getState();
                    }
                    return true;
                }
                break;

            case R.id.edt_reg_pan:
                //   if (actionId == EditorInfo.IME_ACTION_NEXT) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    // Perform action on key press
                    if (imm.isAcceptingText()) {
                        imm.hideSoftInputFromWindow(panedt.getWindowToken(), 0);
                        getPanCardDetails();
                    }
                    return true;
                }
                break;

            case R.id.edt_reg_nomineee:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    // Perform action on key press

                    /*  getRelactionDetails();*/
                    if (imm.isAcceptingText()) {
                        imm.hideSoftInputFromWindow(panedt.getWindowToken(), 0);
                        getRelactionDetails();
                    }
                    return true;

                }
                break;

            case R.id.edt_account_number:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    // Perform action on key press

                    /*  getRelactionDetails();*/
                    if (imm.isAcceptingText()) {
                        imm.hideSoftInputFromWindow(panedt.getWindowToken(), 0);
                        getBankDetails();
                    }
                    return true;

                }
                break;


        }
        return false;
    }
}
