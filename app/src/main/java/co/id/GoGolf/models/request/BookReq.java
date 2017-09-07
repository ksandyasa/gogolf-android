package co.id.GoGolf.models.request;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dedepradana on 7/4/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class BookReq implements Serializable{
    private String gid;

    private String date;

    private String lang;

    private String use_point;

    private ArrayList<FlightarrNew> flightarr;

    public BookReq() {

    }

    public BookReq(String use_point, String gid, String date, String lang, ArrayList<FlightarrNew> flightarr) {
        this.use_point = use_point;
        this.gid = gid;
        this.date = date;
        this.lang = lang;
        this.flightarr = flightarr;
    }

    public String getUse_point() {
        return use_point;
    }

    public void setUse_point(String use_point) {
        this.use_point = use_point;
    }

    public String getGid ()
    {
        return gid;
    }

    public void setGid (String gid)
    {
        this.gid = gid;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getLang ()
    {
        return lang;
    }

    public void setLang (String lang)
    {
        this.lang = lang;
    }

    public ArrayList<FlightarrNew> getFlightarr() {
        return flightarr;
    }

    public void setFlightarr(ArrayList<FlightarrNew> flightarr) {
        this.flightarr = flightarr;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [gid = "+gid+", date = "+date+", lang = "+lang+", flightarr = "+flightarr+"]";
    }
}
