package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataPointHistory;

import java.util.ArrayList;

/**
 * Created by dedepradana on 7/27/16.
 */
public class PointHistoryEvent extends BaseEvent {
    private ArrayList<DataPointHistory> data;

    public ArrayList<DataPointHistory> getData() {
        return data;
    }

    public void setData(ArrayList<DataPointHistory> data) {
        this.data = data;
    }
}
