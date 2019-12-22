package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.excel.ExcelUtil;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UploadFinalList extends Service {
    public UploadFinalList() {
        super("uploadFinalList", UserType.PRINCIPAL);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        String uuid = input.getData().getString("UUID");
        UUID id = UUID.fromString(uuid);
        try {
            ExcelUtil.loadFinalList(id);
        } catch (Throwable e) {
            IResponse res = IResponse.createIResponse(IResponse.Status.ERROR);
            res.set("reason", "文件解析失败: " + e.getMessage());
            return res;
        }
        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}
