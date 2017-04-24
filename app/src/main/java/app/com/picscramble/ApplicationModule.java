package app.com.picscramble;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import app.com.picscramble.libs.Build;
import app.com.picscramble.network.ApiService;
import app.com.picscramble.utils.NetworkUtils;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public final class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @NonNull
    SharedPreferences provideSharedPreference(){
        return PreferenceManager.getDefaultSharedPreferences(application.getApplicationContext());
    }
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        return builder.build();
    }

    @Provides
    @Singleton
    @NonNull
    Build provideBuild(final @NonNull PackageInfo packageInfo) {
        return new Build(packageInfo);
    }

    @Provides
    @Singleton
    @NonNull
    Retrofit provideRetrofit(final @NonNull Gson gson, final @NonNull OkHttpClient client) {
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .client(client).baseUrl(BuildConfig.BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    @NonNull
    ApiService provideApiService(final @NonNull Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
    @Provides
    @Singleton
    @NonNull
    Gson provideGson(){
        return new GsonBuilder().setLenient().create();
    }

    @Provides
    @Singleton
    PackageInfo providePackageInfo() {
        try {
            return application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}
