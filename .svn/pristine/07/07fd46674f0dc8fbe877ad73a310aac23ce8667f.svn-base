package com.nebulacompanies.ibo.ecom.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.MyCartActivity;
import com.nebulacompanies.ibo.ecom.model.AddToCartModel;
import com.nebulacompanies.ibo.ecom.model.DeleteCartModel;
import com.nebulacompanies.ibo.ecom.model.MyCart;
import com.nebulacompanies.ibo.ecom.model.Product;
import com.nebulacompanies.ibo.ecom.utils.CartAdapterCallback;
import com.nebulacompanies.ibo.ecom.utils.CartAdapterPayCallback;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.util.Session;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.nebulacompanies.ibo.activities.ProductListDwarka.TAG;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;

/*public class MyCartProductsAdapter extends RecyclerView.Adapter<MyCartProductsAdapter.MyViewHolder> {

    private Context context;
    private ProductsAdapterListener listener;
    ArrayList<MyCart> myCartsModel = new ArrayList<>();
    private static int countItem = 0;
    private APIInterface mAPIInterface;
    String m_deviceId;
    String uniqueID;
    int addvalue;
    int productQuantity, productMaxSaleQuantity;
    private CartAdapterCallback mAdapterCallback;
    private CartAdapterPayCallback cartAdapterPayCallback;
    Session session;
    double pvResultAdapter;



    //MyCart myCart;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_mycart_name)
        MyTextViewEcom tvMyCartName;


        MyTextViewEcom tvMyCartItemPrice;

        @BindView(R.id.product_count)
        MyTextViewEcom tvProductCount;

        @BindView(R.id.tv_mycart_tablet)
        MyTextViewEcom tvMyCartTablet;

        @BindView(R.id.thumbnail)
        ImageView thumbnail;


        @BindView(R.id.img_my_cart_delete)
        ImageView imgDelete;

        @BindView(R.id.btn_remove_item)
        MyButtonEcom btnRemoveItem;

        @BindView(R.id.btn_add_item)
        MyButtonEcom btnAddItem;


        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.tv_pv_value)
        MyTextViewEcom tvCartPV;

        @BindView(R.id.tv_nv_value)
        MyTextViewEcom tvCartNV;

        @BindView(R.id.tv_bv_value)
        MyTextViewEcom tvCartBV;

        @BindView(R.id.tv_original_price_value)
        MyTextViewEcom tvMRPPrice;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            tvMyCartItemPrice = (MyTextViewEcom) view.findViewById(R.id.tv_mycart_item_price);
        }
    }


    public MyCartProductsAdapter(Context context, ArrayList<MyCart> myCartsModel, CartAdapterCallback callback,
                                 CartAdapterPayCallback cartAdapterPayCallback) {
        // public MyCartProductsAdapter(Context context, ArrayList<MyCart> myCartsModel) {
        this.context = context;
        this.myCartsModel = myCartsModel;
        this.listener = listener;
        this.mAdapterCallback = callback;
        this.cartAdapterPayCallback = cartAdapterPayCallback;
        mAPIInterface = APIClient.getClient(context).create(APIInterface.class);
        *//*TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        m_deviceId = TelephonyMgr.getDeviceId();*//*

        m_deviceId = android.provider.Settings.Secure.getString(
                context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        session = new Session(context);
        SharedPreferences deviceSharedPreferences = context.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceidnull);

        Log.d("MDEVICEID uniqueIDADP", "MDEVICEID uniqueIDADP: "+ uniqueID);
        if (m_deviceId == null || m_deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = context.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceidrandomID);
                editor.apply();
                m_deviceId =  randomID;
            } else {
                m_deviceId = uniqueID;
            }
        }
        Log.d("MDEVICEIDMYCARTADAP", "MDEVICEIDMYCARTADAP: "+ m_deviceId);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_cart_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //getting the product of the specified position
        MyCart myCart = myCartsModel.get(position);
        //binding the data with the viewholder views

        holder.tvMyCartName.setText(myCart.getProductName());

        holder.tvProductCount.setText(String.valueOf(myCart.getCartQuantity()));
        holder.tvMyCartTablet.setText(String.valueOf(myCart.getVolwt()));


        holder.tvCartPV.setText( "" + myCart.getCategoryPV() );
        holder.tvCartBV.setText( "" + myCart.getCategoryBV());
        holder.tvCartNV.setText( "" + myCart.getCategoryNV());


        pvResultAdapter = Double.parseDouble( myCart.getCategoryPV() );

        if (pvResultAdapter>2500){
            holder.tvMyCartItemPrice.setText(String.valueOf(myCart.getPricePerPiece()));
            holder.tvMRPPrice.setText(String.valueOf(myCart.getCartMRPPrice()));
        }else {
            holder.tvMRPPrice.setVisibility( View.GONE );
            holder.tvMyCartItemPrice.setText( String.valueOf(myCart.getCartMRPPrice()) );
        }
//        double productPerPrice = myCart.getPricePerPiece();
//        Integer cartAddedQuantity = myCart.getCartQuantity();



        //holder.tvCount.setText("1");


        Glide.with(context)
                .load(myCart.getImageURL()).fitCenter()
                .placeholder(getRandomDrawbleColor())
                .into(holder.thumbnail);

        holder.btnAddItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               *//* int addvalue = 0;
                addvalue = Integer.parseInt(holder.tvProductCount.getText().toString());
                Log.d("addval", "addval "+addvalue);
                addvalue++;
                addToCart(m_deviceId,"", Integer.valueOf(myCart.getProductId()),addvalue);
                holder.tvProductCount.setText(Integer.toString(addvalue));*//*
//                addvalue = 1;
                productQuantity = Integer.parseInt(myCart.getStockQuantity());
                addvalue = Integer.parseInt(holder.tvProductCount.getText().toString());
                Log.d("addval", "addval " + addvalue);

//                int maxQuantity = 0;
                int maxQuantity = productQuantity;


                *//*if(productMaxSaleQuantity < productQuantity){
                    maxQuantity = productMaxSaleQuantity;
                } else {
                    maxQuantity = productQuantity;
                }*//*

                if (addvalue < maxQuantity) {
                    addvalue++;
                    holder.tvProductCount.setText(Integer.toString(addvalue));
                    Log.d(TAG, "product Id and quantity: " + myCart.getProductId() + "quantity " + addvalue);
                    //addToCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), addvalue);
                    addToCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), 1,"plus");

                } else {
                    Toast.makeText(context, "Sorry you have reached max quantity.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int curval = Integer.parseInt(holder.tvProductCount.getText().toString());
                Log.d("curval", "curval " + curval);
                if (curval > 1) {
                    curval--;
                    holder.tvProductCount.setText(Integer.toString(curval));
                   // addToCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), curval);
                    addToCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), 1,"minus");

                }
            }
        });


        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  deleteFromCart(m_deviceId,session.getIboKeyId() ,Integer.valueOf(myCart.getProductId()));
                deleteFromCart(m_deviceId,session.getIboKeyId() ,Integer.valueOf(myCart.getProductId()));

            }
        });


    }


    @Override
    public int getItemCount() {

        Log.d("myCartsModel.size()", "myCartsModel.size():" + myCartsModel.size());
        return myCartsModel.size();


    }


    public interface ProductsAdapterListener {
        void onProductAddedCart(int index, Product product);

        void onProductRemovedFromCart(int index, Product product);
    }

    private void thisWasClicked(int position) {

        if (position == 0) {
            countItem++;
        }

    }


    private void addToCart(String deviceId, String userId, Integer productId, Integer quantity,String CartFlag) {
        final ProgressDialog mProgressDialog = new ProgressDialog(context, R.style.MyTheme);
        mProgressDialog.show();

        mProgressDialog.setCancelable(false);
        mProgressDialog.setContentView(R.layout.progressdialog_ecom);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                    if(mProgressDialog.isShowing()) {
                        //your logic here for back button pressed event
                        mProgressDialog.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        Call<AddToCartModel> wsCallingRegistation = mAPIInterface.getAddToCartModelCall(deviceId, userId, productId, quantity,CartFlag);
       Log.d("quantityOUT: ","quantityOUT: "+quantity);
        wsCallingRegistation.enqueue(new Callback<AddToCartModel>() {
            @Override
            public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                        Log.d("quantityINBEfore: ","quantityINBEfore: "+quantity);
                        mAdapterCallback.onMethodCallbackMain();

                        Log.d("quantityINAFTER: ","quantityINAFTER: "+quantity);
                    }
                }
            }

            @Override
            public void onFailure(Call<AddToCartModel> call, Throwable t) {
                mProgressDialog.dismiss();
            }
        });
    }


    private void deleteFromCart(String deviceId, String userId,Integer productId) {
       *//* final ProgressDialog mProgressDialog = new ProgressDialog(context, R.style.MyTheme);
        mProgressDialog.show();

        mProgressDialog.setCancelable(false);
        mProgressDialog.setContentView(R.layout.progressdialog_ecom);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                    if(mProgressDialog.isShowing()) {
                        //your logic here for back button pressed event
                        mProgressDialog.dismiss();

                    }
                    return true;
                }
                return false;
            }
        });*//*
        Call<DeleteCartModel> wsCallingRegistation = mAPIInterface.getDeleteFromCartModelCall(deviceId,userId ,productId);
        wsCallingRegistation.enqueue(new Callback<DeleteCartModel>() {
            @Override
            public void onResponse(Call<DeleteCartModel> call, Response<DeleteCartModel> response) {
               *//* if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }*//*

                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                        //   Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Total Delete APISuccess", "Total Delete APISuccess: " + productId);
                        mAdapterCallback.onMethodCallbackMain();
                        Log.d("Total DeleteAPISuccess1", "Total DeleteAPISuccess1: " + productId);

                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteCartModel> call, Throwable t) {
               // mProgressDialog.dismiss();
                Log.d("Total Delete Failure", "Total Delete Failure: " + productId + "Error: " + t);
            }
        });
    }

    public void removeAt(int position) {
        myCartsModel.remove(position);
        notifyItemRemoved(position);
        //notifyItemRangeChanged(position, myCartsModel.size());
    }


    private ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#9ACCCD")), new ColorDrawable(Color.parseColor("#8FD8A0")),
                    new ColorDrawable(Color.parseColor("#CBD890")), new ColorDrawable(Color.parseColor("#DACC8F")),
                    new ColorDrawable(Color.parseColor("#D9A790")), new ColorDrawable(Color.parseColor("#D18FD9")),
                    new ColorDrawable(Color.parseColor("#FF6772")), new ColorDrawable(Color.parseColor("#DDFB5C"))
            };


    public ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }


}*/
