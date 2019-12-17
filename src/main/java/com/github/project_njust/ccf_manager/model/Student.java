package com.github.project_njust.ccf_manager.model;

import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

public class Student {
    private String studentId;
    private int uid = -1;
    private JsonSection data;
    private String identitycard;

    public Student() {
    }

    public Student(String studentId, JsonSection data, String identitycard) {
        this.studentId = studentId;
        this.data = data;
        this.identitycard = identitycard;
    }

    public Student(String studentId, int uid, JsonSection data, String identitycard) {
        this.studentId = studentId;
        this.uid = uid;
        this.data = data;
        this.identitycard = identitycard;
    }

    public JsonSection getData() {
        return data;
    }

    public String getStudentId() {
        return studentId;
    }

    public boolean hasUid(){
        return this.uid != -1;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getIdentitycard() {

        return identitycard;
    }

    public void setIdentitycard(String identitycard) {

        this.identitycard = identitycard;
    }

    @Override
    public String toString() {
        return "student{" +
                "studentid='" + studentId + '\'' +
                ", uid=" + uid +
                ", identitycard='" + identitycard + '\'' +
                '}';
    }
}
