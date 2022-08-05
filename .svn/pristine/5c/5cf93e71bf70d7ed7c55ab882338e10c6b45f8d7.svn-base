package com.nebulacompanies.ibo.ecom.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.MyViewOrderActivity;
import com.nebulacompanies.ibo.ecom.OnItemClickTrack;
import com.nebulacompanies.ibo.ecom.model.CancelOrderModel;
import com.nebulacompanies.ibo.ecom.model.OrderListModel;
import com.nebulacompanies.ibo.ecom.model.OrderListModelEcom;
import com.nebulacompanies.ibo.ecom.model.Product;
import com.nebulacompanies.ibo.ecom.model.TrackListModel;
import com.nebulacompanies.ibo.ecom.model.TrackListModelEcom;
import com.nebulacompanies.ibo.ecom.model.unicommerce.OrderStatusModel;
import com.nebulacompanies.ibo.ecom.model.unicommerce.OrderStatusRequestModel;
import com.nebulacompanies.ibo.ecom.model.unicommerce.ShippingPackagesModel;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.OrderListCallback;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.sweetdialogue.SweetAlertDialogCart;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.view.MyButton;
import com.nebulacompanies.ibo.view.MyTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.activities.ProductListDwarka.TAG;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;

public class MyViewOrderAdapter extends RecyclerView.Adapter<MyViewOrderAdapter.MyViewHolder> {

    private Context context;
    // private RealmResults<Product> products;
    private ProductsAdapterListener listener;
    // private RealmResults<CartItem> cartItems;
    ArrayList<OrderListModel.OrderDetailsListModel> orderDetailsListModels = new ArrayList<>();
    ArrayList<ShippingPackagesModel> shippingPackagesModels = new ArrayList<>();
    private static int countItem = 0;
    int addvalue, productQuantity;
    private APIInterface mAPIInterface;
    String orderId, cancelReason, cancelRemarks, cancelquantity, totalCount;
    APIInterface mAPIInterfaceTrackOrder;
    ArrayList<OrderListModel> orderListModel = new ArrayList<>();
    Typeface typeface;
    private OrderListCallback mAdapterCallback;
    String orderStatusString, orderNumber, cityFacility, status, statusDate;
    SharedPreferences sharedPreferencesFacility;
    private ArrayList<TrackListModel> trackListModels = new ArrayList<>();
    String trackUrl;
    private OnItemClickTrack mCallback;
    Utils utils = new Utils();
    boolean isTrack;

    public MyViewOrderAdapter(MyViewOrderActivity context, ArrayList<OrderListModel.OrderDetailsListModel> orderDetailsListModels,
                              OrderListCallback callback, String orderNumber, String status,
                              String statusDate, OnItemClickTrack listenerTrack, boolean isTrack) {

        this.context = context;
        this.orderDetailsListModels = orderDetailsListModels;
        this.listener = listener;
        this.mAdapterCallback = callback;
        this.orderNumber = orderNumber;
        this.status = status;
        this.statusDate = statusDate;
        this.mCallback = listenerTrack;
        this.isTrack = isTrack;
        mAPIInterface = APIClient.getClient(context).create(APIInterface.class);

        sharedPreferencesFacility = context.getSharedPreferences(PrefUtils.prefFacility, 0);
        cityFacility = sharedPreferencesFacility.getString(PrefUtils.prefFacility, null);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        MyTextViewEcom name;

        @BindView(R.id.tv_offer_price_value)
        MyTextViewEcom tvPrice;

        /*@BindView(R.id.tv_order_quantity)
        MyTextView tvCount;*/

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.btn_remove_item)
        MyButtonEcom btnRemoveItem;

        @BindView(R.id.btn_add_item)
        MyButtonEcom btnAddItem;


        @BindView(R.id.btn_cancel)
        MyButtonEcom btnOrderCancel;

        @BindView(R.id.tv_cancel)
        MyTextViewEcom tvCancel;

        @BindView(R.id.tv_confirm_date)
        MyTextViewEcom tvConfirmDate;

        @BindView(R.id.tv_packed_date)
        MyTextViewEcom tvPackedDate;

