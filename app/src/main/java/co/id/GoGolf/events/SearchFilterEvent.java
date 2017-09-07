package co.id.GoGolf.events;

/**
 * Created by dedepradana on 7/5/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class SearchFilterEvent {
    private int pricemin;
    private int pricemax;
    private String area_id;
    private String gname;
    private String lang;

    public SearchFilterEvent(int pricemin, int pricemax, String area_id, String gname, String lang) {
        this.pricemin = pricemin;
        this.pricemax = pricemax;
        this.area_id = area_id;
        this.gname = gname;
        this.lang = lang;
    }

    public int getPricemin() {
        return pricemin;
    }

    public void setPricemin(int pricemin) {
        this.pricemin = pricemin;
    }

    public int getPricemax() {
        return pricemax;
    }

    public void setPricemax(int pricemax) {
        this.pricemax = pricemax;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
