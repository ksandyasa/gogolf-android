package co.id.GoGolf.models.response.prebooking;

import java.util.ArrayList;

/**
 * Created by prumacadmin on 8/14/16.
 */
public class DataPreBooking {
    private ArrayList<Conditionarr> conditionarr;

    private ArrayList<Timearr> timearr;

    private ArrayList<Promotion> promotion;

    private String gname;

    private String gid;

    private String image;

    private String date;

    private String ab_date_start;

    private String ab_date_end;

    public ArrayList<Conditionarr> getConditionarr() {
        return conditionarr;
    }

    public void setConditionarr(ArrayList<Conditionarr> conditionarr) {
        this.conditionarr = conditionarr;
    }

    public ArrayList<Timearr> getTimearr() {
        return timearr;
    }

    public void setTimearr(ArrayList<Timearr> timearr) {
        this.timearr = timearr;
    }

    public ArrayList<Promotion> getPromotion() {
        return promotion;
    }

    public void setPromotion(ArrayList<Promotion> promotion) {
        this.promotion = promotion;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAb_date_start() {
        return ab_date_start;
    }

    public void setAb_date_start(String ab_date_start) {
        this.ab_date_start = ab_date_start;
    }

    public String getAb_date_end() {
        return ab_date_end;
    }

    public void setAb_date_end(String ab_date_end) {
        this.ab_date_end = ab_date_end;
    }

    @Override
    public String toString() {
        return "ClassPojo [conditionarr = " + conditionarr + ", timearr = " + timearr + ", promotion = " + promotion + ", gname = " + gname + ", gid = " + gid + ", image = " + image + ", date = " + date + "]";
    }
}
