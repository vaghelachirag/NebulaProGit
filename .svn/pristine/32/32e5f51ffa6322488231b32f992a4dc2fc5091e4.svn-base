package com.nebulacompanies.ibo.ecom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.adapter.CustomerSupportDetailsAdapter;
import com.nebulacompanies.ibo.ecom.model.CreateTicketModel;
import com.nebulacompanies.ibo.ecom.model.CustomerSupportDetailTicket;
import com.nebulacompanies.ibo.ecom.utils.DrawableClickListener;
import com.nebulacompanies.ibo.ecom.utils.MyTextViewEcom;
import com.nebulacompanies.ibo.ecom.utils.NebulaEditTextEcom;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.AppUtils;
import com.nebulacompanies.ibo.util.Constants;
import com.nebulacompanies.ibo.util.Session;

import java.io.File;
import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_NO_RECORDS;

public class CustomerSupportList extends AppCompatActivity {

    private static final int IMAGE_PICKER_SELECT = 101;
    MaterialProgressBar mProgressDialog;
    APIInterface mAPIInterface;
    Toolbar toolbar;
    Session session;
    SwipeRefreshLayout mSwipeRefreshLayout;

    MyTextViewEcom tvToolbarTitle, tvSubject, tvTicketCategory, tvOrderId, tvAttachedFileName;
    ImageView imgBackArrow, imgInfo, imgChat, imgClose;
    RecyclerView recyclerView;
    LinearLayout lyMessage;
    NebulaEditTextEcom etMessage;
    AlertDialog dialogTicketDetails;
    Intent ticketDetails, ticketNotification;
    int ticketmasterId;
    CardView lyOrderId;

    ArrayList<CustomerSupportDetailTicket.Detail> mListTickets = new ArrayList<>();

    CustomerSupportDetailsAdapter customerSupportDetailsAdapter;
    String userID;
    String  subject, ticketCategoryName, orderId, message;
    //String createDate;
    ConstraintLayout layChat;
   // CustomerSupportTicket.Datum customerSupportList;
    Utils utils;

    AlertDialog.Builder builder;
    CardView cardSubject;
    Handler handler = new Handler();
    Runnable runnable;
    int backID;
    int delay = 30000;

    File selectedFile;
    Uri selectedMediaUri;
    boolean isChatEnable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_support_list);
        Utils.darkenStatusBar(this, R.color.colorNotification);

        initData();
        initUI();
        initTicketList();

        getSupportTicketDetailList();

    }

    @Override
    protected void onResume() {
        /*handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                //Toast.makeText(CustomerSupportList.this, "Refresh", Toast.LENGTH_SHORT).show();
                getSupportTicketDetailList();
            }
        }, delay);*/
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }

    private void initTicketList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customerSupportDetailsAdapter = new CustomerSupportDetailsAdapter(this, mListTickets);
        recyclerView.setAdapter(customerSupportDetailsAdapter);
    }

    private void initData() {
        session = new Session(this);
        userID = session.getIboKeyId();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);

        ticketNotification = getIntent();
        if (ticketNotification != null) {
            backID = ticketNotification.getIntExtra("session_back", 0);
            ticketmasterId = ticketNotification.getIntExtra("ticketId",0);
            Log.d("ticketId","ticketId"+ticketmasterId);
            //Toast.makeText(this, "TicketId"+abc, Toast.LENGTH_SHORT).show();
            // getSupportTicketDetailList1();
        }
       /* Type listType = new TypeToken<CustomerSupportTicket.Datum>() {
        }.getType();
        String data = getIntent().getStringExtra("data");
        Gson gson = new Gson();*/
