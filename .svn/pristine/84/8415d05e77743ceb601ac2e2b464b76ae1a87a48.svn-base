package com.nebulacompanies.ibo.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nebulacompanies.ibo.Network.APIClient;
import com.nebulacompanies.ibo.Network.APIInterface;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.ecom.utils.Utils;
import com.nebulacompanies.ibo.util.Session;

import static com.nebulacompanies.ibo.ecom.utils.Utils.actionCloseDialog;
import static com.nebulacompanies.ibo.ecom.utils.Utils.actionUservalid;
import static com.nebulacompanies.ibo.util.AvailableSpace.megabytesAvailable;

public class SessionOverDialog extends AppCompatActivity {
    Utils utils;
    private APIInterface mAPIInterface;
    Session session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // Make us non-modal, so that others can receive touch events.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        // ...but notify us that it happened.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        setContentView(R.layout.dialog_session_over);
        this.setFinishOnTouchOutside(false);
        //android O fix bug orientation
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        utils = new Utils();
        mAPIInterface = APIClient.getClient(this).create(APIInterface.class);
        session = new Session(this);
        IntentFilter filterLogin = new IntentFilter();
        filterLogin.addAction(actionCloseDialog);
        registerReceiver(myReceiver, filterLogin);
        // if (Utils.isNetworkAvailable(getApplicationContext())) {
        utils.getExpireUser(mAPIInterface, this, session);
       /* } else
            utils.showErrorDialog(this, false, "", "Session Expired", true, session);*/

    }

    private final BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(actionCloseDialog)) {
                finish();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }

}
