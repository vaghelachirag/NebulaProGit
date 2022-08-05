package com.nebulacompanies.ibo.ecom.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

import static com.nebulacompanies.ibo.ecom.ProductDescription.setdatadefault;

public class ProductColorsAdapter extends RecyclerView.Adapter<ProductColorsAdapter.ViewHolder> {

    private Context context;
    private int checkedPosition = -1;
    private OnProductClick mCallback;
    private OnItemClick mItemCallback;
    String TAG = "Others";
    List<OtherProducts.EcomAttributeValue> ecomAttributeValues;
    int parentpos;
    List<Integer> selids;
    int[] selBars;
    List<Integer> selpos;
    boolean callSubmit;
    OtherProducts.Datum datum;
    List<List<Integer>> selidslist;
    boolean setDefult = true;
    String available = "1";
    String notavailable = "0";
    String notselected = "0";
    String selected = "1";
    MyBoldTextViewEcom tvValue;
    boolean dataset;
    public static List<Integer> selavailids = new ArrayList<>();
    int nochangepos;
    int parentSize;
    int setbordercolorwhite, setbordercolorblack;
    List<Integer> updatePos = new ArrayList<>();
    Set<Integer> selfinal;
    boolean mismatch;
    List<Integer> elementsidsset;

    public ProductColorsAdapter(Context context, OtherProducts.Datum datum, OnProductClick mCallback, int parentpos, List<Integer> selids, int[] selBars, boolean callSubmit, List<List<Integer>> selidslist, MyBoldTextViewEcom tvValue, OnItemClick mItemCallback, int parentSize, int nochangepos, Set<Integer> selfinal, boolean mismatch, List<Integer> elementsidsset) {
        this.context = context;
        this.mCallback = mCallback;
        this.parentpos = parentpos;
        this.selids = selids;
        this.selBars = selBars;
        this.selidslist = selidslist;
        this.datum = datum;
        this.tvValue = tvValue;
        this.mItemCallback = mItemCallback;
        this.parentSize = parentSize;
        selpos = new ArrayList<>();
        this.callSubmit = callSubmit;
        ecomAttributeValues = datum.getEcomAttributeValueList();
        dataset = false;
        setbordercolorwhite = context.getResources().getColor(R.color.white);
        setbordercolorblack = context.getResources().getColor(R.color.black);
        this.nochangepos = nochangepos;
        this.selfinal = selfinal;
        this.mismatch = mismatch;
        Log.d(TAG, "ProductColorsAdapter mismatch  " + parentpos + " : " + mismatch + " : " + setDefult);
        this.elementsidsset = elementsidsset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private MyTextViewEcom colorName;
        private CardView cardView;
        private LinearLayout linearLayout, layInner, layBg;
        private RelativeLayout layImage;
        private ImageView imgBorder, imgCenter;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.img_color);
            colorName = view.findViewById(R.id.color_name);
            cardView = view.findViewById(R.id.card_color);
            linearLayout = view.findViewById(R.id.laydata);
            layInner = view.findViewById(R.id.lay_inner);
            layBg = view.findViewById(R.id.lay_background);
            imgBorder = view.findViewById(R.id.image_border);
            imgCenter = view.findViewById(R.id.image_center);
            layImage = view.findViewById(R.id.lay_image);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_colors, viewGroup, false);
        return new ViewHolder(view);
    }

    int setbordercolor;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OtherProducts.EcomAttributeValue value = ecomAttributeValues.get(position);
        List<Integer> ecomAttributeSKUList = value.getEcomAttributeSKUList();
        Set<Integer> s1 = new HashSet<>(ecomAttributeSKUList);
        Set<Integer> s2 = new HashSet<>(selids);
        s1.retainAll(s2);
        boolean contain = s1.size() > 0;
        String colorcode = value.getAttributeColor();
        setbordercolor = context.getResources().getColor(R.color.white);

        if (setDefult) {
            if (!mismatch) {
                setDefData(contain, value.getAttributeName(), position, ecomAttributeSKUList, holder);
            } else if (parentpos == 0) {
                setDefData(contain, value.getAttributeName(), position, ecomAttributeSKUList, holder);
            } else if (parentpos == nochangepos) {
                setDefDataforFixPos(contain, value.getAttributeName(), position, ecomAttributeSKUList, holder);
            } else {
                Log.d(TAG, "change mismatch 1 " + " : " + nochangepos + " : " + parentpos);
                setDefDataforChangePos(value.getAttributeName(), position, ecomAttributeSKUList, holder);
            }
        } else {
            if (checkedPosition == position) {
                holder.layInner.setTag(selected);
                setbordercolor = setbordercolorblack;
            } else {
                holder.layInner.setTag(notselected);
                setbordercolor = setbordercolorwhite;
            }
        }

        if (TextUtils.isEmpty(colorcode)) {
            holder.layImage.setVisibility(View.GONE);
            holder.cardView.setVisibility(View.VISIBLE);
            holder.linearLayout.setBackgroundColor(setbordercolor);
        } else {
            int colorInt = Color.parseColor(colorcode);
            ColorStateList csl = ColorStateList.valueOf(colorInt);
            holder.imgCenter.setBackgroundTintList(csl);
            holder.layImage.setVisibility(View.VISIBLE);
            holder.cardView.setVisibility(View.GONE);
            ColorStateList cslb = ColorStateList.valueOf(setbordercolor);
            holder.imgBorder.setBackgroundTintList(cslb);
        }

        holder.layInner.setOnClickListener(view -> {
            String mTag = (String) holder.layInner.getTag();
            Log.d(TAG, "mTag: " + mTag);
            mItemCallback.updateMismatch();

            if (holder.layInner.getTag().equals(notselected)) {
                String mTagBg = (String) holder.layBg.getTag();
                String mTagBgCircle = (String) holder.layImage.getTag();
                Log.d(TAG, "mTagBg: " + mTagBg);
                setdatadefault = parentpos != 0;
                if (TextUtils.isEmpty(colorcode)) {
                    if (mTagBg.equals(available)) {
                        callAvailable(value.getAttributeName(), position, ecomAttributeSKUList);
                    } else {
                        mItemCallback.onClick(parentpos, position, ecomAttributeSKUList);
                    }
                } else {
                    if (mTagBgCircle.equals(available)) {
                        callAvailable(value.getAttributeName(), position, ecomAttributeSKUList);
                    } else {
                        mItemCallback.onClick(parentpos, position, ecomAttributeSKUList);
                    }
                }
            }
        });
        holder.colorName.setText(value.getAttributeName());
        Log.d(TAG, "Before " + contain + " : " + parentpos + " :: " + setdatadefault);
        if (!mismatch) {
            setData(holder, position, ecomAttributeSKUList);
        } else if (parentpos == 0 || parentpos == nochangepos) {
            setData(holder, position, ecomAttributeSKUList);
        } else {
            Log.d(TAG, "change mismatch 2 " + " : " + position);
            setData(holder, position, ecomAttributeSKUList);
        }
    }

    private void setDefDataforChangePos(String name, int position, List<Integer> skulist, ViewHolder holder) {
        Set<Integer> s1 = new HashSet<>(skulist);
        Set<Integer> s2 = new HashSet<>(elementsidsset);
        s1.retainAll(s2);
        boolean contain = s1.size() > 0;
        if (contain) {
            dataset = true;
            tvValue.setText(name);
            selBars[parentpos] = position;
            selidslist.set(parentpos, skulist);
            checkedPosition = position;
            setbordercolor = setbordercolorblack;
            holder.layInner.setTag(selected);
        } else {
            setbordercolor = setbordercolorwhite;
            holder.layInner.setTag(notselected);
        }
    }

    private void setDefDataforFixPos(boolean contain, String name, int position, List<Integer> skulist, ViewHolder holder) {
        Log.d(TAG, "setDefDataforFixPos " + contain + " : " + skulist + " :: " + elementsidsset);
        if (skulist.equals(elementsidsset)) {
            dataset = true;
            tvValue.setText(name);
            selBars[parentpos] = position;
            selidslist.set(parentpos, skulist);
            checkedPosition = position;
            setbordercolor = setbordercolorblack;
            holder.layInner.setTag(selected);
            if (parentpos == 0) {
                selavailids = new ArrayList<>(skulist);
            }
        } else {
            setbordercolor = setbordercolorwhite;
            holder.layInner.setTag(notselected);
        }

    }

    private void setDefData(boolean contain, String name, int position, List<Integer> skulist, ViewHolder holder) {
        if (contain) {
            if (!dataset) {
                dataset = true;
                tvValue.setText(name);
                selBars[parentpos] = position;
                selidslist.set(parentpos, skulist);
                checkedPosition = position;
                setbordercolor = setbordercolorblack;
                holder.layInner.setTag(selected);
                if (parentpos == 0) {
                    selavailids = new ArrayList<>(skulist);
                }
            }
        } else {
            setbordercolor = setbordercolorwhite;
            holder.layInner.setTag(notselected);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setData(ViewHolder holder, int position, List<Integer> ecomAttributeSKUList) {
        boolean contain = false;
        if (parentpos == 0) {
            updatePos.clear();

        } else {//&& !isselected && setdatadefault
            Log.d(TAG, " s1 s2 " + position + " : " + ecomAttributeSKUList + " :: " + selavailids);
            Set<Integer> s1 = new HashSet<>(ecomAttributeSKUList);
            Set<Integer> s2 = new HashSet<>(selavailids);
            s1.retainAll(s2);
            contain = s1.size() > 0;

            Log.d(TAG, " s1 s2 " + position + " : " + s1 + " :: " + s2);
        }
        Log.d(TAG, " set available " + position + " : " + parentpos + " :: " + contain);
        Log.d(TAG, "After " + contain);
        if (contain) {
            holder.layBg.setTag(available);
            holder.layImage.setTag(available);
            holder.colorName.setBackground(null);
        } else {
            if (holder.layInner.getTag().equals(selected)) {
                holder.colorName.setBackground(null);
                holder.layBg.setTag(available);
                holder.layImage.setTag(available);
            } else {
                holder.layBg.setTag(notavailable);
                holder.layImage.setTag(notavailable);
                if (parentpos > 0)
                    holder.colorName.setBackground(context.getResources().getDrawable(R.drawable.diagonal));
                else
                    holder.colorName.setBackground(null);
            }
        }
          /*holder.layBg.setBackgroundColor(contain ? Color.WHITE : (isselected ? Color.WHITE : Color.LTGRAY));
            holder.layBg.setTag(contain ? available : (isselected ? available : notavailable));
            holder.colorName.setTextColor(contain ? Color.BLACK : (isselected ? Color.BLACK : Color.GRAY));*/
        if (position == getItemCount() - 1 && setDefult) {
            Log.d(TAG, " last element " + position + " : " + selidslist + " : " + " : " + Arrays.toString(selBars));
            setDefult = false;
            callSubmitMethod(false, parentpos, position, ecomAttributeSKUList);
            // mItemCallback.updateMismatch();
        }

    }

    private void callAvailable(String text, int pos, List<Integer> skulist) {
        tvValue.setText(text);
        selBars[parentpos] = pos;
        selidslist.set(parentpos, skulist);
        checkedPosition = pos;
        notifyDataSetChanged();
        callSubmitMethod(true, parentpos, pos, skulist);
    }

    private void callSubmitMethod(boolean callclick, Integer parentposition, Integer childposition, List<Integer> elementsids) {
        Log.d(TAG, " callSubmitMethod " + " : " + selidslist + " : " + " : " + Arrays.toString(selBars));
        List<Integer> myIds = new ArrayList<>();

        for (int i = 0; i < selidslist.size(); i++) {
            myIds.addAll(selidslist.get(i));
        }
        Log.d("Others", " onSubmit " + " : " + myIds);

        if (myIds.size() > 0) {
            if (myIds.size() == 1) {
                mCallback.onSubmit(myIds.get(0));
            } else {
                boolean callAPI = true;
                Set<Integer> s1 = new HashSet<>();
                Set<Integer> s2;
                boolean notsel = false;
                for (int i = 0; i < selBars.length; i++) {
                    int selection = selBars[i];
                    if (selection != -1) {
                        if (s1.size() == 0) {
                            s1 = new HashSet<>(selidslist.get(i));
                        } else {
                            s2 = new HashSet<>(selidslist.get(i));
                            s1.retainAll(s2);
                            boolean contain = s1.size() > 0;
                            if (!contain) {
                                callAPI = false;
                            }
                        }
                    } else
                        notsel = true;
                }
                if (callAPI) {
                    List<Integer> list = new ArrayList<>(s1);
                    if (list.size() == 1) {
                        mCallback.onSubmit(list.get(0));
                    } else {
                        mCallback.onsetMessage("Please select another category.", false);
                    }
                } else {
                    if (notsel) {
                        mCallback.onsetMessage("Please select all category.", false);
                    } else {
                        if (callclick) {
                            Set<Integer> s0 = new HashSet<>(selavailids);
                            Set<Integer> sNew = new HashSet<>(elementsids);
                            s0.retainAll(sNew);
                            //boolean contain = s0.size() > 0;
                            mCallback.onsetMessage("updated product. ", false);
                            mItemCallback.updateData(parentposition, s0, elementsids);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return ecomAttributeValues.size();
    }
}



