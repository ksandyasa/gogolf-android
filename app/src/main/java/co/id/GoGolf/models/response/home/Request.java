package co.id.GoGolf.models.response.home;

/**
 * Created by prumacadmin on 9/27/16.
 */
public class Request {
    private Data data;

    private String api;

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    public String getApi ()
    {
        return api;
    }

    public void setApi (String api)
    {
        this.api = api;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", api = "+api+"]";
    }
}
