package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataGolf;

import java.util.ArrayList;

/**
 * Created by dedepradana on 6/10/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class SearchEvent extends BaseEvent {
    private ArrayList<DataGolf> data;

    public ArrayList<DataGolf> getData() {
        return data;
    }

    public void setData(ArrayList<DataGolf> data) {
        this.data = data;
    }
}
