package co.id.GoGolf.models.response;

/**
 * Created by dedepradana on 7/27/16.
 */
public class DataPointHistory {
    private String ptid;

    private String transaction;

    private String po_type;

    private String historical_point;

    private String date;

    public String getPtid ()
    {
        return ptid;
    }

    public void setPtid (String ptid)
    {
        this.ptid = ptid;
    }

    public String getTransaction ()
    {
        return transaction;
    }

    public void setTransaction (String transaction)
    {
        this.transaction = transaction;
    }

    public String getPo_type ()
    {
        return po_type;
    }

    public void setPo_type (String po_type)
    {
        this.po_type = po_type;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getHistorical_point() {
        return historical_point;
    }

    public void setHistorical_point(String historical_point) {
        this.historical_point = historical_point;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ptid = "+ptid+", transaction = "+transaction+", po_type = "+po_type+", date = "+date+"]";
    }
}
