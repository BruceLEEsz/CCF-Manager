package com.github.project_njust.ccf_manager.model;

public class ExamGrade {
    private Integer studentid;

    private Integer examgrade;

    private String data;

    public ExamGrade(Integer studentid, Integer examgrade, String data) {
        this.studentid = studentid;
        this.examgrade = examgrade;
        this.data = data;
    }

    public ExamGrade() {
        super();
    }

    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    public Integer getExamgrade() {
        return examgrade;
    }

    public void setExamgrade(Integer examgrade) {
        this.examgrade = examgrade;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}