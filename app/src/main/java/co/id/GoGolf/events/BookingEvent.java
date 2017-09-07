package co.id.GoGolf.events;

/**
 * Created by dedepradana on 7/4/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class BookingEvent extends BaseEvent {

    private DataBooking data;

    public  class DataBooking {
        private String bid;
        private String bcode;

        public String getBcode() {
            return bcode;
        }

        public void setBcode(String bcode) {
            this.bcode = bcode;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }
    }

    public DataBooking getData() {
        return data;
    }

    public void setData(DataBooking data) {
        this.data = data;
    }
}
