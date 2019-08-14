package com.otopba.flick.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.ironz.binaryprefs.BinaryPreferencesBuilder;
import com.ironz.binaryprefs.Preferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class PrefsImpl implements Prefs {

    private static final String DAY_THEME = "DAY_THEME";
    private static final String LAST_IMAGES = "LAST_IMAGES";

    private final Preferences preferences;

    public PrefsImpl(@NonNull Context context) {
        preferences = new BinaryPreferencesBuilder(context)
                .name("user_data")
                .build();
    }

    @Override
    public void setDayTheme(boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(DAY_THEME, value);
        editor.apply();
    }

    @Override
    public boolean isDayTheme() {
        return preferences.getBoolean(DAY_THEME, true);
    }

    @Override
    public void setLastImages(@NonNull List<String> images) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(LAST_IMAGES, new HashSet<>(images));
        editor.apply();
    }

    @NonNull
    @Override
    public List<String> getLastImages() {
        return new ArrayList<>(preferences.getStringSet(LAST_IMAGES, Collections.emptySet()));
    }

}
