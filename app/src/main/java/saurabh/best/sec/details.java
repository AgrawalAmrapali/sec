package saurabh.best.sec;

import java.util.HashMap;

public class details {
    int batch;
    String branch;
    String course;
    String email;
    String name;
    int reg;
    HashMap<String,Boolean> groups;
    public details() {
    }

    public HashMap<String, Boolean> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, Boolean> groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReg() {
        return reg;
    }

    public void setReg(int reg) {
        this.reg = reg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }


    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }
}
