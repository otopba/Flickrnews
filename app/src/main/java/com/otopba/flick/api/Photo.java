package com.otopba.flick.api;

public class Photo {

    public final String id;
    public final String owner;
    public final String secret;
    public final String server;
    public final String farm;
    public final String title;
    public final int ispublic;
    public final int isfriend;
    public final int isfamily;

    public Photo(String id, String owner, String secret, String server, String farm, String title,
                 int ispublic, int isfriend, int isfamily) {
        this.id = id;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.ispublic = ispublic;
        this.isfriend = isfriend;
        this.isfamily = isfamily;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", secret='" + secret + '\'' +
                ", server='" + server + '\'' +
                ", farm='" + farm + '\'' +
                ", title='" + title + '\'' +
                ", ispublic=" + ispublic +
                ", isfriend=" + isfriend +
                ", isfamily=" + isfamily +
                '}';
    }

}
