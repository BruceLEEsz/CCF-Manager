package com.github.project_njust.ccf_manager.model;

public class Examscore {
    private int uid;
    private int examid;
    private int confirm;
    private int examgrade;
    private String competition;

    public int getUid() {

        return uid;
    }

    public void setUid(int uid) {

        this.uid = uid;
    }

    public int getExamid() {

        return examid;
    }

    public void setExamid(int examid) {

        this.examid = examid;
    }

    public int getConfirm() {

        return confirm;
    }

    public void setConfirm(int confirm) {

        this.confirm = confirm;
    }

    public int getExamgrade() {

        return examgrade;
    }

    public void setExamgrade(int examgrade) {

        this.examgrade = examgrade;
    }

    public String getCompetition() {

        return competition;
    }

    public void setCompetition(String competition) {

        this.competition = competition;
    }

    @Override
    public String toString() {
        return "examscore{" +
                "uid='" + uid + '\'' +
                ", examid=" + examid +
                ", confirm=" + confirm +
                ", examgrade=" + examgrade +
                ", competition='" + competition + '\'' +
                '}';
    }
}
