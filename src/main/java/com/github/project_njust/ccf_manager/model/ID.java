package com.github.project_njust.ccf_manager.model;

public class ID {
    private Integer uid;

    private Integer studentid;

    private String data;

    public ID(Integer uid, Integer studentid, String data) {
        this.uid = uid;
        this.studentid = studentid;
        this.data = data;
    }

    public ID() {
        super();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}