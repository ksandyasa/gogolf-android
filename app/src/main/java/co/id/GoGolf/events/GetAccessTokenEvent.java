package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataGetAccessToken;

/**
 * Created by dedepradana on 6/18/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class GetAccessTokenEvent extends BaseEvent {
    private DataGetAccessToken data;

    public DataGetAccessToken getData() {
        return data;
    }

    public void setData(DataGetAccessToken data) {
        this.data = data;
    }
}
