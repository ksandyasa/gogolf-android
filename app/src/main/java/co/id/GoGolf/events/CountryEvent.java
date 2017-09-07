package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataCountry;

import java.util.ArrayList;

/**
 * Created by dedepradana on 6/7/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class CountryEvent extends BaseEvent {
    private ArrayList<DataCountry> data;

    public ArrayList<DataCountry> getData() {
        return data;
    }

    public void setData(ArrayList<DataCountry> data) {
        this.data = data;
    }
}