        @BindView(R.id.tv_dispatched_date)
        MyTextViewEcom tvDispatchedDate;

        @BindView(R.id.btn_return)
        MyButtonEcom btnOrderReturn;

       /* @BindView(R.id.btn_track)
        MyButtonEcom btnOrderTrack;*/

        @BindView(R.id.img_confirmed_horizontal)
        ImageView imgConfirmed;

        @BindView(R.id.img_packed_horizontal)
        ImageView imgPacked;

        @BindView(R.id.img_dispacted_horizontal)
        ImageView imgDispacted;

        @BindView(R.id.img_delivered_horizontal)
        ImageView imgDelivered;

        @BindView(R.id.progresbar)
        MaterialProgressBar materialProgressBar;

        @BindView(R.id.card_view)
        CardView cardView;

        MyTextViewEcom tvCount;

        @BindView(R.id.ln_track_horizontal)
        LinearLayout lnTrackHorizontal;

        @BindView(R.id.card_view_track)
        CardView cardViewTrack;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/ember.ttf");
            tvCount = view.findViewById(R.id.tv_order_quantity);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_view_order_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //getOrderStatus(position);
        //getting the product of the specified position
        OrderListModel.OrderDetailsListModel orderDetailsListModel = orderDetailsListModels.get(position);
        //binding the data with the viewholder views

        Log.e("facilityCodeResstatus1", "facilityCode Res status1 " + orderStatusString);
        Log.e("status", "status " + status);
        Log.e("status", "status " + statusDate);

        holder.name.setText(orderDetailsListModel.getOrderDetailsProductName());
        holder.tvCount.setText(String.valueOf(orderDetailsListModel.getOrderDetailsQuantity()));
        holder.tvPrice.setText(String.valueOf(orderDetailsListModel.getOrderDetailsPrice()));

        // holder.tvCount.setText( String.valueOf( orderDetailsListModel.getOrderDetailsQuantity() ) );
       // holder.btnOrderTrack.setVisibility(isTrack ? View.GONE : View.VISIBLE);
        if (holder.tvCount.getText().equals("0")) {
            holder.cardViewTrack.setVisibility(View.GONE);
            holder.btnOrderCancel.setVisibility(View.GONE);
            holder.tvCancel.setVisibility(View.VISIBLE);

        } else {
            holder.tvCancel.setVisibility(View.GONE);
            holder.cardViewTrack.setVisibility(position==getItemCount()-1 ? View.VISIBLE : View.GONE);
            holder.btnOrderCancel.setVisibility(View.VISIBLE);
            holder.tvCount.setText(String.valueOf(orderDetailsListModel.getOrderDetailsQuantity()));
        }


        List<String> facilityCode = new ArrayList<>();
        /*for (int i = 0; i <cartModels.size() ; i++) {
            sku.add(cartModels.get(i).getCartMRPSKU());
        }*/

        // facilityCode.add("Ahd_001");
        facilityCode.add(cityFacility);
        Log.e("facilityCode Res", "facilityCode Res cityFacility: " + cityFacility);

        mAPIInterface = APIClient.getClientUniCommmerce(context).create(APIInterface.class);
        Log.e("facilityCode Res Start", "facilityCode Res Start: " + facilityCode);
        OrderStatusRequestModel inventoryRequestModel = new OrderStatusRequestModel();
        inventoryRequestModel.setFacilityCodes(facilityCode);
        //  inventoryRequestModel.setCode( "order_FdGzzET2vsVHwI" );
        //inventoryRequestModel.setCode( "order_FdHFVSLWNUXwkP" );

        inventoryRequestModel.setCode(orderNumber);

        //getTrackOrder("order_FcvDwuqbKsnloS");

        mAPIInterfaceTrackOrder = APIClient.getClient(context).create(APIInterface.class);

