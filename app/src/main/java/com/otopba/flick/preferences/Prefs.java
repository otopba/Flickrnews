package com.otopba.flick.preferences;

import androidx.annotation.NonNull;

import java.util.List;

public interface Prefs {

    void setDayTheme(boolean value);

    boolean isDayTheme();

    void setLastImages(@NonNull List<String> images);

    @NonNull
    List<String> getLastImages();

}
