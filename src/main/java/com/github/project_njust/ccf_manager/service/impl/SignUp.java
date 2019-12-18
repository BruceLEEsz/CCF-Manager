package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamScore;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import com.github.project_njust.ccf_manager.sql.IStudentManager;
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection;
import org.jetbrains.annotations.NotNull;

public class SignUp extends Service {
    public  SignUp(){
        super("signUp", UserType.STUDENT);
    }
    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input){
        User us=input.getUser();
        ExamScore examScore=SQLManager.getExamScoreManager().selectExamScore(us.getUid(),examid);
        if(examScore!=null) {
            IResponse response=IResponse.createIResponse(IResponse.Status.ERROR);
            response.set("reason","已经报名");
            return response;
        }else {
            examScore = new ExamScore(us.getUid(), examid, false, 0, JsonSection.createSection());
            SQLManager.getExamScoreManager().insertExamScore(examScore);
            IResponse res=IResponse.createIResponse(IResponse.Status.SUCCESS);
            return res;
        }


}
