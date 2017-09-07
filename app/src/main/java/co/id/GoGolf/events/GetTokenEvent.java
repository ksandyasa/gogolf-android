package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataGetToken;

/**
 * Created by dedepradana on 6/2/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class GetTokenEvent extends BaseEvent{
    private DataGetToken data;

    public DataGetToken getData() {
        return data;
    }

    public void setData(DataGetToken data) {
        this.data = data;
    }
}
