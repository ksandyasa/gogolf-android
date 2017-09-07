package co.id.GoGolf.models.response.point;

import java.io.Serializable;

/**
 * Created by dedepradana on 7/24/16.
 */
public class Get_case implements Serializable {
    private String reward_point;

    public String getReward_point ()
    {
        return reward_point;
    }

    public void setReward_point (String reward_point)
    {
        this.reward_point = reward_point;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [reward_point = "+reward_point+"]";
    }
}
