package com.otopba.flick.controller.error;

import androidx.annotation.Nullable;

public abstract class ImageError {

    public final Throwable throwable;

    public ImageError() {
        throwable = null;
    }

    public ImageError(@Nullable Throwable throwable) {
        this.throwable = throwable;
    }
}
