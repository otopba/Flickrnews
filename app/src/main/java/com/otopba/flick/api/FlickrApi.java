package com.otopba.flick.api;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface FlickrApi {

    @GET("?method=flickr.photos.getRecent&api_key=da9d38d3dee82ec8dda8bb0763bf5d9c&format=json&nojsoncallback=1")
    Single<ImagesResponse> getImages();

}
