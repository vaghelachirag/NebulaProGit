package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nebulacompanies.ibo.activities.ProductListDwarka;
import com.nebulacompanies.ibo.activities.ProductListHyderabad;
import com.nebulacompanies.ibo.util.Config;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.BonanzaIncome;
import com.nebulacompanies.ibo.activities.BoosterIncome;
import com.nebulacompanies.ibo.activities.CarProgramIncome;
import com.nebulacompanies.ibo.activities.DreamVolume;
import com.nebulacompanies.ibo.activities.GenerationIncome;
import com.nebulacompanies.ibo.activities.GoldIncome;
import com.nebulacompanies.ibo.activities.HolidayAchiever;
import com.nebulacompanies.ibo.activities.LeadershipBonus;
import com.nebulacompanies.ibo.activities.RankBonusIncome;
import com.nebulacompanies.ibo.activities.RetailIncome;
import com.nebulacompanies.ibo.activities.SponsorIncome;
import com.nebulacompanies.ibo.activities.SpotIncome;
import com.nebulacompanies.ibo.activities.StarClubIncome;
import com.nebulacompanies.ibo.activities.SuBoosterIncome;
import com.nebulacompanies.ibo.activities.SuperBoosterIncome;
import com.nebulacompanies.ibo.activities.ThreeStarClubIncome;
import com.nebulacompanies.ibo.model.IncomeListDetails;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.view.MyTextView;
import com.nebulacompanies.ibo.walk.FontUtil;
import com.nebulacompanies.ibo.walk.SpotlightView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.jzvd.AVLoadingIndicatorView;
import me.toptas.fancyshowcase.DismissListener;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;

import static com.nebulacompanies.ibo.util.Constants.ID_AAVAAS_CHANGODER;
import static com.nebulacompanies.ibo.util.Constants.ID_AAVAAS_CHANGODER_COMPLEX;
import static com.nebulacompanies.ibo.util.Constants.ID_AAVAAS_CHENNAI;
import static com.nebulacompanies.ibo.util.Constants.ID_AAVAAS_HYDERABD;
import static com.nebulacompanies.ibo.util.Constants.ID_HAWTHORN_DWARKA;
import static com.nebulacompanies.ibo.util.Constants.ID_HOLIDAY;

/**
 * Created by Palak Mehta on 24-Nov-17.
 */

public class IncomeListAdapter extends BaseAdapter {

    Activity context;
    ArrayList<IncomeListDetails> arrayListIncomeList = new ArrayList<>();
    SpotlightView spotLight;
    String PREFS_WALKTHROUGH_MY_INCOME_LIST = "my_income_list";

