package com.nebulacompanies.ibo.ecom.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.ProductDescription;
import com.nebulacompanies.ibo.ecom.model.AddToCartModel;
import com.nebulacompanies.ibo.ecom.model.Product;
import com.nebulacompanies.ibo.ecom.utils.AdapterCallback;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.util.Session;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private Context context;
    ArrayList<Product> mArrayList;
    boolean clicked = false;
    private AdapterCallback mAdapterCallback;
    private APIInterface mAPIInterface;
    String m_deviceId;
    String uniqueID;
    Session sessionUser;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        MyTextViewEcom name;

        @BindView(R.id.name_short_decs)
        MyTextViewEcom nameShortDecs;

        @BindView(R.id.tv_offer_price_value)
        MyTextViewEcom tvOfferPrice;

        @BindView(R.id.tv_original_price_value)
        MyTextViewEcom tvOriginalPrice;

        @BindView(R.id.tv_price_discount)
        MyTextViewEcom tvDiscountPrice;

        @BindView(R.id.product_count)
        MyTextViewEcom tvCount;

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.ic_add)
        ImageView icAdd;

        @BindView(R.id.ic_remove)
        ImageView icRemove;

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.btn_add_to_cart)
        MyButtonEcom btnAddToCart;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public ProductsAdapter(Context context, ArrayList<Product> mArrayList, AdapterCallback callback, Session session) {
        this.context = context;
        this.mArrayList = mArrayList;
        this.mAdapterCallback = callback;

        mAPIInterface = APIClient.getClient(context).create(APIInterface.class);
        sessionUser = new Session(context);


        m_deviceId = android.provider.Settings.Secure.getString(
                context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        SharedPreferences deviceSharedPreferences = context.getSharedPreferences(PrefUtils.prefDeviceid, 0);
        uniqueID = deviceSharedPreferences.getString(PrefUtils.prefDeviceid, null);

        Log.d("MDEVICEID product", "MDEVICEID product uniqueID: " + uniqueID);
        if (m_deviceId == null || m_deviceId.equals("")) {

            if (uniqueID == null || uniqueID.equals("")) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = context.getSharedPreferences(PrefUtils.prefDeviceid, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PrefUtils.prefDeviceid, randomID);
                editor.apply();
                m_deviceId = randomID;
            } else {
                m_deviceId = uniqueID;
            }
        }
        Log.d("MDEVICEID product", "MDEVICEID product: " + m_deviceId);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //getting the product of the specified position
        Product product = mArrayList.get(position);
        //binding the data with the viewholder views
        holder.name.setText(product.getProductName());
        holder.tvOfferPrice.setText(String.valueOf(product.getProductSalePrice()));
        DecimalFormat decimalFormat = new DecimalFormat("0.##");
        holder.tvOriginalPrice.setText(decimalFormat.format(Double.valueOf(product.getProductMRP())));
        holder.tvDiscountPrice.setText(product.getProductSaving());
      //  holder.name.setText(product.getProductName()+"1234567890");
        holder.nameShortDecs.setText(product.getProductShortDescription());

        Glide.with(context)
                .load(product.getProductImage()).fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_shopping_cart)
                .into(holder.thumbnail);

        Log.d("product.getProductID() ", "product.getProductID() " + product.getProductID());
        holder.btnAddToCart.setOnClickListener(view -> {
            clicked = true;
            addToCart(m_deviceId, sessionUser.getIboKeyId(), product.getProductProductId(), 1, "plus");
        });

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProductDescription.class);
          //  if (clicked) {
                intent.putExtra("product_clicked", clicked?"1":"0");
           /* } else {
                intent.putExtra("product_clicked", "0");
            }*/
            intent.putExtra("catid", product.getCategoryId());
            intent.putExtra("ebc_id", product.getProductID());
            intent.putExtra("product_id", product.getProductProductId());
            intent.putExtra("product_name", product.getProductName());
            intent.putExtra("product_offer_price", String.valueOf(product.getProductSalePrice()));
            intent.putExtra("product_mrp_price", String.valueOf(product.getProductMRP()));
            intent.putExtra("product_desc", product.getProductDescription());
            intent.putExtra("product_saving", product.getProductSaving());
            intent.putExtra("product_return", product.getProductReturnPolicy());
            intent.putExtra("product_warranty", product.getProductWarranty());
            intent.putExtra("product_quantity", product.getProductQuantity());
            intent.putExtra("product_MaxSaleQuantity", product.getProductMaxSaleQuantity());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    private void addToCart(String deviceId, String userId, Integer
            productId, Integer quantity, String CartFlag) {
        final ProgressDialog mProgressDialog = new ProgressDialog(context, R.style.MyTheme);
        mProgressDialog.show();

        mProgressDialog.setCancelable(false);
        mProgressDialog.setContentView(R.layout.progressdialog_ecom);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Call<AddToCartModel> wsCallingRegistation = mAPIInterface.getAddToCartModelCall(deviceId, userId, productId, quantity, CartFlag);
        wsCallingRegistation.enqueue(new Callback<AddToCartModel>() {
            @Override
            public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                        mAdapterCallback.onMethodCallbackMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddToCartModel> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(context, "Error: " + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
