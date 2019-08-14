package com.otopba.flick.controller;

import java.util.List;

public class ControllerUpdate {

    public final List<String> images;

    public ControllerUpdate(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "ControllerUpdate{" +
                "imagesCount=" + (images == null ? 0 : images.size()) +
                '}';
    }

}
