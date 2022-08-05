package com.nebulacompanies.ibo.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.model.MyAccount;
import com.nebulacompanies.ibo.model.MyAccountInfo;
import com.nebulacompanies.ibo.util.AppUtils;
import com.soundcloud.android.crop.Crop;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.nebulacompanies.ibo.util.Permissions.isCameraPermissionGranted;
import static com.nebulacompanies.ibo.util.Permissions.isWriteStoragePermissionGranted;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_ERROR;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE;
import static com.nebulacompanies.ibo.util.Constants.REQUEST_STATUS_CODE_SUCCESS;
import static com.nebulacompanies.ibo.util.PopupEmptyView.DisplayEmptyDialog;

/**
 * Class : MyAccountFragment
 * Details:
 * Create by : Jadav Chirag At NebulaApplication Infra space LLP 12-02-2018
 * Modification by :
 */

public class MyAccountFragment extends Fragment implements View.OnClickListener {
    //TODO : Class Components
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View convertView;
    private SimpleDraweeView imgProfile;
    private ImageView imgCapturePic;
    private TextView txtMemberID, txtMemberRank;
    //TODO : Variables
    private APIInterface mAPIInterface;
    public static MyAccountInfo mMyAccountInfo;
    private final int PICK_FROM_GALLERY = 1;
    /*private byte[] bytes;*/
    SwipeRefreshLayout mSwipeRefreshLayout;
    Boolean isRefreshed = false;
    ProgressDialog mProgressDialog;
    LinearLayout lnProfile,lnProfileLayout;

