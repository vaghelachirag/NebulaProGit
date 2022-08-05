package com.nebulacompanies.ibo.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.util.ScratchImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class UnlockedCardAdapter extends RecyclerView.Adapter<UnlockedCardAdapter.MyViewHolder> {

    private Activity context;
    ArrayList<String> selRules = new ArrayList<>();
    ArrayList<Integer> selPos = new ArrayList<>();
    //ArrayList<Integer> selPrize = new ArrayList<>();
    AlertDialog alertDialog;

    public UnlockedCardAdapter(Activity context) {
        this.context = context;
        selRules.add("Collect Ball");
        selRules.add("Collect Balloon");
        selRules.add("Collect Flowers");
        selRules.add("Collect Rangoli");
        selRules.add("Collect Rocket");
        selRules.add("Collect Snow");
        selRules.add("Collect Toy");
        selRules.add("Collect Cash");

      /*  selPrize.add(R.drawable.prize1);
        selPrize.add(R.drawable.prize2);
        selPrize.add(R.drawable.prize3);
        selPrize.add(R.drawable.prize1);
        selPrize.add(R.drawable.prize2);
        selPrize.add(R.drawable.prize3);*/
        selPos.add(0);
        selPos.add(1);
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_unlocked, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
       // boolean contain = selPos.contains(position);
       // holder.layout.setBackground(getRandomDrawbleColor());
        holder.txtRule.setText(selRules.get(position));
       // holder.imgTick.setBackgroundResource(0);
        // holder.imgTick.setBackgroundResource(contain ?0 : R.drawable.padlock);
        holder.imgPrize.setBackgroundResource( R.drawable.scratchhere);
        holder.cardunlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  if (contain)
                    showScratchDialog();
               /* else
                    showScratchUnlockedDialog();*/
            }
        });
    }

    AlertDialog alertDialognlocked;

    private void showScratchUnlockedDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        //Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_scratch_detail, null);
        dialogBuilder.setView(dialogView);
        //initdialog(dialogView);
        ImageView img = dialogView.findViewById(R.id.sample_image);
        ImageView imgtick = dialogView.findViewById(R.id.typedetail);
       /* EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
        editText.setText("test label");*/
        img.setImageResource(R.drawable.trophy);
        // imgtick.setBackgroundResource(contain ? R.drawable.padlock : R.drawable.ic_tick);
        imgtick.setBackgroundResource(0);
        alertDialognlocked = dialogBuilder.create();
        alertDialognlocked.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialognlocked.show();
    }

    private void showScratchDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_scratch, null);
        dialogBuilder.setView(dialogView);
        initdialog(dialogView);
       /* EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
        editText.setText("test label");*/
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
       // alertDialog.setCancelable(true);
        alertDialog.show();
    }

    void initdialog(View view) {
        ScratchImageView scratchImageView = view.findViewById(R.id.sample_image);
        scratchImageView.setScratchImage(R.drawable.scratchhere);

        KonfettiView konfettiView = view.findViewById(R.id.viewKonfetti);
      //  konfettiView.setOnClickListener(v -> alertDialog.dismiss());
        scratchImageView.setRevealListener(new ScratchImageView.IRevealListener() {
            @Override
            public void onRevealed(ScratchImageView view) {
                context.runOnUiThread(() -> new Handler().postDelayed(() -> {
                    alertDialog.dismiss();
                }, 3000L));
                konfettiView.build().addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.Square.INSTANCE, Shape.Circle.INSTANCE)
                        .addSizes(new Size(12, 5f))
                        .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                        .burst(5000);
            }

            @Override
            public void onRevealPercentChangedListener(ScratchImageView view, float percent) {
                // on image percent reveal
                Log.d("percent==", " " + percent);
                if (percent >= 0.4) {
                    Log.d("Reveal Percentage", "onRevealPercentChangedListener: " + String.valueOf(percent));
                    scratchImageView.reveal();
                }
            }
        });
    }

    public ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable(Color.parseColor("#9ACCCD")), new ColorDrawable(Color.parseColor("#8FD8A0")),
                    new ColorDrawable(Color.parseColor("#CBD890")), new ColorDrawable(Color.parseColor("#DACC8F")),
                    new ColorDrawable(Color.parseColor("#D9A790")), new ColorDrawable(Color.parseColor("#D18FD9")),
                    new ColorDrawable(Color.parseColor("#FF6772")), new ColorDrawable(Color.parseColor("#DDFB5C"))
            };

    public ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt(vibrantLightColorList.length);
        return vibrantLightColorList[idx];
    }

    @Override
    public int getItemCount() {
        return selRules.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView txtRule;
        ImageView imgTick, imgPrize;
        CardView cardunlock;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.lay_card);
            txtRule = itemView.findViewById(R.id.text_rule);
            imgTick = itemView.findViewById(R.id.typedetail);
            imgPrize = itemView.findViewById(R.id.sample_image);
            cardunlock = itemView.findViewById(R.id.card_view);
        }
    }
}
