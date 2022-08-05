package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.ProductListDwarka;
import com.nebulacompanies.ibo.activities.ProductListHyderabad;
import com.nebulacompanies.ibo.activities.SalesAavaas;
import com.nebulacompanies.ibo.activities.SalesDwarka;
import com.nebulacompanies.ibo.activities.SalesHoliday;
import com.nebulacompanies.ibo.ecom.utils.PrefUtils;
import com.nebulacompanies.ibo.model.ProjectList;
import com.nebulacompanies.ibo.walk.FontUtil;
import com.nebulacompanies.ibo.walk.SpotlightView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.nebulacompanies.ibo.util.Constants.ID_AAVAAS_CHANGODER;
import static com.nebulacompanies.ibo.util.Constants.ID_AAVAAS_CHANGODER_COMPLEX;
import static com.nebulacompanies.ibo.util.Constants.ID_AAVAAS_CHENNAI;
import static com.nebulacompanies.ibo.util.Constants.ID_AAVAAS_HYDERABD;
import static com.nebulacompanies.ibo.util.Constants.ID_HAWTHORN_DWARKA;
import static com.nebulacompanies.ibo.util.Constants.ID_HOLIDAY;


/**
 * Created by Sagar Virvani on 09-Jan-18.
 */


public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListHolder> {
    private Context context;
    ArrayList<ProjectList> arrayListProductList = new ArrayList<>();

    int lastPosition = -1;
    String mysale;
    SpotlightView spotLightProduct,spotLightSales;

    public ProductListAdapter(Activity context, ArrayList<ProjectList> projects, String mysale) {
        this.context = context;
        this.mysale = mysale;
        arrayListProductList.clear();
        arrayListProductList.addAll(projects);
        spotLightProduct = new SpotlightView((Activity) context);
        spotLightSales = new SpotlightView((Activity) context);
    }

    public class ProductListHolder extends RecyclerView.ViewHolder {

        // CardView cardViewPackage;
        ImageView imageView;
        TextView txtTitle;

        public ProductListHolder(View convertView) {
            super(convertView);
            context = convertView.getContext();
            //  cardViewPackage = convertView.findViewById(R.id.card_view);
            imageView = (ImageView) convertView.findViewById(R.id.list_item_image);
            txtTitle = (TextView) convertView.findViewById(R.id.list_item_name);
        }
    }

    @Override
    public ProductListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_image_name, parent, false);

        return new ProductListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductListHolder holder, final int position) {

        final ProjectList projectList = arrayListProductList.get(position);

        holder.txtTitle.setText(projectList.getProjectName());
        /*Picasso.with(context)
                .load(projectList.getProjectIcon())
                .into(holder.imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        holder.imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                    }
                });*/

        if (position == 1 && mysale.equalsIgnoreCase("MyProject")) {
            SharedPreferences walkthroughProduct = context.getSharedPreferences(PrefUtils.prefwaProduct, 0);
            if (walkthroughProduct.getBoolean("walkthrough_first_time_product_ibo", true)) {

                spotLightProduct= new SpotlightView.Builder((Activity) context)
                        .introAnimationDuration(400)
                        .enableRevealAnimation(true)
                        .performClick(true)
                        .fadeinTextDuration(400)
                        .setTypeface(FontUtil.get(context, "fonts/Montserrat-Regular.ttf"))
                        .headingTvColor(Color.parseColor("#eb273f"))
                        .headingTvSize(18)
                        .headingTvText("Real Estate Projects")
                        .subHeadingTvColor(Color.parseColor("#ffffff"))
                        .subHeadingTvSize(14)
                        .subHeadingTvText("Click on any project to view the types of apartments available for sale.")
                        .maskColor(Color.parseColor("#dc000000"))
                        .target(holder.imageView)
                        .lineAnimDuration(400)
                        .lineAndArcColor(Color.parseColor("#eb273f"))
                        .dismissOnTouch(false)
                        .dismissOnBackPress(false)
                        .enableDismissAfterShown(false)
                        .show();
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (arrayListProductList.get(position).getProjectId()) {
                            case ID_HAWTHORN_DWARKA:
                                Intent i2 = new Intent(context, ProductListDwarka.class);
                                i2.putExtra("ProjectId", arrayListProductList.get(position).getProjectId());
                                context.startActivity(i2);
                                break;

                            case ID_AAVAAS_CHANGODER_COMPLEX:
                            case ID_AAVAAS_HYDERABD:
                            case ID_HOLIDAY:
                            case ID_AAVAAS_CHENNAI:
                            case ID_AAVAAS_CHANGODER:
                                Intent i3 = new Intent(context, ProductListHyderabad.class);
                                i3.putExtra("ProjectId", arrayListProductList.get(position).getProjectId());
                                i3.putExtra("ProjectName", arrayListProductList.get(position).getProjectName());
                                context.startActivity(i3);
                                break;
                        }
                    }
                });


                /*new GuideView.Builder(context)
                        .setTitle("Products")
                        .setContentText("You can see project over here")
                        .setGravity(Gravity.center)
                        .setDismissType(DismissType.targetView)
                        .setTargetView(holder.itemView)
                        .setGuideListener(new GuideListener() {
                            @Override
                            public void onDismiss(View view) {
                                switch (arrayListProductList.get(position).getProjectId()) {
                                    case ID_HAWTHORN_DWARKA:
                                        Intent i2 = new Intent(context, ProductListDwarka.class);
                                        i2.putExtra("ProjectId", arrayListProductList.get(position).getProjectId());
                                        context.startActivity(i2);
                                        break;

                                    case ID_AAVAAS_CHANGODER_COMPLEX:
                                    case ID_AAVAAS_HYDERABD:
                                    case ID_HOLIDAY:
                                    case ID_AAVAAS_CHENNAI:
                                    case ID_AAVAAS_CHANGODER:
                                        Intent i3 = new Intent(context, ProductListHyderabad.class);
                                        i3.putExtra("ProjectId", arrayListProductList.get(position).getProjectId());
                                        i3.putExtra("ProjectName", arrayListProductList.get(position).getProjectName());
                                        context.startActivity(i3);
                                        break;
                                }
                            }
                        })
                        .build().show();*/
            }
            walkthroughProduct.edit().putBoolean("walkthrough_first_time_product_ibo", false).apply();
        }


        if (position == 1 && mysale.equalsIgnoreCase("Mysale")) {
            SharedPreferences walkthroughProduct = context.getSharedPreferences(PrefUtils.prefmysale, 0);
            if (walkthroughProduct.getBoolean("walkthrough_first_time_mysale", true)) {

                spotLightSales= new SpotlightView.Builder((Activity) context)
                        .introAnimationDuration(400)
                        .enableRevealAnimation(true)
                        .performClick(true)
                        .fadeinTextDuration(400)
                        .setTypeface(FontUtil.get(context, "fonts/Montserrat-Regular.ttf"))
                        .headingTvColor(Color.parseColor("#eb273f"))
                        .headingTvSize(18)
                        .headingTvText("Love")
                        .subHeadingTvColor(Color.parseColor("#ffffff"))
                        .subHeadingTvSize(14)
                        .headingTvText("My Sales")
                        .subHeadingTvText("Click on the project to view your sales/purchases done in this project.")
                        .maskColor(Color.parseColor("#dc000000"))
                        .target(holder.imageView)
                        .lineAnimDuration(400)
                        .lineAndArcColor(Color.parseColor("#eb273f"))
                        .dismissOnTouch(false)
                        .dismissOnBackPress(false)
                        .enableDismissAfterShown(false)
                        .show();
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (arrayListProductList.get(position).getProjectId()) {
                            case ID_HAWTHORN_DWARKA:
                                Intent i2 = new Intent(context, SalesDwarka.class);
                                i2.putExtra("ProjectId", arrayListProductList.get(position).getProjectId());
                                i2.putExtra("ProjectName", arrayListProductList.get(position).getProjectName());
                                context.startActivity(i2);
                                break;
                            case ID_AAVAAS_CHANGODER:
                            case ID_AAVAAS_CHANGODER_COMPLEX:
                            case ID_AAVAAS_HYDERABD:
                            case ID_AAVAAS_CHENNAI:
                                Intent i1 = new Intent(context, SalesAavaas.class);
                                i1.putExtra("ProjectId", arrayListProductList.get(position).getProjectId());
                                i1.putExtra("ProjectName", arrayListProductList.get(position).getProjectName());
                                context.startActivity(i1);
                                break;

                            case ID_HOLIDAY:
                                Intent i3 = new Intent(context, SalesHoliday.class);
                                i3.putExtra("ProjectId", arrayListProductList.get(position).getProjectId());
                                i3.putExtra("ProjectName", arrayListProductList.get(position).getProjectName());
                                context.startActivity(i3);
                                break;

                            default:
                                Toast.makeText(context, "Coming soon", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
               /* new GuideView.Builder(context)
                        .setTitle("My Sales")
                        .setContentText("You can see Sales over here")
                        .setGravity(Gravity.center)
                        .setDismissType(DismissType.targetView)
                        .setTargetView(holder.itemView)
                        .setGuideListener(new GuideListener() {
                            @Override
                            public void onDismiss(View view) {
                                switch (arrayListProductList.get(position).getProjectId()) {
                                    case ID_HAWTHORN_DWARKA:
                                        Intent i2 = new Intent(context, SalesDwarka.class);
                                        i2.putExtra("ProjectId", arrayListProductList.get(position).getProjectId());
                                        i2.putExtra("ProjectName", arrayListProductList.get(position).getProjectName());
                                        context.startActivity(i2);
                                        break;
                                    case ID_AAVAAS_CHANGODER:
                                    case ID_AAVAAS_CHANGODER_COMPLEX:
                                    case ID_AAVAAS_HYDERABD:
                                        Intent i1 = new Intent(context, SalesAavaas.class);
                                        i1.putExtra("ProjectId", arrayListProductList.get(position).getProjectId());
                                        i1.putExtra("ProjectName", arrayListProductList.get(position).getProjectName());
                                        context.startActivity(i1);
                                        break;

                                    case ID_HOLIDAY:
                                        Intent i3 = new Intent(context, SalesHoliday.class);
                                        i3.putExtra("ProjectId", arrayListProductList.get(position).getProjectId());
                                        i3.putExtra("ProjectName", arrayListProductList.get(position).getProjectName());
                                        context.startActivity(i3);
                                        break;

                                    default:
                                        Toast.makeText(context, "Coming soon", Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        })
                        .build().show();*/
            }
            walkthroughProduct.edit().putBoolean("walkthrough_first_time_mysale", false).apply();
        }
        if (position > lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.item_animation_fall_down);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return arrayListProductList.size();
    }

    public void clearData() {
        // clear the data
        arrayListProductList.clear();
    }


    public void hideSpotLight() {
        spotLightProduct.setVisibility(View.GONE);
        spotLightSales.setVisibility(View.GONE);
    }
}