package com.nebulacompanies.ibo.ecom.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.PickUpAddressModel;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyItalicTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;

import java.util.ArrayList;

public class PickUpAddressAdapter extends RecyclerView.Adapter<PickUpAddressAdapter.ViewHolder> {

    //private List<String> mItems;
    public ItemListener mListener;
   // ArrayList<AddressModel> addressDataList = new ArrayList<>();
    ArrayList<PickUpAddressModel> addressDataList = new ArrayList<>();
    Context context;

    public PickUpAddressAdapter(ArrayList<PickUpAddressModel> addressDataList, Context context, ItemListener mListener) {
        this.addressDataList = addressDataList;
        this.context = context;
        this.mListener = mListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder( LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.bottom_sheet_item, parent, false ) );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //  holder.setData(mItems.get(position));

        PickUpAddressModel data = addressDataList.get( position );
        holder.address_one.setText( data.getPickUpAddress() );
        holder.address_two.setVisibility( View.GONE );
        holder.address_three.setVisibility( View.GONE );
        holder.address_four.setVisibility( View.GONE );
        holder.address_five.setVisibility( View.GONE );


        Integer cityId= data.getID();
        SharedPreferences citySave = context.getSharedPreferences(PrefUtils.prefCityid, 0);
        SharedPreferences.Editor editor = citySave.edit();
        editor.putInt(PrefUtils.prefCityid,cityId);
        editor.apply();


        holder.lnAddress.setOnClickListener(v -> {
            if (mListener != null) {

                Integer cityId1 = data.getID();
                SharedPreferences citySave1 = context.getSharedPreferences(PrefUtils.prefCityid, 0);
                SharedPreferences.Editor editor1 = citySave1.edit();
                editor1.putInt(PrefUtils.prefCityid, cityId1);
                editor1.apply();
                mListener.onItemClick( addressDataList.get( position ) );
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

        MyItalicTextViewEcom textView;
        MyTextViewEcom address_one, address_two, address_three, address_four, address_five;
        MyBoldTextViewEcom name;
        RelativeLayout parent;
        MyButtonEcom btnSummary, edit;
        RadioButton rdAddress;
        String item;
        CardView cardView;
        LinearLayout lnAddress;

        ViewHolder(View itemView) {
            super( itemView );
            // itemView.setOnClickListener(this);
            textView = (MyItalicTextViewEcom) itemView.findViewById( R.id.textView );
            cardView = (CardView) itemView.findViewById( R.id.card_view );

            // name = itemView.findViewById(R.id.name);
            address_one = itemView.findViewById( R.id.tv_address_name );
            address_two = itemView.findViewById( R.id.tv_address_decs );
            address_three = itemView.findViewById( R.id.tv_address_area );
            address_four = itemView.findViewById( R.id.tv_address_city );
            address_five = itemView.findViewById( R.id.tv_address_state );
            lnAddress = itemView.findViewById( R.id.ln_address_address );

           /* lnAddress.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 mListener.onItemClick( addressDataList.get(getAdapterPosition()));
                }
            } );*/

        }

        void setData(String item) {
            this.item = item;
            textView.setText( item );
        }

       /* @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }*/
    }

    public interface ItemListener {
        void onItemClick(PickUpAddressModel addressDataList);
    }
}
