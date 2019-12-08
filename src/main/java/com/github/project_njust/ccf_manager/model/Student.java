package com.github.project_njust.ccf_manager.model;

public class Student {
    private String studentid;

    private String data;

    public Student(String studentid, String data) {
        this.studentid = studentid;
        this.data = data;
    }

    public Student() {
        super();
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid == null ? null : studentid.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}