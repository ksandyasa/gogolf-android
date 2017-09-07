package co.id.GoGolf.models.response.point;

import java.io.Serializable;

/**
 * Created by dedepradana on 7/24/16.
 */
public class Spend_case implements Serializable {
    private String final_price;

    private String spend_point;

    private String disc_price;

    private String disc_rate;

    public String getFinal_price ()
    {
        return final_price;
    }

    public int getIntFinal_price ()
    {
        return Integer.valueOf(final_price);
    }

    public void setFinal_price (String final_price)
    {
        this.final_price = final_price;
    }

    public String getSpend_point ()
    {
        return spend_point;
    }

    public void setSpend_point (String spend_point)
    {
        this.spend_point = spend_point;
    }

    public String getDisc_price ()
    {
        return disc_price;
    }

    public void setDisc_price (String disc_price)
    {
        this.disc_price = disc_price;
    }

    public String getDisc_rate ()
    {
        return disc_rate;
    }

    public void setDisc_rate (String disc_rate)
    {
        this.disc_rate = disc_rate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [final_price = "+final_price+", spend_point = "+spend_point+", disc_price = "+disc_price+", disc_rate = "+disc_rate+"]";
    }
}
