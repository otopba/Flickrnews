package com.otopba.flick.api;

import java.util.List;

public class Photos {

    public final int page;
    public final int pages;
    public final int perpage;
    public final int total;
    public final List<Photo> photo;

    public Photos(int page, int pages, int perpage, int total, List<Photo> photo) {
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Photos{" +
                "page=" + page +
                ", pages=" + pages +
                ", perpage=" + perpage +
                ", total=" + total +
                ", photoCount=" + (photo == null ? 0 : photo.size()) +
                '}';
    }

}
