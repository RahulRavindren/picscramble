package app.com.picscramble.fragments;

import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;


public abstract class BaseFragment<T extends BasePresenter, V extends BaseView > extends Fragment {
    protected T mPresenter;
    protected V mView;


    public void attachViewAndPresenter(T mPresenter , V mView){
        this.mPresenter = mPresenter;
        this.mView = mView;
    }


    public abstract String getCustomTag();

    public abstract String getActionBarTitle();


    public T getmPresenter() {
        return mPresenter;
    }

    public V getmView() {
        return mView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }



}
