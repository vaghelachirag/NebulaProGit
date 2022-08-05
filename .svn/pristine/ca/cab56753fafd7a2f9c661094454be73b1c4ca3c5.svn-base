package com.nebulacompanies.ibo.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialog;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.ChangePassword;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.view.NebulaEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.SetDateFormat.SetGmtTime;

//TODO : Personal Details Fragment
@SuppressLint("ValidFragment")
public class PersonalAccountFragment extends Fragment {
    //TODO : Class Components
    private View convertView;
    private LinearLayout llPhoneNo, llEmail, llUserName;
    private TextView txtName, txtPhoneNo, txtEmailId, txtDateOfJoining, txtGender, txtAddress, llChangePwd;
    private APIInterface mAPIInterface;
    // private NestedScrollView scrollPersonalProfile;
    Typeface typeface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.fragment_personal_profile, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        // TODO : Initialization UI Component's
        llUserName = convertView.findViewById(R.id.llUserName);
        txtName = convertView.findViewById(R.id.txtIBOName);
        llPhoneNo = convertView.findViewById(R.id.llPhoneNo);
        txtPhoneNo = convertView.findViewById(R.id.txtPhoneNo);
        llEmail = convertView.findViewById(R.id.llEmail);
        txtEmailId = convertView.findViewById(R.id.txtEmailId);
        txtDateOfJoining = convertView.findViewById(R.id.txtDateOfJoining);
        txtGender = convertView.findViewById(R.id.txtGender);
        txtAddress = convertView.findViewById(R.id.txtAddress);
        llChangePwd = convertView.findViewById(R.id.llChangePwd);
        // scrollPersonalProfile = convertView.findViewById(R.id.scroll_personal_profile);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");

        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);

        llChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : Action Change Password here...
                ChangePasswordDialog();
            }
        });

        //TODO : Setup & Binding Personal Details
        if (MyAccountFragment.mMyAccountInfo != null) {
            if (MyAccountFragment.mMyAccountInfo.getBasicInfo().getIBOUser() != null && MyAccountFragment.mMyAccountInfo.getBasicInfo().getIBOUser().length() > 0) {
                llUserName.setVisibility(View.VISIBLE);
                txtName.setText(MyAccountFragment.mMyAccountInfo.getBasicInfo().getIBOUser());
            } else
                llUserName.setVisibility(View.GONE);

            if (MyAccountFragment.mMyAccountInfo.getBasicInfo().getMobile() != null && MyAccountFragment.mMyAccountInfo.getBasicInfo().getMobile().length() > 0) {
                llPhoneNo.setVisibility(View.VISIBLE);
                txtPhoneNo.setText(MyAccountFragment.mMyAccountInfo.getBasicInfo().getMobile());
            } else
                llPhoneNo.setVisibility(View.GONE);

            if (MyAccountFragment.mMyAccountInfo.getBasicInfo().getEmail() != null && MyAccountFragment.mMyAccountInfo.getBasicInfo().getEmail().length() > 0) {
                llEmail.setVisibility(View.VISIBLE);
                txtEmailId.setText(MyAccountFragment.mMyAccountInfo.getBasicInfo().getEmail());
            } else
                llEmail.setVisibility(View.GONE);
            txtDateOfJoining.setText(SetGmtTime(MyAccountFragment.mMyAccountInfo.getBasicInfo().getDOJ()));
            txtGender.setText(MyAccountFragment.mMyAccountInfo.getBasicInfo().getSex());

            if (MyAccountFragment.mMyAccountInfo.getBasicInfo().getAddress() != "") {
                txtAddress.setText(MyAccountFragment.mMyAccountInfo.getBasicInfo().getAddress() + ", ");
            }
            if (MyAccountFragment.mMyAccountInfo.getBasicInfo().getCity() != "") {
                txtAddress.append(MyAccountFragment.mMyAccountInfo.getBasicInfo().getCity() + ", ");
            }
            if (MyAccountFragment.mMyAccountInfo.getBasicInfo().getState() != "") {
                txtAddress.append(MyAccountFragment.mMyAccountInfo.getBasicInfo().getState());
            }
        }
        return convertView;
    }

    private void ChangePasswordDialog() {
        final Dialog dialog = new Dialog(new ContextThemeWrapper(getActivity(), R.style.Dialog));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.change_password);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(layoutParams);

        MyTextView titlePassword = dialog.findViewById(R.id.change_password_title);
        final NebulaEditText txtOldPwd = dialog.findViewById(R.id.old_password);
        final NebulaEditText txtNewPwd = dialog.findViewById(R.id.new_password);
        final NebulaEditText txtConfirmPwd = dialog.findViewById(R.id.confirm_password);

        Button btnChangePwd = dialog.findViewById(R.id.change_password_button);
        Button btnCancel = dialog.findViewById(R.id.cancel_button);

        Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(), Config.FONT_STYLE);
        titlePassword.setTypeface(tf1);
        btnChangePwd.setTypeface(tf1);
        btnCancel.setTypeface(tf1);

        btnChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtOldPwd.getText().toString().trim().isEmpty())
                    AppUtils.displayErrorMessage(getActivity(), "Old password can't be blank.");
                else if (txtNewPwd.getText().toString().trim().isEmpty())
                    AppUtils.displayErrorMessage(getActivity(), "New password can't be blank.");
                else if (txtConfirmPwd.getText().toString().trim().isEmpty())
                    AppUtils.displayErrorMessage(getActivity(), "Confirm password can't be blank.");
                else if (!txtConfirmPwd.getText().toString().trim().equals(txtNewPwd.getText().toString().trim()))
                    AppUtils.displayErrorMessage(getActivity(), "Your password and confirmation password do not match");
                else
                    ChangePassword(txtOldPwd.getText().toString().trim(), txtNewPwd.getText().toString().trim(), dialog);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * This Method for change user current password.
     *
     * @param oldPwd
     * @param newPwd
     * @param mDialog
     */
    private void ChangePassword(String oldPwd, String newPwd, final Dialog mDialog) {
        if (AppUtils.isOnline(getActivity())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
          /*  mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            mProgressDialog.show();

            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            final Call<ChangePassword> wsChangePassword = mAPIInterface.ChangePassword(oldPwd, newPwd);
            wsChangePassword.enqueue(new Callback<ChangePassword>() {
                @Override
                public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS)
                        mDialog.dismiss();
                    if (response.code() == 200) {
                       // AppUtils.displayErrorMessage(getActivity(), response.body().getData());

                        SweetAlertDialog loading = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                        loading.setCancelable(true);
                        loading.setConfirmText("OK");
                        loading.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                                TextView textTitle = (TextView) alertDialog.findViewById(R.id.title_text);
                                TextView textContent = (TextView) alertDialog.findViewById(R.id.content_text);
                                Button btnConfirm = (Button) alertDialog.findViewById(R.id.confirm_button);
                                /*textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                btnConfirm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                textContent.setTypeface(typeface);
                                textTitle.setTypeface(typeface);
                                btnConfirm.setTypeface(typeface);*/
                                textTitle.setText("Success");
                                btnConfirm.setText("OK");
                                textContent.setText(response.body().getData());
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
                        mProgressDialog.dismiss();
                    } else
                        Toast.makeText(getActivity(), "Something went wrong!Please try again.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ChangePassword> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Log.e(getClass().getSimpleName(), "ERROR : " + t.getMessage());
                }
            });
        } else{
           // AppUtils.displayNetworkErrorMessage(getActivity());
    }
    }


}
