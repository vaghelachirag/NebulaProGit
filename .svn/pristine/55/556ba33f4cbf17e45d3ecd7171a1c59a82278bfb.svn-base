package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.MyViewOrderActivity;
import com.nebulacompanies.ibo.ecom.TrackOrderActivity;
import com.nebulacompanies.ibo.ecom.model.OrderListModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ProductViewHolder> {

    //this context we will use to inflate the layout
    private final Context mCtx;

    //we are storing all the products in a list
    private final ArrayList<OrderListModel> orderDetailsModels;

    //getting the context and product list with constructor

    public OrderListAdapter(Context mCtx, ArrayList<OrderListModel> orderDetailsModels) {
        this.mCtx = mCtx;
        this.orderDetailsModels = orderDetailsModels;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.orders_items, null);
        return new ProductViewHolder(view);
    }
    private long mLastClickTime = 0;
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        OrderListModel orderListModel = orderDetailsModels.get(position);
        //binding the data with the viewholder views
        holder.tvOrderTitle.setText(orderListModel.getOrderNumber());
        List<OrderListModel.OrderDetailsListModel> detaillist = orderListModel.getOrderDetails();
        StringBuilder itemname = new StringBuilder("[ ");
        for (int i = 0; i < detaillist.size(); i++) {
            String pName = detaillist.get(i).getOrderDetailsProductName();
            int pQnty = detaillist.get(i).getOrderDetailsQuantity();
            if (i > 0)
                itemname.append(", ");

            String pStr = pName + " (" + pQnty + ")";
            itemname.append(pStr);
        }
        itemname.append(" ]");
        //holder.txtDate.setText(SetGmtTime(eventListList.getEventDate()));
        //holder.tvOrderDesciption.setText(SetGmtTime(orderListModel.getOrderDate()));
        //holder.tvOrderDate.setText(SetPinGmtTime(orderListModel.getOrderDate()));

        /*long batch_date = orderListModel.getOrderDate();
        Date dt = new Date (batch_date * 1000L);
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MMMM-yyyy (hh:mm a)");
        System.out.println(sfd.format(dt));
        holder.tvOrderDate.setText(sfd.format(dt));*/

        holder.tvProductName.setText(itemname.toString());
        // long unixSeconds = 1372339860; // convert seconds to milliseconds
        long unixSeconds = orderListModel.getOrderDate(); // convert seconds to milliseconds
        Date date = new Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy (hh:mm a)");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-0"));
        String formattedDate = sdf.format(date);
        System.out.println(formattedDate);
        holder.tvOrderDate.setText(formattedDate);

        // holder.tvProductName.setText(orderDetailsModels1.get(0).getOrderDetailsProductName());
        // Log.d( "Time CHECK ","Time CHECK "+ sfd.format(dt) );
        Log.d("Time CHECK1 ", "Time CHECK1" + orderListModel.getOrderDate());

        if (orderListModel.isFullOrderDelivered()) {
            // holder.tvOrderStatusSuccess.setVisibility( View.VISIBLE );
            holder.tvOrderStatusSuccess.setVisibility(View.GONE);
        } else {
            holder.tvOrderStatusSuccess.setVisibility(View.GONE);
        }

       /* Glide.with(mCtx)
                .load(orderListModel.getImageURL()).fitCenter()
                .diskCacheStrategy( DiskCacheStrategy.ALL)
                .placeholder(getRandomDrawbleColor())
                .into(holder.imgTrack);*/

        holder.btnTrackOrder.setOnClickListener(view -> {
          /*  if (SystemClock.elapsedRealtime() - mLastClickTime < 5000){
                return;
            }*/
            Intent intent = new Intent(mCtx, TrackOrderActivity.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mCtx.startActivity(intent);
        });

        holder.btnViewOrder.setOnClickListener(view -> {
            // mis-clicking prevention, using threshold of 1000 ms
            if (SystemClock.elapsedRealtime() - mLastClickTime < 5000){
                return;
            }
            int wallet = 0;
            try {
                wallet = orderListModel.geteWalletAmount().intValue();
            }catch (Exception e){
                e.printStackTrace();
            }
            Intent intent = new Intent(mCtx, MyViewOrderActivity.class);
            intent.putExtra("orderNumber", orderListModel.getOrderNumber());
            intent.putExtra("orderTotalPrice", orderListModel.getOrderShippingGrandTotal());
            intent.putExtra("orderShippingPrice", orderListModel.getOrderShippingShippingCharges());
            intent.putExtra("orderSubtotalPrice", orderListModel.getOrderShippingSubTotal());
            intent.putExtra("orderBillingAddressUser", orderListModel.getOrderShippingBillingAddressUser());
            intent.putExtra("orderShippingAddressUser", orderListModel.getOrderShippingShippingAddressUser());
            intent.putExtra("mobileNo", orderListModel.getMobileNo());
            intent.putExtra("orderTransactionID", orderListModel.getOrderShippingTransactionId());
            intent.putExtra("orderShippingAddresses", orderListModel.getOrderShippingAddresses());
            intent.putExtra("orderBillingAddresses", orderListModel.getOrderBillingAddresses());
            intent.putExtra("isPickupPoint", orderListModel.getIsPickupPoint());
            intent.putExtra("status", orderListModel.getStatus());
            intent.putExtra("statusDate", orderListModel.getStatusUpdatedOn());
            intent.putExtra("ewallet", wallet);
            intent.putExtra("view_order_data", orderDetailsModels.get(holder.getAdapterPosition()).getOrderDetails());
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(intent);
        });
    }


   /* private ColorDrawable[] vibrantLightColorList =
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
*/

    @Override
    public int getItemCount() {
        return orderDetailsModels.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        MyTextViewEcom tvOrderDate, tvOrderStatusSuccess, tvProductName;
        MyBoldTextViewEcom tvOrderTitle;
        MyButtonEcom btnTrackOrder, btnViewOrder;
        CardView cvOrderItem;
        //ImageView imgTrack;

        public ProductViewHolder(View itemView) {
            super(itemView);

            cvOrderItem = itemView.findViewById(R.id.cv_order_item);
            tvOrderTitle = itemView.findViewById(R.id.tv_order_title);
            tvOrderStatusSuccess = itemView.findViewById(R.id.tv_order_status_success);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
            tvProductName = itemView.findViewById(R.id.tv_product_details);
           // imgTrack = itemView.findViewById(R.id.img_track);
            btnTrackOrder = itemView.findViewById(R.id.btn_track_order);
            btnViewOrder = itemView.findViewById(R.id.btn_view_order);

        }
    }
}