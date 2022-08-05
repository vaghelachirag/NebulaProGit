package com.nebulacompanies.ibo.customBanner.adapter;

/*
public class MockPagerBannerTextAdapter extends InfinitePagerAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;

    private List<PagerTextItem> mList;
    final String PREFS_WALKTHROUGH_BANNER_TEXT = "bannertextwalkthrough";

    public void setDataList(List<PagerTextItem> list) {
        if (list == null || list.size() == 0)
            throw new IllegalArgumentException("list can not be null or has an empty size");
        this.mList = list;
        this.notifyDataSetChanged();
    }


    public MockPagerBannerTextAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public View getView(final int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(R.layout.card_layout_text, container, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        PagerTextItem item = mList.get(position);
        holder.position = position;
        holder.appName.setText(item.getDesc());
        SharedPreferences walkthroughBannerText = mContext.getSharedPreferences(PREFS_WALKTHROUGH_BANNER_TEXT, 0);
        //holder.description.setText(item.getDesc()+"position:"+position);
        //Picasso.with(mContext).load(item.getImageUrl()).placeholder(R.drawable.nebula_placeholder_land).into(holder.image);
        holder.rlBannerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (walkthroughBannerText.getBoolean("walkthrough_first_time_banner_text", true)) {
                    holder.rlBannerItem.setClickable(false);
                    holder.rlBannerItem.setEnabled(false);
                    walkthroughBannerText.edit().putBoolean("walkthrough_first_time_banner_text", false).apply();
                } else {
                    if (position == 0) {
                        Intent i1 = new Intent(mContext, ViewEvents.class);
                        i1.putExtra("Event_Name", "Nebula AAVAAS Chennai - PreLaunch");
                        mContext.startActivity(i1);
                        ((Activity) mContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    } else if (position == 1) {
                        Intent i1 = new Intent(mContext, ViewSiteProgress.class);
                        i1.putExtra("ProjectId", 5);
                        i1.putExtra("Name", "Aavaas (Changodar), Ahmedabad");
                        i1.putExtra("Month", 1);
                        i1.putExtra("MonthInText", "January");
                        i1.putExtra("Year", "2019");
                        mContext.startActivity(i1);
                        ((Activity) mContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    } else if (position == 2) {
                        Intent i1 = new Intent(mContext, ViewSiteProgress.class);
                        i1.putExtra("ProjectId", 6);
                        i1.putExtra("Name", " Hawthorn Suites, Dwarka");
                        i1.putExtra("Month", 1);
                        i1.putExtra("MonthInText", "January");
                        i1.putExtra("Year", "2019");
                        mContext.startActivity(i1);
                        ((Activity) mContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    } else if (position == 3) {
                        Intent i1 = new Intent(mContext, ViewEvents.class);
                        i1.putExtra("Event_Name", "Nebula AAVAAS Chennai - PreLaunch");
                        mContext.startActivity(i1);
                        ((Activity) mContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    }


                }
            }
        });
        return view;
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    private static class ViewHolder {
        public int position;
        private MyTextView appName;
        private RelativeLayout rlBannerItem;
        private ImageView image;
        private CardView cardView;

        public ViewHolder(View v) {
            appName = (MyTextView) v.findViewById(R.id.item_tv);
            image = (ImageView) v.findViewById(R.id.image);
            rlBannerItem = (RelativeLayout) v.findViewById(R.id.rl_banner_item_text);
            cardView = (CardView) v.findViewById(R.id.card_view);
        }
    }

}*/
