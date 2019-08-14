package com.otopba.flick.controller;

import androidx.annotation.NonNull;

import io.reactivex.subjects.Subject;

public interface ImageController {

    @NonNull
    Subject<ControllerUpdate> getUpdateSubject();

    void requestUpdates();

}
