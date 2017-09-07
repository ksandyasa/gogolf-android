package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataProDetail;

/**
 * Created by dedepradana on 6/19/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class PreBookingEvent extends BaseEvent {
    private DataProDetail data;

    public DataProDetail getData() {
        return data;
    }

    public void setData(DataProDetail data) {
        this.data = data;
    }
}