        Log.e("facilityCode Res Start2", "facilityCode Res Start2: " + orderNumber);
        Log.e("facilityCode Res Start2", "facilityCode Res Start2: " + facilityCode);
        Call<OrderStatusModel> wsCallingRegistation = mAPIInterface.getOrderStatus(inventoryRequestModel);
       /* wsCallingRegistation.enqueue( new Callback<OrderStatusModel>() {
            @Override
            public void onResponse(Call<OrderStatusModel> call, Response<OrderStatusModel> response) {
                Log.e( "facilityCode Res Start3", "facilityCode Res Start3: " + new Gson().toJson( response.body() ) );
                //shippingPackagesModels.clear();
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {

                        if (response.body().getData() != null) {

                            Log.e( "facilityCode Res Start4", "facilityCode Res Start4: " + response.body() );
                            Log.e( "facilityCode Res Start5", "facilityCode Res Start5: " + new Gson().toJson( response.body() ) );
                            Log.e( "facilityCode Res Start6", "facilityCode Res Start6 " + new Gson().toJson( response.body().getData().getOutOfStockProducts() ) );


                            Log.e( "position", "position " + position );

                            if (shippingPackagesModels.size() > 0) {
                                shippingPackagesModels.addAll( response.body().getData().getOutOfStockProducts() );
                                orderStatusString = shippingPackagesModels.get( position ).getStatusOrderNew();
                                Log.e( "facilityCode Res status", "facilityCode Res status " + orderStatusString );


                                Log.e( "facilityCodeResstatus2", "facilityCode Res status2 " + orderStatusString );
                                if (orderStatusString.equalsIgnoreCase( "Created" ) || orderStatusString.equalsIgnoreCase( "Processing" ) ||
                                        orderStatusString.equalsIgnoreCase( "To be Picked" ) || orderStatusString.equalsIgnoreCase( "Picked" )) {
                                    holder.imgConfirmed.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                                    holder.imgPacked.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );
                                    holder.imgDispacted.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );
                                    holder.imgDelivered.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );
                                } else if (orderStatusString.equalsIgnoreCase( "Packed" ) || orderStatusString.equalsIgnoreCase( "Ready to Ship" )) {
                                    holder.imgPacked.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                                    holder.imgConfirmed.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                                    holder.imgDispacted.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );
                                    holder.imgDelivered.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );

                                } else if (orderStatusString.equalsIgnoreCase( "Dispatched" ) || orderStatusString.equalsIgnoreCase( "Shipped" )) {
                                    holder.imgDispacted.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                                    holder.imgConfirmed.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                                    holder.imgPacked.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                                    holder.imgDelivered.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );

                                } else if (orderStatusString.equalsIgnoreCase( "Delivered" )) {
                                    holder.imgDelivered.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                                    holder.imgDispacted.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                                    holder.imgConfirmed.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                                    holder.imgPacked.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                                } else {
                                    holder.imgDelivered.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );
                                    holder.imgDispacted.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );
                                    holder.imgConfirmed.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                                    holder.imgPacked.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );
                                }


                            } else {

                                holder.imgDelivered.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );
                                holder.imgDispacted.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );
                                holder.imgConfirmed.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                                holder.imgPacked.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );

                                //Toast.makeText( context,"Something went wrong", Toast.LENGTH_SHORT ).show();
                                // Toast.makeText( context,response.body().getMessage(), Toast.LENGTH_SHORT ).show();
                            }

                        }
                    } else if (!response.body().isSuccessful()) {
                        // Toast.makeText( context,"successful: false", Toast.LENGTH_SHORT ).show();

                        holder.imgDelivered.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );
                        holder.imgDispacted.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );
                        holder.imgConfirmed.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                        holder.imgPacked.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_inactive ) );


                        Log.e( "facilityCode Res Start7", "facilityCode Res Start8: " + new Gson().toJson( response.body() ) );
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderStatusModel> call, Throwable t) {
                Log.e( "facilityCode Res Fail", "facilityCode Res Fail " + t );
            }
        } );*/

        mCallback.onClick(status);

