package com.nebulacompanies.ibo.ecom.adapter;

/*public class CategoryDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    // private RealmResults<Product> products;
    private ProductsAdapterListener listener;
    // private RealmResults<CartItem> cartItems;
    List<SubCategoryProductList> mArrayList = new ArrayList<>();
    private static int countItem = 0;
    boolean clicked = false;
    private AdapterCallback mAdapterCallback;

    private APIInterface mAPIInterface;
    //String m_deviceId;
    String m_deviceId;
    String uniqueID;
    Session session;
    private boolean isLoadingAdded = false;
    ArrayList<String> imagepic = new ArrayList<String>();
    int categoryIDOne, categoryIDTwo;
    boolean mrpisAssociate,mrpisFirstPurchase,mrpisJoined30Days;
    SharedPreferences citySave;
    int cityId;
    SharedPreferences spGetIsAssociate,spGetIsFirstPurchase,spGetIsJoin;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        MyTextViewEcom name;

        @BindView(R.id.tv_offer_price_value)
        MyTextViewEcom tvOfferPrice;

        @BindView(R.id.tv_original_price)
        MyTextViewEcom tvOriginalPriceSign;

        @BindView(R.id.tv_original_price_value)
        MyTextViewEcom tvOriginalPrice;

        @BindView(R.id.tv_price_discount)
        MyTextViewEcom tvDiscountPrice;

        @BindView(R.id.tv_pv_value)
        MyTextViewEcom tvPVValue;

        @BindView(R.id.tv_nv_value)
        MyTextViewEcom tvNVValue;

        @BindView(R.id.tv_bv_value)
        MyTextViewEcom tvBVValue;


        @BindView(R.id.product_count)
        MyTextViewEcom tvCount;

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.ic_add)
        ImageView icAdd;

        @BindView(R.id.ic_remove)
        ImageView icRemove;

        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        @BindView(R.id.rlContent)
        RelativeLayout rlContent;

       *//* @BindView(R.id.product_count)
        MyTextView lblQuantity;*//*

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.btn_add_to_cart)
        MyButtonEcom btnAddToCart;




        public MyViewHolder(View view) {
            super( view );
            ButterKnife.bind( this, view );
        }
    }


    public class MyViewHolderTopBanner extends RecyclerView.ViewHolder {

        @BindView(R.id.name_three)
        MyTextViewEcom tvCategoryHome;

        @BindView(R.id.name_four)
        MyTextViewEcom tvCategoryKitchen;


        @BindView(R.id.tv_sort)
        MyTextViewEcom tvSort;

        @BindView(R.id.view_one)
        View viewOne;

        @BindView(R.id.view_two)
        View viewTwo;


        public MyViewHolderTopBanner(View view) {
            super( view );
            ButterKnife.bind( this, view );

            citySave = context.getSharedPreferences(PrefUtils.prefCityid, 0);
            cityId = citySave.getInt(PrefUtils.prefCityid, 0); //0 is the default value

            if (categoryIDOne == 2) {
                viewOne.setVisibility( View.VISIBLE );
                viewTwo.setVisibility( View.GONE );
            } else if (categoryIDTwo == 3) {
                viewTwo.setVisibility( View.VISIBLE );
                viewOne.setVisibility( View.GONE );
            }


            tvCategoryHome.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MyCategoryActivity) context).selectCategoryID != 2) {
                        ((MyCategoryActivity) context).loadFirstPage( 2 ,cityId);
                        viewOne.setVisibility( View.VISIBLE );
                        viewTwo.setVisibility( View.GONE );
                    }

                }
            } );


            tvCategoryKitchen.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MyCategoryActivity) context).selectCategoryID != 3) {
                        ((MyCategoryActivity) context).loadFirstPage( 3 ,cityId);
                        viewTwo.setVisibility( View.VISIBLE );
                        viewOne.setVisibility( View.GONE );
                    }
                }
            } );


            tvSort.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MyCategoryActivity) context).alertBoxSort();
                }
            } );
        }
    }


    public CategoryDetailsAdapter(Context context, ArrayList<SubCategoryProductList> mArrayList, MyCategoryActivity callback,
                                  ArrayList<String> imagepic, Integer categoryIDOne, Integer categoryIDTwo) {
        //public ProductsAdapter(Context context, ArrayList<Product> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
        this.listener = listener;
        this.mAdapterCallback = callback;
        this.imagepic = imagepic;
        this.categoryIDOne = categoryIDOne;
        this.categoryIDTwo = categoryIDTwo;
        mAPIInterface = APIClient.getClient( context ).create( APIInterface.class );
        // session = new Session(context);
      *//*  TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService( TELEPHONY_SERVICE );
        m_deviceId = TelephonyMgr.getDeviceId();*//*

        m_deviceId = android.provider.Settings.Secure.getString(
                context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        session = new Session( context );
        SharedPreferences deviceSharedPreferences = context.getSharedPreferences( PrefUtils.prefDeviceid, 0 );
        uniqueID = deviceSharedPreferences.getString( PrefUtils.prefDeviceid, null );

        Log.d( "MDEVICEID product", "MDEVICEID product uniqueID: " + uniqueID );
        if (m_deviceId == null || m_deviceId.equals( "" )) {

            if (uniqueID == null || uniqueID.equals( "" )) {
                String randomID = UUID.randomUUID().toString();

                SharedPreferences preferences = context.getSharedPreferences( PrefUtils.prefDeviceid, Context.MODE_PRIVATE );
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString( PrefUtils.prefDeviceid, randomID );
                editor.apply();
                m_deviceId = randomID;
            } else {
                m_deviceId = uniqueID;
            }
        }
        Log.d( "MDEVICEID product", "MDEVICEID product: " + m_deviceId );
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == 0) {
            View itemView = LayoutInflater.from( parent.getContext() )
                    .inflate( R.layout.content_category_top, parent, false );
            return new MyViewHolderTopBanner( itemView );
        } else {
            View itemView = LayoutInflater.from( parent.getContext() )
                    .inflate( R.layout.product_list_item, parent, false );
            return new MyViewHolder( itemView );
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            SubCategoryProductList product = mArrayList.get( position - 1 );
            if (product.getCategoryCategoryId()== null) {
                myViewHolder.progressBar.setVisibility(View.VISIBLE);
                myViewHolder.rlContent.setVisibility(View.GONE);
            } else {
                myViewHolder.progressBar.setVisibility(View.GONE);
                myViewHolder.rlContent.setVisibility(View.VISIBLE);

                spGetIsAssociate = context.getSharedPreferences( PrefUtils.prefMrp, 0 );
                mrpisAssociate = spGetIsAssociate.getBoolean( PrefUtils.prefMrp, false );

                spGetIsFirstPurchase = context.getSharedPreferences( PrefUtils.prefFirstpurchase, 0 );
                mrpisJoined30Days = spGetIsFirstPurchase.getBoolean( PrefUtils.prefFirstpurchase, false );


                if (mrpisAssociate == true ){
                    myViewHolder.tvOfferPrice.setText( String.valueOf( product.getCategoryMRP() ) );
                    myViewHolder.tvOriginalPrice.setVisibility(View.GONE);
                    myViewHolder.tvDiscountPrice.setVisibility(View.GONE);
                    myViewHolder.tvOriginalPriceSign.setVisibility(View.GONE);

                }else {
                    myViewHolder.tvOfferPrice.setText( String.valueOf( product.getCategorySalePrice() ) );
                    // DecimalFormat decimalFormat = new DecimalFormat("0.##");
                    myViewHolder.tvOriginalPrice.setText( String.valueOf( product.getCategoryMRP() ) );
                    myViewHolder.tvDiscountPrice.setText( product.getCategorySaving() );
                    myViewHolder.tvOriginalPriceSign.setVisibility(View.VISIBLE);
                }
                myViewHolder.name.setText( product.getCategoryName() );
                //myViewHolder.name.setText( product.getCategoryCategoryName() );
                myViewHolder.tvPVValue.setText( "" + product.getCategoryPV() );
                myViewHolder.tvBVValue.setText( "" + product.getCategoryBV() );
                myViewHolder.tvNVValue.setText( "" + product.getCategoryNV() );

                Glide.with( context )
                        .load( product.getCategoryMainImage() ).fitCenter()
                        .placeholder( getRandomDrawbleColor())
                        .into( myViewHolder.thumbnail );

                myViewHolder.btnAddToCart.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clicked = true;

                        //Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show(); getCategoryProductID()
                        addToCart( m_deviceId, session.getIboKeyId(), product.getCategoryProductId(), 1, "plus" );

                        Log.d( "session.getIboKeyId()", "session.getIboKeyId() " + session.getIboKeyId() );

                    }
                } );

                myViewHolder.cardView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent( context, ProductDescription.class );
                        if (clicked) {
                            intent.putExtra( "product_clicked", "1" );
                        } else {
                            intent.putExtra( "product_clicked", "0" );
                        }
                        intent.putExtra( "product_id", product.getCategoryProductId() );
                        intent.putExtra( "product_name", product.getCategoryName() );
                        intent.putExtra( "product_offer_price", String.valueOf( product.getCategorySalePrice() ) );
                        intent.putExtra( "product_mrp_price", String.valueOf( product.getCategoryMRP() ) );
                        intent.putExtra( "product_desc", product.getCategoryDescription() );
                        intent.putExtra( "product_saving", product.getCategorySaving() );
                        intent.putExtra( "product_return", product.getCategoryReturnPolicy() );
                        intent.putExtra( "product_warranty", product.getCategoryWarranty() );
                        intent.putExtra( "product_quantity", product.getCategoryQuantity() );
                        intent.putExtra( "product_MaxSaleQuantity", product.getCategoryMaxSaleQuantity() );
                        intent.putExtra( "product_short_dec", product.getCategoryShortDescription() );

                        context.startActivity( intent );
                    }
                } );
            }

        } else {

        }

    }

    @Override
    public int getItemViewType(int position) {
        //   return super.getItemViewType( position );
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }
*//*
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }*//*


    @Override
    public int getItemCount() {
        //return products.size();
        return mArrayList.size() + 1;
    }


    public interface ProductsAdapterListener {
        void onProductAddedCart(int index, Product product);

        void onProductRemovedFromCart(int index, Product product);
    }

    private void thisWasClicked(int position) {

        if (position == 0) {
            countItem++;
        }

    }

    private void addToCart(String deviceId, String userId, Integer productId, Integer quantity, String CartFlag) {
      *//*  final ProgressDialog mProgressDialog = new ProgressDialog( context, R.style.MyTheme );
        mProgressDialog.show();

        mProgressDialog.setCancelable( false );
        mProgressDialog.setContentView( R.layout.progressdialog_ecom );
        mProgressDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );*//*

        Call<AddToCartModel> wsCallingRegistation = mAPIInterface.getAddToCartModelCall( deviceId, userId, productId, quantity, CartFlag );
        wsCallingRegistation.enqueue( new Callback<AddToCartModel>() {
            @Override
            public void onResponse(Call<AddToCartModel> call, Response<AddToCartModel> response) {
               *//* if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }*//*
               *//* Toast.makeText(context,"Start :",Toast.LENGTH_SHORT).show();
                Toast.makeText(context,"Response: "+response.body().getStatusCode(),Toast.LENGTH_SHORT).show();*//*


                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                        // Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        //confirmDailogue();

                        Toast toast = Toast.makeText( context, "item added to cart", Toast.LENGTH_SHORT );
                        toast.setGravity( Gravity.CENTER, 0, 0 );
                        toast.show();

                        Log.d( "Add to Cart res", "Add to Cart res" + response );
                        mAdapterCallback.onMethodCallbackMain();
                        //Toast.makeText(context,"Response: "+response.body().getStatusCode(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddToCartModel> call, Throwable t) {
                //mProgressDialog.dismiss();
                Log.d( "Add to Cart error", "Add to Cart error " + t );
                Toast.makeText( context, "Error: " + t, Toast.LENGTH_SHORT ).show();
            }
        } );
    }


    public void setItems(List<SubCategoryProductList> items) {
        mArrayList = items;
        notifyDataSetChanged();
    }

    public void clearData() {
        // clear the data
        mArrayList.clear();
    }

    public void add(SubCategoryProductList r) {
        mArrayList.add( r );
        notifyItemInserted( mArrayList.size() );
        // notifyItemInserted(Items.size());
    }

    public void addAll(List<SubCategoryProductList> moveResults) {
       *//* for (SubCategoryProductList result : moveResults) {
            if (!moveResults.contains( result.getCategoryMainImage() )) {
                add( result );
            }
            // add(result);
        }*//*
       mArrayList.addAll( moveResults );
       notifyDataSetChanged();
    }

    public void remove(SubCategoryProductList r) {
        int position = mArrayList.indexOf( r );
        if (position > -1) {
            mArrayList.remove( position );
            notifyItemRemoved( position + 1 );
        }
    }

    public void clear() {
        isLoadingAdded = false;
        mArrayList.clear();
        *//*while (getItemCount() > 0) {
            remove(getItem(0));
        }*//*
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add( new SubCategoryProductList() );
    }


    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mArrayList.size();
        SubCategoryProductList result = getItem( position - 1 );

        if (result != null) {
            mArrayList.remove( position - 1 );
            notifyItemRemoved( position );
        }
    }


    public SubCategoryProductList getItem(int position) {
        return mArrayList.get( position );
    }




    private ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#9ACCCD")), new ColorDrawable(Color.parseColor("#8FD8A0")),
                    new ColorDrawable(Color.parseColor("#CBD890")), new ColorDrawable(Color.parseColor("#DACC8F")),
                    new ColorDrawable(Color.parseColor("#D9A790")), new ColorDrawable(Color.parseColor("#D18FD9")),
                    new ColorDrawable(Color.parseColor("#FF6772")), new ColorDrawable(Color.parseColor("#DDFB5C"))
            };


    public ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

}*/
