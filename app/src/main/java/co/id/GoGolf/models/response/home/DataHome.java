package co.id.GoGolf.models.response.home;

import java.util.ArrayList;

/**
 * Created by dedepradana on 7/28/16.
 */
public class DataHome {
    private ArrayList<Bottomarr> bottomarr;

    private ArrayList<Toparr> toparr;

    public ArrayList<Bottomarr> getBottomarr() {
        return bottomarr;
    }

    public void setBottomarr(ArrayList<Bottomarr> bottomarr) {
        this.bottomarr = bottomarr;
    }

    public ArrayList<Toparr> getToparr() {
        return toparr;
    }

    public void setToparr(ArrayList<Toparr> toparr) {
        this.toparr = toparr;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [bottomarr = "+bottomarr+", toparr = "+toparr+"]";
    }
}
