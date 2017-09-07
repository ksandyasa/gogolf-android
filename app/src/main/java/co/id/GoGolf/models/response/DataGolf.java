package co.id.GoGolf.models.response;

/**
 * Created by dedepradana on 6/10/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class DataGolf {
    private String gid;
    private String gname;
    private String image;
    private String area_name;
    private String date;
    private String limit_num;

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(String limit_num) {
        this.limit_num = limit_num;
    }
}
