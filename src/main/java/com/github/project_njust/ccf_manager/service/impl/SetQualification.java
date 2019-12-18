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

public class SetQualification extends Service {
    public SetQualification(){
        super("setQualification", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        String username = input.getData().getString("username");
        Student student = SQLManager.getStudentManager().selectStudentByStudentID(username);
        if (student == null) {
                IResponse res = IResponse.createIResponse(IResponse.Status.ERROR);
                res.set("reason", "找不到学生信息");
                return res;
        }

        User us = SQLManager.getUserManager().selectUserByName(username);
        if(us==null){
            IResponse res =IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason","数据库异常");
            return res;
        }
        int uid = us.getUid();

        Examscore examscore = new Examscore();
        examscore.setConfirm(1);
        examscore.setUid(uid);
        SQLManager.getExamScoreManager().updateExamScore(examscore,uid);


        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}
