package co.id.GoGolf.models.response;

/**
 * Created by apridosandyasa on 10/13/16.
 */

public class DataLanguage {

    public String id;

    public String lang_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLang_name() {
        return lang_name;
    }

    public void setLang_name(String lang_name) {
        this.lang_name = lang_name;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", lang_name = " + lang_name + "]";
    }

}
