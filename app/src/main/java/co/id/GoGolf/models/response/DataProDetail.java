package co.id.GoGolf.models.response;

import co.id.GoGolf.models.response.promotion.Promotion;
import co.id.GoGolf.models.response.promotion.Timearr;

import java.util.ArrayList;

/**
 * Created by dedepradana on 6/19/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class DataProDetail {
    private String otime;

    private ArrayList<Timearr> timearr;

    private ArrayList<Promotion> promotion;

    private String gname;

    private String gid;

    private String images;

    private String date;

    private String ctime;

    public String getOtime ()
    {
        return otime;
    }

    public void setOtime (String otime)
    {
        this.otime = otime;
    }

    public ArrayList<Timearr> getTimearr() {
        return timearr;
    }

    public void setTimearr(ArrayList<Timearr> timearr) {
        this.timearr = timearr;
    }

    public ArrayList<Promotion> getPromotion() {
        return promotion;
    }

    public void setPromotion(ArrayList<Promotion> promotion) {
        this.promotion = promotion;
    }

    public String getGname ()
    {
        return gname;
    }

    public void setGname (String gname)
    {
        this.gname = gname;
    }

    public String getGid ()
    {
        return gid;
    }

    public void setGid (String gid)
    {
        this.gid = gid;
    }

    public String getImages ()
    {
        return images;
    }

    public void setImages (String images)
    {
        this.images = images;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getCtime ()
    {
        return ctime;
    }

    public void setCtime (String ctime)
    {
        this.ctime = ctime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [otime = "+otime+", timearr = "+timearr+", promotion = "+promotion+", gname = "+gname+", gid = "+gid+", images = "+images+", date = "+date+", ctime = "+ctime+"]";
    }
}
