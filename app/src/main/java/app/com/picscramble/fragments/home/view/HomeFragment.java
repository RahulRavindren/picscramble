package app.com.picscramble.fragments.home.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.logging.Handler;

import app.com.picscramble.MainApplication;
import app.com.picscramble.R;
import app.com.picscramble.adapters.ImageGridAdapter;
import app.com.picscramble.databinding.FragmentHomeBinding;
import app.com.picscramble.fragments.BaseFragment;
import app.com.picscramble.fragments.home.data.FlickerResponse;
import app.com.picscramble.fragments.home.presenter.HomePresenter;
import app.com.picscramble.fragments.viewflipper.DialogFragment;
import app.com.picscramble.libs.DatasetListener;


public class HomeFragment extends BaseFragment<HomePresenter,HomeView>
        implements HomeView, app.com.picscramble.fragments.viewflipper.DialogFragment.FlipViewState{
    public static final String TAG_NAME = "home_fragment";
    private FragmentHomeBinding binding;
    private DialogFragment dialogFragment;
    private DatasetListener listener;


    public HomeFragment setDatasetListener(DatasetListener listener){
        this.listener = listener;
        return this;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //attach presenter and view
        super.attachViewAndPresenter(new HomePresenter(this),this);
    }

    @Override
    public void onLoadGridView(@NonNull List<FlickerResponse.Item> items) {
        if (items != null && items.size() > 0) {
            final ImageGridAdapter adapter = new ImageGridAdapter(items, getFragmentContext(),listener);
            binding.gridViewPhoto.setLayoutManager(new GridLayoutManager(getFragmentContext(),
                    2, GridLayoutManager.VERTICAL, false));
            binding.gridViewPhoto.setAdapter(adapter);
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final ImageGridAdapter adapter = (ImageGridAdapter) binding.gridViewPhoto.getAdapter();
                    adapter.setFlip(true);
                    adapter.notifyDataSetChanged();

                    showDialog();
                }
            }, 8000);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.loadData();
        }
    }

    @Override
    public String getCustomTag() {
        return TAG_NAME;
    }

    @Override
    public String getActionBarTitle() {
        return "Pic Scramble";
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public MainApplication getApplicationClass() {
        return (MainApplication) getActivity().getApplicationContext();
    }

    @Override
    public Context getFragmentContext() {
        return getContext();
    }

    @Override
    public void onstartFlipper() {

    }

    @Override
    public void onendFlipper() {
        if (dialogFragment != null) {
            dialogFragment.dismiss();

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                Snackbar.make(binding.getRoot(),"Shuffling images",Snackbar.LENGTH_SHORT).show();
            }
            else Toast.makeText(getContext(),"Shuffling images",Toast.LENGTH_SHORT).show();

            ImageGridAdapter adapter = (ImageGridAdapter) binding.gridViewPhoto.getAdapter();
            adapter.randomiseDataSet();
            listener.onsetViewPager(adapter.getData());

        }
    }

    @Override
    public void showDialog() {
        dialogFragment = new DialogFragment().setOnFlipStateListener(this);
        dialogFragment.show(getChildFragmentManager(),DialogFragment.TAG_NAME);
    }

    @Override
    public View getSnackbarView() {
        return binding.getRoot();
    }

    @Override
    public void showSnackBar(String errormsg) {
        Snackbar.make(getSnackbarView(),errormsg,Snackbar.LENGTH_SHORT).show();
    }
}
