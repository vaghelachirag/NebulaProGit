package com.nebulacompanies.ibo.activities;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;


public class UploadDocument extends AppCompatActivity  implements View.OnClickListener{


    Session session;


    // Global Variable

    private int  REQUEST_CAPTURE = 0;

    // UI Components
    public static ImageView  imgCaptureIdProof, imgCaptureAddressProof;
    public static MyTextView txtIdProofLabel, txtAddressProofLabel;



    public static final int REQUEST_STATUS_CODE_ID_CAPTURE = 3;
    public static final int REQUEST_STATUS_CODE_ADDRESS_CAPTURE = 4;


    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_document);
        session = new Session(this);
        setActionBar();
        init();
    }

    void setActionBar() {
        TextView tv = new TextView(getApplicationContext());
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), Config.FONT_STYLE);
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText("Documents");
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nebula_new_dark)));
    }

    void init(){
        txtIdProofLabel = (MyTextView)findViewById(R.id.txtIdProofLabel);
        imgCaptureAddressProof = (ImageView)findViewById(R.id.imgCaptureAddressProof);
        imgCaptureAddressProof.setOnClickListener(this);
        txtAddressProofLabel = (MyTextView)findViewById(R.id.txtAddressProofLabel);
        imgCaptureIdProof = (ImageView)findViewById(R.id.imgCaptureIdProof);
        imgCaptureIdProof.setOnClickListener(this);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgCaptureIdProof ) {
            //checkAndroidVersion();
            REQUEST_CAPTURE = REQUEST_STATUS_CODE_ID_CAPTURE;
            onSelectImageClick(view);
        }

        if (view.getId() == R.id.imgCaptureAddressProof ) {
            REQUEST_CAPTURE = REQUEST_STATUS_CODE_ADDRESS_CAPTURE;
            onSelectImageClick(view);
        }
    }
    public void onSelectImageClick(View view) {
        CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if (REQUEST_CAPTURE == REQUEST_STATUS_CODE_ID_CAPTURE) {
                    filePath = result.getUri();
                    File file = new File(String.valueOf(result.getUri()));
                    int index = file.getName().lastIndexOf("/");
                    Log.i("ImagePath:", "ImagePath:" + result.getUri());
                    txtIdProofLabel.setText(file.getName().substring(index + 1));
                }
                else if (REQUEST_CAPTURE == REQUEST_STATUS_CODE_ADDRESS_CAPTURE) {
                    filePath = result.getUri();
                    File file = new File(String.valueOf(result.getUri()));
                    int index = file.getName().lastIndexOf("/");
                    Log.i("ImagePath:", "ImagePath:" + result.getUri());
                    txtAddressProofLabel.setText(file.getName().substring(index + 1));
                }

            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
