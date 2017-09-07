package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataArea;

import java.util.ArrayList;

/**
 * Created by dedepradana on 6/18/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class AreaEvent extends BaseEvent {
    private ArrayList<DataArea> data;

    public ArrayList<DataArea> getData() {
        return data;
    }

    public void setData(ArrayList<DataArea> data) {
        this.data = data;
    }
}