    /************************************************************
     *                    OVERRIDE METHOD
     *************************************************************/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.fragment_my_account, container, false);
       // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        Fresco.initialize(getActivity());
        mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        // TODO : Initialization UI Component's
        viewPager = (ViewPager) convertView.findViewById(R.id.viewPagerProfile);
        tabLayout = (TabLayout) convertView.findViewById(R.id.tabLayout);
        //TODO : Initialization & Capture Images Option
        imgProfile = (SimpleDraweeView) convertView.findViewById(R.id.imgProfile);
        imgCapturePic = (ImageView) convertView.findViewById(R.id.imgCapturePic);
        txtMemberID = (TextView) convertView.findViewById(R.id.txtMemberID);
        txtMemberRank = (TextView) convertView.findViewById(R.id.txtMemberRank);
        lnProfile = (LinearLayout) convertView.findViewById(R.id.ln_profile);
        lnProfileLayout = (LinearLayout) convertView.findViewById(R.id.ln_profile_layout);
        //TODO : Click Action Performed
        imgCapturePic.setOnClickListener(this);
        // TODO : Getting My Account Details.
        getProfileDetails();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mSwipeRefreshLayout = convertView.findViewById(R.id.swipeRefreshLayoutMyAccount);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshed = true;
                mSwipeRefreshLayout.setRefreshing(true);
                getProfileDetails();
            }
        });

        //setupViewPager(viewPager);
        //tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgCapturePic) {
            if (isCameraPermissionGranted(getActivity()) && isWriteStoragePermissionGranted(getActivity())) {
               // captureRecentImageSelection();
                checkAndroidVersion();


            } else {
                Toast.makeText(getActivity(), R.string.give_storage_permission, Toast.LENGTH_SHORT).show();
            }
        }
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    captureRecentImageSelection();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PICK_FROM_GALLERY);
                    Toast.makeText(getActivity(), "Permission Canceled, Now your application cannot access GALLERY & CAMERA", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }*/

    /************************************************************
     *                    PRIVATE METHOD
     *************************************************************/

    /**
     * This Method for Setup & Binding the My Account PERSONAL DETAIL & BANK DETAIL.
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PersonalAccountFragment(), getResources().getString(R.string.prompt_personal_info));
        if (mMyAccountInfo.getBankInfo()!=null) {
            adapter.addFragment(new BankAccountFragment(), getResources().getString(R.string.prompt_bank_info));
        }
        viewPager.setAdapter(adapter);

        //TODO : Binding Value.
        txtMemberID.setText(mMyAccountInfo.getBasicInfo().getIBOID());
        txtMemberRank.setText(mMyAccountInfo.getBasicInfo().getRank());

        if (mMyAccountInfo.getBasicInfo().getProfilePicFileName().equals("")) {
            if (mMyAccountInfo.getBasicInfo().getSex()!=null) {
                if (mMyAccountInfo.getBasicInfo().getSex().equals("Male")) {
                    String path = "res:/" + R.drawable.male; // Only one slash after res:
                    imgProfile.setImageURI(Uri.parse(path));
                } else {
                    String path = "res:/" + R.drawable.female; // Only one slash after res:
                    imgProfile.setImageURI(Uri.parse(path));
                }
            }
            else
            {
                String path = "res:/" + R.drawable.male; // Only one slash after res:
                imgProfile.setImageURI(Uri.parse(path));
            }
        } else {
            Uri uri = Uri.parse(mMyAccountInfo.getBasicInfo().getProfilePic());
            Log.e(getClass().getSimpleName(), " Path " + uri);
            imgProfile.setImageURI(uri);
        }
    }

    /**
     * This Method for getting  the My Account Profile Details
     */
    private void getProfileDetails() {
        if (AppUtils.isOnline(getActivity())) {
            mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
           /* mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            if (!isRefreshed) {
                mProgressDialog.show();
            }
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            // TODO : REST API Request Initialization
            Call<MyAccount> requestCall = mAPIInterface.getMyAccountDetails();
            requestCall.enqueue(new Callback<MyAccount>() {
                @Override
                public void onResponse(Call<MyAccount> call, Response<MyAccount> response) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    //TODO : Success Response Binding Detail of My Account.
                    if (getActivity() != null && !getActivity().isFinishing() && mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            if (response.body().getStatusCode() == REQUEST_STATUS_CODE_SUCCESS) {
                                mMyAccountInfo = response.body().getData();
                                setupViewPager(viewPager);
                                tabLayout.setupWithViewPager(viewPager);
                                lnProfile.setVisibility(View.VISIBLE);
                                lnProfileLayout.setVisibility(View.VISIBLE);
                               /* if (mMyAccountInfo.getBankInfo().getBank().equals("null") ||
                                        mMyAccountInfo.getBankInfo().getBank() == null ||
                                        mMyAccountInfo.getBankInfo().getBank().isEmpty() ) {
                                    lnProfileLayout.setVisibility(View.INVISIBLE);
                                }
                                else
                                {
                                    lnProfileLayout.setVisibility(View.VISIBLE);
                                }*/
                            } else if (response.body().getStatusCode() == REQUEST_STATUS_CODE_ERROR || response.body().getStatusCode() == REQUEST_STATUS_CODE_SERVICE_UNAVAILABLE) {
                              //  AppUtils.displayServiceUnavailableMessage(getActivity());
                            } else
                                AppUtils.displayErrorMessage(getActivity(), response.body().getMessage());
                        } else
                            AppUtils.displayServerErrorMessage(getActivity());
                    } else
                        DisplayEmptyDialog(getActivity(), -1);

                }

                @Override
                public void onFailure(Call<MyAccount> call, Throwable t) {
                    //TODO : Failure Response Display Error Developer Mode.
                    mProgressDialog.dismiss();
                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.e(getClass().getSimpleName(), "ERROR : " + t.getMessage());
                }
            });
        } else
            AppUtils.displayAlertErrorNetwork(getActivity());
    }

    /**
     * This REST API Method for upload image file.
     *
     * @param fileName The File Path.
     */
    private void UpLoadImageFile(String fileName) {
        if (AppUtils.isOnline(getActivity())) {
            final ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), R.style.MyTheme);
            /*mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);*/
            mProgressDialog.show();
            mProgressDialog.setCancelable(false);
            mProgressDialog.setContentView(R.layout.progressdialog);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            final Call<JsonObject> wsUpdateProfilePic = mAPIInterface.UpdateProfilePic(fileName);
            wsUpdateProfilePic.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            try {
                                String responseString = response.body().toString();
                                JSONObject jsonObject = new JSONObject(responseString);
                                String message = jsonObject.getString("Data");
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                getProfileDetails();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else
                        DisplayEmptyDialog(getActivity(), -1);
                    mProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    mProgressDialog.dismiss();
                    Log.e(getClass().getSimpleName(), "ERROR : " + t.getMessage());
                }
            });
        } else {
            AppUtils.displayAlertErrorNetwork(getActivity());
        }
    }

    /**
     * This Method for decoding image to byte code.
     *
     * @param path    An Image absolute path
     * @param quality An Image Quality
     * @return
     */
    /*public byte[] compressImage(String path, int quality) {
        //  Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
        //imgProfile.setImageURI(Uri.fromFile(new File(path)));
        *//*Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);*//*
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bos.toByteArray()));
        *//*byte[] byteArray = bos.toByteArray();*//*
        return byteArray;
    }*/

    /**
     * This Method for getting the file  size
     *
     * @param path An Image absolute path
     * @return the long file size
     */
    public long getFileSize(String path) {
        android.util.Log.i("path : ", path);
        File file = new File(path);
        long sizeInBytes = file.length();
        android.util.Log.i("sizeInBytes : ", String.valueOf(sizeInBytes));
        long sizeInKb = sizeInBytes / (1024);
        android.util.Log.i("sizeInKb : ", String.valueOf(sizeInKb));
        return sizeInKb;
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            handleCrop(resultCode, result);
        }
    }*/

    private void beginCrop(Uri source) {

        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity());
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            File file = new File(Crop.getOutput(result).getPath());
            //imgProfile.setImageURI(Crop.getOutput(result));
           /* if (getFileSize(file.getPath()) > 1024) {
                bytes = compressImage(file.getPath(), 10);
            } else {
                bytes = compressImage(file.getPath(), 10);
            }*/


            new AlertDialog.Builder(getActivity())
                    .setTitle("Profile Picture")
                    .setMessage("Are you sure want to change profile picture?")
                    .setCancelable(true)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //UpLoadImageFile(Base64.encodeToString(bytes, Base64.DEFAULT));
                            //UpLoadImageFile(encodedString);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method for capture image or select recent image from picker.
     */
    private void captureRecentImageSelection() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PICK_FROM_GALLERY);
        } else {
            TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(getActivity())
                    .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                        @Override
                        public void onImageSelected(Uri uri) {
                            File file = new File(uri.getPath());
                           /* if (getFileSize(file.getPath()) > 1024) {
                                bytes = compressImage(file.getPath(), 10);
                            } else {
                                bytes = compressImage(file.getPath(), 0);
                            }*/
                            beginCrop(uri);
                            //imgProfile.setImageURI(uri);
                            // UpLoadImageFile(Base64.encodeToString(bytes, Base64.DEFAULT));
                        }
                    })
                    .setPeekHeight(1600)
                    .showTitle(false)
                    .setSelectMaxCount(1)
                    .setCompleteButtonText("Done")
                    .create();

            bottomSheetDialogFragment.show(getFragmentManager());
        }
    }


    /************************************************************
     *                    CHILD CLASS
     *************************************************************/
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() != null && !getActivity().isFinishing() && mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }





    public void checkAndroidVersion() {
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 555);
                Toast.makeText(getActivity(), "Click Done", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {

            }
        } else {
            Toast.makeText(getActivity(), "Click Done1", Toast.LENGTH_SHORT).show();
            pickImage();
        }*/
        pickImage();
    }

    public void pickImage() {
        CropImage.startPickImageActivity(getActivity());
    }

    private void croprequest(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(getActivity());
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //RESULT FROM SELECTED IMAGE
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getActivity(), data);
            croprequest(imageUri);
        }

        //RESULT FROM CROPING ACTIVITY
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result.getUri());

                 //   ((ImageView) getActivity().findViewById(R.id.imageView)).setImageBitmap(bitmap);
                   // ((SimpleDraweeView) getActivity().findViewById(R.id.imgProfile)).setImageBitmap(bitmap);
                   // imgProfile = (SimpleDraweeView) convertView.findViewById(R.id.imgProfile);
                   // bytes = Files.readAllBytes(file.toPath());
                 //   imgProfile.setImageURI(Crop.getOutput(result));
                    /*if (getFileSize(file.getPath()) > 1024) {
                        bytes = compressImage(file.getPath(), 10);
                    } else {
                        bytes = compressImage(file.getPath(), 0);
                    }*/

                    //TODO : Confirmation Message Change Profile Picture

                    byte[] bytes = null;
                    try {
                        ContentResolver cr = getActivity().getBaseContext().getContentResolver();
                        InputStream inputStream = cr.openInputStream(result.getUri());
                        Bitmap bitmaps = BitmapFactory.decodeStream(inputStream);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmaps.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                        bytes = baos.toByteArray();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    byte[] finalBytes = bytes;
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Profile Picture")
                            .setMessage("Are you sure want to change profile picture?")
                            .setCancelable(true)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    UpLoadImageFile(Base64.encodeToString(finalBytes, Base64.DEFAULT));


                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (mMyAccountInfo.getBasicInfo().getProfilePicFileName().equals("")) {
                    if (mMyAccountInfo.getBasicInfo().getSex() != null) {
                        if (mMyAccountInfo.getBasicInfo().getSex().equals("Male")) {
                            String path = "res:/" + R.drawable.male; // Only one slash after res:
                            imgProfile.setImageURI(Uri.parse(path));
                        } else {
                            String path = "res:/" + R.drawable.female; // Only one slash after res:
                            imgProfile.setImageURI(Uri.parse(path));
                        }
                    }
                    else
                    {
                        String path = "res:/" + R.drawable.male; // Only one slash after res:
                        imgProfile.setImageURI(Uri.parse(path));
                    }
                }
                else {
                    Uri uri = Uri.parse(mMyAccountInfo.getBasicInfo().getProfilePic());
                    Log.e(getClass().getSimpleName(), " Path " + uri);
                    imgProfile.setImageURI(uri);
                }
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        } else {
            checkAndroidVersion();
        }
    }


    public byte[] convertImageToByte(Uri uri){
        byte[] data = null;
        try {
            ContentResolver cr = getActivity().getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

}


