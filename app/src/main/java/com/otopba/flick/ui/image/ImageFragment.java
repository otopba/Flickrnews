package com.otopba.flick.ui.image;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.otopba.flick.App;
import com.otopba.flick.R;
import com.otopba.flick.ui.theme.AppTheme;
import com.otopba.flick.ui.theme.Colored;
import com.otopba.flick.ui.theme.Colors;
import com.otopba.flick.utils.ThemeUtils;

import javax.inject.Inject;

public class ImageFragment extends Fragment implements Colored {

    private static final String IMAGE = "IMAGE";

    @Inject
    AppTheme appTheme;

    private View rootView;
    private ImageView imageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (activity == null) {
            throw new RuntimeException("Activity is null");
        }
        ((App) activity.getApplication()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    public static ImageFragment newInstance(@NonNull String image) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE, image);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_image, container, false);
        imageView = rootView.findViewById(R.id.fragment_image__image);
        setupImageView();
        applyColors(appTheme.getColors(), appTheme.isDay());
        return rootView;
    }

    private void setupImageView() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            throw new RuntimeException("bundle is null");
        }
        String image = bundle.getString(IMAGE);
        Glide.with(rootView)
                .load(image)
                .fitCenter()
                .into(imageView);
    }

    @Override
    public void applyColors(@NonNull Colors colors, boolean isDay) {
        rootView.setBackgroundColor(colors.mainBackgroundColor);
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        ThemeUtils.setStatusBarColor(activity, colors.mainBackgroundColor, isDay);
    }

}
