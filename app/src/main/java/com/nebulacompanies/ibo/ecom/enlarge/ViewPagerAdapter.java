package com.nebulacompanies.ibo.ecom.enlarge;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nebulacompanies.ibo.ecom.model.BanersListEcom;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<BanersListEcom> images;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<BanersListEcom> imagesList) {
        super(fm);
        this.images = imagesList;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.getInstance(images.get(position));
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
