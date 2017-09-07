package co.id.GoGolf.events;

import co.id.GoGolf.models.response.point.DataPoint;

/**
 * Created by dedepradana on 7/24/16.
 */
public class PointEvent extends BaseEvent {
    private DataPoint data;

    public DataPoint getData() {
        return data;
    }

    public void setData(DataPoint data) {
        this.data = data;
    }
}
