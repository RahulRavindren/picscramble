package app.com.picscramble.libs;


import java.util.List;

import app.com.picscramble.fragments.home.data.FlickerResponse;

public interface DatasetListener {


    void onsetViewPager(List<FlickerResponse.Item> data);

    boolean onItemClickedResult(FlickerResponse.Item item , int position);


}
