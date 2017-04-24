package app.com.picscramble.fragments.viewflipper;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.picscramble.R;
import app.com.picscramble.databinding.FragmentDialogFlipperBinding;
import app.com.picscramble.widgets.CustomFlipper;

public class DialogFragment extends android.support.v4.app.DialogFragment implements
        CustomFlipper.onChangeListener {
    public static final String TAG_NAME = "dialog_frag";
    private FragmentDialogFlipperBinding binding;
    private FlipViewState state;

    public DialogFragment setOnFlipStateListener(FlipViewState listener){
        state = listener;
        return this;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_flipper,container,false);
        binding.viewFlipper.setOnViewChangeListener(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.viewFlipper.setAutoStart(true);
        binding.viewFlipper.setFlipInterval(1000);

    }

    @Override
    public void onChange(int index) {
        if (index == binding.viewFlipper.getChildCount() - 1 ){
            state.onendFlipper();
        }


    }

    public interface FlipViewState{
        void onstartFlipper();
        void onendFlipper();
    }
}
