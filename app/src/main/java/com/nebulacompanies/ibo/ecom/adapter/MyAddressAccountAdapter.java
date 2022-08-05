package com.nebulacompanies.ibo.ecom.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.EditAddressAccountActivity;
import com.nebulacompanies.ibo.ecom.LoginActivityEcom;
import com.nebulacompanies.ibo.ecom.model.AddressModel;
import com.nebulacompanies.ibo.ecom.model.DeleteAddressModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.nebulacompanies.ibo.util.Const.REQUEST_STATUS_CODE_SUCCESS;

public class MyAddressAccountAdapter extends RecyclerView.Adapter<MyAddressAccountAdapter.MyViewHolder> {
    ArrayList<AddressModel> addressDataList = new ArrayList<>();
    Context context;
    private int lastPosition = -1;
    private int lastSelectedPosition = -1;
    double totalCartAmount;
    String m_deviceId;
    Utils utils = new Utils();
    private APIInterface mAPIInterface;
    public MyAddressAccountAdapter(ArrayList<AddressModel> addressDataList, Context context) {
        this.addressDataList = addressDataList;

        this.context=context;
        mAPIInterface = APIClient.getClient(context).create(APIInterface.class);
        /*TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        m_deviceId = TelephonyMgr.getDeviceId();*/
        m_deviceId = android.provider.Settings.Secure.getString(
                context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_address_account_list_row, viewGroup, false);

        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        AddressModel data = addressDataList.get(i);

        viewHolder.name.setText(data.getFullName());
        viewHolder.address_one.setText(data.getAddressLineOne());
        viewHolder.address_two.setText(data.getAddressLineTwo());
        viewHolder.address_three.setText(data.getAddressCity());
        viewHolder.address_four.setText(data.getAddressState() + " " + data.getAddressPincode());
        viewHolder.address_five.setText("Mobile: " +data.getMobileNo());

        viewHolder.btnAddressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(context)) {
                    Intent addressEdit = new Intent(context, EditAddressAccountActivity.class);
                    addressEdit.putExtra("address_id", data.getID());
                    addressEdit.putExtra("address_name", data.getFullName());
                    addressEdit.putExtra("address_mobile", data.getMobileNo());
                    addressEdit.putExtra("address_one", data.getAddressLineOne());
                    addressEdit.putExtra("address_two", data.getAddressLineTwo());
                    addressEdit.putExtra("address_landmark", data.getAddressLandMark());
                    addressEdit.putExtra("address_city", data.getAddressCity());
                    addressEdit.putExtra("address_state", data.getAddressState());
                    addressEdit.putExtra("address_pincode", data.getAddressPincode());
                    addressEdit.putExtra("address_type", data.getAddressType());
                    addressEdit.putExtra("address_edit_click", 1);
                    addressEdit.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(addressEdit);
                }else{
                    utils.dialogueNoInternet((Activity) context);
                }
            }
        });

        viewHolder.btnAddressRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                deleteFromCart(data.getID());
                                removeAt(viewHolder.getPosition());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.MyDialogTheme);
                builder.setMessage("Are you sure you want to delete this address?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        return addressDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MyTextViewEcom address_one,address_two,address_three,address_four,address_five;
        MyBoldTextViewEcom name;
        RelativeLayout parent;
        MyButtonEcom btnAddressEdit,btnAddressRemove;
        public MyViewHolder(View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            name=itemView.findViewById(R.id.name);
            address_one=itemView.findViewById(R.id.tv_address_one);
            address_two=itemView.findViewById(R.id.tv_address_two);
            address_three=itemView.findViewById(R.id.tv_address_three);
            address_four=itemView.findViewById(R.id.tv_address_four);
            address_five=itemView.findViewById(R.id.tv_address_five);
            btnAddressEdit=itemView.findViewById(R.id.btn_address_edit);
            btnAddressRemove=itemView.findViewById(R.id.btn_address_remove);
        }
    }


    private void deleteFromCart(Integer id) {
      //  final ProgressDialog mProgressDialog = new ProgressDialog(context, R.style.MyTheme);
       /* mProgressDialog.show();

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
        });*/
        Call<DeleteAddressModel> wsCallingRegistation = mAPIInterface.deleteAddressDetails(id);
        wsCallingRegistation.enqueue(new Callback<DeleteAddressModel>() {
            @Override
            public void onResponse(Call<DeleteAddressModel> call, Response<DeleteAddressModel> response) {
               /* if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
*/
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                       // Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "Address Deleted", Toast.LENGTH_SHORT).show();
                        //confirmDailogue();
                        //Log.d("Total Delete API","Total Delete API: "+productId );
//
                    }
                }else if (response.code() == 401){
                    Log.d("AddressListGETINData401","AddressListGETINData: "+  new Gson().toJson(response.body()) );

                   /* Intent dash = new Intent(context, LoginActivityEcom.class);
                    context.startActivity(dash);*/

                }
            }

            @Override
            public void onFailure(Call<DeleteAddressModel> call, Throwable t) {
                //mProgressDialog.dismiss();
               // Log.d("Total Delete Failure","Total Delete Failure: "+productId +"Error: "+ t);
            }
        });
    }
    public void removeAt(int position) {
        addressDataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, addressDataList.size());
    }
}
