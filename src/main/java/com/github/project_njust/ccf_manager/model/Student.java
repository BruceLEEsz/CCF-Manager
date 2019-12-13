package com.github.project_njust.ccf_manager.model;

public class Student {
    private String studentId;
    private int uid = -1;
    private String identitycard;

    public Student() {
    }

    public Student(String studentId, int uid, String identitycard) {
        this.studentId = studentId;
        this.uid = uid;
        this.identitycard = identitycard;
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
