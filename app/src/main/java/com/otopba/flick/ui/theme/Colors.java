package com.otopba.flick.ui.theme;

public class Colors {

    public final int mainBackgroundColor;
    public final int accentColor;

    private Colors(Builder builder) {
        this.mainBackgroundColor = builder.mainBackgroundColor;
        this.accentColor = builder.accentColor;
    }

    public static class Builder {

        private int mainBackgroundColor;
        private int accentColor;

        public Builder setMainBackgroundColor(int mainBackgroundColor) {
            this.mainBackgroundColor = mainBackgroundColor;
            return this;
        }

        public Builder setAccentColor(int accentColor) {
            this.accentColor = accentColor;
            return this;
        }

        public Colors build() {
            return new Colors(this);
        }
    }

}