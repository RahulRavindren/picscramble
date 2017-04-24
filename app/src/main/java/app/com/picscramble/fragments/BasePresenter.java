package app.com.picscramble.fragments;


public abstract class BasePresenter<V extends BaseView>{
    protected V mView;

    public void init( V view){
        this.mView = view;
    }

    public abstract void onDestroy();
}
