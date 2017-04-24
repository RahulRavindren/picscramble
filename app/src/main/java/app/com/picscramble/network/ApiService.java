package app.com.picscramble.network;


import app.com.picscramble.fragments.home.data.FlickerResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/services/feeds/photos_public.gne?format=json&lang=en-us&nojsoncallback=1")
    Call<FlickerResponse> getPhotos();
}
