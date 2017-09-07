package co.id.GoGolf.events;

/**
 * Created by prumacadmin on 9/12/16.
 */
public class VeritransEvent extends BaseEvent {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
