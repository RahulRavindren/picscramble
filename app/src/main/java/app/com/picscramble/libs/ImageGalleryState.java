package app.com.picscramble.libs;


import app.com.picscramble.fragments.home.data.FlickerResponse;

public interface ImageGalleryState {
    boolean hasNext(int position);

    boolean isEnd(int position);

    boolean result(FlickerResponse.Item item , int position);
}
