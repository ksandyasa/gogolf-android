package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataNotification;

import java.util.ArrayList;

/**
 * Created by prumacadmin on 9/4/16.
 */
public class NotifEvent extends BaseEvent {
    private ArrayList<DataNotification>  data;

    public ArrayList<DataNotification> getData() {
        return data;
    }

    public void setData(ArrayList<DataNotification> data) {
        this.data = data;
    }
}
