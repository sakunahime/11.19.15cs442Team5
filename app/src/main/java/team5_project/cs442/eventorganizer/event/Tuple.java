package team5_project.cs442.eventorganizer.event;

import java.io.Serializable;

/**
 * Created by Tae Kyong Han on 11/5/2015.
 */
public class Tuple implements Serializable {
    private String key;
    private String value;

    public Tuple(String key, String value) {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
