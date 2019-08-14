package com.otopba.flick.controller.error;

import androidx.annotation.Nullable;

public class ApiError extends ImageError {

    public ApiError() {
    }

    public ApiError(@Nullable Throwable throwable) {
        super(throwable);
    }

}
