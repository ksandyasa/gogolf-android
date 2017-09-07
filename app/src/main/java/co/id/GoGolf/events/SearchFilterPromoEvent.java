package co.id.GoGolf.events;

/**
 * Created by dedepradana on 7/7/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class SearchFilterPromoEvent {
    private String area_id;

    private String pricemax;

    private String stime;

    private String etime;

    private String gid;

    private String date;

    private String lang;

    private String pricemin;

    public SearchFilterPromoEvent(String area_id, String pricemax, String stime, String etime, String gid, String date, String lang, String pricemin) {
        this.area_id = area_id;
        this.pricemax = pricemax;
        this.stime = stime;
        this.etime = etime;
        this.gid = gid;
        this.date = date;
        this.lang = lang;
        this.pricemin = pricemin;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getPricemax() {
        return pricemax;
    }

    public void setPricemax(String pricemax) {
        this.pricemax = pricemax;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPricemin() {
        return pricemin;
    }

    public void setPricemin(String pricemin) {
        this.pricemin = pricemin;
    }

    @Override
    public String toString() {
        return "SearchFilterPromoEvent{" +
                "area_id='" + area_id + '\'' +
                ", pricemax='" + pricemax + '\'' +
                ", stime='" + stime + '\'' +
                ", etime='" + etime + '\'' +
                ", gid='" + gid + '\'' +
                ", date='" + date + '\'' +
                ", lang='" + lang + '\'' +
                ", pricemin='" + pricemin + '\'' +
                '}';
    }
}
