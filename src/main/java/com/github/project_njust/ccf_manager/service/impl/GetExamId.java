package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GetExamId extends Service {
    public GetExamId() {
        super("getExamId", UserType.ADMIN,UserType.STUDENT);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        ExamInfo examInfo = SQLManager.getExamInfoManager().getLastInfo();
       if(examInfo==null){
           IResponse res =IResponse.createIResponse(IResponse.Status.ERROR);
           res.set("reason","考试未开始");
           return res;
       }
        int examid = examInfo.getExamid();
        IResponse res =IResponse.createIResponse(IResponse.Status.SUCCESS);
        res.set("examId",examid+19);
        return res ;
    }
}
