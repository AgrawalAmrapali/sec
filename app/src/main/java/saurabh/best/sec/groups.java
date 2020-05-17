package saurabh.best.sec;

import java.util.HashMap;

public class groups {
    private String name;
    private HashMap<String,Boolean> users = new HashMap<>();
    private String id;

    public groups() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Boolean> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, Boolean> users) {
        this.users = users;
    }
    public void addUser(String userId){
        if(this.users == null){
            this.users = new HashMap<>();
        }
        this.users.put(userId,true);
    }
}
