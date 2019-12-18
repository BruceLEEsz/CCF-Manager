package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.model.User;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SetScoreLine extends Service {
    public  SetScoreLine(){
        super("setScoreLine", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input){
        Integer scoreLine=input.getData().getInt("scoreLine");

        @Nullable ExamInfo examInfo = SQLManager.getExamInfoManager().getLastInfo();
        if(examInfo==null){
            IResponse res =IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason","找不到考试信息");
            return res;
        }

        examInfo.setExamscore(scoreLine);
        SQLManager.getExamInfoManager().updateExamInformation(examInfo);//更新考试信息

        IResponse res=IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}
