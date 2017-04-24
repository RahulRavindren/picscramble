package app.com.picscramble.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;


public final class CustomFlipper extends ViewFlipper {
    private onChangeListener listener;

    public CustomFlipper(Context context) {
        super(context);
    }

    public CustomFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface onChangeListener{
        void onChange(int index);
    }

    public void setOnViewChangeListener(onChangeListener listener){
        this.listener = listener;
    }

    @Override
    public void showNext() {
        super.showNext();
        if (listener != null) listener.onChange(getDisplayedChild());
    }

    @Override
    public void showPrevious() {
        super.showPrevious();
        if (listener != null) listener.onChange(getDisplayedChild());
    }
}
