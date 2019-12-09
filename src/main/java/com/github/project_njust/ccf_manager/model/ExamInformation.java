package com.github.project_njust.ccf_manager.model;

import java.util.Date;

public class ExamInformation {
    private Integer examscore;

    private Date examdate;

    private String data;

    public ExamInformation(Integer examscore, Date examdate, String data) {
        this.examscore = examscore;
        this.examdate = examdate;
        this.data = data;
    }

    public ExamInformation() {
        super();
    }

    public Integer getExamscore() {
        return examscore;
    }

    public void setExamscore(Integer examscore) {
        this.examscore = examscore;
    }

    public Date getExamdate() {
        return examdate;
    }

    public void setExamdate(Date examdate) {
        this.examdate = examdate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}