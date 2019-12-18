package com.github.project_njust.ccf_manager.model;

import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

import java.sql.Date;

public class ExamInfo {
    public static final String KEY_COMPETITION = "info_competition"
    private final int examid;
    private int examscore;
    private Date examdate;
    private String code;
    private JsonSection data;

    public ExamInfo(int examid, int examscore, Date examdate, String code, JsonSection data) {
        this.examid = examid;
        this.examscore = examscore;
        this.examdate = examdate;
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public JsonSection getData() {
        return data;
    }

    public int getExamid() {
        return examid;
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
