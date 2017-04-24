package app.com.picscramble.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import app.com.picscramble.fragments.home.data.FlickerResponse;
import app.com.picscramble.fragments.singleimage.SingleImageFragment;


public final class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<FlickerResponse.Item> data;
    public static final String DATA_ITEM = "data_item";

    public ViewPagerAdapter(FragmentManager fm , List<FlickerResponse.Item> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Fragment getItem(int position) {
        SingleImageFragment fragment = new SingleImageFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_ITEM,data.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }
    
}
