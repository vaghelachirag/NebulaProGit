package com.nebulacompanies.ibo.activities;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.model.IDCardModel;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.Objects;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Config.FONT_STYLE;

public class IdcardActivity extends AppCompatActivity {
    Session session;
    MyButtonEcom btn;
    MyTextView txtid, txtdob, txtdoj, txtadd, txtRetry, txtTitle, txtSubTitle;
    ImageView imgUser, imgSign;
    CardView cardData;
    LinearLayout llEmpty;
    APIInterface mAPIInterface;
    MaterialProgressBar mProgressDialog;
    MyBoldTextViewEcom txtname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);

        //android O fix bug orientation
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        setActionBar();
        init();
        showDefault();
        callAPI();
    }

    private void callAPI() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            mProgressDialog.setVisibility(View.VISIBLE);
            Call<IDCardModel> wsCallingRegistation = mAPIInterface.getIDCard(session.getIboKeyId());
            wsCallingRegistation.enqueue(new Callback<IDCardModel>() {
                @Override
                public void onResponse(Call<IDCardModel> call, Response<IDCardModel> response) {
                    mProgressDialog.setVisibility(View.INVISIBLE);
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d("CartAPI", "CartAPI: " + response);
                            assert response.body() != null;
                            if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                setData(response.body().getData());
                            } else
                                noDataAvailable();
                        } else
                            noDataAvailable();
                    } else
                        noDataAvailable();
                }

                @Override
                public void onFailure(Call<IDCardModel> call, Throwable t) {
                    mProgressDialog.setVisibility(View.INVISIBLE);
                    noDataAvailable();
                }
            });
        } else {
            noInternetAvailable();
        }
    }

    private void setData(IDCardModel.Data data) {
        txtname.setText(data.getName());
        txtid.setText(data.getIboid());
        txtdob.setText(data.getDOBString());
        txtdoj.setText(data.getDOJString());
        txtadd.setText(data.getAddressWithCityState());
        Glide.with(IdcardActivity.this)
                .load(data.getPhoto())
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.error(R.drawable.imagenotfound)
                .into(imgUser);
        Glide.with(IdcardActivity.this)
                .load(data.getSignature())
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.error(R.drawable.imagenotfound)
                .into(imgSign);
        if (data.isKYCverified())
            showDefault();
        else
            noKYCAvailable();
    }

    private void noInternetAvailable() {
        cardData.setVisibility(View.GONE);
        llEmpty.setVisibility(View.VISIBLE);

        txtTitle.setText(getResources().getString(R.string.error_title));
        txtSubTitle.setText(getResources().getString(R.string.error_content));
        txtSubTitle.setVisibility(View.VISIBLE);
        txtRetry.setVisibility(View.VISIBLE);
    }
    private void noDataAvailable() {
        cardData.setVisibility(View.GONE);
        llEmpty.setVisibility(View.VISIBLE);

        txtTitle.setText(getResources().getString(R.string.error_no_records));
        txtSubTitle.setVisibility(View.GONE);
        txtRetry.setVisibility(View.VISIBLE);
    }
    private void noKYCAvailable() {
        cardData.setVisibility(View.GONE);
        llEmpty.setVisibility(View.VISIBLE);

        txtTitle.setText(getResources().getString(R.string.error_no_kyc));
        txtSubTitle.setVisibility(View.GONE);
        txtRetry.setVisibility(View.VISIBLE);
    }
    private void showDefault() {
        cardData.setVisibility(View.VISIBLE);
        llEmpty.setVisibility(View.GONE);
    }

    void setActionBar() {
        TextView tv = new TextView(getApplicationContext());
        Typeface tf1 = Typeface.createFromAsset(this.getAssets(), FONT_STYLE);
        // Create a LayoutParams for TextView
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText("ID Card"); // ActionBar title text
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(16);
        tv.setTypeface(tf1);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.nebula_new_dark)));
    }

    @SuppressLint("SetTextI18n")
    void init() {
        session = new Session(this);
        btn = findViewById(R.id.button);
        txtname = findViewById(R.id.text_uname);
        txtid = findViewById(R.id.text_id);
        txtdob = findViewById(R.id.text_dob);
        txtdoj = findViewById(R.id.text_join);
        txtadd = findViewById(R.id.text_address);
        imgUser = findViewById(R.id.image_user);
        imgSign = findViewById(R.id.text_sign);
        txtRetry = findViewById(R.id.txtRetry);
        cardData = findViewById(R.id.id_card);
        llEmpty = findViewById(R.id.llEmpty);
        txtTitle = findViewById(R.id.txtErrorTitle);
        txtSubTitle = findViewById(R.id.txtErrorSubTitle);

        mProgressDialog = findViewById(R.id.progresbar);

        mAPIInterface = APIClient.getClient(IdcardActivity.this).create(APIInterface.class);

        Utils utils = new Utils();

        btn.setOnClickListener(v -> {
            Bitmap map = ConvertToBitmap(cardData);
            Log.v("BitmapObject", map.toString());
          /*  int heightG = map.getHeight();
            int widthG = map.getWidth();*/
            utils.sendViewViaMail(map, IdcardActivity.this, "ID Card");
        });

        txtRetry.setOnClickListener(v -> callAPI());
    }

    protected Bitmap ConvertToBitmap(CardView layout) {
        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();
        return layout.getDrawingCache();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
