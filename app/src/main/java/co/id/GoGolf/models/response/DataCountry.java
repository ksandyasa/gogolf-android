package co.id.GoGolf.models.response;

/**
 * Created by dedepradana on 6/7/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class DataCountry {
    private String country_code;

    private String country_name;

    private String country_id;

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [country_code = " + country_code + ", country_name = " + country_name + "]";
    }
}
