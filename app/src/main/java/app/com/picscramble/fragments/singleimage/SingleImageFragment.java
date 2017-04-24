package app.com.picscramble.fragments.singleimage;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import app.com.picscramble.R;
import app.com.picscramble.adapters.ViewPagerAdapter;
import app.com.picscramble.databinding.FragmentSingleImageBinding;
import app.com.picscramble.fragments.home.data.FlickerResponse;


public class SingleImageFragment extends Fragment {
    private FragmentSingleImageBinding binding;


    @BindingAdapter("imgurl")
    public static void imageDownload(ImageView imageView , String url){
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_single_image,container,false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            FlickerResponse.Item data = (FlickerResponse.Item) bundle.getSerializable(ViewPagerAdapter.DATA_ITEM);
            binding.setItem(data);
        }
        return binding.getRoot();
    }


}