        if (!status.equals("")) {

            if (status.equalsIgnoreCase("Processing")) {
                holder.imgConfirmed.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_active));
                holder.imgPacked.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_inactive));
                holder.imgDispacted.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_inactive));
                holder.imgDelivered.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_inactive));
                if (!statusDate.equals("")) {
                    holder.tvConfirmDate.setText("(" + statusDate + ")");
                } else {
                    holder.tvConfirmDate.setVisibility(View.GONE);
                }

                holder.tvPackedDate.setVisibility(View.GONE);
                holder.tvDispatchedDate.setVisibility(View.GONE);
            } else if (status.equalsIgnoreCase("Dispatched")) {
                //holder.imgDispacted.setBackground( ContextCompat.getDrawable( context, R.drawable.ic_marker_active ) );
                holder.imgConfirmed.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_active));
                holder.imgPacked.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_active));
                holder.imgDelivered.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_inactive));
                //holder.tvConfirmDate.setText("("+statusDate+")");
                if (!statusDate.equals("")) {
                    holder.tvPackedDate.setText("(" + statusDate + ")");
                } else {
                    holder.tvPackedDate.setVisibility(View.GONE);
                }

                holder.tvConfirmDate.setVisibility(View.GONE);
                holder.tvDispatchedDate.setVisibility(View.GONE);
            } else if (status.equalsIgnoreCase("Delivered")) {
                holder.imgDelivered.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_active));
                holder.imgDispacted.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_active));
                holder.imgConfirmed.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_active));
                holder.imgPacked.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_active));
                holder.tvConfirmDate.setVisibility(View.GONE);
                holder.tvPackedDate.setVisibility(View.GONE);
                holder.tvDispatchedDate.setVisibility(View.VISIBLE);

                if (!statusDate.equals("")) {
                    holder.tvDispatchedDate.setText("(" + statusDate + ")");
                } else {
                    holder.tvDispatchedDate.setVisibility(View.GONE);
                }

            } else {
                holder.imgDelivered.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_inactive));
                holder.imgDispacted.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_inactive));
                holder.imgConfirmed.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_active));
                holder.imgPacked.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_marker_inactive));
                holder.tvPackedDate.setVisibility(View.GONE);
                holder.tvDispatchedDate.setVisibility(View.GONE);
                holder.tvConfirmDate.setVisibility(View.GONE);
            }
        }
        Glide.with(context)
                .load(orderDetailsListModel.getOrderDetailsImageURL()).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(getRandomDrawbleColor())
                .into(holder.thumbnail);


       /* if (orderDetailsListModel.getOrderDetailsBillingStatus().equals("Delivered")){
            holder.btnOrderReturn.setVisibility( View.VISIBLE );
            holder.btnOrderCancel.setVisibility( View.GONE );
        }else {
            holder.btnOrderReturn.setVisibility( View.GONE );
            holder.btnOrderCancel.setVisibility( View.VISIBLE );
        }*/

        //isCancellable() == true
        if (orderDetailsListModel.isCancellable() == true) {
            holder.btnOrderCancel.setVisibility(View.VISIBLE);
        } else {
            holder.btnOrderCancel.setVisibility(View.INVISIBLE);
        }

        holder.btnAddItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int addvalue = 0;
                addvalue = Integer.parseInt(holder.tvCount.getText().toString());
                Log.d("addval", "addval " + addvalue);
                addvalue++;
                holder.tvCount.setText(Integer.toString(addvalue));

            }
        });

        holder.btnRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int curval = 0;
                curval = Integer.parseInt(holder.tvCount.getText().toString());
                Log.d("curval", "curval " + curval);
                if (curval > 1) {
                    curval--;
                    holder.tvCount.setText(Integer.toString(curval));
                }
            }
        });

