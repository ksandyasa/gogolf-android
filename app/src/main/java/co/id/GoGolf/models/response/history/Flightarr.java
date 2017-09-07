package co.id.GoGolf.models.response.history;

import co.id.GoGolf.models.request.Price_list;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dedepradana on 6/27/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class Flightarr implements Serializable{

    private String number;

    private String ttime;

    private String membership;

    private String cart;

    private String tprice;

    private String pid;

    private ArrayList<Price_list> playerarr;

    public Flightarr(String number, String membership, String cart, String ttime, String tprice, ArrayList<Price_list> playerarr) {
        this.number = number;
        this.membership = membership;
        this.cart = cart;
        this.ttime = ttime;
        this.tprice = tprice;
        this.playerarr = playerarr;
    }


    public String getTprice() {
        return tprice;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTtime() {
        return ttime;
    }

    public void setTtime(String ttime) {
        this.ttime = ttime;
    }

    public ArrayList<Price_list> getPlayerarr() {
        return playerarr;
    }

    public void setPlayerarr(ArrayList<Price_list> playerarr) {
        this.playerarr = playerarr;
    }


    @Override
    public String toString() {
        return "ClassPojo [tprice = " + tprice + ", membership = " + membership + ", pid = " + pid + ", cart = " + cart + ", number = " + number + ", ttime = " + ttime + "]";
    }
}
