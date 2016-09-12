package com.dyned.imanuel.dyneduser.Model;

import java.io.Serializable;

/**
 * Created by nuel4 on 10/09/2016.
 */
public class Geo implements Serializable {
    String lat;
    String lng;

    public String getLat() {
        return this.lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return this.lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
