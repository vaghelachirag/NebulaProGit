package com.nebulacompanies.ibo.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;

import com.nebulacompanies.ibo.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Palak Mehta on 4/13/2016.
 */
public class AsyncComplex extends AsyncTask<Void, Void, String> {

    Context mContext = null;
    String API_URL, Tag;
    Boolean isRefreshed;
    public AsyncResponse asyncResponse;
    private ProgressDialog progressDialog;

    public AsyncComplex(Context context, String url, String tag, Boolean isRefreshed) {
        this.mContext = context;
        this.API_URL = url;
        this.Tag = tag;
        this.isRefreshed = isRefreshed;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mContext != null) {
            progressDialog = new ProgressDialog(mContext, R.style.MyTheme);
            if (!isRefreshed) {
                try {
                    progressDialog.show();
                } catch (Error e) {

                }
                progressDialog.setContentView(R.layout.progressdialog);
                progressDialog.setCancelable(true);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            } else {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
        }
    }

    @Override
    protected String doInBackground(Void... urls) {
        try {
            java.net.URL url = new URL(API_URL);
            System.setProperty("http.keepAlive", "false");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        } catch (FileNotFoundException e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (final IllegalArgumentException e) {
                Log.e("ERROR", e.getMessage(), e);
            } catch (final Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            } finally {
                progressDialog = null;
            }
        }
        try {
            asyncResponse.processFinish(response, Tag);
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e("ERROR", e.getMessage(), e);
        }
    }
}
