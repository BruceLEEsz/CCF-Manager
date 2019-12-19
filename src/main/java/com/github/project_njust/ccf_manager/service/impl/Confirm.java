package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.model.ExamScore;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.jetbrains.annotations.NotNull;

public class Confirm extends Service {
    public Confirm() {
        super("confirm", UserType.STUDENT);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        User us = input.getUser();
        Integer uid = us.getUid();
        ExamInfo examInfo = SQLManager.getExamInfoManager().getLastInfo();
        if (examInfo == null) {
            IResponse res = IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason", "数据库异常");
        }
        if (uid == null) {
            IResponse res = IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason", "系统异常");
            return res;
        }
        ExamScore examScore = SQLManager.getExamScoreManager().selectExamScore(uid, examInfo.getExamid());
        Boolean confirm = examScore.getConfirm();
        String code = examInfo.getCode();
        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        res.set("confirm", confirm);
        res.set("competition", examInfo.getData().getString(ExamInfo.KEY_COMPETITION));
        res.set("code", code);
        res.set("examId",examInfo.getExamid());
        return res;
    }
}
