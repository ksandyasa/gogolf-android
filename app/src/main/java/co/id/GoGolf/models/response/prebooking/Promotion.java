package co.id.GoGolf.models.response.prebooking;

/**
 * Created by prumacadmin on 8/14/16.
 */
public class Promotion {
    private String condition_index;

    private String prate;

    private String stime;

    private String pprice;

    private String min_number;

    private String payment_mandatory;

    private String visit_only;

    private String pid;

    private String cart_mandatory;

    private String discount;

    private String etime;

    private String max_number;

    private String cancel_limit_hours;

    private String deposite_rate;

    private String limit_num;

    public String getCondition_index ()
    {
        return condition_index;
    }

    public void setCondition_index (String condition_index)
    {
        this.condition_index = condition_index;
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

    public String getPprice ()
    {
        return pprice;
    }

    public void setPprice (String pprice)
    {
        this.pprice = pprice;
    }

    public String getMin_number ()
    {
        return min_number;
    }

    public void setMin_number (String min_number)
    {
        this.min_number = min_number;
    }

    public String getPayment_mandatory ()
    {
        return payment_mandatory;
    }

    public void setPayment_mandatory (String payment_mandatory)
    {
        this.payment_mandatory = payment_mandatory;
    }

    public String getVisit_only ()
    {
        return visit_only;
    }

    public void setVisit_only (String visit_only)
    {
        this.visit_only = visit_only;
    }

    public String getPid ()
    {
        return pid;
    }

    public void setPid (String pid)
    {
        this.pid = pid;
    }

    public String getCart_mandatory ()
    {
        return cart_mandatory;
    }

    public void setCart_mandatory (String cart_mandatory)
    {
        this.cart_mandatory = cart_mandatory;
    }

    public String getDiscount ()
    {
        return discount;
    }

    public void setDiscount (String discount)
    {
        this.discount = discount;
    }

    public String getEtime ()
    {
        return etime;
    }

    public void setEtime (String etime)
    {
        this.etime = etime;
    }

    public String getMax_number ()
    {
        return max_number;
    }

    public void setMax_number (String max_number)
    {
        this.max_number = max_number;
    }

    public String getCancel_limit_hours ()
    {
        return cancel_limit_hours;
    }

    public void setCancel_limit_hours (String cancel_limit_hours)
    {
        this.cancel_limit_hours = cancel_limit_hours;
    }

    public String getDeposite_rate ()
    {
        return deposite_rate;
    }

    public void setDeposite_rate (String deposite_rate)
    {
        this.deposite_rate = deposite_rate;
    }

    public String getLimit_num ()
    {
        return limit_num;
    }

    public void setLimit_num (String limit_num)
    {
        this.limit_num = limit_num;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [condition_index = "+condition_index+", prate = "+prate+", stime = "+stime+", pprice = "+pprice+", min_number = "+min_number+", payment_mandatory = "+payment_mandatory+", visit_only = "+visit_only+", pid = "+pid+", cart_mandatory = "+cart_mandatory+", discount = "+discount+", etime = "+etime+", max_number = "+max_number+", cancel_limit_hours = "+cancel_limit_hours+", deposite_rate = "+deposite_rate+", limit_num = "+limit_num+"]";
    }
}
