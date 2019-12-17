package com.github.project_njust.ccf_manager.model;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

public class User {
    private final int uid;
    private String password;
    private String name;
    private int type;
    private JsonSection data;

    public User(int uid) {
        this.uid = uid;
    }

    public User(int uid, String password, String name, int type, JsonSection data) {
        this.uid = uid;
        this.password = password;
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public JsonSection getData() {
        return data;
    }

    public UserType getUserType() {
        return UserType.getType(this.type);
    }

    public void setUserType(UserType type) {
        this.type = type.getTypeId();
    }

    public int getUid() {
        return uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
