package saurabh.best.sec;

import java.io.Serializable;

public class Group implements Serializable {
    private String name;
    private String type;
    private String id;

    public Group(String name, String type,String id) {
        this.name = name;
        this.type = type;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Group()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
