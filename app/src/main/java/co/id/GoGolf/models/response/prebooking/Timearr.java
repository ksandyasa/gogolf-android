package co.id.GoGolf.models.response.prebooking;

import java.io.Serializable;

/**
 * Created by prumacadmin on 8/14/16.
 */
public class Timearr implements Serializable{
    private String condition;

    private String promo;

    private String ttime;

    private int pos;

    public String getCondition ()
    {
        return condition;
    }

    public int getIntCondition(){
        return Integer.valueOf(condition);
    }

    public void setCondition (String condition)
    {
        this.condition = condition;
    }

    public String getPromo ()
    {
        return promo;
    }

    public int getIntPromo(){
        return Integer.valueOf(promo);
    }

    public void setPromo (String promo)
    {
        this.promo = promo;
    }

    public String getTtime ()
    {
        return ttime;
    }

    public void setTtime (String ttime)
    {
        this.ttime = ttime;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [condition = "+condition+", promo = "+promo+", ttime = "+ttime+"]";
    }
}
