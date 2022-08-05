package com.nebulacompanies.ibo.ecom.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.Interface.OnItemClick;
import com.nebulacompanies.ibo.Interface.OnProductClick;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.model.OtherProducts;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.nebulacompanies.ibo.ecom.adapter.ProductColorDynamicAdapter.selavailids;


/*
MOST IMPORTANT THING
Hello,

        Before adding product attributes from backend side :

        1.  Make sure child and parent product have same property attributes.
        Example: If parent product has only one attribute as weight then child can have only one attribute as weight.

        2. Make sure first attribute is for 'color'.
        Example: 'MainKey' of response will be color in first position.
        http://203.88.139.169:9064/API/EcomAttribute/GetEcomAttributeValuesList?pickupid=0&Id=57

        3. make sure color/image value available for each child in same 'MainKey'.
        Example:  'MainKey' of response : all child will have color value.*/
public class ProductVariantsAdapter extends RecyclerView.Adapter<ProductVariantsAdapter.ViewHolder> implements OnItemClick {

    private final Context context;
    List<OtherProducts.Datum> datumArrayList;
    String TAG = "Others";
    List<Integer> selids;
    int[] selBars;
    RecyclerView recyclerView;
    ArrayList<MyBoldTextViewEcom> valueText;
    private final OnProductClick mCallback;
    List<List<Integer>> selidslist = new ArrayList<>();
    int nochangepos = 0;
    Set<Integer> selfinal = new HashSet<>();
    boolean mismatch = false;

    public ProductVariantsAdapter(Context context, List<OtherProducts.Datum> datumArrayList,
                                  List<Integer> selids, RecyclerView recyclerView, OnProductClick mCallback) {
        this.context = context;
        this.datumArrayList = datumArrayList;
        this.selids = selids;
        this.mCallback = mCallback;
        selBars = new int[datumArrayList.size()];
        this.recyclerView = recyclerView;
        valueText = new ArrayList<>();
        selBars = new int[datumArrayList.size()];
        for (int i = 0; i < datumArrayList.size(); i++) {
            selBars[i] = -1;
        }
        for (int i = 0; i < datumArrayList.size(); i++) {
            selidslist.add(new ArrayList<>());
        }
        selavailids.clear();
    }

    @Override
    public void onClick(Integer parentposition, Integer childposition, List<Integer> elementsids) {
        Log.d(TAG, "click--" + parentposition + " :: " + childposition + " : " + elementsids);
        selids.clear();
        selids.addAll(elementsids);
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSubmit() {
        Log.d(TAG, " selBars Parent " + " : " + Arrays.toString(selBars));
        boolean readytoCall = true;
        for (int i = 0; i < selBars.length; i++) {
            int pos = selBars[i];
            valueText.get(i).setText(": " + datumArrayList.get(i).getMainValue());
            if (pos == -1)
                readytoCall = false;
            Log.d(TAG, i + " pos  " + pos + " : " + datumArrayList.get(i).getMainValue() + " : " + readytoCall);
        }
        if (readytoCall)
            Toast.makeText(context, "Call API " + selids, Toast.LENGTH_SHORT).show();
    }

    List<Integer> elementsids = new ArrayList<>();

    @Override
    public void updateData(Integer nochangepos, Set<Integer> selfinal, List<Integer> elementsids) {
        this.elementsids = elementsids;
        mismatch = true;
        this.nochangepos = nochangepos;
        this.selfinal = selfinal;
        notifyDataSetChanged();
    }

    @Override
    public void updateMismatch() {
        mismatch = false;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final MyTextViewEcom tvKey;
        MyBoldTextViewEcom tvValue;
        RecyclerView recVariant;

        public ViewHolder(View view) {
            super(view);
            tvKey = view.findViewById(R.id.tv_key);
            tvValue = view.findViewById(R.id.tv_value);
            recVariant = view.findViewById(R.id.recycler_view_data);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_product_variants, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OtherProducts.Datum datum = datumArrayList.get(position);
        holder.tvKey.setText("Choose " + datum.getMainKey() + " : ");
        // holder.tvValue.setText(" : " +datum.getMainValue());
        valueText.add(holder.tvValue);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.recVariant.setLayoutManager(layoutManager);
        ProductColorsAdapter mProductColorsAdapter = new ProductColorsAdapter(context, datum, mCallback, position, selids, selBars, position == getItemCount() - 1, selidslist, holder.tvValue, this, datumArrayList.size() - 1, nochangepos, selfinal, mismatch, elementsids);//,
        holder.recVariant.setAdapter(mProductColorsAdapter);
        holder.recVariant.setHasFixedSize(true);
       /* if (position == getItemCount() - 1) {
            Log.d(TAG, position + " selBars Parent " + " : " + Arrays.toString(selBars));
        }*/
    }

    @Override
    public int getItemCount() {
        return datumArrayList.size();
    }
}



