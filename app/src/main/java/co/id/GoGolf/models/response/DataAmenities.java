package co.id.GoGolf.models.response;

/**
 * Created by apridosandyasa on 11/21/16.
 */

public class DataAmenities {

    private int icon;
    private String title;
    private int checklist;
    private int isAvailable;

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public int getChecklist() {
        return checklist;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChecklist(int checklist) {
        this.checklist = checklist;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public DataAmenities(int icon, String title, int checklist, int isAvailable) {
        this.icon = icon;
        this.title = title;
        this.checklist = checklist;
        this.isAvailable = isAvailable;
    }
}
