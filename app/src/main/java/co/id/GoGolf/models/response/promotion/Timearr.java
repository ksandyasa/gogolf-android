package co.id.GoGolf.models.response.promotion;

import java.io.Serializable;

/**
 * Created by dedepradana on 6/19/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class Timearr implements Serializable{
    private String prate;

    private String pprice;

    private String crate;

    private String ttime;

    private int pos;

    public String getPrate ()
    {
        return prate;
    }

    public void setPrate (String prate)
    {
        this.prate = prate;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getCrate ()
    {
        return crate;
    }

    public void setCrate (String crate)
    {
        this.crate = crate;
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
        return "ClassPojo [prate = "+prate+", pprice = "+pprice+", crate = "+crate+", ttime = "+ttime+"]";
    }
}
