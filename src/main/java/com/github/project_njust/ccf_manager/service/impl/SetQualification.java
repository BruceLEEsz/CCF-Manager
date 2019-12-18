package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamScore;
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
                res.set("reason", "系统异常");
                return res;
        }

        ExamScore examScore=SQLManager.getExamScoreManager().selectExamScoreByUser();
        examScore.setConfirm(true);

        //SQLManager.getExamScoreManager(). 缺确定资格


        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}
