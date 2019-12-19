package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.jetbrains.annotations.NotNull;

public class DownloadSignUpList extends Service {
    public DownloadSignUpList(){
        super("downloadSignUpList", UserType.ADMIN);
    }

    @Override
    public @NotNull
    IResponse onRequest(@NotNull ISubmitData input) {




        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}
