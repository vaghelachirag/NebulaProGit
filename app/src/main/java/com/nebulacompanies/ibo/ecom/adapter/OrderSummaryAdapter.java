package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.MyCart;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;

import java.util.ArrayList;
import java.util.UUID;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.MyViewHolder> {

    private Context context;
    ArrayList<MyCart> myCartsModel = new ArrayList<>();
    private static int countItem = 0;
    private APIInterface mAPIInterface;
    String m_deviceId;
    String uniqueID;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_mycart_name)
        MyTextViewEcom tvMyCartName;



        MyTextViewEcom tvMyCartItemPrice;

        @BindView(R.id.product_count)
        MyTextViewEcom tvProductCount;

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.card_view)
        CardView cardView;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            tvMyCartItemPrice = (MyTextViewEcom) view.findViewById(R.id.tv_mycart_item_price);
        }
    }

    public OrderSummaryAdapter(Context context, ArrayList<MyCart> myCartsModel) {
        this.context = context;
        this.myCartsModel = myCartsModel;
        mAPIInterface = APIClient.getClient(context).create(APIInterface.class);
        /*TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        m_deviceId = TelephonyMgr.getDeviceId();*/

        m_deviceId = android.provider.Settings.Secure.getString(
                context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = context.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid,null);

        Log.d("MDEVICEID uniqueID", "MDEVICEID uniqueID: "+ uniqueID);
        if (m_deviceId == null || m_deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = context.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid,randomID);
                editor.apply();
                m_deviceId =  randomID;
            } else {
                m_deviceId = uniqueID;
            }
        }
        Log.d("MDEVICEID", "MDEVICEID: "+ m_deviceId);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_summary_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       MyCart  myCart = myCartsModel.get(position);

        holder.tvMyCartName.setText(myCart.getProductName());
        holder.tvMyCartItemPrice.setText(String.valueOf(myCart.getTotalPrice()));
        holder.tvProductCount.setText(String.valueOf(myCart.getCartQuantity()));


        Glide.with(context)
                .load(myCart.getImageURL()).fitCenter()
                .placeholder(R.drawable.ic_shopping_cart)
                .into(holder.thumbnail);

       holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(context, ProductDescriptionCart.class);
                context.startActivity(intent);*/
            }
        });
    }


    @Override
    public int getItemCount() {

        Log.d("myCartsModel.size()","myCartsModel.size():"+ myCartsModel.size());
        return myCartsModel.size();


    }
}
