package co.id.GoGolf.models.request;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dedepradana on 7/4/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class Flightarr implements Serializable {
    private String player;

    private String cart;

    private ArrayList<co.id.GoGolf.models.response.prebooking.Price_list> playerarr;

    private String ttime;

    public Flightarr(String player, String cart, ArrayList<co.id.GoGolf.models.response.prebooking.Price_list> playerarr, String ttime) {
        this.player = player;
        this.cart = cart;
        this.playerarr = playerarr;
        this.ttime = ttime;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (co.id.GoGolf.models.response.prebooking.Price_list a: playerarr) {
            totalPrice = totalPrice + a.getIntPrice();
        }
        return totalPrice;
    }

    public boolean needCart(){
        boolean status = false;
        for (co.id.GoGolf.models.response.prebooking.Price_list a: playerarr) {
            if (a.getCart_mandatory().equals("1")){
                status = true;
            }
        }
        return status;
    }

    public int getTotalPriceCart() {
        int totalPrice = 0;
        for (co.id.GoGolf.models.response.prebooking.Price_list a: playerarr) {
            totalPrice = totalPrice + a.getIntPrice();
        }
        return totalPrice;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public ArrayList<co.id.GoGolf.models.response.prebooking.Price_list> getPlayerarr() {
        return playerarr;
    }

    public void setPlayerarr(ArrayList<co.id.GoGolf.models.response.prebooking.Price_list> playerarr) {
        this.playerarr = playerarr;
    }

    public String getTtime() {
        return ttime;
    }

    public void setTtime(String ttime) {
        this.ttime = ttime;
    }

    @Override
    public String toString() {
        return "ClassPojo [player = " + player + ", cart = " + cart + ", playerarr = " + playerarr + ", ttime = " + ttime + "]";
    }
}
