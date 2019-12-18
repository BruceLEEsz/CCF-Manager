package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.Examscore;
import com.github.project_njust.ccf_manager.model.Student;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.jetbrains.annotations.NotNull;

public class Confirm extends Service {
    public Confirm(){
        super("confirm", UserType.STUDENT);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        /*User us = input.getUser();
        Integer uid = us.getUid();
        if(uid==null){
            IResponse res =IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason","系统异常");
            return res;
        }*/

        Student sql = SQLManager.getStudentManager().selectStudent(uid);
        if(sql==null){
            IResponse res =IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason","数据库异常");
            return res;
        }

        IResponse res =IResponse.createIResponse(IResponse.Status.SUCCESS);
        res.set("免费资格确认",sql);
        return res;
    }
}
