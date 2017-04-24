package app.com.picscramble.fragments.home.presenter;

import android.os.Build;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import app.com.picscramble.R;
import app.com.picscramble.fragments.BasePresenter;
import app.com.picscramble.fragments.home.data.FlickerResponse;
import app.com.picscramble.fragments.home.view.HomeView;
import app.com.picscramble.network.ApiService;
import app.com.picscramble.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomePresenter extends BasePresenter<HomeView>{
    public static final String TAG_NAME = "home_presenter";
    @Inject
    ApiService mApiService;
    private List<FlickerResponse.Item> totalSetItems;

    public HomePresenter(HomeView mView) {
        init(mView);
        mView.getApplicationClass().getComponent().inject(this);
    }

    public void loadData(){
        if (NetworkUtils.haveNetworkConnection(mView.getFragmentContext())) {
            mApiService.getPhotos().enqueue(new Callback<FlickerResponse>() {
                @Override
                public void onResponse(Call<FlickerResponse> call, Response<FlickerResponse> response) {
                    if (response.isSuccessful()) {
                        totalSetItems = response.body().getItems();
                        mView.onLoadGridView(getRandomFive());

                    } else {
                        //show snackbar for error
                        String msg = mView.getFragmentContext().getResources().getString(R.string.network_error);
                        mView.showSnackBar(msg);
                    }
                }

                @Override
                public void onFailure(Call<FlickerResponse> call, Throwable t) {
                    Log.d(TAG_NAME, t.getMessage());
                    String msg = mView.getFragmentContext().getResources().getString(R.string.network_error);
                    mView.showSnackBar(msg);
                }
            });
        }
        else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
                Snackbar.make(mView.getSnackbarView(),"No network connection",Snackbar.LENGTH_SHORT).show();
            }
            else Toast.makeText(mView.getFragmentContext(),"No network connection", Toast.LENGTH_SHORT).show();

        }
    }

    private List<FlickerResponse.Item> getRandomFive(){
        List<FlickerResponse.Item> random = new ArrayList<>(9);
        if (totalSetItems != null) {
            for (int i =0 ; i < 9 ; i ++) {
                Random rand = new Random();
                FlickerResponse.Item item = totalSetItems.get(rand.nextInt(totalSetItems.size()));
                random.add(item);
            }
        }
        return random;
    }

    @Override
    public void onDestroy() {
        totalSetItems.clear();
    }
}
