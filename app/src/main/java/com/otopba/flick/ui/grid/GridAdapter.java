package com.otopba.flick.ui.grid;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.otopba.flick.R;
import com.otopba.flick.ui.theme.AppTheme;
import com.otopba.flick.ui.theme.Colored;
import com.otopba.flick.ui.theme.Colors;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ImageHolder> {

    private final Subject<String> clickSubject = PublishSubject.create();
    private final LayoutInflater layoutInflater;
    private final AppTheme appTheme;
    private List<String> values;
    private int itemSize;

    public GridAdapter(@NonNull LayoutInflater layoutInflater, @NonNull AppTheme appTheme,
                       @NonNull List<String> values, int itemSize) {
        this.layoutInflater = layoutInflater;
        this.appTheme = appTheme;
        this.values = values;
        this.itemSize = itemSize;
    }

    public void update(@NonNull List<String> values) {
        this.values = values;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageHolder(layoutInflater.inflate(R.layout.image_holder, parent, false), itemSize);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        holder.bind(values.get(position));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public Subject<String> getClickSubject() {
        return clickSubject;
    }

    class ImageHolder extends RecyclerView.ViewHolder implements Colored {

        private View rootView;
        private ImageView imageView;
        private LottieAnimationView placeHolderView;
        private String value;

        public ImageHolder(@NonNull View itemView, int itemSize) {
            super(itemView);
            rootView = itemView;
            imageView = itemView.findViewById(R.id.image_holder__image);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.height = itemSize;
            layoutParams.width = itemSize;
            imageView.setLayoutParams(layoutParams);
            placeHolderView = itemView.findViewById(R.id.image_holder__place_holder);
            itemView.setOnClickListener(v -> handleClick());
        }

        private void handleClick() {
            if (TextUtils.isEmpty(value)) {
                return;
            }
            clickSubject.onNext(value);
        }

        void bind(@NonNull String value) {
            this.value = value;
            Glide.with(rootView)
                    .load(value)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }

        @Override
        public void applyColors(@NonNull Colors colors, boolean isDay) {

        }

    }

}
