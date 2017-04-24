package app.com.picscramble.fragments;

import android.app.Application;
import android.content.Context;
import android.view.View;

import app.com.picscramble.MainApplication;

public interface BaseView {

    MainApplication getApplicationClass();

    Context getFragmentContext();

    View getSnackbarView();
}
