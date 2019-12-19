package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.jetbrains.annotations.NotNull;

public class SetCode extends Service {
    public SetCode(){
        super("setCode", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        String code=input.getData().getString("code");
        ExamInfo examInfo=SQLManager.getExamInfoManager().getLastInfo();
        if(examInfo==null) {
            IResponse response=IResponse.createIResponse(IResponse.Status.ERROR);
            response.set("reason","数据库异常");
            return  response;
        }
        examInfo.setCode(code);
        SQLManager.getExamInfoManager().updateExamInformation(examInfo);
        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}

