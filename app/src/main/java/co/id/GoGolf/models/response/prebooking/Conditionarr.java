package co.id.GoGolf.models.response.prebooking;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by prumacadmin on 8/14/16.
 */
public class Conditionarr implements Serializable{
    private String payment_mandatory;

    private String min_number;

    private String cancel_limit_hours;

    private String max_number;

    private String visit_only;

    private String deposit_rate;

    private ArrayList<Price_list> price_list;

    private String cart_mandatory;

    private int intPrice;

    public String getPayment_mandatory ()
    {
        return payment_mandatory;
    }

    public void setPayment_mandatory (String payment_mandatory)
    {
        this.payment_mandatory = payment_mandatory;
    }

    public int getMinInt() {
        return Integer.valueOf(getMin_number());
    }

    public void setIntPrice(int intPrice) {
        this.intPrice = intPrice;
    }

    public String getMin_number ()
    {
        return min_number;
    }

    public void setMin_number (String min_number)
    {
        this.min_number = min_number;
    }

    public String getCancel_limit_hours ()
    {
        return cancel_limit_hours;
    }

    public void setCancel_limit_hours (String cancel_limit_hours)
    {
        this.cancel_limit_hours = cancel_limit_hours;
    }

    public String getMax_number ()
    {
        return max_number;
    }

    public void setMax_number (String max_number)
    {
        this.max_number = max_number;
    }

    public String getVisit_only ()
    {
        return visit_only;
    }

    public void setVisit_only (String visit_only)
    {
        this.visit_only = visit_only;
    }

    public String getDeposit_rate() {
        return deposit_rate;
    }

    public void setDeposit_rate(String deposit_rate) {
        this.deposit_rate = deposit_rate;
    }

    public int getIntPrice() {
        return intPrice;
    }

    public ArrayList<Price_list> getPrice_list() {
        return price_list;
    }

    public void setPrice_list(ArrayList<Price_list> price_list) {
        this.price_list = price_list;
    }

    public String getCart_mandatory ()
    {
        return cart_mandatory;
    }

    public void setCart_mandatory (String cart_mandatory)
    {
        this.cart_mandatory = cart_mandatory;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [payment_mandatory = "+payment_mandatory+", min_number = "+min_number+", cancel_limit_hours = "+cancel_limit_hours+", max_number = "+max_number+", visit_only = "+visit_only+", deposite_rate = "+deposit_rate+", price_list = "+price_list+", cart_mandatory = "+cart_mandatory+"]";
    }
}