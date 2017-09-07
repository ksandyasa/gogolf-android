package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataVersion;

/**
 * Created by apridosandyasa on 11/8/16.
 */

public class VersionEvent extends BaseEvent {

    private DataVersion data;

    public DataVersion getData() {
        return data;
    }

    public void setData(DataVersion data) {
        this.data = data;
    }

}