    public IncomeListAdapter(Activity context, ArrayList<IncomeListDetails> incomeDetails) {
        this.context = context;
        arrayListIncomeList.clear();
        arrayListIncomeList.addAll(incomeDetails);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return arrayListIncomeList.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arrayListIncomeList.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        MyTextView txtTitle, txtAmt;
        AVLoadingIndicatorView incomAvLoadingIndicatorView;
        LinearLayout incomeinfo;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_income_dashboard, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.dashboard_images);
            holder.txtTitle = (MyTextView) convertView.findViewById(R.id.dashboard_text);
            holder.txtAmt = (MyTextView) convertView.findViewById(R.id.dashboard_amount);
            holder.incomeinfo = (LinearLayout) convertView.findViewById(R.id.income_info);
            holder.incomAvLoadingIndicatorView = (AVLoadingIndicatorView) convertView.findViewById(R.id.aviLoadingPackageIncome);
            //aviLoadingPackageIncome
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position < arrayListIncomeList.size()) {
            holder.incomeinfo.setVisibility(View.VISIBLE);
            holder.incomAvLoadingIndicatorView.setVisibility(View.VISIBLE);
            // holder.imageView.setVisibility(View.GONE);
            holder.txtTitle.setText(arrayListIncomeList.get(position).getIncomeTitle());

            Picasso.with(context)
                    .load(arrayListIncomeList.get(position).getIncomeUrl().replaceAll(" ", "%20"))
                    .into(holder.imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            holder.incomAvLoadingIndicatorView.setVisibility(View.GONE);
                            holder.imageView.setVisibility(View.VISIBLE);


                        }

                        @Override
                        public void onError() {
                            holder.incomAvLoadingIndicatorView.setVisibility(View.GONE);
                        }
                    });
           /* if (position == 0) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                         spotLight = new SpotlightView.Builder((Activity) context)
                                .introAnimationDuration(400)
                                .enableRevealAnimation(true)
                                .performClick(true)
                                .fadeinTextDuration(400)
                                .setTypeface(FontUtil.get(context, "fonts/Montserrat-Regular.ttf"))
                                .headingTvColor(Color.parseColor("#eb273f"))
                                .headingTvSize(32)
                                .headingTvText("Love")
                                .subHeadingTvColor(Color.parseColor("#ffffff"))
                                .subHeadingTvSize(16)
                                .subHeadingTvText("Like the picture?\nLet others know.")
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
                                if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_RETAIL_INCOME) {
                                    Intent intentretailincome = new Intent(context, RetailIncome.class);
                                    context.startActivity(intentretailincome);
                                }
                            }
                        });
                    }
                }, 5000);
            }*/
           /* if (position == 2) {
                SharedPreferences walkthroughProduct = context.getSharedPreferences(PREFS_WALKTHROUGH_MY_INCOME_LIST, 0);
                if (walkthroughProduct.getBoolean("walkthrough_my_income_list_call", true)) {
                    new FancyShowCaseView.Builder(context)
                            .focusOn(holder.incomeinfo)
                            .title("Click to view details.")
                            .focusBorderColor(R.color.nebula_new_light)
                            //.titleSize(20,20)
                            .titleStyle(R.style.TitleBarTextAppearance, Gravity.CENTER)
                            .focusShape(FocusShape.ROUNDED_RECTANGLE)
                            .dismissListener(new DismissListener() {
                                @Override
                                public void onDismiss(String id) {
                                    if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_THREE_STAR_POOL_INCOME) {
                                        Intent tsp = new Intent(context, ThreeStarClubIncome.class);
                                        context.startActivity(tsp);
                                    }
                                }
                                @Override
                                public void onSkipped(String id) {
                                }
                            })
                            .build()
                            .show();
                }
                walkthroughProduct.edit().putBoolean("walkthrough_my_income_list_call", false).apply();
            }*/

            if (!arrayListIncomeList.get(position).getIncomeTitle().equals("Dream Volume")) {
                //holder.txtAmt.setText((context.getResources().getString(R.string.Rs))+" "+ Config.formatter.format(arrayListIncomeList.get(position).getIncomeAmount()));
                holder.txtAmt.setText("" + Config.formatter.format(arrayListIncomeList.get(position).getIncomeAmount()).replace("Rs.", context.getResources().getString(R.string.Rs)).replace(".00", ""));
            } else {
                //holder.txtAmt.setText("" + Config.formatter.format(arrayListIncomeList.get(position).getIncomeAmount()));
                /* holder.txtAmt.setText("" + Config.formatter.format(arrayListIncomeList.get(position).getIncomeAmount()).replace(".00", "").replace("Rs.", ""));*/
                holder.txtAmt.setText("" + Config.formatter.format(arrayListIncomeList.get(position).getIncomeAmount()).replace(".00", "").replace("Rs.", "").replace("\u20B9", ""));
            }
            if (position % 2 == 1) {
                // Set a background color for ListView regular row/item
                convertView.setBackgroundResource(R.color.table_bg_odd);
                holder.incomeinfo.setBackgroundResource(R.drawable.nebula_effect_white);
            } else {
                // Set the background color for alternate row/item
                convertView.setBackgroundResource(R.color.table_bg_even);
                holder.incomeinfo.setBackgroundResource(R.drawable.nebula_effect);
            }


            /*holder.incomeinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_RETAIL_INCOME) {
                        Intent intentretailincome = new Intent(context, RetailIncome.class);
                        context.startActivity(intentretailincome);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_STAR_POOL_INCOME) {
                        Intent intentstarpoolincome = new Intent(context, StarClubIncome.class);
                        context.startActivity(intentstarpoolincome);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_THREE_STAR_POOL_INCOME) {
                        Intent tsp = new Intent(context, ThreeStarClubIncome.class);
                        context.startActivity(tsp);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_GENERATION_INCOME) {
                        Intent g = new Intent(context, GenerationIncome.class);
                        context.startActivity(g);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_GOLD_INCOME) {
                        Intent gd = new Intent(context, GoldIncome.class);
                        context.startActivity(gd);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_SPOT_INCOME) {
                        Intent sv = new Intent(context, SpotIncome.class);
                        context.startActivity(sv);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_BOOSTER_INCOME) {
                        Intent bi = new Intent(context, BoosterIncome.class);
                        context.startActivity(bi);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_SUPER_BOOSTER_INCOME) {
                        Intent sbi = new Intent(context, SuperBoosterIncome.class);
                        context.startActivity(sbi);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_SU_BOOSTER_INCOME) {
                        Intent intentsuboosterincome = new Intent(context, SuBoosterIncome.class);
                        context.startActivity(intentsuboosterincome);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_CAR_PROGRAM_INCOME) {
                        Intent intentcarincome = new Intent(context, CarProgramIncome.class);
                        context.startActivity(intentcarincome);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_DREAM_Volume_INCOME) {
                        Intent dv = new Intent(context, DreamVolume.class);
                        dv.putExtra("IncomeAmount", arrayListIncomeList.get(position).getIncomeAmount());
                        context.startActivity(dv);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_LEADERSHIP_BONUS_INCOME) {
                        Intent ldsb = new Intent(context, LeadershipBonus.class);
                        context.startActivity(ldsb);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_HOLIDAY_ACHIEVER) {
                        Intent dn = new Intent(context, HolidayAchiever.class);
                        dn.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(dn);
                    }
                   *//* else if (arrayListIncomeList.get(position).getIncomeId() == 14)
                    {
                        Intent vb = new Intent(context, VacationBooster.class);
                        context.startActivity(vb);
                    }*//*
                    else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_HOLIDAY_MINI_BONANZA) {
                        Intent dn = new Intent(context, BonanzaIncome.class);
                        dn.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(dn);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_HOLIDAY_BONANZA) {
                        Intent dn = new Intent(context, BonanzaIncome.class);
                        dn.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(dn);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_RANK_BONUS) {
                        Intent dn = new Intent(context, RankBonusIncome.class);
                        dn.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(dn);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_SPONSOR_INCOME) {
                        Intent dn = new Intent(context, SponsorIncome.class);
                        dn.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(dn);
                    }
                }
            });*/


            holder.incomeinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_RETAIL_INCOME) {
                        Intent intentretailincome = new Intent(context, RetailIncome.class);
                        intentretailincome.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(intentretailincome);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_STAR_POOL_INCOME) {
                        Intent intentstarpoolincome = new Intent(context, StarClubIncome.class);
                        intentstarpoolincome.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(intentstarpoolincome);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_THREE_STAR_POOL_INCOME) {
                        Intent tsp = new Intent(context, ThreeStarClubIncome.class);
                        tsp.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(tsp);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_GENERATION_INCOME) {
                        Intent g = new Intent(context, GenerationIncome.class);
                        g.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(g);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_GOLD_INCOME) {
                        Intent gd = new Intent(context, GoldIncome.class);
                        gd.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(gd);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_SPOT_INCOME) {
                        Intent sv = new Intent(context, SpotIncome.class);
                        sv.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(sv);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_BOOSTER_INCOME) {
                        Intent bi = new Intent(context, BoosterIncome.class);
                        bi.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(bi);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_SUPER_BOOSTER_INCOME) {
                        Intent sbi = new Intent(context, SuperBoosterIncome.class);
                        sbi.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(sbi);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_SU_BOOSTER_INCOME) {
                        Intent intentsuboosterincome = new Intent(context, SuBoosterIncome.class);
                        intentsuboosterincome.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(intentsuboosterincome);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_CAR_PROGRAM_INCOME) {
                        Intent intentcarincome = new Intent(context, CarProgramIncome.class);
                        intentcarincome.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(intentcarincome);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_DREAM_Volume_INCOME) {
                        Intent dv = new Intent(context, DreamVolume.class);
                        dv.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        dv.putExtra("IncomeAmount", arrayListIncomeList.get(position).getIncomeAmount());
                        context.startActivity(dv);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_LEADERSHIP_BONUS_INCOME) {
                        Intent ldsb = new Intent(context, LeadershipBonus.class);
                        ldsb.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(ldsb);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_HOLIDAY_ACHIEVER) {
                        Intent dn = new Intent(context, HolidayAchiever.class);
                        dn.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(dn);
                    }
       /* else if (arrayListIncomeList.get(position).getIncomeId() == 14)
        {
            Intent vb = new Intent(context, VacationBooster.class);
            context.startActivity(vb);
        }*/
                    else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_HOLIDAY_MINI_BONANZA) {
                        Intent dn = new Intent(context, BonanzaIncome.class);
                        dn.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(dn);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_HOLIDAY_BONANZA) {
                        Intent dn = new Intent(context, BonanzaIncome.class);
                        dn.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(dn);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_RANK_BONUS) {
                        Intent dn = new Intent(context, RankBonusIncome.class);
                        dn.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(dn);
                    } else if (arrayListIncomeList.get(position).getIncomeId() == Constants.ID_SPONSOR_INCOME) {
                        Intent dn = new Intent(context, SponsorIncome.class);
                        dn.putExtra("IncomeName", arrayListIncomeList.get(position).getIncomeTitle());
                        context.startActivity(dn);
                    }
                }
            });
        }

        return convertView;
    }


    public void clearData() {
        arrayListIncomeList.clear();
    }
}
