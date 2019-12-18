package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.apache.ibatis.jdbc.SQL;
import org.jetbrains.annotations.NotNull;

public class SetScoreLine extends Service {
    public  SetScoreLine(){
        super("setScoreLine", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input){
        Integer scoreLine=input.getData().getInt("scoreLine");
        Integer id=0;
        ExamInfo examInfo=SQLManager.getExamInfoManager().selectExamInformationByExamId(id);
        examInfo.setExamscore(scoreLine);
        SQLManager.getExamInfoManager().updateExamInformation(examInfo);
        examInfo.setExamscore(scoreLine);


        IResponse res=IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}
