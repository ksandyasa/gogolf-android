package co.id.GoGolf.models.response;

/**
 * Created by apridosandyasa on 11/8/16.
 */

public class DataVersion {

    private String version_id;
    private String operating_system;
    private String min_version;
    private String description;
    private String version;
    private String update_date;

    public String getVersion_id() {
        return version_id;
    }

    public String getOperating_system() {
        return operating_system;
    }

    public String getMin_version() {
        return min_version;
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setVersion_id(String version_id) {
        this.version_id = version_id;
    }

    public void setOperating_system(String operating_system) {
        this.operating_system = operating_system;
    }

    public void setMin_version(String min_version) {
        this.min_version = min_version;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }
}
