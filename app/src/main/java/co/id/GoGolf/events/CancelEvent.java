package co.id.GoGolf.events;

/**
 * Created by prumacadmin on 8/29/16.
 */
public class CancelEvent extends BaseEvent {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private class Data {
        private String bid;

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }
    }
}
