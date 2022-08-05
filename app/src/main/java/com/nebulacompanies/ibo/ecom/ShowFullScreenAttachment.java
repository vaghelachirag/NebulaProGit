package com.nebulacompanies.ibo.ecom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.ortiz.touchview.TouchImageView;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class ShowFullScreenAttachment extends AppCompatActivity {

    MaterialProgressBar mProgressDialog;
    TouchImageView touchImageView;
    Toolbar toolbar;
    MyTextViewEcom tvToolbarTitle;
    ImageView imgBackArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_screen_attachment);

        //attachment = getIntent();
        String file = getIntent().getStringExtra("data");
        Log.d("attch",""+file);

        mProgressDialog = findViewById(R.id.progresbar);
        mProgressDialog.setVisibility(View.VISIBLE);
        touchImageView = findViewById(R.id.pager_image);
        toolbar = findViewById(R.id.toolbar_search);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("Attachment");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imgBackArrow = findViewById(R.id.img_back);

        imgBackArrow.setOnClickListener(view -> onBackPressed());


        Glide.with(getApplicationContext()).load(file)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.nebula_effect)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mProgressDialog.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mProgressDialog.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(touchImageView);
    }
}

