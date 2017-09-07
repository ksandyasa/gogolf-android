package co.id.GoGolf.models.request;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prumacadmin on 10/9/16.
 */
public class FlightarrNew implements Serializable{
    private String player;

    private String cart;

    private ArrayList<Price_list> playerarr;

    private String ttime;

    public FlightarrNew() {
        
    }

    public FlightarrNew(String player, String cart, ArrayList<Price_list> playerarr, String ttime) {
        this.player = player;
        this.cart = cart;
        this.playerarr = playerarr;
        this.ttime = ttime;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (Price_list a : playerarr) {
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

    public ArrayList<Price_list> getPlayerarr() {
        return playerarr;
    }

    public void setPlayerarr(ArrayList<Price_list> playerarr) {
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
        return "FlightarrNew{" +
                "player='" + player + '\'' +
                ", cart='" + cart + '\'' +
                ", playerarr=" + playerarr +
                ", ttime='" + ttime + '\'' +
                '}';
    }
}