package com.nebulacompanies.ibo.ecom.utils;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nebulacompanies.ibo.ecom.MainActivity;

public class BottomSheetDialogbackFragment  extends AppCompatDialogFragment {
    public BottomSheetDialogbackFragment() {
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
       // return new BottomSheetDialog(this.getContext(), this.getTheme());
        return new BottomSheetDialog(this.getContext(), getTheme()){
            @Override
            public void onBackPressed() {
                //do your stuff
               // Toast.makeText(this.getContext(), "back dialog", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(MainActivity.Dialogdismis);
                this.getContext().sendBroadcast(intent);
                dismiss();
            }
        };
    }
}


