package com.otopba.flick.ui.grid;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.otopba.flick.App;
import com.otopba.flick.R;
import com.otopba.flick.controller.ControllerUpdate;
import com.otopba.flick.controller.ImageController;
import com.otopba.flick.controller.error.ImageError;
import com.otopba.flick.controller.error.NoConnectionError;
import com.otopba.flick.ui.MainActivity;
import com.otopba.flick.ui.theme.AppTheme;
import com.otopba.flick.ui.theme.Colored;
import com.otopba.flick.ui.theme.Colors;
import com.otopba.flick.utils.Snackbars;
import com.otopba.flick.utils.ThemeUtils;

import java.util.Collections;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GridFragment extends Fragment implements Colored {

    public static final String TAG = GridFragment.class.getName();
    private static final int PORTRAIT_COLUMN_COUNT = 2;
    private static final float PORTRAIT_IMAGE_MARGIN = 0.05f;
    private static final float PORTRAIT_IMAGE_SIZE = 0.35f;

    private static final int LANDSCAPE_COLUMN_COUNT = 4;
    private static final float LANDSCAPE_IMAGE_MARGIN = 0.02f;
    private static final float LANDSCAPE_IMAGE_SIZE = 0.2f;

    @Inject
    AppTheme appTheme;
    @Inject
    ImageController imageController;

    private View rootView;
    private Toolbar toolbar;
    private RecyclerView gridView;

    private CompositeDisposable disposables;
    private GridAdapter gridAdapter;
    private Snackbars snackbars;

    public static GridFragment newInstance() {
        return new GridFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (activity == null) {
            throw new RuntimeException("Activity is null");
        }
        ((App) activity.getApplication()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grid, container, false);
        toolbar = rootView.findViewById(R.id.fragment_grid__toolbar);
        gridView = rootView.findViewById(R.id.fragment_grid__grid);
        snackbars = new Snackbars(rootView);
        setHasOptionsMenu(true);
        setupToolbar();
        setupRecyclerView(inflater);
        applyColors(appTheme.getColors(), appTheme.isDay());
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Disposable disposable = imageController.getUpdateSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(this::onUpdate, this::onError);
        addDisposable(disposable);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (disposables != null && !disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    private void setupToolbar() {
        Activity activity = getActivity();
        if (!(activity instanceof AppCompatActivity)) {
            return;
        }
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        appCompatActivity.setSupportActionBar(toolbar);
    }

    private void setupRecyclerView(@NonNull LayoutInflater inflater) {
        Display display = getActivity().getWindowManager().getDefaultDisplay(); //TODO: проверка на нулл
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        int orientation = getResources().getConfiguration().orientation;
        boolean landscape = orientation == Configuration.ORIENTATION_LANDSCAPE;
        float margin = landscape ? LANDSCAPE_IMAGE_MARGIN : PORTRAIT_IMAGE_MARGIN;
        float imageSize = landscape ? LANDSCAPE_IMAGE_SIZE : PORTRAIT_IMAGE_SIZE;
        int columnCount = landscape ? LANDSCAPE_COLUMN_COUNT : PORTRAIT_COLUMN_COUNT;

        gridView.addItemDecoration(new SpacesItemDecoration((int) (width * margin), columnCount));

        gridAdapter = new GridAdapter(inflater, appTheme, Collections.emptyList(), (int) (width * imageSize));
        gridView.setAdapter(gridAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), columnCount);
        gridView.setLayoutManager(layoutManager);

        Disposable disposable = gridAdapter.getClickSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onImageClick, this::onError);
        addDisposable(disposable);
    }

    private void onImageClick(@NonNull String image) {
        Log.d(TAG, String.format("Click on image %s", image));
        ((MainActivity) getActivity()).openImage(image); //TODO: проверка на нулл
    }

    private void onUpdate(@NonNull ControllerUpdate controllerUpdate) {
        Log.d(TAG, String.format("Controller update %s", controllerUpdate));
        gridAdapter.update(controllerUpdate.images);
        gridAdapter.notifyDataSetChanged();
    }

    private void onError(@NonNull Throwable throwable) {
        Log.e(TAG, "App error", throwable);
    }

    private void onError(@NonNull ImageError imageError) {
        Log.e(TAG, String.format(Locale.ENGLISH, "Can't update images: %s", imageError));
        if (getContext() == null) {
            return;
        }
        if (snackbars == null) {
            return;
        }
        if (imageError instanceof NoConnectionError) {
            snackbars.showShort(R.string.no_connection);
        } else {
            snackbars.showShort(R.string.cant_update);
        }
    }

    private void addDisposable(@NonNull Disposable disposable) {
        if (disposables == null || disposables.isDisposed()) {
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_grid, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

    private void onRefreshClick() {
        Log.d(TAG, "On refresh click");
        imageController.requestUpdates();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_grid__refresh:
                onRefreshClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