//        String json = gson.toJson(data, listType);
 //       customerSupportList = gson.fromJson(data, listType);


    }

    void initUI() {
        utils = new Utils();
        mProgressDialog = findViewById(R.id.progresbar);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        toolbar = findViewById(R.id.toolbar_search);
        tvToolbarTitle = toolbar.findViewById(R.id.toolbar_title1);
        tvToolbarTitle.setText("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        imgInfo = findViewById(R.id.img_info);
        imgInfo.setVisibility(View.VISIBLE);
        mProgressDialog = findViewById(R.id.progresbar);
        imgBackArrow = findViewById(R.id.img_back);

        recyclerView = findViewById(R.id.recycler_view);
        lyMessage = findViewById(R.id.ly_message);
        tvAttachedFileName = findViewById(R.id.tv_file_name);
        etMessage = findViewById(R.id.et_message);
        imgChat = findViewById(R.id.send);
        layChat = findViewById(R.id.cn_chat_message);

        imgChat.setOnClickListener(v -> {
            message = etMessage.getText().toString().trim();
            if (!TextUtils.isEmpty(message)) {
                chatEnable(false);
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                supportMessage();
            }
        });

        tvAttachedFileName.setDrawableClickListener(target -> clearFileData());
        etMessage.setDrawableClickListener(new DrawableClickListener() {

            public void onClick(DrawableClickListener.DrawablePosition target) {
                if(isChatEnable) {
 //      Toast.makeText(CustomerSupportList.this, "ABC", Toast.LENGTH_SHORT).show();
//               Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                //Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                pickIntent.setType("*/*");
//                startActivityForResult(pickIntent, 1212);

                    Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickIntent.setType("image/*");
                    startActivityForResult(pickIntent, IMAGE_PICKER_SELECT);
                }
            }
        });
        imgInfo.setOnClickListener(v -> dialogTicketDetails.show());

        imgBackArrow.setOnClickListener(view -> onBackPressed());

        mSwipeRefreshLayout.setOnRefreshListener(this::getSupportTicketDetailList);
    }


    private void clearFileData() {
        // tvAttachedFileName.setText("No file chosen");
        selectedFile = null;
        selectedMediaUri = null;
        tvAttachedFileName.setVisibility(View.GONE);
    }

    public void alertBoxTicketDetail() {

        builder = new AlertDialog.Builder(this);

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_ticket_details, null);
        builder.setView(customLayout);

        imgClose = customLayout.findViewById(R.id.img_ticket_close);
        tvSubject = customLayout.findViewById(R.id.tv_subject);
        tvTicketCategory = customLayout.findViewById(R.id.tv_ticket_category);
        tvOrderId = customLayout.findViewById(R.id.tv_order_id);
        lyOrderId = customLayout.findViewById(R.id.ly_order);
        cardSubject = customLayout.findViewById(R.id.card_subject);

        tvSubject.setText(subject);
        tvTicketCategory.setText(ticketCategoryName);

        cardSubject.setVisibility(TextUtils.isEmpty(subject) ? View.GONE : View.VISIBLE);

        Log.d("orderId", ":" + orderId);
        if (TextUtils.isEmpty(orderId)) {
            lyOrderId.setVisibility(View.GONE);
        } else {
            lyOrderId.setVisibility(View.VISIBLE);
            String orderDate = utils.convertDateMonth(ticketData.getOrderDateINLong());
            tvOrderId.setText(orderId + " [" + orderDate + "]");
        }
        dialogTicketDetails = builder.create();
        dialogTicketDetails.setCancelable(false);

        imgClose.setOnClickListener(v -> dialogTicketDetails.dismiss());

    }
    CustomerSupportDetailTicket.Data ticketData;
    private void getSupportTicketDetailList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);

            Call<CustomerSupportDetailTicket> wsCallingEvents = mAPIInterface.getSupporTicketDetailList(ticketmasterId);
            wsCallingEvents.enqueue(new Callback<CustomerSupportDetailTicket>() {
                @Override
                public void onResponse(Call<CustomerSupportDetailTicket> call, Response<CustomerSupportDetailTicket> response) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mProgressDialog.setVisibility(View.GONE);
                    if (response.isSuccessful()) {

                        if (response.code() == 200) {
                            assert response.body() != null;
                            CustomerSupportDetailTicket mData = response.body();
                           ticketData = mData.getData();
                            int statuscode = mData.getStatusCode();

                            if (statuscode == REQUEST_STATUS_CODE_NO_RECORDS) {
                                // noRecordsFound();
                            } else if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {

                                orderId =ticketData.getOrderNumber();
                                ticketCategoryName = ticketData.getTicketCategoryName();
                                subject = ticketData.getSubject();
                                //description = ticketData.getDescription();
                                //createDate = ticketData.getCreatedOn();
                                tvToolbarTitle.setText(ticketCategoryName);

                                boolean show = (ticketData.getStatus().equalsIgnoreCase("Closed"));
                                layChat.setVisibility(show ? View.GONE : View.VISIBLE);
                                if (show) {
                                    StyleableToast.makeText(CustomerSupportList.this, "This ticket is already closed. So, you can not send any comment.", Toast.LENGTH_LONG, R.style.StyleToastWhile).show();
                                    layChat.setVisibility(View.GONE);
                                }

                                alertBoxTicketDetail();
                                recyclerView.setVisibility(View.VISIBLE);
                                mListTickets.clear();
                                mListTickets.addAll(mData.getData().getDetail());
                                customerSupportDetailsAdapter.notifyDataSetChanged();
                                recyclerView.scrollToPosition(mListTickets.size() - 1);


                                //mListTickets.addAll(response.body().getData());

                                //Log.d("attachment", "" + mListTickets.get(0).getAttachmentFile());

                                // filterApplyList();
                            }

                        } else if (response.code() == 401) {
                            showError();
                            //noRecordsFound();
                        }
                        //if (mListOrderName.size() < 2)
                        //getSupportCategoryList();
                    } else {
                        showError();
                        //noRecordsFound();
                    }
                }

                @Override
                public void onFailure(Call<CustomerSupportDetailTicket> call, Throwable t) {
                    //noRecordsFound();
                    mSwipeRefreshLayout.setRefreshing(false);
                    showError();
                }
            });
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            noInternetConnection();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            selectedMediaUri = data.getData();

            Uri selectedImageUri = data.getData();

            // OI FILE Manager
            String filemanagerstring = selectedImageUri.getPath();

            // MEDIA GALLERY
            //String selectedImagePath = getPath(selectedImageUri);
            String selectedImagePath = getPath(selectedImageUri);


            selectedFile = new File(selectedImagePath);
            Log.d("Result == ", "Result ok" + getPath(selectedMediaUri));
            Log.d("Result1 == ", "Result ok" + getMimeType(selectedMediaUri));

            if (selectedFile != null) {
                tvAttachedFileName.setText(selectedFile.getName());
                tvAttachedFileName.setVisibility(View.VISIBLE);
            } else {
                tvAttachedFileName.setVisibility(View.GONE);
            }
            // String abc = selectedFile.getName();
            Log.d("File NAme", "File Name" + selectedFile.getName());
            Toast.makeText(this, "File attached", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

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

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1212:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    String displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = this.getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Log.d("FileName","FileName"+displayName);
                                Toast.makeText(this, "FileName"+displayName, Toast.LENGTH_SHORT).show();
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        Log.d("FileName1","FileName"+displayName);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

*/


    private void noInternetConnection() {
        AppUtils.displayNetworkErrorMessage(this);
        // Toast.makeText(this, "No Internet Available.", Toast.LENGTH_SHORT).show();
    }

    private void showError() {
        AppUtils.displayServerErrorMessage(this);
        // Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
    }


    public void onBackPressed() {
        if (backID == 1) {
            Intent cartReview = new Intent(CustomerSupportList.this, MainActivity.class);
            startActivity(cartReview);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        } else {
            super.onBackPressed();

        }
    }
    private void chatEnable(boolean b)
    {
        imgChat.setEnabled(b);
        imgClose.setEnabled(b);
        etMessage.setEnabled(b);
        isChatEnable=b;
    }

    private void supportMessage() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {

            mProgressDialog.setVisibility(View.VISIBLE);

            MultipartBody.Part fbody = null;
            if (selectedFile != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), selectedFile);
                fbody = MultipartBody.Part.createFormData("AttachmentFile", selectedFile.getName(), requestBody);
            }
            Call<CreateTicketModel> wsCallingEvents = mAPIInterface.supportMessage(
                    RequestBody.create(MultipartBody.FORM, String.valueOf(ticketmasterId)),
                    fbody,
                    RequestBody.create(MultipartBody.FORM, message)
            );
            wsCallingEvents.enqueue(new Callback<CreateTicketModel>() {
                @Override
                public void onResponse(Call<CreateTicketModel> call, Response<CreateTicketModel> response) {

                    mProgressDialog.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            assert response.body() != null;
                            CreateTicketModel mData = response.body();
                            int statuscode = mData.getStatusCode();

                            if (statuscode == REQUEST_STATUS_CODE_NO_RECORDS) {
                                // noRecordsFound();
                            } else if (statuscode == Constants.REQUEST_STATUS_CODE_SUCCESS) {
                                clearFileData();
                                etMessage.setText("");
                                getSupportTicketDetailList();
                            }
                        } else if (response.code() == 401) {
                            //noRecordsFound();
                        }
                        //if (mListOrderName.size() < 2)
                        //getSupportCategoryList();
                    } else {
                        //noRecordsFound();
                    }
                    chatEnable(true);
                }

                @Override
                public void onFailure(Call<CreateTicketModel> call, Throwable t) {
                    //noRecordsFound();
                    chatEnable(true);
                }
            });
        } else {
            //noInternetConnection();
            utils.dialogueNoInternet(this);
            chatEnable(true);
        }
    }
}

