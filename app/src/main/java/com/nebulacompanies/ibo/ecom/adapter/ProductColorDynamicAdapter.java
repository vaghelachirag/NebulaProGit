package com.nebulacompanies.ibo.ecom.adapter;

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

public class ProductColorDynamicAdapter extends RecyclerView.Adapter<ProductColorDynamicAdapter.ViewHolder> {

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
    //ArrayList<LinearLayout> layouts;
    List<List<Integer>> selidslist;
    boolean setDefult = true;
    String available = "1";
    String notavailable = "0";
    String notselected = "0";
    String selected = "1";
    MyBoldTextViewEcom tvValue;
    boolean dataset = false;
    boolean semiauto = false;
    public static List<Integer> selavailids = new ArrayList<>();
    int parentSize;
    int setbordercolorwhite, setbordercolorblack;
List<Integer> updatePos=new ArrayList<>();
    public ProductColorDynamicAdapter(Context context, OtherProducts.Datum datum, OnProductClick mCallback, int parentpos, List<Integer> selids, int[] selBars, boolean callSubmit, List<List<Integer>> selidslist, MyBoldTextViewEcom tvValue, OnItemClick mItemCallback, int parentSize) {
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
        //selavailids = new ArrayList<>(selids);
        selpos = new ArrayList<>();
        //layouts = new ArrayList<>();
        this.callSubmit = callSubmit;
        ecomAttributeValues = datum.getEcomAttributeValueList();
        dataset = false;
        setbordercolorwhite = context.getResources().getColor(R.color.white);
        setbordercolorblack = context.getResources().getColor(R.color.black);
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

    boolean isselected = false;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OtherProducts.EcomAttributeValue value = ecomAttributeValues.get(position);
        List<Integer> ecomAttributeSKUList = value.getEcomAttributeSKUList();
        Set<Integer> s1 = new HashSet<>(ecomAttributeSKUList);
        Set<Integer> s2 = new HashSet<>(selids);
        s1.retainAll(s2);
        boolean contain = s1.size() > 0;
        Log.d(TAG, " contain " + position + " : " + contain + " s1 " + s1 + " s2 " + s2);

        String colorcode = value.getAttributeColor();

        int setbordercolor = context.getResources().getColor(R.color.white);
        if (setDefult) {
            if (contain) {
                if (!dataset) {
                    dataset = true;
                    tvValue.setText(value.getAttributeName());
                    setbordercolor = setbordercolorblack;
                    // holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.nebula_new_dark));
                    selBars[parentpos] = position;
                    selidslist.set(parentpos, ecomAttributeSKUList);
                    checkedPosition = position;
                    holder.layInner.setTag(selected);
                    isselected = true;
                    if (parentpos == 0) {
                        Log.d(TAG, " selavailids " + position + " : " + selavailids);
                        selavailids = new ArrayList<>(ecomAttributeSKUList);
                        Log.d(TAG, " selavailids after " + position + " : " + selavailids);
                    }
                }
            } else {
                setbordercolor = setbordercolorwhite;
                //   holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
                holder.layInner.setTag(notselected);
                isselected = false;
            }
        } else {
            if (checkedPosition == position) {
                isselected = false;
                holder.layInner.setTag(selected);
                setbordercolor = setbordercolorblack;
                // holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.nebula_new_dark));
            } else {
                isselected = false;
                holder.layInner.setTag(notselected);
                setbordercolor = setbordercolorwhite;
                // holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
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

       /*if (TextUtils.isEmpty(colorcode)) {
            holder.linearLayout.setBackgroundColor(setbordercolor);
        } else {
            ColorStateList csl = ColorStateList.valueOf(setbordercolor);
            holder.imgBorder.setBackgroundTintList(csl);
        }*/

        holder.layInner.setOnClickListener(view -> {
            String mTag = (String) holder.layInner.getTag();
            Log.d(TAG, "mTag: " + mTag);
            if (semiauto) {
                if (checkedPosition == position) {
                   /*checkedPosition = -1;
                     selBars[parentpos] = -1;
                     selidslist.set(parentpos, new ArrayList<>());
                     notifyDataSetChanged();
                     callSubmitMethod();*/
                } else {
                    tvValue.setText(value.getAttributeName());
                    selBars[parentpos] = position;
                    selidslist.set(parentpos, ecomAttributeSKUList);
                    checkedPosition = position;
                    notifyDataSetChanged();
                    callSubmitMethod(true, parentpos, position, value.getEcomAttributeSKUList());
                }
            } else {
                if (holder.layInner.getTag().equals(notselected)) {
                    String mTagBg = (String) holder.layBg.getTag();
                    String mTagBgCircle = (String) holder.layImage.getTag();
                    Log.d(TAG, "mTagBg: " + mTagBg);
                    setdatadefault = parentpos != 0;
                    if(TextUtils.isEmpty(colorcode)) {
                        if (mTagBg.equals(available)) {
                            tvValue.setText(value.getAttributeName());
                            selBars[parentpos] = position;
                            selidslist.set(parentpos, ecomAttributeSKUList);
                            checkedPosition = position;
                            notifyDataSetChanged();
                            callSubmitMethod(true, parentpos, position, value.getEcomAttributeSKUList());
                        } else {
                            mItemCallback.onClick(parentpos, position, value.getEcomAttributeSKUList());
                        }
                    }else{
                        if (mTagBgCircle.equals(available)) {
                            tvValue.setText(value.getAttributeName());
                            selBars[parentpos] = position;
                            selidslist.set(parentpos, ecomAttributeSKUList);
                            checkedPosition = position;
                            notifyDataSetChanged();
                            callSubmitMethod(true, parentpos, position, value.getEcomAttributeSKUList());
                        } else {
                            mItemCallback.onClick(parentpos, position, value.getEcomAttributeSKUList());
                        }
                    }
                }
            }
        });

        if (!semiauto) {
            Log.d(TAG, "Before " + contain+" : "+parentpos+" :: "+setdatadefault);
            if (parentpos > 0 ) {//&& !isselected && setdatadefault
                Log.d(TAG, " s1 s2 " + position + " : " + ecomAttributeSKUList + " :: " + selavailids);
                s1 = new HashSet<>(ecomAttributeSKUList);
                s2 = new HashSet<>(selavailids);
                s1.retainAll(s2);
                contain = s1.size() > 0;
                /*if(contain){
                    selavailids.clear();
                    selavailids.addAll(s1);
                }*/
                Log.d(TAG, " s1 s2 " + position + " : " + s1 + " :: " + s2);
            }
            Log.d(TAG, " set available " + position + " : " + parentpos + " :: " + contain);
            Log.d(TAG, "After " + contain);
            if (contain) {
                holder.layBg.setTag(available);
                holder.layImage.setTag(available);
                holder.colorName.setBackground(null);
            } else {
                if (isselected) {
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
        }

        holder.colorName.setText(value.getAttributeName());
        // layouts.add(holder.layInner);
        if (position == getItemCount() - 1 && setDefult) {
            Log.d(TAG, " last element " + position + " : " + selidslist + " : " + " : " + Arrays.toString(selBars));
            setDefult = false;
            if (!semiauto)
                callSubmitMethod(false, parentpos, position, value.getEcomAttributeSKUList());
        }
       /*if (position == getItemCount() - 1 && parentpos == parentSize) {
            setdatadefault = false;
        }*/
    }

    private void callSubmitMethod(boolean callclick, Integer parentposition, Integer childposition, List<Integer> elementsids) {
        Log.d(TAG, " callSubmitMethod " + " : " + selidslist + " : " + " : " + Arrays.toString(selBars));
        List<Integer> myIds = new ArrayList<>();
        List<Integer> myIdsNotMatching = new ArrayList<>();
        List<Integer> finalselIDs = new ArrayList<>();
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
                                updatePos.add(i);
                                // add id here
                            }
                        }
                    } else
                        notsel = true;
                }
                if (callAPI) {
                    List<Integer> list = new ArrayList<>(s1);
                    if (list.size() == 1) {
                        mCallback.onSubmit(list.get(0));
                        //Toast.makeText(context, "select " + list.get(0), Toast.LENGTH_SHORT).show();
                    } else {
                        mCallback.onsetMessage("Please select another category.", false);
                        // Toast.makeText(context, "Please select another category.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    if (notsel) {
                        mCallback.onsetMessage("Please select all category.", false);
                        // Toast.makeText(context, "Please select all category.", Toast.LENGTH_LONG).show();
                    } else {
                        if (callclick) {
                            mCallback.onsetMessage("Product not available according to your selection."+ updatePos, true);
                           //  mItemCallback.onClick(parentposition, childposition,elementsids);
                        }
                        // Toast.makeText(context, "Product not available according to your selection.", Toast.LENGTH_LONG).show();
                    }
                }
               /* Set<Integer> s = new HashSet<>();
                List<Integer> result = new ArrayList<>();
                for (Integer name : myIds) {
                    if (!s.add(name))
                        result.add(name);
                }
                Log.d("Others", " result " + " : " + result);

                if (result.size() > 0) {
                    if (result.size() == 1) {
                        mCallback.onSubmit(result.get(0));
                    } else {
                        int pop = mostCommon(result);
                        Log.d("Others", " popular " + " : " + pop);
                        if (pop != -1) {
                            mCallback.onSubmit(pop);
                        } else {
                            boolean callApi = true;
                            for (int pos : selBars)
                                if (pos == -1) {
                                    callApi = false;
                                    break;
                                }
                            if (callApi) {
                                Toast.makeText(context, "Product not available according to selection.\nPlease select another category.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Please select another category.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }*/
            }
        } else {
           /* boolean callApi = true;
            for (int pos : selBars)
                if (pos == -1) {
                    callApi = false;
                    break;
                }
            if (callApi) {
                Toast.makeText(context, "Product not available according to selection.\nPlease select another category.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Please select another category.", Toast.LENGTH_LONG).show();
            }*/
        }

    }

    @Override
    public int getItemCount() {
        return ecomAttributeValues.size();
    }
/*
    public <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }

    public int findPopular(List<Integer> list) {
        if (list == null || list.size() == 0)
            return 0;
        int[] a = new int[list.size()];
        for (int i = 0; i < list.size(); i++) a[i] = list.get(i);
        Arrays.sort(a);

        int previous = a[0];
        int popular = a[0];
        int count = 1;
        int maxCount = 1;

        for (int i = 1; i < a.length; i++) {
            if (a[i] == previous)
                count++;
            else {
                if (count > maxCount) {
                    popular = a[i - 1];
                    maxCount = count;
                }
                previous = a[i];
                count = 1;
            }
        }
        Log.d(TAG, " count <= maxCount " + count + " : " + maxCount);
        return count <= maxCount ? -1 : popular;
    }*/
}



