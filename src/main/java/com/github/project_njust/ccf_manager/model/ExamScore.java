package com.github.project_njust.ccf_manager.model;

import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;

public class ExamScore {

    /**
     * 学生每题的详细得分 是一个长度为5的集合
     */
    public static final String DETAILED_SCORE = "DETAILED_SCORE";

    private final int uid;
    private final int examid;
    private boolean confirm;
    private int examgrade;
    private JsonSection data;


    public ExamScore(int uid, int examid, boolean confirm, int examgrade, JsonSection data) {
        this.uid = uid;
        this.examid = examid;
        this.confirm = confirm;
        this.examgrade = examgrade;
        this.data = data;
    }

    public JsonSection getData() {
        return data;
    }

    public int getUid() {
        return uid;
    }

    public int getExamid() {
        return examid;
    }


    public boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public int getExamgrade() {
        return examgrade;
    }

    public void setExamgrade(int examgrade) {
        this.examgrade = examgrade;
    }


    @Override
    public String toString() {
        return "ExamScore{" +
                "uid=" + uid +
                ", examid=" + examid +
                ", confirm=" + confirm +
                ", examgrade=" + examgrade +
                ", data=" + data +
                '}';
    }
}
