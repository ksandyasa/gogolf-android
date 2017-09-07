package co.id.GoGolf.events;
import co.id.GoGolf.models.response.login.DataLogin;

/**
 * Created by dedepradana on 6/7/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class LoginEvent extends BaseEvent {
    private DataLogin data;

    public DataLogin getData() {
        return data;
    }

    public void setData(DataLogin data) {
        this.data = data;
    }
}
