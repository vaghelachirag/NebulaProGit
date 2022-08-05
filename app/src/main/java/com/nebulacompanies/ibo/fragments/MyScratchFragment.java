package com.nebulacompanies.ibo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nebulacompanies.ibo.R;
import com.nebulacompanies.ibo.activities.TambolaActivity;
import com.nebulacompanies.ibo.adapter.ScratchAdapter;
import com.nebulacompanies.ibo.adapter.UnlockedCardAdapter;
import com.nebulacompanies.ibo.util.Session;

public class MyScratchFragment extends Fragment {
    //  private APIInterface mAPIInterface;
    Session session;
    RecyclerView recyclescratchunscratch, rvRewards;
    int numberOfColumnsItems = 2;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_scratchunscratch, container, false);
        session = new Session(getActivity());
        // mAPIInterface = APIClient.getClient(getActivity()).create(APIInterface.class);
        init(view);
        return view;
    }


    void init(View view) {

        imageView = view.findViewById(R.id.image_tambola);
        rvRewards = view.findViewById(R.id.rvscratched);
        recyclescratchunscratch = view.findViewById(R.id.rvscratchunscratch);

        rvRewards.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvRewards.setAdapter(new ScratchAdapter(getActivity()));

        recyclescratchunscratch.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsItems));
        recyclescratchunscratch.setAdapter(new UnlockedCardAdapter(getActivity()));

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TambolaActivity.class);
            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        /*
        recycleritems = view.findViewById(R.id.rvscratch);
        recyclerRule = view.findViewById(R.id.rvscratchrule);
        recycleUnlocked= view.findViewById(R.id.rvscratchunlocked);

        recycleritems.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumnsItems));
        recyclerRule.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleUnlocked.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        recycleritems.setAdapter(new ScratchAdapter(getActivity()));
        recyclerRule.setAdapter(new ScratchRuleAdapter(getActivity()));
        recycleUnlocked.setAdapter(new UnlockedCardAdapter(getActivity()));
        */
    }
}