/*

        holder.btnOrderTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable(context)) {
                    holder.materialProgressBar.setVisibility(View.VISIBLE);
                    Call<TrackListModelEcom> wsCallingEvents = mAPIInterfaceTrackOrder.getTrackEcom(orderNumber);
                    wsCallingEvents.enqueue(new Callback<TrackListModelEcom>() {
                        @Override
                        public void onResponse(Call<TrackListModelEcom> call, Response<TrackListModelEcom> response) {
                            if (holder.materialProgressBar != null) {
                                holder.materialProgressBar.setVisibility(View.VISIBLE);
                            }
                            if (response.isSuccessful()) {
                                trackListModels.clear();
                                // if (response.body().getData().size() > 0) {
                                if (response.code() == 200) {
                                    if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                        trackUrl = response.body().getData().getTrackingData().getTrackUrl();

                                        if (holder.materialProgressBar != null) {
                                            holder.materialProgressBar.setVisibility(View.GONE);
                                        }
                                        if (!TextUtils.isEmpty(trackUrl)) {

                                            Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                                            myWebLink.setData(Uri.parse(trackUrl));
                                            context.startActivity(myWebLink);
                                        } else {
                                            if (holder.materialProgressBar != null) {
                                                holder.materialProgressBar.setVisibility(View.GONE);
                                            }
                                            Toast.makeText(context, "Tracking not available", Toast.LENGTH_SHORT).show();
                                        }

                                    } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                            || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                    }
                                } else {
                                    if (holder.materialProgressBar != null) {
                                        holder.materialProgressBar.setVisibility(View.GONE);
                                    }
                                }

                            } else {
                                if (holder.materialProgressBar != null) {
                                    holder.materialProgressBar.setVisibility(View.GONE);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<TrackListModelEcom> call, Throwable t) {
                            Log.d("TrackOrderERROR", "TrackOrderERROR: " + t);

                            if (holder.materialProgressBar != null) {
                                holder.materialProgressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                } else {
                    utils.dialogueNoInternet((Activity) context);
                }


            }
        });
*/


        holder.btnOrderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.setContentView(R.layout.dialog_order_cancel);
                dialog.setCancelable(false);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

                // This is line that does all the magic
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);

                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
                RadioButton rbWrongProduct = (RadioButton) dialog.findViewById(R.id.rb_wrong_product);
                RadioButton rbMistakeProduct = (RadioButton) dialog.findViewById(R.id.rb_mistake_product);
                RadioButton rbTimeProduct = (RadioButton) dialog.findViewById(R.id.rb_time_product);
                RadioButton rbPlacedProduct = (RadioButton) dialog.findViewById(R.id.rb_placed_product);
                RadioButton rbOtherProduct = (RadioButton) dialog.findViewById(R.id.rb_other_product);
                MyButtonEcom btnRemoveItem = (MyButtonEcom) dialog.findViewById(R.id.btn_remove_item);
                MyButtonEcom btnAddItem = (MyButtonEcom) dialog.findViewById(R.id.btn_add_item);
                MyButtonEcom btnCancelItem = (MyButtonEcom) dialog.findViewById(R.id.btn_cancel);
                MyButtonEcom btnOkayItem = (MyButtonEcom) dialog.findViewById(R.id.btn_okay);
                MyTextViewEcom txtProductCount = (MyTextViewEcom) dialog.findViewById(R.id.product_count);
                NebulaEditTextEcom edtRemarks = (NebulaEditTextEcom) dialog.findViewById(R.id.edt_remarks);

                btnAddItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        productQuantity = Integer.parseInt(holder.tvCount.getText().toString());
                        addvalue = Integer.parseInt(txtProductCount.getText().toString());
                        Log.d("addval", "addval " + addvalue);
                        int maxQuantity = productQuantity;
                        if (addvalue < maxQuantity) {
                            addvalue++;
                            txtProductCount.setText(Integer.toString(addvalue));
                            Log.d("Order Cancel", "Order Cancel Add" + txtProductCount.getText().toString());
                            //Log.d(TAG, "product Id and quantity: " + myCart.getProductId() + "quantity " + addvalue);
                            //addToCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), addvalue);
                            totalCount = Integer.toString(addvalue);
                            Log.d(TAG, "totalCount: " + totalCount);
                        } else {
                            Toast.makeText(context, "Sorry you have reached max quantity.", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


                btnRemoveItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int curval = Integer.parseInt(txtProductCount.getText().toString());
                        Log.d("curval", "curval " + curval);
                        if (curval > 1) {
                            curval--;
                            txtProductCount.setText(Integer.toString(curval));
                            Log.d("Order Cancel", "Order Cancel Remove" + txtProductCount.getText().toString());
                        }
                    }
                });

                /*Typeface typeface = context.getResources().getFont( R.font.ember );
                rbWrongProduct.setTypeface( typeface );
                rbMistakeProduct.setTypeface( typeface );
                rbTimeProduct.setTypeface( typeface );
                rbPlacedProduct.setTypeface( typeface );
                rbOtherProduct.setTypeface( typeface );*/


                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(checkedId);
                        int checkedIndex = radioGroup.indexOfChild(checkedRadioButton);
                        switch (checkedIndex) {
                            case 0:
                                // dialog.dismiss();

                                cancelReason = (String) rbWrongProduct.getText();
                                return;
                            case 1:
                                // dialog.dismiss();
                                /*Toast.makeText(context,
                                        rbMistakeProduct.getText(), Toast.LENGTH_SHORT).show();*/
                                cancelReason = (String) rbMistakeProduct.getText();
                                return;
                            case 2:
                                // dialog.dismiss();
                                /*Toast.makeText(context,
                                        rbTimeProduct.getText(), Toast.LENGTH_SHORT).show();*/
                                cancelReason = (String) rbPlacedProduct.getText();
                                return;
                            case 3:
                                // dialog.dismiss();
                               /* Toast.makeText(context,
                                        rbPlacedProduct.getText(), Toast.LENGTH_SHORT).show();*/

                                cancelReason = (String) rbTimeProduct.getText();
                                return;

                            case 4:
                                //  dialog.dismiss();
                              /*  Toast.makeText(context,
                                        rbOtherProduct.getText(), Toast.LENGTH_SHORT).show();*/

                                cancelReason = (String) rbOtherProduct.getText();
                                return;
                        }
                    }
                });

                orderId = String.valueOf(orderDetailsListModel.getOrderDetailsDetailsId());
                cancelReason = cancelReason;
                cancelRemarks = "" + edtRemarks.getText().toString();
                // cancelquantity = totalCount;
                //  cancelquantity = txtProductCount.getText(Integer.toString(addvalue)).toString();;
