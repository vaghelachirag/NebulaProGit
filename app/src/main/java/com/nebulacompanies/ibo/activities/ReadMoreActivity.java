package com.nebulacompanies.ibo.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.EBCAdapter;
import com.nebulacompanies.ibo.ecom.model.BanersListEcom;
import com.nebulacompanies.ibo.ecom.model.ECBBannerDetailsModel;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Session;

import java.util.ArrayList;

public class ReadMoreActivity extends AppCompatActivity {

    Session session;

    Toolbar toolbar;
    ImageView imgBackArrow;
    MyTextViewEcom tvToolbarTitle;
    MyTextViewEcom productDesc;
    Intent Productdata;
    String  descData;
    RecyclerView rvEBC;
    public static ArrayList<ECBBannerDetailsModel> list = new ArrayList<>();
    EBCAdapter ebcAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_more);
        session = new Session(this);
        Utils.darkenStatusBar(this, R.color.colorNotification);


        toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        imgBackArrow = (ImageView) findViewById(R.id.img_back);

        tvToolbarTitle = (MyTextViewEcom) toolbar.findViewById(R.id.toolbar_title1);


        productDesc = findViewById(R.id.tv_product_desc);

        rvEBC = (RecyclerView) findViewById(R.id.rv_ebc);
        rvEBC.setNestedScrollingEnabled(false);


        Productdata = getIntent();
        if (Productdata != null) {
            descData = Productdata.getStringExtra("desc");
            String name = Productdata.getStringExtra("name");
            String listdata = Productdata.getStringExtra("list");
            tvToolbarTitle.setText(name);
            Gson gson = new Gson();
            list = gson.fromJson(listdata, new TypeToken<ArrayList<ECBBannerDetailsModel>>() {
            }.getType());
        }

        imgBackArrow.setOnClickListener(view -> {

            onBackPressed();
        });

        productDesc.setText(HtmlCompat.fromHtml(descData, 0));
        initEbcList();

    }

    private void initEbcList() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvEBC.setLayoutManager(mLayoutManager);
        rvEBC.setItemAnimator(new DefaultItemAnimator());
        ebcAdapter = new EBCAdapter(ReadMoreActivity.this, list);
        rvEBC.setAdapter(ebcAdapter);
    }


}
