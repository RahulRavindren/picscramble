package app.com.picscramble.libs;


import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import app.com.picscramble.adapters.ViewPagerAdapter;
import app.com.picscramble.fragments.home.data.FlickerResponse;
import app.com.picscramble.fragments.singleimage.SingleImageFragment;
import app.com.picscramble.widgets.CustomViewPager;

public final class ImageGalleryType implements ImageGalleryState{
    private  List<FlickerResponse.Item> data;
    private CustomViewPager pager;
    private boolean[] correctStatus;
    private int currentposs = 0;

    public ImageGalleryType(List<FlickerResponse.Item> data, CustomViewPager pager) {
        this.data = data;
        this.pager = pager;
        correctStatus = new boolean[data.size()];
    }

    @Override
    public boolean result(FlickerResponse.Item item, int position) {
        currentposs = pager.getCurrentItem();
        ViewPagerAdapter adapter = (ViewPagerAdapter) pager.getAdapter();
        FlickerResponse.Item frag_item = (FlickerResponse.Item) adapter.getItem(currentposs)
                .getArguments().getSerializable(ViewPagerAdapter.DATA_ITEM);


        if (item.equals(frag_item) && !correctStatus[position]){
            correctStatus[position] = true;
            currentposs += 1;
            pager.setCurrentItem(currentposs);
            return true;
        }
        else return false;
    }

    @Override
    public boolean hasNext(int position) {
        return false;
    }

    @Override
    public boolean isEnd(int position) {
        return false;
    }
}
