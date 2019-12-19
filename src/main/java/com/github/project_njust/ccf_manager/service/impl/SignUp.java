package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.model.ExamScore;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import com.github.project_njust.ccf_manager.sql.IStudentManager;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class SignUp extends Service {
    public SignUp() {
        super("signUp", UserType.STUDENT);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        User us = input.getUser();
        ExamInfo examid = SQLManager.getExamInfoManager().getLastInfo();

        Date date = new Date();
        date = examid.getExamdate();
        Date date1 = new Date();
        if(date.before(date1)){
            IResponse response = IResponse.createIResponse(IResponse.Status.ERROR);
            response.set("reason", "未开始报名");
            return  response;
        }

        if (examid==null) {
            IResponse response = IResponse.createIResponse(IResponse.Status.ERROR);
            response.set("reason", "未开始报名");
            return  response;
        }
            ExamScore examScore = SQLManager.getExamScoreManager().selectExamScore(us.getUid(), examid.getExamid());

            if (examScore != null) {
                IResponse response = IResponse.createIResponse(IResponse.Status.ERROR);
                response.set("reason", "已经报名");
                return response;
            } else {
                examScore = new ExamScore(us.getUid(), examid.getExamid(), false, 0, JsonSection.createSection());
                SQLManager.getExamScoreManager().insertExamScore(examScore);
                IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
                return res;
            }

        }
    }

