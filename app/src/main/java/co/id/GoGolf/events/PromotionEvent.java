package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataPromotion;

import java.util.ArrayList;

/**
 * Created by dedepradana on 6/10/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class PromotionEvent extends BaseEvent{
    private ArrayList<DataPromotion> data;

    public ArrayList<DataPromotion> getData() {
        return data;
    }

    public void setData(ArrayList<DataPromotion> data) {
        this.data = data;
    }
}
