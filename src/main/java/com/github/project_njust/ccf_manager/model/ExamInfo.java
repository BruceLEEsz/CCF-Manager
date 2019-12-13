package com.github.project_njust.ccf_manager.model;

import java.util.Date;

public class ExamInfo {
    private int examid;
    private int examscore;
    private Date examdate;

    public ExamInfo() {
    }

    public ExamInfo(int examid, int examscore, Date examdate) {
        this.examid = examid;
        this.examscore = examscore;
        this.examdate = examdate;
    }

    public int getExamid() {
        return examid;
    }

    public void setExamid(int examid) {
        this.examid = examid;
    }

    public int getExamscore() {
        return examscore;
    }

    public void setExamscore(int examscore) {
        this.examscore = examscore;
    }

    public Date getExamdate() {
        return examdate;
    }

    public void setExamdate(Date examdate) {
        this.examdate = examdate;
    }

    @Override
    public String toString() {
        return "examinformation{" +
                "examid=" + examid +
                ", examscore=" + examscore +
                ", examdate=" + examdate +
                '}';
    }
}
