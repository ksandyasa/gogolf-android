package co.id.GoGolf.models.response.prebooking;

import java.io.Serializable;

/**
 * Created by prumacadmin on 8/14/16.
 */
public class Price_list implements Serializable{
    private String price;

    private String type;

    private String price_cart;

    private String cart_mandatory;

    public Price_list(String price, String type, String price_cart, String cart_mandatory) {
        this.price = price;
        this.type = type;
        this.price_cart = price_cart;
        this.cart_mandatory = cart_mandatory;
    }

    private int pos;

    private int posParent;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getPosParent() {
        return posParent;
    }

    public void setPosParent(int posParent) {
        this.posParent = posParent;
    }

    public String getPrice ()
    {
        return price;
    }

    public int getIntPrice(){
        return Integer.valueOf(price);
    }

    public void setPrice (String price)
    {
        this.price = price;
    }
    public int getIntPriceCart(){
        return Integer.valueOf(price_cart);
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getPrice_cart() {
        return price_cart;
    }

    public void setPrice_cart(String price_cart) {
        this.price_cart = price_cart;
    }

    public String getCart_mandatory() {
        return cart_mandatory;
    }

    public void setCart_mandatory(String cart_mandatory) {
        this.cart_mandatory = cart_mandatory;
    }

    @Override
    public String toString() {
        return "Price_list{" +
                "price='" + price + '\'' +
                ", type='" + type + '\'' +
                ", price_cart='" + price_cart + '\'' +
                ", cart_mandatory='" + cart_mandatory + '\'' +
                ", pos=" + pos +
                ", posParent=" + posParent +
                '}';
    }
}