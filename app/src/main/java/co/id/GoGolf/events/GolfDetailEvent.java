package co.id.GoGolf.events;

import co.id.GoGolf.models.response.golf.DataGolfDetail;

/**
 * Created by prumacadmin on 8/12/16.
 */
public class GolfDetailEvent extends BaseEvent{
    private DataGolfDetail data;

    public DataGolfDetail getData() {
        return data;
    }

    public void setData(DataGolfDetail data) {
        this.data = data;
    }
}
