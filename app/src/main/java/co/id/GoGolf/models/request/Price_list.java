package co.id.GoGolf.models.request;

import java.io.Serializable;

/**
 * Created by prumacadmin on 8/14/16.
 */
public class Price_list implements Serializable{
    private String price;

    private String type;

    public Price_list(String price, String type) {
        this.price = price;
        this.type = type;
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

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [price = "+price+", type = "+type+"]";
    }
}
