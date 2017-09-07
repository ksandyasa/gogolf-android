package co.id.GoGolf.models.response.history;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dedepradana on 6/27/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class DataHistory implements Serializable {

    private String image;

    private String uid;

    private String bid;

    private String bcode;

    private String gid;

    private String gname;

    private String date;

    private String status;

    private String tprice;

    private String deposit_price;

    private String reward_point;

    private ArrayList<Flightarr> flightarr;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
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

    public ArrayList<Flightarr> getFlightarr() {
        return flightarr;
    }

    public void setFlightarr(ArrayList<Flightarr> flightarr) {
        this.flightarr = flightarr;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode;
    }

    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDeposit_price() {
        return deposit_price;
    }

    public void setDeposit_price(String deposit_price) {
        this.deposit_price = deposit_price;
    }

    public String getReward_point() {
        return reward_point;
    }

    public void setReward_point(String reward_point) {
        this.reward_point = reward_point;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
