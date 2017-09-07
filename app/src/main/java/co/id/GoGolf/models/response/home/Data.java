package co.id.GoGolf.models.response.home;

/**
 * Created by prumacadmin on 9/27/16.
 */
public class Data {
    private String gid;

    private String gdate;

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGid ()
    {
        return gid;
    }

    public void setGid (String gid)
    {
        this.gid = gid;
    }

    public String getGdate ()
    {
        return gdate;
    }

    public void setGdate (String gdate)
    {
        this.gdate = gdate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [gid = "+gid+", gdate = "+gdate+"]";
    }
}
