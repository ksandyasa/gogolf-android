package co.id.GoGolf.events;

import co.id.GoGolf.models.response.DataLanguage;

import java.util.ArrayList;

/**
 * Created by apridosandyasa on 10/13/16.
 */

public class LanguageEvent extends BaseEvent {
    private ArrayList<DataLanguage> data;

    public ArrayList<DataLanguage> getData() {
        return data;
    }

    public void setData(ArrayList<DataLanguage> data) {
        this.data = data;
    }
}
