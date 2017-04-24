package app.com.picscramble;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.TransitionRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import java.util.List;

import app.com.picscramble.adapters.ViewPagerAdapter;
import app.com.picscramble.databinding.ActivityMainBinding;
import app.com.picscramble.fragments.BaseFragment;
import app.com.picscramble.fragments.home.data.FlickerResponse;
import app.com.picscramble.fragments.home.view.HomeFragment;
import app.com.picscramble.libs.DatasetListener;
import app.com.picscramble.libs.FragmentTransactionLifeCycle;
import app.com.picscramble.libs.FragmentTxn;
import app.com.picscramble.libs.FragmentTxnType;
import app.com.picscramble.libs.ImageGalleryType;

public class MainActivity extends AppCompatActivity
        implements FragmentManager.OnBackStackChangedListener,FragmentTransactionLifeCycle,
        DatasetListener{
    private ActivityMainBinding binding;
    private FragmentManager mFragManager;
    private boolean isActivityPaused =false;
    private SparseArray<FragmentTxnType> transactions = new SparseArray<>();
    Object lock = new Object();
    private ImageGalleryType galleryType;


    @Override
    public void onsetViewPager(List<FlickerResponse.Item> data) {
        binding.imgViewpager.setVisibility(View.VISIBLE);
        binding.imgViewpager.setSwipeLocked(true);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),data);
        binding.imgViewpager.setAdapter(adapter);
        galleryType = new ImageGalleryType(data,binding.imgViewpager);
    }

    @Override
    public boolean onItemClickedResult(FlickerResponse.Item item, int position) {
        if (galleryType != null) {
            return galleryType.result(item,position);
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        //init frag manager
        mFragManager = getSupportFragmentManager();
        mFragManager.addOnBackStackChangedListener(this);
    }



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setSupportActionBar(binding.toolbar);
            if (mFragManager != null && mFragManager.getBackStackEntryCount() == 0){
            HomeFragment fragment = new HomeFragment().setDatasetListener(this);
            addFragment(fragment);
        }
    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void addFragment(BaseFragment fragment) {
        if (mFragManager != null) {
            if (!isActivityPaused) {
                if (getTopFragment() != null && getTopFragment().getCustomTag().equals(fragment.getCustomTag()))
                    return;
                mFragManager.beginTransaction().replace(R.id.fragment_container,
                        fragment, fragment.getCustomTag())
                        .addToBackStack(fragment.getCustomTag())
                        .commit();
                mFragManager.executePendingTransactions();
            } else {
                transactions.append(transactions.size(), new FragmentTxn.FragmentPush(fragment));
            }
        }
    }

    @Override
    public void addFragment(@NonNull BaseFragment fragment, @NonNull Bundle bundle) {
        fragment.setArguments(bundle);
        addFragment(fragment);
    }

    @Override
    public BaseFragment getTopFragment() {
        if (mFragManager != null) {
            android.support.v4.app.Fragment fragment = mFragManager.findFragmentById(R.id.fragment_container);
            if (fragment instanceof BaseFragment) return (BaseFragment) fragment;
        }
        return null;
    }

    @Override
    public String getTopFragmentName() {
        if (getTopFragment() != null)
            return getTopFragment().getCustomTag();
        return "";
    }

    @Override
    public boolean isFragmentOnStack(@NonNull String tagname) {
        synchronized (lock) {

        }
        return false;
    }

    @Override
    public BaseFragment getFragmentByTagName(String tag) {
        Fragment fragment = mFragManager != null ? mFragManager.findFragmentByTag(tag) : null;
        return (BaseFragment) fragment;
    }

    @Override
    public void popAll() {
        if (mFragManager != null) {
            if (!isActivityPaused && mFragManager.getBackStackEntryCount() > 0) {
                if (isFragmentOnStack(HomeFragment.TAG_NAME))
                    mFragManager.popBackStackImmediate(HomeFragment.TAG_NAME, 0);
                else
                    mFragManager.popBackStackImmediate(null, android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else if (mFragManager.getBackStackEntryCount() > 0) {
                transactions.clear();
                ;
                transactions.put(transactions.size(), new FragmentTxn.FragmentPopAll());
            }
        }
    }

    @Override
    public void pop() {
        if (mFragManager != null) {
            if (!isActivityPaused && mFragManager.getBackStackEntryCount() > 0)
                mFragManager.popBackStackImmediate();
            else if (transactions.size() > 0
                    && transactions.get(transactions.size() - 1) instanceof FragmentTxn.FragmentPush)
                transactions.removeAt(transactions.size() - 1);
            else if (mFragManager.getBackStackEntryCount() > 0)
                transactions.put(transactions.size(), new FragmentTxn.FragmentPop());

        }
    }

    @Override
    public void popUntil(String tag) {
        if (mFragManager != null) {
            if (!isActivityPaused && mFragManager.getBackStackEntryCount() > 0) {
                mFragManager.popBackStackImmediate(tag, 0);
            } else if (mFragManager.getBackStackEntryCount() > 0) {
                transactions.put(transactions.size(), new FragmentTxn.FragmentPopUntil(tag));
            }
        }
    }

    @Override
    public void addFragmentWithSharedElementTransition(@NonNull BaseFragment fragment, @NonNull @TransitionRes int enterTransition, @NonNull @TransitionRes int exitTransition, @NonNull View[] views) {

    }

    @Override
    public void addFragmentWithTransition(@NonNull BaseFragment fragment, @NonNull View view, @NonNull String transitionName) {

    }

    @Override
    public void addFragmentAllowingStateLoss(@NonNull BaseFragment fragment) {
        if (mFragManager != null) {
            if (!isActivityPaused) {
                if (getTopFragment() != null && getTopFragment().getCustomTag().equals(fragment.getCustomTag()))
                    return;
                mFragManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.getCustomTag())
                        .addToBackStack(fragment.getCustomTag())
                        .commitAllowingStateLoss();
                mFragManager.executePendingTransactions();
            } else
                transactions.put(transactions.size(), new FragmentTxn.FragmentPush(fragment));
        }
    }

    @Override
    public void addFragmentWithoutBackStack(@NonNull BaseFragment fragment) {
        if (mFragManager != null) {
            if (!isActivityPaused) {
                mFragManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.getCustomTag())
                        .commit();
                mFragManager.executePendingTransactions();
                onBackStackChanged();
            }
        } else
            transactions.put(transactions.size(), new FragmentTxn.FragmentAddWithoutBackStack(fragment));
    }

    @Override
    public int getStackCount() {
        return mFragManager.getBackStackEntryCount();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityPaused = true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        isActivityPaused = false;
    }
}
