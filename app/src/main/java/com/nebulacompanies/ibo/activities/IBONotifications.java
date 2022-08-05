package com.nebulacompanies.ibo.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nebulacompanies.ibo.util.Session;

public class IBONotifications extends AppCompatActivity {

    Session session;
    String Redirect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();

        if(b != null) {
            Redirect = b.getString("REDIRECT");
        }

        session = new Session(this);

        if(session.getLogin()){
            switch (Redirect) {
                case "ACHIEVER_UPDATE":
                case "AIR_TICKET_SELECTION":
                case "CASH_REWARD_SELECTION": {
                    Intent intent = new Intent(this, HolidayAchiever.class);
                    intent.putExtra("IncomeName", "Holiday Achiever");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                }
                case "MINI_BONANZA_UPDATE": {
                    Intent intent = new Intent(this, BonanzaIncome.class);
                    intent.putExtra("IncomeName", "Mini Bonanza");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                }
                case "BONANZA_UPDATE": {
                    Intent intent = new Intent(this, BonanzaIncome.class);
                    intent.putExtra("IncomeName", "Bonanza");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    break;
                }
            }
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }
}
