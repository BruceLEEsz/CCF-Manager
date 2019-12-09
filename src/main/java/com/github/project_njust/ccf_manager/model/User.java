package com.github.project_njust.ccf_manager.model;

public class User {
    private Integer uid;

    private String name;

    private Integer type;

    private String data;

    private String password;

    public User(Integer uid, String name, Integer type, String data, String password) {
        this.uid = uid;
        this.name = name;
        this.type = type;
        this.data = data;
        this.password = password;
    }

    public User() {
        super();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}