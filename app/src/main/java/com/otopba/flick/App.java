package com.otopba.flick;

import android.app.Application;

import com.otopba.flick.dependency.AppComponent;
import com.otopba.flick.dependency.AppModule;
import com.otopba.flick.dependency.DaggerAppComponent;


public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}
