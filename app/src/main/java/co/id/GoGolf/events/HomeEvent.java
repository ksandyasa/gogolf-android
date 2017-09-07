package co.id.GoGolf.events;

import co.id.GoGolf.models.response.home.DataHome;

/**
 * Created by dedepradana on 7/28/16.
 */
public class HomeEvent extends BaseEvent {
    private DataHome data;

    public DataHome getData() {
        return data;
    }

    public void setData(DataHome data) {
        this.data = data;
    }
}
