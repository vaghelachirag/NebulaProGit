
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.fragments.form.Validations;
import com.nebulacompanies.ibo.model.PlacementID;
import com.nebulacompanies.ibo.model.Registation;
import com.nebulacompanies.ibo.model.RegistationOTP;
import com.nebulacompanies.ibo.model.SponsorID;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.view.NebulaEditText;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.fragments.form.Validations.isValidEmail;
import static com.nebulacompanies.ibo.fragments.form.Validations.isValidID;
import static com.nebulacompanies.ibo.fragments.form.Validations.isValidMobile;
import static com.nebulacompanies.ibo.fragments.form.Validations.isValidPhone;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class RegistationActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {


    NebulaEditText sponsoredt, placementedt, firstnameedt, middlenameedt, lastnameedt,
            emailedt, numberedt, otpedt, dobedt;
    CheckBox rechek;
    private APIInterface mAPIInterface;
    public static final String TAG = "Registation";
    Button registerbtn, getotptxt;
    LinearLayout enter_otp;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_registration_animation);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            isIBO = b.getString("IBOID");
            entersponsor = b.getBoolean("enter");

        }
       /* SweetAlertDialog loading = new SweetAlertDialog(RegistationActivity.this, SweetAlertDialog.SUCCESS_TYPE);
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
                textTitle.setText("Success");
                btnConfirm.setText("OK");
                textContent.setText("Click my web site: www.stackoverflow.com");
                Linkify.addLinks(textContent, Linkify.WEB_URLS);
                textContent.setGravity(Gravity.NO_GRAVITY);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loading.dismiss();
                        Intent login = new Intent(RegistationActivity.this, LoginActivity.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(login);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        finish();
                    }
                });
            }
        });
        loading.show();*/
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
        getotptxt = (Button) findViewById(R.id.getOtpTxt);
        otpedt = (NebulaEditText) findViewById(R.id.edt_reg_mobile_otp);
        rechek = (CheckBox) findViewById(R.id.cb_reg);
        registerbtn = (Button) findViewById(R.id.btn_register);
        enter_otp = (LinearLayout) findViewById(R.id.enter_otp);
        lnTc = (RelativeLayout) findViewById(R.id.ln_tc);
        sponsoretxt = (NebulaEditText) findViewById(R.id.sponsor_id_txt);
        placementext = (NebulaEditText) findViewById(R.id.placement_id_txt);
        imgBack = (ImageView) findViewById(R.id.img_back_arrow);
        policiesbtn = (MyTextView) findViewById(R.id.policies);
        disclaimerbtn = (MyTextView) findViewById(R.id.disclaimerbtn);
        dobedt = (NebulaEditText) findViewById(R.id.edt_reg_dob);
        sponsoredt.addTextChangedListener(this);
        placementedt.addTextChangedListener(this);
        numberedt.addTextChangedListener(this);
        otpedt.addTextChangedListener(this);
        dobedt.addTextChangedListener(this);
        /*sponsoredt.setOnKeyListener(this);
        placementedt.setOnKeyListener(this);
        firstnameedt.setOnKeyListener(this);
        lastnameedt.setOnKeyListener(this);
        emailedt.setOnKeyListener(this);
        numberedt.setOnKeyListener(this);*/
        policiesbtn.setOnClickListener(this);
        disclaimerbtn.setOnClickListener(this);
        rechek.setTypeface(ResourcesCompat.getFont(RegistationActivity.this, R.font.montserrat_regular));
        rechek.setChecked(false);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        // registerbtn.setText("show");
        registerbtn.setTypeface(typeface);
        getotptxt.setTypeface(typeface);
        policiesbtn.setTypeface(typeface);
        disclaimerbtn.setTypeface(typeface);
        getotptxt.setOnClickListener(this);
        rechek.setOnClickListener(this);
        registerbtn.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        dobedt.setOnClickListener(this);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        if (!entersponsor) {
            sponsoredt.setText(isIBO);
            CallSponsorId(isIBO);
            sponsoredt.setClickable(false);
            sponsoredt.setEnabled(false);
        }
        emailedt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    // Perform action on key press
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isAcceptingText()) {
                        imm.hideSoftInputFromWindow(emailedt.getWindowToken(), 0);
                        getDateOfBirth();
                    }
                    return true;
                }
                return false;
            }
        });
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
        } else if (editable == dobedt.getEditableText()) {
            if (dobedt.getText().toString().length() > 0) {
                dobedt.setError(null);
            }
        } else if (editable == numberedt.getEditableText()) {


            if (numberedt.getText().toString().length() > 0 && !isValidPhone(numberedt.getText().toString())) {
                getotptxt.setVisibility(View.GONE);
                enter_otp.setVisibility(View.GONE);
                otpedt.setVisibility(View.GONE);
            } else if (isValidMobile(numberedt.getText().toString())) {
                getotptxt.setVisibility(View.VISIBLE);
                enter_otp.setVisibility(View.VISIBLE);
                otpedt.setVisibility(View.VISIBLE);
                getotptxt.setText("Get OTP");
            }
        } else if (editable == otpedt.getEditableText()) {
            if (otpedt.getText().toString().length() == 6) {
                if (!otpedt.getText().toString().equals(Config.RegistationOTP)) {
                    otpedt.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_close_red), null);
                    getotptxt.setVisibility(View.VISIBLE);
                } else {
                    otpedt.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_right_green), null);
                    getotptxt.setVisibility(View.GONE);
                    numberedt.setEnabled(false);

                    sponsoredt.setEnabled(false);
                    placementedt.setEnabled(false);
                    otpedt.setFocusable(false);
                    otpedt.setClickable(false);
                    otpedt.setFocusableInTouchMode(false);

                    /*if (dobedt.getText().toString().length() > 0) {
                        dobedt.setEnabled(false);
                    }
                    if (firstnameedt.getText().toString().length() > 0) {
                        firstnameedt.setEnabled(false);
                    }
                    if (middlenameedt.getText().toString().length() > 0) {
                        middlenameedt.setEnabled(false);
                    }
                    if (lastnameedt.getText().toString().length() > 0) {
                        lastnameedt.setEnabled(false);
                    }*/
                    /*if (emailedt.getText().toString().length() > 0) {
                        emailedt.setEnabled(false);
                    }*/
                    /*numberedt.setClickable(false);
                    numberedt.setFocusable(false);
                    numberedt.setFocusableInTouchMode(false);
                    otpedt.setFocusable(false);
                    otpedt.setClickable(false);
                    otpedt.setFocusableInTouchMode(false);*/
                }
            } else if (otpedt.getText().toString().length() <= 0 || otpedt.getText().toString().length() < 6) {
                otpedt.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_close_red), null);
                getotptxt.setVisibility(View.VISIBLE);
                dobedt.setEnabled(true);
                numberedt.setEnabled(true);
                firstnameedt.setEnabled(true);
                middlenameedt.setEnabled(true);
                lastnameedt.setEnabled(true);
                placementedt.setEnabled(true);
                sponsoredt.setEnabled(true);
                emailedt.setEnabled(true);
            }
        }
    }

    private void CallSponsorId(String SponsorID) {
        Call<SponsorID> wsCallingSponsorID = mAPIInterface.getSponsorID(SponsorID);
        wsCallingSponsorID.enqueue(new Callback<SponsorID>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(Call<SponsorID> call, Response<SponsorID> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                        sponsoretxt.setText(response.body().getData());
                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                        if (sponsoredt.getText().toString().length() == 5) {
                            Toast.makeText(RegistationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                          /*  SweetAlertDialog loading = new SweetAlertDialog(RegistationActivity.this, SweetAlertDialog.WARNING_TYPE);
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
                                            finish();
                                        }
                                    });
                                }
                            });

                            loading.show();*/
                            sponsoredt.getText().clear();
                           /* registerbtn.setEnabled(false);
                            getotptxt.setEnabled(false);*/
                            //registerbtn.setBackgroundColor(getResources().getColor(R.drawable.drawable_rounded_button_disabled));
                            //getotptxt.setBackgroundColor(getResources().getColor(R.drawable.drawable_rounded_button_disabled));
                           /* registerbtn.setBackgroundResource(R.drawable.drawable_rounded_button_disabled);
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
                            Toast.makeText(RegistationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
      /*  final ProgressDialog mProgressDialog = new ProgressDialog(RegistationActivity.this, R.style.MyTheme);
        mProgressDialog.show();

        mProgressDialog.setCancelable(false);
        mProgressDialog.setContentView(R.layout.progressdialog);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
*/

        Call<RegistationOTP> wsCallingRegistationOtp = mAPIInterface.getRegistationOtp(MobileNumber);
        wsCallingRegistationOtp.enqueue(new Callback<RegistationOTP>() {
            @Override
            public void onResponse(Call<RegistationOTP> call, Response<RegistationOTP> response) {
               /* if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }*/
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                        Config.RegistationOTP = String.valueOf(response.body().getData());
                        enter_otp.setVisibility(View.VISIBLE);
                        otpedt.setVisibility(View.VISIBLE);
                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR) {
                        enter_otp.setVisibility(View.GONE);
                        otpedt.setVisibility(View.GONE);
                        getotptxt.setVisibility(View.GONE);
                        SweetAlertDialog loading = new SweetAlertDialog(RegistationActivity.this, SweetAlertDialog.ERROR_TYPE);
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
                                textTitle.setText(response.body().getMessage());
                                btnConfirm.setText("OK");
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
                //mProgressDialog.dismiss();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getOtpTxt:

                if (sponsoredt.getText().toString().length() == 0 ||
                        placementedt.getText().toString().length() == 0) {
                    sponsoredt.setError("Error");
                    sponsoredt.requestFocus();
                    placementedt.setError("Error");
                    placementedt.requestFocus();
                } else {
                    getotptxt.setText(R.string.resend_otp);
                    enter_otp.setVisibility(View.VISIBLE);
                    MoblieGetOtp(numberedt.getText().toString());
                }

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


           /* case R.id.btn_register:
                if (rechek.isChecked()) {
                    if (Utils.isNetworkAvailable(getApplicationContext())) {
                        if (sponsoredt.getText().toString().length()==0 && placementedt.getText().toString().length()==0 && firstnameedt.getText().toString().length()==0 && lastnameedt.getText().toString().length()==0 && emailedt.getText().toString().length()==0 && numberedt.getText().toString().length()==0) {
                            checkValidation();
                        } else {
                            if (isValidID(sponsoredt.getText().toString()) && isValidID(placementedt.getText().toString()) && isValidname(placementext.getText().toString()) && isValidname(sponsoretxt.getText().toString()) && isValidname(firstnameedt.getText().toString()) && isValidname(middlenameedt.getText().toString()) && isValidname(lastnameedt.getText().toString()) && isValidEmail(emailedt.getText().toString()) && isValidMobile(numberedt.getText().toString()) && !otpedt.getText().toString().isEmpty()) {
                                if (!Config.RegistationOTP.equals(otpedt.getText().toString())) {
                                    Toast.makeText(this, R.string.otp_registation, Toast.LENGTH_SHORT).show();
                                } else if (Config.RegistationOTP.equals(otpedt.getText().toString())) {
                               *//* getregistation(sponsoredt.getText().toString(), sponsoretxt.getText().toString(), placementedt.getText().toString(), placementext.getText().toString(),
                                        firstnameedt.getText().toString(), middlenameedt.getText().toString(),
                                        lastnameedt.getText().toString(), emailedt.getText().toString(),
                                        numberedt.getText().toString());*//*

                                    getregistation(sponsoredt.getText().toString(), sponsoretxt.getText().toString(), placementedt.getText().toString(), placementext.getText().toString(),
                                            firstnameedt.getText().toString(), middlenameedt.getText().toString(),
                                            lastnameedt.getText().toString(), emailedt.getText().toString(),
                                            dobedt.getText().toString(), numberedt.getText().toString());

                                } else {
                                    Toast.makeText(this, R.string.enter_otp_registation, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, R.string.enter_registation, Toast.LENGTH_SHORT).show();
                            }
                        }


                    } else {
                        Toast.makeText(this, R.string.reaccept, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    errorNetworkDailogue();
                }
                break;*/

            case R.id.btn_register:
                if (rechek.isChecked()) {
                    if (Utils.isNetworkAvailable(getApplicationContext())) {
                        if (sponsoredt.getText().toString().length() == 0 || placementedt.getText().toString().length() == 0 || firstnameedt.getText().toString().length() == 0 || lastnameedt.getText().toString().length() == 0 || emailedt.getText().toString().length() == 0 || dobedt.getText().toString().length() == 0 || numberedt.getText().toString().length() == 0 || otpedt.getText().length() == 0) {
                            checkValidation();
                        } else {
                            if (!Config.RegistationOTP.equals(otpedt.getText().toString())) {
                                Toast.makeText(this, R.string.otp_registation, Toast.LENGTH_SHORT).show();
                            } else if (Config.RegistationOTP.equals(otpedt.getText().toString())) {
                                if (!Validations.isValidID(sponsoredt.getText().toString()) || !Validations.isValidID(placementedt.getText().toString()) || !Validations.isValidname(firstnameedt.getText().toString()) || !Validations.isValidname(lastnameedt.getText().toString()) || !Validations.isValidEmail(emailedt.getText().toString()) || dobedt.getText().toString().length() == 0 || !isValidPhone(numberedt.getText().toString()) || !isValidID(otpedt.getText().toString())) {
                                    checkValidation();
                                } else {
                                    getregistation(sponsoredt.getText().toString(), sponsoretxt.getText().toString(), placementedt.getText().toString(), placementext.getText().toString(),
                                            firstnameedt.getText().toString(), middlenameedt.getText().toString(),
                                            lastnameedt.getText().toString(), emailedt.getText().toString(),
                                            dobedt.getText().toString(), numberedt.getText().toString());
                                }
                            } else {
                                Toast.makeText(this, R.string.enter_otp_registation, Toast.LENGTH_SHORT).show();
                            }
                        }


                    } else {
                        errorNetworkDailogue();
                    }
                } else {
                    Toast.makeText(this, R.string.reaccept, Toast.LENGTH_SHORT).show();
                }
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


    /*public Boolean checkValidation() {
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
        if (emailedt.getText().toString().length() == 0 || !isValidEmail(emailedt.getText().toString())) {
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

    public Boolean checkValidation() {
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

        if (lastnameedt.getText().toString().length() == 0) {
            lastnameedt.setError("Error");
            lastnameedt.requestFocus();
            return false;
        }
        if (emailedt.getText().toString().length() == 0 || !isValidEmail(emailedt.getText().toString())) {
            emailedt.setError("Error");
            emailedt.requestFocus();
            return false;
        }

        if (dobedt.getText().toString().length() == 0) {
            dobedt.setError("Error");
            dobedt.requestFocus();
            return false;
        }
        if (numberedt.getText().toString().length() == 0) {
            numberedt.setError("Error");
            numberedt.requestFocus();
            return false;
        }

        if (otpedt.getText().toString().length() == 0) {
            otpedt.setError("Error");
            otpedt.requestFocus();
            return false;
        }


        return true;
    }


    private void getregistation(String sponsoreId, String sponsoreName, String
            placementId, String placementName, String firstname, String middlename, String
                                        lastname, String emailId, String Dob, String number) {
        final ProgressDialog mProgressDialog = new ProgressDialog(RegistationActivity.this, R.style.MyTheme);
        mProgressDialog.show();

        mProgressDialog.setCancelable(false);
        mProgressDialog.setContentView(R.layout.progressdialog);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

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
                        SweetAlertDialog loading = new SweetAlertDialog(RegistationActivity.this, SweetAlertDialog.SUCCESS_TYPE);
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
                               // btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                textContent.setTypeface(typeface);
                                textTitle.setTypeface(typeface);
                                btnConfirm.setTypeface(typeface);
                                textTitle.setText(response.body().getMessage());
                                btnConfirm.setText("OK");
                                textContent.setText("");
                                // textContent.setText("Please complete your profile details by following link: \n https://nebulacompanies.net/");
                                // Linkify.addLinks(textContent, Linkify.WEB_URLS);
                                //  textContent.setGravity(Gravity.NO_GRAVITY);
                                btnConfirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //  loading.dismiss();
                                        /*Intent login = new Intent(RegistationActivity.this, LoginActivity.class);
                                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                        finish();*/
                                        if (!entersponsor) {
                                            Intent Dashboard = new Intent(RegistationActivity.this, DashboardActivity.class);
                                            Dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(Dashboard);
                                        } else {
                                            Intent login = new Intent(RegistationActivity.this, LoginActivity.class);
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

                        SweetAlertDialog loading = new SweetAlertDialog(RegistationActivity.this, SweetAlertDialog.ERROR_TYPE);
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
                                textTitle.setText(response.body().getMessage());
                                btnConfirm.setText("OK");
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
        getotptxt.setEnabled(true);
        getotptxt.setClickable(true);


    }


    //Terms and Conditions Popup
    private void openPrivacyPolicy() {

        ///SalesAdapter.ViewHolder2 holder2 = null;
        LayoutInflater mInflater2 = (LayoutInflater)
                this.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Dialog dialog1 = new Dialog(RegistationActivity.this);
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
        Dialog dialog1 = new Dialog(RegistationActivity.this);
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
        SweetAlertDialog loading = new SweetAlertDialog(RegistationActivity.this, SweetAlertDialog.SUCCESS_TYPE);
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
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loading.dismiss();
                        Intent login = new Intent(RegistationActivity.this, LoginActivity.class);
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

        DatePickerDialog d = new DatePickerDialog(RegistationActivity.this, dpd, mYear, mMonth, mDay);
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
        SweetAlertDialog loading = new SweetAlertDialog(RegistationActivity.this, SweetAlertDialog.ERROR_TYPE);
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
                textTitle.setText("Network Error");
                textContent.setText(R.string.error_msg_network);
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
    }
}
