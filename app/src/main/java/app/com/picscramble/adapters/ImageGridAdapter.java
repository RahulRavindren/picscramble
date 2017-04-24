package app.com.picscramble.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import app.com.picscramble.R;
import app.com.picscramble.databinding.GridImageItemBinding;
import app.com.picscramble.fragments.home.data.FlickerResponse;
import app.com.picscramble.libs.DatasetListener;


public final class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ViewHolder> {

    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;
        private GridImageItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            boolean status = listener.onItemClickedResult((FlickerResponse.Item) itemView.getTag()
                    , getAdapterPosition());
            if (status)
                flipViewHolder(itemView,false);
        }
    }

    @Override
    public void onViewAttachedToWindow(final ViewHolder holder) {

        if (isFlip){
            flipViewHolder(holder.itemView,true);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GridImageItemBinding binding = DataBindingUtil.inflate(mInflator, R.layout.grid_image_item,parent,false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        return holder;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder != null) {
            FlickerResponse.Item item = data.get(holder.getPosition());
            holder.itemView.setTag(item);
            GridImageItemBinding binding = DataBindingUtil.getBinding(holder.itemView);
            Glide.with(mContext).load(item.getMedia().getM()).fitCenter().into(binding.flickImg);
        }
    }

    private List<FlickerResponse.Item> data;
    private Context mContext;
    private LayoutInflater mInflator;
    private static WindowManager windowManager;
    private boolean isFlip = false;
    private DatasetListener listener;

    public ImageGridAdapter(final List<FlickerResponse.Item> data ,
                            final Context context ,
                            final DatasetListener listener) {
        this.data = data;
        this.mContext = context;
        this.listener = listener;
        init();
    }

    private void init() {
        mInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

    }

    public void setFlip(boolean status){
        this.isFlip = status;
    }

    public void randomiseDataSet(){
        Collections.shuffle(data);
        notifyDataSetChanged();
    }

    public List<FlickerResponse.Item> getData() {
        return data;
    }

    private void flipViewHolder(final  View  holder, final boolean visibility){
        final GridImageItemBinding  binding = DataBindingUtil.getBinding(holder);
        ObjectAnimator animator = ObjectAnimator.ofFloat(holder,"rotationY",-0f,180f);
        animator.setDuration(500);
        animator.setRepeatCount(0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                binding.flickImg.setAlpha(visibility ? 0.6f : 0.3f);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                binding.flickImg.setAlpha(visibility? 0.0f: 1.0f);
                binding.flickImg.setVisibility(visibility? View.GONE : View.VISIBLE);
                binding.resultLayerId.setVisibility(visibility? View.VISIBLE : View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}
