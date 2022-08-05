package com.nebulacompanies.ibo.ecom.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.view.MyBoldTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ActionBottomLocationDialogFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {
    public static final String TAG = "ActionBottomDialog";
    private ItemClickListener mListener;
    RadioButton rbPickUp,rbAhmd,rbHyd,rbChennai;
    MyBoldTextViewEcom tvcity;

    public static ActionBottomLocationDialogFragment newInstance() {
        return new ActionBottomLocationDialogFragment();
    }
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_location, container, false);
    }
    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      /*  view.findViewById(R.id.textView).setOnClickListener(this);
        view.findViewById(R.id.textView2).setOnClickListener(this);
        view.findViewById(R.id.textView3).setOnClickListener(this);
        view.findViewById(R.id.textView4).setOnClickListener(this);*/

        rbPickUp = (RadioButton) view.findViewById( R.id.rd2);
        rbAhmd = (RadioButton) view.findViewById( R.id.rd_ahmd);
        rbHyd = (RadioButton) view.findViewById( R.id.rd_hyd);
        rbChennai = (RadioButton) view.findViewById( R.id.rd_chennai);
        tvcity = (MyBoldTextViewEcom) view.findViewById( R.id.tv_city);


        view.findViewById(R.id.rd1).setOnClickListener(this);
       // view.findViewById(R.id.rd2).setOnClickListener(this);
        view.findViewById(R.id.rd_ahmd).setOnClickListener(this);
        view.findViewById(R.id.rd_hyd).setOnClickListener(this);
        view.findViewById(R.id.rd_chennai).setOnClickListener(this);

       /* if (rbPickUp.isActivated()){
            tvcity.setVisibility( View.VISIBLE );
            rbAhmd.setVisibility( View.VISIBLE );
            rbHyd.setVisibility( View.VISIBLE );
            rbChennai.setVisibility( View.VISIBLE );
        }*/

       rbPickUp.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               tvcity.setVisibility( View.VISIBLE );
               rbAhmd.setVisibility( View.VISIBLE );
               rbHyd.setVisibility( View.VISIBLE );
               rbChennai.setVisibility( View.VISIBLE );
           }
       } );


    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override public void onClick(View view) {
      //  TextView tvSelected = (TextView) view;

        RadioButton radioButton = (RadioButton) view;
        mListener.onItemClick(radioButton.getText().toString());
        dismiss();
    }
    public interface ItemClickListener {
        void onItemClick(String item);
    }
}
