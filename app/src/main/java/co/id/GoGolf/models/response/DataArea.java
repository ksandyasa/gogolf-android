package co.id.GoGolf.models.response;

/**
 * Created by dedepradana on 6/18/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class DataArea {
    private String area_id;
    private String area_name;
    private String lat;
    private String lang;

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
