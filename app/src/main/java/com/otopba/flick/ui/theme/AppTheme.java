package com.otopba.flick.ui.theme;

import androidx.annotation.NonNull;

public interface AppTheme {

    @NonNull
    Colors getColors();

    void invertTheme();

    boolean isDay();

}