package app.com.picscramble.libs;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.TransitionRes;
import android.view.View;

import app.com.picscramble.fragments.BaseFragment;

public interface FragmentTransactionLifeCycle {
    void addFragment(BaseFragment fragment);

    void addFragment(final @NonNull BaseFragment fragment, final @NonNull Bundle bundle);

    BaseFragment getTopFragment();

    String getTopFragmentName();

    boolean isFragmentOnStack(final @NonNull String tagname);

    BaseFragment getFragmentByTagName(String tag);

    void popAll();

    void pop();

    void popUntil(String tag);

    void addFragmentWithSharedElementTransition(final @NonNull BaseFragment fragment,
                                                final @NonNull @TransitionRes int enterTransition,
                                                final @NonNull @TransitionRes int exitTransition,
                                                final @NonNull View[] views);

    void addFragmentWithTransition(final @NonNull BaseFragment fragment, final @NonNull View view,
                                   final @NonNull String transitionName);

    void addFragmentAllowingStateLoss(final @NonNull BaseFragment fragment);

    void addFragmentWithoutBackStack(final @NonNull BaseFragment fragment);

    int getStackCount();
}
