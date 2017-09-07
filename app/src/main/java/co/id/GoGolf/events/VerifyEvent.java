package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataVerify;

/**
 * Created by dedepradana on 7/24/16.
 */
public class VerifyEvent extends BaseEvent {
    private DataVerify data;

    public DataVerify getData() {
        return data;
    }

    public void setData(DataVerify data) {
        this.data = data;
    }
}
