package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataPaymentToken;

/**
 * Created by apridosandyasa on 11/18/16.
 */

public class PaymentTokenEvent extends BaseEvent {

    private DataPaymentToken data;

    public DataPaymentToken getData() {
        return data;
    }

    public void setData(DataPaymentToken data) {
        this.data = data;
    }

}
