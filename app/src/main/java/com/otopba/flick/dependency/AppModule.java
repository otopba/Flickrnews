package com.otopba.flick.dependency;

import android.content.Context;

import androidx.annotation.NonNull;

import com.otopba.flick.controller.FlickrImageController;
import com.otopba.flick.controller.ImageController;
import com.otopba.flick.preferences.Prefs;
import com.otopba.flick.preferences.PrefsImpl;
import com.otopba.flick.provider.FlickrImageProvider;
import com.otopba.flick.provider.ImageProvider;
import com.otopba.flick.ui.theme.AppTheme;
import com.otopba.flick.ui.theme.FlickrAppTheme;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Context context;

    public AppModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    public Context context() {
        return context;
    }

    @Provides
    @Singleton
    ImageProvider provideImageProvider() {
        return new FlickrImageProvider();
    }

    @Provides
    @Singleton
    ImageController provideImageController(ImageProvider imageProvider, Prefs prefs) {
        return new FlickrImageController(imageProvider, prefs);
    }

    @Provides
    @Singleton
    AppTheme provideAppTheme(Prefs prefs) {
        return new FlickrAppTheme(prefs.isDayTheme());
    }

    @Provides
    @Singleton
    Prefs providePrefs(Context context) {
        return new PrefsImpl(context);
    }

}
