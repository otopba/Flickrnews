package com.otopba.flick.provider;

import androidx.annotation.NonNull;

import com.otopba.flick.api.ImagesResponse;

import io.reactivex.Single;

public interface ImageProvider {

    @NonNull
    Single<ImagesResponse> getImages();

}
