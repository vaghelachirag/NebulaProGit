package com.nebulacompanies.ibo.ecom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.CartListModelRemoveEcom;
import com.nebulacompanies.ibo.ecom.model.CartListModelReviewEcom;
import com.nebulacompanies.ibo.ecom.model.MyCartReview;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;
import com.nebulacompanies.ibo.util.UtilsVersion;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class ProductAvaibilityActivity extends AppCompatActivity {

    APIInterface mAPIInterface, mAPIInterfaceCartReview;
    MaterialProgressBar mProgressDialog;
    Session session;
    Typeface typeface;

    Toolbar toolbar;
    ImageView imgBackArrow,imgError;
    MyTextViewEcom tvToolbarTitle,txtErrorContent, txtRetry;
    NestedScrollView nvProductAvailibilty;
    LinearLayout lnProductCheck;
    MyBoldTextViewEcom txtErrorTitle;
    MyButtonEcom btnReviewCart, btnConfirmOrder;

    String deviceId,uniqueID;
    int cityId;
    boolean mrpisAssociate;

    RecyclerView rvProductCheck;
    private MyProductAvaibilityAdapter myProductAvaibiltyAdapter;
    ArrayList<MyCartReview> cartModelsMyCartReviews = new ArrayList<>();
    UtilsVersion utilsVersion = new UtilsVersion();

    SharedPreferences citySave,spGetIsAssociate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_product_avaibilty );

        session = new Session( this );
        Utils.darkenStatusBar( this, R.color.colorNotification );
        typeface = Typeface.createFromAsset( getAssets(), "fonts/ember.ttf" );
        /*TelephonyManager TelephonyMgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();*/


        citySave = getSharedPreferences( PrefUtils.prefCityid, 0 );
        cityId = citySave.getInt( PrefUtils.prefCityid, 0 ); //0 is the default value

        deviceId = android.provider.Settings.Secure.getString(
                this.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID );

        SharedPreferences deviceSharedPreferences = this.getSharedPreferences( PrefUtils.prefDeviceid, 0 );
        uniqueID = deviceSharedPreferences.getString( PrefUtils.prefDeviceid, null );

        if (deviceId == null || deviceId.equals( "" )) {

            if (uniqueID == null || uniqueID.equals( "" )) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = this.getSharedPreferences( PrefUtils.prefDeviceid, Context.MODE_PRIVATE );
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString( PrefUtils.prefDeviceid, randomID );
                editor.apply();
                deviceId = randomID;
            } else {
                deviceId = uniqueID;
            }
        }

        //checkVersion();

        utilsVersion.checkVersionFirsTime(this);

        spGetIsAssociate = this.getSharedPreferences( PrefUtils.prefMrp, 0 );
        mrpisAssociate = spGetIsAssociate.getBoolean( PrefUtils.prefMrp, false );

        Log.d( "MDEVICEID cart uniqueID", "MDEVICEIDcart uniqueID: " + uniqueID );
        if (deviceId == null || deviceId.equals( "" )) {

            if (uniqueID == null || uniqueID.equals( "" )) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = this.getSharedPreferences( PrefUtils.prefDeviceid, Context.MODE_PRIVATE );
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString( PrefUtils.prefDeviceid, randomID );
                editor.apply();
                deviceId = randomID;
            } else {
                deviceId = uniqueID;
            }
        }
        imgError =  findViewById( R.id.imgError );
        txtErrorTitle =  findViewById( R.id.txtErrorTitle );
        txtErrorContent =  findViewById( R.id.txt_error_content );
        txtRetry =  findViewById( R.id.txtRetry );

        nvProductAvailibilty =  findViewById( R.id.nv_product_availibilty );
        lnProductCheck =  findViewById( R.id.ln_product_check );
        mAPIInterface = APIClient.getClient( ProductAvaibilityActivity.this ).create( APIInterface.class );
        //getting the toolbar
        mProgressDialog =  findViewById( R.id.progresbar );
        toolbar =  findViewById( R.id.toolbar_search );
        imgBackArrow =  findViewById( R.id.img_back );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayShowTitleEnabled( false );
        rvProductCheck = findViewById( (R.id.rv_myProductCheck) );
        rvProductCheck.setNestedScrollingEnabled( false );
        tvToolbarTitle =  toolbar.findViewById( R.id.toolbar_title1 );
        tvToolbarTitle.setText("Availability Check" );

        rvProductCheck.setHasFixedSize( true );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( ProductAvaibilityActivity.this, LinearLayoutManager.VERTICAL, false );
        rvProductCheck.setLayoutManager( mLayoutManager );
        rvProductCheck.setItemAnimator( new DefaultItemAnimator() );

        imgBackArrow.setOnClickListener(view -> {
            onBackPressed();
        });

        btnReviewCart =  findViewById( R.id.btn_review_cart );
        btnConfirmOrder =  findViewById( R.id.btn_confirm_order );

        btnReviewCart.setOnClickListener(v -> {
            Intent intent = new Intent( ProductAvaibilityActivity.this, MyCartActivity.class );
            startActivity( intent );
        });

        btnConfirmOrder.setOnClickListener(v -> {
            getMyCartListRemove( String.valueOf( cityId ) );
        });
        getMyCartListReview( String.valueOf( cityId ) );
    }

    private void getMyCartListReview(String cityID) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility( View.VISIBLE );
            mAPIInterfaceCartReview = APIClient.getClient(ProductAvaibilityActivity.this).create(APIInterface.class);

            Call<CartListModelReviewEcom> wsCallingEvents = mAPIInterfaceCartReview.getMyCartListReviewEcom( cityID );
            wsCallingEvents.enqueue( new Callback<CartListModelReviewEcom>() {
                @Override
                public void onResponse(Call<CartListModelReviewEcom> call, Response<CartListModelReviewEcom> response) {
                    Log.d( "CartAPIReviewAvail111", "CartAPIReviewAvail111: " + response.body() );

                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility( View.GONE );
                    }
                    cartModelsMyCartReviews.clear();

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d( "CartAPIReviewAvail1", "CartAPIReviewAvail1: " + response );

                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                //noRecordsFound();
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                nvProductAvailibilty.setVisibility( View.VISIBLE );
                                lnProductCheck.setVisibility( View.VISIBLE );
                                cartModelsMyCartReviews.addAll( response.body().getData().getOutOfStockProducts() );
                                Log.d( "CartAPIReviewAvail2", "CartAPIReviewAvail2: " + response.body().getData().getCartCount() );
                                final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( ProductAvaibilityActivity.this );
                                rvProductCheck.setLayoutManager( mLayoutManager );
                                rvProductCheck.setItemAnimator( new DefaultItemAnimator() );
                                myProductAvaibiltyAdapter = new MyProductAvaibilityAdapter( ProductAvaibilityActivity.this, cartModelsMyCartReviews );
                                // myCartProductsAdapter = new MyCartProductsAdapter(MyCartActivity.this,cartModels);
                                rvProductCheck.setAdapter( myProductAvaibiltyAdapter );

                                Log.e( "CartAPIReviewAvail3", "CartAPIReviewAvail3: " + new Gson().toJson( response.body() ) );

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }
                            if (cartModelsMyCartReviews.size() > 0) {
                                // llEmpty.setVisibility(View.GONE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelReviewEcom> call, Throwable t) {
                    mProgressDialog.setVisibility( View.GONE );
                    Log.d( "CartAPI", "CartAPI: product" + t );
                    Log.d( "CartResponse", "CartResponseFil: " + t );
                    Log.d( "CartAPIReviewAvail4", "CartAPIReviewAvail4: " + cartModelsMyCartReviews.size() );
                }
            } );
        } else {
            // noInternetConnection();
        }
    }


    public class MyProductAvaibilityAdapter extends RecyclerView.Adapter<MyProductAvaibilityAdapter.MyViewHolder> {

        private final Context context;
        //private MyCartProductsAdapter.ProductsAdapterListener listener;
        ArrayList<MyCartReview> myCartsModel = new ArrayList<>();

        String m_deviceId;
        String uniqueID;

        Session session;
        boolean mrpisAssociate, mrpisJoin;
        SharedPreferences spGetIsAssociate,  spGetIsJoin;


        //MyCart myCart;
        public class MyViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.product_quantity)
            MyTextViewEcom productQuantity;

            @BindView(R.id.name)
            MyTextViewEcom tvProductName;

            @BindView(R.id.thumbnail)
            ImageView thumbnail;

            @BindView(R.id.tv_original_price_value)
            MyTextViewEcom tvMRPPrice;


            public MyViewHolder(View view) {
                super( view );
                ButterKnife.bind( this, view );
            }
        }


        @SuppressLint("HardwareIds")
        public MyProductAvaibilityAdapter(Context context, ArrayList<MyCartReview> myCartsModel) {
            this.context = context;
            this.myCartsModel = myCartsModel;
            mAPIInterface = APIClient.getClient( context ).create( APIInterface.class );

            m_deviceId = android.provider.Settings.Secure.getString(
                    context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID );
            session = new Session( context );
            SharedPreferences deviceSharedPreferences = context.getSharedPreferences( PrefUtils.prefDeviceid, 0 );
            uniqueID = deviceSharedPreferences.getString( PrefUtils.prefDeviceid, null );

            Log.d( "MDEVICEID uniqueIDADP", "MDEVICEID uniqueIDADP: " + uniqueID );
            if (m_deviceId == null || m_deviceId.equals( "" )) {

                if (uniqueID == null || uniqueID.equals( "" )) {
                    String randomID = UUID.randomUUID().toString();

                    SharedPreferences preferences = context.getSharedPreferences( PrefUtils.prefDeviceid, Context.MODE_PRIVATE );
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString( PrefUtils.prefDeviceid, randomID );
                    editor.apply();
                    m_deviceId = randomID;
                } else {
                    m_deviceId = uniqueID;
                }
            }
            Log.d( "MDEVICEIDMYCARTADAP", "MDEVICEIDMYCARTADAP: " + m_deviceId );
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from( parent.getContext() )
                    .inflate( R.layout.product_unavaibility_list_item, parent, false );
            return new MyViewHolder( itemView );
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            //getting the product of the specified position
            MyCartReview myCart = myCartsModel.get( position );
            //binding the data with the viewholder views

            holder.tvProductName.setText(""+ myCart.getProductName() );
            holder.productQuantity.setText(""+ myCart.getOutOfStockQty()+" items out of stock" );
            spGetIsAssociate = context.getSharedPreferences( PrefUtils.prefMrp, 0 );
            mrpisAssociate = spGetIsAssociate.getBoolean( PrefUtils.prefMrp, false );

            spGetIsJoin = context.getSharedPreferences( PrefUtils.prefJoindays, 0 );
            mrpisJoin = spGetIsJoin.getBoolean( PrefUtils.prefJoindays, false );

            Log.e( "Image","Image"+ myCart.getMainImage() );

            Glide.with( context )
                    .load( myCart.getMainImage()).fitCenter()
                    .diskCacheStrategy( DiskCacheStrategy.ALL)
                    .placeholder( getRandomDrawbleColor() )
                    .into(holder.thumbnail );
        }

        @Override
        public int getItemCount() {
            Log.d( "myCartsModel.size()", "myCartsModel.size():" + myCartsModel.size() );
            return myCartsModel.size();
        }
    }

    private final ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable( Color.parseColor( "#9ACCCD" ) ), new ColorDrawable( Color.parseColor( "#8FD8A0" ) ),
                    new ColorDrawable( Color.parseColor( "#CBD890" ) ), new ColorDrawable( Color.parseColor( "#DACC8F" ) ),
                    new ColorDrawable( Color.parseColor( "#D9A790" ) ), new ColorDrawable( Color.parseColor( "#D18FD9" ) ),
                    new ColorDrawable( Color.parseColor( "#FF6772" ) ), new ColorDrawable( Color.parseColor( "#DDFB5C" ) )
            };


    public ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt( vibrantLightColorList.length );
        return vibrantLightColorList[idx];
    }


    private void getMyCartListRemove(String cityID) {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mAPIInterfaceCartReview = APIClient.getClient(ProductAvaibilityActivity.this).create(APIInterface.class);
            mProgressDialog.setVisibility( View.VISIBLE );

            Call<CartListModelRemoveEcom> wsCallingEvents = mAPIInterfaceCartReview.getMyCartListRemoveEcom( cityID );
            wsCallingEvents.enqueue( new Callback<CartListModelRemoveEcom>() {
                @Override
                public void onResponse(Call<CartListModelRemoveEcom> call, Response<CartListModelRemoveEcom> response) {
                    Log.d( "CartAPIReview1", "CartAPIReview1: " + response.body() );

                    if (mProgressDialog != null) {
                        mProgressDialog.setVisibility( View.GONE );
                    }

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            Log.d( "CartAPIReview2", "CartAPIReview2: " + response );
                            Log.d( "CartResponse2", "CartResponse2: " + response.body() );

                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {

                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                Log.d( "CartAPIRemove", "CartAPIRemove: " + response.body().getStatusCode() );

                                if (response.body().getStatusCode()==1){
                                    Intent cartReview = new Intent( ProductAvaibilityActivity.this, MyCartActivity.class );
                                    cartReview.putExtra( "SELECTEDVALUECATEGORY", 10 );
                                    cartReview.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity( cartReview );
                                    overridePendingTransition( R.anim.push_left_in, R.anim.push_left_out );
                                }

                                android.util.Log.e( "CartAPIRemove Res", "CartAPIRemove Res: " + new Gson().toJson( response.body() ) );

                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<CartListModelRemoveEcom> call, Throwable t) {
                    mProgressDialog.setVisibility( View.GONE );
                    Log.d("CartAPIRemove","CartAPIRemove "+ t);
                }
            } );
        } else {
            // noInternetConnection();
        }
    }
}
