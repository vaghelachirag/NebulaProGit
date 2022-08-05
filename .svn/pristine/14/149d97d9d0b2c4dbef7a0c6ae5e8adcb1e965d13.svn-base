package com.nebulacompanies.ibo.ecom;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.CustomerSupportAdapter;
import com.nebulacompanies.ibo.ecom.model.CreateTicketModel;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportCategory;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportOrder;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportTicket;
import com.nebulacompanies.ibo.ecom.utils.MyBoldTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.MyButtonEcom;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
//import static com.nebulacompanies.ibo.util.NetworkChangeReceiver.Utils.isNetworkAvailable(getApplicationContext());

public class CustomerSupport extends AppCompatActivity {

    int IMAGE_PICKER_SELECT = 101;
    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface;
    Session session;
    Toolbar toolbar;
    LinearLayout lyOrder;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ImageView imgBackArrow, imgSortClose, imgTicketClose;
    MyTextViewEcom tvToolbarTitle, tvToolbarSort, tvNoTickets, tvWordCount;
    MyButtonEcom tvFile;
    MyBoldTextViewEcom tvTicket;
    Spinner spinCategory, spinOrder, spStatus, spCategorySort;
    MyButtonEcom btnCreateTicket;
    private RecyclerView recyclerView;
    private CustomerSupportAdapter customerSupportAdapter;
    ArrayList<CustomerSupportCategory.SupportList> mListCategory = new ArrayList<>();
    ArrayList<String> mListCategoryName = new ArrayList<>();
    ArrayList<String> mListOrderNameDate = new ArrayList<>();
    ArrayList<String> mListOrderName = new ArrayList<>();
    ArrayList<String> mListCategoryNameSort = new ArrayList<>();
    ArrayList<String> statusList = new ArrayList<>();
    ArrayList<CustomerSupportTicket.Datum> mListTicketsOriginal = new ArrayList<>();
    ArrayList<CustomerSupportTicket.Datum> mListTickets = new ArrayList<>();
    AlertDialog dialogSortBy;
    String userID;
    MyButtonEcom btnApplySort;
    int selSortCat = 0;
    int selSortStatus = 0;
    int posCategorySelected = 0;
    int posOrderIdSelected = 0;
    String strComment = "", strSubject = "", selCategoryName = "", strFileName = "";
    NebulaEditTextEcom editComment, editSubject, tvFileName;
    CardView cardOrderId;
    ImageView fileclose;
    int selCatID, backID;
    String selOrderNumber = "";
    Utils utils = new Utils();
    File selectedFile;
    Uri selectedMediaUri;
    Intent ticketNotification;
    boolean showOrder = false;
    String selTicketNumber = "";
    MyTextViewEcom txtCategory, txtOrder, txtselcategory, txtselorderid;
    String catname = "";
    String cattitle = "My Order";//"Your Orders";
    int selcat = 0;
    MyTextViewEcom  txtrequiredcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_support);
        Utils.darkenStatusBar(this, R.color.colorNotification);

        ticketNotification = getIntent();

        if (ticketNotification != null) {
            backID = ticketNotification.getIntExtra("session_back", 0);
            int dispOrder = ticketNotification.getIntExtra("showorder", 0);
            selcat = ticketNotification.getIntExtra("cat_sel", 0);
           /* if (ticketNotification.hasExtra("cat"))
                catname = ticketNotification.getStringExtra("cat");*/
            selTicketNumber = ticketNotification.getStringExtra("ticketid"); //"Order_637490001589298448";//
            Log.d("order position", selTicketNumber + " : ");
            showOrder = dispOrder == 1;
        }
        initData();
        initUI();
        initDialogSortBy();
        initOnClick();
        getSupportCategoryList();
        initTicketList();
        callAPISet();
        if (showOrder) {
            txtselcategory.setText("Ticket Category");
            txtselorderid.setText("OrderId");
        }

    }

    private void initTicketList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customerSupportAdapter = new CustomerSupportAdapter(this, mListTickets);
        recyclerView.setAdapter(customerSupportAdapter);
    }

    private void initData() {
        session = new Session(this);
        userID = session.getIboKeyId();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
    }
    void initUI() {
        mProgressDialog = findViewById(R.id.progresbar);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        toolbar = findViewById(R.id.toolbar_search);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("Customer Support");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        tvToolbarSort = findViewById(R.id.toolbar_sort);

        mProgressDialog = findViewById(R.id.progresbar);
        imgBackArrow = findViewById(R.id.img_back);

        tvTicket = findViewById(R.id.tv_ticket);
        recyclerView = findViewById(R.id.recycler_view);
        tvNoTickets = findViewById(R.id.tv_no_ticket);
        txtCategory = findViewById(R.id.textcategory);
        txtOrder = findViewById(R.id.textorderid);
        txtselcategory = findViewById(R.id.textselectcategory);
        txtselorderid = findViewById(R.id.textselectorderid);
        mProgressDialog.setVisibility(View.GONE);
       // txtrequiredorder= findViewById(R.id.textselectorderid);
        txtrequiredcategory= findViewById(R.id.txtrequiredcategory);
        //No Records Found
        /*llEmpty = findViewById(R.id.llEmpty);
        imgError = findViewById(R.id.imgError);
        txtErrorTitle = findViewById(R.id.txtErrorTitle);
        txtErrorContent = findViewById(R.id.txt_error_content);
        txtRetry = findViewById(R.id.txtRetry);*/

        lyOrder = findViewById(R.id.ly_order);
        spinCategory = findViewById(R.id.spnCategory);
        spinOrder = findViewById(R.id.spnOrderID);
        imgTicketClose = findViewById(R.id.img_ticket_close);
        tvFile = findViewById(R.id.tv_file);
        tvFileName = findViewById(R.id.tv_file_name);
        btnCreateTicket = findViewById(R.id.btn_create_ticket);
        editComment = findViewById(R.id.simpleEditTextComment);
        editSubject = findViewById(R.id.simpleEditTextSubject);
        tvWordCount = findViewById(R.id.tv_word_count);
        cardOrderId = findViewById(R.id.layOrderID);
        fileclose = findViewById(R.id.file_close);
        fileclose.setVisibility(View.GONE);

        editComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int length = editComment.getText().toString().trim().length();
                String convert = String.valueOf(length);
                tvWordCount.setText("(" + convert + "/" + "250" + ")");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tvFile.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            startActivityForResult(pickIntent, IMAGE_PICKER_SELECT);
        });

        btnCreateTicket.setOnClickListener(v ->
                {
                    editComment.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    try {
                        if (validate1()) {
                            submitTicket();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );

        fileclose.setOnClickListener(v -> clearFileData());
        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    posCategorySelected = position;
                    selCategoryName = mListCategoryName.get(posCategorySelected);
                    if (posCategorySelected > 0)
                        selCatID = mListCategory.get(posCategorySelected - 1).getCategoryId();
                    Log.d("select:", "item sel: " + position + " : " + mListCategoryName.get(posCategorySelected));
                   /* TextView errorText = (TextView) (spinOrder.getSelectedView().findViewById(android.R.id.text1));//(TextView) spinOrder.getSelectedView();
                    errorText.setError(null);*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posOrderIdSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void setOrderSpinner() {
        boolean show = selCategoryName.equalsIgnoreCase(cattitle);
        Log.d("order==", selTicketNumber + " : ticket");
        selOrderNumber = selTicketNumber;
        Log.d("order==", selOrderNumber + " : selOrderNumber");

        lyOrder.setVisibility(show ? View.VISIBLE : View.GONE);
        spinOrder.setSelection(selOrderPos, true);
        spinOrder.setEnabled(false);
        spinOrder.setVisibility(View.GONE);
        txtOrder.setVisibility(View.VISIBLE);
        txtOrder.setText(mListOrderNameDate.get(selOrderPos));
    }

    void initOnClick() {
        imgBackArrow.setOnClickListener(view -> onBackPressed());
        tvToolbarSort.setOnClickListener(v -> dialogSortBy.show());

        mSwipeRefreshLayout.setOnRefreshListener(
                this::refreshContent
        );
    }

    public void initDialogSortBy() {

        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_sort_customer_support, null);
        builder.setView(customLayout);

        spStatus = customLayout.findViewById(R.id.spinner_status);
        spCategorySort = customLayout.findViewById(R.id.spinner_category);
        imgSortClose = customLayout.findViewById(R.id.img_sort_close);
        btnApplySort = customLayout.findViewById(R.id.btn_apply);

        initSpinnerSort();
        // create and show// the alert dialog
        dialogSortBy = builder.create();
        dialogSortBy.setCancelable(false);

        imgSortClose.setOnClickListener(v -> dialogSortBy.dismiss());
        btnApplySort.setOnClickListener(v -> {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                selSortCat = spCategorySort.getSelectedItemPosition();
                selSortStatus = spStatus.getSelectedItemPosition();
                filterApplyList();
            } else {
                utils.dialogueNoInternet(this);
            }
        });
    }

    private void filterApplyList() {
        mListTickets.clear();
        if (selSortCat == 0 && selSortStatus == 0) {
            mListTickets.addAll(mListTicketsOriginal);
        } else {
            String sortcatName = mListCategoryNameSort.get(selSortCat);
            String sortStatus = statusList.get(selSortStatus);

            for (int i = 0; i < mListTicketsOriginal.size(); i++) {
                String status = mListTicketsOriginal.get(i).getStatus();
                String category = mListTicketsOriginal.get(i).getTicketCategoryName();
                if ((selSortCat == 0 || category.equalsIgnoreCase(sortcatName))
                        && (selSortStatus == 0 || status.equalsIgnoreCase(sortStatus))) {
                    mListTickets.add(mListTicketsOriginal.get(i));
                }
            }
        }
        customerSupportAdapter.notifyDataSetChanged();
        dialogSortBy.dismiss();
        if (mListTickets.size() == 0)
            noRecordsFound();
        else
            changeVisibility(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Result == ", "file Received");
        if (resultCode == RESULT_OK) {

            selectedMediaUri = data.getData();

            Uri selectedImageUri = data.getData();

            // OI FILE Manager
            //String filemanagerstring = selectedImageUri.getPath();

            // MEDIA GALLERY
            String selectedImagePath = getPath(selectedImageUri);
            fileclose.setVisibility(View.VISIBLE);

            selectedFile = new File(selectedImagePath);
            Log.d("Result == ", "Result ok" + getMimeType(selectedMediaUri));
            strFileName = selectedFile.getName();
            tvFileName.setText(selectedFile.getName());
            /*if (selectedMediaUri.toString().contains("image")) {
                //handle image
                tvFileName.setText(selectedFile.getName());
            } else  if (selectedMediaUri.toString().contains("video")) {
                //handle video
                tvFileName.setText(selectedFile.getName());
            } else{
                tvFileName.setText("No file is chosen");
            }*/
        } else {
            clearFileData();
        }
    }


    // UPDATED!
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    private void initCategorySpinner() {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_ticket_item_ecom, mListCategoryName);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_ticket_item_ecom);

        // attaching data adapter to spinner
        spinCategory.setAdapter(dataAdapter);
    }

    private void initOrderIdSpinner() {
        // Creating adapter for spinner
        ArrayAdapter<String> dataIdAdapter = new ArrayAdapter<>(this, R.layout.spinner_ticket_item_ecom, mListOrderNameDate);

        // Drop down layout style - list view with radio button
        dataIdAdapter.setDropDownViewResource(R.layout.spinner_ticket_item_ecom);

        // attaching data adapter to spinner
        spinOrder.setAdapter(dataIdAdapter);
    }

    private boolean validate1() {
        boolean ret1 = true;
        strComment = Objects.requireNonNull(editComment.getText()).toString().trim();
        strSubject = Objects.requireNonNull(editSubject.getText()).toString().trim();


        Log.d("file", "file" + strFileName);
        if (posCategorySelected > 0) {
            if (selCategoryName.equalsIgnoreCase("profile changes")) {
                Log.d("CategoryName", "categoryname" + selCategoryName);
                if (TextUtils.isEmpty(strSubject)) {
                    editSubject.setError(getResources().getString(R.string.error_subject));
                    ret1 = false;
                }
                if (TextUtils.isEmpty(strComment)) {
                    editComment.setError(getResources().getString(R.string.error_comment));
                    ret1 = false;
                }
               /* if(TextUtils.isEmpty(strFileName)){
                    tvFileName.setError("Please Upload Documents");
                    ret1 = false;
                }*/
            } else {
                Log.d("CategoryName1", "categoryname" + selCategoryName);
                if (TextUtils.isEmpty(strSubject)) {
                    editSubject.setError(getResources().getString(R.string.error_subject));
                    ret1 = false;
                }
                if (TextUtils.isEmpty(strComment)) {
                    editComment.setError(getResources().getString(R.string.error_comment));
                    ret1 = false;
                }
                if (TextUtils.isEmpty(strFileName)) {
                    tvFileName.setError(null);
                }

            }
        } else {
            TextView errorText = (TextView) (spinCategory.getSelectedView().findViewById(android.R.id.text1));// spinCategory.getSelectedItem();
            errorText.setError(getResources().getString(R.string.error_selectcategory));
            ret1 = false;
        }
        return ret1;
    }


    private boolean validate() {
        strComment = Objects.requireNonNull(editComment.getText()).toString().trim();
        strSubject = Objects.requireNonNull(editSubject.getText()).toString().trim();


        if (posCategorySelected > 0) {
            if (selCategoryName.equalsIgnoreCase(cattitle)) {
                if (posOrderIdSelected > 0) {

                    if (!TextUtils.isEmpty(strComment))
                        return true;
                    else {
                        editComment.requestFocus();
                        editComment.setError(getResources().getString(R.string.error_comment));
                    }

                } else {
                    TextView errorText = (TextView) (spinOrder.getSelectedView().findViewById(android.R.id.text1));//(TextView) spinOrder.getSelectedView();
                    errorText.setError(getResources().getString(R.string.error_selectorderid));
                    // errorText.setTextColor(Color.RED);
                }
            } else {
                if (!TextUtils.isEmpty(strComment))
                    return true;
                else {
                    editComment.requestFocus();
                    editComment.setError(getResources().getString(R.string.error_comment));
                }
            }
        } else {
            TextView errorText = (TextView) (spinCategory.getSelectedView().findViewById(android.R.id.text1));// spinCategory.getSelectedItem();
            errorText.setError(getResources().getString(R.string.error_selectcategory));
            // errorText.setTextColor(Color.RED);
        }
        return false;
    }

    private void initSpinnerSort() {
        // Spinner Drop down elements
        statusList.clear();
        statusList.add("All");
        statusList.add("Open");
        statusList.add("Closed");

        // Creating adapter for spinner
        ArrayAdapter<String> dataStatusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);

        // Drop down layout style - list view with radio button
        dataStatusAdapter.setDropDownViewResource(R.layout.spinner_textview_item_ecom);

        // attaching data adapter to spinner
        spStatus.setAdapter(dataStatusAdapter);
    }

    private void initCategorySort() {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mListCategoryNameSort);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_textview_item_ecom);

        // attaching data adapter to spinner
        spCategorySort.setAdapter(dataAdapter);
    }

    private void refreshContent() {
        mSwipeRefreshLayout.setRefreshing(true);
        callAPISet();
    }

    private void callAPISet() {
        getSupportTicketList();
    }

    private void getSupportCategoryList() {
        clearCategory();
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Call<CustomerSupportCategory> wsCallingEvents = mAPIInterface.getSupportCategoryList();
            wsCallingEvents.enqueue(new Callback<CustomerSupportCategory>() {
                @Override
                public void onResponse(Call<CustomerSupportCategory> call, Response<CustomerSupportCategory> response) {
                    int statuscode = 0;
                    int selpos = 0;
                    int selid = 0;
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            assert response.body() != null;
                            CustomerSupportCategory mData = response.body();
                            statuscode = mData.getStatusCode();
                            if (statuscode == REQUEST_STATUS_CODE_NO_RECORDS) {

                            } else if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                clearCategory();

                                ArrayList<CustomerSupportCategory.SupportList> mListCategoryOrig = new ArrayList<>(mData.getData());

                                for (int i = 0; i < mListCategoryOrig.size(); i++) {
                                    String catename = mListCategoryOrig.get(i).getCategoryName();

                                    if (showOrder) {
                                        if (catename.equalsIgnoreCase(cattitle)) {
                                            mListCategory.add(mListCategoryOrig.get(i));
                                            mListCategoryName.add(catename);
                                            mListCategoryNameSort.add(catename);
                                        }
                                    } else {
                                      /*  if (!catename.equalsIgnoreCase(cattitle) &&
                                                !catename.equalsIgnoreCase("my address")) {*/
                                        mListCategory.add(mListCategoryOrig.get(i));
                                        mListCategoryName.add(catename);
                                        mListCategoryNameSort.add(catename);
                                       /* }
                                    }*/
                                        if (selcat == mListCategoryOrig.get(i).getCategoryId()) {
                                            selpos = i;
                                        }
                                    }
                                }
                               /* if (showOrder) {
                                    spinCategory.setSelection(1, true);
                                    //  spinOrder.setSelection(selOrderPos, true);
                                }*/
                            }
                        }
                    }
                    setCategoryListData();
                    if (showOrder && statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                        spinCategory.setSelection(1, true);
                        spinCategory.setEnabled(false);
                        spinCategory.setVisibility(View.GONE);
                        txtrequiredcategory.setVisibility(View.GONE);
                        txtCategory.setVisibility(View.VISIBLE);
                        txtCategory.setText(mListCategoryName.get(1));
                        //  spinOrder.setSelection(selOrderPos, true);
                    }
                    else if (selcat > 0) {
                        spinCategory.setSelection(selpos+1, true);
                        spinCategory.setEnabled(false);
                        spinCategory.setVisibility(View.GONE);
                        txtrequiredcategory.setVisibility(View.GONE);
                        txtCategory.setVisibility(View.VISIBLE);
                        txtCategory.setText(mListCategoryName.get(selpos+1));
                       // spinCategory.setSelection(1, true);
                    }
                    /*if (!TextUtils.isEmpty(catname)) {
                        spinCategory.setSelection(selpos, true);
                        spinCategory.setEnabled(false);
                        spinCategory.setVisibility(View.GONE);
                        txtCategory.setVisibility(View.VISIBLE);
                        txtCategory.setText(mListCategoryName.get(selpos));
                    }*/
                }

                @Override
                public void onFailure(Call<CustomerSupportCategory> call, Throwable t) {
                    setCategoryListData();
                }
            });
        } else {
            setCategoryListData();
        }
    }

    private void clearCategory() {
        mListCategory.clear();
        mListCategoryName.clear();
        mListCategoryName.add("Select Category");
        mListCategoryNameSort.clear();
        mListCategoryNameSort.add("All");
    }

    private void setCategoryListData() {
        initCategorySpinner();
        initCategorySort();
        getSupportOrderID();
       /* if (showOrder) {
            spinCategory.setSelection(1, true);
            spinCategory.setEnabled(false);
            // spinOrder.setSelection(selOrderPos, true);
        }*/
    }

    int selOrderPos = 0;

    private void getSupportOrderID() {
        mListOrderNameDate.clear();
        mListOrderNameDate.add("Select Order Number");
        mListOrderName.clear();
        mListOrderName.add("");
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Call<CustomerSupportOrder> wsCallingEvents = mAPIInterface.getSupportOrderList(userID);
            wsCallingEvents.enqueue(new Callback<CustomerSupportOrder>() {
                @Override
                public void onResponse(Call<CustomerSupportOrder> call, Response<CustomerSupportOrder> response) {
                    int statuscode = 0;
                    if (response.isSuccessful()) {

                        if (response.code() == 200) {
                            assert response.body() != null;
                            CustomerSupportOrder mData = response.body();
                            statuscode = mData.getStatusCode();

                            if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                for (int i = 0; i < mData.getData().size(); i++) {

                                    String date = utils.convertDateMonth(mData.getData().get(i).getOrderDateInLong());
                                    String number = mData.getData().get(i).getOrderNumber();
                                    String orderDate = " [" + date + "]";
                                    String datam = number + orderDate;
                                    mListOrderName.add(number);
                                    mListOrderNameDate.add(datam);
                                    Log.d("order position", number);
                                    if (showOrder && selTicketNumber.equalsIgnoreCase(number)) {
                                        selOrderPos = i + 1;
                                        Log.d("sel order position", selOrderPos + " : ");
                                    }
                                }
                            }
                        }
                    }
                    initOrderIdSpinner();
                    if (showOrder && statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                        setOrderSpinner();
                    }
                }

                @Override
                public void onFailure(Call<CustomerSupportOrder> call, Throwable t) {
                    initOrderIdSpinner();
                }
            });
        } else {
            initOrderIdSpinner();
        }
    }

    private void getSupportTicketList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);

            Call<CustomerSupportTicket> wsCallingEvents = mAPIInterface.getSupporTicketList(userID, "", "");
            wsCallingEvents.enqueue(new Callback<CustomerSupportTicket>() {
                @Override
                public void onResponse(Call<CustomerSupportTicket> call, Response<CustomerSupportTicket> response) {

                    mProgressDialog.setVisibility(View.GONE);
                    if (response.isSuccessful()) {

                        if (response.code() == 200) {
                            assert response.body() != null;
                            CustomerSupportTicket mData = response.body();
                            int statuscode = mData.getStatusCode();

                            if (statuscode == REQUEST_STATUS_CODE_NO_RECORDS) {
                                noRecordsFound();
                            } else if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                recyclerView.setVisibility(View.VISIBLE);
                                mListTicketsOriginal.clear();
                                mListTicketsOriginal.addAll(mData.getData());
                                filterApplyList();
                            }

                        } else if (response.code() == 401) {
                            noRecordsFound();
                        }
                        if (mListOrderNameDate.size() < 2)
                            getSupportCategoryList();
                    } else {
                        noRecordsFound();
                    }
                }

                @Override
                public void onFailure(Call<CustomerSupportTicket> call, Throwable t) {

                    noRecordsFound();
                }
            });
        } else {
            noInternetConnection();
        }
    }

    private void submitTicket() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            ProgressDialog pd;
            pd = new ProgressDialog(CustomerSupport.this);
            pd.setTitle("Generating Ticket");
            pd.setMessage("Please wait..");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();

            MultipartBody.Part fbody = null;
            if (selectedFile != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), selectedFile);
                fbody = MultipartBody.Part.createFormData("AttachmentFile", selectedFile.getName(), requestBody);
            }
            //selOrderNumber = mListOrderName.get(posOrderIdSelected);
            Log.d("selOrderNumber==", selOrderNumber + " : ");
            Call<CreateTicketModel> wsCallingEvents = mAPIInterface.createTicket
                    (RequestBody.create(MultipartBody.FORM, userID),
                            fbody,
                            RequestBody.create(MultipartBody.FORM, String.valueOf(selCatID)),
                            RequestBody.create(MultipartBody.FORM, strSubject.trim()),
                            RequestBody.create(MultipartBody.FORM, strComment.trim()),
                            RequestBody.create(MultipartBody.FORM, selOrderNumber));

            wsCallingEvents.enqueue(new Callback<CreateTicketModel>() {
                @Override
                public void onResponse(Call<CreateTicketModel> call, Response<CreateTicketModel> response) {
                    if (response.isSuccessful()) {

                        if (response.code() == 200) {
                            assert response.body() != null;
                            CreateTicketModel mData = response.body();
                            int statuscode = mData.getStatusCode();

                            if (statuscode == REQUEST_STATUS_CODE_SUCCESS) {
                                Toast.makeText(CustomerSupport.this, "Ticket has been generated successfully.", Toast.LENGTH_SHORT).show();
                                if (showOrder) {
                                    editComment.setText("");
                                    tvWordCount.setText("(0/250)");
                                    editSubject.setText("");
                                    clearFileData();
                                } else
                                    clearTicketFields();
                                callAPISet();
                            } else {
                                showError();
                            }
                        } else {
                            showError();
                        }
                    } else {
                        showError();
                    }
                    pd.dismiss();

                }

                @Override
                public void onFailure(Call<CreateTicketModel> call, Throwable t) {
                    showError();
                    pd.dismiss();
                }
            });

        } else {
            // showError();
            utils.dialogueNoInternet(this);
        }
    }

    private void clearTicketFields() {
        spinCategory.setSelection(0, true);
        txtrequiredcategory.setVisibility(View.VISIBLE);
        editSubject.setText("");
        editComment.setText("");
        tvWordCount.setText("(0/250)");
        clearFileData();
    }

    private void clearFileData() {
        tvFileName.setText("No file chosen");
        selectedFile = null;
        selectedMediaUri = null;
        fileclose.setVisibility(View.GONE);
    }

    private void showError() {
        Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
    }

    private void noRecordsFound() {
        tvNoTickets.setText("Ticket has not been generated yet.");


        //  imgError.setImageResource(R.drawable.no_record);
        // txtRetry.setVisibility(View.GONE);
        changeVisibility(false);
        //btnTicket.setVisibility(View.VISIBLE);
        tvToolbarSort.setVisibility(View.VISIBLE);
        mProgressDialog.setVisibility(View.GONE);
    }

    private void changeVisibility(boolean Data) {
        if (Data) {
            tvTicket.setVisibility(View.VISIBLE);
            tvNoTickets.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            tvToolbarSort.setVisibility(View.VISIBLE);
        } else {
            // tvNoTickets.setVisibility(View.VISIBLE);
            tvTicket.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            tvToolbarSort.setVisibility(View.GONE);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void noInternetConnection() {
        tvNoTickets.setText(R.string.error_msg_network);
        changeVisibility(false);
    }
}
