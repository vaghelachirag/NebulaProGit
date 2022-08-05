package com.nebulacompanies.ibo.fragments;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nebulacompanies.ibo.R;

//TODO : Bank Details Fragment
@SuppressLint("ValidFragment")
public class BankAccountFragment extends Fragment {
    //TODO : Class Components
    private View convertView;
    private LinearLayout llBank, llBranch, llBranchCity, llIFSCCode, llAccountHolder, llAccountNo;
    private TextView txtBank, txtBranch, txtBranchCity, txtIFSCCode, txtAccountHolder, txtAccountNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.fragment_bank_profile, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        // TODO : Initialization UI Component's
        llBank = convertView.findViewById(R.id.llBank);
        txtBank = convertView.findViewById(R.id.txtBank);
        llBranch = convertView.findViewById(R.id.llBranch);
        txtBranch = convertView.findViewById(R.id.txtBranch);
        llBranchCity = convertView.findViewById(R.id.llBranchCity);
        txtBranchCity = convertView.findViewById(R.id.txtBranchCity);
        llIFSCCode = convertView.findViewById(R.id.llIFSCCode);
        txtIFSCCode = convertView.findViewById(R.id.txtIFSCCode);
        llAccountHolder = convertView.findViewById(R.id.llAccountHolder);
        txtAccountHolder = convertView.findViewById(R.id.txtAccountHolder);
        llAccountNo = convertView.findViewById(R.id.llAccountNo);
        txtAccountNo = convertView.findViewById(R.id.txtAccountNo);
        //TODO : Setup & Binding Bank Details
        if (MyAccountFragment.mMyAccountInfo.getBankInfo().getBank() != null &&
                MyAccountFragment.mMyAccountInfo.getBankInfo().getBank().length() > 0) {
            llBank.setVisibility(View.VISIBLE);
            // txtBank.setText(MyAccountFragment.mMyAccountInfo.getBankInfo().getBank());
            if (MyAccountFragment.mMyAccountInfo.getBankInfo().getBank().contains("Select")) {
                txtBank.setText("");
            } else {
                txtBank.setText(MyAccountFragment.mMyAccountInfo.getBankInfo().getBank());
            }
        } else {
            llBank.setVisibility(View.GONE);
        }
        if (MyAccountFragment.mMyAccountInfo.getBankInfo().getBranch() != null && MyAccountFragment.mMyAccountInfo.getBankInfo().getBranch().length() > 0) {
            llBranch.setVisibility(View.VISIBLE);
            txtBranch.setText(MyAccountFragment.mMyAccountInfo.getBankInfo().getBranch());
        } else
            llBranch.setVisibility(View.GONE);
        if (MyAccountFragment.mMyAccountInfo.getBankInfo().getBranchCity() != null && MyAccountFragment.mMyAccountInfo.getBankInfo().getBranchCity().length() > 0) {
            llBranchCity.setVisibility(View.VISIBLE);
            txtBranchCity.setText(MyAccountFragment.mMyAccountInfo.getBankInfo().getBranchCity());
        } else
            llBranchCity.setVisibility(View.GONE);
        if (MyAccountFragment.mMyAccountInfo.getBankInfo().getIFSCCode() != null && MyAccountFragment.mMyAccountInfo.getBankInfo().getIFSCCode().length() > 0) {
            llIFSCCode.setVisibility(View.VISIBLE);
            txtIFSCCode.setText(MyAccountFragment.mMyAccountInfo.getBankInfo().getIFSCCode());
        } else
            llIFSCCode.setVisibility(View.GONE);
        if (MyAccountFragment.mMyAccountInfo.getBankInfo().getAccountHolderName() != null && MyAccountFragment.mMyAccountInfo.getBankInfo().getAccountHolderName().length() > 0) {
            llAccountHolder.setVisibility(View.VISIBLE);
            txtAccountHolder.setText(MyAccountFragment.mMyAccountInfo.getBankInfo().getAccountHolderName());
        } else
            llAccountHolder.setVisibility(View.GONE);
        if (MyAccountFragment.mMyAccountInfo.getBankInfo().getAccountNo() != null && MyAccountFragment.mMyAccountInfo.getBankInfo().getAccountNo().length() > 0) {
            llAccountNo.setVisibility(View.VISIBLE);
            //Change Due to Technical Error on Custom Phone Date on : 22/11/2018
            txtAccountNo.setText(/*maskNumber*/(MyAccountFragment.mMyAccountInfo.getBankInfo().getAccountNo())); //, "#xxx-xxxx-xxxx-##"
            //txtAccountNo.setText(maskNumber(MyAccountFragment.mMyAccountInfo.getBankInfo().getAccountNo(), "#xxx-xxxx-xxxx-##"));
        } else
            llAccountNo.setVisibility(View.GONE);

        return convertView;
    }
}
