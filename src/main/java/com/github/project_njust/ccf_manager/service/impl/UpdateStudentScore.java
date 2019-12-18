package com.github.project_njust.ccf_manager.service.impl;

import com.github.project_njust.ccf_manager.UserType;
import com.github.project_njust.ccf_manager.service.IResponse;
import com.github.project_njust.ccf_manager.service.ISubmitData;
import com.github.project_njust.ccf_manager.service.Service;
import org.jetbrains.annotations.NotNull;

public class UpdateStudentScore extends Service {
    public UpdateStudentScore(){
        super("updateStudentScore", UserType.ADMIN);
    }

    @Override
    public @NotNull IResponse onRequest(@NotNull ISubmitData input) {
        String uuid = null;


        IResponse res = IResponse.createIResponse(IResponse.Status.SUCCESS);
        return res;
    }
}
