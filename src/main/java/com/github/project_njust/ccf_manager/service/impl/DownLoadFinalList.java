package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.excel.ExcelUtil;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DownLoadFinalList extends Service {
    public DownLoadFinalList(){
        super("downLoadFinalList",UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input){
        UUID uid = ExcelUtil.createFinalList();
        IResponse res=IResponse.createIResponse(IResponse.Status.SUCCESS);
        res.set("UUID",uid.toString());
        return res;

    }
}
