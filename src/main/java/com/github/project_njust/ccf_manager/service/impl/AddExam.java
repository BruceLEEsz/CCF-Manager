package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddExam extends Service {
    public AddExam() {
        super("addExam", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        String signUpTime = input.getData().getString("signUpTime");//报名时间
        int scoreLine = input.getData().getInt("scoreLine");//分数线

        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        dateFormat.setLenient(false);
        Date timeDate = null;
        try {
            timeDate = dateFormat.parse(signUpTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date dateTime = new java.sql.Date(timeDate.getTime());//String转Date

        //User us = input.getUser();
        //String name = us.getName();
        //if(name==null){
        //    IResponse res =IResponse.createIResponse(IResponse.Status.ERROR);
        //   res.set("reason","系统异常");
        //    return res;
        //}


        ExamInfo examInformation = SQLManager.getExamInfoManager().createExamInformation(scoreLine, dateTime);
        if (examInformation == null) {
            IResponse res = IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason", "数据库异常");
            return res;
        }//创建

        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}