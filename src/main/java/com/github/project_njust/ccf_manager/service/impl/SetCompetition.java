package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.jetbrains.annotations.NotNull;

public class SetCompetition extends Service {
    public SetCompetition() {
        super("setCompetition", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        String competition = input.getData().getString("competition");
        ExamInfo lastInfo = SQLManager.getExamInfoManager().getLastInfo();
        if (lastInfo == null) {
            IResponse response = IResponse.createIResponse(IResponse.Status.ERROR);
            response.set("reason", "数据库异常");
            return response;
        }
        lastInfo.getData().set(ExamInfo.KEY_COMPETITION, competition);
        SQLManager.getExamInfoManager().updateExamInformation(lastInfo);
        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}


