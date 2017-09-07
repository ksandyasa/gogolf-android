package co.id.GoGolf.events;

import co.id.GoGolf.models.response.history.DataHistory;

import java.util.ArrayList;

/**
 * Created by dedepradana on 7/4/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class BookHisEvent extends BaseEvent {
    private ArrayList<DataHistory> data;

    public ArrayList<DataHistory> getData() {
        return data;
    }

    public void setData(ArrayList<DataHistory> data) {
        this.data = data;
    }
}
