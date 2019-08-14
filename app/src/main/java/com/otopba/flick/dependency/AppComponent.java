package com.otopba.flick.dependency;

import com.otopba.flick.ui.grid.GridFragment;
import com.otopba.flick.ui.image.ImageFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(GridFragment fragment);

    void inject(ImageFragment fragment);

}
