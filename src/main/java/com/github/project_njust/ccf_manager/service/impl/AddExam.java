package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;
import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.Data;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddExam extends Service {
    public AddExam() {
        super("addExam", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        String signUpTime=input.getData().getString("signUpTime");
        Integer scoreLine=input.getData().getInt("scroeLine");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(signUpTime, pos);
        java.sql.Date sqlDate=new java.sql.Date(strtodate.getTime());

        User us = input.getUser();
        String name = us.getName();
        if(name==null){
            IResponse res =IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason","系统异常");
            return res;
        }

        ExamInfo examInfo=new ExamInfo();
        examInfo.setExamscore(scoreLine);
        int sql = SQLManager.getExamInfoManager().createExamInformation(scoreLine,);
        if(sql==-1){
            IResponse res =IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason","数据库异常");
            return res;
        }

        IResponse res =IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res ;
    }
}