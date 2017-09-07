package co.id.GoGolf.models.response;

/**
 * Created by prumacadmin on 9/4/16.
 */
public class DataNotification {
    private String pushed_date;
    private String title;
    private String body;

    public String getPushed_date() {
        return pushed_date;
    }

    public void setPushed_date(String pushed_date) {
        this.pushed_date = pushed_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
