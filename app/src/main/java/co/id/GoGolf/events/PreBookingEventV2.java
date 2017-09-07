package co.id.GoGolf.events;

import co.id.GoGolf.models.response.prebooking.DataPreBooking;

/**
 * Created by prumacadmin on 8/14/16.
 */
public class PreBookingEventV2 extends BaseEvent {
    private DataPreBooking data;

    public DataPreBooking getData() {
        return data;
    }

    public void setData(DataPreBooking data) {
        this.data = data;
    }
}
