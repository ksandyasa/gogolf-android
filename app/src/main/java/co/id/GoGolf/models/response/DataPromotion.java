package co.id.GoGolf.models.response;

/**
 * Created by dedepradana on 6/10/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class DataPromotion {
    private String limit_num;

    private String prate;

    private String stime;

    private String etime;

    private String pprice;

    private String gname;

    private String gid;

    private String image;

    private String pid;

    private String date;

    private String discount;

    private String area_name;

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(String limit_num) {
        this.limit_num = limit_num;
    }

    public String getPrate ()
    {
        return prate;
    }

    public void setPrate (String prate)
    {
        this.prate = prate;
    }

    public String getStime ()
    {
        return stime;
    }

    public void setStime (String stime)
    {
        this.stime = stime;
    }

    public String getEtime ()
    {
        return etime;
    }

    public void setEtime (String etime)
    {
        this.etime = etime;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid ()
    {
        return pid;
    }

    public void setPid (String pid)
    {
        this.pid = pid;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getDiscount ()
    {
        return discount;
    }

    public void setDiscount (String discount)
    {
        this.discount = discount;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [limit = "+limit_num+", prate = "+prate+", stime = "+stime+", etime = "+etime+", pprice = "+pprice+", gname = "+gname+", gid = "+gid+", image = "+image+", pid = "+pid+", date = "+date+", discount = "+discount+"]";
    }
}
