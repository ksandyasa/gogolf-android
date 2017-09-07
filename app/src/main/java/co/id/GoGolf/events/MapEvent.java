package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataMap;

import java.util.ArrayList;

/**
 * Created by dedepradana on 6/11/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class MapEvent extends BaseEvent {

    private ArrayList<DataMap> data;

    public ArrayList<DataMap> getData() {
        return data;
    }

    public void setData(ArrayList<DataMap> data) {
        this.data = data;
    }
}
