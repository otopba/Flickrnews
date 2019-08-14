package com.otopba.flick.controller.error;

import androidx.annotation.Nullable;

public class NoConnectionError extends ImageError {

    public NoConnectionError() {
    }

    public NoConnectionError(@Nullable Throwable throwable) {
        super(throwable);
    }

}
