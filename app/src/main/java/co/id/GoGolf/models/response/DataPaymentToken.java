package co.id.GoGolf.models.response;

/**
 * Created by apridosandyasa on 11/18/16.
 */

public class DataPaymentToken {
    private String uid;
    private String saved_token_id;
    private String masked_card;
    private String expiry_date;

    public DataPaymentToken() {
        this.uid = "";
        this.saved_token_id = "";
        this.masked_card = "";
        this.expiry_date = "";
    }

    public String getUid() {
        return uid;
    }

    public String getSaved_token_id() {
        return saved_token_id;
    }

    public String getMasked_card() {
        return masked_card;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setSaved_token_id(String saved_token_id) {
        this.saved_token_id = saved_token_id;
    }

    public void setMasked_card(String masked_card) {
        this.masked_card = masked_card;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }
}
