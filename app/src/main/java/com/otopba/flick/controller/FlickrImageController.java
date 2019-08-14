package com.otopba.flick.controller;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.otopba.flick.api.ImagesResponse;
import com.otopba.flick.controller.error.ApiError;
import com.otopba.flick.controller.error.ImageError;
import com.otopba.flick.preferences.Prefs;
import com.otopba.flick.provider.ImageProvider;

import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class FlickrImageController implements ImageController {

    private static final String TAG = FlickrImageController.class.getName();
    private static final int IMAGE_COUNT = 20;
    private static final String PATTERN = "https://farm%s.staticflickr.com/%s/%s_%s.jpg";

    private final Subject<ControllerUpdate> updateSubject = BehaviorSubject.create();
    private final Subject<ImageError> errorSubject = PublishSubject.create();

    private final ImageProvider imageProvider;
    private final Prefs prefs;
    private Disposable fetchDisposable;
    private Disposable mapDisposable;

    public FlickrImageController(@NonNull ImageProvider imageProvider, @NonNull Prefs prefs) {
        this.imageProvider = imageProvider;
        this.prefs = prefs;
        updateSubject.onNext(new ControllerUpdate(prefs.getLastImages()));
        requestUpdates();
    }

    @NonNull
    @Override
    public Subject<ControllerUpdate> getUpdateSubject() {
        return updateSubject;
    }

    @Override
    public void requestUpdates() {
        Log.d(TAG, "Request updates");
        fetchDisposable = imageProvider.getImages()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleUpdate, this::notifyApiError);
    }

    private void handleUpdate(@NonNull ImagesResponse imageUpdate) {
        Log.d(TAG, String.format(Locale.ENGLISH, "Handle update : %s", imageUpdate));
        mapDisposable = Observable.fromIterable(imageUpdate.photos.photo)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .take(IMAGE_COUNT)
                .map(photo -> String.format(Locale.ENGLISH, PATTERN, photo.farm, photo.server, photo.id, photo.secret))
                .toList()
                .map(ControllerUpdate::new)
                .subscribe(this::notifyUpdate, this::notifyApiError);
    }

    private void notifyUpdate(@NonNull ControllerUpdate controllerUpdate) {
        prefs.setLastImages(controllerUpdate.images); //TODO: в фоне
        updateSubject.onNext(controllerUpdate);
    }

    private void notifyApiError(@Nullable Throwable throwable) {
        errorSubject.onNext(new ApiError(throwable));
    }

}
