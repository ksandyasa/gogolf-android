package co.id.GoGolf.models.response.point;

import java.io.Serializable;

/**
 * Created by dedepradana on 7/24/16.
 */
public class DataPoint implements Serializable{
    private Get_case get_case;

    private Spend_case spend_case;

    private String point_availability;

    public Get_case getGet_case ()
    {
        return get_case;
    }

    public void setGet_case (Get_case get_case)
    {
        this.get_case = get_case;
    }

    public Spend_case getSpend_case ()
    {
        return spend_case;
    }

    public void setSpend_case (Spend_case spend_case)
    {
        this.spend_case = spend_case;
    }

    public String getPoint_availability ()
    {
        return point_availability;
    }

    public void setPoint_availability (String point_availability)
    {
        this.point_availability = point_availability;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [get_case = "+get_case+", spend_case = "+spend_case+", point_availability = "+point_availability+"]";
    }
}
