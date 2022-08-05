package com.nebulacompanies.ibo.dwarkaPackage.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.dwarkaPackage.model.PackageData;
import com.nebulacompanies.ibo.ecom.model.Product;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DwarkaPackageAdapter extends RecyclerView.Adapter<DwarkaPackageAdapter.MyViewHolder> {

    private Context context;
    private ProductsAdapterListener listener;
    ArrayList<PackageData> packageData;
    private static int countItem = 0;
    private int lastSelectedPosition = -1;
    private APIInterface mAPIInterface;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rb_package_one)
        RadioButton rbPackage;

        MyTextViewEcom tvPV,tvNV,tvBV;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            tvPV = (MyTextViewEcom) view.findViewById(R.id.tv_pv);
            tvNV = (MyTextViewEcom) view.findViewById(R.id.tv_nv);
            tvBV = (MyTextViewEcom) view.findViewById(R.id.tv_bv);

           /* rbPackage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    mAdapterCallback.onMethodCallbackMain();
                    Toast.makeText(DwarkaPackageAdapter.this.context,
                            "selected offer is " + lastSelectedPosition,
                            Toast.LENGTH_LONG).show();
                }
            });*/

        }
    }


    public DwarkaPackageAdapter(Context context, ArrayList<PackageData> packageData) {
        this.context = context;
        this.packageData = packageData;
        this.listener = listener;
        mAPIInterface = APIClient.getClient(context).create(APIInterface.class);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dwarka_package_list, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //getting the product of the specified position
        PackageData data = packageData.get(position);
        //binding the data with the viewholder views

        //₹ 4000 (1 Night/2 Days stay.)
       // holder.rbPackage.setText("₹ " + data.getProductPrice() + " " + "(" + data.getProductName() + ")\n"+data.getProductDecs());
        holder.rbPackage.setText("₹ " + data.getProductPrice() + " " + "(" + data.getProductDecs() + ")");

        holder.tvPV.setText("PV: "+data.getProductPV());
        holder.tvNV.setText("NV: "+data.getProductNV());
        holder.tvBV.setText("BV: "+data.getProductBV());

        if (position==1){
            holder.rbPackage.setChecked( true );
        }

        holder.rbPackage.setChecked(lastSelectedPosition == position);

        holder.rbPackage.setOnClickListener(v -> {
            lastSelectedPosition = position;
            notifyDataSetChanged();
            Gson gson = new Gson();
            String json = gson.toJson(data,new TypeToken<PackageData>(){}.getType());
            Log.d("position GET", "position GET" + position);
            Intent intent = new Intent("custom-message");
            intent.putExtra("data", json);
            intent.putExtra("position", position);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        });

    }



    @Override
    public int getItemCount() {
        Log.d("myCartsModel.size()", "myCartsModel.size():" + packageData.size());
        return packageData.size();
    }


    public interface ProductsAdapterListener {
        void onProductAddedCart(int index, Product product);
        void onProductRemovedFromCart(int index, Product product);
    }

}
