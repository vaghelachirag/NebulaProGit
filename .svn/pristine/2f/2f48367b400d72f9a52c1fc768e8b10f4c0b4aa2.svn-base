package com.nebulacompanies.ibo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.adapter.ScratchAdapter;
import com.nebulacompanies.ibo.adapter.ScratchRuleAdapter;
import com.nebulacompanies.ibo.adapter.TambolaAdapter;
import com.nebulacompanies.ibo.adapter.UnlockedCardAdapter;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Session;

public class TambolaActivity extends AppCompatActivity {
    Session session;
    Toolbar toolbar;
    ImageView imgBackArrow;
    MyTextViewEcom tvToolbarTitle;
    RecyclerView recycleritems, recyclerRule;
    int numberOfColumnsItems = 4;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);
        session = new Session(this);
        Utils.darkenStatusBar(this, R.color.colorNotification);
        initUI();
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar_search);
        imgBackArrow = findViewById(R.id.img_back);

        imgBackArrow.setOnClickListener(view -> {

            onBackPressed();
        });
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);

        tvToolbarTitle.setText("Tambola");

        recycleritems = findViewById(R.id.rvscratch);
        recyclerRule = findViewById(R.id.rvscratchrule);

        recycleritems.setLayoutManager(new GridLayoutManager(this, numberOfColumnsItems));
        recyclerRule.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recycleritems.setAdapter(new TambolaAdapter(this));
        recyclerRule.setAdapter(new ScratchRuleAdapter(this));
    }
}