//                cancelquantity = Integer.toString(addvalue);
                cancelquantity = txtProductCount.getText().toString();

              /*  if (cancelquantity.equals("0")){
                    Toast.makeText(context,"0",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"1>",Toast.LENGTH_SHORT).show();
                }*/

                btnCancelItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                btnOkayItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("Order Cancel ", "Order Cancel " + orderId);
                        Log.d("Order Cancel ", "Order Cancel " + cancelReason);
                        Log.d("Order Cancel ", "Order Cancel " + edtRemarks.getText().toString());
                        Log.d("Order Cancel ", "Order Cancel " + txtProductCount.getText().toString());
                        cancelOrder(orderId, cancelReason, cancelRemarks, cancelquantity);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        holder.btnOrderReturn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_order_returnl);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

                // This is line that does all the magic
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);

                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
                RadioButton rbDemageReturn = (RadioButton) dialog.findViewById(R.id.rb_demage_product);
                RadioButton rbSatisfiedReturn = (RadioButton) dialog.findViewById(R.id.rb_satisfied_product);
                RadioButton rbOtherReturn = (RadioButton) dialog.findViewById(R.id.rb_other_return);

                MyButtonEcom btnRemoveItem = (MyButtonEcom) dialog.findViewById(R.id.btn_return_remove_item);
                MyButtonEcom btnAddItem = (MyButtonEcom) dialog.findViewById(R.id.btn_return_add_item);
                MyButtonEcom btnCancelItem = (MyButtonEcom) dialog.findViewById(R.id.btn_return_cancel);
                MyButtonEcom btnOkayItem = (MyButtonEcom) dialog.findViewById(R.id.btn_return_okay);
                MyTextViewEcom txtProductCount = (MyTextViewEcom) dialog.findViewById(R.id.product_return_count);


               /* Typeface typeface = context.getResources().getFont( R.font.ember );
                rbDemageReturn.setTypeface( typeface );
                rbSatisfiedReturn.setTypeface( typeface );
                rbOtherReturn.setTypeface( typeface );*/


                btnAddItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        productQuantity = Integer.parseInt(holder.tvCount.getText().toString());
                        addvalue = Integer.parseInt(txtProductCount.getText().toString());
                        Log.d("addval", "addval " + addvalue);
                        int maxQuantity = productQuantity;
                        if (addvalue < maxQuantity) {
                            addvalue++;
                            txtProductCount.setText(Integer.toString(addvalue));
                            //Log.d(TAG, "product Id and quantity: " + myCart.getProductId() + "quantity " + addvalue);
                            //addToCart(m_deviceId, session.getIboKeyId(), Integer.valueOf(myCart.getProductId()), addvalue);

                        } else {
                            Toast.makeText(context, "Sorry you have reached max quantity.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                btnRemoveItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int curval = Integer.parseInt(txtProductCount.getText().toString());
                        Log.d("curval", "curval " + curval);

                        if (curval > 1) {
                            curval--;
                            txtProductCount.setText(Integer.toString(curval));
                        }
                    }
                });


                if (rbDemageReturn.isChecked()) {
                    //dialog.dismiss();
                } else if (rbSatisfiedReturn.isChecked()) {
                    //dialog.dismiss();
                } else if (rbOtherReturn.isChecked()) {
                    //  dialog.dismiss();
                }


                btnCancelItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                btnOkayItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        //return products.size();
        return orderDetailsListModels.size();
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

    private void cancelOrder(String OrderDetailsId, String Reason, String Remark, String Quantity) {
        final ProgressDialog mProgressDialog = new ProgressDialog(context, R.style.MyTheme);
        mProgressDialog.show();

        mProgressDialog.setCancelable(false);
        mProgressDialog.setContentView(R.layout.progressdialog_ecom);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                    if (mProgressDialog.isShowing()) {
                        //your logic here for back button pressed event
                        mProgressDialog.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        Call<CancelOrderModel> wsCallingRegistation = mAPIInterface.cancelOrderCall(OrderDetailsId, Reason, Remark, Quantity);
        //Log.d("quantityOUT: ","quantityOUT: "+quantity);
        wsCallingRegistation.enqueue(new Callback<CancelOrderModel>() {
            @Override
            public void onResponse(Call<CancelOrderModel> call, Response<CancelOrderModel> response) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                        //   Log.d("quantityINBEfore: ","quantityINBEfore: "+quantity);
                        Log.e("Cancel Order Success", "Cancel Order Success" + new Gson().toJson(response.body()));


                        SweetAlertDialogCart loading = new SweetAlertDialogCart(context, SweetAlertDialogCart.SUCCESS_TYPE);
                        loading.setCancelable(true);
                        loading.setConfirmText("OK");
                        loading.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {
                                SweetAlertDialogCart alertDialog = (SweetAlertDialogCart) dialog;
                                MyTextView textTitle = (MyTextView) alertDialog.findViewById(R.id.title_text);
                                MyTextView textContent = (MyTextView) alertDialog.findViewById(R.id.content_text);
                                MyButton btnConfirm = (MyButton) alertDialog.findViewById(R.id.confirm_button);
                                textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                                textContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                textContent.setTypeface(typeface);
                                textTitle.setTypeface(typeface);
                                btnConfirm.setTypeface(typeface);
                                alertDialog.setCancelable(false);
                                //textTitle.setText(response.body().getMessage());
                                textTitle.setText("Success");
                                textContent.setText(response.body().getMessage());
                                btnConfirm.setText("OK");
                                // textContent.setText("Pin you have entered is Invalid.");
                                textContent.setGravity(Gravity.NO_GRAVITY);
                                btnConfirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loading.dismiss();
                                        mAdapterCallback.onMethodCallbackMain();
                                    }
                                });
                            }
                        });

                        loading.show();
                    }
                    //Log.d("quantityINAFTER: ","quantityINAFTER: "+quantity);

                }
            }

            @Override
            public void onFailure(Call<CancelOrderModel> call, Throwable t) {
                mProgressDialog.dismiss();

            }
        });
    }


    private void getOrderList() {
        if (Utils.isNetworkAvailable(context)) {

            mAPIInterfaceTrackOrder = APIClient.getClient(context).create(APIInterface.class);
            Call<OrderListModelEcom> wsCallingEvents = mAPIInterfaceTrackOrder.getOrderListEcom();
            wsCallingEvents.enqueue(new Callback<OrderListModelEcom>() {
                @Override
                public void onResponse(Call<OrderListModelEcom> call, Response<OrderListModelEcom> response) {

                    orderListModel.clear();
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_NO_RECORDS) {
                                // noRecordsFound();
                            } else if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                orderListModel.addAll(response.body().getData());

                                orderListModel.get(0).getOrderNumber();
                                Log.e("ORDERListAPI", "ORDERListAPI: " + new Gson().toJson(response.body()));
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                    || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                                // serviceUnavailable();
                            }

                        } else if (response.code() == 401) {
                            Log.d("AddressListGETINData401", "AddressListGETINData: " + new Gson().toJson(response.body()));


                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<OrderListModelEcom> call, Throwable t) {


                }
            });
        } else {
            // noInternetConnection();
        }
    }


    private void getOrderStatus(int position) {

        List<String> facilityCode = new ArrayList<>();
        /*for (int i = 0; i <cartModels.size() ; i++) {
            sku.add(cartModels.get(i).getCartMRPSKU());
        }*/

        facilityCode.add("Ahd_001");

        mAPIInterface = APIClient.getClientUniCommmerce(context).create(APIInterface.class);
        Log.e("facilityCode Res Start", "facilityCode Res Start: " + facilityCode);
        OrderStatusRequestModel inventoryRequestModel = new OrderStatusRequestModel();
        inventoryRequestModel.setFacilityCodes(facilityCode);
        inventoryRequestModel.setCode("order_FdGzzET2vsVHwI");

        Log.e("facilityCode Res Start2", "facilityCode Res Start2: " + facilityCode);
        Call<OrderStatusModel> wsCallingRegistation = mAPIInterface.getOrderStatus(inventoryRequestModel);
        wsCallingRegistation.enqueue(new Callback<OrderStatusModel>() {
            @Override
            public void onResponse(Call<OrderStatusModel> call, Response<OrderStatusModel> response) {
                Log.e("facilityCode Res Start3", "facilityCode Res Start3: " + new Gson().toJson(response.body()));

                if (response.isSuccessful()) {
                    if (response.body().isSuccessful()) {

                        if (response.body().getData() != null) {
                            Log.e("facilityCode Res Start4", "facilityCode Res Start4: " + response.body());
                            Log.e("facilityCode Res Start5", "facilityCode Res Start5: " + new Gson().toJson(response.body()));
                            Log.e("facilityCode Res Start6", "facilityCode Res Start6 " + new Gson().toJson(response.body().getData().getOutOfStockProducts()));


                            shippingPackagesModels.addAll(response.body().getData().getOutOfStockProducts());


                            orderStatusString = shippingPackagesModels.get(position).getStatusOrderNew();
                            Log.e("facilityCode Res status", "facilityCode Res status " + orderStatusString);

                        }
                    } else if (!response.body().isSuccessful()) {
                        Log.e("facilityCode Res Start7", "facilityCode Res Start8: " + new Gson().toJson(response.body()));
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderStatusModel> call, Throwable t) {
                Log.e("facilityCode Res Fail", "facilityCode Res Fail " + t);
            }
        });
    }


    private void getTrackOrder(String orderId) {
        mAPIInterfaceTrackOrder = APIClient.getClient(context).create(APIInterface.class);
        Call<TrackListModelEcom> wsCallingEvents = mAPIInterfaceTrackOrder.getTrackEcom(orderId);
        wsCallingEvents.enqueue(new Callback<TrackListModelEcom>() {
            @Override
            public void onResponse(Call<TrackListModelEcom> call, Response<TrackListModelEcom> response) {

                if (response.isSuccessful()) {
                    trackListModels.clear();
                    if (response.code() == 200) {
                        Log.d("TrackOrder", "TrackOrder: " + response);
                        if (response.body().getStatusCode() == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                            Log.d("TrackOrder", "TrackOrder: " + new Gson().toJson(response.body()));
                            //String firstName = trackListModels.get( 0 ).getTracking_data();

                            //String trackUrl = trackListModels.get( i ).getTracking_data().getTrackUrl();

                            // Log.e( "trackUrl","trackUrl: "+ trackUrl );

                            //joiningdate.setText(idDetails.get(0).getJoiningDate());
                        } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR
                                || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TrackListModelEcom> call, Throwable t) {
                Log.d("TrackOrderERROR", "TrackOrderERROR: " + t);
            }
        });
    }

}