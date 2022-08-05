package com.nebulacompanies.ibo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class ScratchRuleAdapter extends RecyclerView.Adapter<ScratchRuleAdapter.MyViewHolder> {

    private Context context;
    ArrayList<String> selRules = new ArrayList<>();
    ArrayList<Integer> selPos = new ArrayList<>();
    ArrayList<Integer> selPrize = new ArrayList<>();

    public ScratchRuleAdapter(Context context) {
        this.context = context;
        selRules.add("Early Five");
        selRules.add("Top Line");
        selRules.add("Middle Line");
        selRules.add("Bottom Line");
        selRules.add("Four Corners");
        selRules.add("Full House");

        selPrize.add(R.drawable.prize1);
        selPrize.add(R.drawable.prize2);
        selPrize.add(R.drawable.prize3);
        selPrize.add(R.drawable.prize1);
        selPrize.add(R.drawable.prize2);
        selPrize.add(R.drawable.prize3);

        selPos.add(1);
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_scratchrule, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        boolean contain = !selPos.contains(position);
        holder.layout.setBackground(getRandomDrawbleColor());
        holder.txtRule.setText(selRules.get(position));
        holder.imgTick.setBackgroundResource(contain ? R.drawable.padlock : R.drawable.ic_tick);
        // holder.imgPrize.setBackgroundResource(selPrize.get(0));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Rule info dialog", Toast.LENGTH_SHORT).show();
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

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.lay_card);
            txtRule = itemView.findViewById(R.id.text_rule);
            imgTick = itemView.findViewById(R.id.typedetail);
            imgPrize = itemView.findViewById(R.id.sample_image);
        }
    }
}
