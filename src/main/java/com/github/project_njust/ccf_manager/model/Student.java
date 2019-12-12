package com.github.project_njust.ccf_manager.model;

public class Student {
    private String studentid;
    private int uid;
    private String identitycard;

    public String getStudentid() {

        return studentid;
    }

    public void setStudentid(String studentid) {

        this.studentid = studentid;
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
                "studentid='" + studentid + '\'' +
                ", uid=" + uid +
                ", identitycard='" + identitycard + '\'' +
                '}';
    }
}
