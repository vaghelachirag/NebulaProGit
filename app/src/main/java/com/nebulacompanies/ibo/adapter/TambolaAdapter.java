package com.nebulacompanies.ibo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class TambolaAdapter  extends RecyclerView.Adapter<TambolaAdapter.MyViewHolder> {
    private Context context;
    ArrayList<Integer> selPos = new ArrayList<>();
    ArrayList<Integer> selImages = new ArrayList<>();
    final int min = 0;
    final int max = 6;

    public TambolaAdapter(Context context) {
        this.context = context;
        selPos.add(1);
        selPos.add(2);
        selPos.add(3);
        selPos.add(0);
        selPos.add(6);
        selPos.add(8);

        selPos.add(14);
        selImages.add(R.drawable.christmas);
        selImages.add(R.drawable.christmas1);
        selImages.add(R.drawable.christmas2);
        selImages.add(R.drawable.christmas3);
        selImages.add(R.drawable.christmas4);
        selImages.add(R.drawable.christmas5);
        selImages.add(R.drawable.christmas6);
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_tambola_card, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {

        boolean contain = !selPos.contains(position);
        holder.imgTick.setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_IN);

        Log.d("contain", selPos + " ; " + position + " :: " + contain);
        //holder.imgTick.setVisibility(contain ? View.VISIBLE : View.GONE);
        holder.mViw.setVisibility(contain ? View.GONE : View.VISIBLE);
        holder.txtCount.setVisibility(contain ? View.GONE : View.VISIBLE);
        // holder.imgTick.setBackgroundResource(contain ? R.drawable.padlock : R.drawable.ic_tick);
        //  holder.imgTick.setBackgroundResource( contain ? 0 : R.drawable.ic_tick );

        int random = new Random().nextInt((max - min) + 1) + min;
        holder.imgLogo.setImageResource(selImages.get(random));
        holder.cardLay.setOnClickListener(v -> {
            showScratchDialog(random,contain);
        });
    }

    AlertDialog alertDialog;

    private void showScratchDialog(int random, boolean contain) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        //Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_scratch_detail, null);
        dialogBuilder.setView(dialogView);
        //initdialog(dialogView);
        ImageView img=dialogView.findViewById(R.id.sample_image);
        ImageView imgtick=dialogView.findViewById(R.id.typedetail);
       /* EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
        editText.setText("test label");*/
        img .setImageResource( selImages.get(random));
        // imgtick.setBackgroundResource(contain ? R.drawable.padlock : R.drawable.ic_tick);
        //  imgtick.setBackgroundResource(contain ? 0 : R.drawable.ic_tick);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return 16;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardLay;
        ImageView imgTick;
        ImageView imgLogo;
        View mViw;
        TextView txtCount;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mViw = itemView.findViewById(R.id.view);
            cardLay = itemView.findViewById(R.id.card_view);
            imgTick = itemView.findViewById(R.id.type);
            imgLogo = itemView.findViewById(R.id.image);
            txtCount = itemView.findViewById(R.id.typecount);
        }
    }
}