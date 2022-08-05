package com.nebulacompanies.ibo.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nebulacompanies.ibo.Base3Activity;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.view.MyTextView;

public class MapSiteOfficeChennai extends Base3Activity implements View.OnClickListener {

    MyTextView tvChennaiMapPhoneNumber, email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_site_office_chennai, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        tvChennaiMapPhoneNumber = (MyTextView) view.findViewById(R.id.tv_chennai_map_office_phone_number);
        email = (MyTextView) view.findViewById(R.id.email1);
        tvChennaiMapPhoneNumber.setOnClickListener(this);
        email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_chennai_map_office_phone_number:
                Intent callIntent1 = new Intent(Intent.ACTION_DIAL);
                callIntent1.setData(Uri.parse("tel:044 6741 9777"));
                startActivity(callIntent1);
                break;

            case R.id.email1:
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ "happychennai@nebulacompanies.com"});
                //need this to prompts email client only
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                break;
        }
    }
}
