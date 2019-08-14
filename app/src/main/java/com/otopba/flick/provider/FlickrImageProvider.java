package com.otopba.flick.provider;

import androidx.annotation.NonNull;

import com.otopba.flick.api.FlickrApi;
import com.otopba.flick.api.ImagesResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlickrImageProvider implements ImageProvider {

    private static final int MAX_FAIL_COUNT = 3;
    private static final int RETRY_DELAY_MS = 1000;
    private FlickrApi api;

    public FlickrImageProvider() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/services/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(FlickrApi.class);
    }

    @NonNull
    @Override
    public Single<ImagesResponse> getImages() {
        return api.getImages()
                .retryWhen((Flowable<Throwable> f) -> f.take(MAX_FAIL_COUNT).delay(RETRY_DELAY_MS, TimeUnit.MILLISECONDS));
    }

}
