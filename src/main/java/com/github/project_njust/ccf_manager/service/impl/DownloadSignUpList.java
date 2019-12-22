package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.SQLManager;
import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.excel.ExcelUtil;
import com.github.project_njust.ccf_manager.model.ExamInfo;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DownloadSignUpList extends Service {
    public DownloadSignUpList() {
        super("downloadSignUpList", UserType.ADMIN);
    }

    @Override
    public @NotNull
    IResponse onRequest(@NotNull ISubmitData input) {
        ExamInfo lastInfo = SQLManager.getExamInfoManager().getLastInfo();
        if (lastInfo == null) {
            IResponse res = IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason", "未创建任何考试报名信息");
            return res;
        }
        UUID uuid = ExcelUtil.createSignUpList(lastInfo.getExamid());
        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        res.set("UUID", uuid.toString());
        return res;
    }
}
