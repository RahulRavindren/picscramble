package app.com.picscramble.fragments.home.view;

import android.support.annotation.NonNull;

import java.util.List;

import app.com.picscramble.fragments.BaseView;
import app.com.picscramble.fragments.home.data.FlickerResponse;


public interface HomeView extends BaseView {
    void onLoadGridView(@NonNull final List<FlickerResponse.Item> items);

    void showDialog();

    void showSnackBar(final @NonNull String errormsg);
}
