package com.otopba.flick.ui.theme;

import android.graphics.Color;

import androidx.annotation.NonNull;

public class FlickrAppTheme implements AppTheme {

    private final Colors dayColors;
    private final Colors nightColors;
    private boolean day;

    public FlickrAppTheme(boolean day) {
        this.day = day;
        dayColors = new Colors.Builder()
                .setMainBackgroundColor(Color.parseColor("#ffffff"))
                .setAccentColor(Color.parseColor("#2366d3"))
                .build();

        nightColors = new Colors.Builder()
                .setMainBackgroundColor(Color.parseColor("#1d2432"))
                .setAccentColor(Color.parseColor("#2366d3"))
                .build();
    }

    @Override
    @NonNull
    public Colors getColors() {
        if (day) {
            return dayColors;
        } else {
            return nightColors;
        }
    }

    @Override
    public void invertTheme() {
        day = !day;
    }

    @Override
    public boolean isDay() {
        return day;
    }

}