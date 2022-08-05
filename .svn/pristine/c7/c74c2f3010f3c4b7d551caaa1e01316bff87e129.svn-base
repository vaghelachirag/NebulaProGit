package com.nebulacompanies.ibo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;

public class NoLoginActivity extends AppCompatActivity {

    MyButtonEcom btnLogin, btnNologin, btnRegister;
    ImageView imageBack;
    Utils utils;
    int myReqcode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_login);

        btnLogin = findViewById(R.id.btn_login);
        btnNologin = findViewById(R.id.btn_skip);
        btnRegister = findViewById(R.id.btn_register);
        imageBack = findViewById(R.id.img_back);
        utils = new Utils();
        myReqcode = getIntent().getExtras().getInt("req");
        imageBack.setOnClickListener(v -> goBack());
        btnNologin.setOnClickListener(v -> goBack());

        btnRegister.setOnClickListener(v -> {
            Intent registation = new Intent(NoLoginActivity.this, RegistrationActivityWebview.class);
            registation.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(registation);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });
        btnLogin.setOnClickListener(v -> {
            utils.openLoginDialog(NoLoginActivity.this, utils.gotoNologin);
        });
    }

    private void goBack() {
        if (utils.gotoMysales == myReqcode ||
                utils.gotoMypurchase == myReqcode ||
                utils.gotoMycommunity == myReqcode ||
                utils.gotoMyProfile == myReqcode ||
                utils.gotoIbosupport == myReqcode ||
                utils.gotoDownline == myReqcode ||
                utils.gotoPromos == myReqcode ||
                utils.gotoIncome == myReqcode ||
                utils.gotoMyBusiness == myReqcode) {
            Intent returnIntent = new Intent();
            // returnIntent.putExtra("result",result);
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        } else {
            Intent intent = new Intent(NoLoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Intent returnIntent = new Intent();
            // returnIntent.putExtra("result",result);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}