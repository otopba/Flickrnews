package com.otopba.flick.api;

public class ImagesResponse {

    public final Photos photos;
    public final String stat;

    public ImagesResponse(Photos photos, String stat) {
        this.photos = photos;
        this.stat = stat;
    }

    @Override
    public String toString() {
        return "ImageUpdate{" +
                "photos=" + photos +
                ", stat='" + stat + '\'' +
                '}';
    }

}
